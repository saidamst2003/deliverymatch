package delivery.example.backend.dto;

import delivery.example.backend.model.Role;
import delivery.example.backend.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "full name is required")
        String fullName,

        @NotBlank(message = "email is required")
        @Email(message = "please enter a valid email")
        @UniqueEmail
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, message = "password must be at least 6 characters long")
        String password
) {
}