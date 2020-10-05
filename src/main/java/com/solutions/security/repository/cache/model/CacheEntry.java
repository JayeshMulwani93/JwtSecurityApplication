package com.solutions.security.repository.cache.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash
public class CacheEntry {
	@Id
	private Integer customerId;

	private Set<JWTToken> sessions;
}