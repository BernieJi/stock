package com.boin.restController;

import java.util.List;

import com.boin.common.BaseResponseModel;
import com.boin.entity.DTO.AddStockToWatchListDTO;
import com.boin.repository.UserRepository;
import com.boin.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boin.entity.Stock;
//import com.boin.entity.WatchList;
import com.boin.repository.StockRepository;
//import com.boin.repository.WatchListRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "股票Api",description = "關於股票的功能")
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockRestController {
	private final StockService stockService;

	private final UserRepository userRepository;

	// 查詢最近一個股票營業日
	@Operation(summary = "查詢最近一個股票營業日")
	@GetMapping(path="/rawdata/date",produces="application/json")
	public ResponseEntity<BaseResponseModel> getLastStockDate(){
		return stockService.getLastStockDate();
	}

	// 查詢股票總筆數
	@Operation(summary = "查詢股票總筆數")
	@GetMapping(path="/rawdata/count",produces="application/json")
	public ResponseEntity<BaseResponseModel> getAllStockCount(){
		return stockService.getAllStockCount();
	}

	// 查詢最近一個營業日股票資訊
	@Operation(summary = "查詢股票資訊")
	@GetMapping(path="/rawdata",produces="application/json")
	public ResponseEntity<BaseResponseModel> getAllStocks(@RequestParam(value = "currentPage") int currentPage,@RequestParam(value = "pageSize") int pageSize){
		return stockService.getStocksInfo(currentPage, pageSize);
	}

	// 根據stockCode查詢股票資訊
	@Operation(summary = "根據stockCode查詢股票資訊")
	@GetMapping(path="/rawdata/{code}",produces="application/json")
	public ResponseEntity<BaseResponseModel> getStockByCode(@PathVariable("code")String code){
		return stockService.getStockByCode(code);
	}

	// 根據stockCode查詢股票歷史資訊
	@Operation(summary = "根據stockCode查詢股票資訊")
	@GetMapping(path="/rawdata/history/{code}",produces="application/json")
	public ResponseEntity<BaseResponseModel> getStockHistoryInfoByCode(@PathVariable("code")String code){
		return stockService.getStockHistoryInfoByCode(code);
	}

	/*
	* 	將此檔股票加入追蹤清單
	* 	@Author Boin
	* 	@Date   2024/8/22
	*
	*/
	@Operation(summary = "根據userid與追蹤清單名字將股票加入追蹤清單")
	@PostMapping("/addtowatchlist")
	public ResponseEntity<BaseResponseModel> addToWatchList(@RequestBody AddStockToWatchListDTO addStockToWatchListDTO) {
		return stockService.saveStockToWatchList(addStockToWatchListDTO.getUserId(),addStockToWatchListDTO.getWatchlistName(), addStockToWatchListDTO.getCode());
		}

}

