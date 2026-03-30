package com.example.officenavi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.officenavi.domain.user.UserEntity;
import com.example.officenavi.domain.user.UserRegisterRequest;
import com.example.officenavi.domain.user.UserRegisterResponse;
import com.example.officenavi.domain.user.UserResponse;
import com.example.officenavi.repository.UserRepository;

public class UserServiceTest {
    private static final String NAME = "test user";
    private static final String EMAIL = "testuser@mail";
    private static final String PASSWORD = "password";

    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    
    @BeforeEach
    void setup(){
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }
    
    @Test
    void getUsers_shouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(
            new UserEntity("mike", "mike@mail"),
            new UserEntity("john", "john@mail")
        ));

        List<UserResponse> users = userService.getUsers();

        assertEquals(2, users.size());
        assertEquals("mike", users.get(0).getName());
        assertEquals("mike@mail", users.get(0).getEmail());
        assertEquals("john", users.get(1).getName());
        assertEquals("john@mail", users.get(1).getEmail());
    }
    
    @Test
    void getUsers_shouldReturnEmptyListWhenNoUsers() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponse> users = userService.getUsers();

        assertEquals(0, users.size());
    }
    
    @Test
    void registerUser_shouldRegisterUser() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn("hashedPassword");
        
        UserRegisterRequest userRegisterRequest = createRegisterRequest();
        
        UserEntity createdUserEntity = new UserEntity(NAME, EMAIL, "hashedPassword");
        createdUserEntity.setId(1);
        when(userRepository.registerUser(any(UserEntity.class))).thenReturn(createdUserEntity);

        UserRegisterResponse registeredUser = userService.registerUser(userRegisterRequest);
        
        assertEquals(1, registeredUser.getId());
        assertEquals(NAME, registeredUser.getName());
        assertEquals(EMAIL, registeredUser.getEmail());
        verify(passwordEncoder).encode(PASSWORD);
    }

    @Test
    void registerUser_whenDuplicateEmail_shouldThrowDataIntegrityViolationException() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn("hashedPassword");
        
        UserRegisterRequest userRegisterRequest = createRegisterRequest();
        
        when(userRepository.registerUser(any(UserEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));

        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(userRegisterRequest));
    }

    private UserRegisterRequest createRegisterRequest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setName(NAME);
        request.setEmail(EMAIL);
        request.setPassword(PASSWORD);
        return request;
    }
}
