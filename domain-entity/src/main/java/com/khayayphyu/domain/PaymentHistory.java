package com.khayayphyu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.jsonviews.SummaryView;

@Entity
@Table(name = "paymentHistory")
public class PaymentHistory extends AbstractEntity {
	
	@JsonIgnore
	@OneToOne
	private Sale sale;
	
	@Column(name = "currentPayAmount")
	@JsonView(SummaryView.class)
	private int currentPayAmount;
	
	@Column(name = "currentBalance")
	@JsonView(SummaryView.class)
	private int currentBalance;
	
	@Column(name = "previousBalance")
	@JsonView(SummaryView.class)
	private int previousBalance;
	
	@Column(name = "remaining")
	@JsonView(SummaryView.class)
	private int remaining;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payDate")
	@JsonView(SummaryView.class)
	private Date payDate;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public int getCurrentPayAmount() {
		return currentPayAmount;
	}

	public void setCurrentPayAmount(int currentPayAmount) {
		this.currentPayAmount = currentPayAmount;
	}

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

	public int getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(int previousBalance) {
		this.previousBalance = previousBalance;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}
