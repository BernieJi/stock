package com.boin.repository;

import com.boin.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    *
    *  查詢所有會員資料
    *
    */
    public List<CustomUser> getAllUsersInfo() {
        final String sql = """
                SELECT id, username, password, email, authority
                FROM user
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomUser.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  根據username查詢會員資料
     *
     */
    public CustomUser getUserByUserName(String username) {
        final String sql = """
                SELECT id, username, password, email, authority
                FROM user
                where username = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CustomUser.class),username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  根據id查詢會員資料
     *
     */
    public CustomUser getUserById(Integer id) {
        final String sql = """
                SELECT id, username, password, email, authority
                FROM user
                where id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CustomUser.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  新增會員資料
     *
     */
    public Integer addUser(String username, String password, String email, String authority) {
        final String sql = """
                INSERT into user (username, password, email, authority) 
                VALUES (?, ?, ?, ?) 
                """;
        try {
            return jdbcTemplate.update(sql, username, password, email, authority);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  修改會員資料
     *
     */
    public Integer updateUserInfo(Integer id, String username, String password, String email, String authority) {
        final String sql = """
                UPDATE user  SET username = ?, password = ?, email = ?, authority = ? 
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.update(sql, username, password, email, authority, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  刪除會員資料
     *
     */
    public Integer deleteUserById(Integer id) {
        final String sql = """
                DELETE FROM user
                where id = ?
                """;
        try {
            return jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
