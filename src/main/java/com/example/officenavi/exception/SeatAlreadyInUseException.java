package com.example.officenavi.exception;

/**
 * 指定座席が既に利用中の場合の例外です。
 */
public class SeatAlreadyInUseException extends RuntimeException {
    private final String code;

    /**
     * 例外を生成します。
     *
     * @param code エラーコード
     * @param message エラーメッセージ
     */
    public SeatAlreadyInUseException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * エラーコードを返します。
     *
     * @return エラーコード
     */
    public String getCode() {
        return code;
    }
}