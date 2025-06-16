package delivery.example.backend.controller.auth;

import delivery.example.backend.dto.LoginDTO;
import delivery.example.backend.model.User;
import delivery.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/login")
@CrossOrigin
public class LoginController {

    private final UserService userService;

    public LoginController (
           final UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> loginUser (@Valid @RequestBody LoginDTO loginDTO) {
        User user = new User();

        user.setEmail(loginDTO.email());
        user.setPassword(loginDTO.password());

        return userService.verify(user);

    }
}
