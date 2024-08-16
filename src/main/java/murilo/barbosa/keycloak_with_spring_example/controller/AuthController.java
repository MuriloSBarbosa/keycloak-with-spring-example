package murilo.barbosa.keycloak_with_spring_example.controller;

import static murilo.barbosa.keycloak_with_spring_example.controller.AuthController.PATH;

import lombok.RequiredArgsConstructor;
import murilo.barbosa.keycloak_with_spring_example.domain.RefreshTokenRequestDto;
import murilo.barbosa.keycloak_with_spring_example.domain.TokenResponseDto;
import murilo.barbosa.keycloak_with_spring_example.domain.User;
import murilo.barbosa.keycloak_with_spring_example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PATH)
@RequiredArgsConstructor
public class AuthController {

    public static final String PATH = "/auth";

    private final AuthService service;

    @PostMapping("/login/admin")
    public ResponseEntity<TokenResponseDto> loginAdmin(@RequestBody User user) {
        return ResponseEntity.ok(service.loginAdmin(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody User user) {
        return ResponseEntity.ok(service.login(user));
    }


    @PostMapping("/logout")
    public ResponseEntity<Jwt> logout(@RequestBody RefreshTokenRequestDto token) {
        service.logout(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto token) {
        return ResponseEntity.ok(service.refresh(token.refreshToken()));
    }
}
