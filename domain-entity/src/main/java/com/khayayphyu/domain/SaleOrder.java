package com.khayayphyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.khayayphyu.domain.constant.PackingType;

@Entity
@Table(name="saleOrder")
public class SaleOrder extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "sale")
	private Sale sale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@Column(name = "peckagingType")
	private PackingType peckagingType;
	
	public SaleOrder() {
		super();
	}
	
	public Sale getSale() {
		return sale;
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
	public String toString() {
		return new ToStringBuilder(this)
				.append(peckagingType)
				.toString();
	}
}
