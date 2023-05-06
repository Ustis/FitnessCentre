package ustis.fitnesscentre.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ustis.fitnesscentre.mapper.ClientMapper;
import ustis.fitnesscentre.model.Client;

import java.util.Optional;

@Repository
public class ClientRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Client> findByPhoneNumber(String phoneNumber) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                    "SELECT id, phoneNumber, password, full_name, birthday_date, gender FROM client " +
                            "WHERE phoneNumber = ?",
                    new Object[]{phoneNumber},
                    new ClientMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void save(Client client) {
        try {
            this.jdbcTemplate.update(
                    "INSERT INTO client ( phonenumber, password, full_name, birthday_date, gender) \n" +
                            "VALUES ( ?, ? , ? , ? , ? );",
                    client.getPhoneNumber(), client.getPassword(), client.getFullName(), java.sql.Date.valueOf(client.getBirthdayDate()), client.getGender()
            );
        } catch (DataIntegrityViolationException e) {
            // Обработка ошибки
            if (e.getMessage().contains("client_phone_number_idx")) {
                throw new DuplicateKeyException("Пользователь с таким номером телефона уже существует");
            } else {
                throw e;
            }
        }
    }
}
