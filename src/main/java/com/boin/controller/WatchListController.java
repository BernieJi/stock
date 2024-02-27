//package com.boin.controller;
//
//import java.util.Set;
//
//import com.boin.entity.CustomUser;
//import com.boin.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.boin.entity.Stock;
//import com.boin.entity.WatchList;
//import com.boin.repository.WatchListRepository;
//import com.boin.service.StockService;
//
//@Controller
//public class WatchListController {
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private WatchListRepository watchListRepository;
//	@Autowired
//	private StockService stockService;
//
//	// 個股加入追蹤清單頁面
//	@GetMapping("/index/stock/watchlist/{stockcode}")
//	public String addWatchList(@PathVariable("stockcode") String stockcode) {
//		Stock stock = stockService.getByCode(stockcode);
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		CustomUser users = userRepository.getUserByUserName(username);
//		WatchList watchList = watchListRepository.getByUsersId(users.getId());
//		if(watchList != null) {
//			watchList.getStocks().add(stock);
//		}
//		else {
//		watchList = new WatchList();
//		// watchList.setUsers(users);
//		watchList.getStocks().add(stock);
//		}
//		watchListRepository.save(watchList);
//		return "redirect:../";
//	}
//
//	// 個人追蹤清單頁面
//	@GetMapping("/index/watchlist")
//	public String watchList(Model model) {
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		CustomUser users = userRepository.getUserByUserName(username);
//		WatchList watchList = watchListRepository.getByUsersId(users.getId());
//		Set<Stock> stocks = watchList.getStocks();
//		model.addAttribute("users",users);
//		model.addAttribute("stocks",stocks);
//		return "watchlist";
//	}
//
//	// 從個人追蹤清單頁面移除股票
//	@GetMapping("/index/watchlist/delete/{stockcode}")
//	public String watchList(@PathVariable("stockcode")String stockcode) {
//		Stock stock = stockService.getByCode(stockcode);
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		CustomUser users = userRepository.getUserByUserName(username);
//		WatchList watchList = watchListRepository.getByUsersId(users.getId());
//		watchList.getStocks().remove(stock);
//		watchListRepository.save(watchList);
//		return "redirect:../";
//	}
//
//}
