package com.hippo.nky.service.monitoring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringSamplelinkEntity;
import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringDectionTempletEntity;
import com.hippo.nky.entity.monitoring.MonitoringOrganizationEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;

public interface MonitoringProjectServiceI extends CommonService{

	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(MonitoringProjectEntity monitoringProject,
	        List<MonitoringSamplelinkEntity> monitoringSamplelinkList,List<MonitoringAreaCountEntity> monitoringAreaCountList,List<MonitoringBreedEntity> monitoringBreedList,List<MonitoringDectionTempletEntity> monitoringDectionTempletList,List<MonitoringOrganizationEntity> monitoringOrganizationList,List<MonitoringTaskEntity> monitoringTaskList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MonitoringProjectEntity monitoringProject,
	        List<MonitoringSamplelinkEntity> monitoringSamplelinkList,List<MonitoringAreaCountEntity> monitoringAreaCountList,List<MonitoringBreedEntity> monitoringBreedList,List<MonitoringDectionTempletEntity> monitoringDectionTempletList,List<MonitoringOrganizationEntity> monitoringOrganizationList,List<MonitoringTaskEntity> monitoringTaskList);
	public void delMain (MonitoringProjectEntity monitoringProject);
	
	/**
	 * 项目设置数据读取
	 * 
	 * @param monitoringProject
	 * @param req
	 */
	public void projectSet(String projectCode,
			HttpServletRequest req);
	

}
