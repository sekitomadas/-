package com.example.officenavi.service;

import com.example.officenavi.domain.seat.SeatEntity;
import com.example.officenavi.domain.seat.SeatRegisterRequest;
import com.example.officenavi.domain.seat.SeatResponse;
import com.example.officenavi.repository.SeatRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
                seatEntity.getLocation());
    }

    /**
     * 新しい座席を追加し、追加された座席情報をレスポンス形式で返します。
     *
     * @return 追加された座席レスポンス
     */
    public SeatResponse addSeat(SeatRegisterRequest request) {
        SeatEntity newSeat = new SeatEntity(null, request.getName(), request.getLocation());
        try {
            SeatEntity addedSeat = seatRepository.addSeat(newSeat);
            return toResponse(addedSeat);
        } catch (DataIntegrityViolationException e) {
            // 座席名重複の場合のみ独自例外に変換（他のケースは従来通り）
            if (e.getMessage() != null && e.getMessage().contains("seats_name_key")) {
                throw new com.example.officenavi.exception.DuplicateSeatNameException(
                        "座席名（" + request.getName() + "）が既に登録されています");
            }
            throw e;
        }
    }
}