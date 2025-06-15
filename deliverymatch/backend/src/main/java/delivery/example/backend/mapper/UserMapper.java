package delivery.example.backend.mapper;


import delivery.example.backend.dto.AuthUserDTO;
import org.apache.catalina.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(AuthUserDTO authUserDTO);
    AuthUserDTO toDTO( User user );
}
