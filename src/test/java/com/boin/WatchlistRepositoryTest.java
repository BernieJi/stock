package com.boin;

import com.boin.repository.StockRepository;
import com.boin.repository.WatchListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WatchlistRepositoryTest {

    @Autowired
    private WatchListRepository watchListRepository;

    @Test
    void defaultAddWachlist(){
        Integer isSuccessful = watchListRepository.defaultAddWatchList("c5bbf3f2-0eb8-4a66-8094-a263146bd329");
        Assertions.assertNotNull(isSuccessful);
    }

    @Test
    void addWachlist(){
        Integer isSuccessful = watchListRepository.addWatchList("c5bbf3f2-0eb8-4a66-8094-a263146bd329","testTwo");
        Assertions.assertNotNull(isSuccessful);
    }

    @Test
    void updateWatchList(){
        Integer isSuccessful = watchListRepository.updateWatchList("c5bbf3f2-0eb8-4a66-8094-a263146bd329","testTwo","testThree");
        Assertions.assertNotNull(isSuccessful);
    }

    @Test
    void getWatchList(){
        List res = watchListRepository.getWatchListByUserId("c5bbf3f2-0eb8-4a66-8094-a263146bd329");
        System.out.println(res);
    }

    @Test
    void getFollowedStock(){
        var data = watchListRepository.getStockByUserIdAndWatchlistName("c5bbf3f2-0eb8-4a66-8094-a263146bd329","追蹤表1");
        System.out.println("data from watchlist is:"+ data);
        Assertions.assertNotNull(data);
    }

    @Test
    void deleteStockFromWatchlist(){
        Integer isSuccessful = watchListRepository.deleteStockFromWatchList("c5bbf3f2-0eb8-4a66-8094-a263146bd329","追蹤表1","0056");
        System.out.println("num:" + isSuccessful);
        Assertions.assertNotNull(isSuccessful);
    }


}