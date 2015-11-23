package org.walkerljl.smart.mvc.url;

import java.util.Collections;
import java.util.Map;

/**
 *
 * UrlBuilders
 *
 * @author lijunlin
 */
public class UrlBuilders {

	private final Map<String, UrlBuilder> urlBuilders;

	public UrlBuilders(Map<String, UrlBuilder> urlBuilders) {
		this.urlBuilders = Collections.unmodifiableMap(urlBuilders);
	}

	/**
	 * fetch predefined url builder by key.
	 * 
	 * @param key
	 * @return
	 * @throws NullPointerException
	 *             no url builder found.
	 */
	public UrlBuilder get(String key) throws NullPointerException {
		UrlBuilder urlBuilder = urlBuilders.get(key);
		if (urlBuilder == null) {
			throw new NullPointerException("No such predefined url builder: "
					+ key);
		}
		return urlBuilder;
	}
}
