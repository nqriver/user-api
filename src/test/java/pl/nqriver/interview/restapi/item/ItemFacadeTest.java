package pl.nqriver.interview.restapi.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import pl.nqriver.interview.restapi.common.TestDataUtils;
import pl.nqriver.interview.restapi.item.dto.ItemCreateRequest;
import pl.nqriver.interview.restapi.item.dto.ItemResponse;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.UserEntity;
import pl.nqriver.interview.restapi.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ItemFacadeTest extends AbstractTestcontainersIT {

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void shouldFindItemsOfUser() {
        // given
        String userUnderTest = "user";
        String otherUser = "user2";
        List<String> itemsOfUserUnderTest = List.of("item1", "item2", "item3");
        List<String> itemsOfOtherUser = List.of("item4", "item5", "item6");

        testDataUtils.givenUserWithItems(userUnderTest, itemsOfUserUnderTest);
        testDataUtils.givenUserWithItems(otherUser, itemsOfOtherUser);

        // when
        List<ItemResponse> actualItemsOfUser = itemFacade.findAllByUser(userUnderTest, Pageable.unpaged());
        List<String> actualItemNamesOfUser = actualItemsOfUser.stream().map(ItemResponse::name).toList();

        // then

        assertThat(actualItemNamesOfUser).containsOnly(itemsOfUserUnderTest.toArray(String[]::new));
    }

    @Test
    void shouldCreateNewItem() {
        // given
        String ownerUsername = "kasia";
        UserEntity ownerOfItemUnderTest = testDataUtils.givenUserOfName(ownerUsername);
        String itemName = "bike";

        ItemCreateRequest itemCreateRequest = new ItemCreateRequest(itemName);

        // when
        ItemResponse itemResponse = itemFacade.create(itemCreateRequest, ownerUsername);

        // then
        assertThat(itemRepository.findAllByOwner(ownerOfItemUnderTest, Pageable.unpaged()))
                .extracting("name").containsExactly(itemName);

        assertThat(itemResponse.owner().name()).isEqualTo(ownerUsername);
        assertThat(itemResponse.name()).isEqualTo(itemName);
    }
}