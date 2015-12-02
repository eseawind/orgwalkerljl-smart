//package org.walkerljl.smart.web;
//
//import java.util.Map;
//
//import org.walkerljl.commons.collection.MapUtils;
//import org.walkerljl.commons.datetime.DateUtils;
//import org.walkerljl.smart.mvc.DefaultMvcSupporter;
//
///**
// * CustomizedMvcSupporter 
// *
// * @author lijunlin
// */
//public class CustomizedMvcSupporter extends DefaultMvcSupporter {
//	
//	@Override
//	public Map<String, Object> getBussinessContext() {
//		Map<String, Object> context = MapUtils.newHashMap();
//		context.put("dateUtils", DateUtils.class);
//		context.put("isLogin", isLogin());
//		return context;
//	}
//}