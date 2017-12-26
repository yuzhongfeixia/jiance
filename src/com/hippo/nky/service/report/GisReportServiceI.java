package com.hippo.nky.service.report;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

public interface GisReportServiceI extends CommonService{

	/**
	 * 参检农产品分布  选择项目
	 */
	public void selectProject(AjaxJson j, Map<String,Object> modelMap);

	/**
	 * 参检农产品分布  农产品分布检索
	 */
	public void searchBreed(AjaxJson j, Map<String, Object> modelMap);

	/**
	 * 各类工作各类产品区域态势图查询   选择项目
	 */
	public void statisticsDistributionSelectProject(AjaxJson j,
			Map<String, Object> modelMap);

	/**
	 * 各类工作各类产品区域态势图查询   查询完成情况
	 */
	public void statisticsSearchBreed(AjaxJson j, Map<String, Object> modelMap);
	
	/**
	 * 取得参检受检单位情况
	 * 
	 * @param projectCode
	 * @return
	 */
	public List<Map<String, Object>> getDetectedInfo(String projectCode);

	/**
	 * 取得超标受检单位情况
	 * 
	 * @param projectCode
	 * @return
	 */
	public List<Map<String, Object>> getOverPoofInfo(String projectCode);

	/**
	 * 取得抽样任务完成情况
	 * 
	 * @param projectCode
	 * @return
	 */
	public List<Map<String, Object>> getSampleTaskInfo(String projectCode);

	/**
	 * 取得检测任务完成情况
	 * 
	 * @param projectCode
	 * @return
	 */
	public List<Map<String, Object>> getDetectionTaskInfo(String projectCode);

}
