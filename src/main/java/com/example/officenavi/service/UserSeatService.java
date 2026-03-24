package com.example.officenavi.service;

import com.example.officenavi.domain.userseat.UserCurrentSeatEntity;
import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatEntity;
import com.example.officenavi.domain.userseat.UserSeatLeaveResponse;
import com.example.officenavi.domain.userseat.UserSeatRegisterResponse;
import com.example.officenavi.exception.ResourceNotFoundException;
import com.example.officenavi.exception.SeatAlreadyInUseException;
import com.example.officenavi.repository.UserSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 在席情報の業務ロジックを扱うサービスです。
 */
@Service
public class UserSeatService {

    private final UserSeatRepository userSeatRepository;

    /**
     * コンストラクタインジェクションでリポジトリを受け取ります。
     *
     * @param userSeatRepository 在席情報リポジトリ
     */
    public UserSeatService(UserSeatRepository userSeatRepository) {
        this.userSeatRepository = userSeatRepository;
    }

    /**
     * 社員の現在位置を登録します。
     * 存在チェック後、既存の有効在席情報をクローズして新規登録します。
     *
     * @param userId ユーザーID
     * @param seatId 座席ID
     * @return 在席情報登録レスポンス
     */
    @Transactional
    public UserSeatRegisterResponse registerCurrentSeat(Integer userId, Integer seatId) {
        if (!userSeatRepository.existsUser(userId)) {
            throw new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません");
        }

        if (!userSeatRepository.existsSeat(seatId)) {
            throw new ResourceNotFoundException("SEAT_NOT_FOUND", "指定されたseatIdは存在しません");
        }

        if (userSeatRepository.isSeatInUseByAnotherUser(seatId, userId)) {
            throw new SeatAlreadyInUseException("SEAT_ALREADY_IN_USE", "指定されたseatIdは既に利用中です");
        }

        userSeatRepository.closeCurrentSeat(userId);
        UserSeatEntity userSeatEntity = userSeatRepository.registerCurrentSeat(userId, seatId);

        return new UserSeatRegisterResponse(
                userSeatEntity.getId(),
                userSeatEntity.getUserId(),
                userSeatEntity.getSeatId(),
                userSeatEntity.getStartTime());
    }

    /**
     * 社員を退席状態に更新します。
     *
     * @param userId ユーザーID
     * @return 退席レスポンス
     */
    @Transactional
    public UserSeatLeaveResponse leaveCurrentSeat(Integer userId) {
        if (!userSeatRepository.existsUser(userId)) {
            throw new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません");
        }

        LocalDateTime leftAt = LocalDateTime.now();
        int updatedCount = userSeatRepository.closeOneCurrentSeat(userId, leftAt);
        if (updatedCount == 0) {
            throw new ResourceNotFoundException("CURRENT_SEAT_NOT_FOUND", "対象ユーザーの現在位置が登録されていません");
        }

        return new UserSeatLeaveResponse(userId, leftAt);
    }

    /**
     * 社員の現在位置を取得します。
     *
     * @param userId ユーザーID
     * @return 現在位置取得レスポンス
     */
    public UserCurrentSeatResponse getCurrentSeat(Integer userId) {
        if (!userSeatRepository.existsUser(userId)) {
            throw new ResourceNotFoundException("USER_NOT_FOUND", "指定されたuserIdは存在しません");
        }

        UserCurrentSeatEntity entity = userSeatRepository.findCurrentSeatByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CURRENT_SEAT_NOT_FOUND",
                        "対象ユーザーの現在位置が登録されていません"));

        return new UserCurrentSeatResponse(
                entity.getUserId(),
                entity.getUserName(),
                new UserCurrentSeatResponse.SeatInfo(
                        entity.getSeatId(),
                        entity.getSeatName(),
                        entity.getSeatLocation()),
                entity.getSince());
    }
}
