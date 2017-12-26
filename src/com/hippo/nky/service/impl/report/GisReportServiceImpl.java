package com.hippo.nky.service.impl.report;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.report.GisReportServiceI;

@Service("gisReportService")
@Transactional
public class GisReportServiceImpl extends CommonServiceImpl implements GisReportServiceI {
	private static final String NAME_SPACE = "com.hippo.nky.entity.report.GisReportEntity.";
	/**
	 * 参检农产品分布  选择农产品
	 */
	@Override
	public void selectProject(AjaxJson j, Map<String,Object> modelMap){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		List list1 = this.findListByMyBatis(NAME_SPACE+"monitoringLinkList", modelMap);
		List list2 = this.findListByMyBatis(NAME_SPACE+"monitoringBreedList", modelMap);
		reqMap.put("monitoringLinkList", list1);
		reqMap.put("monitoringBreedList", list2);
		j.setSuccess(true);
		j.setAttributes(reqMap);
		
	}
	
	/**
	 * 参检农产品分布  农产品分布检索
	 */
	@Override
	public void searchBreed(AjaxJson j, Map<String, Object> modelMap) {
		Map<String,Object> reqMap = new HashMap<String,Object>();
		// 设置权限
		setUserDataPriv(ConverterUtil.toString(modelMap.get("projectCode")), modelMap);
		List list1 = this.findListByMyBatis(NAME_SPACE+"searchBreedList", modelMap);
		reqMap.put("searchBreedList", list1);
		j.setSuccess(true);
		j.setAttributes(reqMap);
		
	}

	/**
	 * 各类工作各类产品区域态势图查询   选择项目
	 */
	@Override
	public void statisticsDistributionSelectProject(AjaxJson j,
			Map<String, Object> modelMap) {
		Map<String,Object> reqMap = new HashMap<String,Object>();
		// 设置权限
		setUserDataPriv(ConverterUtil.toString(modelMap.get("projectCode")), modelMap);
		List list1 = this.findListByMyBatis(NAME_SPACE+"monitoringLinkList", modelMap);
		List list2 = this.findListByMyBatis(NAME_SPACE+"monitoringBreedList", modelMap);
		List list3 = this.findListByMyBatis(NAME_SPACE+"monitoringPollList", modelMap);
		reqMap.put("monitoringLinkList", list1);
		reqMap.put("monitoringBreedList", list2);
		reqMap.put("monitoringPollList", list3);
		j.setSuccess(true);
		j.setAttributes(reqMap);
		
	}

	/**
	 * 各类工作各类产品区域态势图查询   查询完成情况
	 */
	@Override
	public void statisticsSearchBreed(AjaxJson j, Map<String, Object> modelMap) {
		Map<String,Object> reqMap = new HashMap<String,Object>();
		//List list1 = this.findListByMyBatis(NAME_SPACE+"statisticsSearchBreedList", modelMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "statisticsSearchBreedList", modelMap);
		String htmls = "";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLING_COUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVER_STANDERD_COUNT"));
			datamap.put("QUALIFIED_RATE_100", df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount)))));
			datamap.put("QUALIFIED_RATE", df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%");	
		}
		reqMap.put("searchBreedList", pcList);
		j.setSuccess(true);
		j.setAttributes(reqMap); 
		
	}
	
	/**
	 * 取得参检受检单位情况
	 * 
	 * @param projectCode
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDetectedInfo(String projectCode) {
		return findListByMyBatis(NAME_SPACE + "getDetectedInfo", projectCode);
	}

	/**
	 * 取得超标受检单位情况
	 * 
	 * @param projectCode
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getOverPoofInfo(String projectCode) {
		return findListByMyBatis(NAME_SPACE + "getOverPoofInfo", projectCode);
	}
	
	/**
	 * 取得抽样任务完成情况
	 * 
	 * @param projectCode
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSampleTaskInfo(String projectCode){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 设置权限
		setUserDataPriv(projectCode, selCodition);
		selCodition.put("projectCode", projectCode);
		return findListByMyBatis(NAME_SPACE + "getSampleTaskInfo", selCodition);
	}

	/**
	 * 取得检测任务完成情况
	 * 
	 * @param projectCode
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDetectionTaskInfo(String projectCode){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 设置权限
		setUserDataPriv(projectCode, selCodition);
		selCodition.put("projectCode", projectCode);
		return findListByMyBatis(NAME_SPACE + "getDetectionTaskInfo", selCodition);
	}
}