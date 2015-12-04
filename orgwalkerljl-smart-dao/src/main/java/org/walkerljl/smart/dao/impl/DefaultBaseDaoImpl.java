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
import org.walkerljl.smart.dao.DefaultBaseDao;

/**
 * 默认的数据访问层基础接口实现
 * 
 * @author lijunlin
 */
public class DefaultBaseDaoImpl<T, KEY extends Serializable> extends MyBatisSupport implements DefaultBaseDao<T, KEY> {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
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
        return sqlSession.delete(condition);
    }
	
	@Override @SuppressWarnings("unchecked")
	public int updateByKeys(T entity, KEY... keys) {
		return sqlSession.updateByKeys(entity, keys);
	}

	@Override
	public int update(T entity, T condition) {
		return sqlSession.update(entity, condition);
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<T> selectListByKeys(KEY... keys) {
		return sqlSession.selectByKeys(entityClass, keys);
	}
	
	@Override
	public List<T> selectList(T condition, int currentPage, int pageSize) {
		return sqlSession.selectList(condition, currentPage, pageSize);
	}
	
	@Override
	public int selectListCount(T condition) {
		return sqlSession.selectCount(condition);
	}
}