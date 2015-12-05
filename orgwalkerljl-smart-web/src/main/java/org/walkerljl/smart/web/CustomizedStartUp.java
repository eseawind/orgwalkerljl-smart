/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.web;

import java.util.List;

import javax.annotation.Resource;

import org.walkerljl.commons.collection.CollectionUtils;
import org.walkerljl.smart.dao.sys.SysConfigDao;
import org.walkerljl.smart.domain.sys.SysConfig;
import org.walkerljl.smart.mvc.init.DefaultStartUp;

/**
 * CustomizedStartUp 
 *
 * @author lijunlin
 */
public class CustomizedStartUp extends DefaultStartUp {
	
	@Resource private SysConfigDao sysConfigDao;
	
	static  {
		System.setProperty("orgwalkerljl.log", "slf4j");
	}
	@Override
	public void subProcess() {
		for (int i = 0; i < 10; i++) {
			List<SysConfig> sysConfigs = sysConfigDao.selectListByKeys(1L, 2L, 3L);
			if (CollectionUtils.isNotEmpty(sysConfigs)) {
				for (SysConfig sysConfig : sysConfigs) {
					System.out.println(sysConfig.toString());
				}
			}
		}
	}
}
