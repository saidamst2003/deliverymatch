package delivery.example.backend.service;

import delivery.example.backend.dto.RegisterDTO;
import delivery.example.backend.model.*;
import delivery.example.backend.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Authservice {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructeur pour l'injection de dépendances
    public Authservice(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Enregistre un nouvel utilisateur avec un rôle spécifié
    public User registerUser(RegisterDTO registerDTO, String role) {
        User newUser;

        if(role.equalsIgnoreCase("admin")) {
            newUser = new Administrateur();
            newUser.setRole(Role.ADMIN);
        } else if (role.equalsIgnoreCase("conducteur")) {
            newUser = new Conducteur();
            newUser.setRole(Role.CONDUCTEUR);
        } else {
            newUser = new Expediteur();
            newUser.setRole(Role.EXPEDITEUR);
        }

        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));

        return userRepository.save(newUser);
    }
hh
}
