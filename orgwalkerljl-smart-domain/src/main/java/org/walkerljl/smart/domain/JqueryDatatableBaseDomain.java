/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain;

/**
 * DataTable插件分页参数
 * @author lijunlin<walkerljl@qq.com>
 */
public abstract class JqueryDatatableBaseDomain extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	/** DataTable请求服务器端次数*/
	private String sEcho;
	/** 过滤文本*/
	private String sSearch;
	/** 每页显示的数量*/
	private int iDisplayLength;
    /** 分页时开始行号*/
	private int iDisplayStart;
	/** 列数*/
	private int iColumns;
    /** 排序列的数量*/
	private int iSortingCols;
    /** 逗号分割所有的列*/
	private String sColumns;
	
	//扩展字段
	/** 分页开始索引*/
	private int startIndex = 0;
	/**　每页数量*/
	private int pageSize = 200;
	
	public JqueryDatatableBaseDomain(){}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
		this.setPageSize(this.iDisplayLength);
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
		this.setStartIndex(this.iDisplayStart);
	}

	public int getiColumns() {
		return iColumns;
	}

	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}