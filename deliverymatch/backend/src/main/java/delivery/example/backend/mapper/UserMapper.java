package delivery.example.backend.mapper;

import delivery.example.backend.dto.AuthUserDTO;
import delivery.example.backend.model.User;
import delivery.example.backend.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(AuthUserDTO authUserDTO);
    AuthUserDTO toDTO(User user);

    default Role map(Role role) {
        return role;
    }
}
