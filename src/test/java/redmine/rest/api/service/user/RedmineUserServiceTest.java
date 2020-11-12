package redmine.rest.api.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import redmine.rest.api.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineUserServiceTest {

    private static final String USER_FULL_NAME = "Redmine Admin";
    private static final String OTHER_USER_FULL_NAME = "Person Person";

    @Mock
    UserService userService;
    Long userId;

    private void initializeMock() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeData() {
        userId = 1L;
    }

    @BeforeEach
    void setUp() {
        initializeMock();
        initializeData();
    }

    @Test
    void findUserIdByName() {
        when(userService.findUserIdByName(USER_FULL_NAME)).thenReturn(userId);
        Long id = userService.findUserIdByName(USER_FULL_NAME);
        assertEquals(userId, id);
    }

    @Test
    void dontFindUserIdByName() {
        when(userService.findUserIdByName(USER_FULL_NAME)).thenReturn(userId);
        when(userService.findUserIdByName(OTHER_USER_FULL_NAME)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserIdByName(OTHER_USER_FULL_NAME);
        });
    }
}