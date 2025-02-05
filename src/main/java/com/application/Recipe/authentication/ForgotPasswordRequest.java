package com.application.Recipe.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ForgotPasswordRequest {
	private String email;

}
