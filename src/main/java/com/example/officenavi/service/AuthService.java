package com.example.officenavi.service;

import com.example.officenavi.domain.auth.LoginRequest;
import com.example.officenavi.domain.auth.LoginResponse;
import com.example.officenavi.domain.user.UserEntity;
import com.example.officenavi.exception.AuthenticationFailedException;
import com.example.officenavi.repository.UserRepository;
import com.example.officenavi.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 認証関連の業務ロジックを扱います。
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmailForAuth(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("emailまたはpasswordが不正です"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationFailedException("emailまたはpasswordが不正です");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRoleCode());

        return new LoginResponse(
                accessToken,
                "Bearer",
                jwtTokenProvider.getExpirationSeconds(),
                user.getRoleCode(),
                user.getName());
    }
}
