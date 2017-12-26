package com.hippo.nky.service.impl.monitoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jeecg.system.pojo.base.TSUser;

import org.jeecgframework.core.common.model.common.SessionInfo;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.MonitoringAttachmentEntity;
import com.hippo.nky.entity.monitoring.MonitoringPlanEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskDetailsEntity;
import com.hippo.nky.page.monitoring.MonitoringTaskDetailsPage;
import com.hippo.nky.service.monitoring.MonitoringPlanServiceI;
@Service("monitoringPlanService")
@Transactional
public class MonitoringPlanServiceImpl extends CommonServiceImpl implements MonitoringPlanServiceI {

	private static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.MonitoringPlanEntity.";
	
	/**
	 * 删除检测方案数据
	 * 
	 * @return
	 */
	public void del(MonitoringPlanEntity monitoringPlan){
		monitoringPlan = this.getEntity(MonitoringPlanEntity.class, monitoringPlan.getId());
		if(monitoringPlan != null){
			String hql0 = "from MonitoringProjectEntity where 1 = 1 AND planCode = ? ";
			List<MonitoringProjectEntity> monitoringProjectEntityList = this.findHql(hql0,monitoringPlan.getPlanCode());
			String hql1 = "from MonitoringAttachmentEntity where 1 = 1 AND planCode = ? ";
		    List<MonitoringAttachmentEntity> monitoringAttachmentEntityList = this.findHql(hql1,monitoringPlan.getPlanCode());
		    this.deleteAllEntitie(monitoringProjectEntityList);
		    this.deleteAllEntitie(monitoringAttachmentEntityList);
		    this.delete(monitoringPlan);
		}
	}
	/**
	 * 检测方案管理  列表数据
	 * 
	 * @return
	 */
	@Override
	public void datagrid(MonitoringPlanEntity monitoringPlan, DataGrid dataGrid){
		//this.getObjectByMyBatis(NAME_SPACE+"getUserDepartInfo", sessioninfo.getUser().getId());
		dataGrid.setReaults(this.findListByMyBatis(NAME_SPACE+"findPlanList", monitoringPlan,dataGrid));
	}
	
	@Override
	public void addMain(MonitoringPlanEntity monitoringPlan,
	        List<MonitoringProjectEntity> monitoringProjectList,List<MonitoringAttachmentEntity> monitoringAttachmentList,SessionInfo sessioninfo){
			//保存主信息
			Map<String,String> getUserDepartInfo  = this.getObjectByMyBatis(NAME_SPACE+"getUserDepartInfo", sessioninfo.getUser().getId());
			monitoringPlan.setState("0");
			monitoringPlan.setPlevel(getUserDepartInfo.get("grade"));
			monitoringPlan.setReleaseunit(getUserDepartInfo.get("id"));
			monitoringPlan.setPlanCode(getUidFromOracle());
			this.save(monitoringPlan);
		
			/**保存-检测项目数据*/
			for(MonitoringProjectEntity monitoringProject:monitoringProjectList){
				//外键设置
				monitoringProject.setProjectCode(getUidFromOracle());
				monitoringProject.setState("0");
				monitoringProject.setPlanCode(monitoringPlan.getPlanCode());
				this.save(monitoringProject);
			}
			/**保存-方案附件*/
			for(MonitoringAttachmentEntity monitoringAttachment:monitoringAttachmentList){
				//外键设置
				monitoringAttachment.setPlanCode(monitoringPlan.getPlanCode());
				this.save(monitoringAttachment);
			}
	}

	@Override
	public void updateMain(MonitoringPlanEntity monitoringPlan,
	        List<MonitoringProjectEntity> monitoringProjectList,List<MonitoringAttachmentEntity> monitoringAttachmentList) {
		//保存订单主信息
		this.saveOrUpdate(monitoringPlan);
		
		
		//===================================================================================
		//获取参数
		Object planCode0 = monitoringPlan.getPlanCode();
		Object planCode1 = monitoringPlan.getPlanCode();
		//===================================================================================
		//删除-检测项目数据
	    String hql0 = "from MonitoringProjectEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringProjectEntity> monitoringProjectOldList = this.findHql(hql0,planCode0);
		this.deleteAllEntitie(monitoringProjectOldList);
		//保存-检测项目数据
		for(MonitoringProjectEntity monitoringProject:monitoringProjectList){
			//外键设置
			monitoringProject.setProjectCode(getUidFromOracle());
			monitoringProject.setState("0");
			monitoringProject.setPlanCode(monitoringPlan.getPlanCode());
			this.save(monitoringProject);
		}
		//===================================================================================
		//删除-方案附件
	    String hql1 = "from MonitoringAttachmentEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringAttachmentEntity> monitoringAttachmentOldList = this.findHql(hql1,planCode1);
		this.deleteAllEntitie(monitoringAttachmentOldList);
		//保存-方案附件
		for(MonitoringAttachmentEntity monitoringAttachment:monitoringAttachmentList){
			//外键设置
			monitoringAttachment.setPlanCode(monitoringPlan.getPlanCode());
			this.save(monitoringAttachment);
		}
		
	}

