package org.walkerljl.smart.service;

import java.io.Serializable;
import java.util.List;

import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.domain.sys.SysLog;

/**
 * 基础业务接口
 * 
 * @author lijunlin
 */
public interface BaseService<T, KEY extends Serializable> {
	
	/**
	 * 添加对象集合
	 * @param entities
	 * @return
	 */
    @SuppressWarnings("unchecked")
	int insert(T... entities);
    
    /**
     * 添加对象集合,并记录日志
     * @param sysLog
     * @param entities
     * @return
     */
    @SuppressWarnings("unchecked")
	int insert(SysLog sysLog, T... entities);
    
    /**
     * 删除一个或多个对象,主键
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	int deleteByKeys(KEY... keys);
    
    /**
     * 删除一个或多个对象,主键,记录日志
     * @param sysLog
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	int deleteByKeys(SysLog sysLog, KEY... keys);
    
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
     * 更新对象,条件主键列表
     * @param modifiedEntity
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	int updateByKeys(T modifiedEntity, KEY... keys);
    
    /**
     * 更新对象,条件主键列表
     * @param modifiedEntity
     * @param sysLog
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	int updateByKeys(T modifiedEntity, SysLog sysLog, KEY... keys);
    
    /**
     * 更新或保存对象
     * @param entity
     * @return
     */
    int saveOrUpdate(T entity);
    
    /**
     * 更新或保存对象,记录日志
     * @param entity
     * @param sysLog
     * @return
     */
    int saveOrUpdate(T entity, SysLog sysLog);
    
    /**
     * 根据主键查询
     * @param key
     * @return
     */
    T selectByKey(KEY key);
    
    /**
     * 查询对象集合,条件主键
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
	List<T> selectListByKeys(KEY... keys);
    
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