package com.boin.service;

import com.boin.common.BaseResponseModel;

import com.boin.entity.Stock;
import com.boin.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WatchListService {


	private final WatchListRepository watchListRepository;

	/* 	根據會員id和watchlistName查詢追蹤的股票資訊
	 *	author Boin
	 *	Date   2024/4/7
	 */
	public ResponseEntity<BaseResponseModel> getUserFollowedStock(String userId, String watchlistName){
		BaseResponseModel res = new BaseResponseModel();
		List<Stock> followedStocks = watchListRepository.getStockByUserIdAndWatchlistName(userId, watchlistName);
		if(Objects.isNull(followedStocks)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(followedStocks);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	/*
	 *  根據會員id,追蹤表名稱,股票代號 將股票從追蹤表中移除
	 *  @Date   2024/8/23
	 *  @Author Boin
	 */
	public ResponseEntity<BaseResponseModel> deleteStockFromWatchlist(String userId, String watchlistName, String stockCode){
		BaseResponseModel res = new BaseResponseModel();
		System.out.println("service1:" + userId);
		System.out.println("service2:" + watchlistName);
		System.out.println("service3:" + stockCode);
		Integer deleteStock = watchListRepository.deleteStockFromWatchList(userId, watchlistName, stockCode);
		if(Objects.isNull(deleteStock)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(deleteStock);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}




}
