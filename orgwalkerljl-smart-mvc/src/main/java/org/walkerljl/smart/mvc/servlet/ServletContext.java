/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.walkerljl.commons.exception.AppException;

/**
 * ServletContext
 * 
 * @author lijunlin<walkerljl@qq.com>
 */
public class ServletContext {

	private static final ThreadLocal<ServletContext> SERVLET_CONTEXT = new ThreadLocal<ServletContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;
    private ApplicationContext applicationContext;

	private ServletContext() {
		SERVLET_CONTEXT.set(this);
	}
	
	public static final void initContext(HttpServletRequest request, HttpServletResponse response,
            ApplicationContext applicationContext) {
        ServletContext context = new ServletContext();
        context.request = request;
        context.response = response;
        context.applicationContext = applicationContext;
    }

	public static ServletContext getServletContext() {
		return SERVLET_CONTEXT.get();
	}

	public static final void remove() {
		SERVLET_CONTEXT.remove();
	}
	
	public static final HttpServletRequest getRequest() {
		ServletContext context = getServletContext();
		if (context == null) {
			return null;
		}
		return (HttpServletRequest) context.request;
	}

	public static final HttpServletResponse getResponse() {
		ServletContext context = getServletContext();
		if (context == null) {
			return null;
		}
		return context.response;
	}
	
	public static PrintWriter getResponseWriter() {
		HttpServletResponse response = getResponse();
		try {
			return response == null ? null : response.getWriter();
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	public static final ApplicationContext getApplicationContext() {
		ServletContext context = getServletContext();
		if (context == null) {
			return null;
		}
		return context.applicationContext;
	}

	public static final String getContextPath() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return null;
		}
		return (String) request.getContextPath();
	}

	public static final String getRequestURI() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return null;
		}
		return (String) request.getRequestURI();
	}
	
    public static final void setAttribute(String key, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(key, value);
        }
    }
    
    public static final Object getAttribute(String key) {
        HttpServletRequest request = getRequest();
        if (request == null) {
        	return null;
        }
        return request.getAttribute(key);
    }
}