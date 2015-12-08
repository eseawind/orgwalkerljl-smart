/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.walkerljl.commons.collection.ArraysUtils;
import org.walkerljl.db.orm.session.SqlSession;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.dao.BaseDao;

/**
 * 默认的数据访问层基础接口实现
 * 
 * @author lijunlin
 */
public class BaseDaoImpl<T, KEY extends Serializable> extends MyBatisSupport implements BaseDao<T, KEY> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	@Resource private SqlSession sqlSession;
	
	@Override @SuppressWarnings("unchecked")
	public int insert(T... entities) {
		if (ArraysUtils.isEmpty(entities)) {
			return 0;
		}
		return sqlSession.insert(entities);
	}
	
	@Override @SuppressWarnings("unchecked")
	public int deleteByKeys(KEY... keys) {
		if (ArraysUtils.isEmpty(keys)) {
			return 0;
		}
		return sqlSession.deleteByKeys(entityClass, keys);
    }
	
	@Override
	public int delete(T condition) {
		if (condition == null) {
			return 0;
		}
        return sqlSession.delete(condition);
    }
	
	@Override @SuppressWarnings("unchecked")
	public int updateByKeys(T entity, KEY... keys) {
		if (entity == null || ArraysUtils.isEmpty(keys)) {
			return 0;
		}
		return sqlSession.updateByKeys(entity, keys);
	}

	@Override
	public int update(T entity, T condition) {
		if (entity == null || condition == null) {
			return 0;
		}
		return sqlSession.update(entity, condition);
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<T> selectListByKeys(KEY... keys) {
		if (ArraysUtils.isEmpty(keys)) {
			return null;
		}
		return sqlSession.selectByKeys(entityClass, keys);
	}
	
	@Override
	public List<T> selectList(T condition, int currentPage, int pageSize) {
		if (currentPage <= 0 || pageSize <= 0) {
			return null;
		}
		return sqlSession.selectList(condition, currentPage, pageSize);
	}
	
	@Override
	public int selectListCount(T condition) {
		if (condition == null) {
			return 0;
		}
		return sqlSession.selectCount(condition);
	}
}