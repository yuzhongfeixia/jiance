package com.hippo.nky.service.impl.report;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.report.PlantSituationEntity;
import com.hippo.nky.entity.report.SampleOverProofEntity;
import com.hippo.nky.service.report.LivestockPlantSituationServiceI;

@Service("livestockPlantSituationService")
@Transactional
public class LivestockPlantSituationServiceImpl extends CommonServiceImpl implements
    LivestockPlantSituationServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.report.LivestockSituationEntity.";

	public Map<String, Object> totleSituationStatistics(PlantSituationEntity plantSituationEntity) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> selConditionMap = new HashMap<String, Object>();
		try {
			rtnMap.put("year", plantSituationEntity.getYear());
			rtnMap.put("projectName", java.net.URLDecoder.decode(plantSituationEntity.getProjectName(), "utf-8"));
			String projectLevel = plantSituationEntity.getProjectLevel();
			if(projectLevel.equals("1")){
				rtnMap.put("projectLevelName", "全省");
			}else if(projectLevel.equals("2")){
				rtnMap.put("projectLevelName", "全市");
			}else{
				rtnMap.put("projectLevelName", "全县");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		rtnMap.put("projectLevel", plantSituationEntity.getProjectLevel());
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selConditionMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selConditionMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selConditionMap.put("projectCode",plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selConditionMap);
		
		//监测地区及数量
		int areaCount = this.getObjectByMyBatis(NAME_SPACE+ "getAreaCount", selConditionMap);
		//农产品数量
		int argCount = this.getObjectByMyBatis(NAME_SPACE+ "getArgCount", selConditionMap);
		//样品数量 全部
		int sampCount = this.getObjectByMyBatis(NAME_SPACE+ "getSampleCount", selConditionMap);
		//污染物数量
		int pollCount = this.getObjectByMyBatis(NAME_SPACE+ "getPollCount", selConditionMap);
		//样品数量 合格数
		Map<String, Object> qualifiedCodition = new HashMap<String, Object>();
		qualifiedCodition.putAll(selConditionMap);
		qualifiedCodition.put("isQualified", "0");//合格
		int qualifiedCount = (Integer) this.getObjectByMyBatis(NAME_SPACE+ "getSampleCount", qualifiedCodition);
		
		rtnMap.put("areaCount", areaCount);
		rtnMap.put("argCount", argCount);
		rtnMap.put("sampCount", sampCount);
		rtnMap.put("pollCount", pollCount);
		rtnMap.put("qualifiedCount", qualifiedCount);
		DecimalFormat df1 = new DecimalFormat("##.0%"); 
		if(sampCount != 0){
			double divNum = (double)qualifiedCount/sampCount;
			if(divNum == 0.0){
				rtnMap.put("pct", "0.0%");
			}else{
				rtnMap.put("pct", df1.format((double)qualifiedCount/sampCount));
			}
		}else{
			rtnMap.put("pct", "0.0%");
		}
		return rtnMap;
	}
	/**
	 * 用户登陆质检机构编码
	 * @return
	 */
	private String getLoginOrgCode() {
		OrganizationEntity org = this.get(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		return org.getCode();
	}
	
	/**
	 * 用户管理 分成地区编码
	 * @param level
	 * @return
	 */
	private Object getAreaCodeByLevel(String level) {
		String rtnAreaCode = "";
		if(level.equals("1")){
			rtnAreaCode = (String) ResourceUtil.getLoginUserArea().subSequence(0, 2);
		}else if(level.equals("2")){
			rtnAreaCode = (String) ResourceUtil.getLoginUserArea().subSequence(0, 4);
		}else{
			rtnAreaCode = ResourceUtil.getLoginUserArea().toString();
		}
		return rtnAreaCode;
	}

	// 取得查询条件
	private Map<String, Object> getSelCodition(PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selConditionMap = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(plantSituationEntity.getProjectCode())) {
			selConditionMap.put("projectCode",
					plantSituationEntity.getProjectCode());
		}
		// 管理部门 0 质检机构 1
		if (ResourceUtil.getSessionUserName().getUsertype().equals("1")) {
			selConditionMap.put("orgCode",getLoginOrgCode());
		}
		
		if (StringUtil.isNotEmpty(plantSituationEntity.getProjectLevel())) {
			selConditionMap.put("projectLevel",
					plantSituationEntity.getProjectLevel());
		}
		
		if (StringUtil.isNotEmpty(plantSituationEntity.getYear())) {
			selConditionMap.put("year", plantSituationEntity.getYear());
		}
		return selConditionMap;
	}

	public List<Map<String, Object>> getProjectCode(PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		try {
			selCodition.putAll(ConverterUtil.entityToMap(plantSituationEntity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ResourceUtil.getSessionUserName().getUsertype().equals("1")) {
			selCodition.put("orgCode",getLoginOrgCode());
		}
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE
				+ "getProjectCode", selCodition);
		return mapList;
	}

	//chenyingqin↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 各类畜产品监测合格率情况取得
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public String getLivestockVarietyQualifiedSituation (PlantSituationEntity plantSituationEntity){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getLivestockVarietyQualifiedSituation", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >畜产品种类</th>";
		htmls += "<th class='center hidden-480' >抽样总数</th>";
		htmls += "<th class='center hidden-480' >合格数</th>";
		htmls += "<th class='center hidden-480' >合格率%</th>";
		htmls += "</thead></tr>";	
		htmls += "<tbody>";
		
		//计算合计
		int sumSamplingCount = 0;
		int SumQualifiedCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpQualifiedCount = Integer.parseInt((String)datamap.get("QUALIFIEDCOUNT"));
			
			sumSamplingCount += tmpSamplingCount;
			SumQualifiedCount += tmpQualifiedCount;
	
			String qualifiedRate = df.format(100*(float)tmpQualifiedCount/tmpSamplingCount) + "%";

			htmls += "<tr>";
			htmls += "<td>"+datamap.get("AGRNAME")+"</td>";              //畜产品种类
			htmls += "<td>"+datamap.get("SAMPLINGCOUNT")+"</td>";           //抽样总数
			htmls += "<td>"+datamap.get("QUALIFIEDCOUNT")+"</td>";       //合格数
			htmls += "<td>"+qualifiedRate+"</td>";                       //合格率%
			htmls += "</tr>";
		}
		if (pcList.size() >= 2) {
			float num= (float)SumQualifiedCount/sumSamplingCount;	
			String sumQualifiedRate = df.format(100*num)+"%";
			// 合计行
			htmls += "<tr>";
			htmls += "<td>合计</td>";              
			htmls += "<td>"+sumSamplingCount+"</td>";           
			htmls += "<td>"+SumQualifiedCount+"</td>";      
			htmls += "<td>"+sumQualifiedRate+"</td>";                      
			htmls += "</tr>";	
		}
		htmls += "</tbody>";

		return htmls;
	}

	/**
	 * 各类畜产品监测合格率情况导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportLvqs(Map<String, Object> paramMap) {
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getLivestockVarietyQualifiedSituation", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("AGRNAME", "title#KV#畜产品种类#EM#width#KV#20");
		headerMap.put("SAMPLINGCOUNT", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("QUALIFIEDCOUNT", "title#KV#合格数#EM#width#KV#20");
		headerMap.put("QUALIFIEDRATE", "title#KV#合格率%#EM#width#KV#20");
		pcList.add(0, headerMap);

		//计算合计
		int sumSamplingCount = 0;
		int SumQualifiedCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpQualifiedCount = Integer.parseInt((String)datamap.get("QUALIFIEDCOUNT"));
			
			String qualifiedRate = df.format(100*(float)tmpQualifiedCount/tmpSamplingCount) + "%";
			sumSamplingCount += tmpSamplingCount;
			SumQualifiedCount += tmpQualifiedCount;
			
			datamap.put("QUALIFIEDRATE", qualifiedRate);
		}
		if (pcList.size()-1 >= 2) {
			float num= (float)SumQualifiedCount/sumSamplingCount;	
			String sumQualifiedRate = df.format(100*num)+"%";
			// 设置合计行
			LinkedHashMap<String, Object> sumDataMap = new LinkedHashMap<String, Object>();
			sumDataMap.put("AGRNAME", "合计");
			sumDataMap.put("SAMPLINGCOUNT", String.valueOf(sumSamplingCount));
			sumDataMap.put("QUALIFIEDCOUNT", String.valueOf(SumQualifiedCount));
			sumDataMap.put("QUALIFIEDRATE", sumQualifiedRate);
			
			pcList.add(sumDataMap);
		}
		return pcList;
	}
	/**
	 * 各监测环节畜产品监测合格率情况取得
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public String getMonitoringLinkLivestockQualifiedSituation(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getMonitoringLinkLivestockQualifiedSituation", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >监测环节</th>";
		htmls += "<th class='center hidden-480' >抽样总数</th>";
		htmls += "<th class='center hidden-480' >合格数</th>";
		htmls += "<th class='center hidden-480' >合格率%</th>";
		htmls += "</thead></tr>";	
		htmls += "<tbody>";
		
		//计算合计
		int sumSamplingCount = 0;
		int SumQualifiedCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpQualifiedCount = Integer.parseInt((String)datamap.get("QUALIFIEDCOUNT"));
			
			sumSamplingCount += tmpSamplingCount;
			SumQualifiedCount += tmpQualifiedCount;
	
			String qualifiedRate = df.format(100*(float)tmpQualifiedCount/tmpSamplingCount) + "%";

			htmls += "<tr>";
			htmls += "<td>"+datamap.get("MONITORINGLINK")+"</td>";       //监测环节
			htmls += "<td>"+datamap.get("SAMPLINGCOUNT")+"</td>";        //抽样总数
			htmls += "<td>"+datamap.get("QUALIFIEDCOUNT")+"</td>";       //合格数
			htmls += "<td>"+qualifiedRate+"</td>";                       //合格率%
			htmls += "</tr>";
		}
		if (pcList.size() >= 2) {
			float num= (float)SumQualifiedCount/sumSamplingCount;	
			String sumQualifiedRate = df.format(100*num)+"%";
			// 合计行
			htmls += "<tr>";
			htmls += "<td>合计</td>";              
			htmls += "<td>"+sumSamplingCount+"</td>";           
			htmls += "<td>"+SumQualifiedCount+"</td>";      
			htmls += "<td>"+sumQualifiedRate+"</td>";                      
			htmls += "</tr>";
		}
		htmls += "</tbody>";

		return htmls;
	}
	
	/**
	 * 各监测环节畜产品监测合格率情况导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportMLqs(Map<String, Object> paramMap) {
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getMonitoringLinkLivestockQualifiedSituation", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("MONITORINGLINK", "title#KV#监测环节#EM#width#KV#20");
		headerMap.put("SAMPLINGCOUNT", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("QUALIFIEDCOUNT", "title#KV#合格数#EM#width#KV#20");
		headerMap.put("QUALIFIEDRATE", "title#KV#合格率%#EM#width#KV#20");
		pcList.add(0, headerMap);

		//计算合计
		int sumSamplingCount = 0;
		int SumQualifiedCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpQualifiedCount = Integer.parseInt((String)datamap.get("QUALIFIEDCOUNT"));
			
			String qualifiedRate = df.format(100*(float)tmpQualifiedCount/tmpSamplingCount) + "%";
			sumSamplingCount += tmpSamplingCount;
			SumQualifiedCount += tmpQualifiedCount;
			
			datamap.put("QUALIFIEDRATE", qualifiedRate);
		}
		if (pcList.size()-1 >= 2) {
			float num= (float)SumQualifiedCount/sumSamplingCount;	
			String sumQualifiedRate = df.format(100*num)+"%";
			// 设置合计行
			LinkedHashMap<String, Object> sumDataMap = new LinkedHashMap<String, Object>();
			sumDataMap.put("MONITORINGLINK", "合计");
			sumDataMap.put("SAMPLINGCOUNT", String.valueOf(sumSamplingCount));
			sumDataMap.put("QUALIFIEDCOUNT", String.valueOf(SumQualifiedCount));
			sumDataMap.put("QUALIFIEDRATE", sumQualifiedRate);
			
			pcList.add(sumDataMap);
		}

		return pcList;
	}
	
	/**
	 * 不同品种超标情况统计表取得
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public String getDiffVarietyOverStanderd (PlantSituationEntity plantSituationEntity){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getLivestockVarietyOverStandardSituation", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >产品名称</th>";
		htmls += "<th class='center hidden-480' >抽样数</th>";
		htmls += "<th class='center hidden-480' >检出数</th>";
		htmls += "<th class='center hidden-480' >超标数</th>";
		htmls += "<th class='center hidden-480' >超标率</th>";
		htmls += "<th class='center hidden-480' >检出率</th>";
		htmls += "</thead></tr>";	
		htmls += "<tbody>";
		
		//计算合计
		int sumSamplingCount = 0;
		int sumDetetionCount = 0;
		int sumOverStanderdCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> dataMap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)dataMap.get("SAMPLINGCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)dataMap.get("DETETIONCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)dataMap.get("OVERSTANDERDCOUNT"));

			sumSamplingCount += tmpSamplingCount;
			sumDetetionCount += tmpDetetionCount;
			sumOverStanderdCount += tmpOverStanderdCount;
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";

			htmls += "<tr>";
			htmls += "<td>"+dataMap.get("AGRNAME")+"</td>";              //产品名称
			htmls += "<td>"+dataMap.get("SAMPLINGCOUNT")+"</td>";        //抽样总数
			htmls += "<td>"+dataMap.get("DETETIONCOUNT")+"</td>";        //检出数
			htmls += "<td>"+dataMap.get("OVERSTANDERDCOUNT")+"</td>";    //超标数
			htmls += "<td>"+overStanderdRate+"</td>";                    //超标率
			htmls += "<td>"+detectionRate+"</td>";                       //检出率
			
			htmls += "</tr>";
		}
		if (pcList.size() >= 2) {
			float num= (float)sumOverStanderdCount/sumSamplingCount;	
			String sumOverStanderdRate = df.format(100*num)+"%";
			float num1= (float)sumDetetionCount/sumSamplingCount;	
			String sumDetetionRate = df.format(100*num1)+"%";
			// 合计行
			htmls += "<tr>";
			htmls += "<td>合计</td>";              
			htmls += "<td>"+sumSamplingCount+"</td>";           
			htmls += "<td>"+sumDetetionCount+"</td>";      
			htmls += "<td>"+sumOverStanderdCount+"</td>";
			htmls += "<td>"+sumOverStanderdRate+"</td>";   
			htmls += "<td>"+sumDetetionRate+"</td>";   
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		return htmls;
	}
	
	/**
	 * 不同品种超标情况统计表导出
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportDvos(Map<String, Object> paramMap) {
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getLivestockVarietyOverStandardSituation", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("agrName", "title#KV#产品名称#EM#width#KV#30");
		headerMap.put("samplingCount", "title#KV#抽样数#EM#width#KV#20");
		headerMap.put("detetionCount", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("overStanderdCount", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("overStanderdRate", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("detetionRate", "title#KV#检出率#EM#width#KV#20");
		pcList.add(0, headerMap);
		
		// 计算合计值
		int sumSamplingCount = 0;
		int sumDetetionCount = 0;
		int sumOverStanderdCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> dataMap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)dataMap.get("SAMPLINGCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)dataMap.get("DETETIONCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)dataMap.get("OVERSTANDERDCOUNT"));

			sumSamplingCount += tmpSamplingCount;
			sumDetetionCount += tmpDetetionCount;
			sumOverStanderdCount += tmpOverStanderdCount;
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
			dataMap.put("OVERSTANDERDRATE", overStanderdRate);
			dataMap.put("DETETIONRATE", detectionRate);
		}
		if (pcList.size()-1 >= 2) {
			float num = (float)sumOverStanderdCount/sumSamplingCount;
			float num1 = (float)sumDetetionCount/sumSamplingCount;
			
			String sumOverStanderdRate = df.format(100*num)+"%";
			String sumDetetionRate = df.format(100*num1)+"%";
			
			// 设置合计行
			LinkedHashMap<String, Object> sumDataMap = new LinkedHashMap<String, Object>();
			sumDataMap.put("AGRNAME", "合计");
			sumDataMap.put("SAMPLINGCOUNT", String.valueOf(sumSamplingCount));
			sumDataMap.put("DETETIONCOUNT", String.valueOf(sumDetetionCount));
			sumDataMap.put("OVERSTANDERDCOUNT", String.valueOf(sumOverStanderdCount));
			sumDataMap.put("OVERSTANDERDRATE", sumOverStanderdRate);
			sumDataMap.put("DETETIONRATE", sumDetetionRate);

			pcList.add(sumDataMap);
		}
		return pcList;
	}
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	
	//chenyingqin↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓20140306
	/**
	 * 畜禽检测统计类表取得通用方法
	 *  用于【省辖市区畜禽产品监测结果】，【县级畜禽产品监测结果】，【全省生鲜乳监测结果】，【各市超标样品情况表】
	 */
	@Override
	public String getCommonLiveStockSampleOverProofList(PlantSituationEntity plantSituationEntity, String flg) {
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);
		String sqlId1 = "getSampleOverProof"+flg;
		String sqlId2 = "getSjzlLstForCounty"+flg;
		String sqlId3 = "getSampleOverProofRight"+flg;
		
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, selCodition);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, selCodition);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, selCodition);

		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 1);
		
		if (leftList.size() == 0) {
			return "";
		}
		
		String htmls = "<thead><tr>";
		if ("1".equals(flg) || "2".equals(flg) || "3".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>排名</th>";
		} else {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>序号</th>";
		}
		if ("2".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>区县</th>";
		} else {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>地市</th>";
		}
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>抽样总数</th>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>合格数</th>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>合格率</th>";
		for (String header : headerMonList) {
			htmls += "<th class='center hidden-480' colspan=3 style='vertical-align: middle !important'>"+header+"</th>";
		}
		htmls += "</tr>";
		if (headerMonList.size() > 0) {
			htmls += "<tr>";
			for (String header1 : headerMonList) {
				htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>抽样数</th>";
				htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格数</th>";
				htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格率</th>";
			}
			htmls += "</tr>";
		}

		htmls += "</thead>";
		htmls += "<tbody>";
		int indexRow = 0;
		int sumLeftSamplingCount = 0;
		int sumLeftQualifiedCount = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			htmls += "<tr>";
			if ("1".equals(flg) || "2".equals(flg) || "3".equals(flg)) {
				htmls += "<td>"+leftOne.getRank()+"</td>";
			} else {
				htmls += "<td>"+(indexRow+1)+"</td>";
			}
			htmls += "<td>"+leftOne.getCountyArea()+"</td>";
			htmls += "<td>"+leftOne.getSamplingCount()+"</td>";
			htmls += "<td>"+leftOne.getQualifiedCount()+"</td>";
			htmls += "<td>"+df.format(100*(float)leftOne.getQualifiedCount()/leftOne.getSamplingCount()) +"%"+"</td>";
			
			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			for (SampleOverProofEntity rightOne : rightOneList) {
				int samplingCount = rightOne.getSamplingCount();
				int qualifiedCount = rightOne.getQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (samplingCount == -1) {
						sumRight.setSamplingCount(0);
						sumRight.setQualifiedCount(0);
					} else {
						sumRight.setSamplingCount(samplingCount);
						sumRight.setQualifiedCount(qualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (samplingCount != -1) {
						sumRight.setSamplingCount(sumRight.getSamplingCount()+ samplingCount);
						sumRight.setQualifiedCount(sumRight.getQualifiedCount()+ qualifiedCount);
					}		
				}
				if (samplingCount == -1) {
					htmls += "<td>-</td>";
					htmls += "<td>-</td>";
					htmls += "<td>-</td>";
				} else {		
					htmls += "<td>"+rightOne.getSamplingCount()+"</td>";
					htmls += "<td>"+rightOne.getQualifiedCount()+"</td>";
					htmls += "<td>"+df.format(100*(float)rightOne.getQualifiedCount()/rightOne.getSamplingCount()) +"%"+"</td>";
				}
				indexCol++;
			}
			htmls += "</tr>";
			sumLeftSamplingCount += leftOne.getSamplingCount();
			sumLeftQualifiedCount += leftOne.getQualifiedCount();
			indexRow++;
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			String sumLeftQualifiedRate = "0.0%";
			if (sumLeftSamplingCount != 0) {
				sumLeftQualifiedRate = df.format(100*(float)sumLeftQualifiedCount/sumLeftSamplingCount) +"%";
			}
			htmls += "<tr>";
			htmls += "<td id='colspanR'>合计</td>";
			htmls += "<td id='colspanL'></td>";
			htmls += "<td>"+sumLeftSamplingCount+"</td>";
			htmls += "<td>"+sumLeftQualifiedCount+"</td>";
			htmls += "<td>"+sumLeftQualifiedRate+"</td>";
			for (SampleOverProofEntity sumRit : sumRightList) {
				htmls += "<td>"+sumRit.getSamplingCount()+"</td>";
				htmls += "<td>"+sumRit.getQualifiedCount()+"</td>";
				if (sumRit.getSamplingCount() != 0) {
					htmls += "<td>"+df.format(100*(float)sumRit.getQualifiedCount()/sumRit.getSamplingCount()) +"%"+"</td>";
				} else {
					htmls += "<td>0.0%</td>";
				}
				
			}
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		
		return htmls;
	}
	
	
	private List<List<SampleOverProofEntity>> setInitRes(int leftListCount, List<String> headerMonList, List<SampleOverProofEntity> rightList, int flg) {
		List<List<SampleOverProofEntity>> resultList = new ArrayList<List<SampleOverProofEntity>>();
		for (int i = 0; i< leftListCount; i++) {
			List<SampleOverProofEntity> dList = new ArrayList<SampleOverProofEntity>();
			for (String header : headerMonList) {
				SampleOverProofEntity de = new SampleOverProofEntity();
				if (flg == 1) {
					de.setMonitoringLink(header);
				} else if (flg == 2) {
					de.setAgrName(header);
				} else if (flg == 3) {
					de.setPollName(header);
				}
				de.setSamplingCount(-1);
				de.setQualifiedCount(-1);
				de.setDetectionCount(-1);
				de.setUnQualifiedCount(-1);
				dList.add(de);
			}
			resultList.add(dList);
		}

		int count = 0;
		boolean firstFlg = true;
		String tmpName="";
		String compareName="";
		for (SampleOverProofEntity agrst : rightList) {
			tmpName = agrst.getCountyArea();
			if (firstFlg) {
				compareName =tmpName;
				firstFlg = false;
			}
			if (!StringUtils.equals(tmpName, compareName)) {
				count++;
			}
			setLoadRes(resultList, count, agrst, flg);
		
			compareName =tmpName;
		}
		return resultList;
	}
	
	private void setLoadRes(List<List<SampleOverProofEntity>> initList, int index, SampleOverProofEntity sampleOverProofEntity, int flg) {	
		List<SampleOverProofEntity> decDataList = initList.get(index);
		for (SampleOverProofEntity decData : decDataList) {
			if (flg == 1) {
				if (StringUtils.equals(decData.getMonitoringLink(), sampleOverProofEntity.getMonitoringLink())) {
					decData.setRank(sampleOverProofEntity.getRank());
					decData.setSamplingCount(sampleOverProofEntity.getSamplingCount());
					decData.setQualifiedCount(sampleOverProofEntity.getQualifiedCount());
					//decData.setQualifiedRate(df.format(100*(float)sampleOverProofEntity.getQualifiedCount()/sampleOverProofEntity.getSamplingCount()) + "%");
				}
			} else if (flg == 2) {
				if (StringUtils.equals(decData.getAgrName(), sampleOverProofEntity.getAgrName())) {
					decData.setSamplingCount(sampleOverProofEntity.getSamplingCount());
					decData.setQualifiedCount(sampleOverProofEntity.getQualifiedCount());
					//decData.setQualifiedRate(df.format(100*(float)sampleOverProofEntity.getQualifiedCount()/sampleOverProofEntity.getSamplingCount()) + "%");
				}
			} else if (flg == 3) {
				if (StringUtils.equals(decData.getPollName(), sampleOverProofEntity.getPollName())) {
					decData.setDetectionCount(sampleOverProofEntity.getDetectionCount());
					decData.setUnQualifiedCount(sampleOverProofEntity.getUnQualifiedCount());
				}
			}
	
		}
	}
	
	/**
	 * 畜禽检测统计类表导出通用方法
	 *  用于【省辖市区畜禽产品监测结果】，【县级畜禽产品监测结果】，【全省生鲜乳监测结果】，【各市超标样品情况表】
	 */
	@Override
	public List<Map<String, Object>> getCommonLiveStockExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		String flg = (String)paramMap.get("flg");
		String sqlId1 = "getSampleOverProof"+flg;
		String sqlId2 = "getSjzlLstForCounty"+flg;
		String sqlId3 = "getSampleOverProofRight"+flg;

		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, paramMap);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, paramMap);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, paramMap);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 1);
		
		// 表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		if ("1".equals(flg) || "2".equals(flg) || "3".equals(flg)) {
			headerMap.put("TITLE_1", "title#KV#排序#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		} else {
			headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		}
		if ("2".equals(flg)) {
			headerMap.put("TITLE_2", "title#KV#区县#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		} else {
			headerMap.put("TITLE_2", "title#KV#地市#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		}	
		headerMap.put("TITLE_3", "title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
		headerMap.put("TITLE_4", "title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
		headerMap.put("TITLE_5", "title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#4#EM#mergeLastCol#KV#4");
		String title = "TITLE_";
		int colNum = 6;
		int i = 0;
		for (String header : headerMonList) {
			headerMap.put(title+colNum, "title#KV#"+header+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum ++;
			headerMap.put(title+colNum,"");
			colNum ++;
			headerMap.put(title+colNum,"");
			colNum ++;
			i++;
		}
	    colNum = 6;
		i = 0;
		for (String header1 : headerMonList) {
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+4)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
			i++;
		}
		
		resultList.add(headerMap);
		resultList.add(headerMap2);
		int indexRow = 0;
		int sumLeftSamplingCount = 0;
		int sumLeftQualifiedCount = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			Map<String, Object> datamap = new LinkedHashMap<String, Object>();
			if ("1".equals(flg) || "2".equals(flg) || "3".equals(flg)) {
				datamap.put("TITLE_1", leftOne.getRank());
			} else {
				datamap.put("TITLE_1", indexRow+1);
			}
			datamap.put("TITLE_2", leftOne.getCountyArea());
			datamap.put("TITLE_3", leftOne.getSamplingCount());
			datamap.put("TITLE_4", leftOne.getQualifiedCount());
			datamap.put("TITLE_5", df.format(100*(float)leftOne.getQualifiedCount()/leftOne.getSamplingCount()) +"%");
			
			
			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			colNum = 6;
			for (SampleOverProofEntity rightOne : rightOneList) {
				int samplingCount = rightOne.getSamplingCount();
				int qualifiedCount = rightOne.getQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (samplingCount == -1) {
						sumRight.setSamplingCount(0);
						sumRight.setQualifiedCount(0);
					} else {
						sumRight.setSamplingCount(samplingCount);
						sumRight.setQualifiedCount(qualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (samplingCount != -1) {
						sumRight.setSamplingCount(sumRight.getSamplingCount()+ samplingCount);
						sumRight.setQualifiedCount(sumRight.getQualifiedCount()+ qualifiedCount);
					}		
				}
				if (samplingCount == -1) {
					datamap.put(title+colNum, "-");
					colNum++;
					datamap.put(title+colNum, "-");
					colNum++;
					datamap.put(title+colNum, "-");
					colNum++;
				} else {
					datamap.put(title+colNum, rightOne.getSamplingCount());
					colNum++;
					datamap.put(title+colNum, rightOne.getQualifiedCount());
					colNum++;
					datamap.put(title+colNum, df.format(100*(float)rightOne.getQualifiedCount()/rightOne.getSamplingCount()) +"%");
					colNum++;
				}
				indexCol++;
			}
			sumLeftSamplingCount += leftOne.getSamplingCount();
			sumLeftQualifiedCount += leftOne.getQualifiedCount();
			indexRow++;
			resultList.add(datamap);
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			String sumLeftQualifiedRate = "0.0%";
			if (sumLeftSamplingCount != 0) {
				sumLeftQualifiedRate = df.format(100*(float)sumLeftQualifiedCount/sumLeftSamplingCount) +"%";
			}

			Map<String, Object> datamap1 = new LinkedHashMap<String, Object>();
			datamap1.put("TITLE_1", "合计");
			datamap1.put("TITLE_2", "");
			datamap1.put("TITLE_3", sumLeftSamplingCount);
			datamap1.put("TITLE_4", sumLeftQualifiedCount);
			datamap1.put("TITLE_5", sumLeftQualifiedRate);
			//datamap.put("TITLE_5", );
			colNum = 6;
			for (SampleOverProofEntity sumRit : sumRightList) {
				datamap1.put(title+colNum, sumRit.getSamplingCount());
				colNum++;
				datamap1.put(title+colNum, sumRit.getQualifiedCount());
				colNum++;
				if (sumRit.getSamplingCount() != 0) {
					datamap1.put(title+colNum, df.format(100*(float)sumRit.getQualifiedCount()/sumRit.getSamplingCount()) +"%");
				} else {
					datamap1.put(title+colNum, "0.0%");
				}
				colNum++;
			}
			resultList.add(datamap1);
		}
		return resultList;
	}
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑20140306
	
	//新增统计表↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓20140402
	/**
	 * 畜禽检测统计类表取得通用方法（新增统计表）
	 *  用于【市各类畜产品监测合格率情况】，【区县各类畜产品监测合格率情况】
	 */
	@Override
	public Map<String, Object> getCommonLiveStockSitu(PlantSituationEntity plantSituationEntity, String flg) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);

		String sqlId1 = "getCommLeftLiveStockSitu"+flg;
		String sqlId2 = "getCommHeaderLiveStockSitu"+flg;
		String sqlId3 = "getCommRightLiveStockSitu"+flg;
		
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, selCodition);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, selCodition);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, selCodition);

		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 2);
		
		if (leftList.size() == 0) {
			return resultmap;
		}
		
		String htmls = "<thead><tr>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>序号</th>";
		if ("1".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>市别</th>";
		} else if ("2".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>区县</th>";
		}
		
		for (String header : headerMonList) {
			htmls += "<th class='center hidden-480' colspan=3 style='vertical-align: middle;align:center !important'>"+header+"</th>";
		}
		htmls += "<th class='center hidden-480' colspan=3  style='vertical-align: middle !important'>合计</th>";
		htmls += "</tr>";
		htmls += "<tr>";
		for (String header1 : headerMonList) {
			htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>抽样数</th>";
			htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格数</th>";
			htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格率</th>";
		}
		htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>抽样总数</th>";
		htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格数</th>";
		htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>合格率</th>";
		htmls += "</tr>";
		htmls += "</thead>";
		htmls += "<tbody>";
		int indexRow = 0;
		int sumLeftSamplingCount = 0;
		int sumLeftQualifiedCount = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			htmls += "<tr>";
			htmls += "<td>"+(indexRow+1)+"</td>";	
			htmls += "<td>"+leftOne.getCountyArea()+"</td>";	
			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			for (SampleOverProofEntity rightOne : rightOneList) {
				int samplingCount = rightOne.getSamplingCount();
				int qualifiedCount = rightOne.getQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (samplingCount == -1) {
						sumRight.setSamplingCount(0);
						sumRight.setQualifiedCount(0);
					} else {
						sumRight.setSamplingCount(samplingCount);
						sumRight.setQualifiedCount(qualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (samplingCount != -1) {
						sumRight.setSamplingCount(sumRight.getSamplingCount()+ samplingCount);
						sumRight.setQualifiedCount(sumRight.getQualifiedCount()+ qualifiedCount);
					}		
				}
				if (samplingCount == -1) {
					htmls += "<td>-</td>";
					htmls += "<td>-</td>";
					htmls += "<td>-</td>";
				} else {		
					htmls += "<td>"+rightOne.getSamplingCount()+"</td>";
					htmls += "<td>"+rightOne.getQualifiedCount()+"</td>";
					htmls += "<td>"+df.format(100*(float)rightOne.getQualifiedCount()/rightOne.getSamplingCount()) +"%"+"</td>";
				}
				indexCol++;
			}
			htmls += "<td>"+leftOne.getSamplingCount()+"</td>";
			htmls += "<td>"+leftOne.getQualifiedCount()+"</td>";
			htmls += "<td>"+df.format(100*(float)leftOne.getQualifiedCount()/leftOne.getSamplingCount()) +"%"+"</td>";
			htmls += "</tr>";
			sumLeftSamplingCount += leftOne.getSamplingCount();
			sumLeftQualifiedCount += leftOne.getQualifiedCount();
			indexRow++;
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			String sumLeftQualifiedRate = "0.0%";
			if (sumLeftSamplingCount != 0) {
				sumLeftQualifiedRate = df.format(100*(float)sumLeftQualifiedCount/sumLeftSamplingCount) +"%";
			}
			htmls += "<tr>";
			htmls += "<td id='colspanR'>合计</td>";
			htmls += "<td id='colspanL'></td>";
			for (SampleOverProofEntity sumRit : sumRightList) {
				htmls += "<td>"+sumRit.getSamplingCount()+"</td>";
				htmls += "<td>"+sumRit.getQualifiedCount()+"</td>";
				if (sumRit.getSamplingCount() != 0) {
					htmls += "<td>"+df.format(100*(float)sumRit.getQualifiedCount()/sumRit.getSamplingCount()) +"%"+"</td>";
				} else {
					htmls += "<td>0.0%</td>";
				}
				
			}
			htmls += "<td>"+sumLeftSamplingCount+"</td>";
			htmls += "<td>"+sumLeftQualifiedCount+"</td>";
			htmls += "<td>"+sumLeftQualifiedRate+"</td>";
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		resultmap.put("headerSize", headerMonList.size());
		resultmap.put("htmls", htmls);
		return resultmap;
	}
	
	/**
	 * 畜禽检测统计类表导出通用方法
	 *  用于【市各类畜产品监测合格率情况】，【区县各类畜产品监测合格率情况】
	 */
	@Override
	public List<Map<String, Object>> getCommonLiveStockSituExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		String flg = (String)paramMap.get("flg");
		String sqlId1 = "getCommLeftLiveStockSitu"+flg;
		String sqlId2 = "getCommHeaderLiveStockSitu"+flg;
		String sqlId3 = "getCommRightLiveStockSitu"+flg;
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, paramMap);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, paramMap);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, paramMap);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 2);
		
		// 表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
	
		if ("1".equals(flg)) {
			headerMap.put("TITLE_2", "title#KV#市别#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		} else if ("2".equals(flg)){
			headerMap.put("TITLE_2", "title#KV#区县#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		}	
		String title = "TITLE_";
		int colNum = 3;
		int i = 0;
		for (String header : headerMonList) {
			headerMap.put(title+colNum, "title#KV#"+header+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+(i*3+2)+"#EM#mergeLastCol#KV#"+(i*3+4));
			colNum ++;
			headerMap.put(title+colNum,"");
			colNum ++;
			headerMap.put(title+colNum,"");
			colNum ++;
			i++;
		}
		headerMap.put(title+(headerMonList.size()*3 + 3), "title#KV#合计#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#0#EM#mergeFirstCol#KV#"+(headerMonList.size()*3 + 2)+"#EM#mergeLastCol#KV#"+ (headerMonList.size()*3 + 4));
	    colNum = 3;
		i = 0;
		for (String header1 : headerMonList) {
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(i*3+2)+"#EM#mergeLastCol#KV#"+(i*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(i*3+3)+"#EM#mergeLastCol#KV#"+(i*3+3));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(i*3+4)+"#EM#mergeLastCol#KV#"+(i*3+4));
			colNum++;
			i++;
		}
		headerMap2.put(title+(headerMonList.size()*3 + 3), "title#KV#抽样总数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(headerMonList.size()*3 + 2)+"#EM#mergeLastCol#KV#"+(headerMonList.size()*3 + 2));
		headerMap2.put(title+(headerMonList.size()*3 + 4), "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(headerMonList.size()*3 + 3)+"#EM#mergeLastCol#KV#"+(headerMonList.size()*3 + 3));
		headerMap2.put(title+(headerMonList.size()*3 + 5), "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(headerMonList.size()*3 + 4)+"#EM#mergeLastCol#KV#"+(headerMonList.size()*3 + 4));
		resultList.add(headerMap);
		resultList.add(headerMap2);
		int indexRow = 0;
		int sumLeftSamplingCount = 0;
		int sumLeftQualifiedCount = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			Map<String, Object> datamap = new LinkedHashMap<String, Object>();
			datamap.put("TITLE_1", indexRow+1);
			datamap.put("TITLE_2", leftOne.getCountyArea());

			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			colNum = 3;
			for (SampleOverProofEntity rightOne : rightOneList) {
				int samplingCount = rightOne.getSamplingCount();
				int qualifiedCount = rightOne.getQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (samplingCount == -1) {
						sumRight.setSamplingCount(0);
						sumRight.setQualifiedCount(0);
					} else {
						sumRight.setSamplingCount(samplingCount);
						sumRight.setQualifiedCount(qualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (samplingCount != -1) {
						sumRight.setSamplingCount(sumRight.getSamplingCount()+ samplingCount);
						sumRight.setQualifiedCount(sumRight.getQualifiedCount()+ qualifiedCount);
					}		
				}
				if (samplingCount == -1) {
					datamap.put(title+colNum, "-");
					colNum++;
					datamap.put(title+colNum, "-");
					colNum++;
					datamap.put(title+colNum, "-");
					colNum++;
				} else {
					datamap.put(title+colNum, rightOne.getSamplingCount());
					colNum++;
					datamap.put(title+colNum, rightOne.getQualifiedCount());
					colNum++;
					datamap.put(title+colNum, df.format(100*(float)rightOne.getQualifiedCount()/rightOne.getSamplingCount()) +"%");
					colNum++;
				}
				indexCol++;
			}
			sumLeftSamplingCount += leftOne.getSamplingCount();
			sumLeftQualifiedCount += leftOne.getQualifiedCount();
			
			datamap.put(title+(headerMonList.size()*3 + 3), leftOne.getSamplingCount());
			datamap.put(title+(headerMonList.size()*3 + 4), leftOne.getQualifiedCount());
			datamap.put(title+(headerMonList.size()*3 + 5), df.format(100*(float)leftOne.getQualifiedCount()/leftOne.getSamplingCount()) +"%");
			indexRow++;
			resultList.add(datamap);
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			String sumLeftQualifiedRate = "0.0%";
			if (sumLeftSamplingCount != 0) {
				sumLeftQualifiedRate = df.format(100*(float)sumLeftQualifiedCount/sumLeftSamplingCount) +"%";
			}

			Map<String, Object> datamap1 = new LinkedHashMap<String, Object>();
			datamap1.put("TITLE_1", "合计");
			datamap1.put("TITLE_2", "");

			//datamap.put("TITLE_5", );
			colNum = 3;
			for (SampleOverProofEntity sumRit : sumRightList) {
				datamap1.put(title+colNum, sumRit.getSamplingCount());
				colNum++;
				datamap1.put(title+colNum, sumRit.getQualifiedCount());
				colNum++;
				if (sumRit.getSamplingCount() != 0) {
					datamap1.put(title+colNum, df.format(100*(float)sumRit.getQualifiedCount()/sumRit.getSamplingCount()) +"%");
				} else {
					datamap1.put(title+colNum, "0.0%");
				}
				colNum++;
			}
			datamap1.put(title+(headerMonList.size()*3 + 3), sumLeftSamplingCount);
			datamap1.put(title+(headerMonList.size()*3 + 4), sumLeftQualifiedCount);
			datamap1.put(title+(headerMonList.size()*3 + 5), sumLeftQualifiedRate);
			resultList.add(datamap1);
		}
		return resultList;
	}
	
	
	
	/**
	 * 畜禽检测统计类表取得通用方法（新增统计表）
	 *  用于【市各监测项目不合格情况】，【区县各监测项目不合格情况】
	 */
	@Override
	public Map<String, Object> getCommonDetectionItemSitu(PlantSituationEntity plantSituationEntity, String flg) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			selCodition.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			selCodition.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, selCodition);

		String sqlId1 = "getCommLeftDetectionItemSitu"+flg;
		String sqlId2 = "getCommHeaderDetectionItemSitu"+flg;
		String sqlId3 = "getCommRightDetectionItemSitu"+flg;
		
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, selCodition);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, selCodition);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, selCodition);

		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 3);
		
		if (leftList.size() == 0) {
			return resultmap;
		}
		
		String htmls = "<thead><tr>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>序号</th>";
		if ("1".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>市别</th>";
		} else if ("2".equals(flg)) {
			htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>区县</th>";
		}
		
		for (String header : headerMonList) {
			htmls += "<th class='center hidden-480' colspan=2 style='vertical-align: middle;align:center !important'>"+header+"</th>";
		}
		htmls += "</tr>";
		htmls += "<tr>";
		for (String header1 : headerMonList) {
			htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>检出数</th>";
			htmls += "<th class='center hidden-480' style='vertical-align: middle !important'>不合格次数</th>";
		}
		htmls += "</tr>";
		htmls += "</thead>";
		htmls += "<tbody>";
		int indexRow = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			htmls += "<tr>";
			htmls += "<td>"+(indexRow+1)+"</td>";	
			htmls += "<td>"+leftOne.getCountyArea()+"</td>";	
			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			for (SampleOverProofEntity rightOne : rightOneList) {
				int detectionCount = rightOne.getDetectionCount();
				int unQualifiedCount = rightOne.getUnQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (detectionCount == -1) {
						sumRight.setDetectionCount(0);
						sumRight.setUnQualifiedCount(0);
					} else {
						sumRight.setDetectionCount(detectionCount);
						sumRight.setUnQualifiedCount(unQualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (detectionCount != -1) {
						sumRight.setDetectionCount(sumRight.getDetectionCount()+ detectionCount);
						sumRight.setUnQualifiedCount(sumRight.getUnQualifiedCount()+ unQualifiedCount);
					}		
				}
				if (detectionCount == -1) {
					htmls += "<td>-</td>";
					htmls += "<td>-</td>";
				} else {		
					htmls += "<td>"+rightOne.getDetectionCount()+"</td>";
					htmls += "<td>"+rightOne.getUnQualifiedCount()+"</td>";
				}
				indexCol++;
			}
			htmls += "</tr>";
			indexRow++;
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			htmls += "<tr>";
			htmls += "<td id='colspanR'>合计</td>";
			htmls += "<td id='colspanL'></td>";
			for (SampleOverProofEntity sumRit : sumRightList) {
				htmls += "<td>"+sumRit.getDetectionCount()+"</td>";
				htmls += "<td>"+sumRit.getUnQualifiedCount()+"</td>";			
			}
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		resultmap.put("headerSize", headerMonList.size());
		resultmap.put("htmls", htmls);
		return resultmap;
	}
	
	/**
	 * 畜禽检测统计类表导出通用方法
	 *  用于【市各监测项目不合格情况】，【区县各监测项目不合格情况】
	 */
	@Override
	public List<Map<String, Object>> getCommonDetectionItemSituExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String flg = (String)paramMap.get("flg");
		String sqlId1 = "getCommLeftDetectionItemSitu"+flg;
		String sqlId2 = "getCommHeaderDetectionItemSitu"+flg;
		String sqlId3 = "getCommRightDetectionItemSitu"+flg;
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			paramMap.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			paramMap.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		//selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(projectCode, paramMap);
		// 左边数据集
		List<SampleOverProofEntity> leftList = this.findListByMyBatis(NAME_SPACE+ sqlId1, paramMap);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ sqlId2, paramMap);
		// 右边数据集
		List<SampleOverProofEntity> rightList = this.findListByMyBatis(NAME_SPACE+ sqlId3, paramMap);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList, 3);
		
		// 表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
	
		if ("1".equals(flg)) {
			headerMap.put("TITLE_2", "title#KV#市别#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		} else if ("2".equals(flg)){
			headerMap.put("TITLE_2", "title#KV#区县#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		}	
		String title = "TITLE_";
		int colNum = 3;
		int i = 0;
		for (String header : headerMonList) {
			headerMap.put(title+colNum, "title#KV#"+header+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+(i*2+2)+"#EM#mergeLastCol#KV#"+(i*2+3));
			colNum ++;
			headerMap.put(title+colNum,"");
			colNum ++;
			i++;
		}
	    colNum = 3;
		i = 0;
		for (String header1 : headerMonList) {
			headerMap2.put(title+colNum, "title#KV#检出次数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(i*2+2)+"#EM#mergeLastCol#KV#"+(i*2+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#不合格次数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+(i*2+3)+"#EM#mergeLastCol#KV#"+(i*2+3));
			colNum++;
			i++;
		}
		resultList.add(headerMap);
		resultList.add(headerMap2);
		int indexRow = 0;
		
		List<SampleOverProofEntity> sumRightList = new ArrayList<SampleOverProofEntity>();

		for (SampleOverProofEntity leftOne : leftList) {
			Map<String, Object> datamap = new LinkedHashMap<String, Object>();
			datamap.put("TITLE_1", indexRow+1);
			datamap.put("TITLE_2", leftOne.getCountyArea());

			List<SampleOverProofEntity> rightOneList = editedRightList.get(indexRow);
			int indexCol = 0;
	
			colNum = 3;
			for (SampleOverProofEntity rightOne : rightOneList) {
				int detectionCount = rightOne.getDetectionCount();
				int unQualifiedCount = rightOne.getUnQualifiedCount();

				SampleOverProofEntity sumRight = null;
				if (indexRow == 0) {
					sumRight = new SampleOverProofEntity();
					if (detectionCount == -1) {
						sumRight.setDetectionCount(0);
						sumRight.setUnQualifiedCount(0);
					} else {
						sumRight.setDetectionCount(detectionCount);
						sumRight.setUnQualifiedCount(unQualifiedCount);
					}

					sumRightList.add(sumRight);
				} else {
					sumRight = sumRightList.get(indexCol);
					if (detectionCount != -1) {
						sumRight.setDetectionCount(sumRight.getDetectionCount()+ detectionCount);
						sumRight.setUnQualifiedCount(sumRight.getUnQualifiedCount()+ unQualifiedCount);
					}		
				}
				if (detectionCount == -1) {
					datamap.put(title+colNum, "-");
					colNum++;
					datamap.put(title+colNum, "-");
					colNum++;
				} else {
					datamap.put(title+colNum, rightOne.getDetectionCount());
					colNum++;
					datamap.put(title+colNum, rightOne.getUnQualifiedCount());
					colNum++;
				}
				indexCol++;
			}
			
			indexRow++;
			resultList.add(datamap);
		}
		
		// 计算总计
		if (leftList.size() > 1) {
			Map<String, Object> datamap1 = new LinkedHashMap<String, Object>();
			datamap1.put("TITLE_1", "合计");
			datamap1.put("TITLE_2", "");

			colNum = 3;
			for (SampleOverProofEntity sumRit : sumRightList) {
				datamap1.put(title+colNum, sumRit.getDetectionCount());
				colNum++;
				datamap1.put(title+colNum, sumRit.getUnQualifiedCount());
				colNum++;
			}
			resultList.add(datamap1);
		}
		return resultList;
	}
	//新增统计表↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑20140402
	
	/**
	 *  取得超标样品情况详细表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public void getOverProofSampleInfo(PlantSituationEntity plantSituationEntity, DataGrid dataGrid){
		Map<String,Object> params = new HashMap<String, Object>();
		try {
			// 将entity中属性转成map
			params.putAll(ConverterUtil.entityToMap(plantSituationEntity));
			params.put("beginIndex", plantSituationEntity.getBeginIndex());
			params.put("endIndex", plantSituationEntity.getEndIndex());
		} catch (Exception e) {
			// 发生转换异常的处理
			e.printStackTrace();
		}
		Integer iCount = (Integer) this.getObjectByMyBatis(NAME_SPACE + "getOverProofSampleInfoCount", params);
		dataGrid.setTotal(iCount);
		// 取得超标样品情况详细表
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE + "getOverProofSampleInfo", params);
		dataGrid.setReaults(mapList);
	}
	
	@Override
	public List<Map<String, String>> getSampleOverProofList(PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", getLoginAreaCode());
		selCodition.put("isDis", 3);//3 区县 2 辖市
		return this.findListByMyBatis(NAME_SPACE + "getSampleOverProof", selCodition);
	}
	
	@Override
	public Map<String, Object> getSampleOverProofActList(PlantSituationEntity plantSituationEntity) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", getLoginAreaCode());
		selCodition.put("isDis", 3);//3 区县 2 辖市
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", selCodition);
		
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			selCodition.put("monitoringLink", typecode);
			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", selCodition);
		
			if(lst != null && lst.size() > 0){
				Map<String, String> endMap= lst.get(lst.size()-1);
				endMap.put("typename", typename);
				lst.set(lst.size()-1, endMap);
				rtnMap.put(typename, lst);
			}
		}
		return rtnMap;
	}
	
	/**
	 *  县级畜禽产品监测结果 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportMonitor(Map<String, Object> paramMap){
		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
		paramMap.put("areaCode", getLoginAreaCode());
		paramMap.put("isDis", 3);//3 区县 2 辖市
		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProof", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_0", "title#KV#排名#EM#width#KV#10#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_1", "title#KV#县(市/区)#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		headerMap.put("TITLE_2", "title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
		headerMap.put("TITLE_3", "title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
		headerMap.put("TITLE_4", "title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#4#EM#mergeLastCol#KV#4");
			
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", paramMap);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			paramMap.put("monitoringLink", typecode);
			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", paramMap);
			if(rightLst != null && rightLst.size() > 0){
				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
				endMap.put("typename", typename);
				rightLst.set(rightLst.size()-1, endMap);
				rtnMap.put(typename, rightLst);
			}
		}
		String title = "TITLE_";
		int colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			Map<String, String> map = sjzdLst.get(i);
			String typename = map.get("typename").toString();
			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
		}
		colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			headerMap2.put("TITLE_0", "");
			headerMap2.put("TITLE_1", "");
			headerMap2.put("TITLE_2", "");
			headerMap2.put("TITLE_3", "");
			headerMap2.put("TITLE_4", "");
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+4)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
		}
		rtnLst.add(0, headerMap);
		rtnLst.add(1, headerMap2);
		
		for (int i = 0; i < leftLst.size(); i++) {
			Map<String,Object> map = leftLst.get(i);
			if(i == leftLst.size()-1){
				// 设置合计行
				map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
				colNum = 6;
				for (int j = 0; j < sjzdLst.size(); j++) {
					Map<String, String> mapL = sjzdLst.get(j);
					String typename = mapL.get("typename").toString();
					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
					Map<String,String> rMap  = rVal.get(i);
					if(rMap.get("typename") != null){
						if(rMap.get("typename").toString().equals(typename)){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}else{
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
					}
				}
				rtnLst.add(i+2, map);
				break;
			}
			map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
			
			colNum = 6;
			for (int j = 0; j < sjzdLst.size(); j++) {
				Map<String, String> mapL = sjzdLst.get(j);
				String typename = mapL.get("typename").toString();
				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
				Map<String,String> rMap  = rVal.get(i);
				if(rMap.get("TYPENAME") != null){
					if(rMap.get("TYPENAME").toString().equals(typename)){
						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, this.getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}
				}else{
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
				}
			}
			
			map.remove("CODE");
			rtnLst.add(i+2, map);
		}
		return rtnLst;
	}
	
	/**
	 *  省辖市区畜禽产品监测结果 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportDistractMonitor(Map<String, Object> paramMap){
		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
		paramMap.put("areaCode", getLoginAreaCode());
		paramMap.put("isDis", 2);//3 区县 2 辖市
		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProof", paramMap);
		if(leftLst != null && leftLst.size() > 0){
			for (int i = 0; i < leftLst.size(); i++) {
				Map<String, Object> map= leftLst.get(i);
				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("areaCode", map.get("CODE"));
				String pname = this.getObjectByMyBatis(NAME_SPACE+"getParentCityName",cond);
				map.put("AREANAME", pname);
				leftLst.set(i, map);
			}
		}
		
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_0", "title#KV#排名#EM#width#KV#10#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_1", "title#KV#市级#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		headerMap.put("TITLE_2", "title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
		headerMap.put("TITLE_3", "title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
		headerMap.put("TITLE_4", "title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#4#EM#mergeLastCol#KV#4");
			
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", paramMap);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			paramMap.put("monitoringLink", typecode);
			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", paramMap);
			if(rightLst != null && rightLst.size() > 0){
				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
				endMap.put("typename", typename);
				rightLst.set(rightLst.size()-1, endMap);
				rtnMap.put(typename, rightLst);
			}
		}
		String title = "TITLE_";
		int colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			Map<String, String> map = sjzdLst.get(i);
			String typename = map.get("typename").toString();
			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
		}
		colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			headerMap2.put("TITLE_0", "");
			headerMap2.put("TITLE_1", "");
			headerMap2.put("TITLE_2", "");
			headerMap2.put("TITLE_3", "");
			headerMap2.put("TITLE_4", "");
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+4)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
		}
		rtnLst.add(0, headerMap);
		rtnLst.add(1, headerMap2);
		
		for (int i = 0; i < leftLst.size(); i++) {
			Map<String,Object> map = leftLst.get(i);
			if(i == leftLst.size()){
				// 设置合计行
				map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
				
				colNum = 6;
				for (int j = 0; j < sjzdLst.size(); j++) {
					Map<String, String> mapL = sjzdLst.get(j);
					String typename = mapL.get("typename").toString();
					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
					Map<String,String> rMap  = rVal.get(i);
					if(rMap.get("typename") != null){
						if(rMap.get("typename").toString().equals(typename)){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}else{
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
					}
				}
				rtnLst.add(i+2, map);
				break;
			}
			map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
			
			colNum = 6;
			for (int j = 0; j < sjzdLst.size(); j++) {
				Map<String, String> mapL = sjzdLst.get(j);
				String typename = mapL.get("typename").toString();
				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
				Map<String,String> rMap  = rVal.get(i);
				if(rMap.get("TYPENAME") != null){
					if(rMap.get("TYPENAME").toString().equals(typename)){
						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, this.getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}
				}else{
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
				}
			}
			
			map.remove("CODE");
			rtnLst.add(i+2, map);
		}
		return rtnLst;
	}
	
	@Override
	public List<Map<String, String>> getTableForDistrict(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode",getLoginAreaCode()+"01");
		selCodition.put("isDis", 2);//3 区县 2 辖市
		
		List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE+"getSampleOverProof", selCodition);
		if(lst != null && lst.size() > 0){
			for (int i = 0; i < lst.size(); i++) {
				Map<String, String> map= lst.get(i);
				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("areaCode", map.get("CODE"));
				String pname = this.getObjectByMyBatis(NAME_SPACE+"getParentCityName",cond);
				map.put("AREANAME", pname);
				lst.set(i, map);
			}
		}
		return lst;
	}
	
	@Override
	public Map<String, Object> getTableForDistrictActList(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", getLoginAreaCode());
		selCodition.put("isDis", 2);//3 区县 2 辖市
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", selCodition);
		
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			selCodition.put("monitoringLink", typecode);
			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", selCodition);
		
			if(lst != null && lst.size() > 0){
				Map<String, String> endMap= lst.get(lst.size()-1);
				endMap.put("typename", typename);
				lst.set(lst.size()-1, endMap);
				rtnMap.put(typename, lst);
			}
		}
		return rtnMap;
	}
	
	/**
	 * 可以放开市级和县级的菜单选项链接
	 * 取得当前登录者的地区编码
	 * admin 用户 全省
	 * 其他市级，截取地区编码4位
	 * 其他x县级，截取地区编码6位
	 * @param type 
	 * @return
	 *
	 * @return
	 */
	private String getLoginAreaCode() {
		String areaCode = "";
		if (ResourceUtil.getSessionUserName().getUsertype().equals("0")) {// 管理部门
			String countryCode = ResourceUtil.getSessionUserName().getTSDepart().getAreacode2();
			String cityCode = ResourceUtil.getSessionUserName().getTSDepart().getCode();
			// 当为admin管理员
			if (ResourceUtil.getSessionUserName().getUserName().equals("admin")) {
				areaCode = cityCode.substring(0, 2);
			}else{
				if (StringUtil.isNotEmpty(countryCode)) {
					areaCode = countryCode;
				} else {
					if (StringUtil.isNotEmpty(cityCode)) {
						areaCode = cityCode.substring(0,4);
					}
				}
			}
		}else{
			OrganizationEntity org = this.get(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
			String countryCode = org.getAreacode2();
			String cityCode = org.getAreacode();
			if (StringUtil.isNotEmpty(countryCode)) {
				areaCode = countryCode;
			} else {
				if (StringUtil.isNotEmpty(cityCode)) {
					areaCode = cityCode.substring(0,4);
				}
			}
		}
		return areaCode;
	}
	
	@Override
	public List<Map<String, String>> getFreshMilkMonitor(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", "3201");//getLoginAreaCode());
		return this.findListByMyBatis(NAME_SPACE + "getProvinceFreshMilkMonitor", selCodition);
	}
	
	@Override
	public Map<String, Object> getFreshMilkMonitorAct(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", "3201");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForProvince", selCodition);
		
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			selCodition.put("monitoringLink", typecode);
			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getProvinceFreshMilkMonitorRight", selCodition);
			if(lst != null && lst.size() > 0){
				Map<String, String> endMap= lst.get(lst.size()-1);
				endMap.put("typename", typename);
				lst.set(lst.size()-1, endMap);
				rtnMap.put(typename, lst);
			}
		}
		return rtnMap;
	}
	
	/**
	 *  全省生鲜乳监测结果 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportFreshMilk(Map<String, Object> paramMap){
		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
		paramMap.put("areaCode", getLoginAreaCode());
		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getProvinceFreshMilkMonitor", paramMap);
		
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_0", "title#KV#排名#EM#width#KV#10#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_1", "title#KV#市级#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		headerMap.put("TITLE_2", "title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
		headerMap.put("TITLE_3", "title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
		headerMap.put("TITLE_4", "title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#4#EM#mergeLastCol#KV#4");
			
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForProvince", paramMap);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			paramMap.put("monitoringLink", typecode);
			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getProvinceFreshMilkMonitorRight", paramMap);
			if(rightLst != null && rightLst.size() > 0){
				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
				endMap.put("typename", typename);
				rightLst.set(rightLst.size()-1, endMap);
				rtnMap.put(typename, rightLst);
			}
		}
		String title = "TITLE_";
		int colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			Map<String, String> map = sjzdLst.get(i);
			String typename = map.get("typename").toString();
			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
		}
		colNum = 6;
		for (int i = 0; i < sjzdLst.size(); i++) {
			headerMap2.put("TITLE_0", "");
			headerMap2.put("TITLE_1", "");
			headerMap2.put("TITLE_2", "");
			headerMap2.put("TITLE_3", "");
			headerMap2.put("TITLE_4", "");
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+4)+"#EM#mergeLastCol#KV#"+((i+1)*3+4));
			colNum++;
		}
		rtnLst.add(0, headerMap);
		rtnLst.add(1, headerMap2);
		
		for (int i = 0; i < leftLst.size(); i++) {
			Map<String,Object> map = leftLst.get(i);
			if(i == leftLst.size()-1){
				// 设置合计行
				map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
				colNum = 6;
				for (int j = 0; j < sjzdLst.size(); j++) {
					Map<String, String> mapL = sjzdLst.get(j);
					String typename = mapL.get("typename").toString();
					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
					Map<String,String> rMap  = rVal.get(i);
					if(rMap.get("typename") != null){
						if(rMap.get("typename").toString().equals(typename)){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}else{
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
					}
				}
				rtnLst.add(i+2, map);
				break;
			}
			map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
			
			colNum = 6;
			for (int j = 0; j < sjzdLst.size(); j++) {
				Map<String, String> mapL = sjzdLst.get(j);
				String typename = mapL.get("typename").toString();
				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
				Map<String,String> rMap  = rVal.get(i);
				if(rMap.get("TYPENAME") != null){
					if(rMap.get("TYPENAME").toString().equals(typename)){
						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, this.getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}
				}else{
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
				}
			}
			
			map.remove("CODE");
			rtnLst.add(i+2, map);
		}
		return rtnLst;
	}
	
	public String getViewDateForPCT(Object obj){
		String pctSum = "";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		if(StringUtil.isNotEmpty(obj)){
			pctSum = df.format(100*Double.valueOf(obj.toString())) + "%";
		}
		return pctSum;
	}
	
	@Override
	public List<Map<String, String>> getCitySampleOverProof(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", "3201");//getLoginAreaCode());
		return this.findListByMyBatis(NAME_SPACE + "getCitySampleOverProof", selCodition);
	}
	
	@Override
	public Map<String, Object> getCitySampleOverProofAct(
			PlantSituationEntity plantSituationEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode", "3201");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCity", selCodition);
		
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			selCodition.put("monitoringLink", typecode);
			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getCityOverProofRight", selCodition);
			if(lst != null && lst.size() > 0){
				Map<String, String> endMap= lst.get(lst.size()-1);
				endMap.put("typename", typename);
				lst.set(lst.size()-1, endMap);
				rtnMap.put(typename, lst);
			}
		}
		return rtnMap;
	}
	
	/**
	 * 各市超标样品情况表 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportCityOF(Map<String, Object> paramMap){
		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
		paramMap.put("areaCode", getLoginAreaCode());
		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getCitySampleOverProof", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", 	"title#KV#市级#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_2", 	"title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
		headerMap.put("TITLE_3", 		"title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
		headerMap.put("TITLE_4", 		"title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
			
		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCity", paramMap);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		for (Map<String, String> map : sjzdLst) {
			String typecode = map.get("typecode").toString();
			String typename = map.get("typename").toString();
			paramMap.put("monitoringLink", typecode);
			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getCityOverProofRight", paramMap);
			if(rightLst != null && rightLst.size() > 0){
				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
				endMap.put("typename", typename);
				rightLst.set(rightLst.size()-1, endMap);
				rtnMap.put(typename, rightLst);
			}
		}
		String title = "TITLE_";
		int colNum = 5;
		for (int i = 0; i < sjzdLst.size(); i++) {
			Map<String, String> map = sjzdLst.get(i);
			String typename = map.get("typename").toString();
			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
			headerMap.put(title+colNum,"");
			colNum++;
		}
		colNum = 5;
		for (int i = 0; i < sjzdLst.size(); i++) {
			headerMap2.put("TITLE_1", "");
			headerMap2.put("TITLE_2", "");
			headerMap2.put("TITLE_3", "");
			headerMap2.put("TITLE_4", "");
			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+1));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
			colNum++;
			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
			colNum++;
		}
		rtnLst.add(0, headerMap);
		rtnLst.add(1, headerMap2);
		
		for (int i = 0; i < leftLst.size(); i++) {
			Map<String,Object> map = leftLst.get(i);
			if(i == leftLst.size()-1){
				// 设置合计行
				map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
				
				colNum = 5;
				for (int j = 0; j < sjzdLst.size(); j++) {
					Map<String, String> mapL = sjzdLst.get(j);
					String typename = mapL.get("typename").toString();
					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
					Map<String,String> rMap  = rVal.get(i);
					if(rMap.get("typename") != null){
						if(rMap.get("typename").toString().equals(typename)){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}else{
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
						map.put(title+colNum, "");
						colNum++;
					}
				}
				rtnLst.add(i+2, map);
				break;
			}
			map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
			
			colNum = 5;
			for (int j = 0; j < sjzdLst.size(); j++) {
				Map<String, String> mapL = sjzdLst.get(j);
				String typename = mapL.get("typename").toString();
				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
				Map<String,String> rMap  = rVal.get(i);
				if(rMap.get("TYPENAME") != null){
					if(rMap.get("TYPENAME").toString().equals(typename)){
						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
							map.put(title+colNum, rMap.get("TOTALSUM"));
							colNum++;
							map.put(title+colNum, rMap.get("ISQUALSUM"));
							colNum++;
							map.put(title+colNum, this.getViewDateForPCT(rMap.get("PCTSUM")));
							colNum++;
						}
					}
				}else{
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
					map.put(title+colNum, "");
					colNum++;
				}
			}
			
			map.remove("CODE");
			rtnLst.add(i+2, map);
		}
		return rtnLst;
	}
	
	/**
	 * 取得药物检出及超标情况报表
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> getPollDetectionInfo(Map<String, Object> paramMap){
		// 权限条件
		setUserDataPriv(ConverterUtil.toString(paramMap.get("projectCode")), paramMap);
		if(paramMap.get("projectCode") != null){
			String projectCode = String.valueOf(paramMap.get("projectCode"));
			projectCode = "'" + projectCode.replace(",", "','") + "'";
			paramMap.put("projectCode", projectCode);
		}
		this.findListByMyBatis(NAME_SPACE + "getCountryAndOverList", paramMap);
		return (List<Map<String, Object>>) paramMap.get("result");
	}

	/**
	 * 各监测项目不合格情况
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> getPollDetectionCount(Map<String, Object> paramMap) {
		String projectCodeList = (String)paramMap.get("projectCode");
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			projectCode = projectCodes[0];
		}
		setUserDataPriv(projectCode, paramMap);
		paramMap.put("projectCode", setSplitProjectCode(projectCodeList));
		this.findListByMyBatis(NAME_SPACE + "getPollDetectionCountList", paramMap);
		return (List<Map<String, Object>>) paramMap.get("result");
	}
	
	/**
	 * 各监测项目不合格情况导出数据
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getPollDetectionCountExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultList = getPollDetectionCount(paramMap);
		if (resultList.size() > 0) {
			Map<String, Object> titleMap = new LinkedHashMap<String, Object>();
			Map<String, Object> titleRowMap = resultList.get(0);
			int colIndex = 0;
			// 由于sql没有返回title格式，需要重置title行
			for (String key : titleRowMap.keySet()) {
				titleMap.put("COLUMN_" + colIndex, "title#KV#" + key.replace("'", "") + "#EM#width#KV#AUTO");
				colIndex++;
			}
			// 替换原有行
			resultList.set(0, titleMap);
		}
		return resultList;
	}
	
	private String setSplitProjectCode (String str) {
		String splitProjectCode = "";
		String [] splitArr = str.split(",");
		for (String sa : splitArr) {
			splitProjectCode += "'"+ sa + "',";
		}
		return splitProjectCode.substring(0, splitProjectCode.length() - 1);
	}
}