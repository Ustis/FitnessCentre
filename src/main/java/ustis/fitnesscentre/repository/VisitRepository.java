package ustis.fitnesscentre.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ustis.fitnesscentre.mapper.TimeSpentMapper;
import ustis.fitnesscentre.mapper.VisitMapper;
import ustis.fitnesscentre.model.Visit;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public class VisitRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VisitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Visit> findAll() {
        return jdbcTemplate.query("SELECT * FROM visit", new VisitMapper());
    }

    public Visit findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM visit WHERE id = ?", new Object[]{id}, new VisitMapper());
    }

    public Optional<Visit> findIncompleteSession(Long clientId) {
        Optional<Visit> visit = Optional.empty();
        try {
            visit = Optional.of(jdbcTemplate.queryForObject(
                    "SELECT * FROM visit WHERE client_id = ? and leaving IS NULL",
                    new Object[]{clientId},
                    new VisitMapper()));
        } catch (IncorrectResultSizeDataAccessException e) {
            jdbcTemplate.update("UPDATE visit\n" +
                            "SET leaving = arrival + INTERVAL '5 minutes'\n" +
                            "WHERE client_id = ? AND leaving IS NULL AND ID NOT IN (\n" +
                            "    SELECT id from visit WHERE client_id = ? ORDER BY arrival DESC LIMIT 1\n" +
                            ")",
                    clientId, clientId);
            visit = findIncompleteSession(clientId);
        }
        return visit;
    }

    public Duration findTimeSpent(Long clientId) {
        return jdbcTemplate.queryForObject("SELECT SUM(leaving - arrival) AS time_spent\n" +
                        "FROM visit\n" +
                        "WHERE client_id = ? AND leaving IS NOT NULL AND leaving >= NOW() - INTERVAL '1 MONTH';",
                new Object[]{clientId},
                new TimeSpentMapper()
        );
    }

    public void save(Visit visit) {
        jdbcTemplate.update("INSERT INTO visit (arrival, leaving, client_id) VALUES (?, ?, ?)",
                Timestamp.valueOf(visit.getArrival()), Timestamp.valueOf(visit.getLeaving()), visit.getClientId());
    }

    public void update(Visit visit) {
        jdbcTemplate.update("UPDATE visit SET arrival = ?, leaving = ?, client_id = ? WHERE id = ?",
                Timestamp.valueOf(visit.getArrival()), Timestamp.valueOf(visit.getLeaving()), visit.getClientId(), visit.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM visit WHERE id = ?", id);
    }
}
