package com.hippo.nky.service.detection;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.detection.DetectionEntity;
import com.hippo.nky.entity.detection.DetectionStatisticalEntity;
import com.hippo.nky.entity.detection.NkyDetectionInformationEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;

public interface DetectionServiceI extends CommonService {

	/**
	 * 取得页面datatable数据
	 * 
	 * @param detectionEntity
	 * @param dataGrid
	 * @return
	 */

	public JSONObject getDatagrid(DetectionEntity detectionEntity,
			DataGrid dataGrid, String invokeF);

	/**
	 * 样品接收 生成实验室编码
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public void updateDetection(DetectionEntity detectionEntity);

	/**
	 * 通过抽样ids查询抽样信息
	 * 
	 * @param ids
	 * @return
	 */

	public List<Map<String, Object>> findSampleById(String ids);

	/**
	 * 修改样品标签打印数量
	 * 
	 * @param ids
	 */
	public void updateSamplesByIds(String ids);
	
	/**
	 * 取得检测信息汇总数据列表
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	//public JSONObject getDectionInfoCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid);
	public Map<String, Object> getDectionInfoCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得检测信息汇总导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportSampleCollect(Map<String, Object> paramMap);

	/**
	 * 检测信息详情
	 * @param id
	 * @return
	 */
	public Map<String, Object> getDectionInfoCollectDetail(String id);

	/**
	 * 根据监测环节的code取得对应的名称
	 * @param monitoringLinkCode
	 * @return
	 */
	public String getMonitoringLinkName (String monitoringLinkCode);

	/**
	 *  取得样品污染检出值
	 * @param sampleCode
	 * @return
	 */
	public List<NkyDetectionInformationEntity> getPollDetectionResult (String sampleCode);
	
	/**
	 * 取得项目已接收的实验室编码
	 */
	public List<String> getLabCodeForRecv(Map<String, Object> paramMap);
	
	/**
	 * 取得实验室编码最大序号
	 */
	public String getMaxLabSer(List<String> sampleInfoList);

	/**
	 * 取得检测信息录入的样品grid
	 * 
	 * @param detectionEntity
	 * @param dataGrid
	 * @return
	 */
	public void detectionInformationSamGrid(DetectionEntity detectionEntity,DataGrid dataGrid);

	/**
	 * 取得检测信息录入的污染物grid
	 * 
	 * @param detectionEntity
	 * @param dataGrid
	 * @return
	 */
	public void detectionInformationPollGrid(DetectionEntity detectionEntity,DataGrid dataGrid);
	
	/**
	 * 取得样品检测信息污染物list
	 * 
	 * @param detectionEntity
	 * @return 污染物list
	 */
	public List<NkyDetectionInformationEntity> getDetectionInfoPollItem(DetectionEntity detectionEntity);
	
	/**
	 * 取得样品检测信息农产品list
	 * 
	 * @param detectionEntity
	 * @return 农产品list
	 */
	public List<NkyDetectionInformationEntity> getDetectionInfoAgrItem(DetectionEntity detectionEntity);
	
	/**
	 * 设置当前污染物对应的全部的样品检出值=未检
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public int setAllWithUnDetection(DetectionEntity detectionEntity);
	
	/**
	 * 设置样品检测是否超标
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public void setOverproofRecord(DetectionEntity detectionEntity);
	
	/**
	 * 设置样品检测是否超标
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public void setOverproofRecord1(DetectionEntity detectionEntity);
	
	/**
	 * 取得上报列表
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public List<Map<String, Object>> getReportList(DetectionEntity detectionEntity);
	
	/**
	 * 取得上报列表
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public List<Map<String, Object>> getReportListNew(DetectionEntity detectionEntity);
	
	/**
	 * 取得上报的污染物title
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public List<DetectionEntity> getProjectPollInfo(DetectionEntity detectionEntity);
	
	/**
	 * 设置样品的状态为上报完成
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public void setDetectionReported(DetectionEntity detectionEntity);
	
	/**
	 * 检测完成情况统计列表取得
	 * @param projectCode
	 * @return
	 */
	public List<DetectionStatisticalEntity> getDetectionStatisticalList (DetectionEntity detectionEntity);
	
	/**
	 * 检测完成情况统计表头农产品取得
	 * @param projectCode
	 * @return
	 */
	public List<String> getAgrTableHeader (DetectionEntity detectionEntity);

	/**
	 * 检测完成情况统计农产品任务统计状况取得
	 * @param projectCode
	 * @return
	 */
	public List<DetectionStatisticalEntity> getAgrStatisticalList (DetectionEntity detectionEntity);

	/**
	 * 验证实验室编码
	 * 
	 * @param detectionEntity
	 * @return
	 */
	public int checkLabCode(DetectionEntity detectionEntity);
	
	/**
	 * 重置实验室编码
	 */
	public int resetProjectLabCode(DetectionEntity detectionEntity);
	
	/**
	 * 取得上报但未接收的样品信息
	 * @param detectionEntity
	 * @return
	 */
	public boolean getNotRecvSample(DetectionEntity detectionEntity);
	
	/**
	 * 取得检测信息退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDetectionSetBack(DetectionEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得检测信息退回列表数据(审核已通过已退回)
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDetectionSetBack2(DetectionEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得检测信息退回审核列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDetectionSetBackCheck(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 实验室编码管理列表导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportLabSample(Map<String, Object> paramMap);
}
