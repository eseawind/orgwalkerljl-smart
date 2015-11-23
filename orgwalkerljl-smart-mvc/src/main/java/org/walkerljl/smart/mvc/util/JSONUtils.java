/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.commons.collection.MapUtils;
import org.walkerljl.commons.exception.AppException;
import org.walkerljl.commons.io.StreamUtils;
import org.walkerljl.commons.util.HtmlUtils;
import org.walkerljl.commons.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSONUtils
 * 
 * @author lijunlin
 */
public class JSONUtils {

	/**
	 * 将Java对象转成JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		SerializeWriter writer = new SerializeWriter(SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteDateUseDateFormat);
		writer.config(SerializerFeature.QuoteFieldNames, true);
		JSONSerializer json = new JSONSerializer(writer);
		json.write(object);
		return json.toString();
	}
	
	/**
	 * 将Java对象转成JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJSON(Object object) {
		SerializeWriter writer = new SerializeWriter(SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteDateUseDateFormat);
		writer.config(SerializerFeature.QuoteFieldNames, true);
		JSONSerializer json = new JSONSerializer(writer);
		json.write(object);
		return json.toString();
	}
	
	/**
	 * 将Map<String, Object>转换成Map<String, String>即将Map的value序列化成JSON字符串
	 * @param dataMap
	 * @return
	 */
	public static Map<String, String> toJSON(Map<String, Object> dataMap) {
		if (MapUtils.isEmpty(dataMap)) {
			return null;
		}
		Map<String, String> result = MapUtils.newHashMap();
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			result.put(entry.getKey(), toJSONString(entry.getValue()));
		}
		return result;
	}
	
	/**
	 * 将JSON字符串解析成JSONObject对象
	 * @param text
	 * @return JSONObject
	 */
	public static JSONObject parseObject(String text) {
		return JSON.parseObject(text);
	}
	
	/**
	 * 将JSON字符串解析成Java对象
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		return (T) JSON.parseObject(text, clazz);
	}
	
	/**
	 * 解析成List对象
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseList(String text, Class<T> clazz) {
		return (List<T>) JSONObject.parseArray(text, clazz);
	}
	
	/**
	 * 解析成List对象
	 * @param texts
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseList(List<String> texts, Class<T> clazz) {
		if (ListUtils.isEmpty(texts)) {
			return null;
		}
		List<T> result = ListUtils.newArrayList();
		for (String text : texts) {
			if (StringUtils.isBlank(text)) {
				continue;
			}
			T obj = parseObject(text, clazz);
			if (obj != null) {
				result.add(obj);
			}
		}
		return result;
	}
	
	/**
	 * 解析成List集合对象,解析对象为Map<String, String>的value
	 * @param textMap
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseList(Map<String, String> textMap, Class<T> clazz) {
		if (MapUtils.isEmpty(textMap)) {
			return null;
		}
		List<T> result = ListUtils.newArrayList();
		for (Map.Entry<String, String> entry : textMap.entrySet()) {
			T object = parseObject(entry.getValue(), clazz);
			if(object != null){
				result.add(object);
			}
		}
		return result;
	}
	
	/**
	 * 输出JSON格式数据
	 * @param response
	 * @param context
	 * @param serializeFormat
	 */
	public static void write(PrintWriter out, Object context, final Map<String, String> serializeFormat) {
		NameFilter nameFilter =null;
		if (MapUtils.isNotEmpty(serializeFormat)) {
			nameFilter =new NameFilter() {
				@Override
				public String process(Object source, String name, Object value) {
					Object cvnName = serializeFormat.get(name);
					if (cvnName != null && !cvnName.toString().equals("")) {
						return cvnName.toString();
					}
					return name;
				}
			};
		}

		SerializeWriter sw = new SerializeWriter(SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue, 
				SerializerFeature.WriteNullStringAsEmpty);
		sw.config(SerializerFeature.QuoteFieldNames, true);
		JSONSerializer serializer = new JSONSerializer(sw);
		if (nameFilter != null) {
			serializer.getNameFilters().add(nameFilter);
		}
		serializer.write(context);
		try {
			String json = sw.toString();
			out.print(HtmlUtils.escapeJson(json));
			out.flush();
		} catch (Exception ex) {
			throw new AppException(ex);
		} finally {
			StreamUtils.close(out);
		}
	}
}