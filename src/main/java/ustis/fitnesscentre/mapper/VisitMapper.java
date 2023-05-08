package ustis.fitnesscentre.mapper;

import org.springframework.jdbc.core.RowMapper;
import ustis.fitnesscentre.model.Visit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitMapper implements RowMapper<Visit> {
    @Override
    public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Visit visit = new Visit();
        visit.setId(rs.getLong("id"));
        visit.setArrival(rs.getTimestamp("arrival").toLocalDateTime());
        visit.setLeaving(rs.getTimestamp("leaving") != null ? rs.getTimestamp("leaving").toLocalDateTime() : null);
        visit.setClientId(rs.getLong("client_id"));
        return visit;
    }
}
