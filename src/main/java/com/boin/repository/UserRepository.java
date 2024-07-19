package com.boin.repository;

import com.boin.entity.User;
import com.boin.log.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SlackService slackService;

    /*
    *
    *  查詢所有會員資料
    *
    */
    public List<User> getAllUsersInfo() {
        final String sql = """
                SELECT id, username, password, email, role, locked, enabled
                FROM user
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            slackService.sendMessage(String.format("%s error happens: %s", getClass().getName(), e.toString()));
        }
        return null;
    }

    /*
     *
     *  根據username查詢會員資料
     *
     */
    public User getUserByUserName(String username) {
        final String sql = """
                SELECT id, username, password, email, role, locked, enabled
                FROM user
                where username = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException ex){
            return null;
        } catch (Exception e) {
            slackService.sendMessage(String.format("%s error happens: %s", getClass().getName(), e.toString()));
        }
        return null;
    }

    /*
     *
     *  根據id查詢會員資料
     *
     */
    public User getUserById(Integer id) {
        final String sql = """
                SELECT id, username, password, email, role, locked, enabled
                FROM user
                where id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),id);
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
    public Integer addUser(User user) {
        final String sql = """
                INSERT into user (id, username, password, email, role) 
                VALUES (?, ?, ?, ?, ?) 
                """;
        try {
            return jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  修改會員的權限
     *
     */
    public Integer updateUserAuthority(String username, String role) {
        final String sql = """
                UPDATE user SET role = ? 
                WHERE username = ?
                """;
        try {
            return jdbcTemplate.update(sql, role, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  修改會員的信箱資料
     *
     */
    public Integer updateUserInfo(String username, String email) {
        final String sql = """
                UPDATE user SET email = ? 
                WHERE username = ?
                """;
        try {
            return jdbcTemplate.update(sql, email, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     *  開通會員
     *  @author Boin
     *  @Date   2024/06/12
     *
     */
    public Integer enableUser(String userId) {
        final String sql = """
                UPDATE user SET enabled = 1 
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.update(sql, userId);
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
    public Integer deleteUserByName(String username) {
        final String sql = """
                DELETE FROM user
                where username = ?
                """;
        try {
            return jdbcTemplate.update(sql, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
