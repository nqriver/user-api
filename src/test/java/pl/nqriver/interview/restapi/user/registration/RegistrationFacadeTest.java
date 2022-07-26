package pl.nqriver.interview.restapi.user.registration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.nqriver.interview.restapi.common.TestDataUtils;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.UserAlreadyExistsException;
import pl.nqriver.interview.restapi.user.UserRepository;
import pl.nqriver.interview.restapi.user.registration.dto.RegistrationRequest;

import static org.assertj.core.api.Assertions.*;


@Transactional
class RegistrationFacadeTest extends AbstractTestcontainersIT {

    @Autowired
    private RegistrationFacade registrationFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestDataUtils testDataUtils;

    @Test
    void shouldRegisterNewUser_whenUsernameIsNotTaken() {
        // given
        String newUsername = "testUser";

        // when
        assertThatCode(() -> registrationFacade.registerNewUser(new RegistrationRequest(newUsername, "password")))
                .doesNotThrowAnyException();

        // then
        assertThat(userRepository.existsByName(newUsername)).isTrue();
    }


    @Test
    void shouldNotRegisterUser_whenUsernameIsAlreadyTaken() {
        // given

        String usernameToBeSaved = "testUser";
        testDataUtils.givenUserOfName(usernameToBeSaved);

        assertThat(userRepository.existsByName(usernameToBeSaved)).isTrue();

        // when then
        assertThatThrownBy(() -> registrationFacade.registerNewUser(new RegistrationRequest(usernameToBeSaved, "password")))
                .isInstanceOf(UserAlreadyExistsException.class);

    }
}