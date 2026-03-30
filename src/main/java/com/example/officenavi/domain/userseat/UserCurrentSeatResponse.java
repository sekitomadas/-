package com.example.officenavi.domain.userseat;

import java.time.LocalDateTime;

/**
 * 社員の現在位置取得APIレスポンスです。
 */
public class UserCurrentSeatResponse {

    private Integer userId;
    private String userName;
    private SeatInfo seat;
    private LocalDateTime since;

    public UserCurrentSeatResponse(Integer userId, String userName, SeatInfo seat, LocalDateTime since) {
        this.userId = userId;
        this.userName = userName;
        this.seat = seat;
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

    public SeatInfo getSeat() {
        return seat;
    }

    public void setSeat(SeatInfo seat) {
        this.seat = seat;
    }

    public LocalDateTime getSince() {
        return since;
    }

    public void setSince(LocalDateTime since) {
        this.since = since;
    }

    /**
     * 現在位置の座席情報です。
     */
    public static class SeatInfo {
        private Integer id;
        private String name;
        private String location;

        public SeatInfo(Integer id, String name, String location) {
            this.id = id;
            this.name = name;
            this.location = location;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}