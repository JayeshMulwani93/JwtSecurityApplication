package com.solutions.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.security.model.SampleResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "v1/admins", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin", description = "These APIs need ROLE_ADMIN Privileges")
@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
public class AdminController {

	@GetMapping
	public ResponseEntity<SampleResponse> getAdminGreeting(
			@RequestHeader(name = "Authorization", required = true) String jwtToken) {
		return new ResponseEntity<SampleResponse>(new SampleResponse("Hello Admin!"), HttpStatus.OK);
	}
}