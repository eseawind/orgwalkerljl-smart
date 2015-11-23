/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.button;

import java.util.Collections;
import java.util.List;

import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.util.IntegerUtils;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.smart.mvc.MvcSupporter;
import org.walkerljl.smart.mvc.security.Button;
import org.walkerljl.smart.mvc.security.ButtonBar;

/** 
 * CurdTemplateDefaultButtonBar
 * @author lijunlin<walkerljl@qq.com>
 */
public class CurdTemplateDefaultButtonBar implements ButtonBar {
	
	private static final long serialVersionUID = 1L;
	/** 按钮状态激活值*/
	private static final Integer BUTTON_ACTIVE_VALUE = 1;
	
	private final String[] buttonIds = new String[]{"add", "edit", "view", "search", "enable", "disable", "del", "physicsDel"};
	private final String[] buttonNames = buttonIds;
	private final String[] buttonTexts = new String[]{"新增", "修改", "详细", "检索", "启用", "停用", "删除", "物理删除"};
	private final String[] buttonCsses = new String[]{"btn btn-small btn-success", "btn btn-small btn-success", 
		"btn btn-small btn-success", "btn btn-small btn-success", "btn btn-small btn-success", "btn btn-small btn-warning", "btn btn-small btn-warning", "btn btn-small btn-danger"};
	private final String[] buttonIcons = new String[]{"icon-plus-sign-alt", "icon-edit", "icon-view", "icon-search", 
		"icon-plus-sign-alt", "icon-trash", "icon-trash", "icon-trash"};
	private final String[] buttonPermissionCodes = new String[]{"EDIT", "EDIT", "", "", "MODIFYSTATUS", "MODIFYSTATUS", "MODIFYSTATUS", "DELETE"};
	private Integer[] actives = new Integer[]{1, 1, 1, 1, 1, 1, 1, 1};
	
	private final String buttonOnClickEventPrefix = "JARVIS.CONTROLLER.";
	private MvcSupporter mvcSupporter;
	
	public CurdTemplateDefaultButtonBar(MvcSupporter mvcSupporter, Integer[] actives) {
		this.mvcSupporter = mvcSupporter;
		if (actives != null && actives.length == buttonIds.length) {
			this.actives = actives;
		}
	}
	
	/**
	 * 获取权限码
	 * @param permissionCodePrefix
	 * @param key
	 * @return
	 */
	private String getPermissionCode(String permissionCodePrefix, String key) {
		key = StringUtils.isBlank(key) ? "" : key;
		return StringUtils.isBlank(permissionCodePrefix) ? key : permissionCodePrefix + "-" + key;
	}
	
	/**
	 * 获取单击事件
	 * @param key
	 * @return
	 */
	private String getOnClickEvent(String key) {
		return buttonOnClickEventPrefix + key + "();";
	} 

	@Override
	public List<Button> getButtons(String userId, boolean isAdmin, String permissionCodePrefix) {
		List<Button> buttons = ListUtils.newArrayList();
		int activeLen = actives.length;
		for (int i = 0; i < buttonIds.length; i++) {
			if (activeLen > i && IntegerUtils.notEquals(actives[i], BUTTON_ACTIVE_VALUE)) {
				continue;
			}
			String buttonId = buttonIds[i];
			String permissionCode = getPermissionCode(permissionCodePrefix, buttonPermissionCodes[i]);
			boolean isEnable = true; 
			if (!isAdmin) {
				isEnable = mvcSupporter.checkUserAuth(userId, permissionCode);
			}
			if (isEnable) {
				Button button = new Button();
				buttons.add(button);
				
				button.setId(buttonId);
				button.setName(buttonNames[i]);
				button.setText(buttonTexts[i]);
				button.setIcon(buttonIcons[i]);
				button.setCss(buttonCsses[i]);
				button.setOnclickEvent(getOnClickEvent(buttonId));
				button.setPermissionCode(permissionCode);
			}
		}
		Collections.sort(buttons);
		return buttons;
	}
}