package com.solutions.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.solutions.security.repository.cache.model.JWTToken;

@Configuration
public class RedisConfiguration {

	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private int port;

	@Bean
	public JedisConnectionFactory getJedisFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, port);
		return new JedisConnectionFactory(configuration);
	}

	@Bean
	public RedisTemplate<String, JWTToken> redisTemplate() {
		RedisTemplate<String, JWTToken> redisTemplate = new RedisTemplate<String, JWTToken>();
		redisTemplate.setConnectionFactory(getJedisFactory());
		return redisTemplate;
	}
}