/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.velocity;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.walkerljl.commons.Constants;
import org.walkerljl.smart.service.ApplicationContextUtils;

/**
 * VelocityProxy
 * 
 * @author lijunlin
 */
public class VelocityProxy {
	
	//从Spring上下文对象中获取Velocity初始化对象
	private static VelocityEngine getEngine(){
		VelocityConfigurer velocityConfigurer = ApplicationContextUtils.getBean("velocityConfigurer");
		return velocityConfigurer.getVelocityEngine();
	}
	
	public static String getVMDir(){
	    ServletContext context = ApplicationContextUtils.getBean(ServletContext.class);
	    return context.getRealPath("/WEB-INF/vm");
    }
		
	/**
	 * 获取Velocity页面
	 * @param data
	 * @param vm
	 * @return
	 */
	public static String merge(Map<String,Object> data, String vm) {
		Template template = getEngine().getTemplate(vm, Constants.DEFAULT_ENCODING);
		VelocityContext context = new VelocityContext(data);
		context.put("DateUtils", DateUtils.class);
		context.put("cmd", "$");
		context.put("cmd2", "#");
		StringWriter w = new StringWriter();
		template.merge(context, w);
		return w.toString();
	}
}