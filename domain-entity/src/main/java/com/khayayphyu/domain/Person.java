package com.khayayphyu.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class Person extends AbstractEntity {
	private String name;
	private Date dob;
	private String phoneNumber;
    private String address;
    
    public Person() {
    	super();
    }
    
    public Person(String boId) {
    	super(boId);
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
				.append(name)
				.append(phoneNumber)
				.append(address)
				.toString();
	}
}
