package com.khayayphyu.domain.constant;

public interface SystemConstant {
	public static String INTERNAL_BOID="-2";
	
	public static final String BOID_REQUIRED="-1";
	
	public enum EntityType{
		
		CUSTOMER("Customer", "CUSTOMER"),
		SALE("Sale", "SALE"),
		PRICE("Price", "PRICE"),
		PRODUCT("Product", "PRODUCT"),
		SALEORDER("SaleOrder", "SALEORDER"),
		RAWPRODUCT("RawProduct", "RAWPRODUCT"),
		PURCHASE("Purchase", "PURCHASE"),
		PURCHASEORDER("PurchaseOrder", "PURCHASEORDER"),
		USER("User","USER");

		private String value;
		
		private String boIdPrefix;
		
		EntityType(String value,String boIdPrefix) {
			this.setValue(value);
			this.setBoIdPrefix(boIdPrefix);
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getBoIdPrefix() {
			return boIdPrefix;
		}
		public void setBoIdPrefix(String boIdPrefix) {
			this.boIdPrefix = boIdPrefix;
		}
		
	}
	
}
