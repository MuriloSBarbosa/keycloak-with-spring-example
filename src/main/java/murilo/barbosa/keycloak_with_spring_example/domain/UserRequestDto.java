package murilo.barbosa.keycloak_with_spring_example.domain;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Map<String, List<String>> attributes;
    private boolean enabled;
}
