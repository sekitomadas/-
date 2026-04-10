package com.example.officenavi.domain.userseat;

import java.time.LocalDateTime;

/**
 * 社員の現在位置登録APIレスポンスです。
 */
public class UserSeatRegisterResponse {
    private Integer userSeatId;
    private Integer userId;
    private Integer seatId;
    private LocalDateTime startTime;

    public UserSeatRegisterResponse(Integer userSeatId, Integer userId, Integer seatId, LocalDateTime startTime) {
        this.userSeatId = userSeatId;
        this.userId = userId;
        this.seatId = seatId;
        this.startTime = startTime;
    }

    public Integer getUserSeatId() {
        return userSeatId;
    }

    public void setUserSeatId(Integer userSeatId) {
        this.userSeatId = userSeatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
