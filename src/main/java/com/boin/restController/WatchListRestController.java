//package com.boin.restController;
//
//import java.util.List;
//
//import com.boin.entity.CustomUser;
//import com.boin.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.boin.entity.Stock;
//import com.boin.entity.WatchList;
//import com.boin.repository.StockRepository;
//import com.boin.repository.WatchListRepository;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//@Tag(name = "關注列表Api",description = "關於關注列表的功能")
//@RestController
//public class WatchListRestController {
//
//	@Autowired
//	private WatchListRepository watchListRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private StockRepository stockRepository;
//
//	// 查詢所有關注列表資訊
//	@Operation(summary = "查詢所有關注列表的資訊")
//	@GetMapping(path="/watchlistinfo/rawdata/all",produces="application/json")
//	public List<WatchList> allWatchList(){
//		List<WatchList> watchLists = watchListRepository.findAll();
//		return watchLists;
//	}
//
//	//根據username刪除關注列表中的股票
//	@Operation(summary = "根據username刪除關注列表中的股票")
//	@DeleteMapping(path="/watchlistinfo/rawdata/delete/{username}/{stockcode}")
//	public String deleteStockFromWatchList(@PathVariable("username")String username,@PathVariable("stockcode")String stockcode){
//		Stock stock = stockRepository.getByCode(stockcode);
//		CustomUser users = userRepository.getUserByUserName(username);
//		WatchList watchList = watchListRepository.getByUsersId(users.getId());
//		watchList.getStocks().remove(stock);
//		watchListRepository.save(watchList);
//		return "股票成功從追蹤清單中刪除";
//	}
//
//
//
//}
