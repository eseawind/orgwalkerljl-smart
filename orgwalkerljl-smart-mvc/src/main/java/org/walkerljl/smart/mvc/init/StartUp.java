/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.init;

import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 * StartUp 
 *
 * @author lijunlin
 */
public abstract class StartUp {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	public static String vTime = "";
	
	//@Resource private AppConfigDao appConfigDao;    

	public void start() {
		LOG.info("Initializing system data");
		try {
			if ("".equals(vTime)) {
				vTime = System.currentTimeMillis() + "";
			}
			//ConfiguratorFactory.init(new DefaultAppConfigLoader());
			subProcess();
		} catch (Exception e) {
			LOG.info("Fail to initialize system data:" + e.getMessage(), e);
		} 
		LOG.info("Finished to initialize system data");
	}
	
//	private class DefaultAppConfigLoader implements AppConfigLoader {
//		@Override
//		public List<AppConfig> loadAll() {
//			return appConfigDao.selectList(null);
//		}
//	}
	
	/**
	 * 子过程
	 */
	public abstract void subProcess();
}