package com.boin.service;

import com.boin.common.BaseResponse;
import com.boin.config.JwtService;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /*
       用戶註冊
     */
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request){
        try {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encodedPassword);
            userRepository.addUser(request.getUsername(), request.getPassword(), request.getEmail(), request.getRole());
            User user = userRepository.getUserByUserName(request.getUsername());
            String jwt = jwtService.generateToken(user);
            AuthenticationResponse res = AuthenticationResponse.builder().code("200").message("成功註冊").token(jwt).build();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            AuthenticationResponse res = AuthenticationResponse.builder().code("500").message("註冊失敗").token(null).build();
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    /*
       用戶登入
     */
    public ResponseEntity<AuthenticationResponse> login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        User user = userRepository.getUserByUserName(request.getUsername());
        if(Objects.isNull(user)){
            AuthenticationResponse res = AuthenticationResponse.builder().code("400").message("未找到此用戶").token("").build();
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
        String jwt = jwtService.generateToken(user);
        AuthenticationResponse res = AuthenticationResponse.builder().code("200").message("成功登入").token(jwt).build();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
