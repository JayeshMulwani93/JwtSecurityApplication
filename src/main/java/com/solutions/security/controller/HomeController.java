package com.solutions.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.security.model.request.LoginRequest;
import com.solutions.security.model.request.SignUpRequest;
import com.solutions.security.repository.cache.model.JWTToken;
import com.solutions.security.service.UserAuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "SignUp or Login", description = "These APIs will help the client to create a user/admin or login")
public class HomeController {

	@Autowired
	private UserAuthenticationService service;

	@SuppressWarnings("rawtypes")
	@PostMapping("/signup")
	public ResponseEntity signup(@Valid @RequestBody SignUpRequest request) {
		service.signUp(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JWTToken> login(@Valid @RequestBody LoginRequest request) {
		return new ResponseEntity<>(service.login(request), HttpStatus.OK);
	}
}
