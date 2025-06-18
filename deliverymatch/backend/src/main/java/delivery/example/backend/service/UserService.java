package delivery.example.backend.service;

import delivery.example.backend.dto.AuthUserDTO;
import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.exception.PasswordIncorrectException;
import delivery.example.backend.mapper.UserMapper;
import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    public UserService(
            final UserRepo userRepository,
            final AuthenticationManager authenticationManager,
            final UserMapper userMapper,
            final JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public User registerUser(RegisterDTO registerDTO) {
        if (userRepository.findUserByEmail(registerDTO.email()) != null) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        User newUser = new User();
        String encryptedPassword = encoder.encode(registerDTO.password());

        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(encryptedPassword);
        //newUser.setRole(registerDTO.role());

        return userRepository.save(newUser);
    }

    public ResponseEntity<?> verify(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                AuthUserDTO authUser = this.getAuthenticatedUser(user.getEmail());
                String token = jwtService.generateJwtToken(authUser);

                Map<String, String> responseSuccess = new HashMap<>();
                responseSuccess.put("token", token);

                return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
            }
            throw new PasswordIncorrectException("Invalid credentials (Authentication failed unexpectedly).");
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed for user: " + user.getEmail() + " Error: " + e.getMessage());
            throw new PasswordIncorrectException("Invalid credentials.");
        }
    }

    public AuthUserDTO getAuthenticatedUser(String email) {
        User authenticatedUser = userRepository.findUserByEmail(email);
        if (authenticatedUser == null) {
            throw new PasswordIncorrectException("User not found after authentication (this should not happen).");
        }
        return userMapper.toDTO(authenticatedUser);
    }
}