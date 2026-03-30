package com.example.officenavi.domain.userseat;

import java.time.LocalDateTime;

/**
 * user_seats テーブルを表すエンティティです。
 */
public class UserSeatEntity {
    private Integer id;
    private Integer userId;
    private Integer seatId;
    private LocalDateTime startTime;

    public UserSeatEntity(Integer id, Integer userId, Integer seatId, LocalDateTime startTime) {
        this.id = id;
        this.userId = userId;
        this.seatId = seatId;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
