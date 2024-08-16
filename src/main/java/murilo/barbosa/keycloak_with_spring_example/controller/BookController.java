package murilo.barbosa.keycloak_with_spring_example.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    List<String> books = new ArrayList<>(
          List.of("Spring Boot", "Spring Security", "Spring Data", "Spring Cloud"));

    @GetMapping
//    @Secured("ROLE_USER")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> getBooks() {
        return ResponseEntity.ok(books);
    }

    @PostMapping("/{book}")
//    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addBook(@PathVariable String book) {
        books.add(book);
        return ResponseEntity.ok("Book added");
    }
}
