/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.walkerljl.commons.Constants;
import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.data.model.tree.ManyNodeTree;
import org.walkerljl.commons.data.model.tree.ManyTreeNode;
import org.walkerljl.commons.data.model.tree.TreeNode;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.commons.util.UrlEncoderUtils;
import org.walkerljl.config.ConfiguratorFactory;
import org.walkerljl.smart.domain.sys.Menu;
import org.walkerljl.smart.domain.sys.User;
import org.walkerljl.smart.mvc.servlet.ServletContext;
import org.walkerljl.sso.sdk.UserAuthenticationTicket;
import org.walkerljl.sso.sdk.UserAuthenticationTicketFactory;

/**
 * DefaultMvcSupporter 
 *
 * @author lijunlin
 */
public class DefaultMvcSupporter implements MvcSupporter {
	
	@Value(value = "${sso.auth.cookie.domain}")
	private String ssoAuthCookieDomain;
	@Value(value = "${sso.auth.cookie.name}")
	private String ssoAuthCookieName;
	@Value(value = "${sso.auth.cookie.key}")
	private String ssoAuthCookieKey;
	
	@Value(value = "${sso.auth.address}")
	private String ssoAuthAddress;
	@Value(value = "${sso.return.address}")
	private String ssoReturnAddress;
	
	/** 默认菜单构建时间限制*/
	private static final long DEFAULT_MENU_BUILD_TIME_LIMIT = 10 * 1000;
	
	@Override
	public boolean isLogin() {
		UserAuthenticationTicket ticket = getLoginTicket();
		return ticket != null && ticket.isLogin();
	}
	
	@Override
	public void login() {
		MvcUtils.sendRedirect(ssoAuthAddress + "?returnAddress=" 
				+ UrlEncoderUtils.encode(ssoReturnAddress, Constants.DEFAULT_CHARSET));
	}
	
	@Override
	public String getSsoLoginAddress() {
		return ssoAuthAddress;
	}
	
	@Override
	public void logoutAndRedirectToLogin(HttpServletResponse response) {
		logout(response);
		login();
	}
	
	@Override
	public void logout(HttpServletResponse response) {
		new UserAuthenticationTicketFactory(ssoAuthCookieDomain, ssoAuthCookieName, ssoAuthCookieKey).deleteTicket(response);
	}
	
	@Override
	public User getCurrentUser() {
		UserAuthenticationTicket ticket = getLoginTicket();
		if (ticket != null && ticket.isLogin()) {
			User user = new User();
			user.setUserId(ticket.getUserId());
			user.setUserName(ticket.getUserName());
			return user;
		}
		return null;
	}
	
	private UserAuthenticationTicket getLoginTicket() {
		UserAuthenticationTicket ticket = 
				new UserAuthenticationTicketFactory(ssoAuthCookieDomain, ssoAuthCookieName, ssoAuthCookieKey)
					.getTicket(ServletContext.getRequest());
		return ticket;
	}
	
	@Override
	public String getCurrentUserId() {
		User user = getCurrentUser();
		return user == null ? null : user.getUserId();
	}

	@Override
	public boolean isAdmin(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return false;
		}
		Set<String> administrators = ConfiguratorFactory.getInstance().getPropertyAsSet(String.class, "administrators", ",");
		return administrators != null && administrators.contains(userId);
	}

	@Override
	public boolean checkUserAuth(String userId, String authCode) {
		return false;
	}

	@Override
	public Map<String, Object> getBussinessContext() {
		return null;
	}

	@Override
	public String getMenuBar(String userId, String contextPath, String uri) {
		ManyNodeTree manyNodeTree = createMenuTree(queryCurrentUserAuthMenus(userId));
		if (manyNodeTree == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder("<ul class=\"nav nav-list\">");
		List<ManyTreeNode> manyTreeNodes = manyNodeTree.getRootNode().getChildren();
		for (ManyTreeNode manyTreeNode : manyTreeNodes) {
			TreeNode treeNode = manyTreeNode.getNode();
			List<ManyTreeNode> children = manyTreeNode.getChildren();
			boolean isActiveParent = isActiveParent(uri, children);
			if (children.size() > 0) {
				sb.append("<li"+(isActiveParent?" class=\"open active\"":"")+">");
				sb.append("<a href=\"#\" class=\"dropdown-toggle\"><i class=\""+treeNode.getIcon()+"\"></i><span>").append(treeNode.getNodeName()).append("</span><b class=\"arrow icon-angle-down\"></b></a>");
				sb.append("<ul class=\"submenu\"").append((isActiveParent?" style=\"display:block;\"":"")).append(">");
				for (ManyTreeNode subManyTreeNode : manyTreeNode.getChildren()) {
					String subUrl = subManyTreeNode.getNode().getUrl();
					boolean isActive = StringUtils.equals(subUrl, uri);
					sb.append("<li "+(isActive?"class=\"active\"":"")+"><a href=\"").append(StringUtils.isBlank(subUrl)?'#':contextPath+subUrl).append("\"><i class=\"icon-double-angle-right\"></i>").append(subManyTreeNode.getNode().getNodeName()).append("</a></li>");
				}
				sb.append("</ul>");
			} else {
				sb.append("<li "+(uri.equals(treeNode.getUrl())?" class=\"active\"":"")+">");
				sb.append("<a href=\""+contextPath+treeNode.getUrl()+"\"><i class=\""+treeNode.getIcon()+"\"></i><span>"+treeNode.getNodeName()+"</span></a>");
				sb.append("</li>");
			}
			sb.append("</li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}
	
	/**
	 * 创建菜单树
	 * @param menus
	 * @return
	 */
	private ManyNodeTree createMenuTree(List<Menu> menus) {
		if (ListUtils.isEmpty(menus)) {
			return null;
		}
		TreeSet<TreeNode> treeNodes = new TreeSet<TreeNode>();
		for (Menu menu : menus) {
			TreeNode treeNode = new TreeNode();
			treeNode.setNodeId(Long.toString(menu.getId()));
			treeNode.setNodeName(menu.getName());
			treeNode.setParentNodeId(Long.toString(menu.getParentId()));
			treeNode.setUrl(menu.getUrl());
			treeNode.setIcon(menu.getIcon());
			treeNode.setOrderSequence(menu.getOrder());
			treeNodes.add(treeNode);
		}
		ManyNodeTree manyNodeTree = ManyNodeTree.createTree("-1", "root", treeNodes, DEFAULT_MENU_BUILD_TIME_LIMIT);
		return manyNodeTree;
	}
	
	/**
	 * 是否激活父亲
	 * @param uri
	 * @param manyTreeNodes
	 * @return
	 */
	private boolean isActiveParent(String uri, List<ManyTreeNode> manyTreeNodes) {
		boolean flag = false;
		for (ManyTreeNode manyTreeNode : manyTreeNodes) {
			if (StringUtils.equals(manyTreeNode.getNode().getUrl(), uri)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public boolean isLoadButtonBar() {
		return false;
	}

	@Override
	public List<Menu> queryCurrentUserAuthMenus(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}