package br.com.school.edtech.user.domain;

import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.RequiredArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private final String name = "Jhon";
    private final String username = "jhon";
    private final Email email = Email.of("jhon@email.com");

    @DisplayName("Should create user with all fields provided")
    @Test
    void testCreate(){
        //given
        User user = new User(name, username, email, Role.STUDENT);

        //then
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(Role.STUDENT, user.getRole());
    }

    @DisplayName("Should consider the same users with the same username and email address ")
    @Test
    void testCreateEqualUsers(){
        //given
        User user = new User(name, username, email, Role.STUDENT);
        User user2 = new User("Mary", username, email, Role.ADMIN);

        //then
        assertEquals(user2, user);
    }

    @DisplayName("Should throw exception when trying to create user with invalid fields")
    @Test
    void testCreateInvalidUser(){
        //when
        Throwable exceptionNullName = assertThrows(RequiredArgumentException.class, () -> {
            User user = new User(null, username, email, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_NAME.getMessage(), exceptionNullName.getMessage());

        //when
        Throwable exceptionBlankName = assertThrows(InvalidArgumentException.class, () -> {
            User user = new User("", username, email, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_NAME.getMessage(), exceptionBlankName.getMessage());

        //when
        Throwable exceptionNullUsername = assertThrows(RequiredArgumentException.class, () -> {
            User user = new User(name, null, email, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_USERNAME.getMessage(), exceptionNullUsername.getMessage());

        //when
        Throwable exceptionBlankUsername = assertThrows(InvalidArgumentException.class, () -> {
            User user = new User(name, "", email, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_USERNAME.getMessage(), exceptionBlankUsername.getMessage());

        //when
        Throwable exceptionNullEmail = assertThrows(RequiredArgumentException.class, () -> {
            User user = new User(name, username, null, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_EMAIL.getMessage(), exceptionNullEmail.getMessage());

        //when
        Throwable exceptionNullRole = assertThrows(RequiredArgumentException.class, () -> {
            User user = new User(name, username, email, null);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_ROLE.getMessage(), exceptionNullRole.getMessage());
    }

    @DisplayName("Should throw exception when trying to create user with invalid username format")
    @Test
    void testInvalidUsername(){
        //when
        Throwable inValidUsernameFormat = assertThrows(InvalidArgumentException.class, () -> {
            User user = new User(name, "12345", email, Role.STUDENT);;
        });
        //then
        assertEquals(ValidationMessage.INVALID_USERNAME.getMessage(), inValidUsernameFormat.getMessage());
    }
}
