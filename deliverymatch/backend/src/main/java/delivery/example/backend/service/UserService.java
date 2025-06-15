package delivery.example.backend.security;

import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import delivery.example.backend.service.JwtService;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserService (
            final UserRepo userRepository,
//            final AuthenticationManager authenticationManager,
          //  final UserMapper userMapper,
            final JwtService jwtService
    ) {
        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
     //   this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User registerUser (RegisterDTO registerDTO ) {
        User newUser = new User();

        String encryptedPassword = encoder.encode( registerDTO.password() );

        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword( encryptedPassword );
        newUser.setRole( registerDTO.role() );

        return userRepository.save(newUser);
    }

    public ResponseEntity<?> verify ( User user) {
        try {

            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()){
                AuthUserDTO authUser = this.getAuthenticatedUser(user.getEmail());
                String token = jwtService.generateJwtToken(authUser);

                Map<String, String> responseSuccess = new HashMap<>();
                responseSuccess.put("token", token);

                return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
            }

            throw  new PasswordIncorrectException("Invalid credentials");
        }
        catch (AuthenticationException e ) {
            throw  new PasswordIncorrectException("Invalid credentials");
        }
    }

    public AuthUserDTO getAuthenticatedUser ( String email ) {
        User authenticatedUser = userRepository.findUserByEmail( email );
        return userMapper.toDTO(authenticatedUser);
    }

}
