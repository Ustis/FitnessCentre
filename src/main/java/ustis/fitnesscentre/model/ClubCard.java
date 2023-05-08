package ustis.fitnesscentre.model;

import java.time.LocalDate;

public class ClubCard {
    private Long id;
    private LocalDate activeSince;
    private LocalDate activeUntil;
    private boolean isActive;
    private Long clientId;

    public ClubCard() {
    }

    public ClubCard(LocalDate activeSince, LocalDate activeUntil, boolean isActive, Long clientId) {
        this.activeSince = activeSince;
        this.activeUntil = activeUntil;
        this.isActive = isActive;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(LocalDate activeSince) {
        this.activeSince = activeSince;
    }

    public LocalDate getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(LocalDate activeUntil) {
        this.activeUntil = activeUntil;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
