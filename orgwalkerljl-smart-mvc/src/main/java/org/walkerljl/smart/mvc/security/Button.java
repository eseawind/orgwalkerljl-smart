/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.security;

import java.io.Serializable;

/**
 * Button
 * @author lijunlin<walkerljl@qq.com>
 */
public class Button implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = 1L;

	/** ID/DOM ID*/
	private String id;
	/** Name/DOM Name*/
	private String name;
	/** 按钮显示名称*/
	private String text;
	/** 图标*/
	private String icon;
	/** CSS 样式*/
	private String css;
	/** 授权码*/
	private String permissionCode;
	/** 单击事件*/
	private String onclickEvent;
	/** 是否可用*/
	private boolean enabled = true;
	/** 排序值*/
	private int sortSequence;
	
	public Button() {}
	
	public String getBody() {
		StringBuilder body = new StringBuilder();
		body.append("<button class='").append(this.css).append("' type='button' id='").append(this.id).append("' onclick='");
		body.append(this.onclickEvent).append("'>");
		body.append("<i class='").append(this.icon).append("'></i>");
		body.append(this.text);
		body.append("</button>");
		return body.toString();
	}
	
	@Override
	public int compareTo(Object o) {
		Button button = (Button) o;
		if (button.sortSequence < this.sortSequence) {
			return -1;
		} else if (button.sortSequence > this.sortSequence) {
			return 1;
		} else {
			return 0;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getOnclickEvent() {
		return onclickEvent;
	}

	public void setOnclickEvent(String onclickEvent) {
		this.onclickEvent = onclickEvent;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getSortSequence() {
		return sortSequence;
	}

	public void setSortSequence(int sortSequence) {
		this.sortSequence = sortSequence;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Button other = (Button) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String body = "";
		if (enabled) {
			body = String.format("<button type='button' id='%s' name='%s' class='%s' onclick='%s'><i class='%s'></i>%s</button>", 
					new Object[]{getId(), getName(), getCss(), getOnclickEvent(), getIcon(), getText()});
		}
		return body;
	}
}