package murilo.barbosa.keycloak_with_spring_example.helper;

import lombok.Builder;
import murilo.barbosa.keycloak_with_spring_example.domain.GrantTypeEnum;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
public class KeycloakFormRequest {

    private GrantTypeEnum grantType;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String refreshToken;

    public MultiValueMap<String, String> toMap() {
        var map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", clientId);
        map.add("grant_type", grantType.getValue());
        if (username != null) {
            map.add("username", username);
        }
        if (password != null) {
            map.add("password", password);
        }
        if (refreshToken != null) {
            map.add("refresh_token", refreshToken);
        }
        if (clientSecret != null) {
            map.add("client_secret", clientSecret);
        }
        return map;
    }
}
