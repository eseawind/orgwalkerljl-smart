/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.walkerljl.commons.auth.Authentication;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.util.StringPool;
import org.walkerljl.config.ConfiguratorFactory;

/**
 * DefaultIndexController
 * 
 * @author lijunlin
 */
@Controller
@RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
public class DefaultIndexController extends BaseController {
	
	@Resource private MvcSupporter mvcSupporter;
	
	/**
	 * 构造函数,初始化
	 */
	public DefaultIndexController() {
		setTemplateBasePath("/common");
		setCurrentUrl("");
	}

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = "") @Authentication
	public ModelAndView index() {
		return toViewResult(getTemplate("/index", "index"));
	}
	
	@RequestMapping(value = "/login")
	public void login() {
		mvcSupporter.login();
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public void logout() {
		mvcSupporter.logoutAndRedirectToLogin(getResponse());
	}
	
	/**
	 * 系统升级提示页面
	 * @return
	 */
	@RequestMapping(value = "/upgrading")
	public ModelAndView upgrading() {
		//隐藏菜单条
		setLoadMenuBar(false);
		boolean upgrading = ConfiguratorFactory.getInstance().getPropertyAsBoolean("SYSTEM.UPGRADING");
		if(getRequest().getRequestURI().endsWith(StringPool.JSON_SUFFIX)) {
			if (!upgrading) {
				setLoadMenuBar(true);
			}
            return toJSON(null, true, (upgrading ? "系统正在升级" : "系统升级完成,欢迎继续使用"));
        }
		if (!upgrading) {
			setLoadMenuBar(true);
			sendRedirect(getRequest().getContextPath() + "/");
			return null;
		}
		Map<String, Object> model = MapUtils.newHashMap();
		model.put("mails", getAdministratorMailList());
		return toViewResultSkipLayout(getTemplate("upgrading"), model);
	}
	
	/**
	 * 访问拒绝页面
	 * @return
	 */
	@RequestMapping(value = "/accessDenied")
	public ModelAndView accessDenied() {
		Map<String, Object> model = MapUtils.newHashMap();
		model.put("mails", getAdministratorMailList());
		return toViewResult(getTemplate("accessDenied"), model);
	}
	
	/**
	 * 系统错误反馈页面
	 * @return
	 */
	@RequestMapping(value = "/error")
	public ModelAndView error() {
		Map<String, Object> model = MapUtils.newHashMap();
		model.put("mails", getAdministratorMailList());
		return toViewResultSkipLayout(getTemplate("error"), model);
	}
	
	/**
	 * 获取系统管理员邮件列表
	 * @return
	 */
	private String getAdministratorMailList() {
		return ConfiguratorFactory.getInstance().getPropertyAsString("ADMIN.MAILS.LIST");
	}
}