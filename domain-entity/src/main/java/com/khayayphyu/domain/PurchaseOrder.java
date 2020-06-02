package com.khayayphyu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "purchaseOrder")
public class PurchaseOrder extends AbstractEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Purchase purchase;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@Column(name = "weight")
	private double weight;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;
	
	public PurchaseOrder() {
		super();
	}
	
	public PurchaseOrder(String boId) {
		super(boId);
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
}
