package com.khayayphyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khayayphyu.domain.constant.PackingType;

@Entity
@Table(name = "saleOrder")
public class SaleOrder extends AbstractEntity {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sale")
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@OneToOne
	private Price price;

	@Column(name = "amount")
	private int amount;

	@Column(name = "weight")
	private double weight;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "peckagingType")
	private PackingType peckagingType;

	public SaleOrder() {
		super();
	}

	public Sale getSale() {
		return sale;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PackingType getPeckagingType() {
		return peckagingType;
	}

	public void setPeckagingType(PackingType peckagingType) {
		this.peckagingType = peckagingType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String toString() {
		return new ToStringBuilder(this).append(peckagingType).toString();
	}
}
