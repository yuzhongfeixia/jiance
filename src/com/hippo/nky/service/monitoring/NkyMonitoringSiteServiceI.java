package com.hippo.nky.service.monitoring;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;

public interface NkyMonitoringSiteServiceI extends CommonService{
	public List<Map<String, Object>> exportExcelForMonitoringSite(Map<String, Object> paramMap);
	
	/**
	 * 保存数据到数据库模板文件
	 * @param entity
	 */
	public void saveDataToTemplate(NkyMonitoringSiteEntity entity);
	
	/**
	 * 更新数据到数据库模板文件
	 * @param entity
	 */
	public void updateDataForTemplate(NkyMonitoringSiteEntity entity);
	
	/**
	 * 删除数据到数据库模板文件
	 * @param entity
	 */
	public void deleteDataForTemplate(NkyMonitoringSiteEntity entity);
	
}
