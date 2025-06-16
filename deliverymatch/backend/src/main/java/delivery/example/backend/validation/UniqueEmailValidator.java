package delivery.example.backend.validation;

import delivery.example.backend.model.User;
import delivery.example.backend.repository.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepo userRepository;

    public UniqueEmailValidator (
            final UserRepo userRepository
    ) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        User foundEmail = userRepository.findUserByEmail(email);

        return foundEmail == null;
    }
}
