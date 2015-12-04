/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.service;

import java.io.Serializable;
import java.util.List;

import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.domain.sys.SysLog;
import org.walkerljl.smart.enums.Status;

/**
 * DefaultBaseService
 * 
 * @author lijunlin
 */
public interface DefaultBaseService<T, KEY extends Serializable> extends BaseService {

	/**
	 * 添加对象(主键回填到对象ID)
	 * @param t
	 * @return
	 */
	int insert(T t);
	
	/**
	 * 添加对象(主键回填到对象ID),并记录日志
	 * @param t
	 * @param sysLog
	 * @return
	 */
	int insert(T t, SysLog sysLog);
	
	/**
	 * 添加对象集合
	 * @param list
	 * @return
	 */
    int insert(List<T> list);
    
    /**
     * 添加对象集合,并记录日志
     * @param list
     * @param sysLog
     * @return
     */
    int insert(List<T> list, SysLog sysLog);
    
    /**
     * 删除一个或多个对象,主键
     * @param keys
     * @return
     */
    int deleteByKeys(List<KEY> keys);
    
    /**
     * 删除一个或多个对象,主键,记录日志
     * @param keys
     * @param sysLog
     * @return
     */
    int deleteByKeys(List<KEY> keys, SysLog sysLog);
    
    /**
     * 更新一个或多个对象状态
     * @param keys
     * @param status
     * @return
     */
    int updateStatusByKeys(List<KEY> keys, Status status);
    
    /**
     * 更新一个或多个对象状态,记录日志
     * @param keys
     * @param status
     * @param sysLog
     * @return
     */
    int updateStatusByKeys(List<KEY> keys, Status status, SysLog sysLog);
    
    /**
     * 删除对象,只要不为NULL与空则为条件
     * @param condition
     * @return
     */
    int delete(T condition);
    
    /**
     * 删除对象,只要不为NULL与空则为条件,记录日志
     * @param condition
     * @param sysLog
     * @return
     */
    int delete(T condition, SysLog sysLog);
    
    /**
     * 更新对象,条件主键
     * @param condition
     * @return
     */
    int updateByKey(T condition);
    
    /**
     * 更新对象,条件主键,记录日志
     * @param condition
     * @param sysLog
     * @return
     */
    int updateByKey(T condition, SysLog sysLog);
    
    /**
     * 更新或保存对象
     * @param t
     * @return
     */
    boolean saveOrUpdate(T t);
    
    /**
     * 更新或保存对象,记录日志
     * @param t
     * @param sysLog
     * @return
     */
    boolean saveOrUpdate(T t, SysLog sysLog);
    
    /**
     * 查询对象,条件主键
     * @param key
     * @return
     */
    T selectByKey(KEY key);
    
    /**
     * 查询对象集合,条件主键
     * @param keys
     * @return
     */
    List<T> selectListByKeys(List<KEY> keys);
    
    /**
     * 查询对象,只要不为NULL与空则为条件
     * @param condition
     * @return
     */
    List<T> selectList(T condition);
    
    /**
     * 查询对象数量,只要不为NULL与空则为条件
     * @param condition
     * @return
     */
    int selectListCount(T condition);
    
    /**
     * 分页查询
     * @param condition
     * @return
     */
    Page<T> selectPage(T condition);
}