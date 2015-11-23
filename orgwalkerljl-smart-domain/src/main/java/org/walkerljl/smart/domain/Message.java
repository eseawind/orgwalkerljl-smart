/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.domain;

import java.io.Serializable;

/**
 * 消息
 *
 * @author lijunlin
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_SUCCESS_FLAG = "success";
	private static final String DEFAULT_FAILURE_FLAG = "failure";
	private static final String DEFAULT_SUCCESS_MSG = "操作成功";
	private static final String DEFAULT_FAILURE_MSG = "操作失败";
	
	/** Code*/
	private String code;
	/** Body*/
	private String body;
	/** Result,Success:true,Failure:false*/
	private boolean result;
	
	/**
	 * 默认构造函数
	 */
	public Message() {}
	
	private Message(boolean result, String code, String body) {
		this.result = result;
		this.code = code;
		this.body = body;
	}
	
	public static Message create(boolean result) {
		return Message.create(result, 
				(result ? DEFAULT_SUCCESS_FLAG : DEFAULT_FAILURE_FLAG), 
				(result ? DEFAULT_SUCCESS_MSG : DEFAULT_FAILURE_MSG));
	}
	
	public static Message create(boolean result, String code, String body) {
		return result ? success(code, body) : failure(code, body);
	}
	
	public static Message success() {
		return success(DEFAULT_SUCCESS_FLAG, DEFAULT_SUCCESS_MSG);
	}
	
	public static Message success(String body) {
		return success(DEFAULT_SUCCESS_FLAG, body);
	}

	public static Message success(String code, String body) {
		return new Message(true, code, body);
	}
	
	public static Message failure() {
		return failure(DEFAULT_FAILURE_FLAG, DEFAULT_FAILURE_MSG);
	}
	
	public static Message failure(String body) {
		return failure(DEFAULT_FAILURE_FLAG, body);
	}
	
	public static Message failure(String code, String body) {
		return new Message(false, code, body);
	}
	
	public boolean result() {
		return result;
	}

	//setters and getters
	/**
	 * Get message code
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set message code
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get message body
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set message body
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}
}