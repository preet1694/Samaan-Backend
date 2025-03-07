package org.samaan.controllers;

import org.samaan.model.User;
import org.samaan.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Ensure frontend access
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Map<String, Object> user(@AuthenticationPrincipal OidcUser principal) {
        if (principal == null) {
            return Map.of("error", "User not authenticated");
        }

        String email = principal.getAttribute("email");
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (existingUser.isEmpty()) {
            // Register new user if not found
            User newUser = new User();
            newUser.setName(principal.getAttribute("name"));
            newUser.setEmail(email);
            newUser.setRole("sender"); // Default role
            userRepository.save(newUser);
        }

        assert email != null;
        return Map.of(
                "id", existingUser.map(User::getId).orElse(null),
                "name", principal.getAttribute("name"),
                "email", email,
                "picture", principal.getAttribute("picture"),
                "role", existingUser.map(User::getRole).orElse("sender")
        );
    }
}
