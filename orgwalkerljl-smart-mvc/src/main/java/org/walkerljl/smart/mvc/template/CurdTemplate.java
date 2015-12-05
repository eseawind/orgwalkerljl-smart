/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.template;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.walkerljl.commons.Message;
import org.walkerljl.commons.auth.Authentication;
import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.util.LongUtils;
import org.walkerljl.commons.util.StringPool;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.smart.domain.BaseDomain;
import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.domain.ViewResult;
import org.walkerljl.smart.domain.sys.User;
import org.walkerljl.smart.enums.Status;
import org.walkerljl.smart.mvc.MvcSupporter;
import org.walkerljl.smart.mvc.button.CurdTemplateDefaultButtonBar;
import org.walkerljl.smart.mvc.enums.EditType;
import org.walkerljl.smart.mvc.security.Button;
import org.walkerljl.smart.mvc.security.ButtonBar;
import org.walkerljl.smart.service.DefaultBaseService;

/**
 * 基于简单增、删、改、查的Template
 * 
 * @author lijunlin
 */
public abstract class CurdTemplate<T> extends DataTableTemplate {
	
	/** 编辑类型Key*/
	protected static final String EDIT_TYPE_KEY = "editType";
	/** 数据模型Key*/
	protected static final String DATA_MODEL_KEY = "model";
	
	/** 首页模板*/
	private String indexTemplate = "index";
	private boolean indexSkipLayout = false;
	/** 添加操作模板*/
	private String addTemplate = "edit";
	/** 编辑操作模板*/
	private String editTemplate = "edit";
	/** 详情模板*/
	private String viewTemplate = "edit";
	/** 按钮激活状态*/
	private Integer[] buttonActives;
	
	@Resource private MvcSupporter mvcSupporter;
	
	public CurdTemplate() {}
	
	@Override
	public Map<String, Object> getDefaultContext() {
		Map<String, Object> context = super.getDefaultContext();
		if (context == null) {
			context = MapUtils.newHashMap();
		}
		context.put("indexAddress", buildAddress(""));
		context.put("selectJSONPageAddress", buildAddress("selectJSONPage"));
		context.put("selectPageAddress", buildAddress("selectPage"));
		context.put("searchAddress", buildAddress("search"));
		context.put("addAddress", buildAddress("add"));
		context.put("editAddress", buildAddress("edit-"));
		context.put("saveAddress", buildAddress("save"));
		context.put("modifyStatusAddress", buildAddress("modifyStatus"));
		context.put("deleteAddress", buildAddress("delete"));
		context.put("viewAddress", buildAddress("view-"));
		context.put("isExistsAddress", buildAddress("isExists"));
		return context;
	}
	
