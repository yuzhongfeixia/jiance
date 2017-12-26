package com.hippo.nky.service.impl.monitoring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringDectionTempletEntity;
import com.hippo.nky.entity.monitoring.MonitoringOrganizationEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringSamplelinkEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.service.monitoring.MonitoringProjectServiceI;
@Service("monitoringProjectService")
@Transactional
public class MonitoringProjectServiceImpl extends CommonServiceImpl implements MonitoringProjectServiceI {

	private static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.MonitoringProjectEntity.";
	@Override
	public void addMain(MonitoringProjectEntity monitoringProject,
	        List<MonitoringSamplelinkEntity> monitoringSamplelinkList,List<MonitoringAreaCountEntity> monitoringAreaCountList,List<MonitoringBreedEntity> monitoringBreedList,List<MonitoringDectionTempletEntity> monitoringDectionTempletList,List<MonitoringOrganizationEntity> monitoringOrganizationList,List<MonitoringTaskEntity> monitoringTaskList){
			//保存主信息
			
			// 设置外键值
			monitoringProject.setProjectCode(getUidFromOracle());
			this.save(monitoringProject);
		
			/**保存-检测环节数据*/
			for(MonitoringSamplelinkEntity monitoringSamplelink:monitoringSamplelinkList){
				//外键设置
				monitoringSamplelink.setProjectCode(monitoringProject.getProjectCode());
				this.save(monitoringSamplelink);
			}
			/**保存-监测地区及数量数据*/
			for(MonitoringAreaCountEntity monitoringAreaCount:monitoringAreaCountList){
				//外键设置
				monitoringAreaCount.setProjectCode(monitoringProject.getProjectCode());
				this.save(monitoringAreaCount);
			}
			/**保存-抽样品种数据*/
			for(MonitoringBreedEntity monitoringBreed:monitoringBreedList){
				//外键设置
				monitoringBreed.setProjectCode(monitoringProject.getProjectCode());
				this.save(monitoringBreed);
			}
			/**保存-检测污染物模板数据*/
			for(MonitoringDectionTempletEntity monitoringDectionTemplet:monitoringDectionTempletList){
				//外键设置
				monitoringDectionTemplet.setProjectCode(monitoringProject.getProjectCode());
				this.save(monitoringDectionTemplet);
			}
			/**保存-项目质检机构关系表*/
			for(MonitoringOrganizationEntity monitoringOrganization:monitoringOrganizationList){
				//外键设置
				monitoringOrganization.setProjectCode(monitoringProject.getProjectCode());
				this.save(monitoringOrganization);
			}
			/**保存-监测任务表*/
			for(MonitoringTaskEntity monitoringTask:monitoringTaskList){
				//外键设置
				monitoringTask.setProjectCode(monitoringProject.getProjectCode());
				monitoringTask.setTaskcode(this.getUidFromOracle());
				this.save(monitoringTask);
			}
	}

