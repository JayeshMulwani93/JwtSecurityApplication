package com.solutions.security.repository.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solutions.security.repository.cache.model.CacheEntry;

@Repository
public interface JWTRepository extends CrudRepository<CacheEntry, Integer> {

}