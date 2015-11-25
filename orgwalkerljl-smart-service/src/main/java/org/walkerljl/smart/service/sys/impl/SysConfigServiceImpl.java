package org.walkerljl.smart.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.walkerljl.smart.dao.DefaultBaseDao;
import org.walkerljl.smart.dao.sys.SysConfigDao;
import org.walkerljl.smart.domain.sys.SysConfig;
import org.walkerljl.smart.service.impl.JqueryDatatableBaseServiceImpl;
import org.walkerljl.smart.service.sys.SysConfigService;

/**
 * SysConfigServiceImpl 
 *
 * @author lijunlin
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends JqueryDatatableBaseServiceImpl<SysConfig, Long> implements SysConfigService {

	@Resource private SysConfigDao sysConfigDao;
	
	@Override
	public DefaultBaseDao<SysConfig, Long> getDao() {
		return sysConfigDao;
	}
}