	/**
	 * 构建地址
	 * @param key
	 * @return
	 */
	private String buildAddress(String key) {
		return getRequest().getContextPath() + StringPool.SLASH + getObjectIdentifer() + StringPool.SLASH + key;
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public ModelAndView index(T t) {
		ViewResult viewResult = getIndexModel();
		viewResult.addModel(DATA_MODEL_KEY, processSelectPage(t));
		if (mvcSupporter.isLoadButtonBar()) {
			//设置按钮条
			String currentUserId = getCurrentUserId();
			List<Button> customizedButtonBar = getCustomizedButtonBar();
			if (ListUtils.isEmpty(customizedButtonBar)) {
				ButtonBar curdTemplateDefaultButtonBar = new CurdTemplateDefaultButtonBar(mvcSupporter, buttonActives);
				List<Button> buttonBar = curdTemplateDefaultButtonBar.getButtons(currentUserId, isAdministrator(currentUserId), getAuthCodePrefix());
				viewResult.addModel(getButtonBarKey(), buttonBar);
			} else {
				viewResult.addModel(getButtonBarKey(), customizedButtonBar);
			}
		}
		return toCustomizedViewResult(indexTemplate, viewResult, indexSkipLayout);
	}
	
	/**
	 * 获取后去首页需要加载的数据模型
	 * 默认的数据模型为空
	 * @return
	 */
	protected ViewResult getIndexModel() {
		return new ViewResult();
	}
	
	/**
	 * 查询JSON分页数据
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "selectJSONPage")
	public ModelAndView selectJSONPage(T t) {
		return toJSON(processSelectPage(t));
	}
	
	/**
	 * 查询分页数据
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "selectPage")
	public ModelAndView selectPage(T t) {
		ViewResult viewResult = new ViewResult();
		viewResult.addModel(DATA_MODEL_KEY, processSelectPage(t));
		if (indexSkipLayout) {
			return toViewResultSkipLayout(getTemplate(indexTemplate), viewResult);
		} else {
			return toViewResult(getTemplate(indexTemplate), viewResult);
		}
	}
	
	/**
	 * 处理分页查询
	 * @param t
	 * @return
	 */
	protected Page<T> processSelectPage(T t) {
		return getService().selectPage(t);
	}
	
	/**
	 * 条件查询,领域字段值不为NULL则为条件
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "search", method = {RequestMethod.POST})
	public ModelAndView search(T t) {
		return toJSON(processSearch(t));
	}
	
	/**
	 * 处理条件查询,领域字段值不为NULL则为条件
	 * @param t
	 * @return
	 */
	protected List<T> processSearch(T t) {
		return getService().selectList(t);
	}
	
	/**
	 * 新增
	 * @return
	 */
	@Authentication(code="edit")
	@RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView add() {
		ViewResult viewResult = getAddModel();
		viewResult.addModel(EDIT_TYPE_KEY, EditType.ADD.getValue());
		return toCustomizedViewResult(addTemplate, viewResult, false);
	}
	
	/**
	 * 获取新增页面需要加载的数据模型
	 * 默认的数据模型为空
	 * @return
	 */
	protected ViewResult getAddModel() {
		return new ViewResult();
	}
	
	/**
	 * 编辑
	 * @param id
	 * @return
	 */
	@Authentication(code = "edit")
	@RequestMapping(value = "/edit-{id}", method = {RequestMethod.GET})
	public ModelAndView edit(@PathVariable Long id) {
		ViewResult viewResult = getEditModel(id);
		viewResult.addModel(EDIT_TYPE_KEY, EditType.EDIT.getValue());
		viewResult.addModel(DATA_MODEL_KEY, processView(id));
		return toCustomizedViewResult(editTemplate, viewResult, false);
	}

	/**
	 * 获取新增页面需要加载的数据模型
	 * 默认的数据模型为空
	 * @param id
	 * @return
	 */
	protected ViewResult getEditModel(Long id) {
		return new ViewResult();
	}
	
	/**
	 * 处理保存操作
	 * @param t
	 * @return
	 */
	@Authentication(code = "edit")
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public ModelAndView save(T t) {
		if (t != null && t instanceof BaseDomain) {
			User currentUser = getCurrentUser();
			BaseDomain domain = (BaseDomain) t;
			if (LongUtils.greatThanZero(domain.getId())) {//修改
				domain.setModifiedTime(new Date());
				if (currentUser != null) {
					domain.setModifier(currentUser.getUserId());
				}
			} else {//新增
				domain.setStatusType(Status.ENABLED);
				domain.setCreatedTime(new Date());
				domain.setModifiedTime(domain.getCreatedTime());
				if (currentUser != null) {
					domain.setCreator(currentUser.getUserId());
					domain.setModifier(domain.getCreator());
				}
			}
		}
		Message message = Message.create(getService().saveOrUpdate(t, null));
		return toJSON(null, message.result(), message.getBody());
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@Authentication(code="delete")
	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public ModelAndView delete(String ids) {
		Message message = processDelete(StringUtils.splitToLongList(ids, ","));
		return toJSON(null, message.result(), message.getBody());
	}
	
	/**
	 * 处理删除业务
	 * @param ids
	 * @return
	 */
	protected Message processDelete(List<Long> ids) {
		return Message.create(getService().deleteByKeys(ids) > 0);
	}
	
	/**
	 * 更新状态
	 * @param ids
	 * @return
	 */
	@Authentication(code="modifystatus")
	@RequestMapping(value = "/modifyStatus", method = {RequestMethod.POST})
	public ModelAndView modifyStatus(String ids, Integer status) {
		Message message = Message.create(getService().updateStatusByKeys(StringUtils.splitToLongList(ids, ","), Status.getType(status)) > 0);
		return toJSON(null, message.result(), message.getBody());
	}
	
	/**
	 * 详情页面
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/view-{id}")
	public ModelAndView view(@PathVariable Long id) {
		ViewResult viewResult = getViewModel(id);
		viewResult.addModel(EDIT_TYPE_KEY, EditType.VIEW.getValue());
		viewResult.addModel(DATA_MODEL_KEY, processView(id));
		return toCustomizedViewResult(viewTemplate, viewResult, false);
	}
	
	/**
	 * 记录是否存在
	 * @param t
	 */
	@RequestMapping(value = "/isExists")
	public void isExists(T t) {
		boolean isExists = ListUtils.size(getService().selectList(t)) > 0;
		ViewResult viewResult = new ViewResult();
		viewResult.addModel("isExists", isExists);
		toJSON(viewResult);
	}
	
	/**
	 * 处理查看详情页面
	 * @param id
	 * @return
	 */
	protected T processView(Long id) {
		T t = getService().selectByKey(id);
		return t;
	}
	
	/**
	 * 获取详情页面需要加载的数据模型
	 * @param id
	 * @return
	 */
	protected ViewResult getViewModel(Long id) {
		return new ViewResult();
	}
	
	/**
	 * 视图输出
	 * @param view
	 * @param viewResult
	 * @param skipLayout
	 * @return
	 */
	private ModelAndView toCustomizedViewResult(String view, ViewResult viewResult, boolean skipLayout) {
		if (skipLayout) {
			return toViewResultSkipLayout(getTemplate(view), viewResult);
		} else {
			return toViewResult(getLayout(), getTemplate(view), viewResult);
		}
	}
	
	/**
	 * 设置首页模板
	 * @param indexTemplate
	 */
	protected void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	/**
	 * 设置新增操作模板
	 * @param addTemplate
	 */
	protected void setAddTemplate(String addTemplate) {
		this.addTemplate = addTemplate;
	}

	/**
	 * 设置编辑操作模板
	 * @param editTemplate
	 */
	protected void setEditTemplate(String editTemplate) {
		this.editTemplate = editTemplate;
	}

	/**
	 * 设置详情模板
	 * @param viewTemplate
	 */
	protected void setViewTemplate(String viewTemplate) {
		this.viewTemplate = viewTemplate;
	}
	
	/**
	 * 按钮激活状态
	 * @param actives
	 */
	protected void setButtonActives(Integer[] actives) {
		this.buttonActives = actives;
	}
	
	protected void setIndexSkipLayout(boolean skip) {
		this.indexSkipLayout = skip;
	}
	
	/**
	 * 获取业务接口对象
	 * @return
	 */
	public abstract DefaultBaseService<T, Long> getService();
}