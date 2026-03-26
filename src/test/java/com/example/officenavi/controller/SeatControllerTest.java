package com.example.officenavi.controller;

import com.example.officenavi.domain.seat.SeatResponse;
import com.example.officenavi.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SeatControllerTest {

    private MockMvc mockMvc;
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        seatService = mock(SeatService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SeatController(seatService)).build();
    }

    @Test
    void getSeats_shouldReturn200AndSeatList() throws Exception {
        when(seatService.getSeats()).thenReturn(List.of(
                new SeatResponse(10, "A-01", "3F North"),
                new SeatResponse(11, "A-02", "3F East")));

        mockMvc.perform(get("/api/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].name").value("A-01"))
                .andExpect(jsonPath("$[0].location").value("3F North"))
                .andExpect(jsonPath("$[1].location").value("3F East"));

    }

    @Test
    void getSeats_whenNoSeat_shouldReturn200AndEmptyArray() throws Exception {
        when(seatService.getSeats()).thenReturn(List.of());

        mockMvc.perform(get("/api/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
