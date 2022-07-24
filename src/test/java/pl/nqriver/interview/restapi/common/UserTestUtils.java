package pl.nqriver.interview.restapi.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nqriver.interview.restapi.user.UserEntity;
import pl.nqriver.interview.restapi.user.UserRepository;

public class UserTestUtils {

    public static void givenUserOfName(String name, UserRepository userRepository) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword("dummyPassword");
        userRepository.save(user);
    }


    public static void givenUserOfNameAndEncryptedPassword(String name,
                                                           String password,
                                                           PasswordEncoder encoder,
                                                           UserRepository userRepository) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
