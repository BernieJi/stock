package com.boin.restController;

import java.util.List;

import com.boin.entity.User;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("api/v1/user")
public class UsersRestController {
	
	@Autowired
	private UserRepository userRepository;
	
	// 查詢所有用戶資訊
	@Operation(summary = "查詢所有用戶的資訊")
	@GetMapping(path="/usersinfo/all",produces="application/json")
	public List<User> allUsers(){
		List<User> customUsers = userRepository.getAllUsersInfo();
		return customUsers;
		}
	
	// 根據usersName查詢用戶資訊
	@Operation(summary = "根據userName查詢用戶資訊")
	@GetMapping(path="/{username}",produces="application/json")
	public User usersQueryByUserName(@PathVariable("username")String username){
		User user = userRepository.getUserByUserName(username);
		return user;
	}
	
	// 根據username修改用戶資料
	@Operation(summary = "根據username修改用戶資料")
	@PutMapping(path="/usersinfo/rawdata/{usersname}/update")
	public String update(@PathVariable(value="usersname") String username){
		User userByUserName = userRepository.getUserByUserName(username);
		// TODO會員資料更動?
			
		// userRepository.updateUserInfo(users);
		return "update success!";
	}
	
	// 刪除用戶
	@Operation(summary = "刪除使用者")
	@DeleteMapping("/usersinfo/rawdata/{usersid}/delete")
	public String delete(@PathVariable(value="usersid") Integer id){
		userRepository.deleteUserById(id);
		return "redirect:./";
		}
}

