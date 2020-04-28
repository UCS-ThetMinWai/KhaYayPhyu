package com.khayayphyu.domain.constant;

public enum PackingType {
	PACK("Pack"), CARD("Card");
	private String value;

	private PackingType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}	
	
	public void setValue(String value) {
		this.value = value;
	}
}
