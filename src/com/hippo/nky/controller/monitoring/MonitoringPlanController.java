package com.hippo.nky.controller.monitoring;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.SessionInfo;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.monitoring.MonitoringAttachmentEntity;
import com.hippo.nky.entity.monitoring.MonitoringPlanEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.entity.standard.PortalAttachmentEntity;
import com.hippo.nky.entity.standard.StandardVersionEntity;
import com.hippo.nky.page.monitoring.MonitoringPlanPage;
import com.hippo.nky.page.monitoring.MonitoringTaskDetailsPage;
import com.hippo.nky.service.monitoring.MonitoringPlanServiceI;
/**   
 * @Title: Controller
 * @Description: 检测方案数据
 * @author nky
 * @date 2013-10-23 15:54:31
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/monitoringPlanController")
public class MonitoringPlanController extends BaseController {
	
	private static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.MonitoringPlanEntity.";
	
	private static final String NAME_SPACE1 = "com.hippo.nky.entity.monitoring.MonitoringProjectEntity.";
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MonitoringPlanController.class);

	@Autowired
	private MonitoringPlanServiceI monitoringPlanService;
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
	 * 检测方案数据列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringPlan")
	public ModelAndView monitoringPlan(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/monitoring/monitoringPlanList");
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
	public void datagrid(MonitoringPlanEntity monitoringPlan,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MonitoringPlanEntity.class, dataGrid);
		//查询条件组装器
		/*org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, monitoringPlan);
		this.monitoringPlanService.getDataGridReturn(cq, true);*/
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		monitoringPlan.setReleaseunit(sessioninfo.getUser().getTSDepart().getId());
		monitoringPlanService.datagrid(monitoringPlan,dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除检测方案数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MonitoringPlanEntity monitoringPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		monitoringPlanService.del(monitoringPlan);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加检测方案数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MonitoringPlanEntity monitoringPlan,MonitoringPlanPage monitoringPlanPage, HttpServletRequest request) {
		List<MonitoringProjectEntity> monitoringProjectList =  monitoringPlanPage.getMonitoringProjectList();
		List<MonitoringAttachmentEntity> monitoringAttachmentList =  monitoringPlanPage.getMonitoringAttachmentList();
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(monitoringPlan.getId())) {
			message = "更新成功";
			MonitoringPlanEntity t = monitoringPlanService.get(MonitoringPlanEntity.class, monitoringPlan.getId());
			//MyBeanUtils.copyBeanNotNull2Bean(monitoringPlan, t);
			t.setType(monitoringPlan.getType());
			t.setName(monitoringPlan.getName());
			monitoringPlanService.updateMain(t, monitoringProjectList,monitoringAttachmentList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
			monitoringPlanService.addMain(monitoringPlan, monitoringProjectList,monitoringAttachmentList,sessioninfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	/**
	 * 检测方案数据列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MonitoringPlanEntity monitoringPlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(monitoringPlan.getId())) {
			monitoringPlan = monitoringPlanService.getEntity(MonitoringPlanEntity.class, monitoringPlan.getId());
			req.setAttribute("monitoringPlanPage", monitoringPlan);
			monitoringProjectList(monitoringPlan,req);
			List<MonitoringAttachmentEntity> monitoringAttachmentList = getMonitoringAttachmentList(monitoringPlan);
			req.setAttribute("monitoringAttachmentList", monitoringAttachmentList);
		}
		// 加载组织机构
		SessionInfo sessioninfo =(SessionInfo) req.getSession().getAttribute(Globals.USER_SESSION);
		Map<String,String> getUserDepartInfo  = systemService.getObjectByMyBatis(NAME_SPACE+"getUserDepartInfo", sessioninfo.getUser().getId());
	    //List<OrganizationEntity> organizationEntityList = systemService.findHql("from OrganizationEntity where areacode2 like ? ",getUserDepartInfo.get("gradeCode")+"%");
	    List<OrganizationEntity> organizationEntityList = systemService.findListByMyBatis(NAME_SPACE1+"getProjectOrganization", getUserDepartInfo.get("gradeCode")+"%");
	    String organizationData = "";
	    for(OrganizationEntity organizationEntity : organizationEntityList){
	    	organizationData += organizationEntity.getCode() + ConverterUtil.SEPARATOR_KEY_VALUE + organizationEntity.getOgrname() + ConverterUtil.SEPARATOR_ELEMENT;
	    }
		req.setAttribute("organizationData", organizationData);
		// 标准选择列表
		List<StandardVersionEntity> judgeVersionList = systemService.findHql("from StandardVersionEntity where category = ? and publishmark = 1 and stopflag = 0 order by begindate desc ", 2);
		String judgeVersionData = "";
	    for(StandardVersionEntity standardVersionEntity : judgeVersionList){
	    	judgeVersionData += standardVersionEntity.getId() + ConverterUtil.SEPARATOR_KEY_VALUE + standardVersionEntity.getCname() + ConverterUtil.SEPARATOR_ELEMENT;
	    }
		req.setAttribute("judgeVersionData", judgeVersionData);
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringPlan");
	}
	
	
	/**
	 * 加载明细列表[检测项目数据]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringProjectList")
	public ModelAndView monitoringProjectList(MonitoringPlanEntity monitoringPlan, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object planCode0 = monitoringPlan.getPlanCode();
		//===================================================================================
		//删除-检测项目数据
	    String hql0 = "from MonitoringProjectEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringProjectEntity> monitoringProjectEntityList = systemService.findHql(hql0,planCode0);
		req.setAttribute("monitoringProjectList", monitoringProjectEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjectList");
	}
	/**
	 * 加载明细列表[方案附件]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringAttachmentList")
	public ModelAndView monitoringAttachmentList(MonitoringPlanEntity monitoringPlan, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object planCode1 = monitoringPlan.getPlanCode();
		//===================================================================================
		//删除-方案附件
	    String hql1 = "from MonitoringAttachmentEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringAttachmentEntity> monitoringAttachmentEntityList = systemService.findHql(hql1,planCode1);
		req.setAttribute("monitoringAttachmentList", monitoringAttachmentEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringAttachmentList");
	}
	
	/**
	 * 加载明细列表[方案附件]
	 * 
	 * @return
	 */
	private List<MonitoringAttachmentEntity> getMonitoringAttachmentList(MonitoringPlanEntity monitoringPlan) {
		//获取参数
		Object planCode1 = monitoringPlan.getPlanCode();
		return monitoringPlanService.getMonitoringAttachmentList(planCode1);
	}
	
	/**
	 * 我的检测任务管理页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "myTask")
	public ModelAndView myTask(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req){
		
		Map<String,Object> pageObj = new HashMap<String,Object>();
		req.setAttribute("cityCodeList", monitoringPlanService.getCityAndCountryList(pageObj, 1));
		return new ModelAndView("com/hippo/nky/monitoring/monitoringPlanMyTask");
	}
	
	/**
	 * AJAX请求数据 我的检测任务数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "myTaskDatagrid")
	public void myTaskDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		if(ConverterUtil.isNotEmpty(request.getParameter("projectCode"))){
			monitoringPlanService.myTaskDatagrid(request,dataGrid);
		}else{
			dataGrid.setReaults(new ArrayList());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 我的检测任务  任务分配页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "myTaskDistributionSet")
	public ModelAndView myTaskDistributionSet(HttpServletRequest req){
		
		String hql1 = "from MonitoringTaskEntity where 1 = 1 AND taskcode = ? ";
	    List<MonitoringTaskEntity> monitoringTaskEntityList = systemService.findHql(hql1,req.getParameter("taskCode"));
	    req.setAttribute("entity", monitoringTaskEntityList.get(0));
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("taskCode", req.getParameter("taskCode"));
		//TSUser user = ;
		paramMap.put("orgCode", ResourceUtil.getSessionUserName().getTSDepart().getId());
		List list = monitoringPlanService.findListByMyBatis(NAME_SPACE + "myTaskDistributionList", paramMap);
		req.setAttribute("list", list);
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringPlanMyTaskDistributionSet");
	}	
	
	/**
	 * 我的检测任务  任务分配保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "myTaskDistributionSetSave")
	@ResponseBody
	public AjaxJson myTaskDistributionSetSave(MonitoringTaskDetailsPage monitoringTaskDetailsPage, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		monitoringPlanService.myTaskDistributionSetSave(monitoringTaskDetailsPage);
		j.setSuccess(true);
		message = "保存成功";
		j.setMsg(message);
		return j;
		
	}
	
	/**
	 * 我的检测任务  任务分配情况
	 * 
	 * @return
	 */
	@RequestMapping(params = "myTaskDistributionInfo")
	public void myTaskDistributionInfo(MonitoringPlanEntity monitoringPlan,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		String padId = request.getParameter("padId");
		String taskCode = request.getParameter("taskCode");
		if(ConverterUtil.isNotEmpty(padId) && ConverterUtil.isNotEmpty(taskCode)){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("padId", request.getParameter("padId"));
			paramMap.put("taskCode", request.getParameter("taskCode"));
			dataGrid.setReaults(monitoringPlanService.findListByMyBatis(NAME_SPACE + "myTaskDistributionInfoList", paramMap,dataGrid));	
		}else{
			dataGrid.setTotal(0);
			dataGrid.setReaults(new ArrayList());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 添加检测方案数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "stateUpdate")
	@ResponseBody
	public AjaxJson stateUpdate(MonitoringPlanEntity monitoringPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(monitoringPlan.getId())) {
			message = "更新成功";
			MonitoringPlanEntity t = monitoringPlanService.get(MonitoringPlanEntity.class, monitoringPlan.getId());
			t.setState(monitoringPlan.getState());
			if("1".equals(monitoringPlan.getState())){
				t.setReleasetime(new Date());
			}
			monitoringPlanService.saveOrUpdate(t);
			if ("2".equals(monitoringPlan.getState())) {				
				// 将方案下的项目设置为废止
				systemService.updateByMyBatis(NAME_SPACE+"udpProjectState", t.getPlanCode());
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} 
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 检测任务完成统计列表
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "toStatistics")
	public ModelAndView toStatistics(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后10年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringPlanSampleInquire");
	}
	

	/**
	 * easyui 检测任务完成统计数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "statisticsDatagrid")
	public void statisticsDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String projectCode = request.getParameter("projectCode");
		if(ConverterUtil.isEmpty(projectCode)){
			projectCode = "";
		}
		Map<String,String> modelMap = new HashMap<String,String>();
		modelMap.put("project_code", projectCode);
		modelMap.put("org_name", request.getParameter("orgName"));
		monitoringPlanService.findForStatistics(modelMap, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	

	/**
	 * 附件下载
	 * 
	 * @return
	 */
	@RequestMapping(params = "attachmentDownload")
	public void attachmentDownload(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("UTF-8");
		response.setCharacterEncoding("UTF-8");
		BufferedOutputStream bos = null;
		FileInputStream  bis = null;
		long fileLength = 0;
		// 取得管理工程的工程地址
		String basePath = request.getSession().getServletContext().getRealPath("");
		//String basePath = ResourceUtil.getConfigByName("manage_project_address");
		String path = request.getParameter("url");
		File downloadFile = new File(basePath+path.replace("/", "\\"));
		String[] pathArr = path.split("/");
		String name = pathArr[pathArr.length - 1];
		try {
			bis = new FileInputStream(downloadFile);
			fileLength = downloadFile.length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
			}
		}
		return;
	
	}
	
	/**
	 * 重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isNameCheck")
	@ResponseBody
	public AjaxJson isNameCheck(HttpServletRequest request) {
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		String daiwei = sessioninfo.getUser().getTSDepart().getId();
		AjaxJson j = new AjaxJson();
		j.setMsg(null);
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("name", request.getParameter("param"));
		modelMap.put("releaseunit", daiwei);
		
		int resCount = ConverterUtil.toInteger(systemService.getObjectByMyBatis(NAME_SPACE+"uniquenessCheckPlan", modelMap));
		if(resCount == 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("已存在!");
		}
		if(!j.isSuccess()){
			j.setMsg("方案名称已存在!");
		}
		return j;
	}
	
	/**
	 * 项目名称重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isProjectNameCheck")
	@ResponseBody
	public AjaxJson isProjectNameCheck(HttpServletRequest request) {
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		String daiwei = sessioninfo.getUser().getTSDepart().getId();
		AjaxJson j = new AjaxJson();
		j.setMsg(null);
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("name", request.getParameter("param"));
		modelMap.put("releaseunit", daiwei);
		
		int resCount = ConverterUtil.toInteger(systemService.getObjectByMyBatis(NAME_SPACE+"uniquenessCheckProject", modelMap));
		if(resCount == 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("已存在!");
		}
		if(!j.isSuccess()){
			j.setMsg("项目名称已存在!");
		}
		return j;
	}

}
