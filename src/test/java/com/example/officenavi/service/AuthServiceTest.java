package com.example.officenavi.service;

import com.example.officenavi.domain.auth.LoginRequest;
import com.example.officenavi.domain.auth.LoginResponse;
import com.example.officenavi.domain.user.UserEntity;
import com.example.officenavi.exception.AuthenticationFailedException;
import com.example.officenavi.repository.UserRepository;
import com.example.officenavi.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        authService = new AuthService(userRepository, passwordEncoder, jwtTokenProvider);
    }

    @Test
    void login_shouldReturnTokenResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("taro@example.com");
        request.setPassword("Passw0rd");

        UserEntity user = new UserEntity("山田太郎", "taro@example.com", "hashed");
        user.setId(1);
        user.setRoleCode(1);

        when(userRepository.findByEmailForAuth("taro@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("Passw0rd"), eq("hashed"))).thenReturn(true);
        when(jwtTokenProvider.generateToken(1, "taro@example.com", 1)).thenReturn("token-123");
        when(jwtTokenProvider.getExpirationSeconds()).thenReturn(3600L);

        LoginResponse response = authService.login(request);

        assertEquals("token-123", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(3600L, response.getExpiresIn());
        assertEquals(1, response.getRoleCode());
        assertEquals("山田太郎", response.getUserName());
    }

    @Test
    void login_whenUserNotFound_shouldThrowAuthenticationFailedException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("Passw0rd");

        when(userRepository.findByEmailForAuth("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(AuthenticationFailedException.class, () -> authService.login(request));
    }

    @Test
    void login_whenPasswordMismatch_shouldThrowAuthenticationFailedException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("taro@example.com");
        request.setPassword("wrong-pass");

        UserEntity user = new UserEntity("山田太郎", "taro@example.com", "hashed");
        user.setId(1);
        user.setRoleCode(1);

        when(userRepository.findByEmailForAuth("taro@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), eq("hashed"))).thenReturn(false);

        assertThrows(AuthenticationFailedException.class, () -> authService.login(request));
    }
}