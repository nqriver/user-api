package pl.nqriver.interview.restapi.item;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.nqriver.interview.restapi.item.dto.ItemCreateRequest;
import pl.nqriver.interview.restapi.item.dto.ItemResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {


    private final ItemFacade itemFacade;

    public ItemController(final ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody @Valid final ItemCreateRequest itemCreateRequest,
                                               final Authentication authentication) {
        final String nameOfLoggedInUser = authentication.getName();
        final ItemResponse response = itemFacade.create(itemCreateRequest, nameOfLoggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll(
            final Pageable pageable,
            final Authentication authentication) {
        final String nameOfLoggedInUser = authentication.getName();
        return ResponseEntity.ok(itemFacade.findAllByUser(nameOfLoggedInUser, pageable));
    }
}
