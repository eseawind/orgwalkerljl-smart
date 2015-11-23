/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain.sys;

import java.util.Date;

import org.walkerljl.smart.domain.BaseDomain;
import org.walkerljl.smart.enums.identity.Sex;

/**
 * 用户
 * 
 * @author lijunlin
 */
public class User extends BaseDomain {
	
	private static final long serialVersionUID = 1L;

	/** 用户Id*/
	private String userId;
	/** 用户名*/
	private String userName;
	/** 性别*/
	private String sex;
	/** 邮箱号码*/
	private String email;
	/** 手机*/
	private String mobile;
	/** 座机*/
	private String telephone;
	/** 身份证号码*/
	private String idCardNumber;
	/** 出生日期*/
	private Date birthday;
	/** 最近登录日期*/
	private Date lastLoginDate;
	
	public User() {}

	//自定义方法
	/**
	 * 获取性别类型
	 * @return
	 */
	public Sex getSexType() {
		return Sex.getType(this.sex);
	}
	
	/**
	 * 获取性别名称
	 * @return
	 */
	public String getSexName() {
		Sex type = getSexType();
		return type == null ? "" : type.getName();
	}
	
	//getters and setters
	/**
	 * 获取用户Id
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * 设置用户Id
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 获取性别
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取 电子邮件
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置 电子邮件
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 获取 手机
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置 手机
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 获取 座机
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * 设置 座机
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取身份证号码
	 * @return
	 */
	public String getIdCardNumber() {
		return idCardNumber;
	}

	/**
	 * 设置身份证号码
	 * @param idCardNumber
	 */
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	/**
	 * 获取出生日期
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置出生日期
	 * @param birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取最近登录日期
	 * @return
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * 设置最近登录日期
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}