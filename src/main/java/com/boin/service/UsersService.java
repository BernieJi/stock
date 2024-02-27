package com.boin.service;

import java.util.List;

import com.boin.entity.CustomUser;
import com.boin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
	
	@Autowired
	private UserRepository userRepository;
	
	// 查詢所有用戶
	public List<CustomUser> queryAll(){
		return userRepository.getAllUsersInfo();
	}
	
	// 新增用戶資料
	public Boolean saveUsers(CustomUser user) {
		userRepository.addUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAuthority().toString());
		return true;
	}
	
	// 修改用戶資料
	public Boolean updateUsers(Integer id,CustomUser users) {
		userRepository.updateUserInfo(id,users.getUsername(), users.getPassword(), users.getEmail(), users.getAuthority().toString());
		return true;
	}
	
	// 刪除用戶資料
	public Boolean deleteUsers(Integer id) {
		userRepository.deleteUserById(id);
		return true;
	}	
}
