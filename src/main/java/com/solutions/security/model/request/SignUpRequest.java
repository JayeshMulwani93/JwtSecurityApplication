package com.solutions.security.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.solutions.security.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpRequest {

	@Email
	@Schema(example = "abcd@gmail.com")
	private String emailId;

	@NotBlank
	@Schema(example = "Password")
	private String password;

	@NotNull
	private Role role;
}
