package pl.nqriver.interview.restapi.user;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Column(name = "name", unique = true, updatable = false, nullable = false)
    private String name;

    @Column(name = "password")
    private String password;


    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserEntity() {}

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.name);
    }
}
