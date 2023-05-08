package ustis.fitnesscentre.model;

import java.time.LocalDateTime;

public class Visit {
    private Long id;
    private LocalDateTime arrival;
    private LocalDateTime leaving;
    private Long clientId;

    public Visit() {
    }

    public Visit(LocalDateTime arrival, LocalDateTime leaving, Long clientId) {
        this.arrival = arrival;
        this.leaving = leaving;
        this.clientId = clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getLeaving() {
        return leaving;
    }

    public void setLeaving(LocalDateTime leaving) {
        this.leaving = leaving;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
