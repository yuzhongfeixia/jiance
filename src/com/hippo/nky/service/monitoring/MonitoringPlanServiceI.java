package com.hippo.nky.service.monitoring;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.common.SessionInfo;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import com.hippo.nky.entity.monitoring.MonitoringPlanEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringAttachmentEntity;
import com.hippo.nky.page.monitoring.MonitoringTaskDetailsPage;

public interface MonitoringPlanServiceI extends CommonService{
	
	/**
	 * 删除检测方案数据
	 * 
	 * @return
	 */
	public void del(MonitoringPlanEntity monitoringPlan);
	
	/**
	 * 检测方案管理  列表数据
	 * 
	 * @return
	 */
	public void datagrid(MonitoringPlanEntity monitoringPlan, DataGrid dataGrid);
	
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(MonitoringPlanEntity monitoringPlan,
	        List<MonitoringProjectEntity> monitoringProjectList,List<MonitoringAttachmentEntity> monitoringAttachmentList, SessionInfo sessioninfo) ;
	/**
	 * 修改一对多
	 * @param sessioninfo 
	 * 
	 */
	public void updateMain(MonitoringPlanEntity monitoringPlan,
	        List<MonitoringProjectEntity> monitoringProjectList,List<MonitoringAttachmentEntity> monitoringAttachmentList);
	public void delMain (MonitoringPlanEntity monitoringPlan);
	
	/**
	 * 取得方案附件列表
	 * @param monitoringPlanCode 方案编号
	 * @return 方案附件列表
	 */
	public List<MonitoringAttachmentEntity> getMonitoringAttachmentList(Object monitoringPlanCode);
	
	/**
	 * easyui 检测任务完成统计数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	public void findForStatistics(Map<String, String> modelMap, DataGrid dataGrid);
	
	/**
	 * 我的检测任务管理页面跳转
	 * 
	 * @return
	 */
	public Object getCityAndCountryList(Map<String,Object> pageObj,int i);
	
	/**
	 * AJAX请求数据 我的检测任务数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	public void myTaskDatagrid(HttpServletRequest request, DataGrid dataGrid);
	
	/**
	 * 我的检测任务  任务分配保存
	 * 
	 * @return
	 */
	public void myTaskDistributionSetSave(
			MonitoringTaskDetailsPage monitoringTaskDetailsPage);


}
