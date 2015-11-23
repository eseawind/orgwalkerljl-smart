package org.walkerljl.smart.domain.sys;

import java.io.Serializable;

/**
 * Menu 
 *
 * @author lijunlin
 */
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** ID*/
	private Long id;
	/** 名称*/
	private String name;
	/** 父菜单ID*/
	private Long parentId;
	/** 菜单排序值*/
	private Integer order;
	/** 菜单URL*/
	private String url;
	/** ICON*/
	private String icon;
	/** CSS 样式*/
	private String css;
	
	/**
	 * 默认构造函数
	 */
	public Menu() {}
	
	/**
	 * 构造函数
	 * @param id
	 * @param name
	 * @param parentId
	 * @param order
	 * @param url
	 * @param icon
	 * @param css
	 */
	public Menu(Long id, String name, Long parentId, Integer order, String url, String icon, String css) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.order = order;
		this.url = url;
		this.icon = icon;
		this.css = css;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
}