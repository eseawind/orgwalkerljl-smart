/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.enums;


/**
 * Status
 * 
 * @author lijunlin<walkerljl@qq.com>
 */
public enum Status {
	
	/**
	 * 启用
	 */
	ENABLED(1, "启用"),
	
	/** 
	 * 停用
	 */
	DISABLED(2, "停用"),
	
	/** 
	 * 删除
	 */
	DELETED(3, "删除");
	
	/** 值*/
	private Integer value;
	/** 名称/描述*/
	private String name;
	
	private Status(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * 获取类型值
	 * @return
	 */
	public Integer getValue(){
		return this.value;
	}
	
	/**
	 * 获取枚举描述
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 获取状态类型
	 * @param value 状态类型值
	 * @return
	 */
	public static Status getType(Integer value) {
		if (value == null) {
			return null;
		}
		for (Status status : Status.values()) {
			if (status.getValue().intValue() == value.intValue()) {
				return status;
			}
		}
		return null;
	}
}