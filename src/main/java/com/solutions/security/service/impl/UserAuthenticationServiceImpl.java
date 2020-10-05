package com.solutions.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.solutions.security.exception.AlreadyExistsException;
import com.solutions.security.exception.InvalidJWTException;
import com.solutions.security.exception.UserNotFoundException;
import com.solutions.security.jwt.facade.JWTFacade;
import com.solutions.security.model.User;
import com.solutions.security.model.request.LoginRequest;
import com.solutions.security.model.request.SignUpRequest;
import com.solutions.security.model.request.UpdatePasswordRequest;
import com.solutions.security.repository.cache.facade.CacheFacade;
import com.solutions.security.repository.cache.model.JWTToken;
import com.solutions.security.repository.db.UserRepository;
import com.solutions.security.service.PasswordEncoderService;
import com.solutions.security.service.UserAuthenticationService;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoderService passwordService;

	@Autowired
	private CacheFacade cacheFacade;

	@Autowired
	private JWTFacade jwtFacade;

	@Override
	public void signUp(SignUpRequest request) {
		User findByEmailId = userRepository.findByEmailId(request.getEmailId());
		if (findByEmailId != null) {
			throw new AlreadyExistsException();
		}
		User user = new User(request.getEmailId(), passwordService.getEncodePassword(request.getPassword()),
				request.getRole());
		userRepository.save(user);
	}

	@Override
	public JWTToken login(LoginRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmailId(),
				passwordService.getSaltedPassword(request.getPassword()));
		authenticationManager.authenticate(token);
		User user = getUser(request.getEmailId());
		JWTToken jwtToken = jwtFacade.getJWTToken(user);
		cacheFacade.addToCache(jwtToken);
		return jwtToken;
	}

	@Override
	public void logout(String jwtToken) {
		boolean removedFromCache = cacheFacade.removeFromCache(jwtToken);
		if (!removedFromCache) {
			throw new InvalidJWTException();
		}
	}

	@Override
	public void updatePassword(String jwtToken, UpdatePasswordRequest request) {
		cacheFacade.removeAllSessions(jwtToken);
		String subject = jwtFacade.getClaims(jwtToken).getSubject();
		User user = getUser(subject);
		if (user != null) {
			user.setPassword(passwordService.getEncodePassword(request.getNewPassword()));
			userRepository.save(user);
		}
	}

	private User getUser(String subject) {
		User user = userRepository.findByEmailId(subject);
		if (user != null) {
			return user;
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public JWTToken refreshToken(String jwtToken) {
		cacheFacade.removeFromCache(jwtToken);
		JWTToken newJWTToken = jwtFacade.getNewJWTToken(jwtToken);
		cacheFacade.addToCache(newJWTToken);
		return newJWTToken;
	}
}