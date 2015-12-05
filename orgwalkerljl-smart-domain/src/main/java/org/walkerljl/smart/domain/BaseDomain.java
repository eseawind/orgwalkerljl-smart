/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain;

import java.util.Date;

import org.walkerljl.db.api.annotation.Column;
import org.walkerljl.smart.enums.Status;

/**
 * BaseDomain
 * 
 * @author lijunlin
 */
public class BaseDomain extends Page<Object> {
	
	private static final long serialVersionUID = 1L;

	/** 编号*/
	@Column(key = true, value = "id")
	private Long id;
	
	/** 唯一编号*/
	private transient String uuid;
	
	/** 名称*/
	@Column("name")
	private String name;
	
	/** 备注*/
	@Column("remark")
    private String remark;
    
    /** 状态  {@link org.walkerljl.smart.enums.Status}*/
	@Column("status")
    private transient Integer status;
    
	/** 创建者*/
	@Column("creator")
    private String creator;
	
	/** 创建时间*/
	@Column("created_time")
    private Date createdTime;
    
	@Column("modifier")/** 修改者*/
    private String modifier;
	
	/** 修改时间*/
	@Column("modified_time")
    private Date modifiedTime;
	
    /**
     * 默认构造函数
     */
    public BaseDomain() {}

    //================ getter and setter
    /**
     * 获取编号
     * @return
     */
	public Long getId() {
		return id;
	}

    /**
     * 设置编号
     * @param id
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取唯一编号
	 * @return
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 设置唯一编号
	 * @param uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * 获取名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取状态值
	 * @return
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * 设置状态值
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 获取创建者
	 * @return
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置创建者
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * 获取创建时间
	 * @return
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置创建日期
	 * @param createdTime
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取修改者
	 * @return
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * 设置修改者
	 * @param modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 获取修改时间
	 * @return
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * 设置修改时间
	 * @param modifiedTime
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	//============== 自定义方法
	/**
	 * 是否启用
	 * @return
	 */
	public boolean isEnabled() {
		return getStatusType() == Status.ENABLED;
	}
	
	/**
	 * 是否停用
	 * @return
	 */
	public boolean isDisabled() {
		return getStatusType() == Status.DISABLED;	
	}
	
	/**
	 * 是否删除
	 * @return
	 */
	public boolean isDeleted() {
		return getStatusType() == Status.DELETED;
	}
	
	/**
	 * 设置状态类型
	 * @param status
	 */
	public void setStatusType(Status status) {
		this.status = status.getValue();
	}
	
	/**
	 * 获取状态类型
	 * @return
	 */
	public Status getStatusType() {
		return Status.getType((status == null) ? 0 : status);
	}
	
	/**
	 * 获取状态名称
	 * @return
	 */
	public String getStatusName() {
		Status status = getStatusType();
		return status == null ? "未知状态" : status.getName();
	}
}