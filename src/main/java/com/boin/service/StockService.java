//package com.boin.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.boin.repository.StockRepository;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.boin.entity.Stock;
//
//@Service
//public class StockService {
//
//	@Autowired
//	private StockRepository stockRepository;
//
//	// 查詢所有stock資訊
//	public Iterable<Stock> list(){
//		return stockRepository.findAll();
//	}
//	// 儲存stock資訊
//	public Stock save(Stock stock) {
//		return stockRepository.save(stock);
//	}
//
//	public Iterable<Stock> save(List<Stock> stocks){
//		return stockRepository.saveAll(stocks);
//	}
//
//	// 根據Code查詢單檔股票
//	public Stock getByCode(String code) {
//		Optional<Stock> optStock = stockRepository.findAll().stream()
//				.filter(s->s.Code.equals(code))
//				.findFirst();
//		return optStock.isPresent()?optStock.get():null;
//	}
//
//
//}
