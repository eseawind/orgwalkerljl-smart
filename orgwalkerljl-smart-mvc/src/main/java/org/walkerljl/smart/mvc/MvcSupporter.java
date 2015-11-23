/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.walkerljl.smart.domain.sys.Menu;
import org.walkerljl.smart.domain.sys.User;

/**
 * MvcSupporter
 * 
 * @author lijunlin
 */
public interface MvcSupporter {

	/**
	 * 是否已经登录
	 * @return
	 */
	boolean isLogin();
	
	/**
	 * 登录
	 */
	void login();
	
	/**
	 * 获取单点登录地址
	 * @return
	 */
	String getSsoLoginAddress();
	
	/**
	 * 注销
	 * @param response
	 */
	void logoutAndRedirectToLogin(HttpServletResponse response);
	
	/**
	 * 注销
	 * @param response
	 */
	void logout(HttpServletResponse response);
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	User getCurrentUser();
	
	/**
	 * 获取当前登录用户Id
	 * @return
	 */
	String getCurrentUserId();
	
	/**
	 * 是否超级管理员
	 * @param userId
	 * @return
	 */
	boolean isAdmin(String userId);
	
	/**
	 * 检测用户权限
	 * @param userId
	 * @param authCode
	 * @return
	 */
	boolean checkUserAuth(String userId, String authCode);
	
	/**
	 * 获取自定义Context
	 * @return
	 */
	Map<String, Object> getBussinessContext();
	
	/**
	 * 获取菜单条
	 * @param userId
	 * @param contextPath
	 * @param uri
	 * @return
	 */
	String getMenuBar(String userId, String contextPath, String uri);
	
	/**
	 * 查询当前登录用户的菜单
	 * @param userId 当前登录用户ID
	 * @return
	 */
	List<Menu> queryCurrentUserAuthMenus(String userId);
	
	/**
	 * 是否加载按钮条
	 * @return
	 */
	boolean isLoadButtonBar();
}