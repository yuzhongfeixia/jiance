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

import com.hippo.nky.entity.standard.ToxicologyEntity;
import com.hippo.nky.service.standard.ToxicologyServiceI;

/**   
 * @Title: Controller
 * @Description: 毒理学
 * @author nky
 * @date 2013-11-04 14:39:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/toxicologyController")
public class ToxicologyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ToxicologyController.class);

	@Autowired
	private ToxicologyServiceI toxicologyService;
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
	 * 毒理学列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toxicology")
	public ModelAndView toxicology(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/toxicologyList");
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
	public void datagrid(ToxicologyEntity toxicology,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ToxicologyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, toxicology, request.getParameterMap());
		this.toxicologyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除毒理学
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ToxicologyEntity toxicology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		toxicology = systemService.getEntity(ToxicologyEntity.class, toxicology.getId());
		message = "毒理学删除成功";
		toxicologyService.delete(toxicology);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加毒理学
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ToxicologyEntity toxicology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(toxicology.getId())) {
			message = "毒理学更新成功";
			ToxicologyEntity t = toxicologyService.get(ToxicologyEntity.class, toxicology.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(toxicology, t);
				toxicologyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "毒理学更新失败";
			}
		} else {
			message = "毒理学添加成功";
			toxicologyService.save(toxicology);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 毒理学列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ToxicologyEntity toxicology, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(toxicology.getId())) {
			toxicology = toxicologyService.getEntity(ToxicologyEntity.class, toxicology.getId());
			req.setAttribute("toxicologyPage", toxicology);
		}
		return new ModelAndView("com/hippo/nky/standard/toxicology");
	}
}
