package com.boin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
public class SecurityConfig {


	private final JwtAuthenticationFilter jwtAuthFilter;

	public final UserDetailsService userDetailsService;

	private final AuthenticationProvider authenticationProvider;

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// White Lists
		String[] permitted = {"/api/v1/auth/**","/loginpage","/fail","/index","/html/**","/css/**","/js/**","/images/**"};
		http
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers(permitted)
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
				.logout()
				.logoutSuccessUrl("/loginpage")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.rememberMe()
				.userDetailsService(userDetailsService)
				.tokenValiditySeconds(60 * 60 * 3);

		return http.build();
	}
}

// 表單提交
//http.formLogin()
// loginpage.html 表單 action 內容
//.loginProcessingUrl("/login")
// 自定義的登入頁面
// .loginPage("/loginpage")
// 登入成功後造訪的頁面
//.successForwardUrl("/index")  // 只可以POST
// 登入失敗後造訪的頁面
//.failureHandler(new AuthenticationFailureHandlerImpl())
//.permitAll();
//.failureForwardUrl("/fail");
