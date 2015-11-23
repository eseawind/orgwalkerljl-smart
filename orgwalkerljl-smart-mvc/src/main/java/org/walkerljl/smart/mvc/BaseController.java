/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.walkerljl.commons.Constants;
import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.datetime.DateUtils;
import org.walkerljl.commons.exception.AppException;
import org.walkerljl.commons.util.StringPool;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.smart.domain.BaseDomain;
import org.walkerljl.smart.domain.ViewResult;
import org.walkerljl.smart.domain.sys.SysLog;
import org.walkerljl.smart.domain.sys.User;
import org.walkerljl.smart.enums.Status;
import org.walkerljl.smart.enums.sys.SysLogOperateType;
import org.walkerljl.smart.mvc.security.Button;
import org.walkerljl.smart.mvc.servlet.ServletContext;
import org.walkerljl.smart.mvc.util.JSONUtils;
import org.walkerljl.smart.mvc.velocity.VelocitySupport;
import org.walkerljl.smart.mvc.velocity.VelocityUtils;

/**
 * BaseController
 * 
 * @author lijunlin
 * 
 */
public abstract class BaseController extends VelocitySupport {
	
	/** 对象标识符Key*/
	private static final String OBJECT_IDENTIFER_KEY = "objectIdentifer";
	/** 页面标题Key*/
	private static final String PAGE_TITLE_KEY = "pageTitle";
	/** 当前URL Key*/
	private static final String CURRENT_URL_KEY = "currentUrl";
	/** 系统菜单条Key*/
	private static final String SYSTEM_MENUBAR_KEY = "systemMenu";
	/** 按钮条Key*/
	private static final String BUTTON_BAR_KEY = "buttonBar";
	/** 编辑表单Key*/
	private static final String EDIT_FORM_KEY = "editForm";
	/** 默认系统日志模块*/
	private static final String DEFAULT_SYSLOG_MODULE = "通用日志模块";
	
	/** 应用名称*/
	@Value(value = "${app.code}")
	private String appCode;
	/** 应用名称*/
	@Value(value = "${app.name}")
	private String appName;
	@Value(value = "${static.resource.address}")
	private String staticResourceAddress;
	
	/** 对象标识符*/
	private String objectIdentifer;
	/** 功能更模块标题*/
	private String pageTitle;
	/** 当前功能模块URL*/
	private String currentUrl;
	/** 是否加载菜单条*/
	private boolean isLoadMenuBar = true;
	/** 模板基础路径*/
	private String templateBasePath;
	/**　默认布局模板*/
	private String layout = "layout/default";
	
	/** 自定义系统日志模块*/
	private String customizedSysLogModule = null;
	/** 权限码前缀*/
	private String authCodePrefix;
	/** 自定义按钮条*/
	private List<Button> customizedButtonBar = ListUtils.newArrayList();
	
	@Resource private MvcSupporter mvcSupporter;
	
	public BaseController() {}
	
	@PostConstruct
	public void init() {
		if (StringUtils.isEmpty(templateBasePath)) {
			templateBasePath = StringPool.SLASH + getObjectIdentifer();
		}
		if (StringUtils.isEmpty(currentUrl)) {
			currentUrl = StringPool.SLASH + getObjectIdentifer();
		}
		if (StringUtils.isBlank(authCodePrefix)) {
			authCodePrefix = getAppCode() + "-" + getObjectIdentifer();
		}
	}
	
