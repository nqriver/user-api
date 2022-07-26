package pl.nqriver.interview.restapi.item.dto;

import org.springframework.stereotype.Component;
import pl.nqriver.interview.restapi.item.ItemEntity;
import pl.nqriver.interview.restapi.user.UserEntity;

import java.util.List;

@Component
public class ItemMapper {

    public ItemResponse toItemResponse(final ItemEntity itemEntity) {
        return new ItemResponse(itemEntity.getUuid(), itemEntity.getName(), this.toOwnerResponse(itemEntity.getOwner()));
    }

    private OwnerResponse toOwnerResponse(final UserEntity userEntity) {
        return new OwnerResponse(userEntity.getUuid(), userEntity.getName());
    }

    public List<ItemResponse> toListOfItemResponse(List<ItemEntity> allByOwner) {
        return allByOwner.stream().map(this::toItemResponse).toList();
    }
}
