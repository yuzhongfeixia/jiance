package com.hippo.nky.controller.standard;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jms.JMSException;
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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.MessageProductor;
import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.entity.standard.StandardVersionDTO;
import com.hippo.nky.entity.standard.StandardVersionEntity;
import com.hippo.nky.service.standard.StandardVersionServiceI;

/**   
 * @Title: Controller
 * @Description: 版本管理
 * @author zhangdaihao
 * @date 2013-07-01 11:38:33
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/standardVersionController")
public class StandardVersionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StandardVersionController.class);

	@Autowired
	private StandardVersionServiceI standardVersionService;
	@Autowired
	private SystemService systemService;
	private String message;
	private String flag;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 版本管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "standardVersion")
	public ModelAndView standardVersion(HttpServletRequest request) {
		request.setAttribute("category", request.getParameter("category"));
		request.setAttribute("flag", request.getParameter("flag"));	
		// 取得用户类型
		String userType = ResourceUtil.getSessionUserName().getUsertype();
		request.setAttribute("userType", userType);
		return new ModelAndView("com/hippo/nky/standard/standardVersionList");
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
	public void datagrid(StandardVersionEntity standardVersion,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(StandardVersionEntity.class, dataGrid);
		//查询条件组装器
		cq.addOrder("stopflag", SortDirection.asc);
		cq.addOrder("createdate", SortDirection.desc);
		if(standardVersion.getCategory() != null && 2 == standardVersion.getCategory()){
			cq.eq("category", standardVersion.getCategory());
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, standardVersion,request.getParameterMap());
		this.standardVersionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除版本管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(StandardVersionEntity standardVersion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		standardVersion = systemService.getEntity(StandardVersionEntity.class, standardVersion.getId());
		message = "删除成功";
		standardVersionService.delStandardVersion(standardVersion);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加版本管理
	 * 
	 * @param ids
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(StandardVersionEntity standardVersion, HttpServletRequest request) throws ParseException {
		AjaxJson j = new AjaxJson();
		boolean publish = Boolean.parseBoolean(request.getParameter("publish"));
		if(publish){
			standardVersion.setBegindate(new Date());
			//standardVersion.setBegindate(ConverterUtil.toDate("2013-11-14"));
		}
		// 类型(编辑/复制)
		String type = request.getParameter("type");
		if (StringUtil.isNotEmpty(standardVersion.getId())) {
			message = "更新成功";
			StandardVersionEntity t = standardVersionService.get(StandardVersionEntity.class, standardVersion.getId());
			try {
				if("copy".equals(type)){
					message = "复制成功";
					if(standardVersion.getCategory() == 0){
						// 复制农产品分类
						standardVersionService.copyRecordForAgrCategory(standardVersion);
					}else if(standardVersion.getCategory() == 1){
						// 复制污染物分类
						standardVersionService.copyRecordForAgrPoll(standardVersion);
					}else if(standardVersion.getCategory() == 2){
						// 复制判定标准
						standardVersion.setAgrVersionId(t.getAgrVersionId());
						standardVersion.setPollVersionId(t.getPollVersionId());
						standardVersion.setLimitVersionId(t.getLimitVersionId());
						standardVersionService.copyRecordForjudgeStandard(standardVersion);
					}
				}else{
					MyBeanUtils.copyBeanNotNull2Bean(standardVersion, t);
					standardVersionService.standardVersionUpdate(t,publish);
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			standardVersion.setPublishmark(0);
			standardVersion.setStopflag(0);
			if(standardVersion.getCategory() == 2){
				standardVersionService.judgeStandardDataCreate(standardVersion);
			}else{
				standardVersionService.save(standardVersion);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 添加版本管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "copy")
	@ResponseBody
	public AjaxJson copy(StandardVersionEntity standardVersion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean publish = Boolean.parseBoolean(request.getParameter("publish"));
		if(publish){
			standardVersion.setBegindate(new Date());
		}
		// 类型(复制)
		if (StringUtil.isNotEmpty(standardVersion.getId())) {
			try {
				message = "复制成功";
				if(standardVersion.getCategory() == 0){
					// 复制农产品分类
					standardVersionService.copyRecordForAgrCategory(standardVersion);
				}else if(standardVersion.getCategory() == 1){
					// 复制污染物分类
					standardVersionService.copyRecordForAgrPoll(standardVersion);
				}else if(standardVersion.getCategory() == 2){
					// 复制判定标准
					standardVersionService.copyRecordForjudgeStandard(standardVersion);
				}	
//					}else if(standardVersion.getCategory() == 3){
//						// 复制限量标准
//						standardVersionService.copyRecordForLimitStandard(standardVersion);
//					}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		return j;
	}

	/**
	 * 版本管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(StandardVersionEntity standardVersion, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(standardVersion.getId())) {
			standardVersion = standardVersionService.getEntity(StandardVersionEntity.class, standardVersion.getId());
			req.setAttribute("standardVersionPage", standardVersion);
			req.setAttribute("type", req.getParameter("type"));
		}else{
			// 判断是否是新建判定标准
			if(standardVersion.getCategory()!=null && standardVersion.getCategory() == 2){
				// 农产品版本选择列表
				List<StandardVersionEntity> agrVersionList = standardVersionService.findHql("from StandardVersionEntity where category = ? and publishmark = 1 and stopflag = 0 order by begindate desc ", 0);
				// 污染物选择版本列表
				List<StandardVersionEntity> pollVersionList = standardVersionService.findHql("from StandardVersionEntity where category = ? and publishmark = 1 and stopflag = 0 order by begindate desc ", 1);
				// 限量标准版本选择列表
				List<LimitStandardVersionEntity> limitVersionList = standardVersionService.findHql("from LimitStandardVersionEntity where publishflag = 1 and stopflag = 0 order by publishDate desc ");
				// 判定标准版本列表
				List<StandardVersionEntity> judgeVersionList = standardVersionService.findHql("from StandardVersionEntity where category = ? and publishmark = 1 and stopflag = 0 order by begindate desc ", 2);
				req.setAttribute("agrVersionList", agrVersionList);
				req.setAttribute("pollVersionList", pollVersionList);
				req.setAttribute("limitVersionList", limitVersionList);
				req.setAttribute("judgeVersionList", judgeVersionList);
				return new ModelAndView("com/hippo/nky/standard/standardVersionForJudge");
			}
			// 非判定标准时，设定版本类别
			req.setAttribute("standardVersionPage", standardVersion);
		}
		return new ModelAndView("com/hippo/nky/standard/standardVersion");
	}
	
	/**
	 * 判定标准版本新建 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "judgeStandardCreate")
	public ModelAndView judgeStandardCreate(HttpServletRequest request) {
		// 版本id
		String versionId = request.getParameter("versionId");
		// 类型 （agr:农产品; poll:污染物）
		String type = request.getParameter("type");
		String treeData = standardVersionService.judgeStandardCreateTree(versionId, type);
		request	.setAttribute("treeData", treeData);
		
		return new ModelAndView("com/hippo/nky/standard/judgeStandardCreate");
	}
	
	/**
	 * 判断是否已经发布
	 * 
	 * @return
	 */
	@RequestMapping(params = "isPublished")
	@ResponseBody
	public AjaxJson isPublished(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = req.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			StandardVersionEntity svEntity = new StandardVersionEntity();
			svEntity.setId(id);
			svEntity = standardVersionService.getEntity(StandardVersionEntity.class, svEntity.getId());
			Integer publishMark = svEntity.getPublishmark();
			if(Constants.ONE.equals(publishMark)){
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 判断是否已经停用
	 * 
	 * @return
	 */
	@RequestMapping(params = "isStoped")
	@ResponseBody
	public AjaxJson isStoped(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = req.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			StandardVersionEntity svEntity = new StandardVersionEntity();
			svEntity.setId(id);
			svEntity = standardVersionService.getEntity(StandardVersionEntity.class, svEntity.getId());
			Integer stopFlag = svEntity.getStopflag();
			if(Constants.ONE.equals(stopFlag)){
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	@RequestMapping(params = "typeGroupTabs1")
	public ModelAndView typeGroupTabs(HttpServletRequest request) {
		List<StandardVersionDTO> typegroupList = new ArrayList<StandardVersionDTO>();
		StandardVersionDTO dto1 = new StandardVersionDTO("0","农产品分类标准","agr");
		StandardVersionDTO dto2 = new StandardVersionDTO("1","污染物分类标准","pollant");
		StandardVersionDTO dto3 = new StandardVersionDTO("3","限量标准","limitv");
		StandardVersionDTO dto4 = new StandardVersionDTO("2","判定标准","judge");
		StandardVersionDTO dto5 = new StandardVersionDTO("4","毒理学标准","dlx");
		
		typegroupList.add(dto1);
		typegroupList.add(dto2);
		typegroupList.add(dto3);
		typegroupList.add(dto4);
		typegroupList.add(dto5);
		request.setAttribute("typegroupList", typegroupList);
		return new ModelAndView("com/hippo/nky/standard/typeGroupTabs");
	}
	
	/**
	 * 数据同步
	 * 
	 * @return 
	 */
	@RequestMapping(params = "dataSynchronization")
	@ResponseBody
	public AjaxJson dataSynchronization(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		int result;
		try {
			result = standardVersionService.dataSynchronization(req);
			j.setSuccess(result >= 0);
			if(result > 0){
				j.setMsg("同步了"+result+"条数据。");
			}else{
				j.setMsg("没有数据。");
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("同步出错。");
		}
		return j;
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
		String constraint = "CNAME" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param")
				+ ConverterUtil.SEPARATOR_ELEMENT + "CATEGORY"
				+ ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("category");
		AjaxJson j = systemService.uniquenessCheck("nky_standard_version", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("版本名称已存在!");
		}
		return j;
	}
	
}
