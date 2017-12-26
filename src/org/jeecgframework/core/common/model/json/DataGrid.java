package org.jeecgframework.core.common.model.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;

/**
 * easyui的datagrid向后台传递参数使用的model
 * 
 * @author
 * 
 */
@SuppressWarnings("rawtypes")
public class DataGrid {

	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private SortDirection order = SortDirection.asc;// 按什么排序(asc,desc)
	private String field;// 字段
	private String treefield;// 树形数据表文本字段
	private List reaults;// 结果集
	private int total;// 总记录数
	private String footer;// 合计列
	// / DataTable请求服务器端次数
	public int sEcho;
	public String aoData;
	public Map<String,String> aoDataMap;
	private Map<String, String> dataDicMap;
	private Map<String, String> dateFormatMap;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getField() {
		return field;
	}
	public String getAoColumns() {
		return field;
	}


	public List getReaults() {
		return reaults;
	}

	public void setReaults(List reaults) {
		this.reaults = reaults;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public SortDirection getOrder() {
		return order;
	}

	public void setOrder(SortDirection order) {
		this.order = order;
	}

	public String getTreefield() {
		return treefield;
	}

	public void setTreefield(String treefield) {
		this.treefield = treefield;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	
	public String getAoData() {
		return aoData;
	}

	public void setAoData(String aoData) {
		this.aoData = aoData;
		init();
	}

	public Map<String, String> getAoDataMap() {
		return aoDataMap;
	}

	public void setAoDataMap(Map<String, String> aoDataMap) {
		this.aoDataMap = aoDataMap;
	}

	public Map<String, String> getDataDicMap() {
		return dataDicMap;
	}

	public void setDataDicMap(Map<String, String> dataDicMap) {
		this.dataDicMap = dataDicMap;
	}

	public Map<String, String> getDateFormatMap() {
		return dateFormatMap;
	}

	public void setDateFormatMap(Map<String, String> dateFormatMap) {
		this.dateFormatMap = dateFormatMap;
	}

	public void init(){
		Map<String,String> _aoDataMap = new HashMap<String,String>();
		try {
			JSONArray jsonarray = JSONArray.fromObject(aoData);
			for (int j = 0; j < jsonarray.size(); j++) {
				JSONObject obj=(JSONObject)jsonarray.get(j);
				_aoDataMap.put(ConverterUtil.toNotNullString(obj.get("name")), ConverterUtil.toNotNullString(obj.get("value")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.aoDataMap = _aoDataMap;
		
		this.sEcho = Integer.parseInt(this.aoDataMap.get("sEcho"));
		// 当前页数
		this.page = Integer.parseInt(this.aoDataMap.get("iDisplayStart"))/Integer.parseInt(this.aoDataMap.get("iDisplayLength")) + 1;
		//每页显示的长度
		this.rows= Integer.parseInt(this.aoDataMap.get("iDisplayLength"));
		// 数据字典数据
		String dataDic = this.aoDataMap.get("dataDic");
		Map<String,String> _dataDicMap = new HashMap<String, String>();
		// 格式化转换参数
		String dateFormat = this.aoDataMap.get("dateFormat");
		Map<String,String> _dateFormatMap = new HashMap<String, String>();
		try {
			// 数据字典数据map
			if(ConverterUtil.isNotEmpty(dataDic)){
				_dataDicMap = (Map<String,String>)JSONObject.fromObject(dataDic);
			}
			//格式化转换参数map
			if(ConverterUtil.isNotEmpty(dateFormat)){
				_dateFormatMap = (Map<String,String>)JSONObject.fromObject(dateFormat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.dataDicMap = _dataDicMap;
		this.dateFormatMap = _dateFormatMap;

		//开始显示行 
		//Integer.parseInt(this.aoDataMap.get("iDisplayStart"));
		// 对查询项赋值
		if(ConverterUtil.isEmpty(this.field)){
			for(int i=0;i<100;i++){
				String _field = this.aoDataMap.get("mDataProp_"+i);
				if(ConverterUtil.isNotEmpty(_field)){
					if(i == 0){
						this.field = _field;
					}else{
						this.field += "," + _field; 
					}
				}
			}
		}
		
		// 设置隐藏列
		String dataHidden = this.aoDataMap.get("dataHidden");
		if(ConverterUtil.isNotEmpty(dataHidden)){
			this.field += "," + dataHidden;
		}
		
	}

}
