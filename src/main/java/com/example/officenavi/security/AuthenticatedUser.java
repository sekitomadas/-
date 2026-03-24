package com.example.officenavi.security;

/**
 * 認証済みユーザー情報を保持するPrincipalです。
 */
public record AuthenticatedUser(Integer userId, String email, Integer roleCode) {
}
