package ustis.fitnesscentre.dto;

public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;

    private String roles;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtResponse(String accessToken, String roles) {
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public String getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRoles() {
        return roles;
    }
}
