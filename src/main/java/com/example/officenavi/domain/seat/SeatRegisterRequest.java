package com.example.officenavi.domain.seat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SeatRegisterRequest {
    @NotBlank(message = "座席名は必須です")
    @Pattern(regexp = "[A-Z]-\\d{1,3}", message = "座席名は英数字とスペースのみ使用できます")
    private String name;
    @NotBlank(message = "座席の場所は必須です")
    private String location;

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
