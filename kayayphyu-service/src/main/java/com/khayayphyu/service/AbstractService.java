package com.khayayphyu.service;

import java.util.List;
import java.util.function.Consumer;

import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface AbstractService<T> {
	public long getCount();
	
	public T findByBoId(String boId)throws ServiceUnavailableException;
	
	public T findByBoId(String boId, Consumer<T> consumer)throws ServiceUnavailableException;
	
	public List<T> findByName(String name)throws ServiceUnavailableException;

	public String getNextBoId(EntityType entityType);

	public String makeBoId(String prefix, int currentObjCount);
	
	public String makeBoId(EntityType entityType, int currentObjectCount);

}
