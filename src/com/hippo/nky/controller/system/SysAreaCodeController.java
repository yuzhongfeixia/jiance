package com.hippo.nky.controller.system;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;
import com.hippo.nky.service.system.SysAreaCodeServiceI;

/**   
 * @Title: Controller
 * @Description: 行政区划
 * @author nky
 * @date 2013-10-23 13:16:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sysAreaCodeController")
public class SysAreaCodeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SysAreaCodeController.class);
	
	private static final String NAME_SPACE = "com.hippo.nky.entity.common.CommonEntity.";

	@Autowired
	private SysAreaCodeServiceI sysAreaCodeService;
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
	 * 行政区划列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sysAreaCode")
	public ModelAndView sysAreaCode(HttpServletRequest request) {
		//request.setAttribute("areacodeList", areagrid(request));
		//request.setAttribute("areacodeList", getAreaCodeList());
		
		return new ModelAndView("com/hippo/nky/system/sysAreaCodeList");
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
	public void datagrid(SysAreaCodeEntity sysAreaCode,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sysAreaCode, request.getParameterMap());
		this.sysAreaCodeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

//	/**
//	 * 区域列表，树形展示
//	 * @param request
//	 * @param treegrid
//	 * @return
//	 */
//	@RequestMapping(params = "areagrid")
//	@ResponseBody
//	public List<TreeGrid> areagrid(HttpServletRequest request) {
//		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class);
//		cq.addOrder("code", SortDirection.asc);
//		cq.add();
//		List<SysAreaCodeEntity> areaList=systemService.getListByCriteriaQuery(cq, false);
//		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
//		for (SysAreaCodeEntity area: getTreeTableData(areaList)) {
//			String areaname = area.getAreaname();
//			String code = area.getCode();
//			TreeGrid treegrid = new TreeGrid();
//			
//			treegrid.setText(areaname);
//			treegrid.setId(area.getId());
//			
//			if(code != null && code.length() != 0){
//				if ("0000".equals(code.substring(2, code.length())) ){
//					treegrid.setSrc("isPro");
//				} else if ("00".equals(code.substring(4, code.length()))) {
//					treegrid.setSrc("isCity");
//				} else {
//					treegrid.setSrc("isArea");
//				}
//			}
//			
//			if (area.getSysAreaCodeEntity() != null) {
//				treegrid.setParentId(area.getSysAreaCodeEntity().getId());
//			}
//
//
//			treeGrids.add(treegrid);
//		}
//
//		return treeGrids;
//	}
	
	/**
	 * 区域列表，树形展示
	 * @param request
	 * @param treegrid
	 * @return
	 */
	@RequestMapping(params = "areagrid")
	@ResponseBody
	public AjaxJson areagrid(HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		attributesMap.put("areacodeList", getAreaCodeList());
		j.setAttributes(attributesMap);

		return j;
	}
	
	/**
	 * 取得行政区划全部信息
	 * @return
	 */
	private List<TreeGrid> getAreaCodeList() {
//		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class);
//		cq.addOrder("showOrder", SortDirection.asc);
//		cq.eq("flag", "0");
//		cq.add();
//		List<SysAreaCodeEntity> areaList = systemService.getListByCriteriaQuery(cq, false);
		
		List<SysAreaCodeEntity> areaList = systemService.findListByMyBatis(NAME_SPACE + "getAllSysAreaCode", null);
		
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
//		for (SysAreaCodeEntity area: getTreeTableData(areaList)) {
		for (SysAreaCodeEntity area: areaList) {
			String areaname = area.getAreaname();
			String code = area.getCode();
			TreeGrid treegrid = new TreeGrid();
			
			treegrid.setText(areaname);
			treegrid.setId(area.getId());
			
			if(code != null && code.length() != 0){
				if ("0000".equals(code.substring(2, code.length())) ){
					treegrid.setSrc("isPro");
				} else if ("00".equals(code.substring(4, code.length()))) {
					treegrid.setSrc("isCity");
				} else {
					treegrid.setSrc("isArea");
				}
			}
			treegrid.setParentId(area.getParentareaid());
//			if (area.getSysAreaCodeEntity() != null) {
//				treegrid.setParentId(area.getSysAreaCodeEntity().getId());
//			}
			treeGrids.add(treegrid);
		}
		return treeGrids;
	}
	
	/**
	 * 取得treetable数据结构
	 * @param functionList
	 * @return
	 */
	private List<SysAreaCodeEntity> getTreeTableData(List<SysAreaCodeEntity> areaList){
		List<SysAreaCodeEntity> allList = new ArrayList<SysAreaCodeEntity>();
		List<SysAreaCodeEntity> structureList = new ArrayList<SysAreaCodeEntity>();
		for (SysAreaCodeEntity area : areaList) {
			if (area.getSysAreaCodeEntity() == null) {
				structureList.add(area);
			}
		}
		return pp(structureList, allList);

	}
	
	/**
	 * 节点递归
	 * @param structureList
	 * @param allList
	 * @return
	 */
	private List<SysAreaCodeEntity> pp(List<SysAreaCodeEntity> structureList, List<SysAreaCodeEntity> allList) {
		for (SysAreaCodeEntity area : structureList) {
			allList.add(area);
			if (area.getSysAreaCodeEntitys() != null && area.getSysAreaCodeEntitys().size() > 0) {
				pp(area.getSysAreaCodeEntitys(), allList);
			}
		}
		return allList;
	}
	/**
	 * 删除行政区划
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SysAreaCodeEntity sysAreaCode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sysAreaCode = systemService.getEntity(SysAreaCodeEntity.class, sysAreaCode.getId());
		message = "行政区划删除成功";
		sysAreaCodeService.delete(sysAreaCode);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加行政区划
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SysAreaCodeEntity sysAreaCode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String editpid = request.getParameter("parentId");
		String addpid = request.getParameter("pid");
		if (StringUtils.isEmpty(editpid)) {
			SysAreaCodeEntity parentCode = systemService.getEntity(SysAreaCodeEntity.class, addpid);

			sysAreaCode.setSysAreaCodeEntity(parentCode);
			sysAreaCode.setFlag("0");
			sysAreaCodeService.save(sysAreaCode);
			message = "行政区划添加成功";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			attributesMap.put("expendId", addpid);
		} else {
	
			SysAreaCodeEntity parentAreaCode = systemService.getEntity(SysAreaCodeEntity.class, editpid);
			sysAreaCode.setSysAreaCodeEntity(parentAreaCode);
		
			sysAreaCodeService.saveOrUpdate(sysAreaCode);
			message = "行政区划更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			attributesMap.put("expendId", editpid);
		}
		j.setMsg(message);
		
		j.setAttributes(attributesMap);
		return j;
	}

	/**
	 * 行政区划列表页面跳转	
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(SysAreaCodeEntity sysAreaCode, HttpServletRequest req) {
		req.setAttribute("pid", req.getParameter("pid"));
	
		return new ModelAndView("com/hippo/nky/system/sysAreaCode");
	}

	/**
	 * 行政区划列表页面跳转	
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(SysAreaCodeEntity sysAreaCode, HttpServletRequest req) {
		String parentId = req.getParameter("parentId");
		req.setAttribute("parentId", parentId);
		if (StringUtil.isNotEmpty(sysAreaCode.getId())) {
			sysAreaCode = sysAreaCodeService.getEntity(SysAreaCodeEntity.class, sysAreaCode.getId());
			req.setAttribute("sysAreaCode", sysAreaCode);
		}
		return new ModelAndView("com/hippo/nky/system/sysAreaCode");
	}

	/**
	 * 行政区划列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SysAreaCodeEntity sysAreaCode, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sysAreaCode.getId())) {
			sysAreaCode = sysAreaCodeService.getEntity(SysAreaCodeEntity.class, sysAreaCode.getId());
			req.setAttribute("sysAreaCode", sysAreaCode);
		}
		return new ModelAndView("com/hippo/nky/system/sysAreaCode");
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
		String checkFlg = request.getParameter("flg");
		if (StringUtils.equals(checkFlg, "1")) {
//			String constraint = "AREANAME" + ConverterUtil.SEPARATOR_KEY_VALUE
//					+ request.getParameter("param");
//			j = systemService.uniquenessCheck("SYS_AREA_CODE", request.getParameter("id"), constraint);
//			if(!j.isSuccess()){
//				j.setMsg("区划名称已存在!");
//			}

		} 
		if (StringUtils.equals(checkFlg, "2")) {
			String constraint = "CODE" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param")
					+ ConverterUtil.SEPARATOR_ELEMENT + "FLAG"
					+ ConverterUtil.SEPARATOR_KEY_VALUE
					+ "0";
			j = systemService.uniquenessCheck("SYS_AREA_CODE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("区划代码已存在!");
			}
		}
		if (StringUtils.equals(checkFlg, "3")) {
			String constraint = "SELFCODE" + ConverterUtil.SEPARATOR_KEY_VALUE
					+ request.getParameter("param")
					+ ConverterUtil.SEPARATOR_ELEMENT + "FLAG"
					+ ConverterUtil.SEPARATOR_KEY_VALUE
					+ "0";
			j = systemService.uniquenessCheck("SYS_AREA_CODE", request.getParameter("id"), constraint);
			if(!j.isSuccess()){
				j.setMsg("自定义编码已存在!");
			}
		}
		return j;
	}
}
