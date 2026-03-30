package com.example.officenavi.exception;

/**
 * メールアドレスが既に使用されている場合の例外です。
 */
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
