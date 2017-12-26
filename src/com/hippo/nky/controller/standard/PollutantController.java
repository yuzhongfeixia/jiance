package com.hippo.nky.controller.standard;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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

import com.hippo.nky.entity.standard.PollInfoEntity;
import com.hippo.nky.service.standard.PollutantServiceI;

/**   
 * @Title: Controller
 * @Description: 污染物基础信息
 * @author zhangdaihao
 * @date 2013-06-18 13:55:18
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pollutantController")
public class PollutantController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PollutantController.class);

	@Autowired
	private PollutantServiceI pollutantService;
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
	 * 污染物基础信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollutant")
	public ModelAndView pollutant(HttpServletRequest request) {
		// 取得分类ID
		String categoryId = request.getParameter("categoryId");
		if(StringUtil.isNotEmpty(categoryId)){
			request.setAttribute("categoryid", categoryId);
		}
		// 取得版本ID
		String versionId = request.getParameter("versionId");
		if(StringUtil.isNotEmpty(versionId)){
			request.setAttribute("versionid", versionId);
		}
		return new ModelAndView("com/hippo/nky/standard/pollutantList");
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
	public void datagrid(PollInfoEntity pollutant,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		CriteriaQuery cq = new CriteriaQuery(PollInfoEntity.class, dataGrid);
		cq.add(cq.and(Restrictions.eq("categoryid", categoryId),
				Restrictions.eq("versionid", versionId)));
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, pollutant);
		this.pollutantService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除污染物基础信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PollInfoEntity pollutant, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pollutant = systemService.getEntity(PollInfoEntity.class, pollutant.getId());
		message = "删除成功";
		pollutantService.delete(pollutant);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加污染物基础信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PollInfoEntity pollutant, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pollutant.getId())) {
			message = "更新成功";
			PollInfoEntity t = pollutantService.get(PollInfoEntity.class, pollutant.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pollutant, t);
				pollutantService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			pollutantService.save(pollutant);
//			 //保存关系表
//			PollInfoCategoryEntity pc = new PollInfoCategoryEntity();
//			pc.setPoid(pollutant.getId());
//			pc.setPcid(pollutant.getCategoryid());
//			pc.setVid(pollutant.getVersionid());
//			pollutantService.save(pc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 污染物基础信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PollInfoEntity pollutant, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(pollutant.getId())) {
			pollutant = pollutantService.getEntity(PollInfoEntity.class, pollutant.getId());
		}
		req.setAttribute("pollutantPage", pollutant);
		return new ModelAndView("com/hippo/nky/standard/pollutant");
	}
}
