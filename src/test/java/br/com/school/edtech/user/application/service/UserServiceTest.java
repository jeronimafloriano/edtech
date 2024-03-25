package br.com.school.edtech.user.application.service;

import br.com.school.edtech.shared.model.exceptions.DuplicatedException;
import br.com.school.edtech.shared.model.exceptions.NotFoundException;
import br.com.school.edtech.shared.model.exceptions.RequiredArgumentException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("Jhon", "jhon", Email.of("jhon@email.com"), Role.STUDENT);
    }

    @DisplayName("Should search for a user by their username")
    @Test
    void testGetByUsername() {
        //given
        given(repository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        var result = userService.getByUsername(user.getUsername());

        //then
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail().getAddress(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
    }

    @DisplayName("Should return an exception when searching for a user that doesn't exist")
    @Test
    void testGetByUsername_NotFound() {
        //when
        Throwable exceptionUsernameNotFound = assertThrows(NotFoundException.class, () -> {
            userService.getByUsername("xpto");
        });
        //then
        assertEquals(ValidationMessage.USER_NOT_FOUND.getMessage(), exceptionUsernameNotFound.getMessage());
    }

    @DisplayName("Should return an exception when searching for a user with invalid data")
    @Test
    void testGetByUsername_invalidData() {
        //when
        Throwable exceptionUsernameNull = assertThrows(RequiredArgumentException.class, () -> {
            userService.getByUsername(null);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_USERNAME.getMessage(), exceptionUsernameNull.getMessage());
    }

    @DisplayName("Should create a user")
    @Test
    void testRegister() {
        //given
        UserDto userDto = new UserDto("Mary", "mary",
                "mary@email.com", Role.INSTRUCTOR);

        given(repository.findByUsername(userDto.getUsername())).willReturn(Optional.empty());
        given(repository.findByEmail(any())).willReturn(Optional.empty());

        //when
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        var result = userService.register(userDto);

        //then
        verify(repository, times(1)).save(captor.capture());
        User userCreated = captor.getValue();

        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getRole(), result.getRole());

        assertNotNull(userCreated.getId());
        assertEquals(userDto.getName(), userCreated.getName());
        assertEquals(userDto.getUsername(), userCreated.getUsername());
        assertEquals(userDto.getEmail(), userCreated.getEmail().getAddress());
        assertEquals(userDto.getRole(), userCreated.getRole());
    }

    @DisplayName("Should throw an exception when trying to create a user with an already registered username")
    @Test
    void testRegister_DuplicatedUsername() {
        //given
        UserDto userDto = new UserDto("Mary", "mary",
                "mary@email.com", Role.INSTRUCTOR);

        given(repository.findByUsername(userDto.getUsername())).willReturn(Optional.of(user));

        //when
        Throwable exceptionDuplicatedUsername = assertThrows(DuplicatedException.class, () -> {
            userService.register(userDto);
        });

        //then
        assertEquals(ValidationMessage.USERNAME_ALREADY_REGISTERED.getMessage(),
                exceptionDuplicatedUsername.getMessage());

        verify(repository, never()).save(any());
    }

    @DisplayName("Should throw an exception when trying to create a user with an already registered email")
    @Test
    void testRegister_DuplicatedEmail() {
        //given
        UserDto userDto = new UserDto("Mary", "mary",
                "mary@email.com", Role.INSTRUCTOR);

        given(repository.findByUsername(userDto.getUsername())).willReturn(Optional.empty());
        given(repository.findByEmail(any())).willReturn(Optional.of(user));

        //when
        Throwable exceptionDuplicatedEmail = assertThrows(DuplicatedException.class, () -> {
            userService.register(userDto);
        });

        //then
        assertEquals(ValidationMessage.EMAIL_ALREADY_REGISTERED.getMessage(),
                exceptionDuplicatedEmail.getMessage());

        verify(repository, never()).save(any());
    }
}
