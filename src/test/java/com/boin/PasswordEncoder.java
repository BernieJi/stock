package com.boin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//public class PasswordEncoder {
//	public static void main(String[] args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		
//		// 將特定資料編碼
//		String originalPassword = "1207";
//        String encodedPassword = encoder.encode(originalPassword);
//        System.out.printf("originalPassword: %s, encodedPassword: %s\n", originalPassword, encodedPassword);
//        
//        // 比對原始資料與編碼資料
//        String originalPasswordBefore = "1207";
//        String encodedPasswordAfter = "$2a$10$cjXn5eSYelPfrC8y7R2BUeTIEtTnKokJCfeKAEIOqa32/P2b1o0aq";
//        boolean matches = encoder.matches(originalPasswordBefore, encodedPasswordAfter);
//        System.out.println("比對結果: " + matches);
//	}
//}
