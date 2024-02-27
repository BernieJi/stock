package com.boin.config;

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
				.title("金融系統")
				.description("個人金融系統-Sample")
				.version("V1.0")
				);
				
	}
}
