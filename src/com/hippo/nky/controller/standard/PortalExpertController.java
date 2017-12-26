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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.standard.PortalExpertEntity;
import com.hippo.nky.service.standard.PortalExpertServiceI;

/**   
 * @Title: Controller
 * @Description: 专家组
 * @author zhangdaihao
 * @date 2013-08-14 17:24:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/portalExpertController")
public class PortalExpertController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PortalExpertController.class);

	@Autowired
	private PortalExpertServiceI portalExpertService;
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
	 * 专家组列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "portalExpert")
	public ModelAndView portalExpert(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/portalExpertList");
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
	public void datagrid(PortalExpertEntity portalExpert,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(PortalExpertEntity.class, dataGrid);
		cq.addOrder("orderno", SortDirection.asc);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, portalExpert);
		this.portalExpertService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除专家组
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PortalExpertEntity portalExpert, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		portalExpert = systemService.getEntity(PortalExpertEntity.class, portalExpert.getId());
		message = "删除成功";
		portalExpertService.delete(portalExpert);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加专家组
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PortalExpertEntity portalExpert, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(portalExpert.getId())) {
			message = "更新成功";
			PortalExpertEntity t = portalExpertService.get(PortalExpertEntity.class, portalExpert.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(portalExpert, t);
				portalExpertService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			portalExpertService.save(portalExpert);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 专家组列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PortalExpertEntity portalExpert, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(portalExpert.getId())) {
			portalExpert = portalExpertService.getEntity(PortalExpertEntity.class, portalExpert.getId());
			req.setAttribute("portalExpertPage", portalExpert);
		}
		return new ModelAndView("com/hippo/nky/standard/portalExpert");
	}
}
