package com.boin.service;

import com.boin.common.BaseResponseModel;
import com.boin.entity.JsonStock;
import com.boin.entity.Stock;
import com.boin.entity.StockChartData;
import com.boin.entity.User;
import com.boin.repository.StockRepository;
import com.boin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final UserRepository userRepository;

	/*
	 * 查詢所有會員資訊
	 * @author Boin
	 * @Date   2024/7/19
	 *
	 */
	public ResponseEntity<BaseResponseModel> getAllUserInfo(){
		BaseResponseModel res = new BaseResponseModel();
		List<User> allUserInfo = userRepository.getAllUsersInfo();
		if(Objects.isNull(allUserInfo)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(allUserInfo);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	/*
	 * 根據username給予使用者"管理員"權限
	 * @author Boin
	 * @Date   2024/7/19
	 *
	 */
	public ResponseEntity<BaseResponseModel> authorizeUserAdmin(String username){
		BaseResponseModel res = new BaseResponseModel();
		User user = userRepository.getUserByUserName(username);
		if(Objects.isNull(user)){
			res.setFail("500","查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userRepository.updateUserAuthority(username,"admin");
		res.setSuccess("成功賦予使用者admin權限");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	/*
	 * 根據username刪除使用者
	 * @author Boin
	 * @Date   2024/7/19
	 *
	 */
	public ResponseEntity<BaseResponseModel> deleteUser(String username){
		BaseResponseModel res = new BaseResponseModel();
		User user = userRepository.getUserByUserName(username);
		if(Objects.isNull(user)){
			res.setFail("500","查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userRepository.deleteUserByName(username);
		res.setSuccess("刪除成功");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
