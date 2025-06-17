package delivery.example.backend.service;

import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepository;
//    private final AdminRepository adminRepository;
//    private final DriverRepository driverRepository;
//    private final SenderRepository senderRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthService(
            UserRepo userRepository,
//            AdminRepository adminRepository,
//            DriverRepository driverRepository,
    ) {
        this.userRepository = userRepository;
//        this.adminRepository = adminRepository;
//        this.driverRepository = driverRepository;
    }

    public User registerUser(RegisterDTO registerDTO) {
        User newUser;

        switch (registerDTO.fullName().toLowerCase()) {
            case "admin":
                newUser = new KafkaProperties.Admin();
                break;
//            case "sender":
//                newUser = new ();
//                break;
//            case "driver":
//                newUser = new ();
//                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + registerDTO.fullName());
        }

        newUser.getFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(encoder.encode(registerDTO.password()));

        return userRepository.save(newUser);
    }
}
