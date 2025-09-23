package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
public class UserManagementService {
    private final UserClient userClient;
    private final Map<Long, UserDto> userCache = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserDto registerUser(String username, String email) {
        log.info("Registering new user: {}", username);

        UserDto user = userClient.createUser(username, email);
        user.setId(idGenerator.getAndIncrement());
        user.setStatus(UserStatus.ACTIVE);

        userCache.put(user.getId(), user);
        log.info("User registered successfully: {}", user.getId());

        return user;
    }

    public UserDto getUser(Long userId) {
        return userCache.computeIfAbsent(userId, userClient::getUserById);
    }

    public boolean activateUser(Long userId) {
        UserDto user = getUser(userId);
        if (user != null && userClient.validateUser(user)) {
            user.setStatus(UserStatus.ACTIVE);
            userCache.put(userId, user);
            log.info("User activated: {}", userId);
            return true;
        }
        return false;
    }

    public int getRegisteredUsersCount() {
        return userCache.size();
    }
}
