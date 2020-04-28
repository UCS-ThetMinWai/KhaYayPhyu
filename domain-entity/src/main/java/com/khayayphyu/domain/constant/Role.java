package com.khayayphyu.domain.constant;

public enum Role{
	
	INSTRUCTOR("Instructor"),ORGANIZER("Organizer"),ADMINISTRATOR("Adminstrator"),EMPLOYEE("Employee");	
	
	private String value;

	private Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}	
	
	public void setValue(String value) {
		this.value = value;
	}
}
