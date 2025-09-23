package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientTest {

    @Mock
    private UserValidator validator;

    @InjectMocks
    private UserClient userClient;

    @Test
    void createUserValidUsernameReturnsUserTest() {
        when(validator.isValidUsername("john_doe")).thenReturn(true);

        UserDto user = userClient.createUser("john_doe", "john@example.com");

        assertNotNull(user);
        assertEquals("john_doe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals(UserStatus.PENDING, user.getStatus());
    }

    @Test
    void createUserInvalidUsernameThrowsExceptionTest() {
        when(validator.isValidUsername("ab")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> userClient.createUser("ab", "test@example.com"));
    }

    @Test
    void getUserByIdReturnsUserTest() {
        UserDto user = userClient.getUserById(1L);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertTrue(user.isActive());
    }

    @Test
    void validateUserDelegatesToValidatorTest() {
        UserDto user = new UserDto(1L, "test", "test@example.com", UserStatus.ACTIVE);
        when(validator.isValidUser(user)).thenReturn(true);

        boolean isValid = userClient.validateUser(user);

        assertTrue(isValid);
        verify(validator).isValidUser(user);
    }
}