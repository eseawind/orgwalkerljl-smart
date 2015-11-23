/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.walkerljl.commons.Constants;
import org.walkerljl.commons.auth.AuthType;
import org.walkerljl.commons.auth.Authentication;
import org.walkerljl.commons.auth.Authenticator;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.exception.AppException;
import org.walkerljl.commons.util.StringPool;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.config.ConfiguratorFactory;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.mvc.MvcSupporter;
import org.walkerljl.smart.mvc.MvcUtils;
import org.walkerljl.smart.mvc.servlet.ServletContext;
import org.walkerljl.smart.mvc.util.JSONUtils;

/**
 * AppInterceptor
 * 
 * @author lijunlin
 */
public abstract class AppInterceptor extends HandlerInterceptorAdapter {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Resource private Authenticator authenticator;
	@Resource private MvcSupporter mvcSupporter;
    @Resource private ApplicationContext applicationContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
		boolean result = true;
		try {
			 //设置请求响应编码
	        request.setCharacterEncoding(Constants.DEFAULT_CHARSET);
	        response.setCharacterEncoding(request.getCharacterEncoding());
	        
	        if (!(handler instanceof HandlerMethod)) {
				request.setAttribute(Constants.STATIC_RES_REQUEST, true);
				return result;
			}
			//设置本次请求上下文
	        ServletContext.initContext(request, response, applicationContext);

	        //权限校验
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Authentication clazz = handlerMethod.getBean().getClass().getAnnotation(Authentication.class);
			Authentication method = handlerMethod.getMethodAnnotation(Authentication.class);
			result = doAuthentication(clazz, method);
		} catch (Exception e) {
			result = false;
			String errMsg = "AppInterceptor exception, 详细:" + e.getMessage();
			LOG.error(errMsg, e);
			throw new AppException(errMsg, e);
		}
		return result;
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
		ServletContext.remove();
	}
	
	/**
	 * 处理权限校验
	 * @param clazz
	 * @param method
	 * @return
	 */
	private boolean doAuthentication(Authentication clazz, Authentication method) {
		boolean result = true;
		AuthType authType = authenticator.getType(clazz, method);
		boolean validateIsLogin = false;
		boolean validateAuthCode = false;
		
		if (authType == AuthType.LOGIN) {
			validateIsLogin = true;
		} else if (authType == AuthType.CODE) {
			validateIsLogin = true;
			validateAuthCode= true;
		}
		//登录校验
		if (validateIsLogin) {
			result = mvcSupporter.isLogin();
			if (result) {
				result = processSystemUpgrading();
			} else {
				boolean isJsonRequest = ServletContext.getRequestURI().endsWith(StringPool.JSON_SUFFIX);
				if (isJsonRequest) {
					Map<String, Object> context = MapUtils.newHashMap();
					context.put(StringPool.RESPONSE_STATUS_KEY, false);
					context.put(StringPool.RESPONSE_MESSAGE_KEY, "您没有权限进行此操作,请联系系统管理员");
					context.put(StringPool.RESPONSE_DATA_KEY, "notLogin");
					JSONUtils.write(ServletContext.getResponseWriter(), context, null);
				} else {
					mvcSupporter.login();
				}
			}
		}
		//权限校验
		if (result && validateAuthCode) {
			String authCode = authenticator.getAuthCode(clazz, method);
			result = validateAuthCode(authCode);
		}
		return result;
	}
	
	/**
	 * 校验权限码
	 * @param authCode
	 * @return
	 */
	private boolean validateAuthCode(String authCode) {
		boolean result = true;
		if (StringUtils.isBlank(authCode)) {
			return result;
		}
		
		String currentUserId = mvcSupporter.getCurrentUserId();
		if (mvcSupporter.isAdmin(currentUserId)) {
			return result;
		}
		result = mvcSupporter.checkUserAuth(currentUserId, authCode);
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("权限认证：userId=%s, authCode=%s, isAccess=%s", 
					new Object[]{currentUserId, authCode, result}));
		}
		if (!result) {
			boolean isJsonRequest = ServletContext.getRequestURI().endsWith(StringPool.JSON_SUFFIX);
			if (isJsonRequest) {
				Map<String, Object> context = MapUtils.newHashMap();
				context.put(StringPool.RESPONSE_STATUS_KEY, false);
				context.put(StringPool.RESPONSE_MESSAGE_KEY, "您没有权限进行此操作,请联系系统管理员");
				JSONUtils.write(ServletContext.getResponseWriter(), context, null);
			} else {
				jumpToAccessDeniedPage();
			}
		}
		return result;
	}
	
	/**
	 * 系统升级拦截
	 * @return
	 */
	private boolean processSystemUpgrading() {
		boolean result = true;
		Boolean systemUpgrding = ConfiguratorFactory.getInstance().getPropertyAsBoolean("system.upgrading");
		if (systemUpgrding != null && systemUpgrding && !mvcSupporter.isAdmin(mvcSupporter.getCurrentUserId())) {
			jumpToUpgradingPage();
			result = false;
		}
		return result;
	}
	
	/**
	 * 跳转到系统升级提示页面(可重写)
	 */
	public void jumpToUpgradingPage() {
		MvcUtils.sendRedirect(ServletContext.getContextPath() + "/upgrading");
	}
	
	/**
	 * 跳转到拒绝访问页面(可重写)
	 */
	public void jumpToAccessDeniedPage() {
		MvcUtils.sendRedirect(ServletContext.getContextPath() + "/accessDenied");
	}
}