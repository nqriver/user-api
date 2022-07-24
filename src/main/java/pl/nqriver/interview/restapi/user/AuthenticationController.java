package pl.nqriver.interview.restapi.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final UserDetailsService userDetailsService;


    public AuthenticationController(PasswordEncoder passwordEncoder,
                                    JwtGenerator jwtGenerator,
                                    UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username());

        if (!passwordEncoder.matches(authenticationRequest.password(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        return jwtGenerator.generateTokenFor(userDetails).getTokenValue();
    }
}
