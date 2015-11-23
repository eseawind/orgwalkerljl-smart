/*
 * Copyright (c) 2013 lijunlin All Rights Reserved.
 * 本软件源代码版权归作者所有,未经许可不得任意复制与传播.
 */
package org.walkerljl.smart.mvc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.walkerljl.commons.collection.ArraysUtils;
import org.walkerljl.commons.util.StringUtils;

/**
 * CookieUtils
 * @author lijunlin
 * @since 2013-8-13
 */
public final class CookieUtils {

	/**
	 * 私有构造函数
	 */
	private CookieUtils(){}
	
	public static String randomValue(String value){
		StringBuilder result = new StringBuilder();
	    result.append(Math.round((Math.random()*1000))).append("--");
	    result.append(value);
	    result.append("--").append(Math.round((Math.random()*1000)));    
	    return result.toString();
	}
	
	/**
	 * 获取Cookie值
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		String result = null;
		Cookie[] cookies = request.getCookies();
		if (ArraysUtils.isEmpty(cookies)) {
			return result;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				result = cookie.getValue();
				break;
	        }
		}
        return result;
	}
	 
	/**
	 * 添加cookie
	 * @param response
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void addCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(20);
        response.addCookie(cookie);
	}
	    
	/**
	 * 删除指定cookie
	 * @param response
	 * @param domain
	 * @param key
	 */
	public static void delCookie(HttpServletResponse response, String domain, String key){
		 Cookie cookie = new Cookie(key, null);
         cookie.setMaxAge(0);
         cookie.setPath("/");
         if (domain != null) {
             cookie.setDomain(domain);
         }
         response.addCookie(cookie);
	}
	    
	/**
	 * 删除所有cookie
	 * @param request
	 * @param response
	 */
	public static void delAllCookie(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
        	for(Cookie cookie : cookies){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
	}
	
	/**
	 * 获取访问URL
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request){
		StringBuilder url = new StringBuilder(request.getScheme());
		url.append("://").append(request.getServerName()).append(':').append(request.getServerPort());
	    url.append(request.getContextPath());
	    if(request.getQueryString() != null) {
	    	url.append("?").append(request.getQueryString());
	    }
	    return url.toString();
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
        	String[] ipList = StringUtils.split(ip, ",");
            for (int index = 0; index < ipList.length; index ++) {  
                if (!("unknown".equalsIgnoreCase(ipList[index]))) {  
                    ip = ipList[index];  
                    break;  
                }  
            }  
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}