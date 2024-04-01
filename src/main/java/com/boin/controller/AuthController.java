package com.boin.controller;

import com.boin.common.BaseResponse;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;

import com.boin.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;

	// 登入
	@PostMapping(path="/login", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
		return authenticationService.login(request);
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
		if (Objects.isNull(user)) { // 尚未有該帳號
			BaseResponse res = new BaseResponse("200","此帳號允許建立");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		BaseResponse res = new BaseResponse("500","該帳號已存在，請重新輸入！");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
