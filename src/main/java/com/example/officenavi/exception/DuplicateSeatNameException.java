package com.example.officenavi.exception;

/**
 * 座席名重複時の例外です。
 */
public class DuplicateSeatNameException extends RuntimeException {
    public DuplicateSeatNameException(String message) {
        super(message);
    }
}