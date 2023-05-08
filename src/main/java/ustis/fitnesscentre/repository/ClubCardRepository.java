package ustis.fitnesscentre.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        jdbcTemplate.update("INSERT INTO club_card (active_since, active_until, is_active, client_id) VALUES (?, ?, ?, ?)",
                Date.valueOf(clubCard.getActiveSince()), Date.valueOf(clubCard.getActiveUntil()), clubCard.isActive(), clubCard.getClientId());
    }

    public void update(ClubCard clubCard) {
        jdbcTemplate.update("UPDATE club_card SET active_since = ?, active_until = ?, is_active = ?, client_id = ? WHERE id = ?",
                Date.valueOf(clubCard.getActiveSince()), Date.valueOf(clubCard.getActiveUntil()), clubCard.isActive(), clubCard.getClientId(), clubCard.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM club_card WHERE id = ?", id);
    }
}
