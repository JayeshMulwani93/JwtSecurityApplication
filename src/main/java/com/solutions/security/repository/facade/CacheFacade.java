package com.solutions.security.repository.facade;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutions.security.jwt.facade.JWTFacade;
import com.solutions.security.repository.cache.JWTRepository;
import com.solutions.security.repository.cache.model.CacheEntry;
import com.solutions.security.repository.cache.model.JWTToken;

@Component
public class CacheFacade {

	@Autowired
	private JWTRepository repository;

	@Autowired
	private JWTFacade jwtFacade;

	public void addToCache(JWTToken jwtToken) {
		Integer customerId = getCustomerIdFromJWT(jwtToken.getJwt());
		Optional<CacheEntry> cacheOptional = repository.findById(customerId);
		CacheEntry cacheEntry = null;
		if (cacheOptional.isPresent()) {
			cacheEntry = cacheOptional.get();
			cacheEntry.getSessions().add(jwtToken);
		} else {
			cacheEntry = new CacheEntry();
			cacheEntry.setCustomerId(customerId);
			Set<JWTToken> sessions = new HashSet<>();
			sessions.add(jwtToken);
			cacheEntry.setSessions(sessions);
		}
		repository.save(cacheEntry);
	}

	public boolean removeFromCache(String jwtToken) {
		Integer customerId = getCustomerIdFromJWT(jwtToken);
		Optional<CacheEntry> cacheOptional = repository.findById(customerId);
		CacheEntry cacheEntry = null;
		if (cacheOptional.isPresent()) {
			cacheEntry = cacheOptional.get();
			Set<JWTToken> sessions = cacheEntry.getSessions();
			Optional<JWTToken> sessionOptional = sessions.stream().filter(session -> session.getJwt().equals(jwtToken))
					.findFirst();
			if (sessionOptional.isPresent()) {
				if (cacheEntry.getSessions().size() == 1) {
					repository.delete(cacheEntry);
				} else {
					sessions.remove(sessionOptional.get());
					repository.save(cacheEntry);
				}
				return true;
			}
		}
		return false;

	}

	public void removeAllSessions(String jwtToken) {
		Integer customerId = getCustomerIdFromJWT(jwtToken);
		repository.deleteById(customerId);
	}

	public Integer getCustomerIdFromJWT(String jwt) {
		return jwtFacade.getClaims(jwt).get("customerId", Integer.class);
	}

	public JWTToken getCacheEntry(Integer customerId, String jwt) {
		Optional<CacheEntry> cacheOptional = repository.findById(customerId);
		if (cacheOptional.isPresent()) {
			CacheEntry cacheEntry = cacheOptional.get();
			Optional<JWTToken> sessionOptional = cacheEntry.getSessions().stream()
					.filter(session -> session.getJwt().equals(jwt)).findFirst();
			if (sessionOptional.isPresent())
				return sessionOptional.get();
		}
		return null;
	}
}
