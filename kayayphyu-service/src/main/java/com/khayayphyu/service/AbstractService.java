package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface AbstractService<T> {
	public long getCount();
	
	public List<T> findByBoId(String boId)throws ServiceUnavailableException;
	
	public List<T> findByName(String name)throws ServiceUnavailableException;

	public String getNextBoId(EntityType entityType);

	public String makeBoId(String prefix, int currentObjCount);
	
	public String makeBoId(EntityType entityType, int currentObjectCount);
}
