package com.khayayphyu.domain;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class Person extends AbstractEntity {
	private String name;
	private int age;
	private String phoneNumber;
	private String emailAddress;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
				.append(name)
				.append(age)
				.append(phoneNumber)
				.append(address)
				.append(emailAddress)
				.toString();
	}
}
