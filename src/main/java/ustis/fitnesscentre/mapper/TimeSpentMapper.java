package ustis.fitnesscentre.mapper;

import org.springframework.jdbc.core.RowMapper;
import ustis.fitnesscentre.util.DurationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class TimeSpentMapper implements RowMapper<Duration> {
    @Override
    public Duration mapRow(ResultSet rs, int rowNum) throws SQLException {
        String dbResult = rs.getString("time_spent");
        return DurationUtil.stringToDuration(dbResult);
    }
}
