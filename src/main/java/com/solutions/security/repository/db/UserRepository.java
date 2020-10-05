package com.solutions.security.repository.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solutions.security.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findByEmailId(String emailId);
}
