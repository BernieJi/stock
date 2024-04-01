package com.boin;

import com.boin.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    void getAllStockCount(){
        Integer totalCount = stockRepository.getStockTotalCount();
        System.out.println(totalCount);
    }
    @Test
    void findAllStockInfoTest() {
        var res = stockRepository.getStockInfo(1,100);
        System.out.println(res);
        Assertions.assertNotNull(res);
    }

    @Test
    void findStockByCode(){
        var res = stockRepository.getStockByCode("00878");
        System.out.println(res);
    }

}