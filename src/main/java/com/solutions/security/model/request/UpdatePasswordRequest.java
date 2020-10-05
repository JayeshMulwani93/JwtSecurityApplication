package com.solutions.security.model.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePasswordRequest {

	@NotBlank
	@Schema(example = "NewPassword")
	private String newPassword;

}