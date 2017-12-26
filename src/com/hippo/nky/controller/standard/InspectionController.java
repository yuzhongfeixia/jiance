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

import com.hippo.nky.entity.standard.InspectionEntity;
import com.hippo.nky.service.standard.InspectionServiceI;

/**   
 * @Title: Controller
 * @Description: 质检中心
 * @author zhangdaihao
 * @date 2013-08-15 16:43:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/inspectionController")
public class InspectionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InspectionController.class);

	@Autowired
	private InspectionServiceI inspectionService;
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
	@RequestMapping(params = "inspection")
	public ModelAndView inspection(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/inspectionList");
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
	public void datagrid(InspectionEntity inspection,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(InspectionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, inspection);
		this.inspectionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除质检中心
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(InspectionEntity inspection, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		inspection = systemService.getEntity(InspectionEntity.class, inspection.getId());
		message = "删除成功";
		inspectionService.delete(inspection);
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
	public AjaxJson save(InspectionEntity inspection, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(inspection.getId())) {
			message = "更新成功";
			InspectionEntity t = inspectionService.get(InspectionEntity.class, inspection.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(inspection, t);
				inspectionService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			inspectionService.save(inspection);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 质检中心列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(InspectionEntity inspection, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(inspection.getId())) {
			inspection = inspectionService.getEntity(InspectionEntity.class, inspection.getId());
			req.setAttribute("inspectionPage", inspection);
		}
		return new ModelAndView("com/hippo/nky/standard/inspection");
	}
}
