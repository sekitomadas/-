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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.example.officenavi.domain.userseat.UserCurrentSeatEntity;
import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatEntity;
import com.example.officenavi.domain.userseat.UserSeatLeaveRequest;
import com.example.officenavi.domain.userseat.UserSeatRegisterRequest;
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
        when(userSeatRepository.registerCurrentSeat(USER_ID, SEAT_ID)).thenReturn(new UserSeatEntity(1, USER_ID, SEAT_ID, now));

        UserSeatRegisterResponse response = userSeatService.registerCurrentSeat(createRegisterRequest());

        assertEquals(USER_ID, response.getUserId());
        assertEquals(SEAT_ID, response.getSeatId());
        assertEquals(now, response.getStartTime());
        assertEquals(1, response.getUserSeatId());
    }
    
    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenUserNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.registerCurrentSeat(createRegisterRequest()));
    }
    
    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenSeatNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.registerCurrentSeat(createRegisterRequest()));
    }
    
    @Test
    void registerCurrentSeat_shouldThrowExceptionWhenSeatAlreadyInUse() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(true);
        when(userSeatRepository.isSeatInUseByAnotherUser(SEAT_ID, USER_ID)).thenReturn(true);

        assertThrows(SeatAlreadyInUseException.class, () -> userSeatService.registerCurrentSeat(createRegisterRequest()));
    }
    
    @Test
    void registerCurrentSeat_shouldCloseCurrentSeatAndRegisterNewSeat() {
        LocalDateTime now = LocalDateTime.now();
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.existsSeat(SEAT_ID)).thenReturn(true);
        when(userSeatRepository.isSeatInUseByAnotherUser(SEAT_ID, USER_ID)).thenReturn(false);
        when(userSeatRepository.registerCurrentSeat(USER_ID, SEAT_ID)).thenReturn(new UserSeatEntity(1, USER_ID, SEAT_ID, now));

        userSeatService.registerCurrentSeat(createRegisterRequest());
        
        InOrder inOrder = inOrder(userSeatRepository);
        inOrder.verify(userSeatRepository).closeCurrentSeat(USER_ID);
        inOrder.verify(userSeatRepository).registerCurrentSeat(USER_ID, SEAT_ID);
    }
    
    @Test
    void leaveCurrentSeat_shouldLeaveSeat() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class))).thenReturn(1);

        var response = userSeatService.leaveCurrentSeat(createLeaveRequest());

        assertEquals(USER_ID, response.getUserId());
        assertNotNull(response.getLeftAt());
        verify(userSeatRepository).closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class));
    }
    
    @Test
    void leaveCurrentSeat_shouldThrowExceptionWhenUserNotFound() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.leaveCurrentSeat(createLeaveRequest()));
    }
    
    @Test
    void leaveCurrentSeat_shouldThrowExceptionWhenNoCurrentSeat() {
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.closeOneCurrentSeat(eq(USER_ID), any(LocalDateTime.class))).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> userSeatService.leaveCurrentSeat(createLeaveRequest()));
    }
    
    @Test
    void getCurrentSeat_shouldReturnCurrentSeat() {
        LocalDateTime now = LocalDateTime.now();
        when(userSeatRepository.existsUser(USER_ID)).thenReturn(true);
        when(userSeatRepository.findCurrentSeatByUserId(USER_ID)).thenReturn(Optional.of(
                new UserCurrentSeatEntity(USER_ID, "Test User", SEAT_ID, "Test Seat", "Test Location", now)
        ));

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

    private UserSeatRegisterRequest createRegisterRequest() {
        UserSeatRegisterRequest request = new UserSeatRegisterRequest();
        request.setUserId(USER_ID);
        request.setSeatId(SEAT_ID);
        return request;
    }

    private UserSeatLeaveRequest createLeaveRequest() {
        UserSeatLeaveRequest request = new UserSeatLeaveRequest();
        request.setUserId(USER_ID);
        return request;
    }
}
