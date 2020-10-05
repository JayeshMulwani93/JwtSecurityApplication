package com.solutions.security.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.solutions.security.exception.UserNotFoundException;
import com.solutions.security.model.Role;
import com.solutions.security.model.User;
import com.solutions.security.repository.db.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmailId(username);
		if (user != null)
			return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
					getAuthorities(user.getRole()));
		throw new UserNotFoundException();
	}

	public Set<GrantedAuthority> getAuthorities(Role role) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
		return grantedAuthorities;
	}
}
