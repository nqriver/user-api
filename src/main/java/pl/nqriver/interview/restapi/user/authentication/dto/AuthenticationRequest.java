package pl.nqriver.interview.restapi.user.authentication.dto;

import javax.validation.constraints.NotNull;

public record AuthenticationRequest(@NotNull String username, @NotNull String password) {

}