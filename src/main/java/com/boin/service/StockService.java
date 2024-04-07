package com.boin.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.boin.common.BaseResponseModel;
import com.boin.entity.JsonStock;
import com.boin.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.boin.entity.Stock;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	/* 查詢台灣最近的一個股票營業日
	 *	author Boin
	 *	Date   2024/4/7
	 */
	public ResponseEntity<BaseResponseModel> getLastStockDate(){
		BaseResponseModel res = new BaseResponseModel();
		String lastDate = stockRepository.getLastStockDate();
		if(Objects.isNull(lastDate)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 將日期轉換為需要的格式
		String parsedDate = lastDate.substring(0,11);
		res.setSuccess(parsedDate);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	/* 查詢台灣股票總筆數
	 *	author Boin
	 *	Date   2024/4/7
	 */
	public ResponseEntity<BaseResponseModel> getAllStockCount(){
		BaseResponseModel res = new BaseResponseModel();
		Integer stockCount= stockRepository.getStockTotalCount();
		if(Objects.isNull(stockCount)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(stockCount);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	// 查詢最近一個營業日的stock資訊
	public ResponseEntity<BaseResponseModel> getStocksInfo(int currentPage, int pageSize){
		BaseResponseModel res = new BaseResponseModel();
		List<Stock> stocks = stockRepository.getStockInfo(currentPage,pageSize);
		if(Objects.isNull(stocks)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(stocks);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	// 根據股票代號查詢stock資訊
	public ResponseEntity<BaseResponseModel> getStockByCode(String code){
		BaseResponseModel res = new BaseResponseModel();
		Stock stock = stockRepository.getStockByCode(code);
		if(Objects.isNull(stock)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(stock);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	// 儲存stock資訊至資料庫
	public int[] saveAllStock(List<JsonStock> stocks){
		return stockRepository.batchInsertStock(stocks);
	}


}
