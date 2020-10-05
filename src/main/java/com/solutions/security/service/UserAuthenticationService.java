package com.solutions.security.service;

import com.solutions.security.model.request.LoginRequest;
import com.solutions.security.model.request.SignUpRequest;
import com.solutions.security.model.request.UpdatePasswordRequest;
import com.solutions.security.repository.cache.model.JWTToken;

public interface UserAuthenticationService {

	public void signUp(SignUpRequest request);

	public JWTToken login(LoginRequest request);

	public void logout(String jwtToken);

	public void updatePassword(String jwtToken, UpdatePasswordRequest request);

	public JWTToken refreshToken(String jwtToken);
}
