package com.hippo.nky.service.impl.detection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.detection.DetectionEntity;
import com.hippo.nky.entity.detection.DetectionStatisticalEntity;
import com.hippo.nky.entity.detection.NkyDetectionInformationEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.service.detection.DetectionServiceI;
import com.hippo.nky.service.sample.SamplingInfoServiceI;

@Service("detectionService")
@Transactional
public class DetectionServiceImpl extends CommonServiceImpl implements
		DetectionServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.detection.DetectionEntity.";
	@Autowired  
	SamplingInfoServiceI samplingInfoService;
	@Override
	public JSONObject getDatagrid(DetectionEntity detectionEntity,
			DataGrid dataGrid, String invokeFlg) {
		// invokeFlg = 0表示样品接受管理调用，1表示实验室样品管理调用
		Map<String, Object> selCodition = getSelCodition(detectionEntity);
		selCodition.put("invokeFlg", invokeFlg);
		setBeginAndEnd(dataGrid, selCodition);
		Integer iCount = (Integer) this.getObjectByMyBatis(NAME_SPACE
				+ "getDetectionCount", selCodition);

		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE
				+ "getDetection", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("code"),
				new Db2Page("agrname"), new Db2Page("labCode"),
				new Db2Page("sampleStatus"),new Db2Page("printCount"),
				new Db2Page("rn") 
				};
		Map<String, String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("sampleStatus", "sampleStatus");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList,
				iCount.intValue(), dataGrid.getsEcho(), db2Pages,dataDicMap);
		return jObject;
	}

	// 取得查询条件
	private Map<String, Object> getSelCodition(DetectionEntity detectionEntity) {
		Map<String, Object> selConditionMap = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(detectionEntity.getProjectCode())) {
			selConditionMap
					.put("projectCode", detectionEntity.getProjectCode());
		}else{
			selConditionMap.put("projectCode", "0");
		}
		int allcount = checkSampleDetached(detectionEntity.getProjectCode());
		if(allcount > 0){
			selConditionMap.put("isDetached", "true");
		}
		
		if (StringUtil.isNotEmpty(detectionEntity.getOrgCode())) {
			selConditionMap.put("orgCode", detectionEntity.getOrgCode());
		}
		if (StringUtil.isNotEmpty(detectionEntity.getSearchLabCode())) {
			selConditionMap.put("labCode", detectionEntity.getSearchLabCode());
		}
		if (StringUtil.isNotEmpty(detectionEntity.getSampleName())) {
			selConditionMap.put("sampleName", detectionEntity.getSampleName());
		}
		if (StringUtil.isNotEmpty(detectionEntity.getLabPre())) {
			selConditionMap.put("labPre", detectionEntity.getLabPre());
		}
		if (StringUtil.isNotEmpty(detectionEntity.getStartSer())) {
			selConditionMap.put("startSer",
					Integer.parseInt(detectionEntity.getStartSer()));
		}
		if (StringUtil.isNotEmpty(detectionEntity.getSampleStatus())) {
			String status = detectionEntity.getSampleStatus();
			if(status.indexOf(",") == -1){
				selConditionMap.put("sampleStatus", new String[]{status});
			}else{
				String stas[] = status.split(",");
				selConditionMap.put("sampleStatus", stas);
			}
		}
		
		return selConditionMap;
	}

	// 设置查询的开始位置和结尾位置
	public void setBeginAndEnd(DataGrid dataGrid,
			Map<String, Object> selCodition) {
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page - 1) * rows;
		int endIndex = beginIndex + rows;
		selCodition.put("beginIndex", beginIndex);
		selCodition.put("endIndex", endIndex);
	}
	
	//检测项目是否为为抽检分离项目
	public int checkSampleDetached(String projectCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", projectCode);
		Integer allcount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"checkSampleDetached", selCodition);
		return allcount.intValue();
	}

	@Override
	public void updateDetection(DetectionEntity detectionEntity) {
		Map<String, Object> selCodition = getSelCodition(detectionEntity);
		int allcount = checkSampleDetached(detectionEntity.getProjectCode());
		if(allcount > 0){
			selCodition.put("detached", "1");
		} else {
			selCodition.put("detached", "0");
		}
		this.updateByMyBatis(NAME_SPACE + "updateDetection", selCodition);
	}

	@Override
	public List<Map<String, Object>> findSampleById(String ids) {
		Map<String, Object> cond = getIdsMap(ids);
		cond.put("orgCode", ResourceUtil.getSessionUserName().getOrganization().getCode());
		return this.findListByMyBatis(NAME_SPACE + "findSampleByIds", cond);
	}

	@Override
	public void updateSamplesByIds(String ids) {
		Map<String, Object> cond = getIdsMap(ids);
		this.updateByMyBatis(NAME_SPACE + "updateSamplesByIds", cond);
	}

	private Map<String, Object> getIdsMap(String ids) {
		String id[] = ids.split(",");
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("ids", id);
		return cond;
	}
	
	/**
	 * 取得项目已接收的实验室编码
	 */
	@Override
	public List<String> getLabCodeForRecv(Map<String, Object> paramMap) {
		return this.findListByMyBatis(NAME_SPACE + "getLabCodeForRecv", paramMap);
	}
	
	/**
	 * 取得实验室编码最大序号
	 */
	@Override
	public String getMaxLabSer(List<String> sampleInfoList) {
		int maxLabSer = 0;
		for (String labCode : sampleInfoList) {
			
			String strLabSer = labCode.substring(labCode.lastIndexOf("-") + 1);
			int intLabSer = Integer.parseInt(strLabSer);
			if (intLabSer > maxLabSer) {
				maxLabSer = intLabSer;
			}
		}
		return String.valueOf(maxLabSer);
	}
	
	/**
	 * 取得检测信息汇总数据列表
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
//	public JSONObject getDectionInfoCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid) {
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		String projectCode = pageObj.getProjectCode();
//		if (StringUtil.isNotEmpty(projectCode)) {
//			selCodition.put("projectCode", projectCode);
//		} else {
//			selCodition.put("projectCode", "NOPROJECTNODATA");
//		}
//		// 设置权限
//		setUserDataPriv(projectCode, selCodition);
//		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
//			selCodition.put("agrName",  pageObj.getSampleName());
//		}
//
//		if (StringUtil.isNotEmpty(pageObj.getUnitFullname())){
//			selCodition.put("unitFullname", pageObj.getUnitFullname());
//		}
//		if (StringUtil.isNotEmpty(pageObj.getMonitoringLink())){
//			selCodition.put("monitoringLink", pageObj.getMonitoringLink());
//		}	
//		setBeginAndEnd(dataGrid, selCodition);
//
//		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getDetectionInfoCollectCount", selCodition);
//		
//		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getDetectionInfoCollect", selCodition);
//		
//		// 将结果集转换成页面上对应的数据集
//		Db2Page[] db2Pages = {
//				new Db2Page("id")
//				,new Db2Page("sampleCode")
//				,new Db2Page("taskName")
//				,new Db2Page("spCode")
//				,new Db2Page("cname")
//				,new Db2Page("unitFullname")
//				,new Db2Page("monitoringLink")
//				,new Db2Page("unitAddress")
//				,new Db2Page("samplingOgrname")
//				,new Db2Page("detectionOgrname")
//				,new Db2Page("isOverproof")
//				,new Db2Page("rn")
//		};
//		
//		Map<String,String> dataDicMap = new HashMap<String, String>();
//		dataDicMap.put("monitoringLink","allmonLink");
//		dataDicMap.put("isOverproof","detectionJudge");
//		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(), dataGrid.getsEcho(), db2Pages, dataDicMap);
//		return jObject;
//	}
	
	/**
	 * 取得检测信息汇总数据列表
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public Map<String, Object> getDectionInfoCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCode = pageObj.getProjectCode();
		// 取得该项目下的抽样单类型
		String sampleMonType = samplingInfoService.getSampleMonadType(projectCode);
		if (StringUtil.isNotEmpty(projectCode)) {
			selCodition.put("projectCode", projectCode);
		} else {
			selCodition.put("projectCode", "NOPROJECTNODATA");
		}
		// 设置权限
		setUserDataPriv(projectCode, selCodition);
		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("agrName",  pageObj.getSampleName());
		}

		if (StringUtil.isNotEmpty(pageObj.getUnitFullname())){
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}
		if (StringUtil.isNotEmpty(pageObj.getMonitoringLink())){
			selCodition.put("monitoringLink", pageObj.getMonitoringLink());
		}
		selCodition.put("sampleMonType", sampleMonType);
		List<MonitoringProjectEntity> lst = this.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
		String detached = lst.get(0).getDetached();
		// 左边数据集
		List<Map<String, Object>> leftList =  this.findListByMyBatis(NAME_SPACE+ "getLeftDetectionInfoCollect", selCodition);
		// 表头污染物数据集
		List<String> headerPollList = this.findListByMyBatis(NAME_SPACE+ "getDetectionInfoPollHeader", selCodition);
		// 右边数据集
		List<Map<String, Object>> rightList =  this.findListByMyBatis(NAME_SPACE+ "getRightDetectionInfoCollect", selCodition);

		List<List<Map<String, Object>>> editedRightList = this.setDetectionCollectInitRes(leftList.size(), headerPollList, rightList);
		
		String htmls = "<thead><tr>";
		htmls += "<th class='center hidden-480'>序号</th>";
		htmls += "<th class='center hidden-480'>抽样单位</th>";
		htmls += "<th class='center hidden-480'>检测单位</th>";	
		htmls += "<th class='center hidden-480'>被检市县</th>";
//		htmls += "<th class='center hidden-480'>任务</th>";
		if (detached.equals("0")) {
			htmls += "<th class='center hidden-480'>样品条码</th>";
		} else {
			htmls += "<th class='center hidden-480'>制样编码</th>";	
		}
		htmls += "<th class='center hidden-480'>样品名称</th>";
		htmls += "<th class='center hidden-480'>受检单位名称</th>";
		htmls += "<th class='center hidden-480'>监测环节</th>";
		htmls += "<th class='center hidden-480'>受检单位所在地</th>";
		if (StringUtil.equals(sampleMonType, "1") || StringUtil.equals(sampleMonType, "5")) {
			htmls += "<th class='center hidden-480'>样品标示来源地(产地)</th>";
		}
		for (int i = 0; i < headerPollList.size(); i++) {
			htmls += "<th class='center hidden-480 pollheader'>" + headerPollList.get(i) + "</th>";
		}
		htmls += "<th class='center hidden-480'>结果判定(合格或不合格)</th>";
		htmls += "</tr></thead>";
		
		htmls += "<tbody>";
		int row = 0;
		for (Map<String, Object> leftOne : leftList) {
			htmls += "<tr>";
			htmls += "<td>"+leftOne.get("ROWINDEX")+"</td>";
			htmls += "<td>"+leftOne.get("SAMPLINGORGNAME")+"</td>";
			htmls += "<td>"+leftOne.get("DETECTIONORGNAME")+"</td>";
			htmls += "<td>"+leftOne.get("CITYANDCOUNTY")+"</td>";
//			htmls += "<td>"+leftOne.get("TASKNAME")+"</td>";
			htmls += "<td>"+leftOne.get("SPCODE")+"</td>";
			htmls += "<td>"+leftOne.get("CNAME")+"</td>";
			htmls += "<td>"+leftOne.get("UNITFULLNAME")+"</td>";
			htmls += "<td>"+leftOne.get("MONITORINGLINK")+"</td>";
			htmls += "<td>"+leftOne.get("UNITADDRESS")+"</td>";
			if (StringUtil.equals(sampleMonType, "1") || StringUtil.equals(sampleMonType, "5")) {
				htmls += "<td>"+leftOne.get("SMAPLESOURCE")+"</td>";
			}
			for (Map<String, Object> rightOne : editedRightList.get(row)) {
				BigDecimal detectionValue = (BigDecimal)rightOne.get("detectionValue");
				if (detectionValue.doubleValue() == 999999) {
					htmls += "<td>-</td>";
				} else if (detectionValue.doubleValue() == 0) {
					htmls += "<td>未检出</td>";
				} else if (detectionValue.doubleValue() < 0) {
					htmls += "<td>未检</td>";
				} else {
					htmls += "<td>"+rightOne.get("detectionValue")+"</td>";
				}	
			}
			htmls += "<td>"+leftOne.get("ISOVERPROOF")+"</td>";
			htmls += "</tr>";
			row ++;
		}
		htmls += "</tbody>";
		resultmap.put("pollSize", headerPollList.size());
		resultmap.put("htmls", htmls);
		return resultmap;
	}
	
	private List<List<Map<String, Object>>> setDetectionCollectInitRes(int leftListCount, List<String> headerPollList, List<Map<String, Object>> rightList) {
		List<List<Map<String, Object>>> resultList = new ArrayList<List<Map<String, Object>>>();
		for (int i = 0; i< leftListCount; i++) {
			List<Map<String, Object>> dList = new ArrayList<Map<String, Object>>();
			for (String header : headerPollList) {
				Map<String, Object> dMap = new HashMap<String, Object>();
				dMap.put("popCname", header);
				dMap.put("detectionValue", new BigDecimal(999999));
				dList.add(dMap);
			}
			resultList.add(dList);
		}

		int count = 0;
		boolean firstFlg = true;
		String tmpName="";
		String compareName="";
		for (Map<String, Object> agrst : rightList) {
			tmpName = (String)agrst.get("sampleCode");
			if (firstFlg) {
				compareName =tmpName;
				firstFlg = false;
			}
			if (!StringUtils.equals(tmpName, compareName)) {
				count++;
			}
			setDetectionCollectLoadRes(resultList, count, agrst);
		
			compareName =tmpName;
		}
		return resultList;
	}
	
	private void setDetectionCollectLoadRes(List<List<Map<String, Object>>> initList, int index, Map<String, Object> agrst) {	
		List<Map<String, Object>> decDataList = initList.get(index);
		for (Map<String, Object> decData : decDataList) {
			if (StringUtils.equals((String)decData.get("popCname"), (String)agrst.get("popCname"))) {
				decData.put("detectionValue", agrst.get("detectionValue"));
			}
		}
	}
	
	/**
	 * 检测信息详情
	 * @param id
	 * @return
	 */
	public Map<String, Object> getDectionInfoCollectDetail(String id) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("id", id);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getDetectionInfoCollect", selCodition);
		return mapList.get(0);
	}
	
	/**
	 * 检测信息汇总导出
	 * @param paramMap
	 * @return
	 */