	@Override
	public void updateMain(MonitoringProjectEntity monitoringProjectEntity,
	        List<MonitoringSamplelinkEntity> monitoringSamplelinkList,List<MonitoringAreaCountEntity> monitoringAreaCountList,List<MonitoringBreedEntity> monitoringBreedList,List<MonitoringDectionTempletEntity> monitoringDectionTempletList,List<MonitoringOrganizationEntity> monitoringOrganizationList,List<MonitoringTaskEntity> monitoringTaskList) {
		//保存订单主信息
		MonitoringProjectEntity t = this.get(MonitoringProjectEntity.class, monitoringProjectEntity.getId());
		if("0".equals(t.getState()) && "1".equals(monitoringProjectEntity.getState())){
			monitoringProjectEntity.setPublishDate(new Date());
		}
		try {
			MyBeanUtils.copyBeanNotNull2Bean(monitoringProjectEntity, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.saveOrUpdate(t);
		
		//===================================================================================
		//获取参数
		Object projectCode0 = t.getProjectCode();
		Object projectCode1 = t.getProjectCode();
		Object projectCode2 = t.getProjectCode();
		Object projectCode3 = t.getProjectCode();
		Object projectCode4 = t.getProjectCode();
		Object projectCode5 = t.getProjectCode();
		//===================================================================================
	    String hql0 = "from MonitoringSamplelinkEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringSamplelinkEntity> monitoringSamplelinkOldList = this.findHql(hql0,projectCode0);
	    //删除-检测环节数据
		this.deleteAllEntitie(monitoringSamplelinkOldList);
		//保存-检测环节数据
		for(MonitoringSamplelinkEntity monitoringSamplelink:monitoringSamplelinkList){
			//外键设置
			monitoringSamplelink.setProjectCode(t.getProjectCode());
			this.save(monitoringSamplelink);
		}
		//===================================================================================
	    String hql1 = "from MonitoringAreaCountEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringAreaCountEntity> monitoringAreaCountOldList = this.findHql(hql1,projectCode1);
	    //删除-监测地区及数量数据
		this.deleteAllEntitie(monitoringAreaCountOldList);
		//保存-监测地区及数量数据
		for(MonitoringAreaCountEntity monitoringAreaCount:monitoringAreaCountList){
			//外键设置
			monitoringAreaCount.setProjectCode(t.getProjectCode());
			this.save(monitoringAreaCount);
		}
		//===================================================================================
	    String hql2 = "from MonitoringBreedEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringBreedEntity> monitoringBreedOldList = this.findHql(hql2,projectCode2);
	    //删除-抽样品种数据
		this.deleteAllEntitie(monitoringBreedOldList);
		//保存-抽样品种数据
		for(MonitoringBreedEntity monitoringBreed:monitoringBreedList){
			//外键设置
			monitoringBreed.setProjectCode(t.getProjectCode());
			this.save(monitoringBreed);
		}
		//===================================================================================
	    String hql3 = "from MonitoringDectionTempletEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringDectionTempletEntity> monitoringDectionTempletOldList = this.findHql(hql3,projectCode3);
	    //删除-检测污染物模板数据
		this.deleteAllEntitie(monitoringDectionTempletOldList);
		//保存-检测污染物模板数据
		for(MonitoringDectionTempletEntity monitoringDectionTemplet:monitoringDectionTempletList){
			//外键设置
			monitoringDectionTemplet.setProjectCode(t.getProjectCode());
			this.save(monitoringDectionTemplet);
		}
		//===================================================================================
	    String hql4 = "from MonitoringOrganizationEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringOrganizationEntity> monitoringOrganizationOldList = this.findHql(hql4,projectCode4);
	    //删除-项目质检机构关系表
		this.deleteAllEntitie(monitoringOrganizationOldList);
		//保存-项目质检机构关系表
		for(MonitoringOrganizationEntity monitoringOrganization:monitoringOrganizationList){
			//外键设置
			monitoringOrganization.setProjectCode(t.getProjectCode());
			this.save(monitoringOrganization);
		}
		//===================================================================================
	    String hql5 = "from MonitoringTaskEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringTaskEntity> monitoringTaskOldList = this.findHql(hql5,projectCode5);
	    //删除-监测任务表
		this.deleteAllEntitie(monitoringTaskOldList);
		//保存-监测任务表
		for(MonitoringTaskEntity monitoringTask:monitoringTaskList){
			//外键设置
			monitoringTask.setProjectCode(t.getProjectCode());
			monitoringTask.setTaskcode(this.getUidFromOracle());
			this.save(monitoringTask);
		}
		
	}

	@Override
	public void delMain(MonitoringProjectEntity monitoringProject) {
		//删除主表信息
		this.delete(monitoringProject);
		
		//===================================================================================
		//获取参数
		Object projectCode0 = monitoringProject.getProjectCode();
		Object projectCode1 = monitoringProject.getProjectCode();
		Object projectCode2 = monitoringProject.getProjectCode();
		Object projectCode3 = monitoringProject.getProjectCode();
		Object projectCode4 = monitoringProject.getProjectCode();
		Object projectCode5 = monitoringProject.getProjectCode();
		//===================================================================================
		//删除-检测环节数据
	    String hql0 = "from MonitoringSamplelinkEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringSamplelinkEntity> monitoringSamplelinkOldList = this.findHql(hql0,projectCode0);
		this.deleteAllEntitie(monitoringSamplelinkOldList);
		//===================================================================================
		//删除-监测地区及数量数据
	    String hql1 = "from MonitoringAreaCountEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringAreaCountEntity> monitoringAreaCountOldList = this.findHql(hql1,projectCode1);
		this.deleteAllEntitie(monitoringAreaCountOldList);
		//===================================================================================
		//删除-抽样品种数据
	    String hql2 = "from MonitoringBreedEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringBreedEntity> monitoringBreedOldList = this.findHql(hql2,projectCode2);
		this.deleteAllEntitie(monitoringBreedOldList);
		//===================================================================================
		//删除-检测污染物模板数据
	    String hql3 = "from MonitoringDectionTempletEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringDectionTempletEntity> monitoringDectionTempletOldList = this.findHql(hql3,projectCode3);
		this.deleteAllEntitie(monitoringDectionTempletOldList);
		//===================================================================================
		//删除-项目质检机构关系表
	    String hql4 = "from MonitoringOrganizationEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringOrganizationEntity> monitoringOrganizationOldList = this.findHql(hql4,projectCode4);
		this.deleteAllEntitie(monitoringOrganizationOldList);
		//===================================================================================
		//删除-监测任务表
	    String hql5 = "from MonitoringTaskEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringTaskEntity> monitoringTaskOldList = this.findHql(hql5,projectCode5);
		this.deleteAllEntitie(monitoringTaskOldList);
	}
	
	/**
	 * 项目设置数据读取
	 * 
	 * @param projectCode 获取参数
	 * @param req
	 */
	public void projectSet(String projectCode,
			HttpServletRequest req){

		//检测环节数据
	    String hql0 = "from MonitoringSamplelinkEntity where 1 = 1 AND projectCode = ? ";
	    List<MonitoringSamplelinkEntity> monitoringSamplelinkEntityList = this.findHql(hql0,projectCode);
		//req.setAttribute("monitoringSamplelinkList", monitoringSamplelinkEntityList);
		Map<String,Object> monitoringSamplelinkMap = new HashMap<String,Object>();
		for(MonitoringSamplelinkEntity monitoringSamplelinkEntity: monitoringSamplelinkEntityList){
			monitoringSamplelinkMap.put(monitoringSamplelinkEntity.getMonitoringLink(), "checked");
		}
		req.setAttribute("monitoringSamplelinkMap", monitoringSamplelinkMap);

		//监测地区及数量数据
	    List<Map<String,Object>> monitoringAreaCountEntityList = this.findListByMyBatis(NAME_SPACE+"monitoringAreaCountList", projectCode);
	    req.setAttribute("monitoringAreaCountList", monitoringAreaCountEntityList);
		
		//抽样品种数据
	    List<Map<String,Object>> monitoringBreedEntityList = this.findListByMyBatis(NAME_SPACE+"monitoringBreedList", projectCode);
		req.setAttribute("monitoringBreedList", monitoringBreedEntityList);
		
		//检测污染物模板数据
	    List<Map<String,Object>> monitoringDectionTempletEntityList = this.findListByMyBatis(NAME_SPACE+"monitoringDectionTempletList", projectCode);
		req.setAttribute("monitoringDectionTempletList", monitoringDectionTempletEntityList);
		
		//项目质检机构关系表
	    List<Map<String,Object>> monitoringOrganizationEntityList = this.findListByMyBatis(NAME_SPACE+"monitoringOrganizationList", projectCode);
		req.setAttribute("monitoringOrganizationList", monitoringOrganizationEntityList);
		
		//监测任务表
	    List<Map<String,Object>> monitoringTaskEntityList = this.findListByMyBatis(NAME_SPACE+"monitoringTaskList", projectCode);
		req.setAttribute("monitoringTaskList", monitoringTaskEntityList);
		
	}

}
