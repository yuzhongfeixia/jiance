package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSRole;
import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.pojo.base.TSUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import oracle.sql.TIMESTAMP;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;

import com.ctc.wstx.util.DataUtil;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;


/**
 * 
 * 类描述：标签工具类
 * 
 * @author: jeecg
 * @date： 日期：2012-12-28 时间：上午09:58:00
 * @version 1.0
 */
public class TagUtil {
	/**
	 * <b>Summary: </b> getFiled(获得实体Bean中所有属性)
	 * 
	 * @param objClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Field[] getFiled(Class objClass) throws ClassNotFoundException {
		Field[] field = null;
		if (objClass != null) {
			Class class1 = Class.forName(objClass.getName());
			field = class1.getDeclaredFields();// 这里便是获得实体Bean中所有属性的方法
			return field;
		} else {
			return field;
		}
	}

	/**
	 * 
	 * 获取对象内对应字段的值
	 * @param fields
	 */
	public static Object fieldNametoValues(String FiledName, Object o){
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		ReflectHelper reflectHelper=new ReflectHelper(o);
		if (FiledName.indexOf("_") == -1) {
			fieldName = FiledName;
		} else {
			fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
			childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
		}
		value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
		if (value !=""&&value != null && FiledName.indexOf("_") != -1) {
			value = fieldNametoValues(childFieldName, value);
		}
		if(value != "" && value != null) {
		value = ConverterUtil.toString(value).replaceAll("\r\n", "");
		}
		return value;
	}

