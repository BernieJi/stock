package com.boin.repository;

import com.boin.entity.JsonStock;
import com.boin.entity.Stock;
import com.boin.entity.StockChartData;
import com.boin.entity.WatchList;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WatchListRepository {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = (Logger) LogManager.getLogger();

    /*
     *  會員註冊成功後自動加入預設一張追蹤列表
     *  @Date   2024/8/21
     *  @Author Boin
     */
    public Integer defaultAddWatchList(String userId){
        final String sql = """
                INSERT into watchlist(user_id, name)
                VALUES(?,'追蹤表1') 
                """;
        try {
            return jdbcTemplate.update(sql, userId);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  會員新增追蹤表
     *  @Date   2024/8/21
     *  @Author Boin
     */
    public Integer addWatchList(String userId, String name){
        final String sql = """
                INSERT into watchlist(user_id, name)
                VALUES( ?, ?) 
                """;
        try {
            return jdbcTemplate.update(sql, userId, name);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  根據userId和追蹤表name修改追蹤表
     *  @Date   2024/8/21
     *  @Author Boin
     */
    public Integer updateWatchList(String userId, String originName, String newName){
        final String sql = """
                UPDATE watchlist SET name = ?
                WHERE user_id = ? AND name = ?
                """;
        try {
            return jdbcTemplate.update(sql, newName, userId, originName);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  根據會員id查詢追蹤表
     *  @Date   2024/8/21
     *  @Author Boin
     */
    public List<WatchList> getWatchListByUserId(String userId){
        final String sql = """
                SELECT user_id, name 
                FROM watchlist
                WHERE user_id = ? 
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WatchList.class), userId);
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  根據會員id和watchlistName查詢追蹤的股票資訊
     *  @Date   2024/8/7
     *  @Author Boin
     */
    public List<Stock> getStockByUserIdAndWatchlistName(String userId, String watchlistName){
        final String sql = """
                SELECT s.* 
                FROM watchlist_stocks ws
                JOIN stock s ON ws.stock_code = s.code
                JOIN user u ON ws.user_id = u.id
                WHERE ws.watchlist_name = ? AND u.id= ?
                AND s.date = (SELECT MAX(date) FROM stock)
                ORDER BY s.date DESC
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), watchlistName, userId);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  根據會員id,追蹤表名稱,股票代號 將股票從追蹤表中移除
     *  @Date   2024/8/23
     *  @Author Boin
     */
    public Integer deleteStockFromWatchList(String userId, String watchlistName, String stockCode){
        final String sql = """
                DELETE FROM watchlist_stocks 
                WHERE user_id = ? AND watchlist_name = ? AND stock_code = ? 
                """;
        try {
            return jdbcTemplate.update(sql, userId, watchlistName, stockCode);
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

}
