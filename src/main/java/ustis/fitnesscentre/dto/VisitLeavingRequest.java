package ustis.fitnesscentre.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitLeavingRequest {
    private String leavingDateTime;

    public LocalDateTime getLeavingDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return LocalDateTime.parse(leavingDateTime, formatter);
    }

    public void setLeavingDateTime(String leavingDateTime) {
        this.leavingDateTime = leavingDateTime;
    }
}
