/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain;

import java.util.Date;

import org.walkerljl.smart.enums.Status;

/**
 * BaseDomain
 * 
 * @author lijunlin
 */
public class BaseDomain extends Page<Object> {
	
	private static final long serialVersionUID = 1L;

	/** 编号*/
	private Long id;
	
	/** 唯一编号*/
	private transient String uuid;
	
	/** 名称*/
	private String name;
	
	/** 编码*/
	private String code;
	
	/** 备注*/
    private String remark;
    
    /** 创建日期*/
    private Date createDate;
    
    /** 创建者id*/
    private String createUserId;
    
    /** 最后修改日期*/
    private Date lastModifyDate;
    
    /** 创建者姓名*/
    private String createUserName;
    
    /** 最后修改者id*/
    private String lastModifyUserId;
    
    /** 最后修改者姓名*/
    private String lastModifyUserName;
    
    /** 状态  {@link org.walkerljl.smart.enums.Status}*/
    private transient Integer status;
    
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
	 * 获取编码
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置编码
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取创建日期
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建日期
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取创建者id
	 * @return
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 *设置创建者id
	 * @param createUserId
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	/**
	 * 获取创建者姓名
	 * @return
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置创建者姓名
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取最后修改时间
	 * @return
	 */
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * 设置最后修改时间
	 * @param lastModifyDate
	 */
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	/**
	 * 获取最后修改者id
	 * @return
	 */
	public String getLastModifyUserId() {
		return lastModifyUserId;
	}

	/**
	 * 设置最后修改者id
	 * @param lastModifyUserId
	 */
	public void setLastModifyUserId(String lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}
	
	/**
	 * 获取最后修改者姓名
	 * @return
	 */
	public String getLastModifyUserName() {
		return lastModifyUserName;
	}

	/**
	 * 设置最后修改者姓名
	 * @param lastModifyUserName
	 */
	public void setLastModifyUserName(String lastModifyUserName) {
		this.lastModifyUserName = lastModifyUserName;
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