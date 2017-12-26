package com.hippo.nky.controller.monitoring;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.service.monitoring.NkyMonitoringSiteServiceI;

/**   
 * @Title: Controller
 * @Description: 监测点
 * @author nky
 * @date 2013-10-18 16:33:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/nkyMonitoringSiteController")
public class NkyMonitoringSiteController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NkyMonitoringSiteController.class);
	
	private static final String NAME_SPACE = "com.hippo.nky.entity.sample.SamplingInfoEntity.";

	@Autowired
	private NkyMonitoringSiteServiceI nkyMonitoringSiteService;
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
	 * 监测点列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "nkyMonitoringSite")
	public ModelAndView nkyMonitoringSite(HttpServletRequest request) {
		// 取得行政区划
		request.setAttribute("areacodeList", nkyMonitoringSiteService.getSysAreaForString("320000"));
		return new ModelAndView("com/hippo/nky/monitoring/nkyMonitoringSiteList");
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
	public void datagrid(NkyMonitoringSiteEntity nkyMonitoringSite,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) 
			 throws IllegalArgumentException, SecurityException, IllegalAccessException,
				InvocationTargetException, NoSuchMethodException, InstantiationException {
		CriteriaQuery cq = new CriteriaQuery(NkyMonitoringSiteEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, nkyMonitoringSite, request.getParameterMap());
		this.nkyMonitoringSiteService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		
//		// 查询条件
//		// 监测点代码
//		String code = nkyMonitoringSite.getCode();
//		// 监测点名
//		String name = nkyMonitoringSite.getName();
//		// 所属市
//		String areacode = nkyMonitoringSite.getAreacode();
//		// 所属区县
//		String areacode2 = nkyMonitoringSite.getAreacode2();
//		// 监测环节
//		String monitoringLink = nkyMonitoringSite.getMonitoringLink();
////		String[] notQueryAttr = {"code", "name", "areacode", "areacode2","monitorLink"};
////		String where = ConverterUtil.getDataGridQuerySql(nkyMonitoringSite, notQueryAttr);
//		String col1 = "COUNT(1)";
//		String col2 = "*";
//		String sql = "SELECT #{PARAM} FROM NKY_MONITORING_SITE" ;
//		if(StringUtil.isNotEmpty(code)){
//			sql = sql + " where code ="+"'"+code+"'";
//		}
//		if(StringUtil.isNotEmpty(name)){
//			if(StringUtil.isNotEmpty(code)){
//				sql = sql + " AND name="+"'"+name+"'";
//			} else {
//				sql = sql+ " where name="+"'"+name+"'";
//			}
//		}
//		if(StringUtil.isNotEmpty(areacode)){
//			if(StringUtil.isNotEmpty(code) || StringUtil.isNotEmpty(name)) {
//				sql = sql + "AND areacode="+"'"+areacode+"'";
//			}else {
//				sql = sql + " where areacode="+"'"+areacode+"'";
//			}
//		}
//		if(StringUtil.isNotEmpty(areacode2)){
//			if(StringUtil.isNotEmpty(code) || StringUtil.isNotEmpty(name) || StringUtil.isNotEmpty(areacode)) {
//				sql = sql + "AND areacode2="+"'"+areacode2+"'";
//			} else {
//				sql = sql + " where areacode2="+"'"+areacode2+"'";
//			}
//		}
//		if(StringUtil.isNotEmpty(monitoringLink)){
//			if(StringUtil.isNotEmpty(code) || StringUtil.isNotEmpty(name) || StringUtil.isNotEmpty(areacode) || StringUtil.isNotEmpty(areacode2)) {
//				sql = sql + "AND MONITORING_LINK="+"'"+monitoringLink+"'";
//			} else {
//				sql = sql + " where MONITORING_LINK="+"'"+monitoringLink+"'";
//			}
//		}
//
//		List<Map<String, Object>> nkyMonSiteList = null;
//		Long count = nkyMonitoringSiteService.getCountForJdbc(sql.replace("#{PARAM}", col1));
//		nkyMonSiteList = nkyMonitoringSiteService.findForJdbc(sql.replace("#{PARAM}", col2), dataGrid.getPage(), dataGrid.getRows());
//
//		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class);
//		cq.add();
//		List<SysAreaCodeEntity> areaList= systemService.getListByCriteriaQuery(cq, false);
//
//		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
//		dictionaryMap.put("monitoringLink", "allmonLink");
//
//		Map<String, Object> areaMap = new HashMap<String, Object>();
//		for (SysAreaCodeEntity enty : areaList) {
//			areaMap.put( enty.getCode(), enty.getAreaname());
//		}
//		dictionaryMap.put("areacode", areaMap);
//		dictionaryMap.put("areacode2", areaMap);
//
//		Db2Page[] db2Pages = ConverterUtil.autoGetEntityToPage(NkyMonitoringSiteEntity.class, dictionaryMap);
//		JSONObject jObject = TagUtil.getDatagridWithListMapAndDb2Page(nkyMonSiteList, ConverterUtil.toInteger(count), db2Pages, dataGrid);
//		DataUtils.responseDatagrid(response, jObject);
	}

	/**
	 * 删除监测点
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NkyMonitoringSiteEntity nkyMonitoringSite, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		nkyMonitoringSite = systemService.getEntity(NkyMonitoringSiteEntity.class, nkyMonitoringSite.getId());
		message = "监测点删除成功";
		nkyMonitoringSiteService.delete(nkyMonitoringSite);
		// 从sqlite模板文件中删除信息
		nkyMonitoringSiteService.deleteDataForTemplate(nkyMonitoringSite);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加监测点
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NkyMonitoringSiteEntity nkyMonitoringSite, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(nkyMonitoringSite.getId())) {
			message = "监测点更新成功";
			NkyMonitoringSiteEntity t = nkyMonitoringSiteService.get(NkyMonitoringSiteEntity.class, nkyMonitoringSite.getId());
			
			// 更新抽样信息表里的监测点code
			Map<String, Object> selCodition = new HashMap<String, Object>();
			selCodition.put("unitFullCode", nkyMonitoringSite.getCode());
			selCodition.put("unitFullName", t.getName());
			systemService.updateByMyBatis(NAME_SPACE + "updateSamplingMonitorSiteCode", selCodition);
			
			try {
				MyBeanUtils.copyBeanNotNull2Bean(nkyMonitoringSite, t);
				nkyMonitoringSiteService.saveOrUpdate(t);
				// 向sqlite模板文件中写入信息
				nkyMonitoringSiteService.updateDataForTemplate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "监测点更新失败";
			}
		} else {
			message = "监测点添加成功";
			nkyMonitoringSiteService.save(nkyMonitoringSite);
			// 向sqlite模板文件中写入信息
			nkyMonitoringSiteService.saveDataToTemplate(nkyMonitoringSite);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 监测点列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NkyMonitoringSiteEntity nkyMonitoringSite, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(nkyMonitoringSite.getId())) {
			nkyMonitoringSite = nkyMonitoringSiteService.getEntity(NkyMonitoringSiteEntity.class, nkyMonitoringSite.getId());
			String code = nkyMonitoringSite.getAreacode();
			if(ConverterUtil.isNotEmpty(code)){
				// 取得行政区划
				req.setAttribute("areacodeList2", nkyMonitoringSiteService.getSysAreaForString(code));
			}
			req.setAttribute("nkyMonitoringSite", nkyMonitoringSite);
		}
		// 取得行政区划
		req.setAttribute("areacodeList", nkyMonitoringSiteService.getSysAreaForString("320000"));
		return new ModelAndView("com/hippo/nky/monitoring/nkyMonitoringSite");
	}
	
	/**
	 * 重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isExsitCheck")
	@ResponseBody
	public AjaxJson isExsitCheck(HttpServletRequest request) {
		AjaxJson j = null;
		String checkFlg = request.getParameter("flg");
		if (StringUtils.equals(checkFlg, "1")) {
			String constraint = "CODE" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param");
			j = systemService.uniquenessCheck("NKY_MONITORING_SITE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("受检单位代码已存在!");
			}

		} 
		if (StringUtils.equals(checkFlg, "2")) {
			String constraint = "NAME" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param");
			j = systemService.uniquenessCheck("NKY_MONITORING_SITE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("受检单位名称已存在!");
			}
		}
		return j;
	}
}
