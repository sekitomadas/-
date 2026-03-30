package com.example.officenavi.repository;

import com.example.officenavi.domain.userseat.UserCurrentSeatEntity;
import com.example.officenavi.domain.userseat.UserSeatEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 在席情報のデータアクセスを担当するリポジトリです。
 */
@Repository
public class UserSeatRepository {

    private static final RowMapper<UserCurrentSeatEntity> USER_CURRENT_SEAT_ROW_MAPPER = (rs,
            rowNum) -> new UserCurrentSeatEntity(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getInt("seat_id"),
                    rs.getString("seat_name"),
                    rs.getString("seat_location"),
                    rs.getTimestamp("since").toLocalDateTime());

    private static final RowMapper<UserSeatEntity> USER_SEAT_ROW_MAPPER = (rs, rowNum) -> new UserSeatEntity(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("seat_id"),
            rs.getTimestamp("start_time").toLocalDateTime());

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * コンストラクタインジェクションで JDBC テンプレートを受け取ります。
     *
     * @param jdbcTemplate JDBC操作を行うテンプレート
     */
    public UserSeatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * users テーブルに指定ユーザーが存在するかを判定します。
     *
     * @param userId ユーザーID
     * @return 存在する場合は true
     */
    public boolean existsUser(Integer userId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE id = :userId)";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        Boolean result = jdbcTemplate.queryForObject(sql, param, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * seats テーブルに指定座席が存在するかを判定します。
     *
     * @param seatId 座席ID
     * @return 存在する場合は true
     */
    public boolean existsSeat(Integer seatId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM seats WHERE id = :seatId)";
        SqlParameterSource param = new MapSqlParameterSource().addValue("seatId", seatId);
        Boolean result = jdbcTemplate.queryForObject(sql, param, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 指定座席が他ユーザーにより現在利用中かを判定します。
     *
     * @param seatId 座席ID
     * @param userId 登録対象ユーザーID
     * @return 他ユーザーが利用中の場合は true
     */
    public boolean isSeatInUseByAnotherUser(Integer seatId, Integer userId) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM user_seats
                    WHERE seat_id = :seatId
                      AND user_id <> :userId
                      AND end_time IS NULL
                )
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("seatId", seatId)
                .addValue("userId", userId);
        Boolean result = jdbcTemplate.queryForObject(sql, param, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 指定ユーザーの現在有効な在席情報をクローズします。
     *
     * @param userId ユーザーID
     */
    public void closeCurrentSeat(Integer userId) {
        String sql = """
                UPDATE user_seats
                SET end_time = NOW()
                WHERE user_id = :userId
                  AND end_time IS NULL
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        jdbcTemplate.update(sql, param);
    }

    /**
     * 指定ユーザーの現在有効な在席情報を1件クローズします。
     *
     * @param userId ユーザーID
     * @param leftAt 退席時刻
     * @return 更新件数
     */
    public int closeOneCurrentSeat(Integer userId, LocalDateTime leftAt) {
        String sql = """
                UPDATE user_seats
                SET end_time = :leftAt
                WHERE id = (
                    SELECT id
                    FROM user_seats
                    WHERE user_id = :userId
                      AND end_time IS NULL
                    ORDER BY start_time DESC
                    LIMIT 1
                )
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("leftAt", leftAt);
        return jdbcTemplate.update(sql, param);
    }

    /**
     * 指定ユーザーの現在位置を新規登録します。
     *
     * @param userId ユーザーID
     * @param seatId 座席ID
     * @return 登録後の在席情報
     */
    public UserSeatEntity registerCurrentSeat(Integer userId, Integer seatId) {
        String sql = """
                INSERT INTO user_seats (user_id, seat_id, start_time, end_time, created_at)
                VALUES (:userId, :seatId, NOW(), NULL, NOW())
                RETURNING id, user_id, seat_id, start_time
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("seatId", seatId);
        return jdbcTemplate.queryForObject(sql, param, USER_SEAT_ROW_MAPPER);
    }

    /**
     * 指定ユーザーの現在位置を取得します。
     *
     * @param userId ユーザーID
     * @return 現在位置情報（未在席の場合は空）
     */
    public Optional<UserCurrentSeatEntity> findCurrentSeatByUserId(Integer userId) {
        String sql = """
                SELECT
                    u.id AS user_id,
                    u.name AS user_name,
                    s.id AS seat_id,
                    s.name AS seat_name,
                    s.location AS seat_location,
                    us.start_time AS since
                FROM user_seats us
                INNER JOIN users u ON u.id = us.user_id
                INNER JOIN seats s ON s.id = us.seat_id
                WHERE us.user_id = :userId
                  AND us.end_time IS NULL
                ORDER BY us.start_time DESC
                LIMIT 1
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        return jdbcTemplate.query(sql, param, USER_CURRENT_SEAT_ROW_MAPPER).stream().findFirst();
    }

    /**
     * 現在在席中のユーザーを全件取得します。
     *
     * @return 現在位置情報一覧
     */
    public List<UserCurrentSeatEntity> findAllCurrentSeats() {
        String sql = """
                SELECT
                    u.id AS user_id,
                    u.name AS user_name,
                    s.id AS seat_id,
                    s.name AS seat_name,
                    s.location AS seat_location,
                    us.start_time AS since
                FROM user_seats us
                INNER JOIN users u ON u.id = us.user_id
                INNER JOIN seats s ON s.id = us.seat_id
                WHERE us.end_time IS NULL
                ORDER BY u.id ASC
                """;
        return jdbcTemplate.query(sql, USER_CURRENT_SEAT_ROW_MAPPER);
    }
}
