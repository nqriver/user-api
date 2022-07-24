package pl.nqriver.interview.restapi.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {


    private final RegistrationFacade registrationFacade;


    public RegistrationController(RegistrationFacade registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest)
    {
        registrationFacade.registerNewUser(registrationRequest.username(), registrationRequest.password());
        return ResponseEntity.noContent().build();
    }
}
