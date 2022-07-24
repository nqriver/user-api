package pl.nqriver.interview.restapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import pl.nqriver.interview.restapi.common.UserTestUtils;
import pl.nqriver.interview.restapi.user.AbstractTestcontainersIT;
import pl.nqriver.interview.restapi.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class CustomUserDetailsServiceTest extends AbstractTestcontainersIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void shouldLoadUserDetails_whenUserExists() {
        // given
        String name = "test";
        UserTestUtils.givenUserOfName(name, userRepository);

        assertThat(userRepository.existsByName(name)).isTrue();

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(name);

    }

    @Test
    void shouldThrowException_whenUserDoesNotExist() {
        // given
        String name = "test";

        assertThat(userRepository.findByName(name)).isNotPresent();

        // when
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(name))
                .isInstanceOf(UsernameNotFoundException.class);

    }
}