//package com.boin.controller;
//
//import java.util.List;
//
//import com.boin.entity.CustomUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.boin.entity.Stock;
//import com.boin.repository.StockRepository;
//import com.boin.service.StockService;
//
//@Controller
//public class StockController {
//	@Autowired
//	private StockRepository stockRepository;
//
//	@Autowired
//	private StockService stockService;
//
//	// 列出所有股票資訊
//	@GetMapping("/index/stock")
//	public String stockQuery(@ModelAttribute Stock stock, CustomUser users, Model model) {
//	List<Stock> stocks = stockRepository.findAll();
//	model.addAttribute("stocks",stocks);
//	model.addAttribute("users",users);
//	return "stock";
//	}
//
//	// 根據stockcode查詢單筆資料
//	@RequestMapping("/index/stock/info/{stockcode}")
//	public String stockInfo(@PathVariable("stockcode") String stockcode,Model model) {
//		Stock stock = stockRepository.getById(stockcode);
//		model.addAttribute("stock",stock);
//		return "stockInfo";
//	}
//
//}
