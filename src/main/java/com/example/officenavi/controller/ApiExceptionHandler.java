package com.example.officenavi.controller;

import com.example.officenavi.exception.DuplicateSeatNameException;
import com.example.officenavi.exception.AuthenticationFailedException;
import com.example.officenavi.exception.ResourceNotFoundException;
import com.example.officenavi.exception.SeatAlreadyInUseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * API全体の例外を共通形式のレスポンスへ変換するハンドラーです。
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * ログイン失敗を処理します。
     *
     * @param ex 認証失敗例外
     * @return 401レスポンス
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationFailed(AuthenticationFailedException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", "AUTHENTICATION_FAILED");
        response.put("message", ex.getMessage());
        response.put("details", List.of());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * リクエストボディのバリデーション失敗を処理します。
     *
     * @param ex バリデーション例外
     * @return フィールドごとのエラー情報を含む400レスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toError)
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "validation error");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 一意制約違反（メールアドレス重複）を処理します。
     *
     * @param ex データ整合性違反例外
     * @return 競合エラー情報を含む409レスポンス
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", "DUPLICATE_EMAIL");
        response.put("message", "メールアドレスが既に登録されています");

        List<Map<String, Object>> details = List.of(Map.of(
                "field", "email",
                "reason", "既に使用されているメールアドレスです"));
        response.put("details", details);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * リソース未存在エラーを処理します。
     *
     * @param ex リソース未存在例外
     * @return 404レスポンス
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", ex.getCode());
        response.put("message", ex.getMessage());
        response.put("details", List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 座席利用中エラーを処理します。
     *
     * @param ex 座席利用中例外
     * @return 409レスポンス
     */
    @ExceptionHandler(SeatAlreadyInUseException.class)
    public ResponseEntity<Map<String, Object>> handleSeatAlreadyInUse(SeatAlreadyInUseException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", ex.getCode());
        response.put("message", ex.getMessage());
        response.put("details", List.of(Map.of(
                "field", "seatId",
                "reason", "既に他のユーザーが利用中です")));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * フィールドエラーをレスポンス用のMapへ変換します。
     * password は機微情報のため rejectedValue を返しません。
     *
     * @param fieldError フィールドエラー
     * @return レスポンス用エラー情報
     */
    private Map<String, Object> toError(FieldError fieldError) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());

        if (!"password".equalsIgnoreCase(fieldError.getField())) {
            Object rejectedValue = fieldError.getRejectedValue();
            error.put("rejectedValue", rejectedValue == null ? null : String.valueOf(rejectedValue));
        }

        return error;
    }

    /**
     * 座席名重複エラーを処理します。
     *
     * @param ex 座席名重複例外
     * @return 409レスポンス
     */
    @ExceptionHandler(DuplicateSeatNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSeatName(DuplicateSeatNameException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", "DUPLICATE_SEAT_NAME");
        response.put("message", ex.getMessage());
        response.put("details", List.of(Map.of(
                "field", "name",
                "reason", "既に使用されている座席名です")));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