	@Override
	public void delMain(MonitoringPlanEntity monitoringPlan) {
		//删除主表信息
		this.delete(monitoringPlan);
		
		//===================================================================================
		//获取参数
		Object planCode0 = monitoringPlan.getPlanCode();
		Object planCode1 = monitoringPlan.getPlanCode();
		//===================================================================================
		//删除-检测项目数据
	    String hql0 = "from MonitoringProjectEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringProjectEntity> monitoringProjectOldList = this.findHql(hql0,planCode0);
		this.deleteAllEntitie(monitoringProjectOldList);
		//===================================================================================
		//删除-方案附件
	    String hql1 = "from MonitoringAttachmentEntity where 1 = 1 AND planCode = ? ";
	    List<MonitoringAttachmentEntity> monitoringAttachmentOldList = this.findHql(hql1,planCode1);
		this.deleteAllEntitie(monitoringAttachmentOldList);
	}
	
	/**
	 * 取得方案附件列表
	 * @param monitoringPlanCode 方案编号
	 * @return 方案附件列表
	 */
	@Override
	public List<MonitoringAttachmentEntity> getMonitoringAttachmentList(Object monitoringPlanCode){
		//方案附件
	    String hql1 = "from MonitoringAttachmentEntity where 1 = 1 AND planCode = ? ";
	    return this.findHql(hql1,monitoringPlanCode);
	}
	
	
	/**
	 * 检测任务完成统计数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@Override
	public void findForStatistics(Map<String, String> modelMap, DataGrid dataGrid) {
		
		TSUser user = ResourceUtil.getSessionUserName();
		if("0".equals(user.getUsertype())){
			modelMap.put("releaseunit", user.getTSDepart().getId());
		}else{
			modelMap.put("leadunit", user.getOrganization().getCode());
		}
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"findForStatistics", modelMap);
		dataGrid.setReaults(mapList);
	}
	
	/**
	 * 取得对应项目中的地市或区县的下拉框
	 * @param pageObj
	 * @param isCity 是否是地市
	 * @return
	 */
	@Override
	public String getCityAndCountryList(Map<String,Object> pageObj,int isCity) {
		List<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();
		if(1 == isCity){
			codeList = this.findListByMyBatis(NAME_SPACE + "getCityCodeList", pageObj);
		} else {
			codeList = this.findListByMyBatis(NAME_SPACE + "getCountryCodeList", pageObj);
		}
		
		StringBuffer result = new StringBuffer();
		for (Map<String, Object> map : codeList) {
			result.append(map.get("code"));
			result.append(ConverterUtil.SEPARATOR_KEY_VALUE);
			result.append(map.get("areaName"));
			result.append(ConverterUtil.SEPARATOR_ELEMENT);
		}

		return result.toString();
	}
	
	/**
	 * AJAX请求数据 我的检测任务数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	public void myTaskDatagrid(HttpServletRequest request, DataGrid dataGrid){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("projectCode", request.getParameter("projectCode"));
		modelMap.put("taskName", request.getParameter("taskName"));
		modelMap.put("agrName", request.getParameter("agrName"));
		modelMap.put("cityCode", request.getParameter("cityCode"));
		modelMap.put("countyCode", request.getParameter("countyCode"));
		TSUser user = ResourceUtil.getSessionUserName();
		if("0".equals(user.getUsertype())){
			modelMap.put("orgCode", "-");
		}else{
			modelMap.put("orgCode", user.getOrganization().getCode());
		}
		//modelMap.put("beginIndex", Integer.parseInt(dataGrid.getAoDataMap().get("iDisplayStart")));
		//modelMap.put("endIndex", Integer.parseInt(dataGrid.getAoDataMap().get("iDisplayStart")) + Integer.parseInt(dataGrid.getAoDataMap().get("iDisplayLength")));
		//dataGrid.setTotal(ConverterUtil.toInteger(this.getObjectByMyBatis(NAME_SPACE + "myTaskCount", modelMap)));
		//dataGrid.setReaults(this.findListByMyBatis(NAME_SPACE + "myTaskList", modelMap));
		dataGrid.setReaults(this.findListByMyBatis(NAME_SPACE + "myTaskList", modelMap, dataGrid));
	}
	
	/**
	 * 我的检测任务  任务分配保存
	 * 
	 * @return
	 */
	public void myTaskDistributionSetSave(
			MonitoringTaskDetailsPage monitoringTaskDetailsPage){
		if(monitoringTaskDetailsPage.getMonitoringTaskDetailsList() != null){
			for(MonitoringTaskDetailsEntity monitoringTaskDetailsEntity:monitoringTaskDetailsPage.getMonitoringTaskDetailsList()){
				Integer taskCount = monitoringTaskDetailsEntity.getTaskCount();
				if(ConverterUtil.isNotEmpty(monitoringTaskDetailsEntity.getId())){
					MonitoringTaskDetailsEntity t = this.get(MonitoringTaskDetailsEntity.class, monitoringTaskDetailsEntity.getId());
					if(taskCount != null && taskCount > 0){
						t.setTaskCount(monitoringTaskDetailsEntity.getTaskCount());
						t.setTaskStatus("0");
						t.setAssignTime(new Date());
						this.saveOrUpdate(t);	
					}else{
						this.delete(t);
					}
				}else{
					if(taskCount != null && taskCount > 0){
						monitoringTaskDetailsEntity.setTaskStatus("0");
						monitoringTaskDetailsEntity.setAssignTime(new Date());
						this.save(monitoringTaskDetailsEntity);	
					}
				}
				
			}
		}
		
	}
}