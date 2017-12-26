package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.StringUtil;


/**
 * 
 * 选择下拉框
 * 
 * @author: lianglaiyang
 * @date： 日期：2013-04-18
 * @version 1.0
 */
public class DictSelectTag extends TagSupport {
	
	private String typeGroupCode;	//数据字典类型
	private String field;	//选择表单的Name  EAMPLE:<select name="selectName" id = "" />
	private String id;	//选择表单ID   EAMPLE:<select name="selectName" id = "" />
	private String defaultVal;	//默认值 
	private String divClass;	//DIV样式
	private String labelClass;	//Label样式
	private String title;	//label显示值
	private boolean hasLabel = true;	//是否显示label
	private String customData; // 自定义字典数据
	private String extend; // 自定义拓展属性
	//json转换中的系统保留字
	protected static Map syscode = new HashMap<String,String>();
	static{
		syscode.put("class", "clazz");
	}
	
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(divClass)) {
			divClass = "form";	//默认form样式
		}
		if (StringUtils.isBlank(labelClass)){
			labelClass = "Validform_label";	//默认label样式
		}
		
		TSTypegroup typeGroup = null;
		
		List <Map<String,Object>> groupSelect = null;  
		List<TSType> types = new ArrayList<TSType>();
		if(StringUtils.isNotEmpty(customData) && customData.indexOf("#GKV#") > 0){
			groupSelect = convertStringToTSTypeGroup(customData);
		}else if (StringUtils.isNotEmpty(customData)){
			types = convertStringToTSType(customData);
		} else {
			if(typeGroupCode != null){
				typeGroup = TSTypegroup.allTypeGroups.get(this.typeGroupCode.toLowerCase());
				types = TSTypegroup.allTypes.get(this.typeGroupCode.toLowerCase());
			}
		}
		
		if (hasLabel) {
			if (StringUtils.isBlank(this.title)) {
				this.title = StringUtils.isEmpty(typeGroup.getTypegroupname()) ? "" : typeGroup.getTypegroupname();
			}
			sb.append(this.title+":");
			sb.append("</label>");
		}
		sb.append("<select name=\""+field+"\"");
		if (!StringUtils.isBlank(this.id)) {
			 sb.append(" id=\""+id+"\"");
		}
		if(StringUtils.isNotEmpty(this.extend)){
			sb.append(extendAttribute(extend));
		}
		// 如果扩展属性中设置了class则在前添加m-wrap的样式 如果没有则默认m-wrap small
		Pattern pattern=Pattern.compile("class=['\"]([^'\"]*)[^>]");   
		Matcher matcher=pattern.matcher(sb.toString());
		if (matcher.find()) {
			String value = matcher.group().replace("class=", "").replace("\"", "").replace("'", "").trim();
			sb=new StringBuffer(sb.toString().replaceAll("class=['\"]([^'\"]*)[^>]",
					(" class=\"m-wrap " + value + "\"")));
		} else {
			sb.append(" class=\"m-wrap small\"");
		}
		
		sb.append(">");
		// 拼接option
		sb.append(" <option value=\"\"");
		if (StringUtils.isEmpty(this.defaultVal)) {
			sb.append(" selected = \"selected\"");
		}
		sb.append(">");
		if(groupSelect !=null){
			for (Map<String,Object> groupMap : groupSelect) {
				if (groupMap.get("key").equals(this.defaultVal)) {
					sb.append(" <option value=\""+groupMap.get("key")+"\" selected=\"selected\">");
				}else {
					sb.append(" <option value=\""+groupMap.get("key")+"\" >");
				}
				sb.append(groupMap.get("value"));
				sb.append("</option>");
				if(null!=groupMap.get("list")){
					//List groupMap.get("list");
					for (TSType type : (List<TSType>)groupMap.get("list")) {
						if (type.getTypecode().equals(this.defaultVal)) {
							sb.append(" <option value=\""+type.getTypecode()+"\" selected=\"selected\">");
						}else {
							sb.append(" <option value=\""+type.getTypecode()+"\">");
						}
						sb.append("　" + type.getTypename());
						sb.append("</option>");
					}
				}
				
			}
		}else if(null != types){
			for (TSType type : types) {
				if (type.getTypecode().equals(this.defaultVal)) {
					sb.append(" <option value=\""+type.getTypecode()+"\" selected=\"selected\">");
				}else {
					sb.append(" <option value=\""+type.getTypecode()+"\">");
				}
				sb.append(type.getTypename());
				sb.append("</option>");
			}
		}
		
		sb.append("</select>");
		if (hasLabel) {
			sb.append("</div>");
		}
		
		return sb;
	}

	/**
	 * 将采用xxx#KV#yyy#EM#xxx#KV#yyy的自定义数据加载成TsType对象
	 * 
	 * @param dataString 字符串
	 * @return TStype TsType对象
	 */
	private List<TSType> convertStringToTSType(String dataString) {
		List<TSType> list = new ArrayList<TSType>();
		String[] elements = dataString.split(ConverterUtil.SEPARATOR_ELEMENT);
		for(String element : elements){
			TSType tsType = new TSType();
			String[] keyValue = element.split(ConverterUtil.SEPARATOR_KEY_VALUE);
			if(keyValue.length >=2){
				tsType.setTypecode(keyValue[0]);
				tsType.setTypename(keyValue[1]);
				list.add(tsType);
			}
		}
		return list;
	}
	/**
	 * 将采用xxx#KV#yyy#GKV#xxx#KV#yyy#GEM#的自定义数据加载成List<TsType>对象
	 * 
	 * @param dataString 字符串
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String,Object>> convertStringToTSTypeGroup(String dataString) {
		
		List <Map<String,Object>> groupList = new ArrayList<Map<String,Object>>();
		
		String[] groupRowArr = dataString.split("#GEM#");
		for(int i=0;i<groupRowArr.length;i++){
			Map<String,Object>  groupMap = new HashMap<String,Object>();
			if(ConverterUtil.isNotEmpty(groupRowArr[i])){
				String[] groupColumn = groupRowArr[i].split("#GKV#");
				if(ConverterUtil.isNotEmpty(groupColumn[0])){
					String[] groupKey = groupColumn[0].split(ConverterUtil.SEPARATOR_KEY_VALUE);
					if(groupKey.length >= 2){
						groupMap.put("key", groupKey[0]);
						groupMap.put("value", groupKey[1]);
						if(groupColumn.length > 1 && ConverterUtil.isNotEmpty(groupColumn[1])){
							groupMap.put("list", convertStringToTSType(groupColumn[1]));
						}
						groupList.add(groupMap);
					}
				}
			}
			
		}
		return groupList;
	}

	/**
	 * 生成扩展属性
	 * @param field
	 * @return
	 */
	private String extendAttribute(String field) {
		if(StringUtil.isEmpty(field)){
			return "";
		}
		field = dealSyscode(field,1);
		StringBuilder re = new StringBuilder();
		try{
			JSONObject obj = JSONObject.fromObject(field);
			Iterator it = obj.keys();
			while(it.hasNext()){
				String key = String.valueOf(it.next());
				JSONObject nextObj =((JSONObject)obj.get(key));
				Iterator itvalue =nextObj.keys();
				re.append(key+"="+"\"");
				if(nextObj.size()<=1){
					String onlykey = String.valueOf(itvalue.next());
					if("value".equals(onlykey)){
						re.append(nextObj.get(onlykey)+"");
					}else{
						re.append(onlykey+":"+nextObj.get(onlykey)+"");
					}
				}else{
					while(itvalue.hasNext()){
						String multkey = String.valueOf(itvalue.next());
						String multvalue = nextObj.getString(multkey);
						re.append(multkey+":"+multvalue+",");
					}
					re.deleteCharAt(re.length()-1);
				}
				re.append("\" ");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dealSyscode(re.toString(), 2);
	}
	
	/**
	 * 处理否含有json转换中的保留字
	 * @param field
	 * @param flag 1:转换 2:还原
	 * @return
	 */
	private String dealSyscode(String field,int flag) {
		String change = field;
		Iterator it = syscode.keySet().iterator();
		while(it.hasNext()){
			String key = String.valueOf(it.next());
			String value = String.valueOf(syscode.get(key));
			if(flag==1){
				change = field.replaceAll(key, value);
			}else if(flag==2){
				change = field.replaceAll(value, key);
			}
		}
		return change;
	}
	
	public String getTypeGroupCode() {
		return typeGroupCode;
	}

	public void setTypeGroupCode(String typeGroupCode) {
		this.typeGroupCode = typeGroupCode;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}

	public String getLabelClass() {
		return labelClass;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHasLabel() {
		return hasLabel;
	}

	public void setHasLabel(boolean hasLabel) {
		this.hasLabel = hasLabel;
	}

	public String getCustomData() {
		return customData;
	}

	public void setCustomData(String customData) {
		this.customData = customData;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
}
