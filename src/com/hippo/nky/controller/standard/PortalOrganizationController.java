package com.hippo.nky.controller.standard;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.standard.PortalOrganizationEntity;
import com.hippo.nky.service.standard.PortalOrganizationServiceI;

/**   
 * @Title: Controller
 * @Description: 组织机构
 * @author zhangdaihao
 * @date 2013-08-23 13:07:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/portalOrganizationController")
public class PortalOrganizationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PortalOrganizationController.class);

	@Autowired
	private PortalOrganizationServiceI portalOrganizationService;
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
	 * 组织机构列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "portalOrganization")
	public ModelAndView portalOrganization(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/portalOrganizationList");
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
	public void datagrid(PortalOrganizationEntity portalOrganization,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(PortalOrganizationEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, portalOrganization);
		this.portalOrganizationService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除组织机构
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PortalOrganizationEntity portalOrganization, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		portalOrganization = systemService.getEntity(PortalOrganizationEntity.class, portalOrganization.getId());
		message = "删除成功";
		portalOrganizationService.delete(portalOrganization);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加组织机构
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PortalOrganizationEntity portalOrganization, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(portalOrganization.getId())) {
			message = "更新成功";
			PortalOrganizationEntity t = portalOrganizationService.get(PortalOrganizationEntity.class, portalOrganization.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(portalOrganization, t);
				portalOrganizationService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			portalOrganizationService.save(portalOrganization);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 组织机构列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PortalOrganizationEntity portalOrganization, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(portalOrganization.getId())) {
			portalOrganization = portalOrganizationService.getEntity(PortalOrganizationEntity.class, portalOrganization.getId());
			req.setAttribute("portalOrganizationPage", portalOrganization);
		}
		return new ModelAndView("com/hippo/nky/standard/portalOrganization");
	}
}
