/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.dao.DefaultBaseDao;
import org.walkerljl.smart.dao.QueryResult;

/**
 * 默认的数据访问层基础接口实现
 * 
 * @author lijunlin
 */
public abstract class DefaultBaseDaoImpl<T, KEY extends Serializable> extends MyBatisSupport implements DefaultBaseDao<T, KEY> {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private static final String INSERT = "insert";
	private static final String BATCH_INSERT = "batchInsert";
	private static final String DELETE_BY_KEYS = "deleteByKeys";
	private static final String DELETE_BY_CONDITION = "deleteByCondition";
	private static final String UPDATE_STATUS_BY_KEYS = "updateStatusByKeys";
	private static final String UPDATE_BY_KEY = "updateByKey";
	private static final String SELECT_BY_KEY = "selectByKey";
	private static final String SELECT_LIST_BY_KEYS = "selectListByKeys";
	private static final String SELECT_LIST = "selectList";
	private static final String SELECT_LIST_COUNT = "selectListCount";
	
	@Override
	public int insert(T t){
		return this.insert(getNameSpace(INSERT), t);
	}
	
	@Override
	public int insert(List<T> list) {
		int count = 0;
		if (ListUtils.isNotEmpty(list)) {
			count = this.insert(getNameSpace(BATCH_INSERT), list);
		}
		return count;
	}
	
	@Override
	public int deleteByKeys(List<KEY> keys) {
		if (ListUtils.isEmpty(keys)) {
			return 0;
		}
        return delete(getNameSpace(DELETE_BY_KEYS), keys);
    }
	
	@Override
	public int delete(T t) {
        return delete(getNameSpace(DELETE_BY_CONDITION), t);
    }
	
	@Override
	public int updateStatusByKeys(List<KEY> keys, Integer status) {
		if (ListUtils.isEmpty(keys)) { 
			return 0; 
		} 
		Map<String, Object> parameters = MapUtils.newHashMap(); 
 		parameters.put("status", status); 
 		parameters.put("list", keys); 
 		return update(getNameSpace(UPDATE_STATUS_BY_KEYS), parameters); 	 		
	}
	
	@Override
	public int updateByKey(T t) {
		return update(getNameSpace(UPDATE_BY_KEY), t);
	}
	
	@Override
	public T selectByKey(KEY key) {
		return select(getNameSpace(SELECT_BY_KEY), key);
	}
	
	@Override
	public List<T> selectListByKeys(List<KEY> keys) {
		return selectList(getNameSpace(SELECT_LIST_BY_KEYS), keys);
	}
	
	@Override
	public List<T> selectList(T t) {
		return selectList(getNameSpace(SELECT_LIST), t);
	}
	
	@Override
	public int selectListCount(T t) {
		return (Integer)this.select(getNameSpace(SELECT_LIST_COUNT), t);
	}
	
	@Override
	public QueryResult<T> selectQueryResult(T t) {
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultList(selectList(t));
		queryResult.setTotalRecord(selectListCount(t));
		return queryResult;
	}
}