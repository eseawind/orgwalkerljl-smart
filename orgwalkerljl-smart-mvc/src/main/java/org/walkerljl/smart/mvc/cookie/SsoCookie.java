package org.walkerljl.smart.mvc.cookie;

import javax.servlet.http.Cookie;

import org.walkerljl.commons.util.StringUtils;

/**
 *
 * SsoCookie
 *
 * @author lijunlin
 */
public class SsoCookie {
	
	 /**
     * 加密工具
     */
    private CookieCipherUtils cookieCipherUtils;
    
    /**
     * cookie的名字
     */
    private String name;
    /**
     * cookie的domain
     */
    private String domain;
    /**
     * cookie的路径
     */
    private String path;
    /**
     * cookie的过期时间 单位：秒
     */
    private int expiry;
    /**
     * cookie的key
     * 
     * @see #encrypt
     */
    private String key;
    /**
     * 是否加密cookie
     * 
     * @see #key
     */
    private boolean encrypt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public void setcookieCipherUtils(CookieCipherUtils cookieCipherUtils) {
        this.cookieCipherUtils = cookieCipherUtils;
    }

    public Cookie newCookie(String value) {
        String newValue;
        if (StringUtils.isNotBlank(value)) {
            newValue = isEncrypt() ? cookieCipherUtils.encrypt(value, getKey()) : value;
        } else {
            newValue = value;
        }
        Cookie cookie = new Cookie(name, newValue);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }
        if (expiry > 0) {
            cookie.setMaxAge(expiry);
        }
        return cookie;
    }

    public String getValue(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return isEncrypt() ? cookieCipherUtils.decrypt(value, getKey()) : value;
        } else {
            return value;
        }
    }
}