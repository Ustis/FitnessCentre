package ustis.fitnesscentre.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ustis.fitnesscentre.mapper.ClientMapper;
import ustis.fitnesscentre.model.Client;

import java.util.Collections;
import java.util.List;
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
                    "SELECT id, phoneNumber, password, full_name, birthday_date, gender, balance, roles FROM client WHERE phoneNumber = ?",
                    new Object[]{phoneNumber},
                    new ClientMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Client> getAll() {
        try {
            return this.jdbcTemplate.query(
                    "SELECT id, phoneNumber, password, full_name, birthday_date, gender, balance, roles FROM client",
                    new ClientMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return Collections.emptyList();
        }
    }

    public void update(Client client) {
        this.jdbcTemplate.update("UPDATE client SET phoneNumber = ?, password = ?, full_name = ?, birthday_date = ?, " +
                        "gender = ?, balance = ? WHERE id = ?",
                client.getPhoneNumber(), client.getPassword(), client.getFullName(),
                java.sql.Date.valueOf(client.getBirthdayDate()), client.getGender(),
                client.getBalance(),
                client.getId()
        );
    }

    public void updateWithoutPassword(Client client) {
        this.jdbcTemplate.update("UPDATE client SET phoneNumber = ?, full_name = ?, birthday_date = ?, " +
                        "gender = ?, balance = ? WHERE id = ?",
                client.getPhoneNumber(), client.getFullName(),
                java.sql.Date.valueOf(client.getBirthdayDate()), client.getGender(),
                client.getBalance(),
                client.getId()
        );
    }

    public void save(Client client) {
        try {
            String sql = "INSERT INTO client (phonenumber, password, full_name, birthday_date, gender, balance, roles) " +
                    "VALUES (:phoneNumber, :password, :fullName, :birthdayDate, :gender, :balance, :roles)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("phoneNumber", client.getPhoneNumber())
                    .addValue("password", client.getPassword())
                    .addValue("fullName", client.getFullName())
                    .addValue("birthdayDate", java.sql.Date.valueOf(client.getBirthdayDate()))
                    .addValue("gender", client.getGender())
                    .addValue("balance", client.getBalance())
                    .addValue("roles", client.getRoles());
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
            namedParameterJdbcTemplate.update(sql, params);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("client_phone_number_idx")) {
                throw new DuplicateKeyException("Пользователь с таким номером телефона уже существует");
            } else {
                throw e;
            }
        }
    }
}
