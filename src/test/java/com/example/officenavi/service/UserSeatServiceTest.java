package com.example.officenavi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.example.officenavi.domain.userseat.UserCurrentSeatEntity;
import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatEntity;
import com.example.officenavi.domain.userseat.UserSeatLeaveResponse;
import com.example.officenavi.domain.userseat.UserSeatRegisterResponse;
import com.example.officenavi.exception.ResourceNotFoundException;
import com.example.officenavi.exception.SeatAlreadyInUseException;
import com.example.officenavi.repository.UserSeatRepository;

public class UserSeatServiceTest {
    private static final Integer USER_ID = 100;
    private static final Integer SEAT_ID = 200;

    private UserSeatRepository userSeatRepository;
    private UserSeatService userSeatService;

    @BeforeEach
    void setUp() {
        userSeatRepository = mock(UserSeatRepository.class);
        userSeatService = new UserSeatService(userSeatRepository);
    }

    @Test
    void registerCurrentSeat_shouldRegisterSeat() {
        LocalDateTime now = LocalDateTime.now();
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(true);
        when(userSeatRepository.isSeatInUseByAnotherUser(SEAT_ID, USER_ID)).thenReturn(false);
        when(userSeatRepository.registerCurrentSeat(USER_ID, SEAT_ID))
                .thenReturn(new UserSeatEntity(1, USER_ID, SEAT_ID, now));

        UserSeatRegisterResponse response = userSeatService.registerCurrentSeat(USER_ID, SEAT_ID);

        assertEquals(USER_ID, response.getUserId());
        assertEquals(SEAT_ID, response.getSeatId());
        assertEquals(now, response.getStartTime());
        assertEquals(1, response.getUserSeatId());
    }

    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenUserNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.registerCurrentSeat(USER_ID, SEAT_ID));
    }

    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenSeatNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.registerCurrentSeat(USER_ID, SEAT_ID));
    }

    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenSeatAlreadyInUse() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(true);
        when(userSeatRepository.isSeatInUseByAnotherUser(SEAT_ID, USER_ID)).thenReturn(true);

        assertThrows(SeatAlreadyInUseException.class, () -> userSeatService.registerCurrentSeat(USER_ID, SEAT_ID));
    }

    @Test
    void registerCurrentSeat_shouldCloseCurrentSeatAndRegisterNewSeat() {
        LocalDateTime now = LocalDateTime.now();
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(true);
        when(userSeatRepository.isSeatInUseByAnotherUser(SEAT_ID, USER_ID)).thenReturn(false);
        when(userSeatRepository.registerCurrentSeat(USER_ID, SEAT_ID))
                .thenReturn(new UserSeatEntity(1, USER_ID, SEAT_ID, now));

        userSeatService.registerCurrentSeat(USER_ID, SEAT_ID);

        InOrder inOrder = inOrder(userSeatRepository);
        inOrder.verify(userSeatRepository).closeCurrentSeat(USER_ID);
        inOrder.verify(userSeatRepository).registerCurrentSeat(USER_ID, SEAT_ID);
    }

    @Test
    void leaveCurrentSeat_shouldLeaveSeat() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class))).thenReturn(1);

        UserSeatLeaveResponse response = userSeatService.leaveCurrentSeat(USER_ID);

        assertEquals(USER_ID, response.getUserId());
        assertNotNull(response.getLeftAt());
        verify(userSeatRepository).closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class));
    }

    @Test
    void leaveCurrentSeat_shouldThrowExceptionWhenUserNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.leaveCurrentSeat(USER_ID));
    }

    @Test
    void leaveCurrentSeat_shouldThrowExceptionWhenNoCurrentSeat() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class))).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.leaveCurrentSeat(USER_ID));
    }

    @Test
    void getCurrentSeat_shouldReturnCurrentSeat() {
        LocalDateTime now = LocalDateTime.now();
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.findCurrentSeatByUserId(USER_ID)).thenReturn(Optional.of(
                new UserCurrentSeatEntity(USER_ID, "Test User", SEAT_ID, "Test Seat", "Test Location", now)));

        UserCurrentSeatResponse response = userSeatService.getCurrentSeat(USER_ID);

        assertEquals(USER_ID, response.getUserId());
        assertEquals("Test User", response.getUserName());
        assertEquals(SEAT_ID, response.getSeat().getId());
        assertEquals("Test Seat", response.getSeat().getName());
        assertEquals("Test Location", response.getSeat().getLocation());
        assertEquals(now, response.getSince());
    }

    @Test
    void getCurrentSeat_shouldThrowExceptionWhenUserNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.getCurrentSeat(USER_ID));
    }

    @Test
    void getCurrentSeat_shouldThrowExceptionWhenNoCurrentSeat() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.findCurrentSeatByUserId(USER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.getCurrentSeat(USER_ID));
    }

    @Test
    void getAllCurrentSeats_shouldReturnMappedResponses() {
        LocalDateTime since = LocalDateTime.now();
        when(userSeatRepository.findAllCurrentSeats()).thenReturn(List.of(
                new UserCurrentSeatEntity(1, "山田太郎", 10, "A-01", "3F East", since),
                new UserCurrentSeatEntity(2, "佐藤花子", 20, "B-10", "4F West", since)));

        List<UserCurrentSeatResponse> responses = userSeatService.getAllCurrentSeats();

        assertEquals(2, responses.size());
        assertEquals(1, responses.get(0).getUserId());
        assertEquals("山田太郎", responses.get(0).getUserName());
        assertEquals(10, responses.get(0).getSeat().getId());
        assertEquals("A-01", responses.get(0).getSeat().getName());
        assertEquals("3F East", responses.get(0).getSeat().getLocation());
    }

    @Test
    void getAllCurrentSeats_shouldReturnEmptyListWhenNoCurrentSeats() {
        when(userSeatRepository.findAllCurrentSeats()).thenReturn(List.of());

        List<UserCurrentSeatResponse> responses = userSeatService.getAllCurrentSeats();

        assertEquals(0, responses.size());
    }

}
