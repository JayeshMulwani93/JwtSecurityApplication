package com.solutions.security.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
	@Email
	@Schema(example = "abcd@gmail.com")
	private String emailId;

	@NotBlank
	@Schema(example = "Password")
	private String password;
}