	@Override
	public Map<String, Object> getDefaultContext() {
		Map<String, Object> context = MapUtils.newHashMap();
		String contextPath = ServletContext.getContextPath();
		context.put("contextPath", contextPath);
		if (StringUtils.isNotBlank(staticResourceAddress)) {
			context.put("staticRes", staticResourceAddress);
			context.put("localStaticRes", contextPath + "/resource");
		} else {
			context.put("staticRes", contextPath + "/resource");
		}
		context.put("v", Constants.V);
		context.put("utils", VelocityUtils.class);
		context.put("dateUtils", DateUtils.class);
		User currentUser = getCurrentUser();
		context.put("currentUser", currentUser);
		context.put(PAGE_TITLE_KEY, pageTitle);
		context.put(CURRENT_URL_KEY, currentUrl);
		context.put(OBJECT_IDENTIFER_KEY, objectIdentifer);
		context.put(EDIT_FORM_KEY, getObjectIdentifer() + "EditForm");
		context.put("ssoLoginAddress", mvcSupporter.getSsoLoginAddress());
		context.put("appName", getAppName());
		context.put("appCode", getAppCode());
		context.put("formName", getObjectIdentifer() + "Form");
		context.put("currentYear", DateUtils.dateFormat(new Date(), "yyyy"));
		if (isLoadMenuBar && currentUser != null) {//是否加载菜单条
			String uri = getRequest().getRequestURI().replaceAll(contextPath, "");
			String menuBarString = mvcSupporter.getMenuBar(currentUser.getUserId(), contextPath, uri);
			if (StringUtils.isNotBlank(menuBarString)) {
				context.put(SYSTEM_MENUBAR_KEY, menuBarString);
			}
		}
		Map<String, Object> bussinessContext = mvcSupporter.getBussinessContext();
		if (bussinessContext != null) {
			context.putAll(bussinessContext);
		}
		return context;
	}

	/**
	 * 获取Servlet Request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletContext.getRequest();
	}
	
	/**
	 * 获取Servlet Response对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletContext.getResponse();
	}
	
	/**
	 * 重定向
	 * @param url
	 * @param message
	 */
	public void sendRedirect(String url, String message) {
		MvcUtils.sendRedirect(url, message);
    }
	
	/**
	 * 重定向
	 * @param url
	 */
	public void sendRedirect(String url) {
		MvcUtils.sendRedirect(url, null);
    }
	
	/**
	 * 输出数据到模板,可指定默认布局
	 * @param layout 默认布局
	 * @param view 模块
	 * @param model 数据模型
	 * @return
	 */
	protected ModelAndView toViewResult(String layout, String view, Object model) {
		return toVM(layout, view, model);
	}
	
	/**
	 * 输出数据到模板
	 * @param view 模板
	 * @param model 数据模型
	 * @return
	 */
	protected ModelAndView toViewResult(String view, Object model) {
		return toVM(layout, view, model);
	}
	
	/**
	 * 输出数据到模板
	 * @param view 模板
	 * @return
	 */
	protected ModelAndView toViewResult(String view) {
		return toVM(layout, view, null);
	}
	
	/**
	 * 输出数据到模板,跳过默认布局
	 * @param view 模板
	 * @return
	 */
	protected ModelAndView toViewResultSkipLayout(String view) {
        return toViewResultSkipLayout(view, null);
    }
	
	/**
	 * 输出数据到模板,跳过默认布局
	 * @param view 模板
	 * @param model 数据模型
	 * @return
	 */
    protected ModelAndView toViewResultSkipLayout(String view, Object model) {
    	return toVM(null, view, model);
    }
    
    /**
     * 数据JSON格式数据
     * @param data 数据模型
     * @param format key:bean自身属性,value:转换后的属性 orgID -> id, 不需要转换就传递null
     * @return
     */
	protected final ModelAndView toSimpleJSON(Object data, Map<String, String> format) {
		PrintWriter responseWriter = null;
		try {
			responseWriter = getResponse().getWriter();
		} catch (IOException e) {
			throw new AppException(e);
		}
		if (data != null && data instanceof ViewResult) {
			JSONUtils.write(responseWriter, ((ViewResult)data).getMap(), format);
		} else {
			JSONUtils.write(responseWriter, data, format);
		}
		return null;
	}
	
	/**
	 * 输出JSON格式数据
	 * @param data 数据模型
	 * @return
	 */
	protected final ModelAndView toSimpleJSON(Object data) {
		return toSimpleJSON(data, null);
	}
	
	/**
	 * 输出JSON格式数据,JSON格式经过包装
	 * @param data 数据模型
	 * @param flag 页面提示标志,true:操作成功,false:操作失败
	 * @param msg 页面提示信息
	 * @param format JSON格式化
	 * @return
	 */
	protected final ModelAndView toJSON(Object data, boolean flag, String msg, Map<String, String> format) {		
		if (null == data) {
			data = MapUtils.newHashMap();
		}
		Map<String, Object> context = MapUtils.newHashMap();
		context.put(StringPool.RESPONSE_STATUS_KEY, flag);
		context.put(StringPool.RESPONSE_MESSAGE_KEY, msg);
		if (data != null && data instanceof ViewResult) {
			context.put(StringPool.RESPONSE_DATA_KEY, ((ViewResult) data).getMap());
		} else {
			context.put(StringPool.RESPONSE_DATA_KEY, data);
		}
		JSONUtils.write(getResponseWriter(), context, format);
		return null;
	} 
	
