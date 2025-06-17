package delivery.example.backend.service;

import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.model.Role;
import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Authservice {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public Authservice(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterDTO registerDTO) {
        User newUser = new User();
        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
        newUser.setRole(registerDTO.role());

        return userRepository.save(newUser);
    }
}