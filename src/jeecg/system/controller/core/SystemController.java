package jeecg.system.controller.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSDepart;
import jeecg.system.pojo.base.TSDocument;
import jeecg.system.pojo.base.TSFunction;
import jeecg.system.pojo.base.TSOnline;
import jeecg.system.pojo.base.TSRole;
import jeecg.system.pojo.base.TSRoleFunction;
import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.pojo.base.TSVersion;
import jeecg.system.service.SystemService;
import jeecg.system.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.ScaledImage;
import org.jeecgframework.core.util.SetListSort;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.WordUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.standard.PollCategoryEntity;
import com.hippo.nky.entity.standard.PollProductsEntity;

/**
 * 类型字段处理类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/systemController")
public class SystemController extends BaseController {
	private static final Logger logger = Logger.getLogger(SystemController.class);
	private UserService userService;
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(params = "druid")
	public ModelAndView druid() {
		return new ModelAndView(new RedirectView("druid/index.html"));
	}
	/**
	 * 类型字典列表页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "typeGroupTabs")
//	public ModelAndView typeGroupTabs(HttpServletRequest request) {
//		List<TSTypegroup> typegroupList = systemService.loadAll(TSTypegroup.class);
//		request.setAttribute("typegroupList", typegroupList);
//		return new ModelAndView("system/type/typeGroupTabs");
//	}

	
	


	/**
	 * 类型分组列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "typeGroupList")
	public ModelAndView typeGroupList(HttpServletRequest request) {

		//request.setAttribute("typeGupList", typeGridTree(request));
		return new ModelAndView("system/type/typeGroupList");
	}

	/**
	 * 区域列表，树形展示
	 * @param request
	 * @param treegrid
	 * @return
	 */
	@RequestMapping(params = "typeGridTree")
	@ResponseBody
	public AjaxJson typeGridTree(HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		attributesMap.put("typeGupList", getTypeGridTree());
		j.setAttributes(attributesMap);

		return j;
	}
	

	public List<TreeGrid> getTypeGridTree() {
		CriteriaQuery cq;
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();

		int intTreeId = 1;
		TreeGrid treeNode = null;
		cq = new CriteriaQuery(TSTypegroup.class);
		List<TSTypegroup> typeGroupList = systemService.getListByCriteriaQuery(cq, false);
		for (TSTypegroup obj : typeGroupList) {
			treeNode = new TreeGrid();
			treeNode.setId("G"+obj.getId());
			treeNode.setText(obj.getTypegroupname());
			treeNode.setCode(obj.getTypegroupcode());
			treeNode.setState("closed");
			treeNode.setTreeId(String.valueOf(intTreeId));
			treeGrids.add(treeNode);
			TSTypegroup tSTypegroup = new TSTypegroup();
			tSTypegroup.setId(obj.getId());
			List<TSType> types = systemService.findByProperty(TSType.class,"TSTypegroup", tSTypegroup);
			int i = 1;
			for (TSType type : types) {
				treeNode = new TreeGrid();
				treeNode.setId("Z"+type.getId());
				treeNode.setText(type.getTypename());
				treeNode.setCode(type.getTypecode());
				treeNode.setState("closed");
				treeNode.setParentId(String.valueOf(intTreeId));
				treeNode.setTreeId(String.valueOf(intTreeId) + "." + String.valueOf(i));
				treeNode.setTypegroupid(obj.getId());
				treeGrids.add(treeNode);
				i++;
			}
			intTreeId++;
		}
		//}

		return treeGrids;
	}

	/**
	 * 类型列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView typeList(HttpServletRequest request, String typegroupid) {
		TSTypegroup typegroup = systemService.getEntity(TSTypegroup.class, typegroupid);
		request.setAttribute("typegroup", typegroup);
		return new ModelAndView("system/type/typeList");
	}

	/**
	 * easyuiAJAX请求数据
	 */

	@RequestMapping(params = "typeGroupGrid")
	public void typeGroupGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSTypegroup.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "typeGrid")
	public void typeGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typegroupid = request.getParameter("typegroupid");
		String typename = request.getParameter("typename");
		CriteriaQuery cq = new CriteriaQuery(TSType.class, dataGrid);
		cq.eq("TSTypegroup.id", typegroupid);
		cq.like("typename", typename);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
//	@RequestMapping(params = "typeGroupTree")
//	@ResponseBody
//	public List<ComboTree> typeGroupTree(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TSTypegroup.class);
//		List<TSTypegroup> typeGroupList = systemService.getListByCriteriaQuery(cq, false);
//		List<ComboTree> trees = new ArrayList<ComboTree>();
//		for (TSTypegroup obj : typeGroupList) {
//			ComboTree tree = new ComboTree();
//			tree.setId(obj.getId());
//			tree.setText(obj.getTypegroupname());
//			List<TSType> types = obj.getTSTypes();
//			if (types != null) {
//				if (types.size() > 0) {
//					//tree.setState("closed");
//					List<ComboTree> children = new ArrayList<ComboTree>();
//					for (TSType type : types) {
//						ComboTree tree2 = new ComboTree();
//						tree2.setId(type.getId());
//						tree2.setText(type.getTypename());
//						children.add(tree2);
//					}
//					tree.setChildren(children);
//				}
//			}
//			//tree.setChecked(false);
//			trees.add(tree);
//		}
//		return trees;
//	}
	
	
	

