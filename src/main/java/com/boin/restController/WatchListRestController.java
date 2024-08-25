package com.boin.restController;

import java.util.List;

import com.boin.common.BaseResponseModel;

import com.boin.entity.DTO.DeleteStockFromWatchListDTO;
import com.boin.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.boin.entity.Stock;
import com.boin.entity.WatchList;
import com.boin.repository.StockRepository;
import com.boin.repository.WatchListRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "關注表Api",description = "關於關注表的功能")
@RequestMapping("/api/v1/watchlist")
@RestController
public class WatchListRestController {

	@Autowired
	private WatchListService watchListService;

	// 查詢會員關注股票
	@Operation(summary = "查詢會員關注股票")
	@GetMapping(path="/rawdata/{userId}",produces="application/json")
	public ResponseEntity<BaseResponseModel> getUserFollowedStock(@PathVariable("userId") String userId){
		return watchListService.getUserFollowedStock(userId, "追蹤表1");
	}

	// 根據userId 和 watchlistName 和 股票代碼 從關注表中刪除
	@Operation(summary = "根據userId 和 watchlistName 和 股票代碼 從關注表中刪除")
	@DeleteMapping(path="/rawdata/delete")
	public ResponseEntity<BaseResponseModel> deleteStockFromWatchList(@RequestBody DeleteStockFromWatchListDTO deleteStockFromWatchListDTO){
		System.out.println("id" + deleteStockFromWatchListDTO.getUserId());
		System.out.println("code" + deleteStockFromWatchListDTO.getStockCode());
		return watchListService.deleteStockFromWatchlist(deleteStockFromWatchListDTO.getUserId(), "追蹤表1", deleteStockFromWatchListDTO.getStockCode());
	}

}
