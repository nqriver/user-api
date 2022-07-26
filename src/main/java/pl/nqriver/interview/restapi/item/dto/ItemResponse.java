package pl.nqriver.interview.restapi.item.dto;

import java.util.UUID;

public record ItemResponse(UUID uuid, String name, OwnerResponse owner) {
}
