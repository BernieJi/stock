package com.boin.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boin.entity.CustomUser;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	//获取当前用户的方法，使用框架的上下文获取当前请求的用户
	public static Authentication getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1.查詢用戶是否存在 
		CustomUser customUser = userRepository.getUserByUserName(username);
		if(customUser == null)
			throw new UsernameNotFoundException("User Not found!");
		// 2.取得相關資料並進行密碼比對
		List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(customUser.getAuthority());
		return new User(username, customUser.getPassword(), auths);
	}
	
	
}
