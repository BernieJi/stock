package com.boin.service;

import com.boin.common.BaseResponse;
import com.boin.common.BaseResponseModel;
import com.boin.config.StorageService;
import com.boin.entity.DTO.UserUpdateDTO;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final StorageService storageService;

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
	 * 根據userName查詢用戶資訊
	 * @author Boin
	 * @Date   2024/7/20
	 *
	 */
	public ResponseEntity<BaseResponseModel> getUserByUsername(String username){
		BaseResponseModel res = new BaseResponseModel();
		User user = userRepository.getUserByUserName(username);
		if(Objects.isNull(user)){
			res.setFail("500","內部查詢出現錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess(user);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	/*
	 * 根據userName修改用戶資訊
	 * @author Boin
	 * @Date   2024/7/20
	 *
	 */
	public ResponseEntity<BaseResponseModel> updateUserByUsername(String username, String email, String uploadFileUrl){
		BaseResponseModel res = new BaseResponseModel();
		// 先判斷用戶是否有更新大頭照
		if(!Strings.isBlank(uploadFileUrl)){
			String oldFileName = userRepository.getUserImage(username);
			// 先刪除舊的大頭照
			storageService.deleteFile("boin-bucket_1", oldFileName);
		}
		Integer update = userRepository.updateUserInfo(username, email, uploadFileUrl);
		if(Objects.isNull(update) || update <= 0){
			res.setFail("500","更新資料錯誤，請聯絡管理員");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.setSuccess("更新資料成功");
		return new ResponseEntity<>(res, HttpStatus.OK);
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
