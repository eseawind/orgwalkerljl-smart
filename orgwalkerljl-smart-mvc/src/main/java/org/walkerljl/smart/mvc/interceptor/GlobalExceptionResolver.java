/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.walkerljl.commons.Constants;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.util.StringPool;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.mvc.MvcUtils;
import org.walkerljl.smart.mvc.servlet.ServletContext;
import org.walkerljl.smart.mvc.util.JSONUtils;

/**
 * GlobalExceptionResolver
 * 
 * @author lijunlin
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	/** 应用名称*/
	private String appName;
	
	/**
	 * 全局异常处理
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Boolean resource = (Boolean)request.getAttribute(Constants.STATIC_RES_REQUEST);
	    if (resource != null && resource) {//静态资源
	    	return null;
	    }
		StringBuilder details = new StringBuilder();
		StringWriter errInfo = null;
		try {
			details.append("请求[").append(request.getRequestURI()).append("]异常.");
			details.append("请求参数:"+JSONUtils.toJSONString(request.getParameterMap())).append(".");
			errInfo = new StringWriter();
			ex.printStackTrace(new PrintWriter(errInfo));
			details.append("异常信息[").append( ex.getMessage()).append("].");
			details.append("异常详细信息[").append(errInfo.toString()).append("]");
			LOG.error(details.toString());
			
			//发送错误邮件
			String errMessage = StringUtils.isNotBlank(ex.getMessage()) ? "操作失败:" + ex.getMessage() : "系统操作失败,请联系系统维护人员";
			if (request.getRequestURI().endsWith(StringPool.JSON_SUFFIX)) {
				Map<String, Object> context = MapUtils.newHashMap();
				context.put(StringPool.RESPONSE_STATUS_KEY, false);
				context.put(StringPool.RESPONSE_MESSAGE_KEY, errMessage);
				JSONUtils.write(response.getWriter(), context, null);
			} else {
				MvcUtils.sendRedirect(ServletContext.getContextPath() + "/error");
			}
		} catch (Exception e) {
			LOG.error("处理全局异常出错", e);
		}
		return null;
	}

	/**
	 * 获取应用名称
	 * @return
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置应用名称
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
}