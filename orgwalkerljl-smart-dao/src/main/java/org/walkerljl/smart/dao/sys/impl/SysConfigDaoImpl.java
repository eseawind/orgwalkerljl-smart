package org.walkerljl.smart.dao.sys.impl;

import org.springframework.stereotype.Component;
import org.walkerljl.smart.dao.impl.DefaultBaseDaoImpl;
import org.walkerljl.smart.dao.sys.SysConfigDao;
import org.walkerljl.smart.domain.sys.SysConfig;

/**
 * SysConfigDaoImpl 
 *
 * @author lijunlin
 */
@Component("sysConfigDao")
public class SysConfigDaoImpl extends DefaultBaseDaoImpl<SysConfig, Long> implements SysConfigDao {

	public SysConfigDaoImpl() {
		super.baseNameSpace = "org.walkerljl.smart.dao.sys.SysConfigDao";
	}
}
