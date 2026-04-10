package com.example.officenavi.exception;

/**
 * リソースが存在しない場合の例外です。
 */
public class ResourceNotFoundException extends RuntimeException {
    private final String code;

    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
