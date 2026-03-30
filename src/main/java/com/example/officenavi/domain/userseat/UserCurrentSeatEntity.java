package com.example.officenavi.domain.userseat;

import java.time.LocalDateTime;

/**
 * 社員の現在位置情報エンティティです。
 */
public class UserCurrentSeatEntity {

    private Integer userId;
    private String userName;
    private Integer seatId;
    private String seatName;
    private String seatLocation;
    private LocalDateTime since;

    public UserCurrentSeatEntity(
            Integer userId,
            String userName,
            Integer seatId,
            String seatName,
            String seatLocation,
            LocalDateTime since
    ) {
        this.userId = userId;
        this.userName = userName;
        this.seatId = seatId;
        this.seatName = seatName;
        this.seatLocation = seatLocation;
        this.since = since;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatLocation() {
        return seatLocation;
    }

    public void setSeatLocation(String seatLocation) {
        this.seatLocation = seatLocation;
    }

    public LocalDateTime getSince() {
        return since;
    }

    public void setSince(LocalDateTime since) {
        this.since = since;
    }
}