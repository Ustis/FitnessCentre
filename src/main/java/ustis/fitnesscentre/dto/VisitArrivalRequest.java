package ustis.fitnesscentre.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitArrivalRequest {
    private String arrivalDateTime;

    public LocalDateTime getArrivalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return LocalDateTime.parse(arrivalDateTime, formatter);
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
}
