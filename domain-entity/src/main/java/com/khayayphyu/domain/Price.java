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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "price")
public class Price extends AbstractEntity {
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;
	
	@Column(name = "buyAmount")
	private int buyAmount;
	
	@Column(name = "saleAmount")
	private int saleAmount;
	
	@Column(name = "discount")
	private float discount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;
	
	public int getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	public int getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(int saleAmount) {
		this.saleAmount = saleAmount;
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
	
	public static Price clonePrice(Price old, Supplier<String> boIdSupplier) {
		Price price = new Price();
		price.setBoId(boIdSupplier.get());
		price.saleAmount = old.saleAmount;
		price.date = old.date;
		return price;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append(buyAmount)
				.append(saleAmount)
				//.append(product)
				.append(discount)
				.toString();
	}
}
