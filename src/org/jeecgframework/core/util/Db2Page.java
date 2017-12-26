package org.jeecgframework.core.util;

import java.util.Map;

import org.jeecgframework.core.util.DataUtils.IMyDataExchanger;

//页面表示数据与数据库字段的对应关系
public class Db2Page {

	String fieldPage; // 页面的fieldID
	String columnDB; // 数据库的字段名
	String dictionaryName; // 数据字典名
	Map<String, Object> customDict; // 自定义字典
	IMyDataExchanger dataExchanger; // 数据变换
	// 无参构造函数
	public Db2Page(){
		
	}

	// 构造函数1：当页面的fieldID与数据库字段一致时（数据也不用变换）
	public Db2Page(String fieldPage) {
		this.fieldPage = fieldPage;
		this.columnDB = fieldPage;
		this.dataExchanger = null;
	}
	// 构造函数2：当页面的fieldID与数据库字段不一致时（数据不用变换）
	public Db2Page(String fieldPage, String columnDB) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		this.dataExchanger = null;
	}
	// 构造函数3：当页面的fieldID与数据库字段不一致，且数据要进行变换（当然都用这个构造函数也行）
	public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		this.dataExchanger = dataExchanger;
	}
	// 构造函数4:带有数据字典
	public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger, String dictionaryName) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		this.dataExchanger = dataExchanger;
		this.dictionaryName = dictionaryName;
	}
	// 构造函数5:带有自定义字典
	public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger, Map<String, Object> customDict) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		this.dataExchanger = dataExchanger;
		this.customDict = customDict;
	}

	/**
	 * 取页面表示绑定的fieldID
	 */
	public String getKey() {
		return fieldPage;
	}

	/**
	 * 取页面表示对应的值
	 * 
	 * @param mapDB
	 *            : 从数据库直接取得的结果集(一条数据的MAP)
	 * @return Object : 页面表示对应的值
	 */
	public Object getData(Map mapDB) {
		Object objValue = mapDB.get(columnDB);
		if (objValue == null) {
			return null;
		} else {
			if (dataExchanger != null) {
				return dataExchanger.exchange(objValue);
			} else {
				if(StringUtil.isNotEmpty(dictionaryName)){
					// 取得字典的返回值
					return ConverterUtil.getDictionaryText(dictionaryName, ConverterUtil.toString(objValue));
				} else {
					if(customDict != null){
						// 取得自定义字典的值
						return customDict.get(objValue);
					} else {
						return objValue;
					}
				}
			}
		}
	}

}
