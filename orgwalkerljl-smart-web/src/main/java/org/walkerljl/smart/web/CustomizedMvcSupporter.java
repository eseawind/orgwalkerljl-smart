/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.web;

import java.util.Map;

import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.datetime.DateUtils;
import org.walkerljl.smart.mvc.DefaultMvcSupporter;

/**
 * CustomizedMvcSupporter 
 *
 * @author lijunlin
 */
public class CustomizedMvcSupporter extends DefaultMvcSupporter {
	
	@Override
	public Map<String, Object> getBussinessContext() {
		Map<String, Object> context = MapUtils.newHashMap();
		context.put("dateUtils", DateUtils.class);
		context.put("isLogin", isLogin());
		return context;
	}
}