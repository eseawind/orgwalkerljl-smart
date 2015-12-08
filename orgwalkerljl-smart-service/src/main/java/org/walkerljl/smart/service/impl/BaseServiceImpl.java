/*
 * Copyright (c) 2010-2014 lijunlin All Rights Reserved.
 * 本软件源代码版权归作者所有,未经许可不得任意复制与传播.
 */
/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.service.impl;

import java.io.Serializable;
import java.util.List;

import org.walkerljl.commons.collection.CollectionUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.dao.BaseDao;
import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.domain.sys.SysLog;
import org.walkerljl.smart.service.BaseService;

/**
 * 基础业务接口实现
 * 
 * @author lijunlin
 */
public abstract class BaseServiceImpl<T, KEY extends Serializable> implements BaseService<T, KEY> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override @SuppressWarnings("unchecked")
	public int insert(T... entities) {
		return insert(null, entities);
	}

	@Override @SuppressWarnings("unchecked")
	public int insert(SysLog sysLog, T... entities) {
		return getDao().insert(entities);
	}

	@Override @SuppressWarnings("unchecked")
	public int deleteByKeys(KEY... keys) {
		return deleteByKeys(null, keys);
	}

	@Override @SuppressWarnings("unchecked")
	public int deleteByKeys(SysLog sysLog, KEY... keys) {
		return getDao().deleteByKeys(keys);
	}

	@Override
	public int delete(T condition) {
		return delete(condition, null);
	}

	@Override
	public int delete(T condition, SysLog sysLog) {
		return getDao().delete(condition);
	}

	@Override @SuppressWarnings("unchecked")
	public int updateByKeys(T modifiedEntity, KEY... keys) {
		return updateByKeys(modifiedEntity, null, keys);
	}

	@Override @SuppressWarnings("unchecked")
	public int updateByKeys(T modifiedEntity, SysLog sysLog, KEY... keys) {
		return getDao().updateByKeys(modifiedEntity, keys);
	}

	@Override
	public int saveOrUpdate(T entity) {
		return saveOrUpdate(entity, null);
	}

	@Override @SuppressWarnings("unchecked")
	public int saveOrUpdate(T entity, SysLog sysLog) {
		if (entity == null) {
			return 0;
		}
		KEY id = null;
		try {
            Class<?> clazz = entity.getClass();
            id = (KEY) clazz.getMethod("getId").invoke(entity);
        } catch (Exception e) {
            LOGGER.warn("获取对象主键值失败!");
        }
	    if (id != null) {
	        return updateByKeys(entity, id);
	    } 
	    return insert(entity);
	}

	@Override
	public T selectByKey(KEY key) {
		List<T> entities = selectListByKeys(key);
		if (CollectionUtils.isEmpty(entities)) {
			return null;
		}
		return entities.get(0);
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<T> selectListByKeys(KEY... keys) {
		return getDao().selectListByKeys(keys);
	}

	@Override @SuppressWarnings("unchecked")
	public List<T> selectList(T condition) {
		if (condition == null || !(condition instanceof Page)) {
			return null;
		}
		Page<T> page = (Page<T>)condition;
		return getDao().selectList(condition, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public int selectListCount(T condition) {
		return getDao().selectListCount(condition);
	}

	@Override @SuppressWarnings("unchecked")
	public Page<T> selectPage(T condition) {
		if (condition == null || !(condition instanceof Page)) {
			return null;
		}
		Page<T> page = (Page<T>)condition;
		page.setResult(selectList(condition));
		if (page.isFirstPage()) {
			page.setTotalCount(selectListCount(condition));
		}
		return page;
	}
	
	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();
}