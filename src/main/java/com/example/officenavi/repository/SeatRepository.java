package com.example.officenavi.repository;

import com.example.officenavi.domain.seat.SeatEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 座席情報のデータアクセスを担当するリポジトリです。
 */
@Repository
public class SeatRepository {

    private static final RowMapper<SeatEntity> SEAT_ROW_MAPPER = (rs, rowNum) -> new SeatEntity(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("location")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * コンストラクタインジェクションで NamedParameterJdbcTemplate を受け取ります。
     *
     * @param jdbcTemplate JDBC操作を行うテンプレート
     */
    public SeatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * seats テーブルから座席一覧を取得します。
     *
     * @return 座席エンティティ一覧
     */
    public List<SeatEntity> findAll() {
        String sql = """
                SELECT id, name, location
                FROM seats
                ORDER BY id ASC
                """;
        return jdbcTemplate.query(sql, SEAT_ROW_MAPPER);
    }
}