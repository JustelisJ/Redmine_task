package redmine.rest.api.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.config.RestTemplateConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(RestTemplateConfig.class)
class RedmineUserServiceTest {

    private static final String USER_FULL_NAME = "Redmine Admin";
    private static final String OTHER_USER_FULL_NAME = "Person Person";
    private static final long USER_ID = 1L;

    @Autowired
    RestTemplate restTemplate;

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new RedmineUserService(restTemplate);
    }

    @Test
    void findUserIdByName() {
        Optional<Long> id = userService.findUserIdByName(USER_FULL_NAME);
        assertTrue(id.isPresent());
        assertEquals(USER_ID, id.get());
    }

    @Test
    void dontFindUserIdByName() {
        Optional<Long> id = userService.findUserIdByName(OTHER_USER_FULL_NAME);
        assertTrue(id.isEmpty());
    }
}