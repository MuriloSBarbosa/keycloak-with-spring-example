package murilo.barbosa.keycloak_with_spring_example.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

    @Value("${keycloak.base-url}")
    private String url;

    @Value("${keycloak.admin.username}")
    private String username;

    @Value("${keycloak.admin.password}")
    private String password;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
              .serverUrl(url)
              .realm("master")
              .clientId("admin-cli")
              .username(username)
              .password(password)
              .build();
    }
}
