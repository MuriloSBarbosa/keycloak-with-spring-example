package murilo.barbosa.keycloak_with_spring_example.service;


import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import murilo.barbosa.keycloak_with_spring_example.domain.UserRequestDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Value("${keycloak.realm}")
    private String REALM;

    private final Keycloak keycloak;

    public List<UserRepresentation> getUsers() {
        return keycloak.realm(REALM).users().list();
    }

    public UserRepresentation getUser(String userId) {
        return keycloak.realm(REALM).users().get(userId).toRepresentation();
    }

    public UserRepresentation createUser(UserRequestDto user) {

        var userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setAttributes(user.getAttributes());
        userRepresentation.setEnabled(true);

        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        var result = keycloak.realm(REALM).users().create(userRepresentation);

        if (result.getStatus() != 201) {
            throw new ResponseStatusException(HttpStatus.valueOf(result.getStatus()),
                  result.getStatusInfo().getReasonPhrase());
        }

        URI location = result.getLocation();
        var id = location.getPath().substring(location.getPath().lastIndexOf('/') + 1);

        return keycloak.realm(REALM).users().get(id).toRepresentation();
    }

    public void deleteUser(String userId) {
        keycloak.realm(REALM).users().delete(userId);
    }


}
