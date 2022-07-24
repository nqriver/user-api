package pl.nqriver.interview.restapi.user.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationFacade {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    public AuthenticationFacade(final UserDetailsService userDetailsService,
                                final PasswordEncoder passwordEncoder,
                                final JwtGenerator jwtGenerator) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public JwtResponse authenticateUser(final AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username());

        if (!passwordEncoder.matches(authenticationRequest.password(), userDetails.getPassword())) {
            throw new AccessDeniedException("Invalid username or password");
        }

        String tokenValue = jwtGenerator.generateTokenFor(userDetails).getTokenValue();
        return new JwtResponse(tokenValue);
    }

}
