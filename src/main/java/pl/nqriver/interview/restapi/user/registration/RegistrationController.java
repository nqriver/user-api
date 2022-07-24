package pl.nqriver.interview.restapi.user.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {


    private final RegistrationFacade registrationFacade;


    public RegistrationController(final RegistrationFacade registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody final RegistrationRequest registrationRequest) {
        registrationFacade.registerNewUser(registrationRequest);
        return ResponseEntity.ok().build();
    }
}
