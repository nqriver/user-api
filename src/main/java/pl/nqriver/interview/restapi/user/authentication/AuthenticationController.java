package pl.nqriver.interview.restapi.user.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.interview.restapi.user.authentication.dto.AuthenticationRequest;
import pl.nqriver.interview.restapi.user.authentication.dto.JwtResponse;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    public AuthenticationController(final AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }


    @PostMapping("login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationFacade.authenticateUser(authenticationRequest));
    }
}
