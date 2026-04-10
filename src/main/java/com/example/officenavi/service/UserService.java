package com.example.officenavi.service;

import com.example.officenavi.domain.user.UserEntity;
import com.example.officenavi.domain.user.UserRegisterRequest;
import com.example.officenavi.domain.user.UserRegisterResponse;
import com.example.officenavi.domain.user.UserResponse;
import com.example.officenavi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 従業員の業務ロジックを扱うサービスです。
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * コンストラクタインジェクションでリポジトリを受け取ります。
     *
     * @param userRepository 従業員リポジトリ
     * @param passwordEncoder パスワードエンコーダー
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 従業員一覧を取得し、レスポンス形式へ変換して返します。
     *
     * @return 従業員一覧レスポンス
     */
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * 社員を登録します。
     *
     * @param request 社員登録リクエスト
     * @return 社員登録レスポンス
     */
    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = new UserEntity(
                request.getName(),
                request.getEmail(),
                hashedPassword
        );

        UserEntity createdUser = userRepository.registerUser(user);

        return new UserRegisterResponse(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail(),
                createdUser.getCreatedAt()
        );
    }

    /**
     * UserEntityをUserResponseに変換します。
     *
     * @param userEntity 従業員エンティティ
     * @return 従業員レスポンス
     */
    private UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail()
        );
    }
    
}
