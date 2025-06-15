package delivery.example.backend.dto;

import javax.management.relation.Role;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        Role role
) {
}

