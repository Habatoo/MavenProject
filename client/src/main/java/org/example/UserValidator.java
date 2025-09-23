package org.example;

public class UserValidator {
    public boolean isValidUsername(String username) {
        return username != null &&
                username.length() >= 3 &&
                username.length() <= 20 &&
                username.matches("^[a-zA-Z0-9_]+$");
    }

    public boolean isValidUser(UserDto user) {
        return user != null &&
                isValidUsername(user.getUsername()) &&
                user.getEmail() != null &&
                user.getEmail().contains("@");
    }
}
