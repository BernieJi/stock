package com.boin;

import com.boin.repository.StockRepository;
import com.boin.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    void findAllStockInfoTest() {
        var res = stockRepository.getAllStockInfo();
        System.out.println(res);
        Assertions.assertNotNull(res);
    }

    @Test
    void findStockByCode(){
        var res = stockRepository.getStockByCode("00878");
        System.out.println(res);
    }

}