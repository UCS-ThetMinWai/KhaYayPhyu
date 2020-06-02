package com.khayayphyu.domain;

import java.util.function.Supplier;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant;

@MappedSuperclass
public abstract class AbstractEntity {
	
	public AbstractEntity() {
		super();
	}

	public AbstractEntity(String boId) {
		this.boId = boId;
	}
	
	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	private String boId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}
	
	public void setBoId(String boId, Supplier<String> boIdGengenator) {
		if(boId == SystemConstant.BOID_REQUIRED)
			boId = boIdGengenator.get();
		this.boId = boId;
	}
	
	public boolean sameBoId(AbstractEntity entity) {
		if (entity == null)
			return false;
		if (getBoId() == null)
			return false;
		return (this.getBoId().equals(entity.getBoId()));
	}
	
	public boolean isBoIdRequired() {
		return boId == null || SystemConstant.BOID_REQUIRED.equals(getBoId());
	}
	
	public boolean isNew() {
		return isBoIdRequired();
	}
	
	public String toString() {
		return new ToStringBuilder(this).append(boId).toString();
	}
}
