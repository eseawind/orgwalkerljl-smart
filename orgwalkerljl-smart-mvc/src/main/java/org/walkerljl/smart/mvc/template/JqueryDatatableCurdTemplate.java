package org.walkerljl.smart.mvc.template;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.walkerljl.smart.service.DefaultBaseService;
import org.walkerljl.smart.service.JqueryDatatableBaseService;

/**
 * JqueryDatatableCurdTemplate 
 *
 * @author lijunlin
 */
public abstract class JqueryDatatableCurdTemplate<T> extends CurdTemplate<T> {

	/**
	 * 实现父类的getService
	 */
	public DefaultBaseService<T, Long> getService() {
		return getJqueryDatatableBaseService();
	}

	//重写父类方法
	@Override
	@RequestMapping(value = "selectJSONPage")
	public ModelAndView selectJSONPage(T t) {
		return toJSON(processJqueryDatatableSelectPage(t));
	}
	
	/**
	 * 处理Jquery Datatable查询分页数据
	 * @param t
	 * @return
	 */
	protected Map<String, Object> processJqueryDatatableSelectPage(T t) {
		return getJqueryDatatableBaseService().selectJqueryDatatablePage(t);
	}
	
	/**
	 * 获取业务接口对象
	 * @return
	 */
	public abstract JqueryDatatableBaseService<T, Long> getJqueryDatatableBaseService();
}