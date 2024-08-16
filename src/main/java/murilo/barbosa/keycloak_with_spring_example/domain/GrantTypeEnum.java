package murilo.barbosa.keycloak_with_spring_example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GrantTypeEnum {

    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials");

    private final String value;
}
