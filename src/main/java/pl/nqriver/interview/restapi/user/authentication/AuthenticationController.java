package pl.nqriver.interview.restapi.user.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    public AuthenticationController(final AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }


    @GetMapping("login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationFacade.authenticateUser(authenticationRequest));
    }
}
