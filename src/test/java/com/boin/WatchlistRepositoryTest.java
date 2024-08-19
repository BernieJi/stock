package com.boin;

import com.boin.repository.StockRepository;
import com.boin.repository.WatchListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WatchlistRepositoryTest {

    @Autowired
    private WatchListRepository watchListRepository;

    @Test
    void addStockIntoWachlist(){
        Integer isSuccessful = watchListRepository.addStockToWatchList(1,"c5bbf3f2-0eb8-4a66-8094-a263146bd329", "2330");
        Assertions.assertNotNull(isSuccessful);
    }

    @Test
    void getStock(){
        var data = watchListRepository.getStockByWatchlistIdAndUserId(1,"c5bbf3f2-0eb8-4a66-8094-a263146bd329");
        System.out.println("data from watchlist is:"+ data);
        Assertions.assertNotNull(data);
    }


}