//	@RequestMapping(params = "typeGridTree")
//	@ResponseBody
//	public List<TreeGrid> typeGridTree(HttpServletRequest request, TreeGrid treegrid) {
//		CriteriaQuery cq;
//		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
//		if (treegrid.getId() != null) {
//			cq = new CriteriaQuery(TSType.class);
//			cq.eq("TSTypegroup.id", treegrid.getId().substring(1));
//			cq.add();
//			List<TSType> typeList = systemService.getListByCriteriaQuery(cq, false);
//			for (TSType obj : typeList) {
//				TreeGrid treeNode = new TreeGrid();
//				treeNode.setId("T"+obj.getId());
//				treeNode.setText(obj.getTypename());
//				treeNode.setCode(obj.getTypecode());
//				treeGrids.add(treeNode);
//			}
//		} else {
//			cq = new CriteriaQuery(TSTypegroup.class);
//			List<TSTypegroup> typeGroupList = systemService.getListByCriteriaQuery(cq, false);
//			for (TSTypegroup obj : typeGroupList) {
//				TreeGrid treeNode = new TreeGrid();
//				treeNode.setId("G"+obj.getId());
//				treeNode.setText(obj.getTypegroupname());
//				treeNode.setCode(obj.getTypegroupcode());
//				treeNode.setState("closed");
//				treeGrids.add(treeNode);
//			}
//		}
//		return treeGrids;
//	}
	/**
	 * 删除类型分组或者类型（ID以G开头的是分组）
	 * 
	 * @return
	 */
	@RequestMapping(params = "delTypeGridTree")
	@ResponseBody
	public AjaxJson delTypeGridTree(String id, HttpServletRequest request) {
		String delId = request.getParameter("id");
		AjaxJson j = new AjaxJson();
		if (delId.startsWith("G")) {//分组
			TSTypegroup typegroup = systemService.getEntity(TSTypegroup.class, delId.substring(1));
			message = "数据字典分组: " + typegroup.getTypegroupname() + "被删除 成功";
			systemService.delete(typegroup);
		} else {
			TSType type = systemService.getEntity(TSType.class, delId.substring(1));
			message = "数据字典类型: " + type.getTypename() + "被删除 成功";
			systemService.delete(type);
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		//刷新缓存
		systemService.refleshTypeGroupCach();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("expendId", request.getParameter("pid"));
		j.setAttributes(attributesMap);
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型分组
	 * 
	 * @return
	 */
	@RequestMapping(params = "delTypeGroup")
	@ResponseBody
	public AjaxJson delTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		typegroup = systemService.getEntity(TSTypegroup.class, typegroup.getId());
		message = "类型分组: " + typegroup.getTypegroupname() + "被删除 成功";
		systemService.delete(typegroup);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(TSType type, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		type = systemService.getEntity(TSType.class, type.getId());
		message = "类型: " + type.getTypename() + "被删除 成功";
		systemService.delete(type);
		//刷新缓存
		systemService.refleshTypesCach(type);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 检查分组代码
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkTypeGroup")
	@ResponseBody
	public ValidForm checkTypeGroup(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String typegroupcode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<TSTypegroup> typegroups=systemService.findByProperty(TSTypegroup.class,"typegroupcode",typegroupcode);
		if(typegroups.size()>0&&!code.equals(typegroupcode))
		{
			v.setInfo("分组已存在");
			v.setStatus("n");
		}
		return v;
	}

	
	/**
	 * 添加类型分组
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveTypeGroup")
	@ResponseBody
	public AjaxJson saveTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(typegroup.getId())) {
			message = "类型分组: " + typegroup.getTypegroupname() + "被更新成功";
			userService.saveOrUpdate(typegroup);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "类型分组: " + typegroup.getTypegroupname() + "被添加成功";
			userService.save(typegroup);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
//		//刷新缓存
//		systemService.refleshTypeGroupCach();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("expendId", "");
		j.setAttributes(attributesMap);
		j.setMsg(message);
		return j;
	}
	/**
	 * 检查类型代码
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkType")
	@ResponseBody
	public ValidForm checkType(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String typecode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		String typeGroupCode=oConvertUtils.getString(request.getParameter("typeGroupCode"));
		StringBuilder hql = new StringBuilder("FROM ").append(TSType.class.getName()).append(" AS entity WHERE 1=1 ");
		hql.append(" AND entity.TSTypegroup.typegroupcode =  '").append(typeGroupCode).append("'");
		hql.append(" AND entity.typecode =  '").append(typecode).append("'");
		List<Object> types = this.systemService.findByQueryString(hql.toString());
		if(types.size()>0&&!code.equals(typecode))
		{
			v.setInfo("类型已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 添加类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveType")
	@ResponseBody
	public AjaxJson saveType(TSType type, HttpServletRequest request) {
		TSTypegroup tmpTypegroup = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(type.getId())) {
			String typegroupid = request.getParameter("typegroupid");
			tmpTypegroup = systemService.getEntity(TSTypegroup.class, typegroupid);
			type.setTSTypegroup(tmpTypegroup);
			message = "类型: " + type.getTypename() + "被更新成功";
			userService.saveOrUpdate(type);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			String typegroupid = type.getTSTypegroup().getId().substring(1);
			type.getTSTypegroup().setId(typegroupid);
			message = "类型: " + type.getTypename() + "被添加成功";
			userService.save(type);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		//刷新缓存
		systemService.refleshTypesCach(type);
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("expendId", request.getParameter("pid"));
		j.setAttributes(attributesMap);
		j.setMsg(message);
		return j;
	}

	

	/**
	 * 类型分组列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "aouTypeGroup")
	public ModelAndView aouTypeGroup(TSTypegroup typegroup, HttpServletRequest req) {
		String id = typegroup.getId();
		if (id != null) {
			if (id.startsWith("G")) {
				typegroup = systemService.getEntity(TSTypegroup.class, typegroup.getId().substring(1));
			} else if (id.startsWith("Z")) {
				TSType tsType = systemService.getEntity(TSType.class, typegroup.getId().substring(1));
				typegroup.setTypegroupcode(tsType.getTypecode());
				typegroup.setTypegroupname(tsType.getTypename());
			}
			
			req.setAttribute("typegroup", typegroup);
		}
		return new ModelAndView("system/type/typegroup");
	}

	/**
	 * 类型列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateType")
	public ModelAndView addorupdateType(TSType type, HttpServletRequest req) {
		String typegroupid = req.getParameter("typegroupid");
		req.setAttribute("typegroupid", typegroupid);
		if (StringUtil.isNotEmpty(type.getId())) {
			type = systemService.getEntity(TSType.class, type.getId().substring(1));
			req.setAttribute("type", type);
		} 
		req.setAttribute("pid", req.getParameter("pid"));
		return new ModelAndView("system/type/type");
	}
	
	/**
	 * 字典组重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isTypeGroupExsitCheck")
	@ResponseBody
	public AjaxJson isTypeGroupExsitCheck(HttpServletRequest request) {
		AjaxJson j = null;
		String checkFlg = request.getParameter("checkFlg");
		if (StringUtils.equals(checkFlg, "1")) {
			String constraint = "TYPEGROUPNAME" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param");
			j = systemService.uniquenessCheck("T_S_TYPEGROUP", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("字典名称已存在!");
			}

		} 
		if (StringUtils.equals(checkFlg, "2")) {
			String constraint = "TYPEGROUPCODE" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param");
			j = systemService.uniquenessCheck("T_S_TYPEGROUP", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("字典编码已存在!");
			}
		}
		return j;

	}
	
	/**
	 * 字典分类重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isTypeExsitCheck")
	@ResponseBody
	public AjaxJson isTypeExsitCheck(HttpServletRequest request) {
		AjaxJson j = null;
		String checkFlg = request.getParameter("checkFlg");
		if (StringUtils.equals(checkFlg, "1")) {
			String constraint = "TYPENAME" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param") + ConverterUtil.SEPARATOR_ELEMENT
					+ "TYPEGROUPID" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("typegroupid");
			j = systemService.uniquenessCheck("T_S_TYPE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("参数名称已存在!");
			}

		} 
		if (StringUtils.equals(checkFlg, "2")) {
			String constraint = "TYPECODE" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param") + ConverterUtil.SEPARATOR_ELEMENT
					+ "TYPEGROUPID" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("typegroupid");
			j = systemService.uniquenessCheck("T_S_TYPE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("参数值已存在!");
			}
		}
		return j;
	}

	/*
	 * *****************部门管理操作****************************
	 */

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	public ModelAndView depart() {
		return new ModelAndView("system/depart/departList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除部门
	 * 
	 * @return
	 */
	@RequestMapping(params = "delDepart")
	@ResponseBody
	public AjaxJson delDepart(TSDepart depart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		depart = systemService.getEntity(TSDepart.class, depart.getId());
		message = "部门: " + depart.getDepartname() + "被删除 成功";
		systemService.delete(depart);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * 添加部门
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveDepart")
	@ResponseBody
	public AjaxJson saveDepart(TSDepart depart, HttpServletRequest request) {
		// 设置上级部门
		String pid = request.getParameter("TSPDepart.id");
		if (pid.equals("")) {
			depart.setTSPDepart(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(depart.getId())) {
			message = "部门: " + depart.getDepartname() + "被更新成功";
			userService.saveOrUpdate(depart);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "部门: " + depart.getDepartname() + "被添加成功";
			userService.save(depart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateDepart")
	public ModelAndView addorupdateDepart(TSDepart depart, HttpServletRequest req) {
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
		if (depart.getId() != null) {
			depart = systemService.getEntity(TSDepart.class, depart.getId());
			req.setAttribute("depart", depart);
		}
		return new ModelAndView("system/depart/depart");
	}

	/**
	 * 父级权限列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if (StringUtil.isNotEmpty(comboTree.getId())) {
			cq.eq("TSPDepart.id", comboTree.getId());
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		if (StringUtil.isEmpty(comboTree.getId())) {
			cq.isNull("TSPDepart.id");
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		cq.add();
		List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees = systemService.comTree(departsList, comboTree);
		return comboTrees;

	}

	/*
	 * *****************角色管理操作****************************
	 */
	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public ModelAndView role() {
		return new ModelAndView("system/role/roleList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridRole")
	public void datagridRole(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(TSRole role, String ids, HttpServletRequest request) {
		message = "角色: " + role.getRoleName() + "被删除成功";
		AjaxJson j = new AjaxJson();
		role = systemService.getEntity(TSRole.class, role.getId());
		userService.delete(role);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 角色录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveRole")
	@ResponseBody
	public AjaxJson saveRole(TSRole role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (role.getId() != null) {
			message = "角色: " + role.getRoleName() + "被更新成功";
			userService.saveOrUpdate(role);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "角色: " + role.getRoleName() + "被添加成功";
			userService.saveOrUpdate(role);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		request.setAttribute("roleid", roleid);
		return new ModelAndView("system/role/roleList");
	}

	/**
	 * 设置权限
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setAuthority")
	@ResponseBody
	public List<ComboTree> setAuthority(TSRole role, HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (comboTree.getId() != null) {
			cq.eq("TFunction.functionid", oConvertUtils.getInt(comboTree.getId(), 0));
		}
		if (comboTree.getId() == null) {
			cq.isNull("TFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		List<TSFunction> loginActionlist = new ArrayList<TSFunction>();// 已有权限菜单
		role = this.systemService.get(TSRole.class, roleid);
		if (role != null) {
			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionName", "TSFunctions");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel, loginActionlist);
		return comboTrees;
	}

	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	public String updateAuthority(HttpServletRequest request) {
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		String rolefunction = request.getParameter("rolefunctions");
		TSRole role = this.systemService.get(TSRole.class, roleid);
		List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
		systemService.deleteAllEntitie(roleFunctionList);
		String[] roleFunctions = null;
		if (rolefunction != "") {
			roleFunctions = rolefunction.split(",");
			for (String s : roleFunctions) {
				TSRoleFunction rf = new TSRoleFunction();
				TSFunction f = this.systemService.get(TSFunction.class, Integer.valueOf(s));
				rf.setTSFunction(f);
				rf.setTSRole(role);
				this.systemService.save(rf);
			}
		}
		return "system/role/roleList";
	}

	/**
	 * 角色页面跳转
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdateRole")
	public ModelAndView addorupdateRole(TSRole role, HttpServletRequest req) {
		if (role.getId() != null) {
			role = systemService.getEntity(TSRole.class, role.getId());
			req.setAttribute("role", role);
		}
		return new ModelAndView("system/role/role");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operate")
	public ModelAndView operate(HttpServletRequest request) {
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		return new ModelAndView("system/role/functionList");
	}

	/**
	 * 权限操作列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setOperate")
	@ResponseBody
	public List<TreeGrid> setOperate(HttpServletRequest request, TreeGrid treegrid) {
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (treegrid.getId() != null) {
			cq.eq("TFunction.functionid", oConvertUtils.getInt(treegrid.getId(), 0));
		}
		if (treegrid.getId() == null) {
			cq.isNull("TFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		Collections.sort(functionList, new SetListSort());
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setRoleid(roleid);
		treeGrids = systemService.treegrid(functionList, treeGridModel);
		return treeGrids;

	}

	/**
	 * 操作录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveOperate")
	@ResponseBody
	public AjaxJson saveOperate(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String fop = request.getParameter("fp");
		String roleid = request.getParameter("roleid");
		// 录入操作前清空上一次的操作数据
		clearp(roleid);
		String[] fun_op = fop.split(",");
		String aa = "";
		String bb = "";
		// 只有一个被选中
		if (fun_op.length == 1) {
			bb = fun_op[0].split("_")[1];
			aa = fun_op[0].split("_")[0];
			savep(roleid, bb, aa);
		} else {
			// 至少2个被选中
			for (int i = 0; i < fun_op.length; i++) {
				String cc = fun_op[i].split("_")[0]; // 操作id
				if (i > 0 && bb.equals(fun_op[i].split("_")[1])) {
					aa += "," + cc;
					if (i == (fun_op.length - 1)) {
						savep(roleid, bb, aa);
					}
				} else if (i > 0) {
					savep(roleid, bb, aa);
					aa = fun_op[i].split("_")[0]; // 操作ID
					if (i == (fun_op.length - 1)) {
						bb = fun_op[i].split("_")[1]; // 权限id
						savep(roleid, bb, aa);
					}

				} else {
					aa = fun_op[i].split("_")[0]; // 操作ID
				}
				bb = fun_op[i].split("_")[1]; // 权限id

			}
		}

		return j;
	}

	/**
	 * 更新操作
	 * 
	 * @param roleid
	 * @param functionid
	 * @param ids
	 */
	public void savep(String roleid, String functionid, String ids) {
		String hql = "from TRoleFunction t where" + " t.TSRole.id=" + roleid + " " + "and t.TFunction.functionid=" + functionid;
		TSRoleFunction rFunction = systemService.singleResult(hql);
		if (rFunction != null) {
			rFunction.setOperation(ids);
			systemService.saveOrUpdate(rFunction);
		}
	}

	/**
	 * 清空操作
	 * 
	 * @param roleid
	 */
	public void clearp(String roleid) {
		String hql = "from TRoleFunction t where" + " t.TSRole.id=" + roleid;
		List<TSRoleFunction> rFunctions = systemService.findByQueryString(hql);
		if (rFunctions.size() > 0) {
			for (TSRoleFunction tRoleFunction : rFunctions) {
				tRoleFunction.setOperation(null);
				systemService.saveOrUpdate(tRoleFunction);
			}
		}
	}

	/************************************** 版本维护 ************************************/

	/**
	 * 版本维护列表
	 */
	@RequestMapping(params = "versionList")
	public void versionList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSVersion.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除版本
	 */

	@RequestMapping(params = "delVersion")
	@ResponseBody
	public AjaxJson delVersion(TSVersion version, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		version = systemService.getEntity(TSVersion.class, version.getId());
		message = "版本：" + version.getVersionName() + "被删除 成功";
		systemService.delete(version);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * 版本添加跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addversion")
	public ModelAndView addversion(HttpServletRequest req) {
		return new ModelAndView("system/version/version");
	}

	/**
	 * 保存版本
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "saveVersion", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveVersion(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		TSVersion version = new TSVersion();
		String versionName = request.getParameter("versionName");
		String versionCode = request.getParameter("versionCode");
		version.setVersionCode(versionCode);
		version.setVersionName(versionName);
		systemService.save(version);
		j.setMsg("版本保存成功");
		return j;
	}
	//add-begin--Author:lihuan  Date:20130514 for：从V3版本迁移上传下载代码

	/**
	 * 新闻法规文件列表
	 */
	@RequestMapping(params = "documentList")
	public void documentList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDocument.class, dataGrid);
		String typecode = oConvertUtils.getString(request.getParameter("typecode"));
		cq.createAlias("TSType", "TSType");
		cq.eq("TSType.typecode", typecode);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除文档
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delDocument")
	@ResponseBody
	public AjaxJson delDocument(TSDocument document, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		document = systemService.getEntity(TSDocument.class, document.getId());
		message = "" + document.getDocumentTitle() + "被删除成功";
		userService.delete(document);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 文件添加跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addFiles")
	public ModelAndView addFiles(HttpServletRequest req) {
		return new ModelAndView("system/document/files");
	}
	
	/**
	 * 保存文件
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response, TSDocument document) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		TSTypegroup tsTypegroup=systemService.getTypeGroup("fieltype","文档分类");
		TSType tsType = systemService.getType("files","附件", tsTypegroup);
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));// 文件标题
		if (StringUtil.isNotEmpty(fileKey)) {
			document.setId(fileKey);
			document = systemService.getEntity(TSDocument.class, fileKey);
			document.setDocumentTitle(documentTitle);

		}
		document.setSubclassname(MyClassLoader.getPackPath(document));
		document.setCreatedate(DataUtils.gettimestamp());
		document.setTSType(tsType);
		UploadFile uploadFile = new UploadFile(request, document);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		document = systemService.uploadFile(uploadFile);
		attributes.put("url", document.getRealpath());
		attributes.put("fileKey", document.getId());
		attributes.put("name", document.getAttachmenttitle());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + document.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
		j.setMsg("文件添加成功");
		j.setAttributes(attributes);

		return j;
	}

	//add-end--Author:lihuan  Date:20130514 for：从V3版本迁移上传下载代码
	
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	
	/**
	 * 在线用户列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param tSOline
	 */

	@RequestMapping(params = "datagridOnline")
	public void datagrid(TSOnline tSOnline,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSOnline.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSOnline);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	
	/**
	 * 打开附件上传窗口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "callUpload")
	public ModelAndView callUpload(HttpServletRequest request) {
		Map<String, Object> uploadAttributes = new HashMap<String, Object>();
		// 取得版本ID
		String versionId = request.getParameter("versionId");
		if (StringUtil.isEmpty(versionId)) {
			versionId = "";
		}
		request.setAttribute("versionId", versionId);
		// 取得版本ID
		String categoryId = request.getParameter("categoryId");
		if (StringUtil.isEmpty(categoryId)) {
			categoryId = "";
		}
		request.setAttribute("categoryId", categoryId);
		// 取得附件的类型
		String type = request.getParameter("type");
		if (Constants.UPLOAD_TYPE_IMAGE.equals(type)) {
			// 设置附件的类型为images
			uploadAttributes.put("attachmentType", Constants.UPLOAD_TYPE_IMAGE);
			// 设置title
			uploadAttributes.put("title", Constants.UPLOAD_TITLE_IMAGE);
		} else if (Constants.UPLOAD_TYPE_FILE.equals(type)) {
			// 设置附件的类型为files
			uploadAttributes.put("attachmentType", Constants.UPLOAD_TYPE_FILE);
			// 设置title
			uploadAttributes.put("title", Constants.UPLOAD_TITLE_FILE);
		}

		// 取得是否为自动上传
		String autoUpload = request.getParameter("auto");
		if (StringUtil.isEmpty(autoUpload)) {
			// 默认为自动上传
			autoUpload = "true";
		}
		uploadAttributes.put("auto", autoUpload);
		
		// 取得是否支持多文件
		String multi = request.getParameter("multi");
		if (StringUtil.isEmpty(multi)) {
			multi = "false";
		}
		uploadAttributes.put("multi", multi);
		
		// 取得上传文件类型
		String fileType = request.getParameter("fileType");
		if (StringUtil.isEmpty(fileType)) {
			// 没有文件类型时默认office
			fileType = "office";
		}
		uploadAttributes.put("fileType", fileType);
		
		// 取得是否删除队列
		String removeCompleted = request.getParameter("removeCompleted");
		if (StringUtil.isEmpty(removeCompleted)) {
			// 没有文件类型时默认office
			removeCompleted = "true";
		}
		uploadAttributes.put("removeCompleted", removeCompleted);
		
		// 取得是否是导入
		String isImport = request.getParameter("isImport");
		if (StringUtil.isEmpty(isImport)) {
			// 没有默认false
			isImport = "false";
		}
		uploadAttributes.put("isImport", isImport);
		
		// 取得是否重命名
		String rename = request.getParameter("rename");
		if (StringUtil.isEmpty(rename)) {
			// 默认true
			rename = "true";
		}
		uploadAttributes.put("rename", rename);
		
		// 取得默认的导入类型
		String importType = request.getParameter("importType");
		// 设置了导入类型，下拉框不可选
		if (StringUtil.isNotEmpty(importType) && !"undefined".equals(importType)) {
			uploadAttributes.put("disabled", true);
		}
		uploadAttributes.put("importType", importType);
		
		// 取得配置文件中上传图片的默认、最大、最小尺寸
		List<String> defaultSize = ConverterUtil.getSplitProperties(ResourceUtil.getConfigByName(Constants.PROPEERTIS_UPLOADIMAGEDEFAULTSIZE));
		defaultSize = ConverterUtil.complementSize(defaultSize, 2, ConverterUtil.COMPLEMENT_SAME);
		List<String> maxSize = ConverterUtil.getSplitProperties(ResourceUtil.getConfigByName(Constants.PROPEERTIS_UPLOADIMAGEMAXSIZE));
		maxSize = ConverterUtil.complementSize(maxSize, 2, ConverterUtil.COMPLEMENT_SAME);
		List<String> minSize = ConverterUtil.getSplitProperties(ResourceUtil.getConfigByName(Constants.PROPEERTIS_UPLOADIMAGEMINSIZE));
		minSize = ConverterUtil.complementSize(minSize, 2, ConverterUtil.COMPLEMENT_SAME);
		
		// 设置初始值
		uploadAttributes.put("defaultW", defaultSize.get(0));
		uploadAttributes.put("defaultH", defaultSize.get(1));
		uploadAttributes.put("maxW", maxSize.get(0));
		uploadAttributes.put("maxH", maxSize.get(1));
		uploadAttributes.put("minW", minSize.get(0));
		uploadAttributes.put("minH", minSize.get(1));
		
		// 取得回调函数
		String callback = request.getParameter("callback");
		uploadAttributes.put("callback", callback);

		// 设置上传标签属性
		request.setAttribute("uploadAttributes", uploadAttributes);

		return new ModelAndView("com/hippo/nky/standard/commonUpload");
	}

	/**
	 * 保存附件
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveAttachment", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveAttachment(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		UploadFile uploadFile = new UploadFile(request, attributes);
		// 判断是否需要重命名
		if(!ConverterUtil.toBoolean(request.getParameter("rename"))){
			uploadFile.setRename(false);
		}
		// 取得附件的类型
		String type = request.getParameter("type");

		// 如果type设置则按照图片和文件进行分类，如果没有则默认
			// 设置图片的保存目录为images
		if (Constants.UPLOAD_TYPE_IMAGE.equals(type)) {
			uploadFile.setCusPath(Constants.UPLOAD_PATH_IMAGES);
			attributes = systemService.uploadFile(uploadFile, Constants.UPLOAD_TYPE_IMAGE);
		} else if (Constants.UPLOAD_TYPE_FILE.equals(type)) {
			// 设置文件的保存目录为files
			uploadFile.setCusPath(Constants.UPLOAD_PATH_FILES);
			attributes = systemService.uploadFile(uploadFile, Constants.UPLOAD_TYPE_FILE);
		}

		j.setAttributes(attributes);
		return j;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveUpload")
	@ResponseBody
	public AjaxJson saveUpload(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		// 取得附件的类型
		String type = request.getParameter("type");
		// 取得文件路径
		String attachmentPath = request.getParameter("attachmentPath");
		if(StringUtil.isEmpty(attachmentPath)){
			j.setSuccess(false);
			j.setMsg("没有选择任何附件");
			return j;
		}
		// 如果type设置则按照图片和文件进行分类，如果没有则默认
		if (Constants.UPLOAD_TYPE_IMAGE.equals(type)) {
			int x = ConverterUtil.toInteger(request.getParameter("txtX1"), 0);
			int y = ConverterUtil.toInteger(request.getParameter("txtY1"), 0);
			int w = ConverterUtil.toInteger(request.getParameter("txtW"), 0);
			int h = ConverterUtil.toInteger(request.getParameter("txtH"), 0);
			attributes = ScaledImage.cutImage(attachmentPath, x, y, w, h);
		} else {
			attributes = FileUtils.getFileAttribute(attachmentPath);
		}
		j.setSuccess(true);
		j.setAttributes(attributes);
		return j;
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportExcel")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		// 取得导出文件名
		String exportFileName = request.getParameter("exportFileName");
		// 取得导出SQL的ID
		String sqlId = request.getParameter("sqlId");
		// 取得自定义取得方法
		String customService = request.getParameter("customService");
		// 如果SQL是空且自定义是空，则返回
		if(ConverterUtil.isEmpty(sqlId) && ConverterUtil.isEmpty(customService)){
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.print("<script language='javascript'>alert('没有找到导出ID或方法，导出失败!');window.close();</script>");
			return;
		}
		if(ConverterUtil.isEmpty(exportFileName)){
			// 如果没有指定导出的文件名 则按照日期+随机8位码生成文件名
			exportFileName = DataUtils.getDataString(DataUtils.yyyymmddhhmmss) + StringUtil.random(8);
		}
		Map<String, Object> custMap = new HashMap<String, Object>();
		
		OutputStream fOut = null;
		ZipOutputStream zipOut = null;
		try {
			String projectCodeList =  ConverterUtil.toString(request.getParameterMap().get("projectCode"));
			String projectCode = null;
			if(projectCodeList != null){
				if(projectCodeList.indexOf(",") == -1){
					projectCode = projectCodeList;
				}else{
					String projectCodes[] = projectCodeList.split(",");
					projectCode = projectCodes[0];
				}	
			}
			
			// 如果含有projectCode则需要加权限条件
			if(ConverterUtil.isNotEmpty(projectCode)){
				systemService.setUserDataPriv(projectCode, custMap);
			}
			OrganizationEntity org = systemService.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
			if(org != null){
				custMap.put("orgCode", org.getCode());
			}
			custMap.putAll(ConverterUtil.requestParamsToMap(request.getParameterMap()));
			// 产生导出对象
			List<Object> exportList = new ArrayList<Object>(); 
			if (ConverterUtil.isNotEmpty(customService)) {
				// 从自定义service取得数据
				exportList = ConverterUtil.getDataFormCustomService(customService, custMap);
			} else {
				// 用共通的service取得数据
				exportList = systemService.findExportList(sqlId, custMap);
			}
			if (exportList.size() <= 1) {
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.print("<script language='javascript'>alert('没有符合项件的数据!');window.close();</script>");
				return;
			}
			
			// 转换成workbook对象
			List<Workbook> workbookList = ConverterUtil.objectToWorkBook(exportFileName, exportList);
			
			// response类型
			String responseContentType = "";
			// response头
			String responseHeader = "";
			
			if(workbookList.size() <= 1){
				// 如果返回一个book就返回vnd.ms-excel类型
				responseContentType = "application/vnd.ms-excel";
			} else {
				// 如果返回多个book就返回压缩包类型
				responseContentType = "application/zip;charset=UTF-8";
			}
			
			// 取得浏览器类型
			String browse = BrowserUtils.checkBrowse(request);
			// 判断是否是IE浏览器
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
				if(1 == workbookList.size()){
					responseHeader = exportFileName + ".xls";
				} else {
					responseHeader = exportFileName + ".zip";
				}
			} else {
				// 非IE内核的浏览器如果直接用会乱码
				//String newtitle = new String(exportFileName.getBytes("UTF-8"), "ISO8859-1");
				String newtitle = exportFileName;
				if(workbookList.size() <= 1){
					responseHeader = newtitle + ".xls";
				} else {
					responseHeader = newtitle + ".zip";
				}
			}
			// 设置response类型
			response.setContentType(responseContentType);
			// 设置response头
			//response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(responseHeader, "UTF-8"));
			response.setHeader("content-disposition", "attachment;filename=" + new String(responseHeader.getBytes("GBK"), "ISO8859-1") );
			
			// 这里在文件多个的情况下，需要压缩成一个ZIP文件，暂时先按一个book返回
			if(workbookList.size() == 1){
				fOut = response.getOutputStream();
				workbookList.get(0).write(fOut);
			}
			if(workbookList.size() > 1){
				zipOut = new ZipOutputStream(response.getOutputStream());
				for(Workbook book : workbookList){
					ZipEntry entry = new ZipEntry(book.getSheetAt(0).getSheetName() + ".xls");
					zipOut.putNextEntry(entry);
					book.write(zipOut);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.print("<script language='javascript'>alert('导出过程出现错误,请重试!');window.close();</script>");
			return;
		} finally {
			try {
				if(null != fOut){
					fOut.flush();
					fOut.close();
				}
				if(null != zipOut){
					zipOut.flush();
					zipOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.print("<script language='javascript'>alert('导出过程出现错误,请重试!');window.close();</script>");
				return;
			}
		}
	}
	
	/**
	 * 导出word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportWord")
	public void exportWord(HttpServletRequest request, HttpServletResponse response) {
		// 取得导出文件名
		String exportFileName = request.getParameter("exportFileName");
		// 取得导出SQL的ID
		String sqlId = request.getParameter("sqlId");
		// 取得自定义取得方法
		String customService = request.getParameter("customService");
		// 取得模板方法
		String templete = request.getParameter("templete");
		// 如果SQL是空且自定义是空，则返回
		if(ConverterUtil.isEmpty(sqlId) && ConverterUtil.isEmpty(customService)){
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.print("<script language='javascript'>alert('没有找到导出ID或方法，导出失败!');window.close();</script>");
			return;
		}
		if(ConverterUtil.isEmpty(exportFileName)){
			// 如果没有指定导出的文件名 则按照日期+随机8位码生成文件名
			exportFileName = DataUtils.getDataString(DataUtils.yyyymmddhhmmss) + StringUtil.random(8);
		}
		
		OutputStream fOut = null;
		ZipOutputStream zipOut = null;
		try {
			// 产生导出对象
			List<Object> exportList = new ArrayList<Object>(); 
			if (ConverterUtil.isNotEmpty(customService)) {
				// 从自定义service取得数据
				exportList = ConverterUtil.getDataFormCustomService(customService,
						ConverterUtil.requestParamsToMap(request.getParameterMap()));
			} else {
				// 用共通的service取得数据
				exportList = systemService.findExportList(sqlId,
						ConverterUtil.requestParamsToMap(request.getParameterMap()));
			}

			// response类型
			String responseContentType = "";
			// response头
			String responseHeader = "";
			if(exportList.size() < 1){
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.print("<script language='javascript'>alert('没有符合项件的数据!');window.close();</script>");
				return;
			} else if(exportList.size() == 1){
				// 如果返回一个book就返回vnd.ms-word类型
				responseContentType = "application/vnd.ms-word";
			} else {
				// 如果返回多个book就返回压缩包类型
				responseContentType = "application/zip;charset=UTF-8";
			}
			
			// 取得浏览器类型
			String browse = BrowserUtils.checkBrowse(request);
			// 判断是否是IE浏览器
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
				if(1 == exportList.size()){
					responseHeader = exportFileName + ".doc";
				} else {
					responseHeader = exportFileName + ".zip";
				}
			} else {
				// 非IE内核的浏览器如果直接用会乱码
				String newtitle = new String(exportFileName.getBytes("UTF-8"), "ISO8859-1");
				if(exportList.size() <= 1){
					responseHeader = newtitle + ".doc";
				} else {
					responseHeader = newtitle + ".zip";
				}
			}
			// 设置response类型
			response.setContentType(responseContentType);
			// 设置response头
			response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(responseHeader, "UTF-8"));
			
			// 这里在文件多个的情况下，需要压缩成一个ZIP文件，暂时先按一个book返回
			if(exportList.size() == 1){
				WordUtils.createWordFile(templete, exportList.get(0), response.getOutputStream());
			}
			if(exportList.size() > 1){
				zipOut = new ZipOutputStream(response.getOutputStream());
				int num = 1;
				for (Object book : exportList) {
					ZipEntry entry = new ZipEntry(exportFileName + "_" + num + ".doc");
					zipOut.putNextEntry(entry);
					WordUtils.createWordFile(templete, book, fOut);
					num++;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.print("<script language='javascript'>alert('导出过程出现错误,请重试!');window.close();</script>");
			return;
		} finally {
			try {
				if(null != fOut){
					fOut.flush();
					fOut.close();
				}
				if(null != zipOut){
					zipOut.flush();
					zipOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.print("<script language='javascript'>alert('导出过程出现错误,请重试!');window.close();</script>");
				return;
			}
		}
	}
	
	/**
	 * 导入excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InterruptedException 线程中断异常
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException, InterruptedException {
		AjaxJson j = new AjaxJson();
		// 获取上传文件对象
		String paths = request.getParameter("attachmentPath");
		// 取得导入类型
		String importType = request.getParameter("importType");
		// 取得版本ID
		String versionId = request.getParameter("versionId");
		// 取得分类ID
		String categoryId = request.getParameter("categoryId");
		String msg = saveImportData(versionId, categoryId, paths, importType);
		if (StringUtil.isEmpty(msg)) {
			j.setSuccess(true);
			j.setMsg("数据导入成功!");
		} else {
			j.setSuccess(false);
			j.setMsg(msg);
		}

		return j;
	}

	/**
	 * 保存导入数据
	 * 
	 * @param versionId 版本ID
	 * @param categoryId 分类ID
	 * @param filePath 文件路径
	 * @param importType 导入类别
	 * @return 导入产生的msg
	 */
	@SuppressWarnings("unchecked")
	private String saveImportData(String versionId, String categoryId, String filePath, String importType) {
		try {
			// 取得导入每次提交数据行
			Integer importCommitLine = ConverterUtil.toInteger(ResourceUtil
					.getConfigByName(Constants.IMPORT_COMMIT_LINE));
			// 取得物理名映射的entity
			Object entity = ConverterUtil.getEntityWithTableName(importType);
			if (null == entity) {
				return "数据导入失败,没有找到对应的表!";
			}
			List<String> pathLst = ConverterUtil.stringToList(filePath, ";");
			Map<String, Object> entityAllMap = ConverterUtil.entityToMapForAll(entity);
			// 取得excel和entity对应的map
			Map<String, String> excelSetMethodMap = (Map<String, String>) entityAllMap.get("excelSetMethodMap");
			// 取得entity对应的excel列与entity类型的的map
			Map<String, Object> excelTypeMap = (Map<String, Object>) entityAllMap.get("excelTypeMap");
			// entity中属性和类型的map
			Map<String, Object> fieldTypeMap = (Map<String, Object>) entityAllMap.get("fieldTypeMap");
			// entity中需要去重的属性的List
			List<String> distinctFieldList = (List<String>) entityAllMap.get("distinctFieldList");
			// 取得去重SQL
			String distinctSQL = ConverterUtil.getDistinctSql(importType, distinctFieldList);

			// 定义需要存储的list
			List<Object> entityLst = new ArrayList<Object>();
			for (String path : pathLst) {
				String abosloutPath = ResourceUtil.getSysPath() + path;
				File file = new File(abosloutPath);
				if (!file.exists()) {
					continue;
				}
				// 取得文件输入流
				InputStream inputStream = new FileInputStream(file);
				// 得到工作簿
				Workbook book = null;
				try {
					book = new XSSFWorkbook(inputStream);
					
				} catch (Exception ex) {
					book = new HSSFWorkbook(inputStream);
				}

				// 取得sheet页数
				int sheetNum = book.getNumberOfSheets();
				for (int i = 0; i < sheetNum; i++) {
					// 判断如果是隐藏的sheet情况跳过
					if(book.isSheetHidden(i)){
						continue;
					}
					
					// 得到第一页
					Sheet sheet = book.getSheetAt(i);

					// 得到当前sheet所有行
					Iterator<Row> row = sheet.rowIterator();
					// 取得excel标题Map
					Map<Integer, String> titlemap = getExcelTitleMap(row);

					while (row.hasNext()) {
						// 标题下的第一行
						Row rown = row.next();
						// 判断行高如果是0则认为是隐藏行跳过
						if(rown.getZeroHeight()){
							continue;
						}
						
						// 创建一个新的实体类,一行即一个实体
						Object newEntity = entity.getClass().newInstance();
						// 有版本ID的情况下，使用传入的版本ID
						if (fieldTypeMap.containsKey("versionid")) {
							Method setVerMethod = entity.getClass().getMethod("setVersionid",
									(Class<?>) fieldTypeMap.get("versionid"));
							setVerMethod.invoke(newEntity, versionId);
						}
						
						// 行的所有列
						Iterator<Cell> cellbody = rown.cellIterator();

						// 设置colIndex计数器
						int colIndex = 0;

						// 遍历一行的列
						while (cellbody.hasNext()) {
							Cell cell = cellbody.next();
							// 取得列名
							String excField = titlemap.get(colIndex);
							if (newEntity instanceof PollProductsEntity && excField.equals("分类编码")) {
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String cellVal = cell.getStringCellValue();
								((PollProductsEntity) newEntity).setCategoryid(cellVal);
							}
							// 如果是分类编码列，则跳过
							if (!excelSetMethodMap.containsKey(excField)) {
								colIndex++;
								continue;
							}
							// 取得对应的set方法名
							String setMethodName = excelSetMethodMap.get(excField);
							// 取得对应的类型名
							Class<?> clazz = (Class<?>) excelTypeMap.get(excField);
							// 设置对应entity的值
							setNewEntityAttributeValue(newEntity, cell, setMethodName, clazz);
							colIndex++;
						}// End-while 遍历一行结束

						// 把一个对应好的entity存入list
						entityLst.add(newEntity);

						if (entityLst.size() % importCommitLine == 0) {
							// 重新设置污染物基础信息list的categoryId
							String result = getimportPollProductsList(entityLst);
							if (StringUtil.isNotEmpty(result)) {
								return result;
							}
							result = getimportPollCategoryList(entityLst);
							if (StringUtil.isNotEmpty(result)) {
								return result;
							}
							// 一旦缓存的list中数量达到设置的最大值则提交一次并去重
							systemService.batchSaveThenDistinctBySQL(entityLst, distinctSQL);
							// 清空list
							entityLst.clear();
							// 睡眠200毫秒，减少大数据时的CPU压力
							Thread.sleep(200);
						}
					}// End-While 遍历一个sheet中所有行结束

				}// End-for 一个sheet结束
			}// End-for 所有文件结束

			// 如果缓存的list没到设置的最大值，也要提交一次并去重
			if (entityLst.size() > 0) {
				String result = getimportPollProductsList(entityLst);
				if (StringUtil.isNotEmpty(result)) {
					return result;
				}
				result = getimportPollCategoryList(entityLst);
				if (StringUtil.isNotEmpty(result)) {
					return result;
				}
				systemService.batchSaveThenDistinctBySQL(entityLst, distinctSQL);
			}
		} catch (Exception e) {
			return "导入发生错误，数据导入失败!";
		}
		return "";
	}

	/**
	 * 设置污染物分类list的pid
	 * 
	 * @param entityLst
	 * @return
	 */
	private String getimportPollCategoryList(List<Object> entityLst){
		if (entityLst.get(0) instanceof PollCategoryEntity) {
			List<Map<String, Object>> pidLst = new ArrayList<Map<String, Object>>();
			String pidCodeStr = "";
			// 查询分类编码的个数,用于判断是否和分类ID一一对应
			int pidCodeStrLength = 0;
			Map<String,Object> pidCodeMap = new HashMap<String,Object>();
			for (int categoryIndex = 0; categoryIndex < entityLst.size(); categoryIndex++) {
				// 设置IN中最大的个数，避免sql过长
				if((categoryIndex != 0) && (categoryIndex%90 == 0)){
					// check分类是否存在,如果check的ID全部存在,则设置成分类的真实ID
					pidLst.addAll(getPid(pidCodeStr, pidCodeStrLength));
					// 清空计数器
					pidCodeStr = "";
					pidCodeStrLength = 0;
				} else {
					PollCategoryEntity pollCategoryEntity = ((PollCategoryEntity) entityLst.get(categoryIndex));
					// 拼接污染物分类sql条件
					String pidCode = pollCategoryEntity.getPid();
					if (!pidCodeMap.containsKey(pidCode)) {
						pidCodeStr += ("'" + pidCode + "',");
						pidCodeMap.put(pidCode, "");
						pidCodeStrLength++;
					}
				}
			}
//			systemService.getGUID(10);
			
		}
		return "";
	}
	
	/**
	 * 重新设置污染物基础信息list的categoryId
	 * 
	 * @param entityLst 重新设置污染物基础信息list
	 */
	private String getimportPollProductsList(List<Object> entityLst) {
		if (entityLst.get(0) instanceof PollProductsEntity) {
			List<Map<String, Object>> categoryTableLst = new ArrayList<Map<String, Object>>();
			// 查询分类编码的sql串
			String categoryCodeStr = "";
			// 查询字典数据的sql串
			String dictionaryCodeStr = "";
			// 查询分类编码的个数,用于判断是否和分类ID一一对应
			int categoryCodeStrLength = 0;
			// 查询字典编码的个数,用于判断是否和字典数据一一对应
			int dictionaryCodeStrLength = 0;
			// 查询分类编码的map,用于防止excel中数据重复
			Map<String,Object> casMap = new HashMap<String,Object>();
			// 查询字典的map,用于防止excel中数据重复
			Map<String,Object> categoryMap = new HashMap<String,Object>();
			for (int categoryIndex = 0; categoryIndex < entityLst.size(); categoryIndex++) {
				// 设置IN中最大的个数，避免sql过长
				if((categoryIndex != 0) && (categoryIndex%90 == 0)){
					// 是否在字典中存在的check
					String checkDictiResult = checkHaveDictionary(dictionaryCodeStr,
							dictionaryCodeStrLength);
					if(StringUtil.isNotEmpty(checkDictiResult)){
						return checkDictiResult;
					}
					
					// check分类是否存在,如果check的ID全部存在,则设置成分类的真实ID
					String checkCateResult = checkCategoryId(categoryTableLst,categoryCodeStr,
							categoryCodeStrLength);
					if(StringUtil.isNotEmpty(checkCateResult)){
						return checkCateResult;
					}
					// 清空计数器
					categoryCodeStr = "";
					categoryCodeStrLength = 0;
				} else {
					PollProductsEntity pollProductsEntity = ((PollProductsEntity) entityLst.get(categoryIndex));
					// 拼接字典sql条件
					String cas = pollProductsEntity.getCas();
					if (!casMap.containsKey(cas)) {
						dictionaryCodeStr += ("'" + cas + "',");
						casMap.put(cas, "");
						dictionaryCodeStrLength++;
					}

					// 拼接分类sql条件
					String categoryid = pollProductsEntity.getCategoryid();
					if (!categoryMap.containsKey(categoryid)) {
						categoryCodeStr += ("'" + categoryid + "',");
						categoryMap.put(categoryid, "");
						categoryCodeStrLength++;
					}
				}
			}
			// 可能条件未能达到90，或者超过过90后剩下的也要进行查询
			if(StringUtil.isNotEmpty(categoryCodeStr)){
				// 是否在字典中存在的check
				String checkDictiResult = checkHaveDictionary(dictionaryCodeStr,
						dictionaryCodeStrLength);
				if(StringUtil.isNotEmpty(checkDictiResult)){
					return checkDictiResult;
				}
				
				// check分类是否存在,如果check的ID全部存在,则设置成分类的真实ID
				String checkCateResult = checkCategoryId(categoryTableLst,categoryCodeStr,
						categoryCodeStrLength);
				if(StringUtil.isNotEmpty(checkCateResult)){
					return checkCateResult;
				}
			}
			// 设置categoryId
			for (Object pollEntity : entityLst) {
				PollProductsEntity ppEntity = (PollProductsEntity) pollEntity;
				String ppCategoryid = ppEntity.getCategoryid();
				for (Map<String, Object> categoryIdMap : categoryTableLst) {
					// 替换分类编码为真实分类ID
					if (categoryIdMap.get("CODE").equals(ppCategoryid)) {
						ppEntity.setCategoryid(ConverterUtil.toString(categoryIdMap.get("ID")));
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 取得pid
	 * 
	 * @param pidCodeStr
	 *            父分类编码串
	 * @param pidCodeStrLength
	 *            父分类编码串长度
	 * @return check结果
	 */
	private List<Map<String, Object>> getPid(String pidCodeStr, int pidCodeStrLength) {
		// 取得分类ID的sql
		String queryCategoryIdSql = "SELECT ID,CODE FROM NKY_PO_CATEGORY T WHERE CODE IN (?)";
		pidCodeStr = pidCodeStr.substring(0, pidCodeStr.length() - 1);
		// 从DB取得category的真实ID的list
		List<Map<String, Object>> codeList = systemService
				.findForJdbc(queryCategoryIdSql.replace("?", pidCodeStr));
		return codeList;
	}

	/**
	 * check并取得categoryId
	 * 
	 * @param categoryTableLst categoryId缓存的list
	 * @param categoryCodeStr 分类编码串
	 * @param categoryCodeStrLength 分类编码串长度
	 * @return
	 */
	private String checkCategoryId(List<Map<String, Object>> categoryTableLst, String categoryCodeStr,
			int categoryCodeStrLength) {
		// 取得分类ID的sql
		String queryCategoryIdSql = "SELECT ID,CAS FROM NKY_PO_CATEGORY T WHERE CODE IN (?)";
		categoryCodeStr = categoryCodeStr.substring(0, categoryCodeStr.length() - 1);
		// 从DB取得category的真实ID的list
		List<Map<String, Object>> codeList = systemService
				.findForJdbc(queryCategoryIdSql.replace("?", categoryCodeStr));
		// 如果没有对应找到分类ID，则认为分类数据不完备，返回错误信息
		if (null == codeList || codeList.size() != categoryCodeStrLength) {
			return "含有未定义的分类编码，数据导入失败。请先导入相关【污染物分类】数据后重试。";
		}
		// 将取得的categoryId缓存
		categoryTableLst.addAll(codeList);
		return "";
	}

	/**
	 * 检查是否在数据字典中含有该污染物
	 * 
	 * @param dictionaryCodeStr 字典条件CAS串
	 * @param dictionaryCodeStrLength 字典条件CAS串长度
	 * @return check的message
	 */
	private String checkHaveDictionary(String dictionaryCodeStr, int dictionaryCodeStrLength) {
		String queryDictionarySql = "SELECT ID,CAS FROM NKY_POLL_DICTIONARY T WHERE CAS IN (?)";
		dictionaryCodeStr = dictionaryCodeStr.substring(0, dictionaryCodeStr.length()-1);
		// 从DB取得字典数据
		List<Map<String, Object>> dictiCodeList = systemService.findForJdbc(queryDictionarySql.replace("?", dictionaryCodeStr));
		// 如果没有对应字典信息，或者与传入的长度不同，则认为没有分类数据或者不全，返回错误信息
		if(null == dictiCodeList || dictiCodeList.size() != dictionaryCodeStrLength){
			return "含有字典中未定义的污染物信息，数据导入失败。请先导入相关【污染物字典】数据后重试。";
		}
		return "";
	}

	/**
	 * 取得excel标题Map
	 * 
	 * @param row excel的行
	 * @return 标题Map
	 */
	private Map<Integer, String> getExcelTitleMap(Iterator<Row> row) {
		// 得到标题行
		Row title = row.next();
		// 得到标题行的所有列
		Iterator<Cell> cellTitle = title.cellIterator();
		// 标题文字map
		Map<Integer, String> titlemap = new HashMap<Integer, String>();
		int colIndex = 0;
		// 循环标题所有的列
		while (cellTitle.hasNext()) {
			Cell cell = cellTitle.next();
			String value = cell.getStringCellValue();
			// 保存标题到map
			titlemap.put(colIndex, value);
			colIndex++;
		}
		return titlemap;
	}

	/**
	 * 设置对应entity的值
	 * 
	 * @param newEntity 实体
	 * @param cell 单元格
	 * @param setMethodName 方法名
	 * @param clazz 实体中该单元格对应的属性类型
	 * 
	 * @throws NoSuchMethodException  没有找到set方法异常
	 * @throws IllegalAccessException 反射实体类异常
	 * @throws InvocationTargetException 反射set方法异常
	 * @throws ParseException 类型转换异常
	 */
	private void setNewEntityAttributeValue(Object newEntity, Cell cell, String setMethodName, Class<?> clazz)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
		Method setMethod = newEntity.getClass().getMethod(setMethodName, clazz);
		if (clazz.equals(String.class)) {
			// 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
			cell.setCellType(Cell.CELL_TYPE_STRING);
			setMethod.invoke(newEntity, cell.getStringCellValue());
		} else if (clazz.equals(Date.class)) {
			Date cellDate = null;
			// 日期格式
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				cellDate = ConverterUtil.toDate(ConverterUtil.toLong(cell.getDateCellValue()));
			} else {
				cellDate = ConverterUtil.toDate(cell.getStringCellValue());
			}
			setMethod.invoke(newEntity, cellDate);
		} else if (clazz.equals(Boolean.class)) {
			boolean valBool;
			// 布尔格式
			if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
				valBool = cell.getBooleanCellValue();
			} else {
				valBool = cell.getStringCellValue().equalsIgnoreCase("true")
						|| (!cell.getStringCellValue().equals("0"));
			}
			setMethod.invoke(newEntity, valBool);
		} else if (clazz.equals(Integer.class)) {
			Integer valInt;
			// Int型格式
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valInt = (new Double(cell.getNumericCellValue())).intValue();
			} else {
				valInt = new Integer(cell.getStringCellValue());
			}
			setMethod.invoke(newEntity, valInt);
		} else if (clazz.equals(Long.class)) {
			Long valLong;
			// Long型格式
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valLong = (new Double(cell.getNumericCellValue())).longValue();
			} else {
				valLong = new Long(cell.getStringCellValue());
			}
			setMethod.invoke(newEntity, valLong);
		} else if (clazz.equals(BigDecimal.class)) {
			BigDecimal valDecimal;
			// BigDecimal型格式
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valDecimal = new BigDecimal(cell.getNumericCellValue());
			} else {
				valDecimal = new BigDecimal(cell.getStringCellValue());
			}
			setMethod.invoke(newEntity, valDecimal);
		}
	}
	
	/**
	 * 附件下载
	 * 
	 * @return
	 */
	@RequestMapping(params = "attachmentDownload")
	public void attachmentDownload(HttpServletRequest request, HttpServletResponse response) {
		BufferedOutputStream bos = null;
		FileInputStream bis = null;
		try {
			// 设置返回编码格式
			response.setCharacterEncoding("UTF-8");
			
			long fileLength = 0;
			// 取得管理工程的工程地址
			String basePath = request.getSession().getServletContext().getRealPath("");
			String path = request.getParameter("url");
			File downloadFile = new File(basePath + ConverterUtil.getActionPath("", path));
			// 如果文件不存在,则返回错误信息
			if (!downloadFile.exists()) {
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<script language='javascript'>alert('" + downloadFile.getName()
						+ ",文件不存在!');window.close();</script>");
				return;
			}

			bis = new FileInputStream(downloadFile);
			fileLength = downloadFile.length();
			// 设置返回头信息
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(downloadFile.getName().getBytes("GBK"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.print("<script language='javascript'>alert('下载过程出现错误,请重试!');window.close();</script>");
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.print("<script language='javascript'>alert('下载过程出现错误,请重试!');window.close();</script>");
			}
		}
		return;
	}
}
