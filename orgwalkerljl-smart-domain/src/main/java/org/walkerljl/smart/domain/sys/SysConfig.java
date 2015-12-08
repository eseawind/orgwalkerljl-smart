package org.walkerljl.smart.domain.sys;

import org.walkerljl.db.api.annotation.Column;
import org.walkerljl.db.api.annotation.Entity;
import org.walkerljl.smart.domain.JqueryDatatableBaseDomain;

/**
 * 系统配置
 *
 * @author lijunlin
 */
@Entity("sys_config")
public class SysConfig extends JqueryDatatableBaseDomain {

	private static final long serialVersionUID = 1L;
	
	/** 配置Key*/
	@Column("key")
	private String key;
	@Column("value")/** 配置Value*/
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