	/**
	 * 对象转数组
	 * @param fields
	 * @param o
	 * @param stack
	 * @return
	 * @throws Exception
	 */
	protected static Object[] field2Values(String[] fields, Object o) throws Exception {
		Object[] values = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].toString();
			values[i] = fieldNametoValues(fieldName, o);
		}
		return values;
	}

	// -----------------------------------------------------------------------------------
	// 以下各函数可以提成共用部件 (Add by Quainty)
	// -----------------------------------------------------------------------------------
	/**
	 * 返回easyUI的DataGrid数据格式的JSONObject对象
	 * 
	 * @param mapList
	 *            : 从数据库直接取得的结果集列表
	 * @param iTotalCnt
	 *            : 从数据库直接取得的结果集总数据条数
	 * @param dataExchanger
	 *            : 页面表示数据与数据库字段的对应关系列表
	 * @return JSONObject
	 */
	public static JSONObject getDatagridWithListMapAndDb2Page(List<Map<String, Object>> mapList, int iTotalCnt,
			Db2Page[] dataExchanger,DataGrid dataGrid) {
		JSONObject jso = new JSONObject();
		/*String jsonTemp = "{\"sEcho\":" + sEcho + ",\'iTotalRecords\':" + total
				+ ",\"iTotalDisplayRecords\":" + total + ",\'aaData\':[";*/
		jso.accumulate("sEcho", dataGrid.getsEcho());
		jso.accumulate("iTotalRecords", iTotalCnt);
		jso.accumulate("iTotalDisplayRecords", iTotalCnt);
		JSONArray jsa = new JSONArray();
		for (Map<String, Object> map : mapList) {
			String id = "";
			JSONObject rowObj = new JSONObject();
			for (Db2Page dbToPage : dataExchanger) {
				
				String key = dbToPage.getKey();
				String value = ConverterUtil.toNotNullString(ConverterUtil.toString(dbToPage.getData(map)));
				if("id".equals(key)){
					id = value;
				}
				rowObj.accumulate(key, value);
			}
			rowObj.accumulate("DT_RowId",id);
			jsa.add(rowObj);
		}
		jso.accumulate("aaData", jsa);
		return jso;
	}
	
	/**
	 * 循环LIST对象拼接EASYUI格式的JSON数据
	 * 
	 * @param fields 属性
	 * @param total 记录条数
	 * @param list 记录集
	 * @param footers 底部信息
	 */
	public static String listToJSONString(String[] fields, int total, List list, String[] footers) throws Exception {
		JSONObject jso = new JSONObject();
		jso.accumulate("total", total);
		JSONArray jsa = new JSONArray();
		for(Object elem:list){
			JSONObject rowObj = new JSONObject();
			rowObj.accumulate("state", "closed"); 
			for(String field:fields){
				Object value = fieldNametoValues(field, elem);
				rowObj.accumulate(field, ConverterUtil.toNotNullString(ConverterUtil.toString(value)));
			}
			jsa.add(rowObj);
		}
		jso.accumulate("rows", jsa);
		if (footers != null) {
			JSONArray footList = new JSONArray();
			JSONObject sumObj = new JSONObject();
			sumObj.accumulate("name", "合计");
			JSONObject footObj = new JSONObject();
			for (String footer : footers) {
				String footerFiled = footer.split(":")[0];
				Object value = null;
				if (footer.split(":").length == 2) {
					value = footer.split(":")[1];
				} else {
					value = getTotalValue(footerFiled, list);
				}
				footObj.accumulate(footerFiled, value);
			}
			sumObj.accumulateAll(footObj);
			footList.add(sumObj);
			jso.accumulate("footer", footList);
		}
		return jso.toString();
	}
	
	/**
	 * 循环LIST对象拼接EASYUI格式的JSON数据
	 * @param fields
	 * @param total
	 * @param list
	 * @param dateFormatMap 
	 */
	private static String listtojson(String[] fields, int total,int sEcho, List list,String[] footers,Map<String,String> dataDicMap, Map<String, String> dateFormatMap) throws Exception {
		Object[] values = new Object[fields.length];
		//String jsonTemp = "{\'total\':" + total + ",\'rows\':[";
		String jsonTemp = "{\"sEcho\":" + sEcho + ",\'iTotalRecords\':" + total
				+ ",\"iTotalDisplayRecords\":" + total + ",\'aaData\':[";
		for (int j = 0; j < list.size(); j++) {
			String id = "";
			//jsonTemp = jsonTemp + "{\'state\':\'closed\',";
			jsonTemp = jsonTemp + "{";
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				if(list.get(j) instanceof Map){
					values[i] = ((Map)list.get(j)).get(fieldName);
					if (values[i] instanceof TIMESTAMP) {
						values[i] = (Date) ((TIMESTAMP)values[i]).timestampValue();
					}
				}else{
					values[i] = fieldNametoValues(fieldName, list.get(j));
				}
				// 防止出现 "null"
				values[i] = values[i] == null ? "" : values[i];
				
				if("id".equalsIgnoreCase(fieldName)){
					id = values[i].toString();
				}
				// 行号功能加入
				if("rowIndex".equalsIgnoreCase(fieldName)){
					jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + (j + 1) + "\',";
					continue;
				}
				
				String dataFormatStr = dateFormatMap.get(fieldName);
				if(ConverterUtil.isNotEmpty(dataFormatStr) && ConverterUtil.isNotEmpty(values[i])){
					jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + DataUtils.Str2Str(ConverterUtil.toString(values[i]), dataFormatStr) + "\'";
				}else{
					jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + values[i] + "\'";
				}
				
				
				String dataDicName = dataDicMap.get(fieldName);
				if(ConverterUtil.isNotEmpty(dataDicName)){
					jsonTemp = jsonTemp + ",\'" + fieldName + "_name\'" + ":\'" + transform(dataDicName,values[i]) + "\'";
				}
				
				if (i != fields.length - 1) {
					jsonTemp = jsonTemp + ",";
				}
			}
			
			jsonTemp = jsonTemp + ",\'" + "DT_RowId" + "\'" + ":\'" + id + "\'";
			
			if (j != list.size() - 1) {
				jsonTemp = jsonTemp + "},";
			} else {
				jsonTemp = jsonTemp + "}";
			}
		}
		jsonTemp = jsonTemp + "]";
		if(footers!=null){
			jsonTemp = jsonTemp + ",";
			jsonTemp = jsonTemp + "\'footer\':[";
			jsonTemp = jsonTemp + "{";
			jsonTemp = jsonTemp + "\'name\':\'合计\',";
			for(String footer:footers){
				String footerFiled = footer.split(":")[0];
				Object value = null;
				if(footer.split(":").length==2){
					value = footer.split(":")[1];
				}else{
					value = getTotalValue(footerFiled,list);
				}
				jsonTemp = jsonTemp +"\'"+footerFiled+"\':\'"+value+"\',";
			}
			if(jsonTemp.endsWith(",")){
				jsonTemp = jsonTemp.substring(0,jsonTemp.length()-1);
			}
			jsonTemp = jsonTemp + "}";
			jsonTemp = jsonTemp + "]";
		}
		jsonTemp = jsonTemp + "}";
		return jsonTemp;
	}
	
	
	private static String transform(String dataDicName,Object value) { 
		String returnStr = "";
		List<TSType> types = TSTypegroup.allTypes.get(dataDicName.toLowerCase());
		if (types == null || types.size() == 0) {
			if (dataDicName.equals("sysArea")) {
				types = new ArrayList<TSType>();
				TSType type= new TSType();
				 SysAreaCodeEntity sysArea = SysAreaCodeEntity.allSysAreas.get(value);
				 if (sysArea != null) {
					 type.setTypecode(sysArea.getCode());
					 type.setTypename(sysArea.getAreaname());
					 types.add(type);
				 }
			}
		}
		if(types != null){
			for (TSType type : types) {
				if ((type.getTypecode().equals(value))) {
					returnStr = type.getTypename();
				}
			}
		}

		return returnStr;	
	}

	/**
	 * 计算指定列的合计
	 * @param filed 字段名
	 * @param list 列表数据
	 * @return
	 */
	private static Object getTotalValue(String fieldName,List list){
		Double sum = 0D;
		try{
			for (int j = 0; j < list.size(); j++) {
				Double v = 0d;
				String vstr =String.valueOf(fieldNametoValues(fieldName, list.get(j)));
				if(!StringUtil.isEmpty(vstr)){
					v = Double.valueOf(vstr);
				}else{
					
				}
				sum+=v;
			}
		}catch (Exception e) {
			return "";
		}
		return sum;
	}
	/**
	 * 循环LIST对象拼接自动完成控件数据
	 * @param fields
	 * @param total
	 * @param list
	 * @throws Exception 
	 */
	public static String getAutoList(Autocomplete autocomplete, List list) throws Exception {
		String field=autocomplete.getLabelField()+","+autocomplete.getValueField();
		String[] fields=field.split(",");
		Object[] values = new Object[fields.length];
		String jsonTemp = "{\'totalResultsCount\':1,\'geonames\':[";
		if(list.size()>0)
		{
		for (int j = 0; j < list.size(); j++) {
			jsonTemp = jsonTemp + "{'nodate':'yes',";
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				values[i] = fieldNametoValues(fieldName, list.get(j));
				jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + values[i] + "\'";
				if (i != fields.length - 1) {
					jsonTemp = jsonTemp + ",";
				}
			}
			if (j != list.size() - 1) {
				jsonTemp = jsonTemp + "},";
			} else {
				jsonTemp = jsonTemp + "}";
			}
		}
		}
		else {
			jsonTemp+="{'nodate':'数据不存在'}";
		}
		jsonTemp = jsonTemp + "]}";
		return JSONObject.fromObject(jsonTemp).toString();
	}
	/**
	 * 循环LIST对象拼接DATATABLE格式的JSON数据
	 * @param fields
	 * @param total
	 * @param list
	 */
	private static String datatable(String field, int total, List list) throws Exception {
		String[] fields=field.split(",");
		Object[] values = new Object[fields.length];
		String jsonTemp = "{\'iTotalDisplayRecords\':" + total + ",\'iTotalRecords\':" + total + ",\'aaData\':[";
		if(null != list && list.size() > 0){
			for (int j = 0; j < list.size(); j++) {
				String id = "";
				jsonTemp = jsonTemp + "{";
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i].toString();
					values[i] = fieldNametoValues(fieldName, list.get(j));
					if("id".equalsIgnoreCase(fieldName)){
						id = values[i] == null ? "" : values[i].toString();
					}
					jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + values[i] + "\'";
					if (i != fields.length - 1) {
						jsonTemp = jsonTemp + ",";
					}
				}
				if (j != list.size() - 1) {
					jsonTemp = jsonTemp + ",'DT_RowId':"+(id)+ "},";
				} else {
					jsonTemp += ",'DT_RowId':"+(id)+"}";
				}
			}
		}
		jsonTemp = jsonTemp + "]}";
		return jsonTemp;
	}
	
	/**
	 * 返回列表JSONObject对象
	 * @param field
	 * @param dataGrid
	 * @return
	 */
	private static JSONObject getJson(DataGrid dg) {
		JSONObject jObject = null;
		try {
			if(!StringUtil.isEmpty(dg.getFooter())){
				jObject = JSONObject.fromObject(listtojson(dg.getField().split(","), dg.getTotal(),dg.getsEcho(), dg.getReaults(),dg.getFooter().split(","),dg.getDataDicMap(),dg.getDateFormatMap()));
			}else{
				jObject = JSONObject.fromObject(listtojson(dg.getField().split(","), dg.getTotal(),dg.getsEcho(), dg.getReaults(),null,dg.getDataDicMap(),dg.getDateFormatMap()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject;

	}
	/**
	 * 返回列表JSONObject对象
	 * @param field
	 * @param dataGrid
	 * @return
	 */
	private static JSONObject getJson(DataTableReturn dataTable,String field) {
		JSONObject jObject = null;
		try {
			jObject = JSONObject.fromObject(datatable(field, dataTable.getiTotalDisplayRecords(), dataTable.getAaData()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject;

	}


	/**
	 * 获取指定字段类型 <b>Summary: </b> getColumnType(请用一句话描述这个方法的作用)
	 * 
	 * @param fileName
	 * @param fields
	 * @return
	 */
	public static String getColumnType(String fileName, Field[] fields) {
		String type = "";
		if (fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName(); // 获取属性的名字
				String filedType = fields[i].getGenericType().toString(); // 获取属性的类型
				if (fileName.equals(name)) {
					if (filedType.equals("class java.lang.Integer")) {
						filedType = "int";
						type = filedType;
					}else if (filedType.equals("class java.lang.Short")) {
						filedType = "short";
						type = filedType;
					}else if (filedType.equals("class java.lang.Double")) {
						filedType = "double";
						type = filedType;
					}else if (filedType.equals("class java.util.Date")) {
						filedType = "date";
						type = filedType;
					}else if (filedType.equals("class java.lang.String")) {
						filedType = "string";
						type = filedType;
					}else if (filedType.equals("class java.sql.Timestamp")) {
						filedType = "Timestamp";
						type = filedType;
					}else if (filedType.equals("class java.lang.Character")) {
						filedType = "character";
						type = filedType;
					}else if (filedType.equals("class java.lang.Boolean")) {
						filedType = "boolean";
						type = filedType;
					}else if (filedType.equals("class java.lang.Long")) {
						filedType = "long";
						type = filedType;
					}

				}
			}
		}
		return type;
	}

	/**
	 * 
	 * <b>Summary: </b> getSortColumnIndex(获取指定字段索引)
	 * 
	 * @param fileName
	 * @param fieldString
	 * @return
	 */
	protected static String getSortColumnIndex(String fileName, String[] fieldString) {
		String index = "";
		if (fieldString.length > 0) {
			for (int i = 0; i < fieldString.length; i++) {
				if (fileName.equals(fieldString[i])) {
					int j = i + 1;
					index = oConvertUtils.getString(j);
				}
			}
		}
		return index;

	}

	// JSON返回页面MAP方式
	public static void ListtoView(HttpServletResponse response, PageList pageList) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageList.getCount());
		map.put("rows", pageList.getResultList());
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			mapper.writeValue(response.getWriter(), map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 控件类型：easyui
	 * 返回datagrid JSON数据
	 * @param response
	 * @param dataGrid
	 */
	public static void datagrid(HttpServletResponse response,DataGrid dg) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		JSONObject object = TagUtil.getJson(dg);
		try {
			PrintWriter pw=response.getWriter();
			pw.write(object.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 控件类型：datatable
	 * 返回datatable JSON数据
	 * @param response
	 * @param datatable
	 */
	public static void datatable(HttpServletResponse response, DataTableReturn dataTableReturn,String field) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		JSONObject object = TagUtil.getJson(dataTableReturn,field);
		try {
			response.getWriter().write(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手工拼接JSON
	 */
	public static String getComboBoxJson(List<TSRole> list, List<TSRole> roles) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (TSRole node : list) {
			if (roles.size() > 0) {
				buffer.append("{\"id\":" + node.getId() + ",\"text\":\"" + node.getRoleName() + "\"");
				for (TSRole node1 : roles) {
					if (node.getId() == node1.getId()) {
						buffer.append(",\"selected\":true");
					}
				}
				buffer.append("},");
			} else {
				buffer.append("{\"id\":" + node.getId() + ",\"text\":\"" + node.getRoleName() + "\"},");
			}

		}
		buffer.append("]");

		// 将,\n]替换成\n]

		String tmp = buffer.toString();
		tmp = tmp.replaceAll(",]", "]");
		return tmp;

	}

	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public static List<ComboBox> getComboBox(List all, List in, ComboBox comboBox) {
		List<ComboBox> comboxBoxs = new ArrayList<ComboBox>();
		String[] fields = new String[] { comboBox.getId(), comboBox.getText() };
		Object[] values = new Object[fields.length];
		for (Object node : all) {
			ComboBox box = new ComboBox();
			ReflectHelper reflectHelper=new ReflectHelper(node);
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				values[i] = reflectHelper.getMethodValue(fieldName);
			}
			box.setId(values[0].toString());
			box.setText(values[1].toString());
			if (in != null) {
				for (Object node1 : in) {
					ReflectHelper reflectHelper2=new ReflectHelper(node);
					if (node1 != null) {
						String fieldName = fields[0].toString();
						String	test = reflectHelper2.getMethodValue(fieldName).toString();
						if (values[0].toString().equals(test)) {
							box.setSelected(true);
						}
					}
				}
			}
			comboxBoxs.add(box);
		}
		return comboxBoxs;

	}
	/**
	 * 获取自定义函数名
	 * 
	 * @param functionname
	 * @return
	 */
	public static String getFunction(String functionname) {
		int index = functionname.indexOf("(");
		if (index == -1) {
			return functionname;
		} else {
			return functionname.substring(0, functionname.indexOf("("));
		}
	}

	/**
	 * 获取自定义函数的参数
	 * 
	 * @param functionname
	 * @return
	 */
	public static String getFunParams(String functionname) {
		int index = functionname.indexOf("(");
		String param="";
		if (index != -1) {
			String testparam = functionname.substring(functionname.indexOf("(")+1,
					functionname.length() - 1);
			if(StringUtil.isNotEmpty(testparam))
			{
			String[] params=testparam.split(",");
			for (String string : params) {
				param+="'\"+rec."+ string + "+\"',";
			}
			}
		} 
		param+="'\"+index+\"'";//传出行索引号参数
		return param;
	}
	
	/**
	 * 控件类型：bootstrap
	 * 返回datatable JSON数据
	 * @param response
	 * @param orgList 
	 * @param dataGrid
	 */
	public static void dataTable(HttpServletResponse response,DataGrid dg, List<OrganizationEntity> orgList) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		JSONObject jObject = TagUtil.getDTJson(dg,orgList);
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回列表JSONObject对象
	 * @param orgList 
	 * @param field
	 * @param dataTable
	 * @return
	 */
	private static JSONObject getDTJson(DataGrid dg, List<OrganizationEntity> orgList) {
		JSONObject jObject = null;
		try {
			jObject = JSONObject.fromObject(tojson(dg.getField(),dg.getReaults(), dg.getTotal(), dg.getsEcho(),orgList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject;

	}
	
	private static String tojson(String field, List<?> list, int count, int sEcho, List<OrganizationEntity> orgList) {
		String[] fields=field.split(",");
		Object[] values = new Object[fields.length];

		String jsonTemp = "{\"sEcho\":" + sEcho + ",\'iTotalRecords\':" + count
				+ ",\"iTotalDisplayRecords\":" + count + ",\'aaData\':[";
		if(null != list && list.size() > 0){
			for (int j = 0; j < list.size(); j++) {
				jsonTemp = jsonTemp + "{";
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i].toString();
					values[i] = fieldNametoValuesForUser(fieldName, list.get(j),orgList);
					jsonTemp = jsonTemp + "\'" + fieldName + "\'" + ":\'" + values[i] + "\'";
					if (i != fields.length - 1) {
						jsonTemp = jsonTemp + ",";
					}
				}
				if (j != list.size() - 1) {
					jsonTemp = jsonTemp + ",'DT_RowId':"+(j+1)+ "},";
				} else {
					jsonTemp += ",'DT_RowId':"+(j+1)+"}";
				}
			}
		}

		jsonTemp = jsonTemp + "]}";
		return jsonTemp;
	}
	
	/**
	 * User 获取对象内对应字段的值
	 * @param orgList 
	 * @param fields
	 */
	public static Object fieldNametoValuesForUser(String FiledName, Object o, List<OrganizationEntity> orgList){
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		ReflectHelper reflectHelper=new ReflectHelper(o);
		if(o instanceof TSUser){
			String usertype = ((TSUser) o).getUsertype();
			//系统用户类型 管理部门
			if(usertype.equals("0")){
				if (FiledName.indexOf("_") == -1) {
					fieldName = FiledName;
				} else {
					fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
					childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
				}
				value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
				if (value !=""&&value != null && FiledName.indexOf("_") != -1) {
					value = fieldNametoValuesForUser(childFieldName, value,orgList);
				}
				if(value != "" && value != null) {
					value = value.toString().replaceAll("\r\n", "");
				}
//				System.out.println(" ====value111111111===== "+ value);
				return value;
			//系统用户类型 质检机构
			}else if(usertype.equals("1")){
				if (FiledName.indexOf("_") == -1) {
					fieldName = FiledName;
				} else {
					fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
					childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
				}
				if(fieldName.indexOf("TSDepart") != -1){
					//value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
					
					for (OrganizationEntity organizationEntity : orgList) {
						String deptId = ((TSUser) o).getOrganizationId();
						
						if(organizationEntity.getId().equals(deptId)){
							value = organizationEntity.getOgrname();
							break;
						}
					}
					
//					if (value !=""&&value != null && FiledName.indexOf("_") != -1) {
//						value = fieldNametoValuesForUser(childFieldName, value,orgList);
//					}
				}else{
					value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
					if (value !=""&&value != null && FiledName.indexOf("_") != -1) {
						value = fieldNametoValuesForUser(childFieldName, value,orgList);
					}
				}				

				if(value != "" && value != null) {
					value = value.toString().replaceAll("\r\n", "");
				}
//				System.out.println(" ====value22222222===== "+ value);
				return value;
			}
		}else{
			if (FiledName.indexOf("_") == -1) {
				fieldName = FiledName;
			} else {
				fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
				childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
			}
			value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
			if (value !=""&&value != null && FiledName.indexOf("_") != -1) {
				value = fieldNametoValues(childFieldName, value);
			}
			if(value != "" && value != null) {
			value = value.toString().replaceAll("\r\n", "");
			}
			return value;
		}
		return value;
	}
}
