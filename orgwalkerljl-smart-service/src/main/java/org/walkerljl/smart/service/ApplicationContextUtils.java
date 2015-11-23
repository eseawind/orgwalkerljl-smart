/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextUtils
 * 
 * @author lijunlin
 */
public class ApplicationContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		if(context != null) {
			return (T)context.getBean(name);
		}
		return null;
	}
	
	public static <T> T getBean(Class<T> clz){
		if(context != null) {
			return context.getBean(clz);
		}
		return null;
	}
}