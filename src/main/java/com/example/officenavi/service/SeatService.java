package com.example.officenavi.service;

import com.example.officenavi.domain.seat.SeatEntity;
import com.example.officenavi.domain.seat.SeatResponse;
import com.example.officenavi.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 座席情報の業務ロジックを扱うサービスです。
 */
@Service
public class SeatService {

    private final SeatRepository seatRepository;

    /**
     * コンストラクタインジェクションでリポジトリを受け取ります。
     *
     * @param seatRepository 座席情報リポジトリ
     */
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * 座席一覧を取得し、レスポンス形式へ変換して返します。
     *
     * @return 座席一覧レスポンス
     */
    public List<SeatResponse> getSeats() {
        return seatRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * SeatEntity を SeatResponse に変換します。
     *
     * @param seatEntity 座席エンティティ
     * @return 座席レスポンス
     */
    private SeatResponse toResponse(SeatEntity seatEntity) {
        return new SeatResponse(
                seatEntity.getId(),
                seatEntity.getName(),
                seatEntity.getLocation()
        );
    }
}