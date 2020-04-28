package com.khayayphyu.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.AbstractService;

public abstract class AbstractServiceImpl<T> implements AbstractService<T> {
	public abstract long getCount();
	
	public String getNextBoId(EntityType entityType) {
		return makeBoId(entityType, (int) getCount() + 1);
	}

	public String makeBoId(String prefix, int currentObjCount) {
		return prefix + StringUtils.leftPad(currentObjCount+"", 8, '0');
	}

	public String makeBoId(EntityType entityType, int currentObjectCount) {
		return makeBoId(entityType.getBoIdPrefix(), currentObjectCount);
	}
	
	@Override
	public List<T> findByBoId(String boId) throws ServiceUnavailableException {
		return null;
	}

	@Override
	public List<T> findByName(String name) throws ServiceUnavailableException {
		return null;
	}

}
