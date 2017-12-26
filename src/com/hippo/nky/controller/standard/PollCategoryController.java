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
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.PollCategoryEntity;
import com.hippo.nky.service.standard.PollCategoryServiceI;

/**   
 * @Title: Controller
 * @Description: 污染物分类
 * @author zhangdaihao
 * @date 2013-06-18 16:09:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pollCategoryController")
public class PollCategoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PollCategoryController.class);

	@Autowired
	private PollCategoryServiceI pollCategoryService;
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
	 * 污染物分类列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollCategory")
	public ModelAndView pollCategory(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/pollCategory");
	}
	
	/**
	 * 污染物分类树 页面跳转
	 * 
	 * @return
	 */	
	@RequestMapping(params = "pollCategoryTree")
	public ModelAndView pollCategoryTree(HttpServletRequest request) {
		// 版本编号
		String versionId = request.getParameter("versionId");
		request.setAttribute("versionId", versionId);
		
		String data = pollCategoryService.pollCategoryTreeData(versionId);
		request.setAttribute("zTreeData", data);
		return new ModelAndView("com/hippo/nky/standard/pollCategoryIframe");
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
	public void datagrid(PollCategoryEntity pollCategory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(PollCategoryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, pollCategory);
		this.pollCategoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除污染物分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PollCategoryEntity pollCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pollCategory = systemService.getEntity(PollCategoryEntity.class, pollCategory.getId());
		message = "删除成功";
		// 先删除污染物基础信息，再删除分类
		Integer count = pollCategoryService.delPollCategoryTreeNode(pollCategory.getId(), pollCategory.getVersionid());
		if(!(count!=null && count > 0)){
			message = "删除失败";
		}

		j.setMsg(message);
		return j;
	}


	/**
	 * 添加污染物分类
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PollCategoryEntity pollCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pollCategory.getId())) {
			message = "更新成功";
			PollCategoryEntity t = pollCategoryService.get(PollCategoryEntity.class, pollCategory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pollCategory, t);
				pollCategoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			pollCategory.setCode(ConverterUtil.getUUID());
			pollCategoryService.save(pollCategory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setObj(pollCategory);
		
		return j;
	}

	/**
	 * 污染物分类列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PollCategoryEntity pollCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(pollCategory.getId())) {
			pollCategory = pollCategoryService.getEntity(PollCategoryEntity.class, pollCategory.getId());
			pollCategory.setSaveDom(Constants.UPDATE);
		} else {
			String treeNodeId = req.getParameter("treeNodeId");
			String versionId = req.getParameter("versionId");
			if (StringUtil.isEmpty(treeNodeId)) {
				treeNodeId = Constants.ROOT_ID;
			}
			pollCategory.setPid(treeNodeId);
			pollCategory.setVersionid(versionId);
			pollCategory.setSaveDom(Constants.INSERT);
		}
		req.setAttribute("pollCategoryPage", pollCategory);
		return new ModelAndView("com/hippo/nky/standard/pollCategory");
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
		String constraint = "NAME" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param")
				+ ConverterUtil.SEPARATOR_ELEMENT + "VERSIONID"
				+ ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("versionid");
		j = systemService.uniquenessCheck("NKY_PO_CATEGORY", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("分类名称已存在!");
		}
		return j;
	}

}
