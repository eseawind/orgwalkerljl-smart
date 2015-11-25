/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.web;

import org.walkerljl.smart.mvc.init.DefaultStartUp;

/**
 * CustomizedStartUp 
 *
 * @author lijunlin
 */
public class CustomizedStartUp extends DefaultStartUp {
	
	static  {
		System.setProperty("orgwalkerljl.log", "slf4j");
	}
	@Override
	public void subProcess() {
	}
}
