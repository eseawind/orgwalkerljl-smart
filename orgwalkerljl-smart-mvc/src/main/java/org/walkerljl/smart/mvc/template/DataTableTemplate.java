/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.template;

import java.util.Map;

import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.smart.mvc.BaseController;

/**
 * 基于Datatable表格插件的Template
 * 
 * @author lijunlin<walkerljl@qq.com>
 */
public abstract class DataTableTemplate extends BaseController {
	
	/** Datatable 对象标识符*/
	private static final String DATATABLE_IDENTIFER_KEY = "datatableIdentifer";
	/** Datatable checkbox标识符*/
	private static final String CHECKBOX_IDENTIFER_KEY = "checkboxIdentifer";
	/** Datatable checkbox item标识符*/
	private static final String CHECKBOX_ITEM_IDENTIFER_KEY= "checkboxItemIdentifer";
	/** Datatable 对象标识符 后缀*/
	private static final String DATATABLE_IDENTIFER_SUFFIX = "_table";
	/** Datatable checkbox标识符后缀*/
	private static final String CHECKBOX_IDENTIFER_SUFFIX = "_chk";
	/** Datatable checkbox item标识符后缀*/
	private static final String CHECKBOX_ITEM_IDENTIFER_SUFFIX = "_chk_item";
	
	@Override
	public Map<String, Object> getDefaultContext() {
		Map<String, Object> context = super.getDefaultContext();
		if (context == null) {
			context = MapUtils.newHashMap();
		}
		context.put(DATATABLE_IDENTIFER_KEY, getDatatableIdentifer());
		context.put(CHECKBOX_IDENTIFER_KEY, getCheckboxIdentifer());
		context.put(CHECKBOX_ITEM_IDENTIFER_KEY, getCheckboxItemIdentifer());
		return context;
	}
	
	/**
	 * 获取DataTable标识符
	 * @return
	 */
	private String getDatatableIdentifer() {
		return getObjectIdentifer() + DATATABLE_IDENTIFER_SUFFIX;
	}
	
	/**
	 * 获取Datatable checkbox 标识符
	 * @return
	 */
	private String getCheckboxIdentifer() {
		return getObjectIdentifer() + CHECKBOX_IDENTIFER_SUFFIX;
	}
	
	/**
	 * 获取Datatable checkbox item 标识符
	 * @return
	 */
	private String getCheckboxItemIdentifer() {
		return getObjectIdentifer() + CHECKBOX_ITEM_IDENTIFER_SUFFIX;
	}
}