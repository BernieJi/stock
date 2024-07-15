package com.boin.config;

import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApi {
	@Bean
	public OpenAPI initOpenApi() {
		return new OpenAPI().info(
				new Info()
				.title("金融系統api")
				.description("個人金融系統使用到的api描述")
				.version("V1.0")
				.license(new License().name("OpenApi官方文檔").url("http://springdoc.org"))
				);
				
	}
}
