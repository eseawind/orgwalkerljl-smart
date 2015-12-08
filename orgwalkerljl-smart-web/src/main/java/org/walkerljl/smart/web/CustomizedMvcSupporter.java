
package org.walkerljl.smart.web;

import java.util.List;

import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.smart.domain.sys.Menu;
import org.walkerljl.smart.mvc.DefaultMvcSupporter;

/**
 * CustomizedMvcSupporter 
 *
 * @author lijunlin
 */
public class CustomizedMvcSupporter extends DefaultMvcSupporter {
	
	@Override
	public List<Menu> queryCurrentUserAuthMenus(String userId) {
		List<Menu> menus = ListUtils.newArrayList();
		menus.add(new Menu(1L, "系统配置信息", -1L, 0, "/sys/config", "icon-dashboard", null));
		return menus;
	}
}