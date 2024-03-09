package com.boin.controller;

import com.boin.common.BaseResponse;
import com.boin.config.JwtService;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;

import com.boin.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;

	// 登入
	@PostMapping(path="/login", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
		return authenticationService.login(request);
	}
	
	// 首頁
	@GetMapping("/getIndex")
	public String index() {
		// 獲取當前用戶的認證信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("使用者資訊:" + authentication.toString());
		return "登入成功 進來首頁了";
	}
	
	// 註冊頁面
	@PostMapping(path="/register", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return authenticationService.register(request);
	}

	// 註冊頁面中查詢是否已有相同username
	@GetMapping(path="/check",produces = "application/json")
	public ResponseEntity<BaseResponse> check(@RequestParam(value = "username") String username) {
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
