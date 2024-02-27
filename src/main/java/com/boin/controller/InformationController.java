package com.boin.controller;

import com.boin.entity.CustomUser;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InformationController {
	@Autowired
	private UserRepository userRepository;
		
	@RequestMapping("index/information")
	public String information(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		CustomUser users = userRepository.getUserByUserName(username);
		model.addAttribute("users",users);
		return "information";
	}
	
	@PutMapping("index/information/update/{usersid}")
	public String updateInformation(@PathVariable("usersid")Integer usersid,@RequestBody CustomUser users1) {
		CustomUser users = userRepository.getUserById(usersid);
		users.setUsername(users1.getUsername());
		users.setEmail(users1.getEmail());
		userRepository.updateUserInfo(usersid ,users.getUsername(), users.getPassword(), users.getEmail(), users.getAuthority().toString());
		return "更改成功";
	}
	

}
