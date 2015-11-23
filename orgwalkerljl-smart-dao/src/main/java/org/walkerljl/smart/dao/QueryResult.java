/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果集
 * @author lijunlin<walkerljl@qq.com>
 */
public class QueryResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 查询结果集*/
	private List<T> resultList;
	/** 总的记录数*/
	private int totalRecord;

	/**
	 * 获取结果集
	 * @return
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * 设置结果集
	 * @param resultList
	 */
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 获取记录总数
	 * @return
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 设置记录总数
	 * @param totalRecord
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}