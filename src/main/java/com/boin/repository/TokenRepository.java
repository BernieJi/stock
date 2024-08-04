package com.boin.repository;

import com.boin.entity.Authentication.ConfirmationToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TokenRepository {
    private JdbcTemplate jdbcTemplate;

    public TokenRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     *
     *  新增Token資料
     *  @author Boin
     *  @Date   2024/06/11
     */
    public Integer addToken(ConfirmationToken token) {
        final String sql = """
                INSERT into confirmation_token (token, user_id, createdAt, expiresAt) 
                VALUES (?, ?, ?, ?) 
                """;
        try {
            return jdbcTemplate.update(sql, token.getToken(), token.getUserId(), token.getCreatedAt(), token.getExpiresAt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  查詢Token資料
     *  @author Boin
     *  @Date   2024/06/11
     */
    public ConfirmationToken getToken(String token) {
        final String sql = """
                SELECT id, token, user_id, createdAt, expiresAt, confirmedAt 
                FROM confirmation_token 
                WHERE token = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ConfirmationToken.class), token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  修改確認時間
     *  @author Boin
     *  @Date   2024/06/12
     *
     */
    public Integer updateConfirmedAt(String token) {
        final String sql = """
                UPDATE confirmation_token SET confirmedAt = now()
                WHERE token = ?
                """;
        try {
            return jdbcTemplate.update(sql, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
