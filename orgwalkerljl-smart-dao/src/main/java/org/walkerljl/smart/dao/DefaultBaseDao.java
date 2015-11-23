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
     * @param t
     * @return
     */
    int insert(T t);
    
    /**
     * 添加对象
     * @param list
     * @return
     */
    int insert(List<T> list);
    
    /**
     * 删除对象,主键集合
     * @param keys
     * @return
     */
    int deleteByKeys(List<KEY> keys);
    
    /**
     * 删除对象,只要不为NULL与空则为条件
     * @param t
     * @return
     */
    int delete(T t);
    
    /**
    * 修改对象状态
    * @param keys 主键集合
    * @param status
    * @return
    */
    int updateStatusByKeys(List<KEY> keys, Integer status); 

    
    /**
     * 更新对象,条件主键
     * @param t
     * @return
     */
    int updateByKey(T t);

    /**
     * 查询对象,条件主键
     * @param key
     * @return
     */
    T selectByKey(KEY key);
    
    /**
     * 查询对象集合,主键集合
     * @param keys
     * @return
     */
    List<T> selectListByKeys(List<KEY> keys);
    
    /**
     * 查询对象,只要不为NULL与空则为条件
     * @param t
     * @return
     */
    List<T> selectList(T t);
    
    /**
     * 查询对象总数,只要不为NULL与空则为条件
     * @param t
     * @return
     */
    int selectListCount(T t);
    
    /**
     * 获取分页数据,只要不为NULL与空则为条件
     * @param t
     * @return
     */
    QueryResult<T> selectQueryResult(T t);
}