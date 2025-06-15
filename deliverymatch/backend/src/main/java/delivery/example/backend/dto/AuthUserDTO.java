package delivery.example.backend.dto;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        Role role
) {
}

