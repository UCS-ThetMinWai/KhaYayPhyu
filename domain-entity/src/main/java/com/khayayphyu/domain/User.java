package com.khayayphyu.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.khayayphyu.domain.constant.Role;

@Entity
@Table(name = "user")
public class User extends Person {
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public User() {
		super();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
				.append(role)
				.toString();
	}
}
