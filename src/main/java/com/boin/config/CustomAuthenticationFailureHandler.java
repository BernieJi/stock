package com.boin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 添加日志来调试
        System.out.println("CustomAuthenticationFailureHandler 被调用");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        System.out.println("認證錯誤類型:" + exception);
        if (exception instanceof UsernameNotFoundException) {
            data.put("message", "找不到該用戶，去註冊吧");
        } else if (exception instanceof BadCredentialsException) {
            data.put("message", "密碼錯誤，請重新輸入密碼");
        } else {
            data.put("message", "Authentication failed");
        }
        data.put("error", exception.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
    }
}