//	@Override
//	public List<Map<String, Object>> exportSampleCollect(Map<String, Object> paramMap) {
//		String projectCode = (String)paramMap.get("projectCode");
//		if (StringUtil.isNotEmpty(projectCode)) {
//		} else {
//			paramMap.put("projectCode", "NOPROJECTNODATA");
//		}
//		setUserDataPriv(projectCode, paramMap);
//		
//		List<MonitoringProjectEntity> lst = this.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
//		if (lst.size() > 0){
//			MonitoringProjectEntity mp = lst.get(0);
//			if(mp.getDetached().equals("0")){
//				paramMap.put("detached", "0");// 抽检不分离
//			} else {
//				paramMap.put("detached", "1");// 抽检分离
//			}
//		}
//		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE + "getDetectionInfoCollectForExport", paramMap);
//		return mapList;
//	}
//	
	/**
	 * 检测信息汇总导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportSampleCollect(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCode = (String)paramMap.get("projectCode");
		// 取得该项目下的抽样单类型
		String sampleMonType = samplingInfoService.getSampleMonadType(projectCode);
		if (StringUtil.isNotEmpty(projectCode)) {
			selCodition.put("projectCode", projectCode);
		} else {
			selCodition.put("projectCode", "NOPROJECTNODATA");
		}
		// 设置权限
		setUserDataPriv(projectCode, selCodition);
		if (StringUtil.isNotEmpty(paramMap.get("sampleName"))) {
			selCodition.put("agrName", paramMap.get("sampleName"));
		}

		if (StringUtil.isNotEmpty(paramMap.get("unitFullname"))){
			selCodition.put("unitFullname", paramMap.get("unitFullname"));
		}
		if (StringUtil.isNotEmpty(paramMap.get("monitoringLink"))){
			selCodition.put("monitoringLink", paramMap.get("monitoringLink"));
		}
		selCodition.put("sampleMonType", sampleMonType);
		List<MonitoringProjectEntity> lst = this.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
		String detached = lst.get(0).getDetached();
		// 左边数据集
		List<Map<String, Object>> leftList =  this.findListByMyBatis(NAME_SPACE+ "getLeftDetectionInfoCollect", selCodition);
		// 表头污染物数据集
		List<String> headerPollList = this.findListByMyBatis(NAME_SPACE+ "getDetectionInfoPollHeader", selCodition);
		// 右边数据集
		List<Map<String, Object>> rightList =  this.findListByMyBatis(NAME_SPACE+ "getRightDetectionInfoCollect", selCodition);

		List<List<Map<String, Object>>> editedRightList = this.setDetectionCollectInitRes(leftList.size(), headerPollList, rightList);
		
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#5");
		headerMap.put("TITLE_2", "title#KV#抽样单位#EM#width#KV#20");
		headerMap.put("TITLE_3", "title#KV#检测单位#EM#width#KV#20");
		headerMap.put("TITLE_4", "title#KV#被检市县#EM#width#KV#20");
//		headerMap.put("TITLE_2", "title#KV#任务#EM#width#KV#20");
		if (detached.equals("0")) {
			headerMap.put("TITLE_5", "title#KV#样品条码#EM#width#KV#5");
		} else {
			headerMap.put("TITLE_5", "title#KV#制样编码#EM#width#KV#5");
		}	
		headerMap.put("TITLE_6", "title#KV#样品名称#EM#width#KV#10");
		headerMap.put("TITLE_7", "title#KV#受检单位名称#EM#width#KV#20");
		headerMap.put("TITLE_8", "title#KV#监测环节#EM#width#KV#10");
		headerMap.put("TITLE_9", "title#KV#受检单位所在地#EM#width#KV#15");

		int com = 10;
		if (StringUtil.equals(sampleMonType, "1") || StringUtil.equals(sampleMonType, "5")) {
			headerMap.put("TITLE_10", "title#KV#样品标示来源地(产地)#EM#width#KV#25");
			com = 11;
		}
		for (int i = 0; i < headerPollList.size(); i++) {
			com = com+i+1;
			headerMap.put("TITLE_"+com, "title#KV#"+headerPollList.get(i)+"#EM#width#KV#10");

		}
		com++;
		headerMap.put("TITLE_"+com, "title#KV#结果判定(合格或不合格)#EM#width#KV#5");
		resultMapList.add(headerMap);
		
		LinkedHashMap<String, Object> dataMap = null;
		int row = 0;
		for (Map<String, Object> leftOne : leftList) {
			dataMap = new LinkedHashMap<String, Object>();
			dataMap.put("TITLE_1", leftOne.get("ROWINDEX"));
			dataMap.put("TITLE_2", leftOne.get("SAMPLINGORGNAME"));
			dataMap.put("TITLE_3", leftOne.get("DETECTIONORGNAME"));
//			dataMap.put("TITLE_2", leftOne.get("TASKNAME"));
			dataMap.put("TITLE_4", leftOne.get("CITYANDCOUNTY"));
			dataMap.put("TITLE_5", leftOne.get("SPCODE"));
			dataMap.put("TITLE_6", leftOne.get("CNAME"));
			dataMap.put("TITLE_7", leftOne.get("UNITFULLNAME"));
			dataMap.put("TITLE_8", leftOne.get("MONITORINGLINK"));
			dataMap.put("TITLE_9", leftOne.get("UNITADDRESS"));
		
			com = 10;
			if (StringUtil.equals(sampleMonType, "1") || StringUtil.equals(sampleMonType, "5")) {
				dataMap.put("TITLE_10", leftOne.get("SMAPLESOURCE"));
				com = 11;
			}
			for (Map<String, Object> rightOne : editedRightList.get(row)) {
				com++;
				BigDecimal detectionValue = (BigDecimal)rightOne.get("detectionValue");
				if (detectionValue.doubleValue() == 999999) {
					dataMap.put("TITLE_"+com, "-");
				} else if (detectionValue.doubleValue() == 0) {
					dataMap.put("TITLE_"+com, "未检出");
				} else if (detectionValue.doubleValue() < 0) {
					dataMap.put("TITLE_"+com, "未检");
				} else {
					dataMap.put("TITLE_"+com, rightOne.get("detectionValue"));
				}	
			}
			com++;
			dataMap.put("TITLE_"+com, leftOne.get("ISOVERPROOF"));
			row ++;
			resultMapList.add(dataMap);
		}
		return resultMapList;
	}
	
	
	
	
	/**
	 * 根据监测环节的code取得对应的名称
	 * @param monitoringLinkCode
	 * @return
	 */
	public String getMonitoringLinkName (String monitoringLinkCode) {
		final String monitoringLink = "allmonLink";
		List<TSType> types = TSTypegroup.allTypes.get(monitoringLink.toLowerCase());
		for (TSType type : types) {
			if (StringUtils.equals(monitoringLinkCode, type.getTypecode())) {
				return type.getTypename();
			}
		}
		return null;
	}
	
	/**
	 *  取得样品污染检出值
	 * @param sampleCode
	 * @return
	 */
	public List<NkyDetectionInformationEntity> getPollDetectionResult (String sampleCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("sampleCode", sampleCode);
		List<NkyDetectionInformationEntity> detectionList = this.findListByMyBatis(NAME_SPACE+"getPollDetectionResult", selCodition);
		return detectionList;
	}
	/**
	 * 取得检测信息录入的样品grid
	 * 
	 * @param detectionEntity
	 * @param dataGrid
	 * @return
	 */
	@Override
	public void detectionInformationSamGrid(DetectionEntity detectionEntity, DataGrid dataGrid){
		int allcount = checkSampleDetached(detectionEntity.getProjectCode());
		if (allcount > 0) {
			detectionEntity.setDetachedFlg("1");// 抽检分离
		} else {
			detectionEntity.setDetachedFlg("0");// 抽检不分离
		}
		// 设置翻页信息到Entity
		ConverterUtil.setPageNavigateInfo(dataGrid, detectionEntity);
		Integer iCount = (Integer) this.getObjectByMyBatis(NAME_SPACE + "getDetectionInfoSampleCount", detectionEntity);
		dataGrid.setTotal(iCount);
		List<DetectionEntity> entityList = this.findListByMyBatis(NAME_SPACE + "getDetectionInfoSample", detectionEntity);
		dataGrid.setReaults(entityList);
	}
	
	/**
	 * 取得检测信息录入的污染物grid
	 * 
	 * @param detectionEntity
	 * @param dataGrid
	 * @return
	 */
	@Override
	public void detectionInformationPollGrid(DetectionEntity detectionEntity, DataGrid dataGrid){
		// 设置翻页信息到Entity
		ConverterUtil.setPageNavigateInfo(dataGrid, detectionEntity);
		Integer iCount = (Integer) this.getObjectByMyBatis(NAME_SPACE + "getDetectionInfoPollCount", detectionEntity);
		dataGrid.setTotal(iCount);
		List<DetectionEntity> entityList = this.findListByMyBatis(NAME_SPACE + "getDetectionInfoPoll", detectionEntity);
		dataGrid.setReaults(entityList);
	}
	
	/**
	 * 取得样品检测信息污染物list
	 * 
	 * @param detectionEntity
	 */
	@Override
	public List<NkyDetectionInformationEntity> getDetectionInfoPollItem(DetectionEntity detectionEntity) {
		List<NkyDetectionInformationEntity> entityList = this.findListByMyBatis(
				NAME_SPACE + "getDetectionInfoPollItem", detectionEntity);
		return entityList;
	}
	
	/**
	 * 取得样品检测信息农产品list
	 * 
	 * @param detectionEntity
	 * @return 农产品list
	 */
	@Override
	public List<NkyDetectionInformationEntity> getDetectionInfoAgrItem(DetectionEntity detectionEntity){
		List<NkyDetectionInformationEntity> entityList = this.findListByMyBatis(
				NAME_SPACE + "getDetectionInfoAgrItem", detectionEntity);
		return entityList;
	}

	/**
	 * 设置当前污染物对应的全部的样品检出值=未检
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@Override
	public int setAllWithUnDetection(DetectionEntity detectionEntity){
		return this.updateByMyBatis(NAME_SPACE + "setAllWithUnDetection", detectionEntity);
	}
	
	/**
	 * 设置样品检测是否超标
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@Override
	@Transactional 
	public void setOverproofRecord(DetectionEntity detectionEntity) {
		// 取得要更新的样品的sampleCode
		String sampleCode = this.getObjectByMyBatis(NAME_SPACE + "getDetectionUpdateSampleCode", detectionEntity);
		
		this.updateByMyBatis(NAME_SPACE + "setOverproofRecord", sampleCode);
		this.updateByMyBatis(NAME_SPACE + "updateOverproofSampleInfo", sampleCode);
		
//		this.updateByMyBatis(NAME_SPACE + "setOverproofRecord", detectionEntity);
//		this.updateByMyBatis(NAME_SPACE + "updateOverproofSampleInfo", detectionEntity);
	}
	
	/**
	 * 设置样品检测是否超标
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@Override
	@Transactional 
	public void setOverproofRecord1(DetectionEntity detectionEntity) {
		// 取得要更新的样品的sampleCode
		List<String> sampleCodeList = this.findListByMyBatis(NAME_SPACE + "getDetectionUpdateSampleCode", detectionEntity);
		for (String sampleCode : sampleCodeList) {
			this.updateByMyBatis(NAME_SPACE + "setOverproofRecord", sampleCode);
			this.updateByMyBatis(NAME_SPACE + "updateOverproofSampleInfo", sampleCode);
		}

	}
	
	/**
	 * 取得上报列表
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> getReportList(DetectionEntity detectionEntity) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectCode", detectionEntity.getProjectCode());
		paramsMap.put("orgCode", detectionEntity.getOrgCode());
		paramsMap.put("selCondtion", detectionEntity.getSelCondtion());
		this.findListByMyBatis(NAME_SPACE + "getReportList", paramsMap);
		return (List<Map<String, Object>>) paramsMap.get("result");
	}
	
	/**
	 * 取得上报的污染物title
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@Override
	public List<DetectionEntity> getProjectPollInfo(DetectionEntity detectionEntity){
		return this.findListByMyBatis(NAME_SPACE + "getProjectPollInfo", detectionEntity);
	}
	
	/**
	 * 设置样品的状态为上报完成
	 * 
	 * @param detectionEntity
	 * @return
	 */
	@Override
	public void setDetectionReported(DetectionEntity detectionEntity){
		detectionEntity.setDetectionReportingDate(new Date());
		this.updateByMyBatis(NAME_SPACE + "setDetectionReported", detectionEntity);
	}
	
	/**
	 * 检测完成情况统计列表取得
	 * @param projectCode
	 * @return
	 */
	public List<DetectionStatisticalEntity> getDetectionStatisticalList (DetectionEntity detectionEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", detectionEntity.getProjectCode());
		selCodition.put("selCondtion", detectionEntity.getSelCondtion());
		setUserDataPriv(detectionEntity.getProjectCode(), selCodition);

		List<DetectionStatisticalEntity> detectionStatisticalList = this.findListByMyBatis(NAME_SPACE + "getDetectionStatistical", selCodition);
		return detectionStatisticalList;
	}
	
	/**
	 * 检测完成情况统计表头农产品取得
	 * @param projectCode
	 * @return
	 */
	public List<String> getAgrTableHeader (DetectionEntity detectionEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", detectionEntity.getProjectCode());
		selCodition.put("selCondtion", detectionEntity.getSelCondtion());
		setUserDataPriv(detectionEntity.getProjectCode(), selCodition);
		List<String> agrtableHeader = this.findListByMyBatis(NAME_SPACE + "getAgrTableHeader", selCodition);
		return agrtableHeader;
	}
	
	/**
	 * 检测完成情况统计农产品任务统计状况取得
	 * @param projectCode
	 * @return
	 */
	public List<DetectionStatisticalEntity> getAgrStatisticalList (DetectionEntity detectionEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", detectionEntity.getProjectCode());
		selCodition.put("selCondtion", detectionEntity.getSelCondtion());
		setUserDataPriv(detectionEntity.getProjectCode(), selCodition);
		List<DetectionStatisticalEntity> agrStatisticalList = this.findListByMyBatis(NAME_SPACE + "getAgrStatistical", selCodition);
		return agrStatisticalList;
	}
	
	/**
	 * 取得导出word数据
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getExportWordDataList(Map<String, Object> params){
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> sampleCollectList = this.findListByMyBatis(NAME_SPACE + "getSampleCollectInfo", params);
		if(sampleCollectList.size() <= 0){
			return dataList;
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 取得抽样信息
		dataMap = sampleCollectList.get(0);
		// 取得系统默认单位
		dataMap.put(Constants.PROPEERTIS_SYSTEMUNIT, ResourceUtil.getConfigByName(Constants.PROPEERTIS_SYSTEMUNIT));
		// 取得样品code
		String sampleCode = ConverterUtil.toString(dataMap.get("sampleCode"));
		// 设置样品code
		params.put("sampleCode", sampleCode);
		// 取得污染物list
		List<NkyDetectionInformationEntity> detectionInfoList = this.findListByMyBatis(NAME_SPACE + "getExportWordResult", params);
		dataMap.put("detectionInfoList", detectionInfoList);
		dataList.add(dataMap);
		return dataList;
	}
	
	public int checkLabCode(DetectionEntity detectionEntity) {
		Integer total= this.getObjectByMyBatis(NAME_SPACE+"checkLabCode", detectionEntity);
		return total.intValue();
	}
	
	/**
	 * 重置实验室编码
	 */
	public int resetProjectLabCode(DetectionEntity detectionEntity) {
		Integer total = this.updateByMyBatis(NAME_SPACE+"resetProjectLabCode", detectionEntity);
		return total.intValue();
	}
	
	/**
	 * 取得上报但未接收的样品信息
	 */
	@Override
	public boolean getNotRecvSample(DetectionEntity detectionEntity){
		List<String> infoList = this.findListByMyBatis(NAME_SPACE + "getNotRecvSample", detectionEntity);
		if (infoList != null && infoList.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 取得检测信息退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getDetectionSetBack(DetectionEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		if (StringUtil.isNotEmpty(pageObj.getLabCode())) {
			selCodition.put("labCode", pageObj.getLabCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
	
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getDetectionSetBackCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getDetectionSetBack", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("status")
				,new Db2Page("agrName")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 取得检测信息退回列表数据(审核已通过已退回)
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getDetectionSetBack2(DetectionEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		if (StringUtil.isNotEmpty(pageObj.getLabCode())) {
			selCodition.put("labCode", pageObj.getLabCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
	
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getDetectionSetBackCount2", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getDetectionSetBack2", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("status")
				,new Db2Page("agrName")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 取得检测信息退回审核列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getDetectionSetBackCheck(SamplingInfoEntity pageObj, DataGrid dataGrid) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		if (StringUtil.isNotEmpty(pageObj.getdCode())) {
			selCodition.put("dCode", pageObj.getdCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtil.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}
		if (StringUtil.isNotEmpty(pageObj.getOgrName())) {
			selCodition.put("ogrName", pageObj.getOgrName());
		}

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getDetectionSetBackCheckCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getDetectionSetBackCheck", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("status")
				,new Db2Page("projectName")
				,new Db2Page("agrName")
				,new Db2Page("unitFullName")
				,new Db2Page("samplingDate")
				,new Db2Page("ogrName")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 检测信息上报数据导出
	 * 
	 * @param paramMap
	 * @return
	 */
    public List<Map<String, Object>> exprotReport(Map<String, Object> paramMap) {
      String projectCode = ConverterUtil.toString(paramMap.get("projectCode"));
      if(ConverterUtil.isEmpty(projectCode)){
        return new ArrayList<Map<String,Object>>();
      }
      DetectionEntity detectionEntity = new DetectionEntity();
      // 取得当前用户的部门
      String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
      // 过滤用户所能看到的数据 部门=质检机构
      detectionEntity.setOrgCode(detectionCode);
      detectionEntity.setProjectCode(projectCode);
  
      List<Map<String, Object>> reportList = getReportList(detectionEntity);
      // 二次封装数据做成导出格式
      if(reportList.size() > 0){
        Map<String, Object> titleMap = reportList.get(0);
        // 把抬头改成导出excel需要的格式
        for (String titkey : titleMap.keySet()) {
          titleMap.put(titkey, "title" + ConverterUtil.SEPARATOR_KEY_VALUE + titleMap.get(titkey));
        }
        reportList.set(0, titleMap);
        // 把带有字体颜色的改成excel需要的格式
        for (Map<String, Object> repMap : reportList) {
          for(String titkey:repMap.keySet()){
            String value = ConverterUtil.toString(repMap.get(titkey));
            // 如果是超标 要设成红色
            if(value.contains("<FONT")){
              Pattern pattern = Pattern.compile(">(.+?)<");
              Matcher matcher = pattern.matcher(value);
              if(matcher.find()) {
                StringBuilder sf = new StringBuilder();
                sf.append(ConverterUtil.EXCEL_TITLE);
                sf.append(ConverterUtil.SEPARATOR_KEY_VALUE);
                sf.append(matcher.group(1));
                sf.append(ConverterUtil.SEPARATOR_ELEMENT);
                sf.append(ConverterUtil.EXCEL_COLOR);
                sf.append(ConverterUtil.SEPARATOR_KEY_VALUE);
                sf.append("red");
                repMap.put(titkey, sf.toString());
              }
            }
          }
        }
      }
      return reportList;
    }

	/**
	 * 实验室编码管理列表导出
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportLabSample(Map<String, Object> paramMap) {
		int allcount = checkSampleDetached((String)paramMap.get("projectCode"));
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		selCodition.put("projectCode", (String)paramMap.get("projectCode"));
		if (StringUtil.isNotEmpty(paramMap.get("searchLabCode"))) {
			selCodition.put("labCode", paramMap.get("searchLabCode"));
		}
		if (StringUtil.isNotEmpty(paramMap.get("sampleName"))){
			selCodition.put("sampleName", paramMap.get("sampleName"));
		}
		List<Map<String, Object>> mapList =  this.findListByMyBatis(NAME_SPACE+ "getLabSampleForExport", selCodition);
		
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("RN", "title#KV#序号#EM#width#KV#30");
		if (allcount > 0) {// 抽检分离
			headerMap.put("CODE", "title#KV#制样编码#EM#width#KV#20");
		} else  {
			headerMap.put("CODE", "title#KV#样品条码#EM#width#KV#20");
		}
		headerMap.put("LAB_CODE", "title#KV#实验室编码#EM#width#KV#20");
		headerMap.put("CNAME", "title#KV#样品名称#EM#width#KV#20");
		if (allcount <= 0) {
			headerMap.put("UNIT_FULLNAME", "title#KV#受检单位#EM#width#KV#20");
		}
		resultMapList.add(0, headerMap);
		for (Map<String, Object> map : mapList) {
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			dataMap.put("RN", map.get("RN"));
			dataMap.put("CODE", map.get("CODE"));
			dataMap.put("LAB_CODE", map.get("LAB_CODE"));
			dataMap.put("CNAME", map.get("CNAME"));
			if (allcount <= 0) {
				dataMap.put("UNIT_FULLNAME", map.get("UNIT_FULLNAME"));
			}
			resultMapList.add(dataMap);
		}
		
		return resultMapList;
	}
}