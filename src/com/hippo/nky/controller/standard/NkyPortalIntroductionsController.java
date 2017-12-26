package com.hippo.nky.controller.standard;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.standard.NkyPortalIntroductionsEntity;
import com.hippo.nky.service.standard.NkyPortalIntroductionsServiceI;

/**
 * @Title: Controller
 * @Description: 栏目管理
 * @author zhangdaihao
 * @date 2013-07-29 11:43:31
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/nkyPortalIntroductionsController")
public class NkyPortalIntroductionsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NkyPortalIntroductionsController.class);

	@Autowired
	private NkyPortalIntroductionsServiceI nkyPortalIntroductionsService;
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
	 * 栏目列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "nkyPortalIntroductions")
	public ModelAndView nkyPortalIntroductions(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/introductionList");
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
	public void datagrid(NkyPortalIntroductionsEntity nkyPortalIntroductions, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, nkyPortalIntroductions);
		this.nkyPortalIntroductionsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "introductionsGrid")
	@ResponseBody
	public List<TreeGrid> introductionsGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class);
		if (treegrid.getId() != null) {
			cq.eq("pid", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("pid");
		}
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<NkyPortalIntroductionsEntity> introductionsList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		// treeGridModel.setIcon("TSIcon_iconPath");
		treeGridModel.setTextField("name");
		treeGridModel.setParentId("pid");
		// treeGridModel.setParentText("TSFunction_functionName");
		// treeGridModel.setParentId("TSFunction_id");
		treeGridModel.setSrc("createdate");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("NkyPortalIntroductionsEntitys");
		// // 添加排序字段
		//treeGridModel.setOrder("type");
		treeGrids = systemService.treegrid(introductionsList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 删除栏目
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NkyPortalIntroductionsEntity nkyPortalIntroductions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		nkyPortalIntroductions = systemService.getEntity(NkyPortalIntroductionsEntity.class,
				nkyPortalIntroductions.getId());
		List<NkyPortalIntroductionsEntity> list = systemService.findByProperty(NkyPortalIntroductionsEntity.class,
				"pid", nkyPortalIntroductions.getId());
		if (list.size() > 0) {
			message = "请先删除其子节点";
		} else {
			message = "删除成功";
			nkyPortalIntroductionsService.delete(nkyPortalIntroductions);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加栏目
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NkyPortalIntroductionsEntity nkyPortalIntroductions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(nkyPortalIntroductions.getId())) {
			message = "更新成功";
			NkyPortalIntroductionsEntity t = nkyPortalIntroductionsService.get(NkyPortalIntroductionsEntity.class,
					nkyPortalIntroductions.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(nkyPortalIntroductions, t);
				nkyPortalIntroductionsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if((nkyPortalIntroductions.getPid().equals("请选择父栏目！"))&&(nkyPortalIntroductions.getIntroductionleavel()!=0)){
				message = "请选择父栏目！";
			}else{
				message = "添加成功";
				nkyPortalIntroductionsService.save(nkyPortalIntroductions);
				if(!StringUtils.isEmpty(request.getParameter("sourcelist"))){
				if(nkyPortalIntroductions.getSourcelist()==1)
				nkyPortalIntroductions.setAssociatecondition(nkyPortalIntroductions.getId());
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NkyPortalIntroductionsEntity entity, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		List<NkyPortalIntroductionsEntity> entitylist = systemService.getList(NkyPortalIntroductionsEntity.class);
		req.setAttribute("flist", entitylist);
		if (functionid != null) {
			entity = systemService.getEntity(NkyPortalIntroductionsEntity.class, functionid);
			List<NkyPortalIntroductionsEntity> list = systemService.findByProperty(NkyPortalIntroductionsEntity.class,
					"id", entity.getPid());
			if (list.size() > 0) {
				NkyPortalIntroductionsEntity e = list.get(0);
				req.setAttribute("pName", e.getName());
			}
			req.setAttribute("function", entity);

		}
		return new ModelAndView("com/hippo/nky/standard/introduction");
	}

	/**
	 * 父级权限下拉菜单
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class);
		if (comboTree.getId() != null) {
			cq.eq("pid", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("pid");
		}
		cq.add();
		List<NkyPortalIntroductionsEntity> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "NkyPortalIntroductionsEntitys");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel, null);
		return comboTrees;
	}
	
	@RequestMapping(params = "getTreeJson")
	public void getTreeJson(HttpServletRequest request, HttpServletResponse response) {
		String introductionleavel = request.getParameter("introductionleavel");
		if(introductionleavel.equals("0")){
			nkyPortalIntroductionsService.getTreeSon(request, response , 0);
		}else if(introductionleavel.equals("1")){
			nkyPortalIntroductionsService.getTreeSon(request, response , 1);
		}else{
			nkyPortalIntroductionsService.getTreeJson(request, response);
		}
	}
	
	/*public String getNoHtmlContent(String content) {
		return content.replaceAll("<[^>]*>","");
	}*/

}
