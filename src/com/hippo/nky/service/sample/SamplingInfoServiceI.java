package com.hippo.nky.service.sample;

import java.util.List;
import java.util.Map;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSUser;
import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.monitoring.ProjectBreedEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;

public interface SamplingInfoServiceI extends CommonService {

	/**
	 * 取得页面datatable数据
	 * 
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid);

	/**
	 * 取得抽检分离flg
	 */
	public String getDetecthedFlg(String projectCode);
	
	/**
	 * 添加例行监测信息
	 * 
	 * @param barcodeInfoInput
	 */
	public void addMain(SamplingInfoEntity barcodeInfoInput);

	/**
	 * 更新例行监测信息
	 * 
	 * @param barcodeInfoInput
	 */
	public void updateMain(SamplingInfoEntity barcodeInfoInput) throws Exception;

	/**
	 * 删除例行监测信息
	 * 
	 * @param barcodeInfoInput
	 */
	public void delMain(SamplingInfoEntity barcodeInfoInput);

	/**
	 *  根据项目编码取得抽样单模板和行业
	 * @param projectCode
	 * @return
	 */
	public String[] getMonitorTypeAndIndustry(String projectCode);
	
	/**
	 * 取pad抽样环节
	 * @param industry
	 * @return
	 */
	public List<TSType> getPadSampleMonitoringLink(String industry);
	
	/**
	 * 取得抽样环节名称回显
	 */
	public String getNameForMonitoringLink(String code);
	/**
	 * 根据项目编码取得抽样单类型 ，参照对应关系
	 * @param projectCode
	 * @return [ 1：例行监测,2:监督抽查,3：风险（普查）,4:生鲜乳,4:畜禽]
	 */
	public String getSampleMonadType (String projectCode);

	/**
	 * 取得用户监测项目,带过滤条件
	 * @return
	 */
	public List<MonitoringProjectEntity> getUserMonintorProgaram(TSUser user, String monitorType, String year, String showBelowGradeRepFlg);

	/**
	 * 取得页面sampleMakeCode的datatable数据
	 * 
	 * @param pageObj
	 * @param dataGrid
	 * @param areaCode
	 *            用户所属部门的行政区划code
	 * @return
	 */
	public JSONObject getSampleMakeCodeDatagrid(SamplingInfoEntity pageObj,
			DataGrid dataGrid, String areaCode);

	/**
	 * 设置制样编码
	 * 
	 * @param areaCode
	 *            用户所属部门的行政区划code
	 * @return 修改sql的数量
	 */
	public int setSpCode(String areaCode);

	/**
	 * 查询指定抽样样品
	 * 
	 * @param string
	 * @param string2 
	 * @return
	 */
	public Object findObsoleteByCode(String string, String string2);

	/**
	 * 二维码存在性check
	 */
	public int checkCode(String code);

	/**
	 * 抽样信息可上报数量
	 * 
	 * @param samplingInfoEntity
	 * @return
	 */
	public Map<String, Integer> sampleReportCount(
			SamplingInfoEntity samplingInfoEntity, String sampleIdList);

	/**
	 * 去上报数量
	 * 
	 * @param samplingInfoEntity
	 * @return
	 */
	public int toReport(SamplingInfoEntity samplingInfoEntity, String sampleIdList);

	/**
	 * 取得对应项目中的地市或区县的下拉框
	 * @param pageObj
	 * @param isCity 是否是地市
	 * @return
	 */
	public String getCityAndCountryList(SamplingInfoEntity pageObj, int isCity);
	
	/**
	 * 取得最新版本农产品名称
	 * @param sampleCode
	 * @return
	 */
	public String getLastVersionAgrName(String agrCode, String projectCode);
	
	/**
	 * 抽样完成情况统计
	 * @param barcodeInfoInput
	 * @param dataGrid
	 * @return
	 */
	public JSONObject findForStatistics(SamplingInfoEntity barcodeInfoInput,
			DataGrid dataGrid);
	
