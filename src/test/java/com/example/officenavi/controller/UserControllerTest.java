package com.example.officenavi.controller;

import com.example.officenavi.domain.user.UserRegisterResponse;
import com.example.officenavi.domain.user.UserResponse;
import com.example.officenavi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;

        @BeforeEach
        void setUp() {
                userService = mock(UserService.class);

                LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
                validator.afterPropertiesSet();

                mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                        .setControllerAdvice(new ApiExceptionHandler())
                        .setValidator(validator)
                        .build();
        }

        @Test
        void getUsers_shouldReturn200AndUsers() throws Exception {
                when(userService.getUsers()).thenReturn(List.of(
                        new UserResponse(1, "山田太郎", "taro@example.com"),
                        new UserResponse(2, "佐藤花子", "hanako@example.com")
                ));

                mockMvc.perform(get("/api/users"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(1))
                        .andExpect(jsonPath("$[0].name").value("山田太郎"))
                        .andExpect(jsonPath("$[1].email").value("hanako@example.com"));
        }
    
        @Test
        void getUsers_whenServiceReturn_EmptyList_shouldReturn200AndEmptyArray() throws Exception {
                when(userService.getUsers()).thenReturn(List.of());
                
                mockMvc.perform(get("/api/users"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isArray())
                        .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        void registerUser_shouldReturn201AndCreatedUser() throws Exception {
        when(userService.registerUser(any())).thenReturn(
                new UserRegisterResponse(1, "山田太郎", "taro@example.com", LocalDateTime.of(2026, 3, 3, 10, 0, 0))
        );

        String body = """
                {
                  "name": "山田太郎",
                  "email": "taro@example.com",
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("taro@example.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "password1",
            "PASSWORD1",
            "Password",
            "Pass word1",
            "Pass1"
            }
                )
    void registerUser_whenInvalidPassword_shouldReturn400(String invalidPassword) throws Exception {
        String body = """
                {
                  "name": "山田太郎",
                  "email": "taro@example.com",
                  "password": "%s"
                }
                """.formatted(invalidPassword);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("password")));

        verifyNoInteractions(userService);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
            "山田 太郎",
            " 山田太郎",
            "山田太郎 ",
            "山田　太郎"
    })
    void registerUser_whenNameHasWhitespace_shouldReturn400(String invalidName) throws Exception {
        String body = """
                {
                  "name": "%s",
                  "email": "taro@example.com",
                  "password": "Passw0rd"
                }
                """.formatted(invalidName);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("name")));

        verifyNoInteractions(userService);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
            "taro @example.com",
            "taro@example .com",
            "taro@ example.com",
    })
    void registerUser_whenEmailInvalid_shouldReturn400(String invalidEmail) throws Exception {
        String body = """
                {
                  "name": "山田太郎",
                  "email": "%s",
                  "password": "Passw0rd"
                }
                """.formatted(invalidEmail);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems("email")));

        verifyNoInteractions(userService);
    }
    
    
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidRegisterRequestCases")
    void registerUser_whenInvalidRequest_shouldReturn400AndFields(
            String caseName,
            String name,
            String email,
            String password,
            List<String> expectedFields
    ) throws Exception {
        String body = """
                {
                  "name": "%s",
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(name, email, password);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[*].field", hasItems(expectedFields.toArray(new String[0]))));

        verifyNoInteractions(userService);
    }

    private static Stream<Arguments> invalidRegisterRequestCases() {
        return Stream.of(
                Arguments.of("name has whitespace", "山田 太郎", "taro@example.com", "Passw0rd", List.of("name")),
                Arguments.of("email format invalid", "山田太郎", "invalid", "Passw0rd", List.of("email")),
                Arguments.of("email has whitespace", "山田太郎", "taro @example.com", "Passw0rd", List.of("email")),
                Arguments.of("name and email invalid", "山田 太郎", "invalid", "Passw0rd", List.of("name", "email"))
        );
    }

    @Test
    void registerUser_whenDuplicateEmail_shouldReturn409() throws Exception {
        when(userService.registerUser(any())).thenThrow(new DataIntegrityViolationException("duplicate email"));

        String body = """
                {
                  "name": "山田太郎",
                  "email": "taro@example.com",
                  "password": "Passw0rd"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_EMAIL"));
    }
}
