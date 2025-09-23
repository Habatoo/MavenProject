package org.example;

public interface UserService {
    UserDto createUser(String username, String email);
    UserDto getUserById(Long id);
    boolean validateUser(UserDto user);
}
