package com.boin.repository;

import com.boin.entity.JsonStock;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.boin.entity.Stock;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = (Logger) LogManager.getLogger();

    /*
     *  查詢前一個股票營業日
     *  Date   2024/4/7
     *  Author Boin
     */
    public String getLastStockDate(){
        final String sql = """
                SELECT DISTINCT(date) 
                FROM stock 
                ORDER BY date DESC 
                LIMIT 1;
                """;
        try {
            return jdbcTemplate.queryForObject(sql,String.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /*
     *  查詢台灣股票總數
     *  Date   2024/4/1
     *  Author Boin
     */
    public Integer getStockTotalCount(){
        final String sql = """
                SELECT count(DISTINCT(code)) FROM stock;
                """;
        try {
            return jdbcTemplate.queryForObject(sql,Integer.class);
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
    public List<Stock> getStockInfo(int currentPage , int pageSize){
        int startIndex = (currentPage - 1) * pageSize;
        final String sql = """
                SELECT * FROM stock 
                ORDER BY date DESC, code asc
                LIMIT ?,?
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), startIndex, startIndex + 100);
        } catch (EmptyResultDataAccessException ex){
            return null;
        } catch (Exception e) {
           logger.error(e.toString());
        }
        return null;
    }

    /*
     *
     *  儲存台灣股票資料
     *
     */
    public int[] batchInsertStock(List<JsonStock> stocks){
        final String sql = """
                INSERT INTO stock (date, code, name, opening_price, closing_price, highest_price, lowest_price
                                , `change`, trade_value, trade_volume, `transaction`) 
                        VALUES(DATE(NOW()), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE
                        date = VALUES(date),
                        name = VALUES(name),
                        opening_price = VALUES(opening_price),
                        closing_price = VALUES(closing_price),
                        highest_price = VALUES(highest_price),
                        lowest_price = VALUES(lowest_price),
                        `change` = VALUES(`change`),
                        trade_value = VALUES(trade_value),
                        trade_volume = VALUES(trade_volume),
                        `transaction` = VALUES(`transaction`); 
                """;
        try {
            return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    JsonStock jsonStock = stocks.get(i);
                    ps.setString(1,jsonStock.getCode());
                    ps.setString(2,jsonStock.getName());
                    ps.setString(3,jsonStock.getOpeningPrice());
                    ps.setString(4,jsonStock.getClosingPrice());
                    ps.setString(5,jsonStock.getHighestPrice());
                    ps.setString(6,jsonStock.getLowestPrice());
                    ps.setString(7,jsonStock.getChange());
                    ps.setString(8,jsonStock.getTradeValue());
                    ps.setString(9,jsonStock.getTradeVolume());
                    ps.setString(10,jsonStock.getTransaction());
                }

                @Override
                public int getBatchSize() {
                    return stocks.size();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *  根據股票代號查詢股票資料
     *  Author Boin
     *  Date 2024/4/7
     */
    public Stock getStockByCode(String code){
        final String sql = """
                SELECT * FROM stock 
                WHERE code = ? 
                ORDER BY date DESC 
                LIMIT 1;
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Stock.class),code);
        } catch (EmptyResultDataAccessException ex){
            return new Stock();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

}
