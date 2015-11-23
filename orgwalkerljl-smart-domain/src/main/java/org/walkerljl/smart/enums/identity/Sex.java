/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.enums.identity;
/**
 * 性别
 *
 * @author lijunlin<walkerljl@qq.com>
 */
public enum Sex {

	/**
	 * 男
	 */
	MALE("m", "男"),
	
	/**
	 * 女
	 */
	FEMALE("f", "女");
	
	private String value;
	private String name;
	
	private Sex(String value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 获取性别代码
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取性别名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取类型
	 * @param value
	 * @return
	 */
	public static Sex getType(String value) {
		for (Sex item : Sex.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}