package com.khayayphyu.domain;

import java.util.Date;
import java.util.function.Supplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "purchasePrice")
public class PurchasePrice extends AbstractEntity {
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@Column(name = "amount")
	private int amount;

	@Column(name = "discount")
	private float discount;

	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static PurchasePrice create(int amount) {
		PurchasePrice price = new PurchasePrice();
		price.setAmount(amount);
		price.setDate(new Date());
		return price;
	}

	public static PurchasePrice clonePrice(PurchasePrice old, Supplier<String> boIdSupplier) {
		PurchasePrice price = new PurchasePrice();
		price.setBoId(boIdSupplier.get());
		price.amount = old.amount;
		price.date = old.date;
		return price;
	}
}
