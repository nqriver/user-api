package pl.nqriver.interview.restapi.user.registration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nqriver.interview.restapi.user.UserAlreadyExistsException;
import pl.nqriver.interview.restapi.user.UserEntity;
import pl.nqriver.interview.restapi.user.UserRepository;

@Service
public class RegistrationFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public RegistrationFacade(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public void registerNewUser(final RegistrationRequest registrationRequest) {
        if (userRepository.existsByName(registrationRequest.username())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(registrationRequest.username());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        userRepository.save(user);
    }

}
