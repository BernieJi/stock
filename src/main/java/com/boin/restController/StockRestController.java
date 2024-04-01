package com.boin.restController;

import java.util.List;

import com.boin.common.BaseResponseModel;
import com.boin.repository.UserRepository;
import com.boin.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boin.entity.Stock;
//import com.boin.entity.WatchList;
import com.boin.repository.StockRepository;
//import com.boin.repository.WatchListRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import yahoofinance.YahooFinance;

@Tag(name = "股票Api",description = "關於股票的功能")
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockRestController {

	private final StockService stockService;

	private final UserRepository userRepository;

	// private WatchListRepository watchListRepository;

	// 查詢股票筆數
	@GetMapping(path="/rawdata/count",produces="application/json")
	public ResponseEntity<BaseResponseModel> getAllStockCount(){
		return stockService.getAllStockCount();
	}

	// 查詢股票資訊
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

	YahooFinance y = new YahooFinance();


	// 將此檔股票加入追蹤清單
	// 個股加入追蹤清單頁面
//	@Operation(summary = "根據stockCode與username將股票加入追蹤清單")
//	@PostMapping("/stockinfo/rawdata/{stockcode}/{username}/addtowatchlist")
//	public String addToWatchList(@PathVariable("stockcode") String stockcode,@PathVariable("username")String username) {
//		Stock stock = stockRepository.getByCode(stockcode);
//		CustomUser users = userRepository.getUserByUserName(username);
//		WatchList watchList = watchListRepository.getByUsersId(users.getId());
//		if(watchList != null) {
//			watchList.getStocks().add(stock);
//		} else {
//			watchList = new WatchList();
//			// watchList.setUsers(users);
//			watchList.getStocks().add(stock);
//		}
//		watchListRepository.save(watchList);
//		return "加入追蹤成功";
//		}
//
//	// 刪除股票
//	@Operation(summary = "刪除股票")
//	@DeleteMapping("/stockinfo/rawdata/{stockcode}/delete")
//	public String delete(@PathVariable(value="stockcode") String stockcode){
//		Stock stock = stockRepository.getByCode(stockcode);
//		stockRepository.delete(stock);
//		return "redirect:./";
//		}
}

