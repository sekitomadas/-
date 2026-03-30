package com.example.officenavi.controller;

import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatLeaveResponse;
import com.example.officenavi.domain.userseat.UserSeatRegisterResponse;
import com.example.officenavi.exception.ResourceNotFoundException;
import com.example.officenavi.exception.SeatAlreadyInUseException;
import com.example.officenavi.security.AuthenticatedUser;
import com.example.officenavi.service.UserSeatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
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
                                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                                .build();
        }

        @AfterEach
        void clearSecurityContext() {
                SecurityContextHolder.clearContext();
        }

        private RequestPostProcessor loginAs(Integer userId) {
                return request -> {
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                                        new AuthenticatedUser(userId, "user@example.com", 1), null, List.of()));
                        SecurityContextHolder.setContext(context);
                        return request;
                };
        }

        @Test
        void registerCurrentSeat_shouldReturn201() throws Exception {
                when(userSeatService.registerCurrentSeat(anyInt(), anyInt())).thenReturn(
                                new UserSeatRegisterResponse(100, 1, 10, LocalDateTime.of(2026, 3, 3, 10, 5, 0)));

                String body = """
                                {
                                  "seatId": 10
                                }
                                """;

                mockMvc.perform(post("/api/user-seats")
                                .with(loginAs(1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.userSeatId").value(100))
                                .andExpect(jsonPath("$.seatId").value(10));
        }

        @Test
        void leaveCurrentSeat_shouldReturn200() throws Exception {
                when(userSeatService.leaveCurrentSeat(anyInt())).thenReturn(
                                new UserSeatLeaveResponse(1, LocalDateTime.of(2026, 3, 3, 10, 20, 0)));

                mockMvc.perform(post("/api/user-seats/leave")
                                .with(loginAs(1)))
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

                mockMvc.perform(get("/api/users/me/current-seat")
                                .with(loginAs(1)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.userId").value(1))
                                .andExpect(jsonPath("$.seat.id").value(10));
        }

        @Test
        void registerCurrentSeat_whenSeatIdMissing_shouldReturn400() throws Exception {
                String body = "{}";

                mockMvc.perform(post("/api/user-seats")
                                .with(loginAs(1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("validation error"))
                                .andExpect(jsonPath("$.errors[0].field").value("seatId"));
        }

        @Test
        void getCurrentSeat_whenNotFound_shouldReturn404() throws Exception {
                when(userSeatService.getCurrentSeat(anyInt()))
                                .thenThrow(new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND",
                                                "対象ユーザーの現在位置が登録されていません"));

                mockMvc.perform(get("/api/users/me/current-seat")
                                .with(loginAs(1)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("CURRENT_SEAT_NOT_FOUND"));
        }

        @Test
        void getCurrentSeatByUserId_shouldReturn200() throws Exception {
                UserCurrentSeatResponse response = new UserCurrentSeatResponse(
                                5,
                                "佐藤花子",
                                new UserCurrentSeatResponse.SeatInfo(20, "B-20", "4F West"),
                                LocalDateTime.of(2026, 3, 24, 9, 0, 0));
                when(userSeatService.getCurrentSeat(5)).thenReturn(response);

                mockMvc.perform(get("/api/users/5/current-seat")
                                .with(loginAs(1)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.userId").value(5))
                                .andExpect(jsonPath("$.userName").value("佐藤花子"))
                                .andExpect(jsonPath("$.seat.id").value(20));
        }

        @Test
        void getCurrentSeatByUserId_whenNotFound_shouldReturn404() throws Exception {
                when(userSeatService.getCurrentSeat(404))
                                .thenThrow(new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND",
                                                "対象ユーザーの現在位置が登録されていません"));

                mockMvc.perform(get("/api/users/404/current-seat")
                                .with(loginAs(1)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("CURRENT_SEAT_NOT_FOUND"));
        }

        @Test
        void getAllCurrentSeats_shouldReturn200() throws Exception {
                when(userSeatService.getAllCurrentSeats()).thenReturn(List.of(
                                new UserCurrentSeatResponse(
                                                1,
                                                "山田太郎",
                                                new UserCurrentSeatResponse.SeatInfo(10, "A-01", "3F East"),
                                                LocalDateTime.of(2026, 3, 24, 9, 0, 0)),
                                new UserCurrentSeatResponse(
                                                2,
                                                "佐藤花子",
                                                new UserCurrentSeatResponse.SeatInfo(20, "B-10", "4F West"),
                                                LocalDateTime.of(2026, 3, 24, 9, 15, 0))));

                mockMvc.perform(get("/api/users/current-seats")
                                .with(loginAs(1)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].userId").value(1))
                                .andExpect(jsonPath("$[0].seat.id").value(10))
                                .andExpect(jsonPath("$[1].userId").value(2));
        }

        @Test
        void registerCurrentSeat_whenUserNotFound_shouldReturn404() throws Exception {
                when(userSeatService.registerCurrentSeat(anyInt(), anyInt()))
                                .thenThrow(new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません"));

                String body = """
                                {
                                  "seatId": 10
                                }
                                """;

                mockMvc.perform(post("/api/user-seats")
                                .with(loginAs(999))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
        }

        @Test
        void registerCurrentSeat_whenSeatNotFound_shouldReturn404() throws Exception {
                when(userSeatService.registerCurrentSeat(anyInt(), anyInt()))
                                .thenThrow(new ResourceNotFoundException("SEAT_NOT_FOUND", "指定されたseatIdは存在しません"));

                String body = """
                                {
                                  "seatId": 999
                                }
                                """;

                mockMvc.perform(post("/api/user-seats")
                                .with(loginAs(1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("SEAT_NOT_FOUND"));
        }

        @Test
        void registerCurrentSeat_whenSeatInUse_shouldReturn409() throws Exception {
                when(userSeatService.registerCurrentSeat(anyInt(), anyInt()))
                                .thenThrow(new SeatAlreadyInUseException("SEAT_ALREADY_IN_USE", "指定されたseatIdは既に利用中です"));

                String body = """
                                {
                                  "seatId": 10
                                }
                                """;

                mockMvc.perform(post("/api/user-seats")
                                .with(loginAs(1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isConflict())
                                .andExpect(jsonPath("$.code").value("SEAT_ALREADY_IN_USE"));
        }

        @Test
        void leaveCurrentSeat_whenUserNotFound_shouldReturn404() throws Exception {
                when(userSeatService.leaveCurrentSeat(anyInt()))
                                .thenThrow(new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません"));

                mockMvc.perform(post("/api/user-seats/leave")
                                .with(loginAs(999)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
        }

        @Test
        void leaveCurrentSeat_whenCurrentSeatNotFound_shouldReturn404() throws Exception {
                when(userSeatService.leaveCurrentSeat(anyInt()))
                                .thenThrow(new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND",
                                                "対象ユーザーの現在位置が登録されていません"));

                mockMvc.perform(post("/api/user-seats/leave")
                                .with(loginAs(1)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("CURRENT_SEAT_NOT_FOUND"));
        }
}
