/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 默认的数据访问层基础接口
 * 
 * @author lijunlin
 */
public interface DefaultBaseDao<T, KEY extends Serializable> extends BaseDao {
    
    /**
     * 添加对象
     * @param entities
     * @return
     */
    @SuppressWarnings("unchecked")
	int insert(T... entities);
    
    /**
     * 删除对象,主键集合
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	int deleteByKeys(KEY... keys);
    
    /**
     * 删除对象,只要不为NULL与空则为条件
     * @param condition
     * @return
     */
    int delete(T condition);

    /**
     * 更新对象,条件主键
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    int updateByKeys(T entity, KEY... keys);
    
    /**
     * 更新对象
     * @param entity 待更新对象
     * @param condition 更新条件,不为空字段为条件
     * @return
     */
    int update(T entity, T condition);
    
    /**
     * 查询对象集合,主键集合
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
    List<T> selectListByKeys(KEY... keys);
    
    /**
     * 查询对象,只要不为NULL与空则为条件
     * @param condition
     * @param currentPage 当前页码
     * @param pageSize 每页大小
     * @return
     */
    List<T> selectList(T condition, int currentPage, int pageSize);
    
    /**
     * 查询对象总数,只要不为NULL与空则为条件
     * @param condition
     * @return
     */
    int selectListCount(T condition);
}