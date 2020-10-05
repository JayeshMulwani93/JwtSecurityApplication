package com.solutions.security.service;

public interface PasswordEncoderService {

	String getSaltedPassword(String password);

	String getEncodePassword(String password);

}