package pl.nqriver.interview.restapi.item;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.nqriver.interview.restapi.user.UserEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Column(name = "name", nullable = false)
    @NaturalId
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;


    public ItemEntity(String name, UserEntity owner) {
        this.name = name;
        this.owner = owner;
    }

    public ItemEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UserEntity getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
