package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.dto.user.UserCreateDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.dto.user.UserPatchDto;
import org.mindswap.springtheknife.exceptions.user.UserAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.user.UserEmailAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.repository.UserRepository;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests {

    private static ObjectMapper objectMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        userRepository.deleteAll();
        userRepository.resetId();
    }

    @Test
    void contextLoads() {

    }


    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        Page<User> pageUsers = new PageImpl<>(users);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(pageUsers);

        List<UserGetDto> result = userService.getAllUsers(0, 3, "asc");

        assertEquals(users.size(), result.size());
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        long userId = 1L;
        User existingUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User result = userService.getUserById(userId);

        assertEquals(existingUser, result);

        verify(userRepository, times(1)).findById(userId);

        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void testDeleteUser() throws UserNotFoundException {
        long userId = 1L;
        User existingUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).deleteById(userId);

        assertDoesNotThrow(() -> userService.deleteUser(userId));
    }

    @Test
    void testUpdateUser() throws UserNotFoundException, UserAlreadyExistsException {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUserName("oldUsername");
        existingUser.setEmail("oldEmail@example.com");

        UserPatchDto userPatchDto = new UserPatchDto("newUsername", "password", "newEmail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserPatchDto result = userService.updateUser(userId, userPatchDto);

        assertEquals(userPatchDto.userName(), result.userName());
        assertEquals(userPatchDto.email(), result.email());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserThrowsUserNotFoundException() throws UserAlreadyExistsException {
        Long userId = 1L;
        UserPatchDto userPatchDto = new UserPatchDto("username", "password", "email@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userPatchDto));
    }

    @Test
    void testUpdateUserThrowsUserAlreadyExistsException() throws UserNotFoundException {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUserName("existingUsername");

        UserPatchDto userPatchDto = new UserPatchDto("existingUsername", "password", "email@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(userId, userPatchDto));
    }

    @Test
    void testCreateUser() throws UserAlreadyExistsException, UserEmailAlreadyExistsException {
        UserCreateDto userCreateDto = new UserCreateDto("username", "password", "email@example.com", "firstName", "lastName", LocalDate.now(), new HashSet<>());

        when(userRepository.findByUserName(userCreateDto.userName())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userCreateDto.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserGetDto result = userService.createUser(userCreateDto);

        assertEquals(userCreateDto.userName(), result.userName());

        verify(userRepository, times(1)).findByUserName(userCreateDto.userName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserThrowsUserAlreadyExistsException() {
        UserCreateDto userCreateDto = new UserCreateDto("existingUsername", "password", "email@example.com", "firstName", "lastName", LocalDate.now(), new HashSet<>());
        User existingUser = new User();
        existingUser.setUserName("existingUsername");

        when(userRepository.findByUserName(userCreateDto.userName())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userCreateDto));
    }

    @Test
    void testCreateUserThrowsUserEmailAlreadyExistsException() {
        UserCreateDto userCreateDto = new UserCreateDto("username", "password", "existingEmail@example.com", "firstName", "lastName", LocalDate.now(), new HashSet<>());
        User existingUser = new User();
        existingUser.setEmail("existingEmail@example.com");

        when(userRepository.findByEmail(userCreateDto.email())).thenReturn(Optional.of(existingUser));

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.createUser(userCreateDto));
    }
}
