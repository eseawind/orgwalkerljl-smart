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

import org.walkerljl.commons.util.LongUtils;
import org.walkerljl.smart.dao.DefaultBaseDao;
import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.domain.sys.SysLog;
import org.walkerljl.smart.enums.Status;
import org.walkerljl.smart.service.DefaultBaseService;

/**
 * DefaultBaseServiceImpl
 * 
 * @author lijunlin
 */
public abstract class DefaultBaseServiceImpl<T, KEY extends Serializable> extends BaseServiceImpl implements DefaultBaseService<T, KEY> {
	
	/**
	 * 获取DAO操作类
	 */
	public abstract DefaultBaseDao<T, KEY> getDao();
	
	@Override
	public int insert(T t) {
		return t == null ? 0 : getDao().insert(t);
	}
	
	@Override
	public int insert(T t, SysLog sysLog) {
        if (t == null || sysLog == null) {
        	return insert(t);
        }
        int res = 0;
        try {
            res = insert(t);
        } finally {
        	sysLog.setResult(res > 0);
        }
        return res;
    }
	
	@Override
	public int insert(List<T> list) {
	    return getDao().insert(list);
	}
	
	@Override
	public int insert(List<T> list, SysLog sysLog) {
	    if (list == null || sysLog == null) {
	    	return insert(list);
	    }
        int res = 0;
        try {
            res = insert(list);
        } finally {
        	sysLog.setResult(res > 0);
        }
        return res;
	}
	
	@Override
	public int deleteByKeys(List<KEY> keys) {
	    return getDao().deleteByKeys(keys);
	}
	
	@Override
	public int deleteByKeys(List<KEY> keys, SysLog sysLog) {
        if (sysLog == null) {
        	return deleteByKeys(keys);
        }
        int res = 0;
        try {
            res = deleteByKeys(keys);
        } finally {
        	sysLog.setResult(res > 0);
        }
        return res;
    }
	
	@Override
	public int updateStatusByKeys(List<KEY> keys, Status status) {
       return updateStatusByKeys(keys, status, null);
    }
	
	@Override
	public int updateStatusByKeys(List<KEY> keys, Status status, SysLog sysLog) {
        if (sysLog == null) {
        	return updateStatusByKeys(keys, status);
        }
        int res = 0;
        try {
            res = updateStatusByKeys(keys, status);
        } finally {
            sysLog.setResult(res > 0);
        }
        return res;
    }
	
	@Override
	public int delete(T condition) {
        return condition == null ? 0 : getDao().delete(condition);
    }
	
	@Override
	public int delete(T condition, SysLog sysLog) {
        if (condition == null || sysLog == null) {
        	return delete(condition);
        }
        int res = 0;
        try {
            res = delete(condition);
        } finally {
        	sysLog.setResult(res > 0);
        }
        return res;
    }
	
	@Override
	public int updateByKey(T condition) {
	    if (condition == null) {
	    	return 0;
	    }
		return getDao().updateByKey(condition);
	}
	
	@Override
	public int updateByKey(T condition, SysLog sysLog) {
        if (condition == null || sysLog == null) {
        	return updateByKey(condition);
        }
        int res = 0;
        try {
            res = updateByKey(condition);
        } finally {
            sysLog.setResult(res > 0);
        }
        return res;
    }
	
	@Override
	public boolean saveOrUpdate(T t) {
	    Long id = 0L;
	    try {
            Class<?> clz = t.getClass();
            id = (Long)clz.getMethod("getId").invoke(t);
        } catch (Exception e) {
            LOG.warn("获取对象主键值失败!");
        }
	    if (LongUtils.greatThanZero(id)) {
	        return updateByKey(t) > 0;
	    } 
	    return insert(t) > 0;
	}
	
	@Override
	public boolean saveOrUpdate(T t, SysLog sysLog) {
	    if (t == null || sysLog == null) {
	    	return saveOrUpdate(t);
	    }
        boolean res = false;
        try {
            res = saveOrUpdate(t);
        } finally {
        	sysLog.setResult(res);
        }
        return res;
	}
	
	@Override
	public T selectByKey(KEY key) {
		return getDao().selectByKey(key);
	}
	
	@Override
	public List<T> selectListByKeys(List<KEY> keys) {
		return getDao().selectListByKeys(keys);
	}
	
	@Override
	public List<T> selectList(T condition) {
		return condition == null ? null : getDao().selectList(condition);
	}
	
	@Override
	public int selectListCount(T condition) {
		return condition == null ? 0 : getDao().selectListCount(condition);
	}
	
	@Override
	public Page<T> selectPage(T condition) {
		if (condition == null || !(condition instanceof Page)) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Page<T> page = (Page<T>)condition;
		page.setResult(selectList(condition));
		if (page.isFirstPage()) {
			page.setTotalCount(selectListCount(condition));
		}
		return page;
	}
}