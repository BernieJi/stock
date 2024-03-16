package com.boin.repository;

import com.boin.entity.JsonStock;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.boin.entity.Stock;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final JdbcTemplate jdbcTemplate;

    /*
     *
     *  查詢所有台灣股票資料
     *
     */
    public List<Stock> getAllStockInfo(){
        final String sql = """
                SELECT * FROM stock
                """;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class));
        } catch (EmptyResultDataAccessException ex){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
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
                INSERT INTO stock (code, name, opening_price, closing_price, highest_price, lowest_price
                                , `change`, trade_value, trade_volume, `transaction`) 
                        VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE
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
     *
     *  根據股票代號查詢股票資料
     *
     */
    public Stock getStockByCode(String code){
        final String sql = """
                SELECT * FROM stock
                WHERE code = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Stock.class),code);
        } catch (EmptyResultDataAccessException ex){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
