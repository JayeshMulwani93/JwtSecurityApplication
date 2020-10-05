package com.solutions.security.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.security.model.SampleResponse;
import com.solutions.security.model.request.UpdatePasswordRequest;
import com.solutions.security.repository.cache.model.JWTToken;
import com.solutions.security.service.UserAuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "End User APIs", description = "These APIs will help the end user to perform various actions")
public class UserController {

	private static final String AUTH_TOKEN_HEADER = "Authorization";
	@Autowired
	private UserAuthenticationService service;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_USER')")
	public ResponseEntity<SampleResponse> getUserGreeting(
			@NotBlank @RequestHeader(name = AUTH_TOKEN_HEADER, required = true) String jwtToken) {
		return new ResponseEntity<SampleResponse>(new SampleResponse("Hello User!"), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/logout")
	public ResponseEntity logout(@NotBlank @RequestHeader(name = AUTH_TOKEN_HEADER, required = true) String jwtToken) {
		service.logout(getJWTToken(jwtToken));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PatchMapping("/update-password")
	public ResponseEntity updatePassword(
			@NotBlank @RequestHeader(name = AUTH_TOKEN_HEADER, required = true) String jwtToken,
			@Valid @RequestBody UpdatePasswordRequest passwordRequest) {
		service.updatePassword(getJWTToken(jwtToken), passwordRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<JWTToken> refreshToken(
			@NotBlank @RequestHeader(name = AUTH_TOKEN_HEADER, required = true) String jwtToken) {
		return new ResponseEntity<JWTToken>(service.refreshToken(getJWTToken(jwtToken)), HttpStatus.OK);
	}

	private String getJWTToken(String jwtToken) {
		return jwtToken.substring(7);
	}
}
