package org.walkerljl.smart.domain.sys;

import org.walkerljl.smart.domain.BaseDomain;

/**
 * 系统配置
 *
 * @author lijunlin
 */
public class SysConfig extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	/** 配置Key*/
	private String key;
	/** 配置Value*/
	private String value;
	
	/**
	 * 默认构造函数
	 */
	public SysConfig() {}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SysConfig [key=" + key + ", value=" + value + "]";
	}
}