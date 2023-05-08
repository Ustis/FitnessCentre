package ustis.fitnesscentre.mapper;

import org.springframework.jdbc.core.RowMapper;
import ustis.fitnesscentre.model.ClubCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubCardMapper implements RowMapper<ClubCard> {
    @Override
    public ClubCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClubCard clubCard = new ClubCard();
        clubCard.setId(rs.getLong("id"));
        clubCard.setActiveSince(rs.getDate("active_since").toLocalDate());
        clubCard.setActiveUntil(rs.getDate("active_until").toLocalDate());
        clubCard.setActive(rs.getBoolean("is_active"));
        clubCard.setClientId(rs.getLong("client_id"));
        return clubCard;
    }
}
