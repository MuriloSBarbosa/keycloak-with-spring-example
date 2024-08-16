package murilo.barbosa.keycloak_with_spring_example.config;

import murilo.barbosa.keycloak_with_spring_example.controller.AdminController;
import murilo.barbosa.keycloak_with_spring_example.controller.AuthController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
              .requestMatchers(
                    AuthController.PATH + "/**",
                    "/swagger/**",
                    "/v3/api-docs/**"
              ).permitAll()
              .requestMatchers(AdminController.PATH + "/**").hasRole("KEYCLOAK_ADMIN")
              .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2
              .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtConverter()))
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(Customizer.withDefaults());

        return http.build();
    }

}
