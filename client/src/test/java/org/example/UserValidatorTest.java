package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.example.UserStatus.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"apple", "123", "hhh", "Rt_j125HYuj"})
    void isValidUsernameSuccessTest(String strings) {
        assertTrue(validator.isValidUsername(strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"gg", "", " ", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "%67_=+4"})
    void isValidUsernameFailTest(String strings) {
        assertFalse(validator.isValidUsername(strings));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isValidUsernameFail2Test(String argument) {
        assertFalse(validator.isValidUsername(argument));
    }

    @ParameterizedTest
    @MethodSource("dtoGoodProviderFactory")
    void isValidUserSuccessTest(UserDto dto) {
        assertTrue(validator.isValidUser(dto));
    }

    @ParameterizedTest
    @MethodSource("dtoBadProviderFactory")
    void isValidUserFailTest(UserDto dto) {
        assertFalse(validator.isValidUser(dto));
    }

    static Stream<UserDto> dtoGoodProviderFactory() {
        return Stream.of(
                new UserDto(1L, "Apple", "apple@mail.com", ACTIVE),
                new UserDto(2L, "Bob_N12", "bob@mail.com", INACTIVE),
                new UserDto(3L, "12345", "12345@mail.com", PENDING)
        );
    }

    static Stream<UserDto> dtoBadProviderFactory() {
        return Stream.of(
                new UserDto(4L, "", "apple@mail.com", ACTIVE),
                new UserDto(5L, "Bob_N12", "mail.com", INACTIVE),
                new UserDto(6L, "#$%^&*(()_", "12345@mail.com", PENDING),
                new UserDto(7L, "Bob_N12", null, PENDING),
                null
        );
    }
}