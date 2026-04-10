package com.example.officenavi.domain.userseat;

import java.time.LocalDateTime;

/**
 * 社員の退席APIレスポンスです。
 */
public class UserSeatLeaveResponse {

    private Integer userId;
    private LocalDateTime leftAt;

    public UserSeatLeaveResponse(Integer userId, LocalDateTime leftAt) {
        this.userId = userId;
        this.leftAt = leftAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }
}