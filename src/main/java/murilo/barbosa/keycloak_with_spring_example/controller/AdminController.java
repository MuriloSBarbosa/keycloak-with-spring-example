package murilo.barbosa.keycloak_with_spring_example.controller;

import static murilo.barbosa.keycloak_with_spring_example.controller.AdminController.PATH;

import java.util.List;
import lombok.RequiredArgsConstructor;
import murilo.barbosa.keycloak_with_spring_example.domain.UserRequestDto;
import murilo.barbosa.keycloak_with_spring_example.service.AdminService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PATH)
@RequiredArgsConstructor
public class AdminController {

    public static final String PATH = "/admin";

    private final AdminService service;

    @GetMapping("/users")
    public ResponseEntity<List<UserRepresentation>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUser(userId));
    }

    @PostMapping("/users")
    public ResponseEntity<UserRepresentation> createUser(@RequestBody UserRequestDto user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
