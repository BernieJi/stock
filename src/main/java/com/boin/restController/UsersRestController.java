package com.boin.restController;

import java.util.List;
import java.util.Objects;

import com.boin.common.BaseResponse;
import com.boin.common.BaseResponseModel;
import com.boin.entity.DTO.UserUpdateDTO;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api/v1/user")
public class UsersRestController {
	
	@Autowired
	private UserRepository userRepository;
	
	// 查詢所有用戶資訊
	@Operation(summary = "查詢所有用戶的資訊")
	@GetMapping(path="/rawdata/all", produces="application/json")
	public ResponseEntity<BaseResponseModel> getAllUsers(){
		var res = new BaseResponseModel();
		List<User> users = userRepository.getAllUsersInfo();
		if(Objects.isNull(users)){
			res.setFail("500","查詢使用者資料錯誤");
			return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(users);
		return new ResponseEntity<>(res,HttpStatus.OK);
		}
	
	// 根據userName查詢用戶資訊
	@Operation(summary = "根據userName查詢用戶資訊")
	@GetMapping(path="/rawdata/{username}",produces="application/json")
	public User userQueryByUserName(@PathVariable("username")String username){
		User user = userRepository.getUserByUserName(username);
		return user;
	}
	
	// 根據username修改用戶資料
	@Operation(summary = "根據username修改用戶資料")
	@PutMapping(path="/update/{username}")
	public ResponseEntity<BaseResponse> updateUser(@PathVariable(value="username") String username, @RequestBody UserUpdateDTO updateDTO){
		Integer update = userRepository.updateUserInfo(username, updateDTO.getEmail());
		if(Objects.isNull(update) || update <= 0){
			var res = BaseResponse.builder().code("500").message("更新資料錯誤").build();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			var res = BaseResponse.builder().code("200").message("更新資料成功").build();
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}
	
	// 刪除用戶
	@Operation(summary = "刪除使用者")
	@DeleteMapping("/usersinfo/rawdata/{usersid}/delete")
	public String delete(@PathVariable(value="usersid") Integer id){
		userRepository.deleteUserById(id);
		return "redirect:./";
		}
}

