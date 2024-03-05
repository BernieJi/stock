package com.boin.controller;

import com.boin.common.BaseResponse;
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

import java.util.Objects;

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

	// 登入失敗
	@RequestMapping("/registerpage")
	public String registerpage() {
		return "z8 ";
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
	@PostMapping(path="/register",produces = "application/json")
	@ResponseBody
	public ResponseEntity<BaseResponse> register(@RequestBody CustomUser user) {
		//System.out.println("傳進來的用戶資訊:" + user);
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			// 一般註冊會員authority設定為user
            // user.setAuthority("user");
			userRepository.addUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAuthority().toString());
			BaseResponse res = new BaseResponse();
			res.setCode("200");
			res.setMessage(String.format("此帳號:%s已成功註冊 請按上一頁登入帳號",user.getUsername()));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResponse res = new BaseResponse();
			res.setCode("500");
			res.setMessage(String.format("此帳號:%s已使用過 請按上一頁重新註冊帳號",user.getUsername()));
			return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
		}


	}

	// 註冊頁面中查詢是否已有相同username
	@RequestMapping(path="/check")
	@ResponseBody
	public ResponseEntity<BaseResponse> check(String username) {
		System.out.println("有進來檢查函數，名稱為:" + username);
		CustomUser user = userRepository.getUserByUserName(username);
		if (Objects.isNull(user)) {
			BaseResponse res = new BaseResponse();
			res.setCode("200");
			res.setMessage("此帳號允許建立");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		BaseResponse res = new BaseResponse("500","該帳號已存在，請重新輸入！");
		return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
