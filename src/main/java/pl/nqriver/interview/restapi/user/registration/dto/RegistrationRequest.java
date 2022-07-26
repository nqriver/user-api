package pl.nqriver.interview.restapi.user.registration.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record RegistrationRequest(@NotBlank String username, @Length(min = 8) String password) {
}
