package com.boin.controller;

import com.boin.common.BaseResponse;
import com.boin.config.JwtService;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;

import com.boin.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationService authenticationService;
	
	// 登入畫面
	@GetMapping("/loginpage")
	public String loginpage() {
		return "loginpage";
	}

	// 登入
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
		System.out.println("收到的登入資訊為:" + request);
		User user = userRepository.getUserByUserName(request.getUsername());
		String jwt = jwtService.generateToken(user);
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	// 登入成功
	@PostMapping("/index")
	public String index(@ModelAttribute User users, Model model) {
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
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return authenticationService.register(request);
	}

	// 註冊頁面中查詢是否已有相同username
	@GetMapping(path="/check",produces = "application/json")
	@ResponseBody
	public ResponseEntity<BaseResponse> check(@RequestParam(value = "username") String username) {
		// System.out.println("有進來檢查函數，名稱為:" + username);
		User user = userRepository.getUserByUserName(username);
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
