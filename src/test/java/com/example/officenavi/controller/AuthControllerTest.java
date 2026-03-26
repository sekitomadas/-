package com.example.officenavi.controller;

import com.example.officenavi.domain.auth.LoginResponse;
import com.example.officenavi.exception.AuthenticationFailedException;
import com.example.officenavi.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService))
                .setControllerAdvice(new ApiExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void login_shouldReturn200AndToken() throws Exception {
        when(authService.login(any())).thenReturn(new LoginResponse(
                "token-123",
                "Bearer",
                3600,
                1,
                "山田太郎"));

        String body = """
                {
                  "email": "taro@example.com",
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("token-123"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(3600))
                .andExpect(jsonPath("$.roleCode").value(1))
                .andExpect(jsonPath("$.userName").value("山田太郎"));
    }

    @Test
    void login_withMissingEmail_shouldReturn400ValidationError() throws Exception {
        String body = """
                {
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("email")));

        verifyNoInteractions(authService);
    }

    @Test
    void login_withMissingPassword_shouldReturn400ValidationError() throws Exception {
        String body = """
                {
                  "email": "taro@example.com"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("password")));

        verifyNoInteractions(authService);
    }

    @Test
    void login_withEmptyEmail_shouldReturn400ValidationError() throws Exception {
        String body = """
                {
                  "email": "",
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("email")));

        verifyNoInteractions(authService);
    }

    @Test
    void login_withInvalidEmailFormat_shouldReturn400ValidationError() throws Exception {
        String body = """
                {
                  "email": "invalid-email",
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("email")));

        verifyNoInteractions(authService);
    }

    @Test
    void login_withEmptyPassword_shouldReturn400ValidationError() throws Exception {
        String body = """
                {
                  "email": "taro@example.com",
                  "password": ""
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("password")));

        verifyNoInteractions(authService);
    }

    @Test
    void login_whenAuthenticationFailed_shouldReturn401() throws Exception {
        when(authService.login(any())).thenThrow(new AuthenticationFailedException("emailまたはpasswordが不正です"));

        String body = """
                {
                  "email": "taro@example.com",
                  "password": "wrong-pass"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTHENTICATION_FAILED"))
                .andExpect(jsonPath("$.message").value("emailまたはpasswordが不正です"));
    }
}