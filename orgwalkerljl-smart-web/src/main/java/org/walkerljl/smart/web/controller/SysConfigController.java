package org.walkerljl.smart.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.walkerljl.commons.auth.Authentication;
import org.walkerljl.smart.domain.sys.SysConfig;
import org.walkerljl.smart.mvc.template.JqueryDatatableCurdTemplate;
import org.walkerljl.smart.service.JqueryDatatableBaseService;
import org.walkerljl.smart.service.sys.SysConfigService;

/**
 * 系统配置 
 *
 * @author lijunlin
 */
@Controller @Authentication
@RequestMapping(value = "/sys/config", method = {RequestMethod.POST, RequestMethod.GET})
public class SysConfigController extends JqueryDatatableCurdTemplate<SysConfig> {

	@Resource private SysConfigService sysConfigService;
	
	public SysConfigController() {
		setTemplateBasePath("/sys/config");
		setObjectIdentifer("sysConfig");
		setCurrentUrl("/sys/config");
	}
	
	@Override
	public JqueryDatatableBaseService<SysConfig, Long> getJqueryDatatableBaseService() {
		return sysConfigService;
	}	
}
