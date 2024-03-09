package com.boin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login2Controller {

    // 登入畫面
	@RequestMapping("/loginpage")
	public String loginpage() {
		return "loginpage";
	}

    // 登入成功
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

    // 登入失敗
	@RequestMapping("/fail")
	public String fail() {
		return "fail";
	}
}
