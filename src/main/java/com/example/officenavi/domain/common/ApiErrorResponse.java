package com.example.officenavi.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * API エラーレスポンスの統一フォーマットです。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private String code;
    private String message;
    private List<Object> details;

    /**
     * コンストラクタ
     *
     * @param code    エラーコード
     * @param message エラーメッセージ
     * @param details エラー詳細
     */
    public ApiErrorResponse(String code, String message, List<Object> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getDetails() {
        return details;
    }

    public void setDetails(List<Object> details) {
        this.details = details;
    }
}
