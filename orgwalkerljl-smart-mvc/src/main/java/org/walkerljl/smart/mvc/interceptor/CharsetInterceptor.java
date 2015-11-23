/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * CharsetInterceptor 
 *
 * @author lijunlin<walkerljl@qq.com>
 */
public class CharsetInterceptor  implements Filter {

	private FilterConfig config = null; 
    private String encoding = null;
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		 if (encoding == null) {	 
			 encoding = config.getInitParameter("encoding");
	     }
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		config = null;
	    encoding = null;
	}
}