package com.example.officenavi.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 社員登録APIのリクエストです。
 */
public class UserRegisterRequest {
    @NotBlank(message = "名前は必須です")
    @Size(max = 100, message = "名前は1~100文字以内で入力してください")
    @Pattern(regexp = "^(?!.*[\\s　]).+$", message = "名前に空白（半角/全角）は使用できません")
    private String name;

    @NotBlank(message = "メールアドレスは必須です")
    @Size(min = 8, max = 255, message = "メールアドレスは8~255文字以内で入力してください")
    @Email(message = "有効なメールアドレスを入力してください")
    @Pattern(regexp = "^(?!.*[\\s　]).+$", message = "メールアドレスに空白（半角/全角）は使用できません")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, max = 255, message = "パスワードは8~255文字以内で入力してください")
    @Pattern(regexp = "^(?!.*[\\s　])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは空白なしで、大文字・小文字・数字をそれぞれ1文字以上含めてください")
    private String password;

    public UserRegisterRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
