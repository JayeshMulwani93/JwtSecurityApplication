package com.solutions.security.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.solutions.security.jwt.facade.JWTFacade;
import com.solutions.security.repository.cache.facade.CacheFacade;
import com.solutions.security.repository.cache.model.JWTToken;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomRequestFiter extends OncePerRequestFilter {

	private static final String BEARER = "Bearer";

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String CLAIM_ROLE = "role";

	private static final String CLAIM_CUSTOMER_ID = "customerId";

	@Autowired
	private JWTFacade jwtFacade;

	@Autowired
	private CacheFacade cacheFacade;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = getJWTToken(request);
			if (jwtToken != null) {
				Claims claims = jwtFacade.getClaims(jwtToken);
				String userName = claims.getSubject();
				Integer customerId = claims.get(CLAIM_CUSTOMER_ID, Integer.class);
				JWTToken cacheSession = cacheFacade.getCacheEntry(customerId, jwtToken);
				if (cacheSession != null) {
					String role = claims.get(CLAIM_ROLE, String.class);
					Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
					grantedAuthorities.add(new SimpleGrantedAuthority(role));
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null,
							grantedAuthorities);
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			}
		} catch (Exception exp) {
			log.info("Error occurred while parsing JWT Token " + exp.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getJWTToken(HttpServletRequest request) {
		String authHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (authHeader != null && authHeader.startsWith(BEARER))
			return authHeader.substring(7);
		return null;
	}
}