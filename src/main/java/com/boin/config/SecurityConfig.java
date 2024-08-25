package com.boin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtService jwtService;

	public final UserDetailsService userDetailsService;

	private final AuthenticationProvider authenticationProvider;

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
		// White Lists
		String[] permitted = {"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**","/api/v1/auth/**","/loginpage","/fail","/index","/html/**","/css/**","/js/**","/images/**"};

		JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, skipPathRequestMatcher(), customAuthenticationFailureHandler);
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

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public RequestMatcher skipPathRequestMatcher() {
		String[] permitted = {"/api/v1/auth/**", "/loginpage", "/fail", "/index", "/html/**", "/css/**", "/js/**", "/images/**"};
		return new OrRequestMatcher(
				Arrays.stream(permitted).map(AntPathRequestMatcher::new).toArray(RequestMatcher[]::new)
		);
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
