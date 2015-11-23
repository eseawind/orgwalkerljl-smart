/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.enums.sys;


/**
 * SysLogOperateType
 * 
 * @author lijunlin
 */
public enum SysLogOperateType {

	/**
	 * 添加
	 */
	ADD(1, "添加"),
	
	/**
	 * 修改
	 */
	UPDATE(2, "修改"),
	
	/**
	 * 查看
	 */
	VIEW(3, "查看"),
	
	/**
	 * 启用
	 */
	ENABLE(4, "启用"),
	
	/**
	 * 停用
	 */
	DISABLE(5, "停用"),
	
	/**
	 * 删除
	 */
	DELETE(6, "删除"),
	
	/**
	 * 物理删除
	 */
	PHYSICS_DELETE(7, "物理删除");
	
	private Integer value;
	private String name;
	
	private SysLogOperateType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 获取类型
	 * @param value
	 * @return
	 */
	public SysLogOperateType getType(Integer value) {
		if (value == null) {
			return null;
		}
		for (SysLogOperateType type : SysLogOperateType.values()) {
			if (type.getValue().intValue() == value.intValue()) {
				return type;
			}
		}
		return null;
	}
}