package pl.nqriver.interview.restapi.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public RegistrationFacade(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public void registerNewUser(final String name, final String password) {
        if (userRepository.findByName(name).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
