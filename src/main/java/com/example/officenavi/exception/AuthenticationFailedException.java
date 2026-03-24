package com.example.officenavi.exception;

/**
 * 認証失敗を表す例外です。
 */
public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
