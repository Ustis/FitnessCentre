package ustis.fitnesscentre.mapper;

import org.springframework.jdbc.core.RowMapper;
import ustis.fitnesscentre.model.Client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(
                rs.getLong("id"),
                rs.getString("phoneNumber"),
                rs.getString("password"),
                rs.getString("full_name"),
                rs.getDate("birthday_date").toLocalDate(),
                rs.getString("gender"),
                rs.getString("roles"),
                rs.getBigDecimal("balance")
        );
    }
}
