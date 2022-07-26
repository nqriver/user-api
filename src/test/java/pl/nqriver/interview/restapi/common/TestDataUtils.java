package pl.nqriver.interview.restapi.common;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.nqriver.interview.restapi.item.ItemEntity;
import pl.nqriver.interview.restapi.item.ItemRepository;
import pl.nqriver.interview.restapi.user.UserEntity;
import pl.nqriver.interview.restapi.user.UserRepository;

import java.util.List;

@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder encoder;

    public TestDataUtils(UserRepository userRepository, ItemRepository itemRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.encoder = encoder;
    }

    public UserEntity givenUserOfName(String name) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword("dummyPassword");
        return userRepository.save(user);
    }


    public UserEntity givenUserOfNameAndEncryptedPassword(String name, String password) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity givenUserWithItems(String username, List<String> itemNames) {
        UserEntity savedUser = userRepository.save(new UserEntity(username, "dummyPasswd"));

        List<ItemEntity> itemsOfUser = itemNames.stream()
                .map(itemName -> new ItemEntity(itemName, savedUser))
                .toList();

        itemRepository.saveAll(itemsOfUser);
        return savedUser;
    }
}
