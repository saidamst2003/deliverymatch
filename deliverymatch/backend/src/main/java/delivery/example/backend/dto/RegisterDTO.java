package delivery.example.backend.dto;


import delivery.example.backend.model.Role;
import delivery.example.backend.validation.UniqueEmail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank(message = "full name is required")
        String fullName,

        @NotBlank(message = "email is required")
        @Email(message = "please enter a valid email")
        @UniqueEmail
        String email,

        @NotBlank(message = "password is required")
        @Min(value = 6, message = "password must be at least 6 chars")
        String password,

        @NotNull(message = "role is required")
        Role role
) {
}
