/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.velocity;

import java.util.Date;

import org.walkerljl.commons.datetime.DateUtils;

/**
 * VelocityUtils
 * 
 * @author lijunlin<walkerljl@qq.com>
 */
public class VelocityUtils {

	/**
	 * 日期转换
	 * @param date
	 * @return
	 */
	public static String dateFormatDateTime(Date date) {
		return DateUtils.dateFormatDateTime(date);
	}
}