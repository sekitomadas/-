package com.example.officenavi.domain.auth;

/**
 * ログイン成功時のレスポンスです。
 */
public class LoginResponse {

    private final String accessToken;
    private final String tokenType;
    private final long expiresIn;
    private final Integer roleCode;

    public LoginResponse(String accessToken, String tokenType, long expiresIn, Integer roleCode) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.roleCode = roleCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public Integer getRoleCode() {
        return roleCode;
    }
}
