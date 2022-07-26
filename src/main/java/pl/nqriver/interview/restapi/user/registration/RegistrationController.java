package pl.nqriver.interview.restapi.user.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.interview.restapi.user.registration.dto.RegistrationRequest;
import pl.nqriver.interview.restapi.user.registration.dto.RegistrationResponse;

import javax.validation.Valid;

@RestController
public class RegistrationController {


    private final RegistrationFacade registrationFacade;


    public RegistrationController(final RegistrationFacade registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    @PostMapping("register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody @Valid final RegistrationRequest registrationRequest) {
        RegistrationResponse response = registrationFacade.registerNewUser(registrationRequest);
        return ResponseEntity.ok(response);
    }
}
