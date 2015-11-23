package org.walkerljl.smart.mvc.i18n;

import java.util.Locale;
import java.util.MissingResourceException;

import org.springframework.context.MessageSource;

/**
 * 
 * I18N
 *
 * @author lijunlin
 */
public class I18N {
	
	private static MessageSource getMessageSource() {
		return null;
	}
	
	private static Locale getLocale(String lang) {
		try {
			return new Locale(lang);
		} catch (Exception e) {
			return Locale.CHINA;
		}
	}
	
	public static String getMessage(String code, String lang, String...args) {
		return getMessage(code, lang, null, args);
	}
	
	public static String getMessage(String code, String lang, String defaultMsg, String...args) {
		MessageSource source = getMessageSource();
		try {
			return source.getMessage(code, args, defaultMsg, getLocale(lang));
		} catch (MissingResourceException e) {
			try {
				return source.getMessage(code, args, defaultMsg, Locale.CHINA);
			} catch (Exception e2) {
				;
			}
		}
		return defaultMsg;
	}
}