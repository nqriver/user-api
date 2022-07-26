package pl.nqriver.interview.restapi.user.registration.dto;

import pl.nqriver.interview.restapi.user.UserEntity;

import java.util.UUID;

public record RegistrationResponse(UUID uuid, String username) {
    public static RegistrationResponse ofUserEntity(UserEntity userEntity) {
        return new RegistrationResponse(userEntity.getUuid(), userEntity.getName());
    }
}