	/**
	 * 输出JSON格式数据,JSON格式经过包装
	 * @param data 数据模型
	 * @param flag 页面提示标志,true:操作成功,false:操作失败
	 * @param msg 页面提示信息
	 * @return
	 */
	protected final ModelAndView toJSON(Object data, boolean flag, String msg) {		
		return toJSON(data, flag, msg, null);
	} 
	
	protected final ModelAndView toJSON(Object data, boolean flag) {		
		return toJSON(data, flag, flag ? "操作成功" : "操作失败", null);
	} 
	
	/**
	 * 输出JSON格式数据,JSON格式经过包装
	 * @param data 数据模型
	 * @param format JSON格式化
	 * @return
	 */
	protected final ModelAndView toJSON(Object data, Map<String, String> format) {		
		return toJSON(data, true, StringPool.EMPTY, format);
	} 
	
	/**
	 * 输出JSON格式数据,JSON格式经过包装
	 * @param data 数据模型
	 * @return
	 */
	protected final ModelAndView toJSON(Object data) {		
		return toJSON(data, true, StringPool.EMPTY, null);
	}
	
	/**
	 * 获取系统日志模块
	 * @return
	 */
	protected String getSysLogModule() {
		return customizedSysLogModule == null ?  DEFAULT_SYSLOG_MODULE : customizedSysLogModule;
	}
	
	/**
	 * 设置系统日志模块
	 * @param sysLogModule
	 */
	protected void setSysLogModule(String sysLogModule) {
		this.customizedSysLogModule = sysLogModule;
	}
	
	/**
	 * 构建系统日志
	 * @param message
	 * @param operateType
	 * @return
	 */
	protected SysLog buildSysLog(String message, SysLogOperateType operateType) {
		if (operateType == null) {
			return null;
		}
		User user = getCurrentUser();
		if (user == null) {
			return null;
		}
		SysLog sysLog = new SysLog();
//		sysLog.setModuleKey(getSysLogModule().getKey());
//		sysLog.setOperateTypeVal(operateType.getValue());
//		sysLog.setMessage(message);
//		sysLog.setUserId(user.getUserId());
//		sysLog.setUserName(user.getUserName());
//		//sysLog.setSysLogDetails(sysLogDetails);
//		sysLog.setCreateTime(new Date());
		return sysLog;
	}
	
	/**
	 * 设置布局模板
	 * @param layout
	 */
	protected void setLayout(String layout) {
		this.layout = layout;
	}
	
	/**
	 * 获取布局模板
	 * @param layout
	 */
	protected String getLayout() {
		return layout;
	}
	
	/**
	 * 设置页面标题
	 * @param pageTitle
	 */
	protected void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	/**
	 * 获取页面标题
	 * @return
	 */
	protected String getPageTitle() {
		return pageTitle;
	} 
	
