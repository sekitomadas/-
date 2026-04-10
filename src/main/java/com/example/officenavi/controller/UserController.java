package com.example.officenavi.controller;

import com.example.officenavi.domain.user.UserRegisterRequest;
import com.example.officenavi.domain.user.UserRegisterResponse;
import com.example.officenavi.domain.user.UserResponse;
import com.example.officenavi.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 従業員関連APIを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    /**
     * コンストラクタインジェクションでサービスを受け取ります。
     *
     * @param userService 従業員サービス
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 従業員一覧を取得します。
     *
     * @return 従業員一覧レスポンス
     */
    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    /**
     * 社員を登録します。
     * リクエストは {@code @Valid} により検証され、入力不正時は400を返します。
     *
     * @param request 社員登録リクエスト
     * @return 201 Created（登録された社員情報）
     */
    @PostMapping("/users")
    public ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
