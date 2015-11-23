/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.enums;

/**
 * 编辑类型
 * 
 * @author lijunlin
 */
public enum EditType {

	/**
	 * 新增
	 */
	ADD("add", "新增"),
	
	/**
	 * 修改
	 */
	EDIT("edit", "修改"),
	
	/**
	 * 详情
	 */
	VIEW("view", "详情");
	
	private String value;
	private String name;

	private EditType(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
