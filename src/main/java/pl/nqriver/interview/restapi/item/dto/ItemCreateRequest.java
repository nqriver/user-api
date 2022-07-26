package pl.nqriver.interview.restapi.item.dto;

import javax.validation.constraints.NotBlank;

public record ItemCreateRequest(@NotBlank String name) {
}
