package com.boin.restController;

import java.util.List;
import java.util.Objects;

import com.boin.common.BaseResponseModel;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import com.boin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理員Api", description = "關於管理員的功能")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {

	private final AdminService adminService;
	
	// 列出所有用戶資訊
	@Operation(summary = "列出所有User資訊")
	@GetMapping("/user/rawdata/all")
	public ResponseEntity<BaseResponseModel> allUsers(){
		return adminService.getAllUserInfo();
	}
		
	// 根據username給予使用者"管理員"權限
	@Operation(summary = "根據username給予使用者admin權限")
	@PutMapping("/{username}/authority/give")
	public ResponseEntity<BaseResponseModel> authorizeAdmin(@PathVariable("username") String username){
		return adminService.authorizeUserAdmin(username);
	}
		
	// 根據username刪除使用者
	@Operation(summary = "根據username刪除使用者")
	@DeleteMapping("/{username}/delete")
	public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable("username") String username){
		return adminService.deleteUser(username);
	}
}
