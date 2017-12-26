package com.hippo.nky.controller.organization;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;

import jeecg.system.pojo.base.TSBaseUser;
import jeecg.system.pojo.base.TSDepart;
import jeecg.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.service.organization.OrganizationServiceI;

/**   
 * @Title: Controller
 * @Description: 质检中心
 * @author nky
 * @date 2013-10-22 10:05:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/organizationController")
public class OrganizationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationServiceI organizationService;
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
	 * 质检中心列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "organization")
	public ModelAndView organization(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/organization/organizationList");
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
	public void datagrid(OrganizationEntity organization,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(OrganizationEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, organization, request.getParameterMap());
//		this.organizationService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		JSONObject jObject = organizationService.getDatagrid(organization, dataGrid);
		responseDatagrid(response, jObject);
	}
	
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
	
	@RequestMapping(params = "projectdatagrid")
	public void projectdatagrid(OrganizationEntity organization,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OrganizationEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, organization, request.getParameterMap());
		this.organizationService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 删除质检中心
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(OrganizationEntity organization, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		organization = systemService.getEntity(OrganizationEntity.class, organization.getId());
		// 判断该质检机构有没有用户，若有则提示不能删除
		CriteriaQuery cq = new CriteriaQuery(TSBaseUser.class);
		cq.eq("TSDepart.id", organization.getId());
		cq.add();
		List<TSBaseUser> uList = systemService.getListByCriteriaQuery(cq, false);
		if (uList != null && uList.size() > 0) {
			message = "质检机构有用户信息,不能删除";
			j.setMsg(message);
			return j;
		}
		message = "质检中心删除成功";
		organizationService.delete(organization);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加质检中心
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(OrganizationEntity organization, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(organization.getId())) {
			message = "质检中心更新成功";
			OrganizationEntity t = organizationService.get(OrganizationEntity.class, organization.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(organization, t);
				organizationService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "质检中心更新失败";
			}
		} else {
			message = "质检中心添加成功";
			organizationService.save(organization);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 质检中心列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(OrganizationEntity organization, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(organization.getId())) {
			organization = organizationService.getEntity(OrganizationEntity.class, organization.getId());
			String code = organization.getAreacode();
			if(ConverterUtil.isNotEmpty(code)){
				// 取得行政区划
				req.setAttribute("areacodeList2", organizationService.getSysAreaForString(code));
			}
			req.setAttribute("organization", organization);
		}
		// 取得行政区划
		req.setAttribute("areacodeList", organizationService.getSysAreaForString("320000"));
		return new ModelAndView("com/hippo/nky/organization/organization");
	}

	/**
	 *取得机构名称
	 * 
	 * @return
	 */
	@RequestMapping(params = "getorgname")
	@ResponseBody
	public AjaxJson getOrgname(OrganizationEntity organization, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> attributes = new HashMap<String, Object>();
		organization = systemService.getEntity(OrganizationEntity.class, organization.getCode());
		if (organization != null) {

			attributes.put("orgname", organization.getOgrname());
		} else {
			attributes.put("orgname", "");
		}

		j.setAttributes(attributes);
		return j;
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
			j = systemService.uniquenessCheck("NKY_ORGANIZATION_INFO", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("机构代码已存在!");
			}

		} 
		if (StringUtils.equals(checkFlg, "2")) {
			String constraint = "OGRNAME" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param");
			j = systemService.uniquenessCheck("NKY_ORGANIZATION_INFO", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("机构名称已存在!");
			}
		}
		return j;
	}
}
