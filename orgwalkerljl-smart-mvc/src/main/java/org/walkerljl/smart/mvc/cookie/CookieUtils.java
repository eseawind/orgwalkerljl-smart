package org.walkerljl.smart.mvc.cookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.walkerljl.commons.exception.AppException;
import org.walkerljl.commons.util.StringUtils;

/**
 *
 * CookieUtils
 *
 * @author lijunlin
 */
public class CookieUtils {

    private Map<String, SsoCookie> cookieMap;

    public String getCookieValue(HttpServletRequest servletRequest, String name) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(name)) {
                    if (cookieMap != null && cookieMap.containsKey(name)) {
                        SsoCookie ssoCookie = cookieMap.get(name);
                        return ssoCookie.getValue(cookie.getValue());
                    }
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除cookie，不管有没有定义都能删除
     * 
     * @param servletResponse
     * @param name
     */
    public void deleteCookie(HttpServletResponse servletResponse, String name) {
        Cookie cookie;
        if (cookieMap != null && cookieMap.containsKey(name)) {
            SsoCookie ssoCookie = cookieMap.get(name);
            cookie = ssoCookie.newCookie(null);
        } else {
            cookie = new Cookie(name, null);
        }
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
    }

    /**
     * 设置cookie值，必须定义后才能设置。
     * 
     * @param servletResponse
     * @param name
     * @param value
     */
    public void setCookie(HttpServletResponse servletResponse, String name, String value) {
        if (cookieMap != null && cookieMap.containsKey(name)) {
            SsoCookie ssoCookie = cookieMap.get(name);
            Cookie cookie = ssoCookie.newCookie(value);
            servletResponse.addCookie(cookie);
        } else {
            throw new AppException("Cookie " + name + " is undefined!");
        }
    }

    /**
     * 设置cookie定义值
     * 
     * @param SsoCookieList
     */
    public void setSsoCookie(List<SsoCookie> ssoCookieList) {
        if (ssoCookieList != null) {
            HashMap<String, SsoCookie> ssoCookieHashMap = new HashMap<String, SsoCookie>(ssoCookieList.size());
            for (SsoCookie ssoCookie : ssoCookieList) {
                ssoCookieHashMap.put(ssoCookie.getName(), ssoCookie);
            }
            cookieMap = ssoCookieHashMap;
        }
    }

    /**
     * 删除所有状态没有设置过期的cookie
     * 
     * @param request
     * @param response
     */
    public void invalidate(HttpServletRequest request, HttpServletResponse response) {
        if (cookieMap != null && cookieMap.size() > 0) {
            for (Map.Entry<String, SsoCookie> entry : cookieMap.entrySet()) {
                String key = entry.getKey();
                SsoCookie ssoCookie = entry.getValue();
                if (ssoCookie.getExpiry() < 1) {
                    if (StringUtils.isNotEmpty(getCookieValue(request, key))) {
                        deleteCookie(response, key);
                    }
                }
            }
        }
    }
}