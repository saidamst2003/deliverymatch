package delivery.example.backend.dto;

import delivery.example.backend.model.Role;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        Role role
) {
}
