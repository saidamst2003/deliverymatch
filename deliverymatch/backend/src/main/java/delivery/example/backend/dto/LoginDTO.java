package delivery.example.backend.dto;

public record LoginDTO(
        @NotBlank(message = "email is required")
        @Email( message = "please enter a valid email")
        String email,

        @NotBlank( message = "password is required" )
        String password
) {

}
