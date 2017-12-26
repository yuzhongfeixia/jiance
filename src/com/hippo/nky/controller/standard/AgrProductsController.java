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

import com.hippo.nky.entity.standard.AgrProductsEntity;
import com.hippo.nky.service.standard.AgrProductsServiceI;

/**   
 * @Title: Controller
 * @Description: 农产品基础信息
 * @author zhangdaihao
 * @date 2013-07-02 18:11:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/agrProductsController")
public class AgrProductsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AgrProductsController.class);

	@Autowired
	private AgrProductsServiceI agrProductsService;
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
	 * 农产品基础信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "agrProducts")
	public ModelAndView agrProducts(HttpServletRequest request) {
		String versionid = request.getParameter("versionid");
		String categoryid = request.getParameter("categoryid");
		request.setAttribute("versionid", versionid);
		request.setAttribute("categoryid", categoryid);
		return new ModelAndView("com/hippo/nky/standard/agrProductsList");
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
	public void datagrid(AgrProductsEntity agrProducts,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(AgrProductsEntity.class, dataGrid);
		// 增加删选条件
		cq.eq("versionid", agrProducts.getVersionid());
		cq.eq("categoryid", agrProducts.getCategoryid());
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, agrProducts);
		this.agrProductsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除农产品基础信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(AgrProductsEntity agrProducts, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		agrProducts = systemService.getEntity(AgrProductsEntity.class, agrProducts.getId());
		message = "删除成功";
		agrProductsService.delete(agrProducts);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加农产品基础信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(AgrProductsEntity agrProducts, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(agrProducts.getId())) {
			message = "更新成功";
			AgrProductsEntity t = agrProductsService.get(AgrProductsEntity.class, agrProducts.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(agrProducts, t);
				agrProductsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			agrProductsService.save(agrProducts);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 农产品基础信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(AgrProductsEntity agrProducts, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(agrProducts.getId())) {
			agrProducts = agrProductsService.getEntity(AgrProductsEntity.class, agrProducts.getId());
			req.setAttribute("agrProductsPage", agrProducts);
		}else{
			AgrProductsEntity newAgrProducts = new AgrProductsEntity();
			newAgrProducts.setCategoryid(agrProducts.getCategoryid());
			newAgrProducts.setVersionid(agrProducts.getVersionid());
			req.setAttribute("agrProductsPage", newAgrProducts);
		}
		return new ModelAndView("com/hippo/nky/standard/agrProducts");
	}

}
