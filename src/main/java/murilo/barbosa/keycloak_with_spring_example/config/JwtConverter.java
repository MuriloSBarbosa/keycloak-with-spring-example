package murilo.barbosa.keycloak_with_spring_example.config;

import java.util.List;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Map<String, List<String>> realmAccessRoles = source.getClaim("realm_access");

        var roles = realmAccessRoles.get("roles");

        var grants = roles.stream()
              .map(role -> "ROLE_" + role)
              .map(SimpleGrantedAuthority::new)
              .toList();

        return new JwtAuthenticationToken(source, grants);
    }
}
