package delivery.example.backend.controller.auth;

import com.itsupport.backend.dto.RegisterDTO;
import com.itsupport.backend.model.User;
import com.itsupport.backend.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
@CrossOrigin
public class RegisterController {

    private final UserService userService;

    public RegisterController (
            final UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping
    public User register ( RegisterDTO registerDTO ) {
        return userService.registerUser( registerDTO );
    }

}
