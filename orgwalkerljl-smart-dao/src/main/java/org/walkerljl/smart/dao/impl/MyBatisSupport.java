/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.walkerljl.commons.exception.AppDaoException;
import org.walkerljl.smart.dao.rw.RoutingDataSource;

/**
 * 对mybatis的支持<br/>
 * spring配置文件需定义sqlTemplate与batchSqlTemplate
 * @author lijunlin
 * @param <T>
 * @param KEY
 */
public abstract class MyBatisSupport {

	/** 分隔符*/
	private final String separator = ".";
	
	/** 基础命名空间*/
	protected String baseNameSpace;
	
	/** spring 提供的sql操作模板*/
	@Resource(name="sqlTemplate")
	protected SqlSessionTemplate sqlTemplate;
	
	/**
	 * 获取mybatis配置文件完整命名空间 
	 * @param baseNameSpace 基础命名空间
	 * @param methodName 方法名称(mybatis中sql配置文件定义的sql方法id)
	 * @return
	 */
	protected String getNameSpace(final String baseNameSpace, final String methodName) {
		return baseNameSpace + this.separator + methodName;
	}
	
	/**
	 * 获取mybatis配置文件完整命名空间 
	 * @param methodName 方法名称
	 * @return
	 */
	protected String getNameSpace(final String methodName) {
		if (methodName == null || methodName.equals("")) {
			throw new AppDaoException("MyBatis命名空间值为Null或\"\"");
		}
		return getNameSpace(this.baseNameSpace, methodName);
	}

	/**
	 * SqlSessionTemplate
	 * @param readonly 是否只读
	 * @return
	 */
	protected SqlSessionTemplate getSqlTemplate(boolean readonly) {
		if (readonly) {
			RoutingDataSource.readOnly();
		}
        return sqlTemplate;
    }
	
	/**
	 * 新增对象
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int insert(String statement, Object parameter){
		int res = 0;
		try {
			if (parameter != null) {
				res = getSqlTemplate(false).insert(statement, parameter);
			}
		} catch (Exception ex) {
			throw new AppDaoException("Mybatis执行新增异常", ex);
		}
		return res;
	}
	
	/**
	 * 删除对象
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int delete(String statement, Object parameter){
		int res = 0;
		try {
			res = getSqlTemplate(false).delete(statement, parameter);
		} catch (Exception ex) {
		    throw new AppDaoException("Mybatis执行删除异常", ex);
		}
		return res;
	}
	
	/**
	 * 更新对象
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int update(String statement, Object parameter){
		int res = 0;
		try {
			if (parameter != null) {
				res = getSqlTemplate(false).update(statement, parameter);
			}
		} catch (Exception ex) {
		    throw new AppDaoException("Mybatis执行更新异常", ex);
		}
		return res;
	}
	
	/**
	 * 查询一条记录
	 * @param statement
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T select(String statement, Object parameter) {
	    T obj = null;
	    try {
	        obj = (T) getSqlTemplate(true).selectOne(statement, parameter);
        } catch (Exception ex) {
            throw new AppDaoException("Mybatis执行单条查询异常",ex);
        } finally {
            RoutingDataSource.clear();
        }
	    return obj;
	}
	
	/**
	 * 查询列表
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected <T> List<T> selectList(String statement, Object parameter) {
		List<T> list = null;
        try {
            list = getSqlTemplate(true).selectList(statement, parameter);
        } catch (Exception ex) {
            throw new AppDaoException("Mybatis执行列表查询异常",ex);
        } finally {
            RoutingDataSource.clear();
        }
        return list;
	}
	
	/**
	 *  查询Map
	 * @param <K>
	 * @param <V>
	 * @param statement
	 * @param parameter
	 * @param mapKey
	 * @return
	 */
	protected <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		Map<K, V> map = null;
        try {
            map = getSqlTemplate(true).selectMap(statement, parameter, mapKey);
        } catch (Exception ex) {
            throw new AppDaoException("Mybatis执行Map查询异常",ex);
        } finally {
            RoutingDataSource.clear();
        }
        return map;
	}
}