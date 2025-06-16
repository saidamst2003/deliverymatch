package delivery.example.backend.config;

import delivery.example.backend.model.Role;
import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(UserRepo userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findUserByEmail("sine@gmail.com") == null) {
                User newUser = new User(); // Using your custom User class

                newUser.setFullName("admin");
                newUser.setEmail("admin@gmail.com");
                newUser.setPassword(encoder.encode("123456"));
                newUser.setRole(Role.ADMIN);

                userRepository.save(newUser);

            } else {
                System.out.println("this user is already exists");
            }
        };
    }
}