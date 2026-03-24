package com.example.officenavi.controller;

import com.example.officenavi.domain.userseat.UserCurrentSeatResponse;
import com.example.officenavi.domain.userseat.UserSeatLeaveResponse;
import com.example.officenavi.domain.userseat.UserSeatRegisterRequest;
import com.example.officenavi.domain.userseat.UserSeatRegisterResponse;
import com.example.officenavi.security.AuthenticatedUser;
import com.example.officenavi.service.UserSeatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在席情報関連APIを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api")
public class UserSeatController {

    private final UserSeatService userSeatService;

    /**
     * コンストラクタインジェクションでサービスを受け取ります。
     *
     * @param userSeatService 在席情報サービス
     */
    public UserSeatController(UserSeatService userSeatService) {
        this.userSeatService = userSeatService;
    }

    /**
     * 社員の現在位置を登録します。
     *
     * @param request 在席情報登録リクエスト
     * @return 201 Created（登録した在席情報）
     */
    @PostMapping("/user-seats")
    public ResponseEntity<UserSeatRegisterResponse> registerCurrentSeat(
            @Valid @RequestBody UserSeatRegisterRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UserSeatRegisterResponse response = userSeatService.registerCurrentSeat(
                authenticatedUser.userId(),
                request.getSeatId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 社員を退席状態に更新します。
     *
     * @return 200 OK（退席情報）
     */
    @PostMapping("/user-seats/leave")
    public ResponseEntity<UserSeatLeaveResponse> leaveCurrentSeat(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UserSeatLeaveResponse response = userSeatService.leaveCurrentSeat(authenticatedUser.userId());
        return ResponseEntity.ok(response);
    }

    /**
     * ログインユーザーの現在位置を取得します。
     *
     * @return 200 OK（現在位置情報）
     */
    @GetMapping("/users/me/current-seat")
    public ResponseEntity<UserCurrentSeatResponse> getCurrentSeat(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UserCurrentSeatResponse response = userSeatService.getCurrentSeat(authenticatedUser.userId());
        return ResponseEntity.ok(response);
    }
}
