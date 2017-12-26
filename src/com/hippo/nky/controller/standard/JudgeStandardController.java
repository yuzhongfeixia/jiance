package com.hippo.nky.controller.standard;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.JudgeStandardEntity;
import com.hippo.nky.entity.standard.StandardVersionEntity;
import com.hippo.nky.service.standard.JudgeStandardServiceI;

/**   
 * @Title: Controller
 * @Description: 判定标准
 * @date 2013-06-18 16:01:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/judgeStandardController")
public class JudgeStandardController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JudgeStandardController.class);

	@Autowired
	private JudgeStandardServiceI judgeStandardService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 判定标准列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "judgeStandard")
	public ModelAndView judgeStandard(HttpServletRequest request) {
		String versionId = request.getParameter("versionId");
		request.setAttribute("versionId", versionId);
		request.setAttribute("isPublished", isPublished(versionId));
		// 取得用户类型
		String userType = ResourceUtil.getSessionUserName().getUsertype();
		request.setAttribute("userType", userType);
		return new ModelAndView("com/hippo/nky/standard/judgeStandardList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(JudgeStandardEntity judgeStandard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//没有版本id 返回空页面
		if(StringUtil.isEmpty(judgeStandard.getVid())){
			TagUtil.datagrid(response, dataGrid);
		}else{
			JSONObject jObject = this.judgeStandardService.getDatagrid3(judgeStandard, dataGrid);
			responseDatagrid(response, jObject);
		}
	}

	/**
	 * 删除判定标准
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(JudgeStandardEntity judgeStandard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		judgeStandard = systemService.getEntity(JudgeStandardEntity.class, judgeStandard.getId());
		message = "删除成功";
		judgeStandardService.delete(judgeStandard);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 双击table 元素  异步  添加判定标准 
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save(JudgeStandardEntity judgeStandard, HttpServletRequest request) {
		String rtnVal = "";
		String jId = request.getParameter("row_id");
		String jvalue = request.getParameter("jvalue");
		String junits = request.getParameter("junits");
//		String jvaluefrom = request.getParameter("jvaluefrom");
		String jstipulate = request.getParameter("jstipulate");

		if(!StringUtil.isEmpty(jId)){
			judgeStandard.setId(jId);
		}
		// 设置判定值
		if(!StringUtil.isEmpty(jvalue)){
			judgeStandard.setValue(new BigDecimal(jvalue));
			rtnVal = jvalue;
		}
		// 设置单位
		if(!StringUtil.isEmpty(junits)){
			judgeStandard.setUnits(Integer.parseInt(junits));
			rtnVal = getTypeNameByCode("unit",junits);
		}
		// 设置判定值来源
//		if(!StringUtil.isEmpty(jvaluefrom)){
//			judgeStandard.setValuefrom(Integer.parseInt(jvaluefrom));
//			rtnVal = getTypeNameByCode("valuefrom",jvaluefrom);
//		}
		// 设置使用规定
		if(!StringUtil.isEmpty(jstipulate)){
			judgeStandard.setStipulate(Integer.parseInt(jstipulate));
			rtnVal = getTypeNameByCode("stipulate",jstipulate);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(judgeStandard.getId())) {
			message = "更新成功";
			JudgeStandardEntity t = judgeStandardService.get(JudgeStandardEntity.class, judgeStandard.getId());
			try {
				if(!(t.getValue() == null ? "" : t.getValue().toString()).equals(rtnVal)){
					judgeStandard.setValuefrom(2);
				}
				MyBeanUtils.copyBeanNotNull2Bean(judgeStandard, t);
				judgeStandardService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			judgeStandardService.save(judgeStandard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return rtnVal;
	}
	
	/**
	 * 通过标签code查询标签名称 
	 * @param code
	 * @param org
	 * @return
	 */
	private String getTypeNameByCode(String code, String org) {
		String tempStr = "";
		List<TSType> types = TSTypegroup.allTypes.get(code);
	
		for (TSType type : types) {
			 if (type.getTypecode().equals(org)) {
				 tempStr = type.getTypename();
				 break;
			 }
		}
		return tempStr;
	}

	/**
	 * 快速设定判定标准
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(params = "saveMany")
	@ResponseBody
	public AjaxJson saveMany(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String value = request.getParameter("value");
//		String valuefrom = request.getParameter("valuefrom");
		String units = request.getParameter("units");
		String stipulate = request.getParameter("stipulate");
		
		AjaxJson j = new AjaxJson();
		//&& StringUtils.isEmpty(valuefrom)
		if(StringUtils.isEmpty(ids) || (StringUtils.isEmpty(value) && StringUtils.isEmpty(units) && StringUtils.isEmpty(stipulate))){
			message = "无更新";
			j.setSuccess(false);
			j.setMsg("无更新");
			return j;
		}
		
		String[] idArr = ids.split(",");
		message = "更新成功";
		for(int i=0;i<idArr.length;i++){
			JudgeStandardEntity judgeStandard = new JudgeStandardEntity();
			judgeStandard.setId(idArr[i]);
			// 设置判定值
			if(value!=null && !"".equals(value)){
				judgeStandard.setValue(new BigDecimal(value));
				judgeStandard.setValuefrom(2);
			}
			// 设置判定值来源
//			if(valuefrom!=null &&!"".equals(valuefrom)){
//				judgeStandard.setValuefrom(Integer.parseInt(valuefrom));
//			}
			// 设置单位
			if(units!=null && !"".equals(units)){
				judgeStandard.setUnits(Integer.parseInt(units));
			}
			// 设置使用规定
			if(stipulate!=null && !"".equals(stipulate)){
				judgeStandard.setStipulate(Integer.parseInt(stipulate));
			}
			if (StringUtil.isNotEmpty(judgeStandard.getId())) {
				JudgeStandardEntity t = judgeStandardService.get(JudgeStandardEntity.class, judgeStandard.getId());
				try {
					MyBeanUtils.copyBeanNotNull2Bean(judgeStandard, t);
					judgeStandardService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 判定标准列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(JudgeStandardEntity judgeStandard, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(judgeStandard.getId())) {
			judgeStandard = judgeStandardService.getEntity(JudgeStandardEntity.class, judgeStandard.getId());
			req.setAttribute("judgeStandardPage", judgeStandard);
		}
		// 限量标准版本选择列表
		List<StandardVersionEntity> limitVersionList = judgeStandardService.findHql("from StandardVersionEntity where category = ? order by createdate desc ", 3);
		req.setAttribute("limitVersionList", limitVersionList);
		return new ModelAndView("com/hippo/nky/standard/judgeStandard");
	}
	
	// -----------------------------------------------------------------------------------
	// 以下各函数可以提成共用部件 (Add by Quainty)
	// -----------------------------------------------------------------------------------
	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 限定标准选择页面跳转
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "limitStandard")
	public String limitStandard(HttpServletRequest req) throws UnsupportedEncodingException {
		req.setAttribute("jId", req.getParameter("jId"));
		req.setAttribute("versionId", req.getParameter("versionId"));
		req.setAttribute("cas", req.getParameter("cas"));
		req.setAttribute("agrname", URLDecoder.decode(req.getParameter("agrname"),"UTF-8"));
		return "com/hippo/nky/standard/judgeSelectedLimit";
	}
	
	/**
	 * 判断是否已经发布
	 * 
	 * @return
	 */
	public boolean isPublished(String versionId) {
		StandardVersionEntity svEntity = new StandardVersionEntity();
		svEntity.setId(versionId);
		svEntity = judgeStandardService.getEntity(StandardVersionEntity.class, svEntity.getId());
		Integer publishMark = svEntity.getPublishmark();
		if (Constants.ONE.equals(publishMark)) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(params = "getUnitsForJson",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public JSONObject getUnitsForJson(HttpServletRequest request) {
		/**
		 * "{'Belgrade':'Belgrade','Paris':'Paris','London':'London', 'selected':'Belgrade'}"
		 */
		String sel_data = request.getParameter("sel_data");
		String tp = request.getParameter("type");
		Map map = null;

		JSONObject obj = new JSONObject();
		if (tp.equals("tunits")) {
			map = getSelectType("unit",sel_data);
		} else if (tp.equals("tvaluefrom")) {
			map = getSelectType("valuefrom",sel_data);

		} else if (tp.equals("tstipulate")) {
			map = getSelectType("stipulate",sel_data);
		}
		return obj.fromObject(map);
	}
	/**
	 * 遍历数据字典值
	 * @param selType
	 * @param sel_data 
	 * @return
	 */
	private Map getSelectType(String selType, String sel_data) {
		List<TSType> types = TSTypegroup.allTypes.get(selType);
		Map<String, String> rtnMap = new HashMap<String, String>();
		for (TSType type : types) {
			if(sel_data.equals(type.getTypecode())){
				rtnMap.put(type.getTypecode(), type.getTypename());
				rtnMap.put("selected", type.getTypecode());
			}else{
				rtnMap.put(type.getTypecode(), type.getTypename());
			}
		}
		return rtnMap;
	}
	
	/**
	 * 参照  添加判定标准 
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveCZ")
	@ResponseBody
	public AjaxJson saveCZ(JudgeStandardEntity judgeStandard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(judgeStandard.getId())) {
			message = "更新成功";
			JudgeStandardEntity t = judgeStandardService.get(JudgeStandardEntity.class, judgeStandard.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(judgeStandard, t);
				judgeStandardService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			judgeStandardService.save(judgeStandard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
}
