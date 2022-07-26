package pl.nqriver.interview.restapi.item;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nqriver.interview.restapi.item.dto.ItemCreateRequest;
import pl.nqriver.interview.restapi.item.dto.ItemMapper;
import pl.nqriver.interview.restapi.item.dto.ItemResponse;
import pl.nqriver.interview.restapi.user.UserEntity;
import pl.nqriver.interview.restapi.user.UserRepository;

import java.util.List;

@Service
public class ItemFacade {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemFacade(final UserRepository userRepository,
                      final ItemRepository itemRepository,
                      final ItemMapper itemMapper) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    ItemResponse create(final ItemCreateRequest createRequest, final String nameOfLoggedInUser) {

        UserEntity user = getLoggedInUser(nameOfLoggedInUser);

        ItemEntity savedItem = new ItemEntity(createRequest.name(), user);
        return itemMapper.toItemResponse(itemRepository.save(savedItem));

    }

    public List<ItemResponse> findAllByUser(final String nameOfLoggedInUser, final Pageable pageable) {
        UserEntity loggedUser = getLoggedInUser(nameOfLoggedInUser);
        return itemMapper.toListOfItemResponse(
                itemRepository.findAllByOwner(loggedUser, pageable));
    }

    private UserEntity getLoggedInUser(final String nameOfLoggedInUser) {
        return userRepository.findByName(nameOfLoggedInUser).orElseThrow(
                () -> new IllegalStateException("Cannot fetch currently logged user"));
    }
}
