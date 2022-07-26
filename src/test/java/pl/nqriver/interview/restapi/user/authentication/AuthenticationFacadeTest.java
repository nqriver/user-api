package pl.nqriver.interview.restapi.user.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.nqriver.interview.restapi.common.UserTestUtils;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.InvalidUsernameOrPasswordException;
import pl.nqriver.interview.restapi.user.UserRepository;
import pl.nqriver.interview.restapi.user.authentication.dto.AuthenticationRequest;
import pl.nqriver.interview.restapi.user.authentication.dto.JwtResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class AuthenticationFacadeTest extends AbstractTestcontainersIT {


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthenticationFacade authenticationFacade;


    @Test
    void shouldAuthenticateUser_whenPasswordMatches() {

        // given
        String name = "test";
        String password = "test";
        UserTestUtils.givenUserOfNameAndEncryptedPassword(name, password, passwordEncoder, userRepository);

        assertThat(userRepository.existsByName(name)).isTrue();
        AuthenticationRequest validRequest = new AuthenticationRequest(name, password);

        // when

        JwtResponse jwtResponse = authenticationFacade.authenticateUser(validRequest);


        // then
        assertThat(jwtResponse).isNotNull();
        assertThat(jwtResponse.jwt()).isNotBlank();
    }

    @Test
    void shouldFailAuthentication_whenPasswordDoesNotMatch() {

        // given
        String name = "test";
        String password = "test";
        UserTestUtils.givenUserOfNameAndEncryptedPassword(name, password, passwordEncoder, userRepository);

        assertThat(userRepository.existsByName(name)).isTrue();
        String invalidPassword = password.concat("asd");
        AuthenticationRequest invalidRequest = new AuthenticationRequest(name, invalidPassword);

        // when

        assertThatThrownBy(() -> authenticationFacade.authenticateUser(invalidRequest))
                .isInstanceOf(InvalidUsernameOrPasswordException.class);

    }


}