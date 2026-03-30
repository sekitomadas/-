package com.example.officenavi.repository;

import com.example.officenavi.domain.user.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 従業員情報のデータアクセスを担当するリポジトリです。
 */
@Repository
public class UserRepository {

	private static final RowMapper<UserEntity> USER_ROW_MAPPER = (rs, rowNum) -> {
		UserEntity entity = new UserEntity(
				rs.getString("name"),
				rs.getString("email"));
		entity.setId(rs.getInt("id"));
		entity.setRoleCode(rs.getInt("role_code"));
		entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		entity.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
		return entity;
	};

	private static final RowMapper<UserEntity> AUTH_USER_ROW_MAPPER = (rs, rowNum) -> {
		UserEntity entity = new UserEntity(
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("password_hash"));
		entity.setId(rs.getInt("id"));
		entity.setRoleCode(rs.getInt("role_code"));
		entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		entity.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
		return entity;
	};

	private final NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * コンストラクタインジェクションで NamedParameterJdbcTemplate を受け取ります。
	 *
	 * @param jdbcTemplate JDBC操作を行うテンプレート
	 */
	public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * users テーブルから従業員一覧を取得します。
	 *
	 * @return 従業員エンティティ一覧
	 */
	public List<UserEntity> findAll() {
		String sql = """
				SELECT id, name, email, role_code, created_at, updated_at
				FROM users
				ORDER BY id ASC
				""";
		return jdbcTemplate.query(sql, USER_ROW_MAPPER);
	}

	/**
	 * users テーブルに従業員を登録します。
	 *
	 * @param user 登録する従業員エンティティ（passwordHash を含む）
	 * @return 登録後の従業員エンティティ
	 */
	public UserEntity registerUser(UserEntity user) {
		String sql = """
				INSERT INTO users (name, email, password_hash, role_code, created_at, updated_at)
				VALUES (:name, :email, :password, :roleCode, NOW(), NOW())
				RETURNING id, name, email, role_code, created_at, updated_at
				""";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", user.getName())
				.addValue("email", user.getEmail())
				.addValue("password", user.getPasswordHash())
				.addValue("roleCode", user.getRoleCode());
		return jdbcTemplate.queryForObject(sql, param, USER_ROW_MAPPER);
	}

	/**
	 * ログイン認証用途でemailをキーにユーザーを取得します。
	 *
	 * @param email メールアドレス
	 * @return ユーザー（存在しない場合はempty）
	 */
	public Optional<UserEntity> findByEmailForAuth(String email) {
		String sql = """
				SELECT id, name, email, password_hash, role_code, created_at, updated_at
				FROM users
				WHERE email = :email
				""";
		SqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
		List<UserEntity> users = jdbcTemplate.query(sql, params, AUTH_USER_ROW_MAPPER);
		if (users.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(users.getFirst());
	}
}
