package com.hippo.nky.service.report;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.report.PlantSituationEntity;
import com.hippo.nky.entity.report.VegetablesSituationEntity;

public interface PlantSituationServiceI extends CommonService {

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
	 * 不同蔬菜来源超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesMonitoringLinkOverStanderd(PlantSituationEntity plantSituationEntity);
	
	/**
	 * 不同蔬菜来源超标情况统计表列表导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportMlos(Map<String, Object> paramMap);

	/**
	 * 不同蔬菜类别超标情况统计表表列取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesCategoryOverStanderd(PlantSituationEntity plantSituationEntity);
	
   	/**
	* 不同蔬菜类别超标情况统计表表列导出
	* @param plantSituationEntity
	* @return
	*/
	public List<Map<String, Object>> exportCos(Map<String, Object> paramMap);
	
	/**
	 * 不同蔬菜品种超标情况统计表 列表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesVarietyOverStanderd(PlantSituationEntity plantSituationEntity);
	
	/**
	 * 不同蔬菜品种超标情况统计表 列表导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportVos(Map<String, Object> paramMap);
	
	/**
	 * 各省辖市批发市场超标情况表(详表)取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getProvincialCitiesOverStandardDetail (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各省辖市批发市场超标情况表(详表)导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportPcosd(Map<String, Object> paramMap);
	
	/**
	 * 各区县超标情况表（详情）取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getCountiesOverStandardDetail (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各区县超标情况表（详情）导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportCosd(Map<String, Object> paramMap);
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * 取得超标样品情况详细表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	public void getOverProofSampleInfo(
			PlantSituationEntity plantSituationEntity, DataGrid dataGrid);

	
	/**
	 * 取得超标样品情况详细表导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportOverProofSampleInfo(Map<String, Object> paramMap);
//	/**
//	 * 固定各区县超标情况表
//	 * 
//	 * @param plantSituationEntity
//	 * @return
//	 */
//	List<Map<String, String>> getSampleOverProofList(
//			PlantSituationEntity plantSituationEntity);

	/**
	 * 固定各区县超标情况表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	String getSampleOverProofList(
			PlantSituationEntity plantSituationEntity);
	
	
	/**
	 * 各区县超标情况表 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportOverProof(Map<String, Object> paramMap);

//	/**
//	 * 检测环节类型情况
//	 * 
//	 * @param plantSituationEntity
//	 * @return
//	 */
//	Map<String, Object> getSampleOverProofActList(
//			PlantSituationEntity plantSituationEntity);

	/**
	 * 各省辖市批发市场超标情况表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	List<Map<String, String>> getSuperMarketOverProof(
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
//	List<Map<String, String>> getCitySampleOverProof(
//			PlantSituationEntity plantSituationEntity);
	
	String getCitySampleOverProof(PlantSituationEntity plantSituationEntity);

//	/**
//	 * 各市超标样品情况表
//	 * 右侧活动市超标样品情况
//	 * @param plantSituationEntity
//	 * @return
//	 */
//	Map<String, Object> getCitySampleOverProofAct(
//			PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各省辖市批发市场超标情况表导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportSuperMarket(Map<String, Object> paramMap);
	
	
	
	/**
	 * 各省辖市批发市场超标情况表取得 (20141120_cyq_add,modify)
	 * @param plantSituationEntity
	 * @return
	 */
	public String getProvincialCitiesOverStandard (PlantSituationEntity plantSituationEntity);
	
	/**
	 * 各省辖市批发市场超标情况表导出(20141120_cyq_add,modify)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportPcos(Map<String, Object> paramMap);
}
