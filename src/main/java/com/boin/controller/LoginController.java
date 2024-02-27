package com.boin.controller;

import com.boin.entity.CustomUser;
import com.boin.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.boin.entity.Message;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;
	
	// 登入畫面
	@GetMapping("/loginpage")
	public String loginpage() {
		return "loginpage";
	}

	// 登入
	@PostMapping("/login")
	public String login(@ModelAttribute CustomUser users, Model model) {
		model.addAttribute("users",users);
		// 獲取當前用戶的認證信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("使用者資訊:" + authentication.toString());
		return "index";
	}
	
	// 登入成功
	@PostMapping("/index")
	public String index(@ModelAttribute CustomUser users, Model model) {
		model.addAttribute("users",users);
		// 獲取當前用戶的認證信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("使用者資訊:" + authentication.toString());
		return "index";
	}

	// 登入失敗
	@RequestMapping("/fail")
	public String fail() {
		return "fail";
	}
	
	// 註冊頁面
	@PostMapping("/register")
	public ResponseEntity<Object> registerSuccesss(@Valid @ModelAttribute CustomUser users,BindingResult result,Model model) {
		ResponseEntity<Object> response = null;
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(users.getPassword());
			users.setPassword(encodedPassword);
			// 一般註冊會員authority設定為user
			users.setAuthority("user");
			userRepository.updateUserInfo(5,users.getUsername(), users.getPassword(), users.getEmail(), users.getAuthority().toString());
			Message msg = new Message();
			msg.setCode(200);
			msg.setMsg(String.format("此帳號:%s已成功註冊 請按上一頁登入帳號",users.getUsername()));
			response = new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (Exception e) {
			Message msg = new Message();
			msg.setCode(500);
			msg.setMsg(String.format("此帳號:%s已使用過 請按上一頁重新註冊帳號",users.getUsername()));
			response = new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
		}
		return response;
	}
		
}
