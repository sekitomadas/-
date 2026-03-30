package com.example.officenavi.controller;

import com.example.officenavi.domain.seat.SeatRegisterRequest;
import com.example.officenavi.domain.seat.SeatResponse;
import com.example.officenavi.service.SeatService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 座席関連APIを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api")
public class SeatController {

    private final SeatService seatService;

    /**
     * コンストラクタインジェクションでサービスを受け取ります。
     *
     * @param seatService 座席サービス
     */
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    /**
     * 座席一覧を取得します。
     *
     * @return 座席一覧レスポンス
     */
    @GetMapping("/seats")
    public List<SeatResponse> getSeats() {
        return seatService.getSeats();
    }

    /**
     * 座席を追加します。
     *
     * @return 201 Created（追加された座席情報）
     */
    @PostMapping("/seats")
    public ResponseEntity<SeatResponse> addSeat(@Valid @RequestBody SeatRegisterRequest request) {
        SeatResponse addedSeat = seatService.addSeat(request);
        return ResponseEntity.status(201).body(addedSeat);
    }
}