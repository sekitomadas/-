package com.example.officenavi.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * ログインAPIのリクエストです。
 */
public class LoginRequest {

    @NotBlank(message = "emailは必須です")
    @Email(message = "有効なemailを入力してください")
    private String email;

    @NotBlank(message = "passwordは必須です")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
