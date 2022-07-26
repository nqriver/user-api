package pl.nqriver.interview.restapi.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.nqriver.interview.restapi.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

    List<ItemEntity> findAllByOwner(UserEntity owner, Pageable pageable);

}