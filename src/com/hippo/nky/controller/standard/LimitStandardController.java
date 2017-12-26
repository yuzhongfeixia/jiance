package com.hippo.nky.controller.standard;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

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

import com.hippo.nky.entity.standard.LimitStandardEntity;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.service.standard.LimitStandardServiceI;

/**   
 * @Title: Controller
 * @Description: 限量标准
 * @author zhangdaihao
 * @date 2013-07-03 14:36:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/limitStandardController")
public class LimitStandardController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LimitStandardController.class);

	@Autowired
	private LimitStandardServiceI limitStandardService;
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
	 * 限量标准列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "limitStandard")
	public ModelAndView limitStandard(HttpServletRequest request) {
		request.setAttribute("versionId", request.getParameter("versionId"));
		LimitStandardVersionEntity limitStandardVersion = systemService.getEntity(LimitStandardVersionEntity.class, request.getParameter("versionId"));
		request.setAttribute("standardCode", limitStandardVersion.getStandardCode());
		return new ModelAndView("com/hippo/nky/standard/limitStandardList");
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
	public void datagrid(LimitStandardEntity limitStandard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(LimitStandardEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, limitStandard);
		this.limitStandardService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除限量标准
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LimitStandardEntity limitStandard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		limitStandard = systemService.getEntity(LimitStandardEntity.class, limitStandard.getId());
		message = "删除成功";
		limitStandardService.delete(limitStandard);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加限量标准
	 * 
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LimitStandardEntity limitStandard, HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		//String versionid = request.getParameter("versionId");
		if (StringUtil.isNotEmpty(limitStandard.getId())) {
			message = "更新成功";
			LimitStandardEntity t = limitStandardService.get(LimitStandardEntity.class, limitStandard.getId());
				MyBeanUtils.copyBeanNotNull2Bean(limitStandard, t);
				limitStandardService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			//limitStandard.setVersionid(versionid);
			message = "添加成功";
			limitStandardService.save(limitStandard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 限量标准列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LimitStandardEntity limitStandard, HttpServletRequest req) {
		String versionId = req.getParameter("versionId");
		limitStandard.setVersionid(versionId);
		if (StringUtil.isNotEmpty(limitStandard.getId())) {
			limitStandard = limitStandardService.getEntity(LimitStandardEntity.class, limitStandard.getId());
		}
		Boolean isCopy = Boolean.parseBoolean(req.getParameter("isCopy"));
		if(isCopy){
			limitStandard.setId("");
		}
		req.setAttribute("limitStandardPage", limitStandard);
		return new ModelAndView("com/hippo/nky/standard/limitStandard");
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
		String constraint = "CAS" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param")
				+ ConverterUtil.SEPARATOR_ELEMENT + "VERSIONID"
				+ ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("versionid");
		j = systemService.uniquenessCheck("NKY_LIMIT_STANDARD", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("污染物CAS码已存在!");
		}
		return j;
	}
}
