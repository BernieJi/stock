package com.boin.restController;

import java.util.List;

import com.boin.entity.CustomUser;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "管理員Api",description = "關於管理員的功能")
@RestController
public class AdminRestController {
	private UserRepository userRepository;

	public AdminRestController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	// 列出所有用戶資訊
	@Operation(summary = "列出所有Users資訊")
	@GetMapping("/admininfo/rawdata/all")
	public List<CustomUser> allUsers(){
		List<CustomUser> usersz = userRepository.getAllUsersInfo();
		return usersz;
		}
		
	// 根據username給予使用者"管理員"權限
	@Operation(summary = "根據username給予使用者admin權限")
	@PutMapping("/admininfo/rawdata/{username}/giveauthority")
	public String authorizeAdmin(@PathVariable("username") String username){
		CustomUser users = userRepository.getUserByUserName(username);
		// users.setAuthority("users,admin");
		// userRepository.(users);
		return "授予權限成功";
		}
		
	// 根據username刪除使用者
	@Operation(summary = "根據username刪除使用者")
	@DeleteMapping("/admininfo/rawdata/{username}/deleteuser")
	public String deleteUser(@PathVariable("username") String username){
//		Users users = userRepository.getByUsername(username);
//		usersRepository.delete(users);
		return "User刪除成功";
		}
}
