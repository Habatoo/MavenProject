package org.example;

import lombok.extern.slf4j.Slf4j;

import static org.example.UserStatus.ACTIVE;
import static org.example.UserStatus.PENDING;

@Slf4j
public class UserClient implements UserService {
    private final UserValidator validator;

    public UserClient(UserValidator validator) {
        this.validator = validator;
    }

    @Override
    public UserDto createUser(String username, String email) {
        log.info("Creating user: {}", username);

        if (!validator.isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username: " + username);
        }

        UserDto user = new UserDto();
        user.setUsername(username);
        user.setEmail(email);
        user.setStatus(PENDING);

        return user;
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        // Имитация получения пользователя
        return new UserDto(id, "user" + id, "user" + id + "@example.com", ACTIVE);
    }

    @Override
    public boolean validateUser(UserDto user) {
        return validator.isValidUser(user);
    }
}
