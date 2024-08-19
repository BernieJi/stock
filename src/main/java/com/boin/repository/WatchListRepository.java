package com.boin.repository;

import com.boin.entity.JsonStock;
import com.boin.entity.Stock;
import com.boin.entity.StockChartData;
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
     *  根據股票代碼加入追蹤列表
     *  @Date   2024/8/7
     *  @Author Boin
     */
    public Integer addStockToWatchList(Integer watchlistId, String userId, String stockCode){
        final String sql = """
                INSERT into watchlist_stocks(watchlist_id, user_id, stock_code)
                VALUES(?,?,?) 
                """;
        try {
            return jdbcTemplate.update(sql, watchlistId, userId, stockCode);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  根據watchlistId和會員id查詢追蹤的股票資訊
     *  @Date   2024/8/7
     *  @Author Boin
     */
    public List<Stock> getStockByWatchlistIdAndUserId(Integer watchlistId, String userId){
        final String sql = """
                SELECT s.* 
                FROM watchlist_stocks ws
                JOIN stock s ON ws.stock_code = s.code
                JOIN user u ON ws.user_id = u.id
                WHERE ws.watchlist_id = ? AND u.id= ?
                AND s.date = (SELECT MAX(date) FROM stock)
                ORDER BY s.date DESC
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), watchlistId, userId);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  查詢台灣最近的一個營業日股票資料
     *  Author Boin
     *  Date   2024/4/7
     */
//    public List<Stock> getStockInfo(int currentPage , int pageSize){
//        int startIndex = (currentPage - 1) * pageSize;
//        final String sql = """
//                SELECT * FROM stock
//                ORDER BY date DESC, code asc
//                LIMIT ?,?
//                """;
//        try {
//            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), startIndex, startIndex + 100);
//        } catch (EmptyResultDataAccessException ex){
//            return null;
//        } catch (Exception e) {
//           logger.error(e.toString());
//        }
//        return null;
//    }

}
