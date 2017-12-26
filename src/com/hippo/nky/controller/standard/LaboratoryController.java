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

import com.hippo.nky.entity.standard.LaboratoryEntity;
import com.hippo.nky.service.standard.LaboratoryServiceI;

/**   
 * @Title: Controller
 * @Description: 风险评估实验室
 * @author zhangdaihao
 * @date 2013-08-15 16:44:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/laboratoryController")
public class LaboratoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LaboratoryController.class);

	@Autowired
	private LaboratoryServiceI laboratoryService;
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
	 * 风险评估实验室列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "laboratory")
	public ModelAndView laboratory(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/laboratoryList");
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
	public void datagrid(LaboratoryEntity laboratory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(LaboratoryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, laboratory);
		this.laboratoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除风险评估实验室
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LaboratoryEntity laboratory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		laboratory = systemService.getEntity(LaboratoryEntity.class, laboratory.getId());
		message = "删除成功";
		laboratoryService.delete(laboratory);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加风险评估实验室
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LaboratoryEntity laboratory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(laboratory.getId())) {
			message = "更新成功";
			LaboratoryEntity t = laboratoryService.get(LaboratoryEntity.class, laboratory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(laboratory, t);
				laboratoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			laboratoryService.save(laboratory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 风险评估实验室列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LaboratoryEntity laboratory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(laboratory.getId())) {
			laboratory = laboratoryService.getEntity(LaboratoryEntity.class, laboratory.getId());
			req.setAttribute("laboratoryPage", laboratory);
		}
		return new ModelAndView("com/hippo/nky/standard/laboratory");
	}
}
