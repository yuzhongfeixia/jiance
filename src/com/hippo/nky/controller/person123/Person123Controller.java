package com.hippo.nky.controller.person123;
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

import com.hippo.nky.entity.person123.Person123Entity;
import com.hippo.nky.service.person123.Person123ServiceI;

/**   
 * @Title: Controller
 * @Description: person123
 * @author zhangdaihao
 * @date 2013-06-04 17:32:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/person123Controller")
public class Person123Controller extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Person123Controller.class);

	@Autowired
	private Person123ServiceI person123Service;
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
	 * person123列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "person123")
	public ModelAndView person123(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/person123/person123List");
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
	public void datagrid(Person123Entity person123,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Person123Entity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, person123);
		this.person123Service.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除person123
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(Person123Entity person123, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		person123 = systemService.getEntity(Person123Entity.class, person123.getId());
		message = "删除成功";
		person123Service.delete(person123);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加person123
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(Person123Entity person123, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(person123.getId())) {
			message = "更新成功";
			Person123Entity t = person123Service.get(Person123Entity.class, person123.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(person123, t);
				person123Service.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			person123Service.save(person123);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * person123列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(Person123Entity person123, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(person123.getId())) {
			person123 = person123Service.getEntity(Person123Entity.class, person123.getId());
			req.setAttribute("person123Page", person123);
		}
		return new ModelAndView("com/hippo/nky/person123/person123");
	}
}