	/**
	 * 设置当前URL
	 * @param currentUrl
	 */
	protected void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}
	
	/**
	 * 设置当前URL
	 * @return
	 */
	protected String getCurrentUrl() {
		return currentUrl;
	}

	/**
	 * 判断是否加载菜单条
	 * @return
	 */
	protected boolean isLoadMenuBar() {
		return isLoadMenuBar;
	}

	/**
	 * 设置是否加载菜单条
	 * @param isLoadMenuBar
	 */
	protected void setLoadMenuBar(boolean isLoadMenuBar) {
		this.isLoadMenuBar = isLoadMenuBar;
	}

	/**
	 * 获取模板基础路径
	 * @return
	 */
	protected String getTemplateBasePath() {
		return templateBasePath;
	}

	/**
	 * 设置模板基础路径
	 * @param templateBasePath
	 */
	protected void setTemplateBasePath(String templateBasePath) {
		this.templateBasePath = templateBasePath;
	}
	
	/**
	 * 获取模板
	 * @param templateName
	 * @return
	 */
	protected String getTemplate(String templateName) {
		return getTemplate(templateBasePath, templateName);
	}
	
	/**
	 * 获取模板
	 * @param templateBasePath
	 * @param templateName
	 * @return
	 */
	protected String getTemplate(String templateBasePath, String templateName) {
		StringBuilder sb = new StringBuilder();
		sb.append(templateBasePath).append(StringPool.SLASH).append(templateName);
		return sb.toString();
	}
	
	/**
	 * 初始化对象
	 * @param baseDomain
	 */
	protected void initBaseDomain(BaseDomain baseDomain) {
		if (baseDomain == null) {
			return;
		}
		User user = getCurrentUser();
		if (user != null) {
			baseDomain.setCreateUserId(user.getUserId());
			baseDomain.setCreateUserName(user.getUserName());
		}
	}
	
	protected void initBaseDomainWhenCreate(BaseDomain baseDomain) {
		if (baseDomain == null) {
			return;
		}
		User user = getCurrentUser();
		if (user != null) {
			baseDomain.setCreateUserId(user.getUserId());
			baseDomain.setCreateUserName(user.getUserName());
			baseDomain.setLastModifyUserId(baseDomain.getCreateUserId());
			baseDomain.setLastModifyUserName(baseDomain.getCreateUserName());
		}
		baseDomain.setCreateDate(new Date());
		baseDomain.setLastModifyDate(baseDomain.getCreateDate());
		baseDomain.setStatusType(Status.ENABLED);
	}
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected User getCurrentUser() {
		return mvcSupporter.getCurrentUser();
	}
	
	/**
	 * 获取当前登录用户Id
	 * @return
	 */
	protected String getCurrentUserId() {
		User currentUser = getCurrentUser();
		return currentUser == null ? null : currentUser.getUserId();
	}
	
	/**
	 * 检测用户权限
	 * @param userId
	 * @param authCode
	 * @return
	 */
	protected boolean checkUserAuth(String userId, String authCode) {
		return mvcSupporter.checkUserAuth(userId, authCode);
	}
	
	/**
	 * 是否为权限管理员
	 * @param userId
	 * @return
	 */
	protected boolean isAdministrator(String userId) {
		return mvcSupporter.isAdmin(userId);
	}
	
	protected boolean isAdministrator() {
		return mvcSupporter.isAdmin(getCurrentUserId());
	}
	
	/**
	 * 添加按钮
	 * @param button
	 */
	protected void addButton(Button button) {
		String currentUserId = getCurrentUser().getUserId();
		boolean isAdmin = isAdministrator(currentUserId);
		if (isAdmin) {
			customizedButtonBar.add(button);
		} else if (checkUserAuth(currentUserId, button.getPermissionCode())) {
			customizedButtonBar.add(button);
		}
	}

	/**
	 * 获取自定义按钮条
	 * @return
	 */
	protected List<Button> getCustomizedButtonBar() {
		return customizedButtonBar;
	}
	
	/**
	 * 获取权限码前缀
	 * @return
	 */
	protected String getAuthCodePrefix() {
		return authCodePrefix;
	}
	
	/**
	 * 设置权限码前缀
	 * @param authCodePrefix
	 */
	protected void setAuthCodePrefix (String authCodePrefix) {
		this.authCodePrefix = authCodePrefix;
	}
	
	/**
	 * 获取按钮条Key
	 * @return
	 */
	protected String getButtonBarKey() {
		return BUTTON_BAR_KEY;
	}
	
	/**
	 * 设置对象标识符
	 * @param objectIdentifer
	 */
	protected void setObjectIdentifer(String objectIdentifer) {
		this.objectIdentifer = objectIdentifer;
	}
	
	/**
	 * 获取对象标识符
	 * @return
	 */
	protected String getObjectIdentifer() {
		return objectIdentifer;
	}
	
	protected PrintWriter getResponseWriter() {
		HttpServletResponse response = getResponse();
		try {
			return response == null ? null : response.getWriter();
		} catch (IOException e) {
			throw new AppException(e);
		}
	}
	
	/**
	 * 获取应用编码
	 * @return
	 */
	protected String getAppCode() {
		return appCode;
	}
	
	/**
	 * 获取应用名称
	 * @return
	 */
	protected String getAppName() {
		return appName;
	}
}