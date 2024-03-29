package org.jeecgframework.core.util.excel.entity;

import java.lang.reflect.Method;
import java.util.List;
/**
 * excel 导出工具类,对cell类型做映射
 * @author jueyue
 * @version 1.0 2013年8月24日
 */
public class ExcelExportEntity {
	
	private int width;
	private int height;
	/**
	 * 对应exportName
	 */
	private String name;
	/**
	 * 对应exportType
	 */
	private int type;
	/**
	 * 排序顺序
	 */
	private int orderNum;
	/**
	 * 是否支持换行
	 */
	private boolean isWrap;
	/**
	 * 是否需要合并
	 */
	private boolean  needMerge;
	/**
	 * get 和convert 合并
	 */
	private Method getMethod;
	
	private List<Method> getMethods;
	
	private List<ExcelExportEntity> list;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Method getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(Method getMethod) {
		this.getMethod = getMethod;
	}

	public List<ExcelExportEntity> getList() {
		return list;
	}

	public void setList(List<ExcelExportEntity> list) {
		this.list = list;
	}

	public List<Method> getGetMethods() {
		return getMethods;
	}

	public void setGetMethods(List<Method> getMethods) {
		this.getMethods = getMethods;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public boolean isWrap() {
		return isWrap;
	}

	public void setWrap(boolean isWrap) {
		this.isWrap = isWrap;
	}

	public boolean isNeedMerge() {
		return needMerge;
	}

	public void setNeedMerge(boolean needMerge) {
		this.needMerge = needMerge;
	}

}
