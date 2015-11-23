package org.walkerljl.smart.service;

import java.io.Serializable;
import java.util.Map;

/**
 * JqueryDatatableBaseService 
 *
 * @author lijunlin
 */
public interface JqueryDatatableBaseService<T, KEY extends Serializable> extends DefaultBaseService<T, KEY> {

	Map<String, Object> selectJqueryDatatablePage(T condition);
}
