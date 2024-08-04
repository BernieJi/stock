package com.boin.config;

import com.boin.common.BaseResponse;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import com.boin.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Tag(name= "上傳檔案api", description = "上傳檔案相關api")
@RestController
@RequiredArgsConstructor
public class StorageController {

	private final StorageService storageService;

	// 上傳檔案至GCP
	@Operation(summary = "登入api",description = "登入時使用api")
	@PostMapping(path="/upload", produces = "application/json")
	public ResponseEntity<BaseResponse> upload(MultipartFile file , String folder) throws IOException {
		return storageService.uploadFile(file, folder);
	}

}
