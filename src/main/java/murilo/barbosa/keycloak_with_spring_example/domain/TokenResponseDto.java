package murilo.barbosa.keycloak_with_spring_example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponseDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not-before-policy")
    private Integer notBeforePolicy;
    @JsonProperty("session_state")
    private String sessionState;
    private String scope;
}
