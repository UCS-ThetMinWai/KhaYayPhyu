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
@Table(name = "saleprice")
public class SalePrice extends AbstractEntity {

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getDiscount() {
		return discount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public static SalePrice create(int amount) {
		SalePrice price = new SalePrice();
		price.setAmount(amount);
		price.setDate(new Date());
		return price;
	}

	public static SalePrice clonePrice(SalePrice old, Supplier<String> boIdSupplier) {
		SalePrice price = new SalePrice();
		price.setBoId(boIdSupplier.get());
		price.amount = old.amount;
		price.date = old.date;
		return price;
	}
}
