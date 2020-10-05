package com.solutions.security.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = { "emailId" }))
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;

	private String emailId;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	public User(String emailId, String password, Role role) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.role = role;
	}

}
