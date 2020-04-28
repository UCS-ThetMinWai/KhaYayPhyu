package com.khayayphyu.domain.constant;

public enum Status {
	
	OPENED("Opened"), CLOSED("Closed");	
	
	private String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}	
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
