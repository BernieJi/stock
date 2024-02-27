package com.boin.restController;

import com.boin.entity.CustomUser;
import com.boin.entity.Message;
import com.boin.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class LoginRestController {
	
	@Autowired
	private UserRepository userRepository;
	
	// 註冊頁面
	@PostMapping("/register1")
	public ResponseEntity<Object> register(@Valid CustomUser user) {
		// 先檢查傳進來的東西是否為空
		if(Objects.isNull(user)){
			Message msg = new Message();
			msg.setCode(400);
			msg.setMsg("未輸入帳號資訊，請重新輸入！");
			return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
		}
		System.out.println("從前端傳進來的註冊資訊為:" + user);
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			// 一般註冊會員authority設定為user
			user.setAuthority("user");
			// 數據庫新增資料
			userRepository.addUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAuthority());
			Message msg = new Message();
			msg.setCode(200);
			msg.setMsg(String.format("此帳號:%s已成功註冊 請按上一頁登入帳號",user.getUsername()));
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (Exception e) {
			Message msg = new Message();
			msg.setCode(500);
			msg.setMsg(String.format("註冊帳號:%s時發生內部錯誤，請聯絡管理員",user.getUsername()));
			return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
		}
	}
		
}
