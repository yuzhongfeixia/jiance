package com.hippo.nky.controller.person123;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hippo.nky.entity.person123.XDLTESTEntity;
import com.hippo.nky.service.person123.XDLTESTEntityServiceI;

/**   
 * @Title: Controller
 * @Description: xudltest
 * @author zhangdaihao
 * @date 2013-06-24 15:07:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/xDLTESTEntityController")
public class XDLTESTEntityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(XDLTESTEntityController.class);

	@Autowired
	private XDLTESTEntityServiceI xDLTESTEntityService;
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
	 * xudltest列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "xDLTESTEntity")
	public ModelAndView xDLTESTEntity(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/person123/xDLTESTEntityList");
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
	public void datagrid(XDLTESTEntity xDLTESTEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(XDLTESTEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, xDLTESTEntity);
		this.xDLTESTEntityService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除xudltest
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(XDLTESTEntity xDLTESTEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		xDLTESTEntity = systemService.getEntity(XDLTESTEntity.class, xDLTESTEntity.getId());
		message = "删除成功";
		xDLTESTEntityService.delete(xDLTESTEntity);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加xudltest
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(XDLTESTEntity xDLTESTEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(xDLTESTEntity.getId())) {
			message = "更新成功";
			XDLTESTEntity t = xDLTESTEntityService.get(XDLTESTEntity.class, xDLTESTEntity.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(xDLTESTEntity, t);
				xDLTESTEntityService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			xDLTESTEntityService.save(xDLTESTEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * xudltest列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(XDLTESTEntity xDLTESTEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(xDLTESTEntity.getId())) {
			xDLTESTEntity = xDLTESTEntityService.getEntity(XDLTESTEntity.class, xDLTESTEntity.getId());
			req.setAttribute("xDLTESTEntityPage", xDLTESTEntity);
		}
		return new ModelAndView("com/hippo/nky/person123/xDLTESTEntity");
	}
	
	/**
	 * xudltest分页测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "splitPage")
	@ResponseBody
	public AjaxJson splitPage(HttpServletRequest request) {
		int recordsperpage = 10;
		AjaxJson j = new AjaxJson();
		String newPage = request.getParameter("page");
		int page = 0;
		if(newPage != null){
			page = Integer.parseInt(newPage.toString());
		}
		String sql = "select * from XDLTEST";
		List<Map<String, Object>> result = xDLTESTEntityService.findForJdbc(sql, page, recordsperpage);
		//---------------------------------------------------------------
//		CriteriaQuery cq = new CriteriaQuery(XDLTESTEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, xDLTESTEntity);
//		this.xDLTESTEntityService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("resultMap", result);
		j.setAttributes(attributes);
		return j;
	}
	
	/**0
	 * xudltest分页测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "splitOnLoad")
	public ModelAndView splitOnLoad(HttpServletRequest req) {
		int recordsperpage = 10;
		String sql = "select * from XDLTEST";
//		int allRecord = xDLTESTEntityService.findForJdbc(sql, objs)
		//List<Map<String, Object>> result = xDLTESTEntityService.findForJdbc(sql, null);
		List<XDLTESTEntity> result = systemService.findListByMyBatis("com.hippo.nky.entity.portal.FrontPortalIntroductionsEntity.searchData", null);
		req.setAttribute("totalrecords", result.size());
		req.setAttribute("recordsperpage", recordsperpage);
		return new ModelAndView("com/hippo/nky/person123/test");
	}
}
