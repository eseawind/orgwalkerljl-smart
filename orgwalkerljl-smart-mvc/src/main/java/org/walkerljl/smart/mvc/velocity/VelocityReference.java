/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.velocity;

import org.apache.velocity.app.event.implement.EscapeReference;

/**
 * VelocityReference
 * @author lijunlin<walkerljl@qq.com>
 */
public class VelocityReference extends EscapeReference {

	@Override
	protected String escape(Object text) {
		if (!(text instanceof String)) {
			return text.toString();
	    }
		StringBuilder str = new StringBuilder();
		char[] cs = text.toString().toCharArray();
        for (char c : cs) {
            if (c == '>') {
                str.append("&gt;");
            } else if (c == '<') {
                str.append("&lt;");
            } else {
                str.append(c);
            }
        }
        return str.toString();       
	}
	
	/**
	 * 返回正则表达式的key
	 */
	@Override
	protected String getMatchAttribute() {
		return "eventhandler.escape.html.match";
	}
}