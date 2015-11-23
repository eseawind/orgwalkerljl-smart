package org.walkerljl.smart.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.smart.domain.JqueryDatatableBaseDomain;
import org.walkerljl.smart.domain.Page;
import org.walkerljl.smart.service.JqueryDatatableBaseService;

/**
 * JqueryDatatableBaseServiceImpl 
 *
 * @author lijunlin
 */
public abstract class JqueryDatatableBaseServiceImpl<T, KEY extends Serializable> extends DefaultBaseServiceImpl<T, KEY> implements JqueryDatatableBaseService<T, KEY> {

	/** dataTable分页插件,响应次数Key*/
	private static final String DATATABLE_SECHO_KEY = "sEcho";
	/** dataTable分页插件,记录总数Key*/
	private static final String DATATABLE_TOTAL_RECORDS_KEY = "iTotalRecords";
	/** dataTable分页插件,显示的记录总数Key*/
	private static final String DATATABLE_TOTAL_DISPLAY_RECORDS_KEY = "iTotalDisplayRecords";
	/** dataTable分页插件,分页数据Key*/
	private static final String DATATABLE_DATA_KEY = "data";
	
	@Override
	public Map<String, Object> selectJqueryDatatablePage(T condition) {
		if (condition == null || !(condition instanceof JqueryDatatableBaseDomain)) {
			return null;
		}
		Map<String, Object> resultMap = MapUtils.newHashMap();
		Page<T> page = selectPage(condition);
		resultMap.put(DATATABLE_SECHO_KEY, ((JqueryDatatableBaseDomain) condition).getsEcho());
		resultMap.put(DATATABLE_TOTAL_RECORDS_KEY, page.getTotalCount());
		resultMap.put(DATATABLE_TOTAL_DISPLAY_RECORDS_KEY, page.getTotalCount());
		resultMap.put(DATATABLE_DATA_KEY, page.getResult());
		return resultMap;
	}
}