package com.solutions.security.jwt.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.solutions.security.model.User;
import com.solutions.security.repository.cache.model.JWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTFacade {

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.expirationMS}")
	private long expirationMS;

	public JWTToken getJWTToken(User user) {
		long currentTimeMillis = System.currentTimeMillis();
		long expirationTimeMS = currentTimeMillis + expirationMS;
		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
				.setIssuedAt(new Date(currentTimeMillis))
				.setExpiration(new Date(expirationTimeMS))
				.setSubject(user.getEmailId())
				.addClaims(generateDefaultClaims(user))
				.compact();
		return new JWTToken(jwt, new Date(expirationTimeMS));
	}

	public Claims getClaims(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
	}

	private Map<String, Object> generateDefaultClaims(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().name());
		claims.put("customerId", user.getCustomerId());
		return claims;
	}

	public JWTToken getNewJWTToken(String jwtToken) {
		long currentTimeMillis = System.currentTimeMillis();
		long expirationTimeMS = currentTimeMillis + expirationMS;

		Claims claims = getClaims(jwtToken);
		String jwt = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setSubject(claims.getSubject()).addClaims(claims)
				.setIssuedAt(new Date(currentTimeMillis))
				.setExpiration(new Date(expirationTimeMS))
				.compact();
		return new JWTToken(jwt, new Date(expirationTimeMS));

	}
}