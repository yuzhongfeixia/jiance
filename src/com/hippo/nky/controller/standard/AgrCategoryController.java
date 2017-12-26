package com.hippo.nky.controller.standard;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.AgrCategoryEntity;
import com.hippo.nky.service.standard.AgrCategoryServiceI;

/**   
 * @Title: Controller
 * @Description: 农产品分类表
 * @author zhangdaihao
 * @date 2013-06-18 11:03:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/agrCategoryController")
public class AgrCategoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AgrCategoryController.class);

	@Autowired
	private AgrCategoryServiceI agrCategoryService;
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
	 * 农产品分类 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "agrCategory")
	public ModelAndView agrCategory(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/agrCategory");
	}

	/**
	 * 农产品分类树结构 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "agrCategoryTree")
	public ModelAndView agrCategoryTree(HttpServletRequest request) {
		// 版本编号
		String versionId = request.getParameter("versionId");
		request.setAttribute("versionId", versionId);
		//农产品分类树
		String data = agrCategoryService.agrCategoryTreeData(versionId);
		request.setAttribute("zTreeData", data);
		return new ModelAndView("com/hippo/nky/standard/agrCategoryIframe");
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
	public void datagrid(AgrCategoryEntity agrCategory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = this.agrCategoryService.getSearchData(agrCategory, dataGrid,request);
		DataUtils.responseDatagrid(response, jObject);
	}

	/**
	 * 删除农产品分类表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(AgrCategoryEntity agrCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		agrCategory = systemService.getEntity(AgrCategoryEntity.class, agrCategory.getId());
		message = "删除成功";
		Integer count = agrCategoryService.delAgrCategoryTreeNode(agrCategory.getId());
		if(!(count!=null && count > 0)){
			message = "删除失败";
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加农产品分类表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(AgrCategoryEntity agrCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(agrCategory.getId())) {
			message = "更新成功";
			AgrCategoryEntity t = agrCategoryService.get(AgrCategoryEntity.class, agrCategory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(agrCategory, t);
				agrCategoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			// 如果是默认的暂无图片不做存储
			if(agrCategory.getImagepath().contains(Constants.IMAGE_PATH_NOIMAGE)){
				agrCategory.setImagepath("");
			}
			agrCategoryService.save(agrCategory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setObj(agrCategory);
		return j;
	}

	/**
	 * 农产品分类表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(AgrCategoryEntity agrCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(agrCategory.getId())) {
			agrCategory = agrCategoryService.getEntity(AgrCategoryEntity.class, agrCategory.getId());
			agrCategory.setSaveDom(Constants.UPDATE);
		} else {
			String treeNodeId = req.getParameter("treeNodeId");
			String versionId = req.getParameter("versionId");
			if (StringUtil.isEmpty(treeNodeId)) {
				treeNodeId = Constants.ROOT_ID;
			}
			agrCategory.setPid(treeNodeId);
			agrCategory.setVersionid(versionId);
			agrCategory.setSaveDom(Constants.INSERT);
			// 新规时如果缓存被清空时会发生图片加载不出来的问题，这里初始化一下
			agrCategory.setImagepath(ConverterUtil.getActionPath(req.getSession().getServletContext().getContextPath(), Constants.IMAGE_PATH_NOIMAGE));
		}
		req.setAttribute("agrCategoryPage", agrCategory);
		req.setAttribute("load", req.getParameter("load"));
		if(!"add".equals(req.getParameter("load"))){
			return new ModelAndView("com/hippo/nky/standard/agrCategoryInfomation");	
		}
		return new ModelAndView("com/hippo/nky/standard/agrCategory");
	}
	
	/**
	 * 添加农产品分类表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkAgrName")
	@ResponseBody
	public AjaxJson checkAgrName(HttpServletRequest request) {
		String constraint = "CODE" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param")
				+ ConverterUtil.SEPARATOR_ELEMENT + "VERSIONID"
				+ ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("versionid");
		AjaxJson j = agrCategoryService.uniquenessCheck("nky_agr_category", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("编号已存在!");
		}
		return j;
	}
	
	
	/**
	 * 农产品分类表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "anotherNameSet")
	public ModelAndView anotherNameSet(AgrCategoryEntity agrCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(agrCategory.getId())) {
			agrCategory = agrCategoryService.getEntity(AgrCategoryEntity.class, agrCategory.getId());
			if(ConverterUtil.isNotEmpty(agrCategory.getCalias())){
				String[] arr = agrCategory.getCalias().split(",");
				req.setAttribute("caliasArr", arr);	
				req.setAttribute("id", agrCategory.getId());
			}
			
		}
		return new ModelAndView("com/hippo/nky/standard/agrCategoryAnotherNameSet");
	}
	

}
