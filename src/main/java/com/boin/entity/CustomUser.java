package com.boin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class CustomUser{

		private Integer id;

		private String username;

		private String password;

		private String email;

		private String authority;
	}

