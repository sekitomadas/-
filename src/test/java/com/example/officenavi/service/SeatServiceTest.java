package com.example.officenavi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.officenavi.domain.seat.SeatEntity;
import com.example.officenavi.domain.seat.SeatResponse;
import com.example.officenavi.repository.SeatRepository;

public class SeatServiceTest {
    
    private SeatRepository seatRepository;
    private SeatService seatService;
    
    @BeforeEach
    void setUp() {
        seatRepository = mock(SeatRepository.class);
        seatService = new SeatService(seatRepository);
    }
    
    @Test
    void getSeats_shouldReturnListOfSeats() {
        when(seatRepository.findAll()).thenReturn(List.of(
            new SeatEntity(1, "A-01", "3F EAST"),
            new SeatEntity(2, "A-02", "3F WEST")
        ));

        List<SeatResponse> seats = seatService.getSeats();

        assertEquals(2, seats.size());
        assertEquals(1, seats.get(0).getId());
        assertEquals("A-01", seats.get(0).getName());
        assertEquals("3F EAST", seats.get(0).getLocation());
        assertEquals(2, seats.get(1).getId());
        assertEquals("A-02", seats.get(1).getName());
        assertEquals("3F WEST", seats.get(1).getLocation());
    }
    
    @Test
    void getSeats_shouldReturnEmptyListWhenNoSeats() {
        when(seatRepository.findAll()).thenReturn(List.of());

        List<SeatResponse> seats = seatService.getSeats();
        assertEquals(0, seats.size());
    }
}
