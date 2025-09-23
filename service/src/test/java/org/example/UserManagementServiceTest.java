package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private UserClient userClient;

    private UserManagementService userService;

    @BeforeEach
    void setUp() {
        userService = new UserManagementService(userClient);
    }

    @Test
    void registerUser_ValidData_ReturnsUser() {
        UserDto mockUser = new UserDto(1L, "john", "john@example.com", UserStatus.PENDING);
        when(userClient.createUser("john", "john@example.com")).thenReturn(mockUser);

        UserDto result = userService.registerUser("john", "john@example.com");

        assertNotNull(result);
        assertEquals(UserStatus.ACTIVE, result.getStatus());
        assertEquals(1, userService.getRegisteredUsersCount());
    }

    @Test
    void activateUser_ValidUser_ReturnsTrue() {
        UserDto user = new UserDto(1L, "test", "test@example.com", UserStatus.PENDING);
        when(userClient.getUserById(1L)).thenReturn(user);
        when(userClient.validateUser(user)).thenReturn(true);

        boolean result = userService.activateUser(1L);

        assertTrue(result);
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    void getUser_CachedUser_ReturnsFromCache() {
        UserDto user = new UserDto(1L, "cached", "cached@example.com", UserStatus.ACTIVE);
        when(userClient.getUserById(1L)).thenReturn(user);

        // Первый вызов - загружает и кэширует
        UserDto firstCall = userService.getUser(1L);
        // Второй вызов - из кэша
        UserDto secondCall = userService.getUser(1L);

        assertSame(firstCall, secondCall);
        verify(userClient, times(1)).getUserById(1L);
    }
}