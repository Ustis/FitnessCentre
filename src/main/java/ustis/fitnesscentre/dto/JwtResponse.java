package ustis.fitnesscentre.dto;

public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
