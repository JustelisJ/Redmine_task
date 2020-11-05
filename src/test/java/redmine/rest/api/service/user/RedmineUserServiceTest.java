package redmine.rest.api.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineUserServiceTest {

    @Mock
    UserService userService;
    Optional<Long> id;

    @BeforeEach
    void setUp() {
        id = Optional.of(1L);
    }

    @Test
    void findUserIdByName() {
        when(userService.findUserIdByName("Redmine Admin")).thenReturn(id);
        Optional<Long> id = userService.findUserIdByName("Redmine Admin");
        assertTrue(id.isPresent());
        assertEquals(1L, id.get());
    }

    @Test
    void dontFindUserIdByName() {
        when(userService.findUserIdByName("Redmine Admin")).thenReturn(id);
        Optional<Long> id = userService.findUserIdByName("admin admin");
        assertTrue(id.isEmpty());
    }
}