package com.example.officenavi.controller;

import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatLeaveResponse;
import com.example.officenavi.domain.userseat.UserSeatRegisterResponse;
import com.example.officenavi.exception.ResourceNotFoundException;
import com.example.officenavi.exception.SeatAlreadyInUseException;
import com.example.officenavi.service.UserSeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserSeatControllerTest {

    private MockMvc mockMvc;
    private UserSeatService userSeatService;

    @BeforeEach
    void setUp() {
        userSeatService = mock(UserSeatService.class);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new UserSeatController(userSeatService))
                .setControllerAdvice(new ApiExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void registerCurrentSeat_shouldReturn201() throws Exception {
        when(userSeatService.registerCurrentSeat(any())).thenReturn(
                new UserSeatRegisterResponse(100, 1, 10, LocalDateTime.of(2026, 3, 3, 10, 5, 0)));

        String body = """
                {
                  "userId": 1,
                  "seatId": 10
                }
                """;

        mockMvc.perform(post("/api/user-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userSeatId").value(100))
                .andExpect(jsonPath("$.seatId").value(10));
    }

    @Test
    void leaveCurrentSeat_shouldReturn200() throws Exception {
        when(userSeatService.leaveCurrentSeat(any())).thenReturn(
                new UserSeatLeaveResponse(1, LocalDateTime.of(2026, 3, 3, 10, 20, 0)));

        String body = """
                {
                  "userId": 1
                }
                """;

        mockMvc.perform(post("/api/user-seats/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void getCurrentSeat_shouldReturn200() throws Exception {
        UserCurrentSeatResponse response = new UserCurrentSeatResponse(
                1,
                "山田太郎",
                new UserCurrentSeatResponse.SeatInfo(10, "A-01", "3F East"),
                LocalDateTime.of(2026, 3, 3, 10, 5, 0));
        when(userSeatService.getCurrentSeat(1)).thenReturn(response);

        mockMvc.perform(get("/api/users/1/current-seat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.seat.id").value(10));
    }

    @Test
    void leaveCurrentSeat_whenUserIdMissing_shouldReturn400() throws Exception {
        String body = "{}";

        mockMvc.perform(post("/api/user-seats/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    void getCurrentSeat_whenNotFound_shouldReturn404() throws Exception {
        when(userSeatService.getCurrentSeat(999))
                .thenThrow(new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND",
                        "対象ユーザーの現在位置が登録されていません"));

        mockMvc.perform(get("/api/users/999/current-seat"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CURRENT_SEAT_NOT_FOUND"));
    }

    @Test
    void registerCurrentSeat_whenUserIdMissing_shouldReturn400() throws Exception {
        String body = """
                {
                  "seatId": 10
                }
                """;

        mockMvc.perform(post("/api/user-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    void registerCurrentSeat_whenUserNotFound_shouldReturn404() throws Exception {
        when(userSeatService.registerCurrentSeat(any()))
                .thenThrow(new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません"));

        String body = """
                {
                  "userId": 999,
                  "seatId": 10
                }
                """;

        mockMvc.perform(post("/api/user-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
    }

    @Test
    void registerCurrentSeat_whenSeatNotFound_shouldReturn404() throws Exception {
        when(userSeatService.registerCurrentSeat(any()))
                .thenThrow(new ResourceNotFoundException("SEAT_NOT_FOUND", "指定されたseatIdは存在しません"));

        String body = """
                {
                  "userId": 1,
                  "seatId": 999
                }
                """;

        mockMvc.perform(post("/api/user-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("SEAT_NOT_FOUND"));
    }

    @Test
    void registerCurrentSeat_whenSeatInUse_shouldReturn409() throws Exception {
        when(userSeatService.registerCurrentSeat(any()))
                .thenThrow(new SeatAlreadyInUseException("SEAT_ALREADY_IN_USE", "指定されたseatIdは既に利用中です"));

        String body = """
                {
                  "userId": 1,
                  "seatId": 10
                }
                """;

        mockMvc.perform(post("/api/user-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("SEAT_ALREADY_IN_USE"));
    }

    @Test
    void leaveCurrentSeat_whenUserNotFound_shouldReturn404() throws Exception {
        when(userSeatService.leaveCurrentSeat(any()))
                .thenThrow(new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません"));

        String body = """
                {
                  "userId": 999
                }
                """;

        mockMvc.perform(post("/api/user-seats/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
    }

    @Test
    void leaveCurrentSeat_whenCurrentSeatNotFound_shouldReturn404() throws Exception {
        when(userSeatService.leaveCurrentSeat(any()))
                .thenThrow(new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND",
                        "対象ユーザーの現在位置が登録されていません"));

        String body = """
                {
                  "userId": 1
                }
                """;

        mockMvc.perform(post("/api/user-seats/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CURRENT_SEAT_NOT_FOUND"));
    }
}
