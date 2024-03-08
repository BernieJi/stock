package com.boin.controller;

import java.util.List;

import com.boin.entity.User;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	// 列出所有用戶資訊
	@RequestMapping("/index/adminpage")
	public String admin(@ModelAttribute User users, Model model){
		List<User> usersz = userRepository.getAllUsersInfo();
		model.addAttribute("_method","PUT");
		model.addAttribute("usersz",usersz);
		return "adminpage";
		}
	
	// 給予使用者"管理員"權限
	@RequestMapping("/index/adminpage/admin/{usersid}")
	public String authorizeAdmin(@PathVariable("usersid") Integer id){
//		Users users = usersRepository.getUserById(id);
//		// users.setAuthority("users,admin");
//		usersRepository.save(users);
		return "redirect:../";
		}
	
	// 刪除使用者
	@RequestMapping("/index/adminpage/delete")
	public String delete(@RequestParam(value="id") Integer id){
		userRepository.deleteUserById(id);
		return "redirect:./";
		}
}
