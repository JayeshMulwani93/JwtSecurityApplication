package com.solutions.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solutions.security.service.PasswordEncoderService;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

	private static final String SALTED_PASSWORD_FORMAT = "12345%s**";

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public String getEncodePassword(String password) {
		return encoder.encode(getSaltedPassword(password));
	}

	@Override
	public String getSaltedPassword(String password) {
		return String.format(SALTED_PASSWORD_FORMAT, password);
	}
}
