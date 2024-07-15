package com.boin.controller;

import com.boin.common.BaseResponse;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;

import com.boin.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name= "會員權限相關api", description = "會員註冊登入相關api")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;

	// 登入
	@Operation(summary = "登入api",description = "登入時使用api")
	@PostMapping(path="/login", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
		return authenticationService.login(request);
	}
	
	// 註冊頁面
	@Operation(summary = "註冊api",description = "註冊時使用api")
	@PostMapping(path="/register", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return authenticationService.register(request);
	}

	// 註冊頁面中查詢是否已有相同username
	@Operation(summary = "註冊中查詢是否已有相同username的api",description = "註冊中查詢是否已有相同username的api")
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

	@Operation(summary = "帳號確認信api",description = "帳號確認信api")
	@GetMapping(path = "/confirm")
	public String confirm(@RequestParam("token") String token) {
		return authenticationService.confirmToken(token);
	}
}
