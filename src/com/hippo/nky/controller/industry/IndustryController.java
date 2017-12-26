package com.hippo.nky.controller.industry;
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

import com.hippo.nky.entity.industry.IndustryEntity;
import com.hippo.nky.service.industry.IndustryServiceI;

/**   
 * @Title: Controller
 * @Description: 行业信息表 
 * @author zhangdaihao
 * @date 2013-08-01 14:53:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/industryController")
public class IndustryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(IndustryController.class);

	@Autowired
	private IndustryServiceI industryService;
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
	 * 行业信息表 列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "industry")
	public ModelAndView industry(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/industry/industryList");
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
	public void datagrid(IndustryEntity industry,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(IndustryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, industry);
		this.industryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除行业信息表 
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(IndustryEntity industry, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		industry = systemService.getEntity(IndustryEntity.class, industry.getId());
		message = "删除成功";
		industryService.delete(industry);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加行业信息表 
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(IndustryEntity industry, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(industry.getId())) {
			message = "更新成功";
			IndustryEntity t = industryService.get(IndustryEntity.class, industry.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(industry, t);
				industryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			industryService.save(industry);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 行业信息表 列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(IndustryEntity industry, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(industry.getId())) {
			industry = industryService.getEntity(IndustryEntity.class, industry.getId());
			req.setAttribute("industryPage", industry);
		}
		return new ModelAndView("com/hippo/nky/industry/industry");
	}
}
