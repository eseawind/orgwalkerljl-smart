package org.walkerljl.smart.mvc.cookie;

import java.io.UnsupportedEncodingException;

import org.walkerljl.commons.util.Base32;
import org.walkerljl.commons.util.StringUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 *
 * CookieCipherUtils
 *
 * @author lijunlin
 */
class CookieCipherUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(CookieCipherUtils.class);
	
    private String charsetName;

    public String encrypt(String value, String key) {
        try {
            byte[] data;
            if (!StringUtils.isEmpty(charsetName)) {
                try {
                    data = value.getBytes(charsetName);
                } catch (Exception e1) {
                	LOG.error("charset " + charsetName + " Unsupported!", e1);
                    data = value.getBytes();
                }
            } else {
                data = value.getBytes();
            }
            byte[] bytes = encrypt(key, data);
            return encoding(bytes);
        } catch (Exception e) {
        	LOG.error("encrypt error", e);
            return null;
        }
    }

    private String encoding(byte[] bytes) throws Exception {
        return Base32.encode(bytes);
    }
    private byte[] decoding(String value) throws Exception {
        return  Base32.decode(value);
    }

    private byte[] encrypt(String key, byte[] data) throws Exception {
        return null;
    }

    private byte[] decrypt(String key, byte[] data) throws Exception {
        return null;
    }
    
    public String decrypt(String value, String key) {
        try {
            byte[] data = decoding(value);
            byte[] bytes = decrypt(key, data);
            if (StringUtils.isNotEmpty(charsetName)) {
                try {
                    return new String(bytes, charsetName);
                } catch (UnsupportedEncodingException e1) {
                    LOG.error("charset " + charsetName + " Unsupported!", e1);
                    return new String(bytes);
                }
            } else {
                return new String(bytes);
            }
        } catch (Exception e) {
            LOG.error("encrypt error", e);
            return null;
        }
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }
}