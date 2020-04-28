package com.khayayphyu.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="customer")
public class Customer extends Person {
	
	public Customer() {
		super();
	}
	
	public Customer(String boId) {
		super(boId);
	}

	public String toString() {
		return new ToStringBuilder(this)
				.toString();
	}
}
