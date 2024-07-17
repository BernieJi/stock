package com.boin.restController;

import java.util.List;

import com.boin.entity.User;
import com.boin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理員Api",description = "關於管理員的功能")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {

	private final UserRepository userRepository;
	
	// 列出所有用戶資訊
	@Operation(summary = "列出所有User資訊")
	@GetMapping("/user/rawdata/all")
	public List<User> allUsers(){
		List<User> usersz = userRepository.getAllUsersInfo();
		return usersz;
		}
		
	// 根據username給予使用者"管理員"權限
	@Operation(summary = "根據username給予使用者admin權限")
	@PutMapping("/admininfo/rawdata/{username}/giveauthority")
	public String authorizeAdmin(@PathVariable("username") String username){
		User users = userRepository.getUserByUserName(username);
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
