package com.boin.restController;

import java.util.List;
import java.util.Objects;

import com.boin.common.BaseResponse;
import com.boin.common.BaseResponseModel;
import com.boin.entity.DTO.UserUpdateDTO;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import com.boin.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
@Tag(name = "會員api", description = "會員操作api")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	private UserService userService;
	
	// 查詢所有用戶資訊
	@Operation(summary = "查詢所有用戶的資訊")
	@GetMapping(path="/rawdata/all", produces="application/json")
	public ResponseEntity<BaseResponseModel> getAllUsers(){
		return userService.getAllUserInfo();
	}
	
	// 根據userName查詢用戶資訊
	@Operation(summary = "根據userName查詢用戶資訊")
	@GetMapping(path="/rawdata/{username}",produces="application/json")
	public ResponseEntity<BaseResponseModel> getUserByUsername(@PathVariable("username")String username){
		return userService.getUserByUsername(username);
	}

	// TODO 修改的東西add!!
	// 根據username修改用戶資料
	@Operation(summary = "根據username修改用戶資料")
	@PutMapping(path="/update/{username}")
	public ResponseEntity<BaseResponseModel> updateUser(@PathVariable(value="username") String username, @RequestBody UserUpdateDTO updateDTO){
		return userService.updateUserByUsername(username, updateDTO);
	}
	
	// 根據username刪除用戶
	@Operation(summary = "根據username刪除使用者")
	@DeleteMapping("/delete/{username}/")
	public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable(value="username") String username){
		return userService.deleteUser(username);
	}
}

