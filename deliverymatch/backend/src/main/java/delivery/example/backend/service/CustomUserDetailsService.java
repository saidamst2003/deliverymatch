package delivery.example.backend.service;

import delivery.example.backend.model.UserPrincipal;
import delivery.example.backend.repository.UserRepo;
import delivery.example.backend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    public CustomUserDetailsService(
            final UserRepo userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException
    {

        User user = userRepository.findUserByEmail(email);

        if ( user == null ) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("this user is not found");
        }

        return new UserPrincipal(user);
    }
}
