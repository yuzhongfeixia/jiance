package com.hippo.nky.controller.monitoring;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.service.SystemService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
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

import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringDectionTempletEntity;
import com.hippo.nky.entity.monitoring.MonitoringOrganizationEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringSamplelinkEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;
import com.hippo.nky.page.monitoring.MonitoringProjectPage;
import com.hippo.nky.service.monitoring.MonitoringPlanServiceI;
import com.hippo.nky.service.monitoring.MonitoringProjectServiceI;
import com.hippo.nky.service.sample.SamplingInfoServiceI;
import com.hippo.nky.service.standard.AgrCategoryServiceI;
/**   
 * @Title: Controller
 * @Description: 检测项目数据
 * @author nky
 * @date 2013-11-06 17:46:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/monitoringProjectController")
public class MonitoringProjectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MonitoringProjectController.class);

	private static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.MonitoringProjectEntity.";
	
	@Autowired
	private MonitoringProjectServiceI monitoringProjectService;
	@Autowired
	private AgrCategoryServiceI agrCategoryService; 
	@Autowired
	private SystemService systemService;
	@Autowired
	private SamplingInfoServiceI samplingInfoService;
	@Autowired
	private MonitoringPlanServiceI monitoringPlanService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 检测项目数据列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringProject")
	public ModelAndView monitoringProject(HttpServletRequest request) {
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		if("0".equals(sessioninfo.getUser().getUsertype())){
			request.setAttribute("usertype", "0");
		}else{
			request.setAttribute("usertype", "1");
		}
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjectList");
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
	public void datagrid(MonitoringProjectEntity monitoringProject,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		Map<String,Object> mapModel = new HashMap<String,Object>();
		if("0".equals(sessioninfo.getUser().getUsertype())){
			mapModel.put("releaseunit", sessioninfo.getUser().getTSDepart().getId());
		}else{
			mapModel.put("leadunit", sessioninfo.getUser().getOrganization().getCode());
		}
		mapModel.put("userType", sessioninfo.getUser().getUsertype());
		mapModel.put("name", monitoringProject.getName());
		dataGrid.setReaults(monitoringProjectService.findListByMyBatis(NAME_SPACE+"projectList", mapModel, dataGrid));
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除检测项目数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MonitoringProjectEntity monitoringProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		monitoringProject = systemService.getEntity(MonitoringProjectEntity.class, monitoringProject.getId());
		message = "删除成功";
		monitoringProjectService.delete(monitoringProject);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除检测项目数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "statusSave")
	@ResponseBody
	public AjaxJson statusSave(MonitoringProjectEntity monitoringProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		MonitoringProjectEntity t = systemService.getEntity(MonitoringProjectEntity.class, monitoringProject.getId());
		if(t != null){
			if("0".equals(t.getState()) && "1".equals(monitoringProject.getState())){
				t.setState(monitoringProject.getState());
				t.setPublishDate(new Date());
				monitoringProjectService.saveOrUpdate(t);
				j.setSuccess(true);
				message = "发布成功！";
			}else if("1".equals(t.getState()) && "2".equals(monitoringProject.getState())){
				t.setState(monitoringProject.getState());
				monitoringProjectService.saveOrUpdate(t);
				j.setSuccess(true);
				message = "操作成功！";
			}else if("2".equals(t.getState()) && "3".equals(monitoringProject.getState())){
				t.setState(monitoringProject.getState());
				monitoringProjectService.saveOrUpdate(t);
				j.setSuccess(true);
				message = "上报成功！";
			}else if("4".equals(monitoringProject.getState())){
				t.setState(monitoringProject.getState());
				monitoringProjectService.saveOrUpdate(t);
				j.setSuccess(true);
				message = "废止成功！";
			}else{
				j.setSuccess(false);
				message = "无法变更！";
			}
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加检测项目数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MonitoringProjectEntity monitoringProject,MonitoringProjectPage monitoringProjectPage, HttpServletRequest request) {
		initSaveData(monitoringProject, monitoringProjectPage);
		List<MonitoringSamplelinkEntity> monitoringSamplelinkList =  monitoringProjectPage.getMonitoringSamplelinkList();
		List<MonitoringAreaCountEntity> monitoringAreaCountList =  monitoringProjectPage.getMonitoringAreaCountList();
		List<MonitoringBreedEntity> monitoringBreedList =  monitoringProjectPage.getMonitoringBreedList();
		List<MonitoringDectionTempletEntity> monitoringDectionTempletList =  monitoringProjectPage.getMonitoringDectionTempletList();
		List<MonitoringOrganizationEntity> monitoringOrganizationList =  monitoringProjectPage.getMonitoringOrganizationList();
		List<MonitoringTaskEntity> monitoringTaskList =  monitoringProjectPage.getMonitoringTaskList();
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(monitoringProject.getId())) {
			message = "更新成功";
			monitoringProjectService.updateMain(monitoringProject, monitoringSamplelinkList,monitoringAreaCountList,monitoringBreedList,monitoringDectionTempletList,monitoringOrganizationList,monitoringTaskList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "更新失败";
			j.setSuccess(false);
			/*monitoringProjectService.addMain(monitoringProject, monitoringSamplelinkList,monitoringAreaCountList,monitoringBreedList,monitoringDectionTempletList,monitoringOrganizationList,monitoringTaskList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);*/
		}
		j.setMsg(message);
		// 发布时启用线程保存抽样品种
		if("1".equals(monitoringProject.getState())){
			MonitoringProjectEntity t = systemService.getEntity(MonitoringProjectEntity.class, monitoringProject.getId());
			new SaveBreedThread(t.getProjectCode(), samplingInfoService).start();
		}
		return j;
	}

	/**
	 * 设置保存数据
	 * 
	 * @param monitoringProject
	 * @param monitoringProjectPage
	 */
	private void initSaveData(MonitoringProjectEntity monitoringProject,
			MonitoringProjectPage monitoringProjectPage) {
		// 检测环节
		List<MonitoringSamplelinkEntity> newMonitoringSamplelinkList =  new ArrayList<MonitoringSamplelinkEntity>();
		for(MonitoringSamplelinkEntity entity : monitoringProjectPage.getMonitoringSamplelinkList()){
			if(ConverterUtil.isNotEmpty(entity.getMonitoringLink())){
				newMonitoringSamplelinkList.add(entity);
			}
		}
		monitoringProjectPage.setMonitoringSamplelinkList(newMonitoringSamplelinkList);
		
		//地区及数量
		List<MonitoringAreaCountEntity> newMonitoringAreaCountList =   new ArrayList<MonitoringAreaCountEntity>();
		for(MonitoringAreaCountEntity entity : monitoringProjectPage.getMonitoringAreaCountList()){
			if(ConverterUtil.isNotEmpty(entity.getCitycode())){
				// 设置总数量
				String districtcode = entity.getDistrictcode();
				entity.setDistrictcode(null);
				newMonitoringAreaCountList.add(entity);
				
				Map<String,Object> districtMap = ConverterUtil.stringToMap(districtcode);
				for(String key:districtMap.keySet()){//遍历key
					if(ConverterUtil.isNotEmpty(key)){
						MonitoringAreaCountEntity districtEntity = new MonitoringAreaCountEntity();
						districtEntity.setCitycode(entity.getCitycode());
						districtEntity.setDistrictcode(key);
						districtEntity.setCount(ConverterUtil.toInteger(districtMap.get(key)));
						newMonitoringAreaCountList.add(districtEntity);
					}
				}
			}
		}
		monitoringProjectPage.setMonitoringAreaCountList(newMonitoringAreaCountList);
		// 抽样品种
		// 污染物
		List<MonitoringDectionTempletEntity> monitoringDectionTempletList =  new ArrayList<MonitoringDectionTempletEntity>();
		for(MonitoringDectionTempletEntity entity : monitoringProjectPage.getMonitoringDectionTempletList()){
			if(ConverterUtil.isNotEmpty(entity.getAgrCode())){
				// 设置总数量
				Map<String,Object> pollMap = ConverterUtil.stringToMap(entity.getPollCas());
				for(String key:pollMap.keySet()){
					if(ConverterUtil.isNotEmpty(key)){
						MonitoringDectionTempletEntity monitoringDectionTempletEntity = new MonitoringDectionTempletEntity();
						monitoringDectionTempletEntity.setAgrCode(entity.getAgrCode());
						monitoringDectionTempletEntity.setPollCas(key);
						monitoringDectionTempletList.add(monitoringDectionTempletEntity);
					}
				}
			}
		}
		monitoringProjectPage.setMonitoringDectionTempletList(monitoringDectionTempletList);
		
		// 质检机构
		// 任务分配
		List<MonitoringTaskEntity> monitoringTaskList =  new ArrayList<MonitoringTaskEntity>(); 
		for(MonitoringTaskEntity entity : monitoringProjectPage.getMonitoringTaskList()){
			if(ConverterUtil.isNotEmpty(entity.getAgrCode())){
				// 任务名称地区
				Map<String,Object> tasknameMap = ConverterUtil.stringToMap(entity.getTaskname());
				// 抽样地区
				Map<String,Object> areacodeMap = ConverterUtil.stringToMap(entity.getAreacode());
				// 抽样环节
				Map<String,Object> monitoringLinkMap = ConverterUtil.stringToMap(entity.getMonitoringLink());
				// 抽样品种
				Map<String,Object> agrCodeMap = ConverterUtil.stringToMap(entity.getAgrCode());
				// 抽样数量
				Map<String,Object> samplingCountMap = ConverterUtil.stringToMap(entity.getSamplingCounts());
				for(String key:areacodeMap.keySet()){//遍历key
					if(ConverterUtil.isNotEmpty(key)){
						MonitoringTaskEntity monitoringTaskEntity = new MonitoringTaskEntity();
						monitoringTaskEntity.setOrgCode(entity.getOrgCode());
						monitoringTaskEntity.setAgrCode(ConverterUtil.toString(agrCodeMap.get(key)));
						monitoringTaskEntity.setMonitoringLink(ConverterUtil.toString(monitoringLinkMap.get(key)));
						monitoringTaskEntity.setAreacode(ConverterUtil.toString(areacodeMap.get(key)));
						monitoringTaskEntity.setSamplingCount(ConverterUtil.toInteger(samplingCountMap.get(key)));
						monitoringTaskEntity.setTaskname(ConverterUtil.toString(tasknameMap.get(key)));
						monitoringTaskList.add(monitoringTaskEntity);
					}
				}
			}
		}
		monitoringProjectPage.setMonitoringTaskList(monitoringTaskList);
	}

	/**
	 * 检测项目数据列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(monitoringProject.getId())) {
			monitoringProject = monitoringProjectService.getEntity(MonitoringProjectEntity.class, monitoringProject.getId());
			req.setAttribute("monitoringProjectPage", monitoringProject);
		}
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProject");
	}
	
	
	/**
	 * 加载明细列表[检测环节数据]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringSamplelinkList")
	public ModelAndView monitoringSamplelinkList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode0 = monitoringProject.getProjectCode();
		//===================================================================================
		//检测环节数据
	    String hql0 = "from MonitoringSamplelinkEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringSamplelinkEntity> monitoringSamplelinkEntityList = systemService.findHql(hql0,projectCode0);
		req.setAttribute("monitoringSamplelinkList", monitoringSamplelinkEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringSamplelinkList");
	}
	/**
	 * 加载明细列表[监测地区及数量数据]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringAreaCountList")
	public ModelAndView monitoringAreaCountList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode1 = monitoringProject.getProjectCode();
		//===================================================================================
		//监测地区及数量数据
	    String hql1 = "from MonitoringAreaCountEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringAreaCountEntity> monitoringAreaCountEntityList = systemService.findHql(hql1,projectCode1);
		req.setAttribute("monitoringAreaCountList", monitoringAreaCountEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringAreaCountList");
	}
	/**
	 * 加载明细列表[抽样品种数据]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringBreedList")
	public ModelAndView monitoringBreedList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode2 = monitoringProject.getProjectCode();
		//===================================================================================
		//抽样品种数据
	    String hql2 = "from MonitoringBreedEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringBreedEntity> monitoringBreedEntityList = systemService.findHql(hql2,projectCode2);
		req.setAttribute("monitoringBreedList", monitoringBreedEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringBreedList");
	}
	/**
	 * 加载明细列表[检测污染物模板数据]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringDectionTempletList")
	public ModelAndView monitoringDectionTempletList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode3 = monitoringProject.getProjectCode();
		//===================================================================================
		//检测污染物模板数据
	    String hql3 = "from MonitoringDectionTempletEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringDectionTempletEntity> monitoringDectionTempletEntityList = systemService.findHql(hql3,projectCode3);
		req.setAttribute("monitoringDectionTempletList", monitoringDectionTempletEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringDectionTempletList");
	}
	/**
	 * 加载明细列表[项目质检机构关系表]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringOrganizationList")
	public ModelAndView monitoringOrganizationList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode4 = monitoringProject.getProjectCode();
		//===================================================================================
		//项目质检机构关系表
	    String hql4 = "from MonitoringOrganizationEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringOrganizationEntity> monitoringOrganizationEntityList = systemService.findHql(hql4,projectCode4);
		req.setAttribute("monitoringOrganizationList", monitoringOrganizationEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringOrganizationList");
	}
	/**
	 * 加载明细列表[监测任务表]
	 * 
	 * @return
	 */
	@RequestMapping(params = "monitoringTaskList")
	public ModelAndView monitoringTaskList(MonitoringProjectEntity monitoringProject, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object projectCode5 = monitoringProject.getProjectCode();
		//===================================================================================
		//监测任务表
	    String hql5 = "from MonitoringTaskEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringTaskEntity> monitoringTaskEntityList = systemService.findHql(hql5,projectCode5);
		req.setAttribute("monitoringTaskList", monitoringTaskEntityList);
		return new ModelAndView("com/hippo/nky/monitoring/monitoringTaskList");
	}
	
	/**
	 * 检测项目设置页面跳转
	 */
	@RequestMapping(params = "projectSet")
	public ModelAndView projectSet(MonitoringProjectEntity monitoringProject, HttpServletRequest req){
		String data = "{}";
		if (StringUtil.isNotEmpty(monitoringProject.getId())) {
			monitoringProject = monitoringProjectService.getEntity(MonitoringProjectEntity.class, monitoringProject.getId());
			
			if(ConverterUtil.isEmpty(req.getParameter("cid"))){
				monitoringProjectService.projectSet(monitoringProject.getProjectCode(), req);			
			}else{
				MonitoringProjectEntity copyMonitoringProject = monitoringProjectService.getEntity(MonitoringProjectEntity.class, req.getParameter("cid"));
				monitoringProjectService.projectSet(copyMonitoringProject.getProjectCode(), req);
				
				monitoringProject.setSampleTemplet(copyMonitoringProject.getSampleTemplet());
				monitoringProject.setDetached(copyMonitoringProject.getDetached());
				//monitoringProject.setStarttime(copyMonitoringProject.getStarttime());
				//monitoringProject.setEndtime(copyMonitoringProject.getEndtime());
			}
			req.setAttribute("monitoringProjectPage", monitoringProject);
			
			// 查询农产品版本号
			Object agrVersionId = monitoringProjectService.getObjectByMyBatis(NAME_SPACE+"getAgrVersionId", monitoringProject.getProjectCode());
			if(ConverterUtil.isNotEmpty(agrVersionId)){
				data = agrCategoryService.agrCategoryTreeData(ConverterUtil.toString(agrVersionId));
			}
		}
		

		
		// 加载 检测环节数据
		List<TSType> monLinkList = TSTypegroup.allTypes.get(monitoringProject.getIndustryCode() + "monitoringLink".toLowerCase());
		req.setAttribute("monLinkList", monLinkList);
		// 加载行政区划 ： 江苏省
		List<SysAreaCodeEntity> sysAreaCodeList = monitoringProjectService.getSysAreaCodeData("320000", false);
		req.setAttribute("sysAreaCodeList", sysAreaCodeList);
		// 加载最新版农产品信息(树状结构)
		req.setAttribute("zTreeData", data);
		// 加载组织机构
		Map<String,String> getUserDepartInfo  = systemService.getObjectByMyBatis(NAME_SPACE+"getUserDepartInfoForPlanCode", monitoringProject.getPlanCode());
	    //List<OrganizationEntity> organizationEntityList = systemService.findHql("from OrganizationEntity where areacode2 like ? ",getUserDepartInfo.get("gradeCode")+"%");
		List<OrganizationEntity> organizationEntityList = systemService.findListByMyBatis(NAME_SPACE+"getProjectOrganization", getUserDepartInfo.get("gradeCode")+"%");
		req.setAttribute("organizationEntityList", organizationEntityList);
		
		// 方案附件
		Object planCode1 = monitoringProject.getPlanCode();
		req.setAttribute("monitoringAttachmentList", monitoringPlanService.getMonitoringAttachmentList(planCode1));
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjectSetWindow");
	}
	
	/**
	 * 第二步 地区及数量设置
	 */
	@RequestMapping(params = "projectAreaCountSet")
	public ModelAndView projectAreaCountSet(HttpServletRequest req){
		// 市编码
		String code = req.getParameter("code");
		// 已选择的县，市区
		String oldAreaCount = req.getParameter("areaCount");
		// 返回的行数
		String returnRows = req.getParameter("returnRows");
		Map<String,Object> oldAreaCountMap = ConverterUtil.stringToMap(oldAreaCount);
		if(oldAreaCountMap == null){
			oldAreaCountMap = new HashMap<String,Object>();
		}
		// 加载行政区划 ： 江苏省
		List<SysAreaCodeEntity> sysAreaCodeList = monitoringProjectService.getSysAreaCodeData(code, false);
		List<Map<String,Object>> newAreaCountList = new ArrayList<Map<String,Object>>();
		
		// 设置县，市区列表
		int column = sysAreaCodeList.size()/3;
		if(sysAreaCodeList.size()%3 != 0){
			column++;
		}
		for(int i=0;i<sysAreaCodeList.size();i++){
			SysAreaCodeEntity sysAreaCodeEntity = sysAreaCodeList.get(i); 
			Map<String,Object> newMonitoringAreaCountMap = new HashMap<String,Object>();
			newMonitoringAreaCountMap.put("code", sysAreaCodeEntity.getCode());
			newMonitoringAreaCountMap.put("name", sysAreaCodeEntity.getAreaname());
			Object count = oldAreaCountMap.get(sysAreaCodeEntity.getCode());
			if(!"0".equals(count)){
				newMonitoringAreaCountMap.put("count", count);
			}else{
				newMonitoringAreaCountMap.put("count", "");
			}
			if(ConverterUtil.isNotEmpty(count)){
				newMonitoringAreaCountMap.put("checked", "checked");
			}
			newAreaCountList.add(newMonitoringAreaCountMap);
			
			if(column * 2 ==  i+2 && sysAreaCodeList.size()%3 == 1 ){
				newAreaCountList.add(new HashMap<String,Object>());
			}
		}

		req.setAttribute("column", column);
		req.setAttribute("areaCountList", newAreaCountList);
		req.setAttribute("returnRows", returnRows);
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjectAreaCountSetWindow");
	}
	/**
	 * 第四步，污染物设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "projectAreaCountPollSet")
	public ModelAndView projectAreaCountPollSet(HttpServletRequest request) {
		// 取得分类ID
		String returnRows = request.getParameter("returnRows");
		request.setAttribute("returnRows", returnRows);
		String pollArr = request.getParameter("pollArr");
		request.setAttribute("pollArr", pollArr);

		// 查询农产品版本号
		Object pollVersionId = monitoringProjectService.getObjectByMyBatis(NAME_SPACE+"getPollVersionId", request.getParameter("projectCode"));
		request.setAttribute("pollVersionId", pollVersionId);
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("pollVersionId", pollVersionId);
		modelMap.put("industryCode", request.getParameter("industryCode"));
		Object categoryid =  monitoringProjectService.getObjectByMyBatis(NAME_SPACE+"getCategoryId", modelMap);
		request.setAttribute("categoryid", categoryid);
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjecPollChooseWindow");
	}
	
	/**
	 * 第七步 任务设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "projectTaskSet")
	public ModelAndView projectTaskSet(HttpServletRequest request) {
		// 下拉框数据
		String returnRows = request.getParameter("returnRows");
		request.setAttribute("returnRows", returnRows);
		String samplelinkStr = request.getParameter("samplelinkStr");
		request.setAttribute("samplelinkStr", samplelinkStr);
		String breedStr = request.getParameter("breedStr");
		request.setAttribute("breedStr", breedStr);
		String areaCityStr = request.getParameter("areaCityStr");
		request.setAttribute("areaCityStr", areaCityStr);
		
		// 取得一行检测机构的任务信息
		int infoCount = Integer.parseInt(request.getParameter("infoCount"));
		Map<String,Object> samplingCountMap = ConverterUtil.stringToMap(request.getParameter("samplingCount"));
		Map<String,Object> areacodeMap = ConverterUtil.stringToMap(request.getParameter("areacode"));
		Map<String,Object> monitoringLinkMap = ConverterUtil.stringToMap(request.getParameter("monitoringLink"));
		Map<String,Object> agrCodeMap = ConverterUtil.stringToMap(request.getParameter("agrCode"));
		
		// 变更成表格
		List<Map<String,Object>> taskInfoList = new ArrayList<Map<String,Object>>();
		for(int i=0; i < infoCount ;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			String istr  = String.valueOf(i);
			map.put("samplingCount", samplingCountMap.get(istr));
			map.put("areacode", areacodeMap.get(istr));
			map.put("monitoringLink", monitoringLinkMap.get(istr));
			map.put("agrCode", agrCodeMap.get(istr));
			taskInfoList.add(map);
		}
		request.setAttribute("taskInfoList", taskInfoList);
		
		return new ModelAndView("com/hippo/nky/monitoring/monitoringProjectTaskSetWindow");
	}
	
	/**
	 * 复制项目 项目选择
	 * 
	 * @return
	 */
	@RequestMapping(params = "projectSelectEdit")
	@ResponseBody
	public AjaxJson projectSelectEdit(HttpServletRequest request) {
		
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributeMap = new HashMap<String, Object>(); 
		// 编码
		String code = ConverterUtil.toString(request.getAttribute("code"));
		if (ConverterUtil.isEmpty(code)) {
			j.setSuccess(false);
			return j;
		}
		// 取得登陆的单位id
		SessionInfo sessioninfo =(SessionInfo) request.getSession().getAttribute(Globals.USER_SESSION);
		String releaseunit = sessioninfo.getUser().getOrganization().getCode();
		// 项目列表
		String hql0 = "select t1 from MonitoringProjectEntity t1 ,MonitoringPlanEntity t2 where 1 = 1 AND t1.state > 0 AND t1.state < 4 AND t1.planCode = t2.planCode and t1.leadunit = ? and t2.type = ? "; 
	    List<MonitoringProjectEntity> monitoringProjectEntityList = systemService.findHql(hql0,releaseunit,code);
		StringBuffer st = new StringBuffer();
		// 拼接下拉框
		for (MonitoringProjectEntity monitoringProjectEntity : monitoringProjectEntityList) {
			st.append("<option value=\"" + monitoringProjectEntity.getId() + "\">");
			st.append(monitoringProjectEntity.getName());
			st.append("</option>");
		}
		
		j.setSuccess(true);
		attributeMap.put("data", st.toString());
		j.setAttributes(attributeMap);
		
		return j;
		
	}
	
	/**
	 * 取得项目任务完成情况
	 * 
	 * @return
	 */
	@RequestMapping(params = "projectStatistical")
	@ResponseBody
	public AjaxJson projectStatistical(MonitoringProjectEntity monitoringProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributeMap = new HashMap<String, Object>(); 
		Map<String,Object> mapModel = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(monitoringProject.getId())) {
			mapModel.put("id", monitoringProject.getId());
		} else if (StringUtils.isNotEmpty(monitoringProject.getProjectCode())) {
			mapModel.put("projectCode", monitoringProject.getProjectCode());
		}
		
		List<MonitoringProjectEntity> infoList = monitoringProjectService.findListByMyBatis(NAME_SPACE+"projectStatistical", mapModel);
		attributeMap.put("projectStatisticalInfo", infoList.get(0));
		j.setAttributes(attributeMap);
		return j;
	}
	
	/**
	 * 取得抽样上报抽样完成情况
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingComleteStatistical")
	@ResponseBody
	public AjaxJson samplingComleteStatistical(MonitoringProjectEntity monitoringProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributeMap = new HashMap<String, Object>(); 
		Map<String,Object> mapModel = new HashMap<String,Object>();
		mapModel.put("projectCode", monitoringProject.getProjectCode());
		OrganizationEntity org = systemService.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		mapModel.put("ogrCode", org.getCode());
		List<MonitoringProjectEntity> infoList = monitoringProjectService.findListByMyBatis(NAME_SPACE+"samplingComleteStatistical", mapModel);
		attributeMap.put("projectStatisticalInfo", infoList.get(0));
		j.setAttributes(attributeMap);
		return j;
	}
	
	/**
	 * 取得检测信息上报检测数据完成情况
	 * 
	 * @return
	 */
	@RequestMapping(params = "detectionComleteStatistical")
	@ResponseBody
	public AjaxJson detectionComleteStatistical(MonitoringProjectEntity monitoringProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributeMap = new HashMap<String, Object>(); 
		Map<String,Object> mapModel = new HashMap<String,Object>();
		mapModel.put("projectCode", monitoringProject.getProjectCode());
		OrganizationEntity org = systemService.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		mapModel.put("ogrCode", org.getCode());
		List<MonitoringProjectEntity> infoList = monitoringProjectService.findListByMyBatis(NAME_SPACE+"detectionComleteStatistical", mapModel);
		attributeMap.put("projectStatisticalInfo", infoList.get(0));
		j.setAttributes(attributeMap);
		return j;
	}
	
}


