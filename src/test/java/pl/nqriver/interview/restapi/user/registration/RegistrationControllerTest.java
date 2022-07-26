package pl.nqriver.interview.restapi.user.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pl.nqriver.interview.restapi.common.UserTestUtils;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.UserRepository;
import pl.nqriver.interview.restapi.user.registration.dto.RegistrationRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class RegistrationControllerTest extends AbstractTestcontainersIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldRegisterNewUser_whenUsernameIsNotTakenYetAndPasswordIsOfRequiredLength() throws Exception {
        String savedUsername = "test";
        String savedUserPassword = "test12345678";

        RegistrationRequest request = new RegistrationRequest(savedUsername, savedUserPassword);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/register").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        boolean saved = userRepository.existsByName(("test"));
        assertThat(saved).isTrue();
    }

    @Test
    public void shouldNotRegisterNewUser_whenPasswordIsNotOfRequiredLength() throws Exception {
        String username = "test";
        String password = "";

        RegistrationRequest request = new RegistrationRequest(username, password);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/register").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        boolean saved = userRepository.existsByName(("test"));
        assertThat(saved).isFalse();
    }

    @Test
    public void shouldNotRegisterNewUser_whenUsernameIsBlank() throws Exception {
        String username = "";
        String password = "validpassword";

        RegistrationRequest request = new RegistrationRequest(username, password);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/register").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        boolean saved = userRepository.existsByName(("test"));
        assertThat(saved).isFalse();
    }

    @Test
    public void shouldNotRegisterUser_whenUsernameIsTaken() throws Exception {
        String takenUsername = "test";
        String password = "testtestest";

        UserTestUtils.givenUserOfNameAndEncryptedPassword(
                takenUsername, password, passwordEncoder, userRepository);

        RegistrationRequest request = new RegistrationRequest(takenUsername, password);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/register").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}
