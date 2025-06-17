package delivery.example.backend.controller.auth;

import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.model.User;
import delivery.example.backend.service.Authservice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
@CrossOrigin
public class RegisterController {

    private final Authservice authService;

    public RegisterController(final Authservice authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO registerDTO) {
        User registeredUser = authService.registerUser(registerDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}