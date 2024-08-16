package murilo.barbosa.keycloak_with_spring_example.service;

import static murilo.barbosa.keycloak_with_spring_example.domain.GrantTypeEnum.CLIENT_CREDENTIALS;
import static murilo.barbosa.keycloak_with_spring_example.domain.GrantTypeEnum.PASSWORD;
import static murilo.barbosa.keycloak_with_spring_example.domain.GrantTypeEnum.REFRESH_TOKEN;

import lombok.RequiredArgsConstructor;
import murilo.barbosa.keycloak_with_spring_example.client.KeycloakClient;
import murilo.barbosa.keycloak_with_spring_example.domain.GrantTypeEnum;
import murilo.barbosa.keycloak_with_spring_example.domain.RefreshTokenRequestDto;
import murilo.barbosa.keycloak_with_spring_example.domain.TokenResponseDto;
import murilo.barbosa.keycloak_with_spring_example.domain.User;
import murilo.barbosa.keycloak_with_spring_example.helper.KeycloakFormRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${keycloak.client-id}")
    private String CLIENT_ID;

    @Value("${keycloak.client-admin-id}")
    private String CLIENT_ADMIN_ID;

    private final KeycloakClient keycloakClient;

    private static final String TOKEN_ENDPOINT = "/token";

    public TokenResponseDto login(User user) {
        var form = getForm(user, PASSWORD);
        var result = keycloakClient.sendRequest(TOKEN_ENDPOINT, form, TokenResponseDto.class);
        return result.getBody();
    }


    public TokenResponseDto loginAdmin(User user) {
        var form = getAdminForm(user);
        var result = keycloakClient.sendRequest(TOKEN_ENDPOINT, form, TokenResponseDto.class);
        return result.getBody();
    }

    public TokenResponseDto refresh(String refreshToken) {
        var form = getForm(refreshToken, REFRESH_TOKEN);
        var result = keycloakClient.sendRequest(TOKEN_ENDPOINT, form, TokenResponseDto.class);
        return result.getBody();
    }

    public void logout(RefreshTokenRequestDto token) {
        var form = getForm(token.refreshToken(), REFRESH_TOKEN);
        keycloakClient.sendRequest("/logout", form, Void.class);
    }

    private MultiValueMap<String, String> getAdminForm(User user) {
        return KeycloakFormRequest.builder()
              .grantType(CLIENT_CREDENTIALS)
              .clientId(user.username())
              .clientSecret(user.password())
              .build()
              .toMap();
    }

    private MultiValueMap<String, String> getForm(String token, GrantTypeEnum grantType) {
        return KeycloakFormRequest.builder()
              .clientId(CLIENT_ID)
              .grantType(grantType)
              .refreshToken(token)
              .build()
              .toMap();
    }

    private MultiValueMap<String, String> getForm(User user,
          GrantTypeEnum grantType) {
        return KeycloakFormRequest.builder()
              .clientId(CLIENT_ID)
              .grantType(grantType)
              .username(user.username())
              .password(user.password())
              .build()
              .toMap();
    }

}
