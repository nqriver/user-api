package pl.nqriver.interview.restapi.user.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pl.nqriver.interview.restapi.common.TestDataUtils;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.UserRepository;
import pl.nqriver.interview.restapi.user.authentication.dto.AuthenticationRequest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
class AuthenticationControllerTest extends AbstractTestcontainersIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestDataUtils testDataUtils;

    @Test
    public void shouldGenerateAuthenticationToken_whenUserExistsAndAuthenticatedSuccessfully() throws Exception {
        String savedUsername = "test";
        String savedUserPassword = "test";
        testDataUtils.givenUserOfNameAndEncryptedPassword(
                savedUsername, savedUserPassword);

        AuthenticationRequest request = new AuthenticationRequest(savedUsername, savedUserPassword);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/login").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").isNotEmpty());
    }

    @Test
    public void shouldNotAuthorize_whenUserDoesNotExist() throws Exception {
        String nonexistentUsername = "test1";
        String password = "test";

        AuthenticationRequest request = new AuthenticationRequest(nonexistentUsername, password);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/login").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotAuthorize_whenUserPassedWrongCredentials() throws Exception {
        String savedUsername = "test";
        String savedUserPassword = "test";
        String wrongPassword = savedUserPassword.concat("asdsd");

        testDataUtils.givenUserOfNameAndEncryptedPassword(savedUsername, savedUserPassword);

        AuthenticationRequest request = new AuthenticationRequest(savedUsername, wrongPassword);


        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/login").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}