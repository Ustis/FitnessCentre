package ustis.fitnesscentre.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ustis.fitnesscentre.mapper.ClubCardMapper;
import ustis.fitnesscentre.model.ClubCard;

import java.sql.Date;
import java.util.List;

@Repository
public class ClubCardRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClubCardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClubCard> findAll() {
        return jdbcTemplate.query("SELECT * FROM club_card", new ClubCardMapper());
    }

    public ClubCard findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM club_card WHERE id = ?", new Object[]{id}, new ClubCardMapper());
    }

    public boolean isClubCardActive(Long clientId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM club_card WHERE client_id = ? AND is_active = true AND active_since <= CURRENT_DATE AND active_until >= CURRENT_DATE)",
                Boolean.class, clientId
        );
    }

    public void save(ClubCard clubCard) {
        String sql = "INSERT INTO club_card (active_since, active_until, is_active, client_id) " +
                "VALUES (:activeSince, :activeUntil, :isActive, :clientId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("activeSince", Date.valueOf(clubCard.getActiveSince()))
                .addValue("activeUntil", Date.valueOf(clubCard.getActiveUntil()))
                .addValue("isActive", clubCard.isActive())
                .addValue("clientId", clubCard.getClientId());
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(ClubCard clubCard) {
        String sql = "UPDATE club_card SET active_since = :activeSince, active_until = :activeUntil, is_active = :isActive, client_id = :clientId WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("activeSince", Date.valueOf(clubCard.getActiveSince()))
                .addValue("activeUntil", Date.valueOf(clubCard.getActiveUntil()))
                .addValue("isActive", clubCard.isActive())
                .addValue("clientId", clubCard.getClientId())
                .addValue("id", clubCard.getId());
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM club_card WHERE id = ?", id);
    }
}
