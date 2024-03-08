package com.boin.config;

import com.boin.handle.AuthenticationFailureHandlerImpl;
import com.boin.handle.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Autowired
	public UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// 關閉 csrf 防護
		http
				.csrf()
				.disable();

		// 授權認證
		String[] permitted = {"/loginpage","/login","/register","/check","/fail","/css/**","/images/**","/js/**","/usersinfo/**"};
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
				// 不需要被認證的頁面：/loginpage,/register與資源檔
				.requestMatchers(permitted).permitAll()
				// 權限判斷
				// 必須要有 admin權限才可以訪問
				//.requestMatchers("/adminpage").hasRole("admin")
				// 必須要有manager角色才可以訪問
				//.antMatchers("/managerpage").hasRole("manager")
				// 其他的都要被認證
				.anyRequest()
				.authenticated());

		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		// 表單提交
		http.formLogin()
				// loginpage.html 表單 action 內容
				//.loginProcessingUrl("/login")
				// 自定義的登入頁面
				.loginPage("/loginpage")
				// 登入成功後造訪的頁面
				//.successForwardUrl("/index")  // 只可以POST
				// 登入失敗後造訪的頁面
				.failureHandler(new AuthenticationFailureHandlerImpl())
				.permitAll();
				//.failureForwardUrl("/fail");

		// 登出設置
		http.logout()
				.deleteCookies("JESSIONID")
				.logoutSuccessUrl("/loginpage")
				// 可以使用任何的HTTP方法登出
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

		// 異常處理
		http.exceptionHandling()
				// 有兩種處理方式(二擇一）
				//.accessDeniedPage("/errorpage")
				.accessDeniedHandler(myAccessDeniedHandler);

		// remember-me
		http.rememberMe()
				.userDetailsService(userDetailsService)
				.tokenValiditySeconds(60 * 60 * 3); // 通常都會大於 session timeout的時間(30mins)

		return http.build();
	}

}
