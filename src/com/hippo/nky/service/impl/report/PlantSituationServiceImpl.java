package com.hippo.nky.service.impl.report;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jeecg.system.pojo.base.TSDepart;
import jeecg.system.pojo.base.TSUser;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.detection.DetectionStatisticalEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.report.PlantSituationEntity;
import com.hippo.nky.entity.report.SampleOverProofEntity;
import com.hippo.nky.entity.report.VegetablesSituationEntity;
import com.hippo.nky.service.report.PlantSituationServiceI;

@Service("plantSituationService")
@Transactional
public class PlantSituationServiceImpl extends CommonServiceImpl implements
		PlantSituationServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.report.PlantSituationEntity.";

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
		
		//监测地区及数量(区县)
		int areaCount = this.getObjectByMyBatis(NAME_SPACE+ "getAreaCount", selConditionMap);
		//监测地区及数量(市辖区)
		int areaCount1 = this.getObjectByMyBatis(NAME_SPACE+ "getAreaCount1", selConditionMap);
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
		
		//样品数量 全部(区县)
		int sampCount1 = this.getObjectByMyBatis(NAME_SPACE+ "getSampleCount1", selConditionMap);
		//样品数量 全部(市辖区)
		int sampCount2 = sampCount - sampCount1; 
		//样品数量 合格数(区县)
		int qualifiedCount1 = (Integer) this.getObjectByMyBatis(NAME_SPACE+ "getSampleCount1", qualifiedCodition);
		//样品数量 合格数(市辖区)
		int qualifiedCount2 = qualifiedCount -qualifiedCount1;
				
		rtnMap.put("areaCount", areaCount);
		rtnMap.put("areaCount1", areaCount1);
		rtnMap.put("argCount", argCount);
		rtnMap.put("sampCount", sampCount);
		rtnMap.put("pollCount", pollCount);
		rtnMap.put("qualifiedCount", qualifiedCount);
		rtnMap.put("sampCount1", sampCount1);
		rtnMap.put("sampCount2", sampCount - sampCount1);
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
		if(sampCount1 != 0){
			double divNum = (double)qualifiedCount1/sampCount1;
			if(divNum == 0.0){
				rtnMap.put("pct1", "0.0%");
			}else{
				rtnMap.put("pct1", df1.format((double)qualifiedCount1/sampCount1));
			}
		}else{
			rtnMap.put("pct1", "0.0%");
		}
		if(sampCount2 != 0){
			double divNum = (double)qualifiedCount2/sampCount2;
			if(divNum == 0.0){
				rtnMap.put("pct2", "0.0%");
			}else{
				rtnMap.put("pct2", df1.format((double)qualifiedCount2/sampCount2));
			}
		}else{
			rtnMap.put("pct2", "0.0%");
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

	/**
	 * 取得管理部门查看数据权限
	 * @return
	 */
	private Map<String, String> getManagementDataPriv(TSUser user) {
		String departid = user.getTSDepart().getId();
		Map<String, String> privMap = new HashMap<String, String>();
	
		// 取得管理部门级别 [1:省级,2:市级,3:区县级]
		TSDepart depart = this.getEntity(TSDepart.class, departid);
		String grade = depart.getGrade();
		//privMap.put("gl", departid);   //管理部门ID
		privMap.put("gl_g", grade);   //管理部门级别
		if (StringUtil.equals(grade, "1")) {
			privMap.put("gl_code1", "320000");
		} else if (StringUtil.equals(grade, "2")){
			privMap.put("gl_code2", depart.getCode());
		} else {
			privMap.put("gl_code2", depart.getCode());
			privMap.put("gl_code3", depart.getAreacode2());
		}
		return privMap;
	}

	public List<Map<String, Object>> getProjectCode(PlantSituationEntity plantSituationEntity) {
		List<Map<String, Object>> mapList = null;
		Map<String, Object> selCodition = new HashMap<String, Object>();
		try {
			selCodition.putAll(ConverterUtil.entityToMap(plantSituationEntity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		TSUser user = ResourceUtil.getSessionUserName();
		String departid = user.getTSDepart().getId();
		// 管理部门
		if (StringUtil.equals(user.getUsertype(),"0")) {
			Map<String, String> pmap = getManagementDataPriv(user);
			selCodition.put("gl_g", pmap.get("gl_g"));
			selCodition.put("gl_code1", pmap.get("gl_code1"));
			selCodition.put("gl_code2", pmap.get("gl_code2"));
			selCodition.put("gl_code3", pmap.get("gl_code3"));
			selCodition.put("orgId", departid);
			mapList = this.findListByMyBatis(NAME_SPACE + "getProjectCodeForDepartment", selCodition);
		// 质检机构
		} else {
			selCodition.put("orgCode",getLoginOrgCode());
			mapList = this.findListByMyBatis(NAME_SPACE + "getProjectCode", selCodition);
		}
		setProjectMapList(mapList);
		return mapList;
	}
	
	/**
	 * 设置项目列表中的map
	 */
	private void setProjectMapList(List<Map<String, Object>> mapList) {
		for(Map<String, Object> projectMap : mapList) {
			if (projectMap.containsKey("publishDate")) {
				projectMap.remove("publishDate");
			}
		}
	}
	
//	/**
//	 * 根据登录的用户取得查看数据的权限
//	 * @return
//	 */
//	private void setUserDataPriv(String projectCode, Map<String, Object> selCodition) {
//		String deparid = ResourceUtil.getSessionUserName().getTSDepart().getId();
//		// 取得用户类型
//		String userType = ResourceUtil.getSessionUserName().getUsertype();
//		
//		// 管理部门
//		if (StringUtils.equals(userType, "0")) {
//			selCodition.put("gl", deparid);   //管理部门ID
//		// 质检机构
//		} else {
//			OrganizationEntity org = this.getEntity(OrganizationEntity.class, deparid);
//
//			CriteriaQuery cq = new CriteriaQuery(MonitoringProjectEntity.class);
//			cq.eq("projectCode", projectCode);
//			cq.eq("leadunit", org.getCode());
//			cq.add();
//			List<MonitoringProjectEntity> projectList =  this.getListByCriteriaQuery(cq, false); 
//			if (projectList != null && projectList.size() > 0) {
//				selCodition.put("qt", org.getCode()); //牵头单位code
//			} else {
//				selCodition.put("pt", org.getCode()); //普通质检机构code
//			}
//			
//		}
//	}

	//chenyingqin↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 不同蔬菜来源超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesMonitoringLinkOverStanderd(PlantSituationEntity plantSituationEntity){
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
	
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		List<VegetablesSituationEntity> resList =  this.findListByMyBatis(NAME_SPACE+ "getVegetablesMonitoringLinkOverStanderd", selCodition);
		
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (VegetablesSituationEntity vsEntity : resList) {
			int samplingCount = vsEntity.getSamplingCount();
			int overStanderdCount = vsEntity.getOverStanderdCount();
			float num= (float)overStanderdCount/samplingCount;
			vsEntity.setOverStanderdRate(df.format(100*num)+"%");
			
			int detetionCount = vsEntity.getDetetionCount();
			float num1= (float)detetionCount/samplingCount;
			vsEntity.setDetetionRate(df.format(100*num1)+"%");
		}
		return resList;
	}
	
	/**
	 * 不同蔬菜来源超标情况统计表列表导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportMlos(Map<String, Object> paramMap) {
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
		// 设置数据权限
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> vList = this.findListByMyBatis(NAME_SPACE+ "getVegetablesMonitoringLinkOverStanderdForExport", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("monitoringLink", "title#KV#来原地#EM#width#KV#30");
		headerMap.put("unitCount", "title#KV#来源地数量#EM#width#KV#20");
		headerMap.put("samplingCount", "title#KV#抽样数#EM#width#KV#20");
		headerMap.put("overStanderdCount", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("overStanderdRate", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("detetionCount", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("detetionRate", "title#KV#检出率#EM#width#KV#20");
		vList.add(0, headerMap);
		
		// 计算合计值
		int sumUnitCount = 0;
		int sumSamplingCount = 0;
		int sumOverStanderdCount = 0;
		int sumDetetionCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < vList.size(); i++) {
			Map<String, Object> dataMap = vList.get(i);
			int tmpUnitCount = Integer.parseInt((String)dataMap.get("UNITCOUNT"));
			int tmpSamplingCount = Integer.parseInt((String)dataMap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)dataMap.get("OVERSTANDERDCOUNT"));
			int tmpDetectionCount = Integer.parseInt((String)dataMap.get("DETETIONCOUNT"));
			
			sumUnitCount += tmpUnitCount;
			sumSamplingCount += tmpSamplingCount;
			sumOverStanderdCount += tmpOverStanderdCount;
			sumDetetionCount += tmpDetectionCount;
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			dataMap.put("OVERSTANDERDRATE", overStanderdRate);
			String detetionRate = df.format(100*(float)tmpDetectionCount/tmpSamplingCount) + "%";
			dataMap.put("DETETIONRATE", detetionRate);
			
		}
		if (vList.size() - 1 >= 2) {
			float num= (float)sumOverStanderdCount/sumSamplingCount;	
			String sumOverStanderdRate = df.format(100*num)+"%";
			float num1= (float)sumDetetionCount/sumSamplingCount;	
			String sumDetetionRate = df.format(100*num1)+"%";
			
			// 设置合计行
			LinkedHashMap<String, Object> sumDataMap = new LinkedHashMap<String, Object>();
			sumDataMap.put("MONITORINGLINK", "合计");
			sumDataMap.put("UNITCOUNT", String.valueOf(sumUnitCount));
			sumDataMap.put("SAMPLINGCOUNT", String.valueOf(sumSamplingCount));
			sumDataMap.put("OVERSTANDERDCOUNT", String.valueOf(sumOverStanderdCount));
			sumDataMap.put("OVERSTANDERDRATE", sumOverStanderdRate);
			sumDataMap.put("DETETIONCOUNT", String.valueOf(sumDetetionCount));
			sumDataMap.put("DETETIONRATE", sumDetetionRate);
			
			vList.add(sumDataMap);
		}

		return vList;
	}
	
	/**
	 * 不同蔬菜类别超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesCategoryOverStanderd(PlantSituationEntity plantSituationEntity){
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
		List<VegetablesSituationEntity> resList = this.findListByMyBatis(NAME_SPACE+ "getVegetablesCategoryOverStanderd", selCodition);
		
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (VegetablesSituationEntity vsEntity : resList) {
			int samplingCount = vsEntity.getSamplingCount();
			int overStanderdCount = vsEntity.getOverStanderdCount();
			float num= (float)overStanderdCount/samplingCount;
			vsEntity.setOverStanderdRate(df.format(100*num)+"%");
		}
		return resList;
	}
	
	/**
	 * 不同蔬菜类别超标情况统计表表列导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportCos(Map<String, Object> paramMap) {
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> vList = this.findListByMyBatis(NAME_SPACE+ "getVegetablesCategoryOverStanderdForExport", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("category", "title#KV#类别#EM#width#KV#30");
		headerMap.put("samplingCount", "title#KV#抽样数#EM#width#KV#20");
		headerMap.put("detetionCount", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("overStanderdCount", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("overStanderdRate", "title#KV#超标率#EM#width#KV#20");
		vList.add(0, headerMap);
		
		// 计算合计值
		int sumSamplingCount = 0;
		int sumDetetionCount = 0;
		int sumOverStanderdCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < vList.size(); i++) {
			Map<String, Object> dataMap = vList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)dataMap.get("SAMPLINGCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)dataMap.get("DETETIONCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)dataMap.get("OVERSTANDERDCOUNT"));

			sumSamplingCount += tmpSamplingCount;
			sumDetetionCount += tmpDetetionCount;
			sumOverStanderdCount += tmpOverStanderdCount;
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			dataMap.put("OVERSTANDERDRATE", overStanderdRate);
		}
		if (vList.size() - 1 >= 2) {
			float num= (float)sumOverStanderdCount/sumSamplingCount;	
			String sumOverStanderdRate = df.format(100*num)+"%";
			
			// 设置合计行
			LinkedHashMap<String, Object> sumDataMap = new LinkedHashMap<String, Object>();
			sumDataMap.put("CATEGORY", "合计");
			sumDataMap.put("SAMPLINGCOUNT", String.valueOf(sumSamplingCount));
			sumDataMap.put("DETETIONCOUNT", String.valueOf(sumDetetionCount));
			sumDataMap.put("OVERSTANDERDCOUNT", String.valueOf(sumOverStanderdCount));
			sumDataMap.put("OVERSTANDERDRATE", sumOverStanderdRate);
			
			vList.add(sumDataMap);
		}
		return vList;
	}
	
	/**
	 * 不同蔬菜品种超标情况统计表 列表取得
	 * @param plantSituationEntity
	 * @return
	 */
	public List<VegetablesSituationEntity> getVegetablesVarietyOverStanderd(PlantSituationEntity plantSituationEntity){
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		List<VegetablesSituationEntity> resList = this.findListByMyBatis(NAME_SPACE+ "getVegetablesVarietyOverStanderd", selCodition);
		
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (VegetablesSituationEntity vsEntity : resList) {
			int samplingCount = vsEntity.getSamplingCount();
			int overStanderdCount = vsEntity.getOverStanderdCount();
			int detetionCount = vsEntity.getDetetionCount();
			float num = (float)overStanderdCount/samplingCount;
			float num1 = (float)detetionCount/samplingCount;
			vsEntity.setOverStanderdRate(df.format(100*num)+"%");
			vsEntity.setDetetionRate(df.format(100*num1)+"%");
			
		}
		return resList;
	}
	
	/**
	 * 不同蔬菜品种超标情况统计表 列表导出
	 * @param plantSituationEntity
	 * @return
	 */
	public List<Map<String, Object>> exportVos(Map<String, Object> paramMap) {
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> vList = this.findListByMyBatis(NAME_SPACE+ "getVegetablesVarietyOverStanderdForExport", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("agrName", "title#KV#产品名称#EM#width#KV#30");
		headerMap.put("samplingCount", "title#KV#抽样数#EM#width#KV#20");
		headerMap.put("detetionCount", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("overStanderdCount", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("overStanderdRate", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("detetionRate", "title#KV#检出率#EM#width#KV#20");
		vList.add(0, headerMap);
		
		// 计算合计值
		int sumSamplingCount = 0;
		int sumDetetionCount = 0;
		int sumOverStanderdCount = 0;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < vList.size(); i++) {
			Map<String, Object> dataMap = vList.get(i);
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
		if (vList.size() - 1 >= 2) {
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
	
			vList.add(sumDataMap);
		}
		return vList;
	}
	
	/**
	 * 各省辖市批发市场超标情况表(详表)取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getProvincialCitiesOverStandardDetail (PlantSituationEntity plantSituationEntity){
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getProvincialCitiesOverStandardDetail", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >地市</th>";
		htmls += "<th class='center hidden-480' >抽样总数</th>";
		htmls += "<th class='center hidden-480' >超标数</th>";
		htmls += "<th class='center hidden-480' >超标率</th>";
		htmls += "<th class='center hidden-480' >合格率</th>";
		htmls += "<th class='center hidden-480' >名次排序</th>";
		htmls += "<th class='center hidden-480' >检出数</th>";
		htmls += "<th class='center hidden-480' >检出率</th>";
		htmls += "<th class='center hidden-480' >检出参数达三个数</th>";
		htmls += "<th class='center hidden-480' >检出率</th>";
		htmls += "</thead></tr>";
		
		htmls += "<tbody>";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)datamap.get("DETETIONCOUNT"));
			int tmpCon3DetetionCount = Integer.parseInt((String)datamap.get("CON3DETETIONCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
			String con3DetetionRate = df.format(100*(float)tmpCon3DetetionCount/tmpSamplingCount) + "%";
			
			htmls += "<tr>";
			htmls += "<td>"+datamap.get("CITYAREA")+"</td>";                //地市
			htmls += "<td>"+datamap.get("SAMPLINGCOUNT")+"</td>";           //抽样总数
			htmls += "<td>"+datamap.get("OVERSTANDERDCOUNT")+"</td>";       //超标数
			htmls += "<td>"+overStanderdRate+"</td>";                       //超标率
			htmls += "<td>"+qualifiedRate+"</td>";                          //合格率
			htmls += "<td>"+datamap.get("RANK")+"</td>";                    //名次排序
			htmls += "<td>"+datamap.get("DETETIONCOUNT")+"</td>";           //检出数
			htmls += "<td>"+detectionRate+"</td>";                          //检出率
			htmls += "<td>"+datamap.get("CON3DETETIONCOUNT")+"</td>";       //检出参数达三个数
			htmls += "<td>"+con3DetetionRate+"</td>";                       //检出率
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		return htmls;
	}

	/**
	 * 各省辖市批发市场超标情况表(详表)导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportPcosd(Map<String, Object> paramMap) {
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getProvincialCitiesOverStandardDetail", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("CITYAREA", "title#KV#地市#EM#width#KV#30");
		headerMap.put("SAMPLINGCOUNT", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDCOUNT", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDRATE", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("QUALIFIEDRATE", "title#KV#合格率#EM#width#KV#20");
		headerMap.put("RANK", "title#KV#名次排序#EM#width#KV#20");
		headerMap.put("DETETIONCOUNT", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("DETETIONRATE", "title#KV#检出率#EM#width#KV#20");
		headerMap.put("CON3DETETIONCOUNT", "title#KV#检出参数达三个数#EM#width#KV#20");
		headerMap.put("CON3DETETIONRATE", "title#KV#检出率#EM#width#KV#20");
		pcList.add(0, headerMap);

		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)datamap.get("DETETIONCOUNT"));
			int tmpCon3DetetionCount = Integer.parseInt((String)datamap.get("CON3DETETIONCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
			String con3DetetionRate = df.format(100*(float)tmpCon3DetetionCount/tmpSamplingCount) + "%";
			
			datamap.put("OVERSTANDERDRATE", overStanderdRate);
			datamap.put("QUALIFIEDRATE", qualifiedRate);
			datamap.put("DETETIONRATE", detectionRate);
			datamap.put("CON3DETETIONRATE", con3DetetionRate);
		}		
		return pcList;
	}
	
	/**
	 * 各区县超标情况表（详情）取得
	 * @param plantSituationEntity
	 * @return
	 */
	public String getCountiesOverStandardDetail (PlantSituationEntity plantSituationEntity){
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getCountiesOverStandardDetail", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >区县</th>";
		htmls += "<th class='center hidden-480' >抽样总数</th>";
		htmls += "<th class='center hidden-480' >超标数</th>";
		htmls += "<th class='center hidden-480' >超标率</th>";
		htmls += "<th class='center hidden-480' >合格率</th>";
		htmls += "<th class='center hidden-480' >名次排序</th>";
		htmls += "<th class='center hidden-480' >检出数</th>";
		htmls += "<th class='center hidden-480' >检出率</th>";
		htmls += "<th class='center hidden-480' >检出参数达三个数</th>";
		htmls += "<th class='center hidden-480' >检出率</th>";
		htmls += "</thead></tr>";
		
		htmls += "<tbody>";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)datamap.get("DETETIONCOUNT"));
			int tmpCon3DetetionCount = Integer.parseInt((String)datamap.get("CON3DETETIONCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
			String con3DetetionRate = df.format(100*(float)tmpCon3DetetionCount/tmpSamplingCount) + "%";
			
			htmls += "<tr>";
			htmls += "<td>"+datamap.get("COUNTYAREA")+"</td>";              //区县
			htmls += "<td>"+datamap.get("SAMPLINGCOUNT")+"</td>";           //抽样总数
			htmls += "<td>"+datamap.get("OVERSTANDERDCOUNT")+"</td>";       //超标数
			htmls += "<td>"+overStanderdRate+"</td>";                       //超标率
			htmls += "<td>"+qualifiedRate+"</td>";                          //合格率
			htmls += "<td>"+datamap.get("RANK")+"</td>";                    //名次排序
			htmls += "<td>"+datamap.get("DETETIONCOUNT")+"</td>";           //检出数
			htmls += "<td>"+detectionRate+"</td>";                          //检出率
			htmls += "<td>"+datamap.get("CON3DETETIONCOUNT")+"</td>";       //检出参数达三个数
			htmls += "<td>"+con3DetetionRate+"</td>";                       //检出率
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		return htmls;
	}

	/**
	 * 各区县超标情况表（详情）导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportCosd(Map<String, Object> paramMap) {
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getCountiesOverStandardDetail", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("COUNTYAREA", "title#KV#区县#EM#width#KV#30");
		headerMap.put("SAMPLINGCOUNT", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDCOUNT", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDRATE", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("QUALIFIEDRATE", "title#KV#合格率#EM#width#KV#20");
		headerMap.put("RANK", "title#KV#名次排序#EM#width#KV#20");
		headerMap.put("DETETIONCOUNT", "title#KV#检出数#EM#width#KV#20");
		headerMap.put("DETETIONRATE", "title#KV#检出率#EM#width#KV#20");
		headerMap.put("CON3DETETIONCOUNT", "title#KV#检出参数达三个数#EM#width#KV#20");
		headerMap.put("CON3DETETIONRATE", "title#KV#检出率#EM#width#KV#20");
		pcList.add(0, headerMap);

		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
			int tmpDetetionCount = Integer.parseInt((String)datamap.get("DETETIONCOUNT"));
			int tmpCon3DetetionCount = Integer.parseInt((String)datamap.get("CON3DETETIONCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";
			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
			String con3DetetionRate = df.format(100*(float)tmpCon3DetetionCount/tmpSamplingCount) + "%";
			
			datamap.put("OVERSTANDERDRATE", overStanderdRate);
			datamap.put("QUALIFIEDRATE", qualifiedRate);
			datamap.put("DETETIONRATE", detectionRate);
			datamap.put("CON3DETETIONRATE", con3DetetionRate);
		}		
		return pcList;
	}
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 *  取得超标样品情况详细表
	 * 
	 * @param plantSituationEntity
	 * @return
	 */
	@Override
	public void getOverProofSampleInfo(PlantSituationEntity plantSituationEntity, DataGrid dataGrid){
		Map<String,Object> params = new HashMap<String, Object>();
		String projectCodeList = plantSituationEntity.getProjectCode();
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			params.put("projectCode", new String[]{projectCodeList});
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			params.put("projectCode", projectCodes);
			projectCode = projectCodes[0];
		}
		// 权限条件设置
		setUserDataPriv(projectCode, params);

		Integer iCount = (Integer) this.getObjectByMyBatis(NAME_SPACE + "getOverProofSampleInfoCount", params);
		dataGrid.setTotal(iCount);
		// 取得超标样品情况详细表
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE + "getOverProofSampleInfo", params);
		dataGrid.setReaults(mapList);
		Map<String, String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("MONITORING_LINK", "allmonLink");
		dataGrid.setDataDicMap(dataDicMap);
	}
	
	/**
	 * 取得超标样品情况详细表导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportOverProofSampleInfo(Map<String, Object> paramMap) { 
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE + "getOverProofSampleInfo", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		String unit = ConverterUtil.getDictionaryName("unit",ResourceUtil.getConfigByName(Constants.PROPEERTIS_SYSTEMUNIT));
		headerMap.put("RN", "title#KV#序号#EM#width#KV#10");
		headerMap.put("SAMPLE_AREA_NAME", "title#KV#抽样地点#EM#width#KV#30");
		headerMap.put("D_CODE", "title#KV#样品条码#EM#width#KV#30");
		headerMap.put("SP_CODE", "title#KV#制样编码#EM#width#KV#30");
		headerMap.put("UNIT_FULLNAME", "title#KV#受检单位#EM#width#KV#50");
		headerMap.put("CNAME", "title#KV#样品名称#EM#width#KV#20");
		headerMap.put("MONITORING_LINK", "title#KV#监测环节#EM#width#KV#20");
		headerMap.put("POLL_VALUE", "title#KV#不合格参数及检测值("+unit+")#EM#width#KV#30");
		headerMap.put("OGRNAME", "title#KV#检测机构#EM#width#KV#30");
		mapList.add(0, headerMap);

		for (int i = 1; i < mapList.size(); i++) {
			Map<String, Object> datamap = mapList.get(i);	
			datamap.put("MONITORING_LINK", ConverterUtil.getDictionaryName("allmonLink", (String)datamap.get("MONITORING_LINK")));
		}		
		return mapList;
	}
	
//	@Override
//	public List<Map<String, String>> getSampleOverProofList(PlantSituationEntity plantSituationEntity) {
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		// 设置数据权限
//		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
//		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
//		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
//		//selCodition.put("areaCode", getLoginAreaCode());   //预留
//		return this.findListByMyBatis(NAME_SPACE + "getSampleOverProof", selCodition);
//		
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
//		// 设置数据权限
//		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
//		List<VegetablesSituationEntity> resList =  this.findListByMyBatis(NAME_SPACE+ "getVegetablesMonitoringLinkOverStanderd", selCodition);
//		
//		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
//	}
	
	@Override
	public String getSampleOverProofList(PlantSituationEntity plantSituationEntity) {
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		// 左边数据集
		List<SampleOverProofEntity> leftList =  this.findListByMyBatis(NAME_SPACE+ "getSampleOverProof", selCodition);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ "getSjzlLstForCounty", selCodition);
		// 右边数据集
		List<SampleOverProofEntity> rightList =  this.findListByMyBatis(NAME_SPACE+ "getSampleOverProofRight", selCodition);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList);
		
		if (leftList.size() == 0) {
			return "";
		}
		String htmls = "<thead><tr>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>序号</th>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>区县</th>";
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
		}
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
	
	private List<List<SampleOverProofEntity>> setInitRes(int leftListCount, List<String> headerMonList, List<SampleOverProofEntity> rightList) {
		List<List<SampleOverProofEntity>> resultList = new ArrayList<List<SampleOverProofEntity>>();
		for (int i = 0; i< leftListCount; i++) {
			List<SampleOverProofEntity> dList = new ArrayList<SampleOverProofEntity>();
			for (String header : headerMonList) {
				SampleOverProofEntity de = new SampleOverProofEntity();
				de.setMonitoringLink(header);
				de.setSamplingCount(-1);
				de.setQualifiedCount(-1);
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
			setLoadRes(resultList, count, agrst);
		
			compareName =tmpName;
		}
		return resultList;
	}
	
	private void setLoadRes(List<List<SampleOverProofEntity>> initList, int index, SampleOverProofEntity sampleOverProofEntity) {	
		List<SampleOverProofEntity> decDataList = initList.get(index);
		for (SampleOverProofEntity decData : decDataList) {
			if (StringUtils.equals(decData.getMonitoringLink(), sampleOverProofEntity.getMonitoringLink())) {
				decData.setSamplingCount(sampleOverProofEntity.getSamplingCount());
				decData.setQualifiedCount(sampleOverProofEntity.getQualifiedCount());
				//decData.setQualifiedRate(df.format(100*(float)sampleOverProofEntity.getQualifiedCount()/sampleOverProofEntity.getSamplingCount()) + "%");
			}
		}
	}
	
//	@Override
//	public Map<String, Object> getSampleOverProofActList(PlantSituationEntity plantSituationEntity) {
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
//		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
//		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
//		//selCodition.put("areaCode", getLoginAreaCode());    //预留
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", selCodition);
//		
//		for (Map<String, String> map : sjzdLst) {
//			String typecode = map.get("typecode").toString();
//			String typename = map.get("typename").toString();
//			selCodition.put("monitoringLink", typecode);
//			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", selCodition);
//		
//			if(lst != null && lst.size() > 0){
//				Map<String, String> endMap= lst.get(lst.size()-1);
//				endMap.put("typename", typename);
//				lst.set(lst.size()-1, endMap);
//				rtnMap.put(typename, lst);
//			}
//		}
//		return rtnMap;
//	}
	
	
	/**
	 * 各区县超标情况表 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportOverProof(Map<String, Object> paramMap){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		//	Map<String, Object> selCodition = new HashMap<String, Object>();
		//selCodition.put("projectCode", paramMap);
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
		// 设置数据权限
		setUserDataPriv(projectCode, paramMap);
		// 左边数据集
		List<SampleOverProofEntity> leftList =  this.findListByMyBatis(NAME_SPACE+ "getSampleOverProof", paramMap);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ "getSjzlLstForCounty", paramMap);
		// 右边数据集
		List<SampleOverProofEntity> rightList =  this.findListByMyBatis(NAME_SPACE+ "getSampleOverProofRight", paramMap);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList);
		
		// 表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_2", "title#KV#区县#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
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
		
			datamap.put("TITLE_1", indexRow+1);
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

//	/**
//	 * 各区县超标情况表 导出excel
//	 * @param paramMap
//	 * @return
//	 */
//	public List<Map<String, Object>> exportOverProof(Map<String, Object> paramMap){
//		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
//		paramMap.put("areaCode", getLoginAreaCode());
//		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProof", paramMap);
//		// 设置表头
//		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
//		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
//		headerMap.put("TITLE_1", 	"title#KV#区县#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
//		headerMap.put("TITLE_2", 	"title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
//		headerMap.put("TITLE_3", 		"title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
//		headerMap.put("TITLE_4", 		"title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
//			
//		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCounty", paramMap);
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		for (Map<String, String> map : sjzdLst) {
//			String typecode = map.get("typecode").toString();
//			String typename = map.get("typename").toString();
//			paramMap.put("monitoringLink", typecode);
//			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getSampleOverProofRight", paramMap);
//			if(rightLst != null && rightLst.size() > 0){
//				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
//				endMap.put("typename", typename);
//				rightLst.set(rightLst.size()-1, endMap);
//				rtnMap.put(typename, rightLst);
//			}
//		}
//		String title = "TITLE_";
//		int colNum = 5;
//		for (int i = 0; i < sjzdLst.size(); i++) {
//			Map<String, String> map = sjzdLst.get(i);
//			String typename = map.get("typename").toString();
//			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
//			colNum++;
//			headerMap.put(title+colNum,"");
//			colNum++;
//			headerMap.put(title+colNum,"");
//			colNum++;
//		}
//		colNum = 5;
//		for (int i = 0; i < sjzdLst.size(); i++) {
//			headerMap2.put("TITLE_1", "");
//			headerMap2.put("TITLE_2", "");
//			headerMap2.put("TITLE_3", "");
//			headerMap2.put("TITLE_4", "");
//			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+1));
//			colNum++;
//			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
//			colNum++;
//			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
//			colNum++;
//		}
//		rtnLst.add(0, headerMap);
//		rtnLst.add(1, headerMap2);
//		
//		for (int i = 0; i < leftLst.size(); i++) {
//			Map<String,Object> map = leftLst.get(i);
//			if(i == leftLst.size()-1){
//				// 设置合计行
//				map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
//				
//				colNum = 5;
//				for (int j = 0; j < sjzdLst.size(); j++) {
//					Map<String, String> mapL = sjzdLst.get(j);
//					String typename = mapL.get("typename").toString();
//					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
//					Map<String,String> rMap  = rVal.get(i);
//					if(rMap.get("typename") != null){
//						if(rMap.get("typename").toString().equals(typename)){
//							map.put(title+colNum, rMap.get("TOTALSUM"));
//							colNum++;
//							map.put(title+colNum, rMap.get("ISQUALSUM"));
//							colNum++;
//							map.put(title+colNum, getViewDateForPCT(rMap.get("PCTSUM")));
//							colNum++;
//						}
//					}else{
//						map.put(title+colNum, "");
//						colNum++;
//						map.put(title+colNum, "");
//						colNum++;
//						map.put(title+colNum, "");
//						colNum++;
//					}
//				}
//				rtnLst.add(i+2, map);
//				break;
//			}
//			map.put("PCTSUM", getViewDateForPCT(map.get("PCTSUM")));
//			
//			colNum = 5;
//			for (int j = 0; j < sjzdLst.size(); j++) {
//				Map<String, String> mapL = sjzdLst.get(j);
//				String typename = mapL.get("typename").toString();
//				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
//				Map<String,String> rMap  = rVal.get(i);
//				if(rMap.get("TYPENAME") != null){
//					if(rMap.get("TYPENAME").toString().equals(typename)){
//						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
//							map.put(title+colNum, rMap.get("TOTALSUM"));
//							colNum++;
//							map.put(title+colNum, rMap.get("ISQUALSUM"));
//							colNum++;
//							map.put(title+colNum, this.getViewDateForPCT(rMap.get("PCTSUM")));
//							colNum++;
//						}
//					}
//				}else{
//					map.put(title+colNum, "");
//					colNum++;
//					map.put(title+colNum, "");
//					colNum++;
//					map.put(title+colNum, "");
//					colNum++;
//				}
//			}
//			
//			map.remove("CODE");
//			rtnLst.add(i+2, map);
//		}
//		return rtnLst;
//	}
//	
	public String getViewDateForPCT(Object obj){
		String pctSum = "";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		if(StringUtil.isNotEmpty(obj)){
			pctSum = df.format(100*Double.valueOf(obj.toString())) + "%";
		}
		return pctSum;
	}
	
	@Override
	public List<Map<String, String>> getSuperMarketOverProof(
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
		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
		selCodition.put("areaCode",getLoginAreaCode()+"01");
		List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE+"getSuperMarketOverProof", selCodition);
		if(lst != null && lst.size() > 0){
			for (int i = 0; i < lst.size(); i++) {
				Map<String, String> map= lst.get(i);
				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("areaCode", map.get("CODE").toString());
				String pname = this.getObjectByMyBatis(NAME_SPACE+"getParentCityName",cond);
				map.put("AREANAME", pname);
				lst.set(i, map);
			}
		}
		return lst;
	}
	
	public List<Map<String, Object>> exportSuperMarket(Map<String, Object> paramMap){
		paramMap.put("areaCode",getLoginAreaCode()+"01");
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> lst = this.findListByMyBatis(NAME_SPACE+"getSuperMarketOverProof", paramMap);
		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("AREANAME", "title#KV#地市#EM#width#KV#30");
		headerMap.put("TOTALSUM", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("ISQUALSUM", "title#KV#合格数#EM#width#KV#20");
		headerMap.put("PCTSUM", "title#KV#合格率#EM#width#KV#20");
		headerMap.put("OVERSUM", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("OVERPCT", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("CLSNO", "title#KV#名次排序#EM#width#KV#20");
		rtnLst.add(0, headerMap);

		if(lst != null && lst.size() > 0){
			for (int i = 0; i < lst.size(); i++) {
				Map<String, Object> map= lst.get(i);
				if(i == lst.size()-1){
					// 设置合计行
					map.put("OVERPCT",map.get("OVERPCT").toString() + "%");
					map.put("PCTSUM", map.get("PCTSUM").toString() + "%");
					rtnLst.add(i+1, map);
					break;
				}
				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("areaCode", map.get("CODE").toString());
				String pname = this.getObjectByMyBatis(NAME_SPACE+"getParentCityName",cond);
				map.put("AREANAME", pname);
				map.remove("CODE");
				
				map.put("OVERPCT",map.get("OVERPCT").toString() + "%");
				map.put("PCTSUM", map.get("PCTSUM").toString() + "%");
				rtnLst.add(i+1, map);
			}
		}
		return rtnLst;
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
	
//	@Override
//	public List<Map<String, String>> getCitySampleOverProof(
//			PlantSituationEntity plantSituationEntity) {
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
//		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
//		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
//		selCodition.put("areaCode", "3201");//getLoginAreaCode());
//		return this.findListByMyBatis(NAME_SPACE + "getCitySampleOverProof", selCodition);
//	}
	
	@Override
	public String getCitySampleOverProof(PlantSituationEntity plantSituationEntity) {
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		// 左边数据集
		List<SampleOverProofEntity> leftList =  this.findListByMyBatis(NAME_SPACE+ "getCitySampleOverProof", selCodition);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ "getSjzlLstForCity", selCodition);
		// 右边数据集
		List<SampleOverProofEntity> rightList =  this.findListByMyBatis(NAME_SPACE+ "getCityOverProofRight", selCodition);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList);
		
		if (leftList.size() == 0) {
			return "";
		}
		String htmls = "<thead><tr>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>序号</th>";
		htmls += "<th class='center hidden-480' colspan=1 rowspan=2 style='vertical-align: middle !important'>地市</th>";
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
			htmls += "<td>"+(indexRow+1)+"</td>";
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
	
//	@Override
//	public Map<String, Object> getCitySampleOverProofAct(
//			PlantSituationEntity plantSituationEntity) {
//		Map<String, Object> selCodition = new HashMap<String, Object>();
//		selCodition.put("projectCode", plantSituationEntity.getProjectCode());
//		setUserDataPriv(plantSituationEntity.getProjectCode(), selCodition);
//		selCodition.put("industryCode", plantSituationEntity.getIndustryCode());
//		selCodition.put("areaCode", "3201");
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCity", selCodition);
//		
//		for (Map<String, String> map : sjzdLst) {
//			String typecode = map.get("typecode").toString();
//			String typename = map.get("typename").toString();
//			selCodition.put("monitoringLink", typecode);
//			List<Map<String, String>> lst = this.findListByMyBatis(NAME_SPACE + "getCityOverProofRight", selCodition);
//			if(lst != null && lst.size() > 0){
//				Map<String, String> endMap= lst.get(lst.size()-1);
//				endMap.put("typename", typename);
//				lst.set(lst.size()-1, endMap);
//				rtnMap.put(typename, lst);
//			}
//		}
//		return rtnMap;
//	}
	
//	/**
//	 * 各市超标样品情况表 导出excel
//	 * @param paramMap
//	 * @return
//	 */
//	public List<Map<String, Object>> exportCityOF(Map<String, Object> paramMap){
//		List<Map<String, Object>> rtnLst = new ArrayList<Map<String,Object>>();
//		paramMap.put("areaCode", getLoginAreaCode());
//		List<Map<String, Object>> leftLst = this.findListByMyBatis(NAME_SPACE + "getCitySampleOverProof", paramMap);
//		// 设置表头
//		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
//		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
//		headerMap.put("TITLE_1", 	"title#KV#市级#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
//		headerMap.put("TITLE_2", 	"title#KV#抽样总数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
//		headerMap.put("TITLE_3", 		"title#KV#合格数#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#2#EM#mergeLastCol#KV#2");
//		headerMap.put("TITLE_4", 		"title#KV#合格率#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#3#EM#mergeLastCol#KV#3");
//			
//		List<Map<String,String>> sjzdLst = this.findListByMyBatis(NAME_SPACE + "getSjzlLstForCity", paramMap);
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		for (Map<String, String> map : sjzdLst) {
//			String typecode = map.get("typecode").toString();
//			String typename = map.get("typename").toString();
//			paramMap.put("monitoringLink", typecode);
//			List<Map<String, String>> rightLst = this.findListByMyBatis(NAME_SPACE + "getCityOverProofRight", paramMap);
//			if(rightLst != null && rightLst.size() > 0){
//				Map<String, String> endMap= rightLst.get(rightLst.size()-1);
//				endMap.put("typename", typename);
//				rightLst.set(rightLst.size()-1, endMap);
//				rtnMap.put(typename, rightLst);
//			}
//		}
//		String title = "TITLE_";
//		int colNum = 5;
//		for (int i = 0; i < sjzdLst.size(); i++) {
//			Map<String, String> map = sjzdLst.get(i);
//			String typename = map.get("typename").toString();
//			headerMap.put(title+colNum, "title#KV#"+typename+"#EM#width#KV#30#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+0+"#EM#mergeLastRow#KV#"+0+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
//			colNum++;
//			headerMap.put(title+colNum,"");
//			colNum++;
//			headerMap.put(title+colNum,"");
//			colNum++;
//		}
//		colNum = 5;
//		for (int i = 0; i < sjzdLst.size(); i++) {
//			headerMap2.put("TITLE_1", "");
//			headerMap2.put("TITLE_2", "");
//			headerMap2.put("TITLE_3", "");
//			headerMap2.put("TITLE_4", "");
//			headerMap2.put(title+colNum, "title#KV#抽样数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+1)+"#EM#mergeLastCol#KV#"+((i+1)*3+1));
//			colNum++;
//			headerMap2.put(title+colNum, "title#KV#合格数#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+2)+"#EM#mergeLastCol#KV#"+((i+1)*3+2));
//			colNum++;
//			headerMap2.put(title+colNum, "title#KV#合格率#EM#width#KV#10#EM#cssClass#KV#text_center#EM#mergeFirstRow#KV#"+1+"#EM#mergeLastRow#KV#"+1+"#EM#mergeFirstCol#KV#"+((i+1)*3+3)+"#EM#mergeLastCol#KV#"+((i+1)*3+3));
//			colNum++;
//		}
//		rtnLst.add(0, headerMap);
//		rtnLst.add(1, headerMap2);
//		
//		for (int i = 0; i < leftLst.size(); i++) {
//			Map<String,Object> map = leftLst.get(i);
//			if(i == leftLst.size()-1){
//				// 设置合计行
//				map.put("PCTSUM", map.get("PCTSUM") + "%");
//				colNum = 5;
//				for (int j = 0; j < sjzdLst.size(); j++) {
//					Map<String, String> mapL = sjzdLst.get(j);
//					String typename = mapL.get("typename").toString();
//					List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
//					Map<String,String> rMap  = rVal.get(i);
//					if(rMap.get("typename") != null){
//						if(rMap.get("typename").toString().equals(typename)){
//							map.put(title+colNum, rMap.get("TOTALSUM"));
//							colNum++;
//							map.put(title+colNum, rMap.get("ISQUALSUM"));
//							colNum++;
//							map.put(title+colNum, String.valueOf(rMap.get("PCTSUM")).toString()+"%");
//							colNum++;
//						}
//					}else{
//						map.put(title+colNum, "");
//						colNum++;
//						map.put(title+colNum, "");
//						colNum++;
//						map.put(title+colNum, "");
//						colNum++;
//					}
//				}
//				rtnLst.add(i+2, map);
//				break;
//			}
//			map.put("PCTSUM", map.get("PCTSUM") + "%");
//			
//			colNum = 5;
//			for (int j = 0; j < sjzdLst.size(); j++) {
//				Map<String, String> mapL = sjzdLst.get(j);
//				String typename = mapL.get("typename").toString();
//				List<Map<String, String>> rVal = (List<Map<String, String>>) rtnMap.get(typename);
//				Map<String,String> rMap  = rVal.get(i);
//				if(rMap.get("TYPENAME") != null){
//					if(rMap.get("TYPENAME").toString().equals(typename)){
//						if(map.get("CODE").equals(rMap.get("CODE").toString()) ){
//							map.put(title+colNum, rMap.get("TOTALSUM"));
//							colNum++;
//							map.put(title+colNum, rMap.get("ISQUALSUM"));
//							colNum++;
//							map.put(title+colNum, String.valueOf(rMap.get("PCTSUM")).toString()+"%");
//							colNum++;
//						}
//					}
//				}else{
//					map.put(title+colNum, "");
//					colNum++;
//					map.put(title+colNum, "");
//					colNum++;
//					map.put(title+colNum, "");
//					colNum++;
//				}
//			}
//			
//			map.remove("CODE");
//			rtnLst.add(i+2, map);
//		}
//		return rtnLst;
//	}
	
	/**
	 * 各市超标样品情况表 导出excel
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> exportCityOF(Map<String, Object> paramMap){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		//	Map<String, Object> selCodition = new HashMap<String, Object>();
		//selCodition.put("projectCode", paramMap);
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
		// 设置数据权限
		setUserDataPriv(projectCode, paramMap);
		// 左边数据集
		List<SampleOverProofEntity> leftList =  this.findListByMyBatis(NAME_SPACE+ "getCitySampleOverProof", paramMap);
		// 表头监测环节数据集
		List<String> headerMonList = this.findListByMyBatis(NAME_SPACE+ "getSjzlLstForCity", paramMap);
		// 右边数据集
		List<SampleOverProofEntity> rightList =  this.findListByMyBatis(NAME_SPACE+ "getCityOverProofRight", paramMap);
		
		List<List<SampleOverProofEntity>> editedRightList = this.setInitRes(leftList.size(), headerMonList, rightList);
		
		// 表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> headerMap2 = new LinkedHashMap<String, Object>();
		headerMap.put("TITLE_1", "title#KV#序号#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#0#EM#mergeLastCol#KV#0");
		headerMap.put("TITLE_2", "title#KV#地市#EM#width#KV#20#EM#mergeFirstRow#KV#0#EM#mergeLastRow#KV#1#EM#mergeFirstCol#KV#1#EM#mergeLastCol#KV#1");
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
		
			datamap.put("TITLE_1", indexRow+1);
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
		String projectCodeList = ConverterUtil.toString(paramMap.get("projectCode"));
		String projectCode = null;
		if(projectCodeList.indexOf(",") == -1){
			projectCode = projectCodeList;
		}else{
			String projectCodes[] = projectCodeList.split(",");
			projectCode = projectCodes[0];
		}
		// 权限条件
		setUserDataPriv(projectCode, paramMap);
//		paramMap.put("projectCode", projectCode);
		paramMap.put("projectCode", setSplitProjectCode(projectCodeList));
		this.findListByMyBatis(NAME_SPACE + "getCountryAndOverList", paramMap);
		return (List<Map<String, Object>>) paramMap.get("result");
	}
	
	private String setSplitProjectCode (String str) {
		String splitProjectCode = "";
		String [] splitArr = str.split(",");
		for (String sa : splitArr) {
			splitProjectCode += "'"+ sa + "',";
		}
		return splitProjectCode.substring(0, splitProjectCode.length() - 1);
	}
	
	
	/**
	 * 各省辖市批发市场超标情况表取得 (20141120_cyq_add,modify)
	 * @param plantSituationEntity
	 * @return
	 */
	public String getProvincialCitiesOverStandard (PlantSituationEntity plantSituationEntity){
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
		// 设置数据权限
		setUserDataPriv(projectCode, selCodition);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getProvincialCitiesOverStandard", selCodition);
		
		String htmls = "";
		htmls += "<thead><tr>";
		htmls += "<th class='center hidden-480' >地市</th>";
		htmls += "<th class='center hidden-480' >抽样总数</th>";
		htmls += "<th class='center hidden-480' >超标数</th>";
		htmls += "<th class='center hidden-480' >超标率</th>";
		htmls += "<th class='center hidden-480' >合格数</th>";
		htmls += "<th class='center hidden-480' >合格率</th>";
		htmls += "<th class='center hidden-480' >名次排序</th>";
		htmls += "</thead></tr>";
		
		htmls += "<tbody>";
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 0; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";

			htmls += "<tr>";
			htmls += "<td>"+datamap.get("CITYAREA")+"</td>";                //地市
			htmls += "<td>"+datamap.get("SAMPLINGCOUNT")+"</td>";           //抽样总数
			htmls += "<td>"+datamap.get("OVERSTANDERDCOUNT")+"</td>";       //超标数
			htmls += "<td>"+overStanderdRate+"</td>";                       //超标率
			htmls += "<td>"+datamap.get("QUALIFIEDCOUNT")+"</td>";                          //合格率
			htmls += "<td>"+qualifiedRate+"</td>";                          //合格率
			htmls += "<td>"+datamap.get("RANK")+"</td>";                    //名次排序
			htmls += "</tr>";
		}
		htmls += "</tbody>";
		return htmls;
	}

	/**
	 * 各省辖市批发市场超标情况表导出(20141120_cyq_add,modify)
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportPcos(Map<String, Object> paramMap) {
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
		setUserDataPriv(projectCode, paramMap);
		List<Map<String, Object>> pcList = this.findListByMyBatis(NAME_SPACE+ "getProvincialCitiesOverStandard", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("CITYAREA", "title#KV#地市#EM#width#KV#30");
		headerMap.put("SAMPLINGCOUNT", "title#KV#抽样总数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDCOUNT", "title#KV#超标数#EM#width#KV#20");
		headerMap.put("OVERSTANDERDRATE", "title#KV#超标率#EM#width#KV#20");
		headerMap.put("QUALIFIEDCOUNT", "title#KV#合格数#EM#width#KV#20");
		headerMap.put("QUALIFIEDRATE", "title#KV#合格率#EM#width#KV#20");
		headerMap.put("RANK", "title#KV#名次排序#EM#width#KV#20");
		pcList.add(0, headerMap);

		DecimalFormat df = new DecimalFormat("0.0");//格式化小数
		for (int i = 1; i < pcList.size(); i++) {
			Map<String, Object> datamap = pcList.get(i);
			int tmpSamplingCount = Integer.parseInt((String)datamap.get("SAMPLINGCOUNT"));
			int tmpOverStanderdCount = Integer.parseInt((String)datamap.get("OVERSTANDERDCOUNT"));
//			int tmpDetetionCount = Integer.parseInt((String)datamap.get("DETETIONCOUNT"));
//			int tmpCon3DetetionCount = Integer.parseInt((String)datamap.get("CON3DETETIONCOUNT"));
			
			String overStanderdRate = df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount) + "%";
			String qualifiedRate = df.format((100.0f - Float.parseFloat(df.format(100*(float)tmpOverStanderdCount/tmpSamplingCount))))+ "%";
//			String detectionRate = df.format(100*(float)tmpDetetionCount/tmpSamplingCount) + "%";
//			String con3DetetionRate = df.format(100*(float)tmpCon3DetetionCount/tmpSamplingCount) + "%";
			
			datamap.put("OVERSTANDERDRATE", overStanderdRate);
			datamap.put("QUALIFIEDRATE", qualifiedRate);
//			datamap.put("DETETIONRATE", detectionRate);
//			datamap.put("CON3DETETIONRATE", con3DetetionRate);
		}		
		return pcList;
	}
}


