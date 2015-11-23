/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ViewResult
 * 
 * @author lijunlin
 */
public class ViewResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_KEY = "value";
	private Map<String, Object> data = new HashMap<String, Object>();
	
	public void setPageTitle(String pageTitle) {
		addModel("pageTitle", pageTitle);
	}
	
	public void setAlertMessage(String msg) {
		addModel("alertMsg", msg);
	}
	
	public void setDefaultModel(Object value) {
		addModel(DEFAULT_KEY, value);
	}
	
	public void addModel(String key, Object value) {
		data.put(key, value);
	}
	
	public Object remove(String key) {
		return data.remove(key);
	}
	
	public Object get() {
		return get(DEFAULT_KEY);
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	
    public String getAsString(String key){
        Object obj = get(key);   
        return obj == null ? "" : obj.toString();
    }

    public boolean getAsBoolean(String key){
        return Boolean.valueOf(getAsString(key)).booleanValue();
    }

	public Set<String> keySet() {
		return data.keySet();
	}

	public Collection<Object> values() {
		return data.values();
	}

	public Map<String, Object> getMap() {
		return data;
	}
}