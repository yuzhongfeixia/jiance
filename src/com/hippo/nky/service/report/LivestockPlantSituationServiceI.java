package com.hippo.nky.service.report;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.report.PlantSituationEntity;

public interface LivestockPlantSituationServiceI extends CommonService {

	/**
	 * 通过年份、项目级别取得 检测项目
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, Object>> getProjectCode(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 总体情况 统计
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	Map<String, Object> totleSituationStatistics(
			PlantSituationEntity plantSituationEntity);

	//chenyingqin↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 各类畜产品监测合格率情况取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getLivestockVarietyQualifiedSituation (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各类畜产品监测合格率情况导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportLvqs(Map<String, Object> paramMap);
	
	/**
	 * 各监测环节畜产品监测合格率情况取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getMonitoringLinkLivestockQualifiedSituation (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各监测环节畜产品监测合格率情况导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportMLqs(Map<String, Object> paramMap);
	
	/**
	 * 不同品种超标情况统计表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getDiffVarietyOverStanderd (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 不同品种超标情况统计表导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportDvos(Map<String, Object> paramMap);
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * 畜禽检测统计类表取得通用方法
	 *  用于【省辖市区畜禽产品监测结果】，【县级畜禽产品监测结果】，【全省生鲜乳监测结果】，【各市超标样品情况表】
	 */
	public String getCommonLiveStockSampleOverProofList(PlantSituationEntity plantSituationEntity, String flg);
	
	/**
	 * 畜禽检测统计类表导出通用方法
	 *  用于【省辖市区畜禽产品监测结果】，【县级畜禽产品监测结果】，【全省生鲜乳监测结果】，【各市超标样品情况表】
	 */
	public List<Map<String, Object>> getCommonLiveStockExport(Map<String, Object> paramMap);
	
	//新增统计表↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓20140402
	/**
	 * 畜禽检测统计类表取得通用方法（新增统计表）
	 * 用于【市各类畜产品监测合格率情况】，【区县各类畜产品监测合格率情况】
	 * @param plantSituationEntity
	 * @param flg
	 * @return
	 */
	public Map<String, Object> getCommonLiveStockSitu(PlantSituationEntity plantSituationEntity, String flg);
	
	/**
	 * 畜禽检测统计类表导出通用方法（新增统计表） 
	 * 用于【市各类畜产品监测合格率情况】，【区县各类畜产品监测合格率情况】
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getCommonLiveStockSituExport(Map<String, Object> paramMap) ;
	
	
	/**
	 * 畜禽检测统计类表取得通用方法（新增统计表）
	 *  用于【市各监测项目不合格情况】，【区县各监测项目不合格情况】
	 */
	public Map<String, Object> getCommonDetectionItemSitu(PlantSituationEntity plantSituationEntity, String flg);
	
	/**
	 * 畜禽检测统计类表导出通用方法
	 *  用于【市各监测项目不合格情况】，【区县各监测项目不合格情况】
	 */
	public List<Map<String, Object>> getCommonDetectionItemSituExport(Map<String, Object> paramMap);
	//新增统计表↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑20140402
	/**
	 * 取得超标样品情况详细表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	public void getOverProofSampleInfo(
			PlantSituationEntity plantSituationEntity, DataGrid dataGrid);

	/**
	 * 固定县级畜禽产品监测结果
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, String>> getSampleOverProofList(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 检测环节类型情况
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	Map<String, Object> getSampleOverProofActList(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 固定省辖市区畜禽产品监测结果
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, String>> getTableForDistrict(
			PlantSituationEntity plantSituationEntity);
	
	/**
	 * 取得药物检出及超标情况报表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getPollDetectionInfo(Map<String, Object> paramMap);
	

	/**
	 * 各市超标样品情况表
	 * 左侧固定市超标样品情况
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, String>> getCitySampleOverProof(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 各市超标样品情况表
	 * 右侧活动市超标样品情况
	 * @param plantSituationEntity
	 * @return
	 */
	Map<String, Object> getCitySampleOverProofAct(
			PlantSituationEntity plantSituationEntity);
	
	/**
	 * 检测环节类型情况
	 * @param plantSituationEntity
	 * @return
	 */
	Map<String, Object> getTableForDistrictActList(
			PlantSituationEntity plantSituationEntity);
	
	/**
	 * 全省生鲜乳监测结果
	 * 左侧固定全省生鲜乳监测结果
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, String>> getFreshMilkMonitor(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 全省生鲜乳监测结果
	 * 右侧活动全省生鲜乳监测结果
	 * @param plantSituationEntity
	 * @return
	 */
	Map<String, Object> getFreshMilkMonitorAct(
			PlantSituationEntity plantSituationEntity);

	/**
	 * 各监测项目不合格情况
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getPollDetectionCount(Map<String, Object> paramMap);

}
