package delivery.example.backend.controller.auth;

import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.model.User;
import delivery.example.backend.service.Authservice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/register")
@CrossOrigin
public class RegisterController {

    private final Authservice authService;

    public RegisterController(final Authservice authService) {
        this.authService = authService;
    }

    @PostMapping("/{role}")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO registerDTO, @PathVariable String role) {
        User registeredUser = authService.registerUser(registerDTO, role);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}