	/**
	 * 样品分发数据取得
	 * @param barcodeInfoInput
	 * @param request
	 * @return
	 */
	public JSONObject getSampleDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity, DataGrid dataGrid);
	
	/**
	 * 整包样品分发数据取得
	 * @param monitoringProjectEntity
	 * @param request
	 * @return
	 */
	public JSONObject getPackingDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity, DataGrid dataGrid);
	
	/**
	 * 整包分发前确认样品是否接收
	 * @param projectCode
	 * @return
	 */
	public boolean checkRecvPacking(SamplingInfoEntity samplingInfoEntity);
	
	/**
	 * 拆包样品分发数据取得
	 * @param monitoringProjectEntity
	 * @param request
	 * @return
	 */
	public JSONObject getUnpackingDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity, DataGrid dataGrid);
	
	/**
	 * 拆包分发前确认样品是否接收
	 * @param projectCode
	 */
	public boolean checkRecvUnPacking(Map<String, Object> paramMap);
	
	/**
	 * 整包分发数据保存
	 */
	public int saveSamplingDistribute(Map<String, Object> paramMap, int flg);
	
	/**
	 * 取得当前项目下的质检机构
	 * @param monitoringProjectEntity
	 * @param request
	 * @return
	 */
	public JSONObject getProjectOrgdatagrid(MonitoringProjectEntity monitoringProjectEntity, String orgCode, String sampleIdList, DataGrid dataGrid);

	/**
	 * 取得制样编码的按钮是否可用
	 * @param projectCode 项目code
	 * @return
	 */
	public boolean getMakeCodeButtonEnable(String projectCode);
	
	/**
	 * 检查项目是否上报
	 * @param projectCode
	 * @return
	 */
	public int checkReport(String projectCode);
	
	/**
	 * 检查样品信息是否上报
	 * @param samplingInfoId
	 * @return
	 */
	public int checkSampleReport(String samplingInfoId);
	
	/**
	 * 更据关键子搜索受检单位
	 * @param keyWords
	 * @return
	 */
	public List<NkyMonitoringSiteEntity> getMonitoringSite (String keyWords);
	
	/**
	 * 抽检分离的制样编码是否为空的验证
	 * 
	 * @param samplingInfoEntity
	 * @return
	 */
	public int checkSampleDetached(
			SamplingInfoEntity samplingInfoEntity);
	/**
	 * 样品信息详情-基本公共信息
	 * @param samplingInfoEntity 
	 * @return
	 */
	public Map<String, Object> getCommonSampleDetail(SamplingInfoEntity samplingInfoEntity);
	
	/**
	 * 取得项目对应的抽样品种
	 */
	public List<MonitoringBreedEntity> getMonitoringBreedForProject(String projectCode);
	
	/**
	 * 取得抽样信息退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingSetBack(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得抽样信息退回列表数据(审核通过已退回)
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingSetBack2(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得抽样信息退回审核列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingSetBackCheck(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得盲样管理列表
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingBlindData(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得添加盲样任务数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getAddSamplingBlindData(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 增加盲样选择样品 数据取得
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSelectSamplingBlind(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得盲样列表导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportSamplingBlindInfo(Map<String, Object> paramMap);
	
	public void saveProjectBreed(MonitoringBreedEntity monitoringBreedEntity);
	
	/**
	 * 将字节数组内容转换成图片上传到服务器
	 * @param imgContent
	 */
	public String initDataForImage (String imgContentMap);
	
	/**
	 *取得抽样信息汇总页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSampleCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	
	/**
	 * 取得抽样信息批量退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingBatchSetBack(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
	/**
	 * 取得抽样信息批量退回详情列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSamplingBatchSetBackDetail(SamplingInfoEntity pageObj, DataGrid dataGrid);
	

	/**
	 *取得页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getBatchSetBackSampleList(SamplingInfoEntity pageObj, DataGrid dataGrid);
	
}
