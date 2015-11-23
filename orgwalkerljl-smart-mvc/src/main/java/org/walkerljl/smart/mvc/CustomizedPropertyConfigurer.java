/*
 * Copyright (c) 2013 lijunlin All rights reserved.
 * 本软件源代码版权归作者所有,未经许可不得任意复制与传播.
 */
package org.walkerljl.smart.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.walkerljl.commons.security.EncryptUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 * 自定义spring属性文件加载器
 * 
 * @author lijunlin
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomizedPropertyConfigurer.class);

	private static final String[] ENCRYPT_PROPERTY_NAMES = {}; 
	
	@Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        for (Entry<Object, Object> entry : props.entrySet()) {
            updateSystemConfig(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()));
        }
    }
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {		
		if(isEncryptProperty(propertyName)){
			return EncryptUtils.decryptByDES(propertyValue);
		}else{
			return propertyValue;
		}
	}
	
	/**
	 * 判断是否是需要进行解密的属性
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProperty(String propertyName) {
		for (String encryptPropertyName : ENCRYPT_PROPERTY_NAMES) {
			if (propertyName.equals(encryptPropertyName)) {
				return true;
			}
		}
		return false;
	}
	
	private static final Map<String, String> SYSTEM_PROPERTIES = new HashMap<String, String>();
	
	private void updateSystemConfig(String key, String value) {
		if (SYSTEM_PROPERTIES.containsKey(key)) {
			String oldValue = SYSTEM_PROPERTIES.get(key);
			if (!oldValue.equals(value)) {
				LOG.debug(String.format("系统配置[%s]的值由[%s]更新为:[%s]",new Object[]{key,oldValue,value}));
			}
		}
	}
}