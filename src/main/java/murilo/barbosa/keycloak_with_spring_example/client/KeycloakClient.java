package murilo.barbosa.keycloak_with_spring_example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.base-url}")
    private String BASE_KEYCLOAK_URL;

    @Value("${keycloak.realm}")
    private String REALM;

    public <T> ResponseEntity<T> sendRequest(String endpoint,
          MultiValueMap<String, String> form, Class<T> responseType) {
        var headers = createHeaders();
        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.postForEntity(getKeycloakUrl(endpoint), requestEntity,
              responseType);
    }

    private HttpHeaders createHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private String getKeycloakUrl(String endpoint) {
        return "%s/realms/%s/protocol/openid-connect%s"
              .formatted(BASE_KEYCLOAK_URL, REALM, endpoint);
    }
}
