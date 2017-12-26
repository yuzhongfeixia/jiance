package com.hippo.nky.service.impl.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jeecg.system.pojo.base.TSDepart;
import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSUser;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Decoder;

import com.google.l99gson.Gson;
import com.google.l99gson.reflect.TypeToken;
import com.hippo.nky.entity.detection.NkyDetectionInformationEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.sample.GeneralcheckEntity;
import com.hippo.nky.entity.sample.LivestockEntity;
import com.hippo.nky.entity.sample.NkyFreshMilkEntity;
import com.hippo.nky.entity.sample.RoutinemonitoringEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.entity.sample.SuperviseCheckEntity;
import com.hippo.nky.service.monitoring.NkyMonitoringSiteServiceI;
import com.hippo.nky.service.sample.SamplingInfoServiceI;

@Service("barcodeInfoInputService")
@Transactional
public class SamplingInfoServiceImpl extends CommonServiceImpl implements SamplingInfoServiceI {
	@Autowired  
	NkyMonitoringSiteServiceI nkyMonitoringSiteServiceI;

	public static final String NAME_SPACE = "com.hippo.nky.entity.sample.SamplingInfoEntity.";
	/**
	 *取得页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = getSelCodition(pageObj);
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSample", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("unitFullname")
				,new Db2Page("sampleCode")
				,new Db2Page("agrname")
				,new Db2Page("samplingDate")
				,new Db2Page("monitoringLink")
				,new Db2Page("taskCode")
				,new Db2Page("projectName")
				,new Db2Page("projectCode")
				,new Db2Page("countycode")
				,new Db2Page("dcode")
				,new Db2Page("spCode")
				,new Db2Page("rn")
				,new Db2Page("cityAndCountry")
				,new Db2Page("samplingOrgName")
				,new Db2Page("reportingDate")
		};
		
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("monitoringLink","allmonLink");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages, dataDicMap);
		return jObject;
	}
	
	// 取得查询条件
	private Map<String, Object> getSelCodition(SamplingInfoEntity pageObj) {
		Map<String, Object> selConditionMap = new HashMap<String,Object>();
		// 项目质检机构关系表中一个项目对多个质检机构,此处进行过滤
		//selConditionMap.put("orgId", ResourceUtil.getSessionUserName().getTSDepart().getId());
		if (StringUtils.isNotEmpty(pageObj.getSamplingOrgCode())) {
			selConditionMap.put("samplingOrgCode", pageObj.getSamplingOrgCode());
		} else {
			OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
			if(org!=null){
				selConditionMap.put("samplingOrgCode", org.getCode());
			}
		}

		if (StringUtil.isNotEmpty(pageObj.getSampleName())) {
			selConditionMap.put("agrName",  pageObj.getSampleName());
		}
		if (StringUtil.isNotEmpty(pageObj.getOrgCode())) {
			selConditionMap.put("orgCode", pageObj.getOrgCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getProjectCode())) {
			selConditionMap.put("projectCode", pageObj.getProjectCode());
			if (StringUtil.equals(pageObj.getProjectCode(), "FEIYANGDATA")) {
				selConditionMap.put("projectCode", "");
			}
		} else{
			selConditionMap.put("projectCode", "NOPROJECTNODATA");
		}
		if (StringUtil.isNotEmpty(pageObj.getUnitFullname())){
			selConditionMap.put("unitFullname", pageObj.getUnitFullname());
		}
		if (StringUtil.isNotEmpty(pageObj.getSampleStatus())){
			String status = pageObj.getSampleStatus();
			if(status.indexOf(",") == -1){
				selConditionMap.put("sampleStatus", new String[]{status});
			}else{
				String stas[] = status.split(",");
				selConditionMap.put("sampleStatus", stas);
			}
		}
		if (StringUtil.isNotEmpty(pageObj.getdCode())){
			selConditionMap.put("dCode", pageObj.getdCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getCityCode())) {
			selConditionMap.put("cityCode",pageObj.getCityCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getCountyCode())) {
			selConditionMap.put("countyCode",pageObj.getCountyCode());
		}
		if (StringUtil.isNotEmpty(pageObj.getMonitoringLink())){
			selConditionMap.put("monitoringLink", pageObj.getMonitoringLink());
		}		
		if (StringUtil.isNotEmpty(pageObj.getProjectName())){
			selConditionMap.put("projectName", pageObj.getProjectName());
		}
//		if (StringUtil.isNotEmpty(pageObj.getSamplingOrgCode())) {
//			selConditionMap.put("samplingOrgCode", pageObj.getSamplingOrgCode());
//		}
		if (StringUtil.isNotEmpty(pageObj.getComplete())) {
			selConditionMap.put("complete", pageObj.getComplete());
		}

		return selConditionMap;
		
	}
	
	// 设置查询的开始位置和结尾位置
	public void setBeginAndEnd(DataGrid dataGrid ,Map<String,Object> selCodition){
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page-1)*rows;
		int endIndex = beginIndex+rows;
		selCodition.put("beginIndex", beginIndex);
		selCodition.put("endIndex", endIndex);	
	}

	/**
	 *  根据项目编码取得抽样单模板和行业
	 * @param projectCode
	 * @return
	 */
	public String[] getMonitorTypeAndIndustry(String projectCode) {
		String reArr[] = new String[2];
//		TSUser user = ResourceUtil.getSessionUserName();
		//List<MonitoringProjectEntity> projectList = this.getUserMonintorProgaram(user, null, null, null);
		List<MonitoringProjectEntity> projectList = this.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
		MonitoringProjectEntity mp = projectList.get(0);
//		for (MonitoringProjectEntity mp : projectList) {
//			if (mp.getProjectCode().equals(projectCode)) {
//				reArr[0] = mp.getSampleTemplet();
//				reArr[1] = mp.getIndustryCode();
//			}
//		}
		reArr[0] = mp.getSampleTemplet();
		reArr[1] = mp.getIndustryCode();
		return reArr;
	}
	
	/**
	 * 取pad抽样环节
	 * @param industry
	 * @return
	 */
	public List<TSType> getPadSampleMonitoringLink(String industry) {
		String param = industry + "monitoringLink";
		List<TSType> mapList = this.findListByMyBatis(NAME_SPACE + "getPadSampleMonitoringLink", param);
    	return mapList;
	}
	
	/**
	 * 取得抽样环节名称回显
	 */
	public String getNameForMonitoringLink(String code) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("param", code);
		List<String> mapList = this.findListByMyBatis(NAME_SPACE + "getPadSampleMonitoringLinkName", selCodition);
    	if (mapList != null && mapList.size() > 0) {
    		return mapList.get(0);
    	}
		return "";
	}
	
	/**
	 * 根据项目编码取得抽样单类型 ，参照对应关系
	 * @param projectCode
	 * @return [ 1：例行监测,2:监督抽查,3：风险（普查）,4:生鲜乳,4:畜禽]
	 */
	@Override
	public String getSampleMonadType (String projectCode) {
		String sampleMonadType = "";
		String reArr[] = this.getMonitorTypeAndIndustry(projectCode);
		String sampleTemplet = reArr[0];
		String industry = reArr[1];
		// 种植业
		if (StringUtils.equals(industry, "f")) {
			if (StringUtils.equals(sampleTemplet, "0")) {//例行监测
				sampleMonadType = "1";
			} else if (StringUtils.equals(sampleTemplet, "1")) {//风险
				sampleMonadType = "3";
			} else if (StringUtils.equals(sampleTemplet, "2")) {//监督抽查
				sampleMonadType = "2";
			} else if (StringUtils.equals(sampleTemplet, "3")) {//专项
				sampleMonadType = "2";
			} 
		}
		// 畜禽业
		if (StringUtils.equals(industry, "a")) {
			if (StringUtils.equals(sampleTemplet, "0")) {//例行监测
				sampleMonadType = "5";
			} else if (StringUtils.equals(sampleTemplet, "1")) {//风险
				sampleMonadType = "5";
			} else if (StringUtils.equals(sampleTemplet, "2")) {//监督抽查
				sampleMonadType = "2";
			} else if (StringUtils.equals(sampleTemplet, "3")) {//专项
				sampleMonadType = "5";
			} else if (StringUtils.equals(sampleTemplet, "4")) {//生鲜
				sampleMonadType = "4";
			}
		}
		return sampleMonadType;
	}

	/**
	 * 取得抽检分离flg
	 */
	@Override
	public String getDetecthedFlg(String projectCode) {
		List<MonitoringProjectEntity> list = this.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
		return list.get(0).getDetached();
	}
	/**
	 * 保存抽样信息
	 * @param barcodeInfoInput
	 */
	public void addMain(SamplingInfoEntity barcodeInfoInput) {
		boolean isFromPad = false;
		// 若为pad端录入，抽样单位已设定；否则为pc端录入时，取当前用户的质检机构
		if (StringUtils.isEmpty(barcodeInfoInput.getSamplingOrgCode())) {
			// 抽样单位
			String departid = ResourceUtil.getSessionUserName().getTSDepart().getId();
			if (StringUtils.isNotEmpty(departid)) {
				OrganizationEntity org = this.getEntity(OrganizationEntity.class, departid);
				barcodeInfoInput.setSamplingOrgCode(org.getCode());
			}
		} else {
			isFromPad = true;
		}
		
		// 若不是抽检分离项目，则设置质检机构为抽样机构
		if (StringUtils.equals(getDetecthedFlg(barcodeInfoInput.getProjectCode()), "0")) {
			barcodeInfoInput.setDetectionCode(barcodeInfoInput.getSamplingOrgCode());
		}

		// 样品图片字节数组
//		byte[] imgContent = barcodeInfoInput.getImgContent();
//		if (imgContent != null) {
//			// 上传文件内容到服务端并保存图片路径
//			barcodeInfoInput.setSamplePath(uploadImage(imgContent));
//		}

		// 检查并设置信息完整度，1：完整，0：不完整
		if(checkComplete(barcodeInfoInput)) {
			barcodeInfoInput.setComplete("1");
		} else {
			barcodeInfoInput.setComplete("0");
		}

		// 受检单位名称若为纯手工输入,则需检查在监测点表里是否存在
		if (StringUtils.isEmpty(barcodeInfoInput.getUnitFullcode())
				&& StringUtils.isNotEmpty(barcodeInfoInput.getUnitFullname())) {
			NkyMonitoringSiteEntity monitoringSiteEntity = doCheckMonitoringSite(barcodeInfoInput);
			barcodeInfoInput.setUnitFullcode(monitoringSiteEntity.getCode());
		}
		// 设置抽样单状态为草稿
		barcodeInfoInput.setSampleStatus("1");
		
		// 设置抽样信息合格状态为合格
		barcodeInfoInput.setIsQualified("0");
		
		String pre_spcode = barcodeInfoInput.getPre_spcode();

		// 例行监测
		List<RoutinemonitoringEntity> routinemonitoringList = barcodeInfoInput.getRoutinemonitoringList();
		// 普查
		GeneralcheckEntity generalCheck =  barcodeInfoInput.getGeneralcheckEntity();
		// 监督抽查
		SuperviseCheckEntity superviseCheckEntity = barcodeInfoInput.getSuperviseCheckEntity();
		// 畜禽
		//LivestockEntity livestockEntity = barcodeInfoInput.getLivestockEntity();
		List<LivestockEntity> livestockEntityList = barcodeInfoInput.getLivestockEntityList();
		// 生鲜乳
		NkyFreshMilkEntity nkyFreshMilk = barcodeInfoInput.getNkyFreshMilkEntity();
		if (routinemonitoringList != null && routinemonitoringList.size() > 0) {//例行监测
			// 隐性设置抽样单ID
			String samplingMonadId = ConverterUtil.getUUID();
			// 取得任务来源
			String taskSource = routinemonitoringList.get(0).getTaskSource();
			// 取得执行标准
			String execStanderd = routinemonitoringList.get(0).getExecStanderd();
		
			for (int i = 0; i < routinemonitoringList.size(); i++) {
				// 备注
				String remark = routinemonitoringList.get(i).getRemark();
				// 样品编码
				//String sampleCode = routinemonitoringList.get(i).getSampleCode();
				String sampleCode = ConverterUtil.getUUID();
				// 任务编码
				String taskCode = routinemonitoringList.get(i).getTaskCode();
				// 条码
				String dCode = routinemonitoringList.get(i).getdCode();
				// 农产品编码
				String agrCode = routinemonitoringList.get(i).getAgrCode();
				// 图片字节数组
//				imgContent = routinemonitoringList.get(i).getImgContent();
				// 抽样地点
				String samplingAddress = routinemonitoringList.get(i).getSamplingAddress();
				// 制样编码
				String spCode = routinemonitoringList.get(i).getSpCode();
				// 样品保存路径(20141119)
				String id = routinemonitoringList.get(i).getImgContent();
				String samplePath = routinemonitoringList.get(i).getSamplePath();
				String samplingTime = routinemonitoringList.get(i).getSamplingTime();
				
				SamplingInfoEntity barcodeInfoClone = (SamplingInfoEntity)barcodeInfoInput.clone();
				barcodeInfoClone.setRemark(remark);
				barcodeInfoClone.setSampleCode(sampleCode);
				barcodeInfoClone.setTaskCode(taskCode);
				barcodeInfoClone.setdCode(dCode);
				barcodeInfoClone.setAgrCode(agrCode);
				barcodeInfoClone.setSamplingAddress(samplingAddress);
				barcodeInfoClone.setSamplingMonadId(samplingMonadId);
				if (StringUtils.isNotEmpty(spCode)){
					barcodeInfoClone.setSpCode(pre_spcode +"-" +spCode);
				}
				// 样品保存路径(20141119)
				barcodeInfoClone.setId(id);
				barcodeInfoClone.setSamplePath(samplePath);
				barcodeInfoClone.setSamplingTime(samplingTime);
//				if (imgContent != null) {
//					barcodeInfoClone.setSamplePath(uploadImage(imgContent));
//				}
				
				// 检查信息完整度，1：完整，0：不完整
				if(checkComplete(barcodeInfoClone)) {
					barcodeInfoClone.setComplete("1");
				} else {
					barcodeInfoClone.setComplete("0");
				}
				if (isFromPad && StringUtils.isNotEmpty(barcodeInfoClone.getId())) {
					this.saveOrUpdate(barcodeInfoClone);
				} else {
					this.save(barcodeInfoClone);
				}
				RoutinemonitoringEntity routinemonitoring = routinemonitoringList.get(i);
				routinemonitoring.setSampleCode(sampleCode);
				routinemonitoring.setTaskSource(taskSource);
				routinemonitoring.setExecStanderd(execStanderd);
				routinemonitoring.setSamplingMonadId(samplingMonadId);
				//routinemonitoring.setSamplingAddress(samplingAddress);
				this.save(routinemonitoring);
				//  录入默认检测信息
				addDetectionInfo(barcodeInfoClone);
			}
		} else if (generalCheck != null) {//普查
			// 样品编码
			String sampleCode = ConverterUtil.getUUID();
			barcodeInfoInput.setSampleCode(sampleCode);
			barcodeInfoInput.setId(barcodeInfoInput.getImgContent());
			if (isFromPad && StringUtils.isNotEmpty(barcodeInfoInput.getId())) {
				this.saveOrUpdate(barcodeInfoInput);
			} else {
				this.save(barcodeInfoInput);
			}
			generalCheck.setSampleCode(sampleCode);
			this.save(generalCheck);
		    // 录入默认检测信息
			addDetectionInfo(barcodeInfoInput);

		} else if(superviseCheckEntity != null) {//监督抽查
			// 样品编码
			String sampleCode = ConverterUtil.getUUID();
			barcodeInfoInput.setSampleCode(sampleCode);
			barcodeInfoInput.setId(barcodeInfoInput.getImgContent());
			if (isFromPad && StringUtils.isNotEmpty(barcodeInfoInput.getId())) {
				this.saveOrUpdate(barcodeInfoInput);
			} else {
				this.save(barcodeInfoInput);
			}
			superviseCheckEntity.setSampleCode(sampleCode);
			this.save(superviseCheckEntity);
			// 录入默认检测信息
			addDetectionInfo(barcodeInfoInput);

//		} else if(livestockEntity != null) {//畜禽
//			// 样品编码
//			String sampleCode = ConverterUtil.getUUID();
//			barcodeInfoInput.setSampleCode(sampleCode);
//			this.save(barcodeInfoInput);
//			livestockEntity.setSampleCode(sampleCode);
//			this.save(livestockEntity);
//			// 录入默认检测信息
//			addDetectionInfo(barcodeInfoInput);
		} else if(livestockEntityList != null && livestockEntityList.size() > 0) {//畜禽
			// 隐性设置抽样单ID
			String samplingMonadId = ConverterUtil.getUUID();
			// 抽样依据
			String taskSource = livestockEntityList.get(0).getTaskSource();
			// 商标
			String tradeMark = livestockEntityList.get(0).getTradeMark();
			// 抽样基数
			String samplingBaseCount = livestockEntityList.get(0).getSamplingBaseCount();
			// 抽样数量
			String samplingCount = livestockEntityList.get(0).getSamplingCount();
			// 保存情况
			String saveSaveSituation = livestockEntityList.get(0).getSaveSaveSituation();
			// 抽样方式
			String samplingMode = livestockEntityList.get(0).getSamplingMode();
			// 样品包装
			String pack = livestockEntityList.get(0).getPack();
			// 签封标志
			String signFlg = livestockEntityList.get(0).getSignFlg();
			
			for (int i = 0; i < livestockEntityList.size(); i++) {
				// 备注
				String remark = livestockEntityList.get(i).getRemark();
				// 样品编码
				String sampleCode = ConverterUtil.getUUID();
				// 任务编码
				String taskCode = livestockEntityList.get(i).getTaskCode();
				// 条码
				String dCode = livestockEntityList.get(i).getdCode();
				// 图片字节数组
//				imgContent = livestockEntityList.get(i).getImgContent();
				// 抽样地点
				String samplingAddress = livestockEntityList.get(i).getSamplingAddress();
				// 制样编码
				String spCode = livestockEntityList.get(i).getSpCode();
				// 样品保存路径(20141119)
				String id = livestockEntityList.get(i).getImgContent();
				String samplePath = livestockEntityList.get(i).getSamplePath();
				String samplingTime = livestockEntityList.get(i).getSamplingTime();
				
				SamplingInfoEntity barcodeInfoClone = (SamplingInfoEntity)barcodeInfoInput.clone();
				barcodeInfoClone.setRemark(remark);
				barcodeInfoClone.setSampleCode(sampleCode);
				barcodeInfoClone.setTaskCode(taskCode);
				barcodeInfoClone.setdCode(dCode);
				barcodeInfoClone.setSamplingAddress(samplingAddress);
				barcodeInfoClone.setSamplingMonadId(samplingMonadId);
				if (StringUtils.isNotEmpty(spCode)){
					barcodeInfoClone.setSpCode(pre_spcode +"-" +spCode);
				}
				// 样品保存路径(20141119)
				barcodeInfoClone.setId(id);
				barcodeInfoClone.setSamplePath(samplePath);
				barcodeInfoClone.setSamplingTime(samplingTime);
//				if (imgContent != null) {
//					barcodeInfoClone.setSamplePath(uploadImage(imgContent));
//				}
				
				// 检查信息完整度，1：完整，0：不完整
				if(checkComplete(barcodeInfoClone)) {
					barcodeInfoClone.setComplete("1");
				} else {
					barcodeInfoClone.setComplete("0");
				}
				if (isFromPad && StringUtils.isNotEmpty(barcodeInfoClone.getId())) {
					this.saveOrUpdate(barcodeInfoClone);
				} else {
					this.save(barcodeInfoClone);
				}
				LivestockEntity livestockEntity = livestockEntityList.get(i);
				livestockEntity.setSampleCode(sampleCode);
				livestockEntity.setTradeMark(tradeMark);
				livestockEntity.setTaskSource(taskSource);
				livestockEntity.setSamplingBaseCount(samplingBaseCount);
				livestockEntity.setSamplingCount(samplingCount);
				livestockEntity.setSaveSaveSituation(saveSaveSituation);
				livestockEntity.setSamplingMode(samplingMode);
				livestockEntity.setPack(pack);
				livestockEntity.setSignFlg(signFlg);
				livestockEntity.setSamplingMonadId(samplingMonadId);
				//livestockEntity.setSamplingAddress(samplingAddress);
				this.save(livestockEntity);
				//  录入默认检测信息
				addDetectionInfo(barcodeInfoClone);
			}
		} else if (nkyFreshMilk != null) {	// 生鲜乳
			// 样品编码
			String sampleCode = ConverterUtil.getUUID();
			barcodeInfoInput.setSampleCode(sampleCode);
			barcodeInfoInput.setId(barcodeInfoInput.getImgContent());
			if (isFromPad && StringUtils.isNotEmpty(barcodeInfoInput.getId())) {
				this.saveOrUpdate(barcodeInfoInput);
			} else {
				this.save(barcodeInfoInput);
			}
			nkyFreshMilk.setSampleCode(sampleCode);
			this.save(nkyFreshMilk);
			// 录入默认检测信息
			addDetectionInfo(barcodeInfoInput);
		}

	}
	
	/**
	 * 当受检单位名称为纯手工输入时,检查DB里是否存在
	 * @param unitFullName
	 * @return
	 */
	public NkyMonitoringSiteEntity doCheckMonitoringSite (String unitFullName) {
		List<NkyMonitoringSiteEntity> list = this.findByProperty(NkyMonitoringSiteEntity.class, "name", unitFullName);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			NkyMonitoringSiteEntity nmsEntity = new NkyMonitoringSiteEntity();
			nmsEntity.setName(unitFullName);
			this.save(nmsEntity);
			return null;
		}
	}
	
	public NkyMonitoringSiteEntity doCheckMonitoringSite (SamplingInfoEntity barcodeInfoInput) {
		List<NkyMonitoringSiteEntity> list = this.findByProperty(NkyMonitoringSiteEntity.class, "name", barcodeInfoInput.getUnitFullname());
		if (list.size() == 1) {
			return list.get(0);
		} else {
			NkyMonitoringSiteEntity nmsEntity = new NkyMonitoringSiteEntity();
			// 设置受检单位编码
			nmsEntity.setCode((String)this.getObjectByMyBatis(NAME_SPACE+"getGenerateMonitoringSiteCode", null));
			nmsEntity.setName(barcodeInfoInput.getUnitFullname());
			nmsEntity.setAddress(barcodeInfoInput.getUnitAddress());
			nmsEntity.setZipcode(barcodeInfoInput.getZipCode());
			nmsEntity.setLegalPerson(barcodeInfoInput.getLegalPerson());
			nmsEntity.setContactPerson(barcodeInfoInput.getContact());
			nmsEntity.setContact(barcodeInfoInput.getTelphone());
			nmsEntity.setFax(barcodeInfoInput.getFax());
			this.save(nmsEntity);
			// 向sqlite模板文件中写入信息
			nkyMonitoringSiteServiceI.saveDataToTemplate(nmsEntity);
			return nmsEntity;
		}
	}
	
	/**
	 * 将字节数组内容转换成图片上传到服务器
	 * @param imgContent
	 */
	public String uploadImage (byte[] imgContent) {
		// 取得系统绝对路径
		String syspath = ResourceUtil.getSysPath();
		// 取得文件上传根目录
	    String uploadbasepath = ResourceUtil.getConfigByName("uploadpath")+"\\images";
		// 取得文件名
	    String filename = DataUtils.getDataString(DataUtils.yyyymmddhhmmss) + StringUtil.random(8) + ".jpg";

	    // 图片保存的真是路径
	    String realPath = syspath + uploadbasepath+"\\"+filename;
	    	    
	    String dbPath = "/" + ResourceUtil.getConfigByName("uploadpath")+"/images" +"/"+filename;
	    
	    // 定义文件
	    File file = new File(realPath);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			
		      //将字节写入文件
	        fout.write(imgContent);
	        fout.close();

		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		} 
		return dbPath;
	}
	
	/**
	 * 将字节数组内容转换成图片上传到服务器
	 * @param imgContent
	 */
	@Override
	public String initDataForImage (String imgContentMap) {
		Map<String, String> resultMap = new HashMap<String, String>();
//		String resultMap = "[";
		Gson gson = new Gson();
		Map<String, String> contentMap = gson.fromJson(imgContentMap, new TypeToken<Map<String, String>>() {}.getType());
		//int i = 0;
		// 取得系统绝对路径
		String syspath = ResourceUtil.getSysPath();
		// 取得文件上传根目录
	    String uploadbasepath = ResourceUtil.getConfigByName("uploadpath")+"\\images";
		for (String imageName : contentMap.keySet()) {
			
			String imgContent = (String)contentMap.get(imageName);
			// 取得文件名
		    //String filename = DataUtils.getDataString(DataUtils.yyyymmddhhmmss) + StringUtil.random(8) + ".jpg";

		    // 图片保存的真是路径
		    String realPath = syspath + uploadbasepath+"\\"+imageName;
		    	    
		    //String dbPath = "/" + ResourceUtil.getConfigByName("uploadpath")+"/images" +"/"+imageName;
		    
		    // 定义文件
		    File file = new File(realPath);
		    
		    // 图片已经存在则取图片在数据对应的信息，若找不到则覆盖图片并写入db
		    if (file.exists() ) {
		    	// 返回所有图片在数据库表中对应的Id信息
		    	String existsImageId = getExistsImgInfo(imageName);
		    	if (StringUtils.isNotEmpty(existsImageId)) {
		    		resultMap.put(imageName, existsImageId);
		    		continue;
		    	}
		    } 
				
			FileOutputStream fout;
			try {
				fout = new FileOutputStream(file);
			      //将字节写入文件
		        fout.write(new BASE64Decoder().decodeBuffer(imgContent));
		        fout.close();

			} catch (Exception e) {
				// Auto-generated catch block
				e.printStackTrace();
			} 
			// 将图片信息数据初始化到数据库
			SamplingInfoEntity bathInfo = new SamplingInfoEntity();
			bathInfo.setSamplePath(imageName);
			this.save(bathInfo);
//			if (i < contentMap.size() -1) {
//				resultMap += "{" + imageName + ":'"+ bathInfo.getId()+"'},";
//			} else {
//				resultMap += "{" + imageName + ":'"+ bathInfo.getId()+"'}";
//			}
			
			resultMap.put(imageName, bathInfo.getId());
		   
		}
//		resultMap += "]";
		return gson.toJson(resultMap);
		
	}
	
	/**
	 * 图片在数据库已经存在，说明样品基本信息已经存在，则直接返回
	 * @param contentMap
	 * @return
	 */	
	private String getExistsImgInfo (String imageName) {

		List<SamplingInfoEntity> sinfoList = this.findByProperty(SamplingInfoEntity.class, "samplePath", imageName);
		if (sinfoList != null && sinfoList.size() > 0) {
			return sinfoList.get(0).getId();
		} else {
			return "";
		}
	}
	
	/**
	 * 录入默认检测信息
	 * @param barcodeInfoInput
	 */
	public void addDetectionInfo (SamplingInfoEntity barcodeInfoInput) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", barcodeInfoInput.getProjectCode());
		selCodition.put("agrCode", barcodeInfoInput.getAgrCode());
		// 取得该农产品对应的污染物模板
		List<String> pollList = null;
		pollList = this.findListByMyBatis(NAME_SPACE+"getAgrPollTemplate", selCodition);
		// 若污染物模版里找不到对应的农产品，则从从分类里找，一层层找直到找到
		if(pollList.size() == 0){
			pollList = this.findListByMyBatis(NAME_SPACE+"getAgrPollTemplate1", selCodition);
		}
		// 若分类里找不到，则说明该农产品及其分类没有设置污染物模版，则从判定标准里取
		if (pollList.size() == 0) {
			pollList = this.findListByMyBatis(NAME_SPACE+"getAgrPollTemplate2", selCodition);
		}
		
		for (String pollCas: pollList) {
			NkyDetectionInformationEntity detectionInformation = new NkyDetectionInformationEntity();
			detectionInformation.setCasCode(pollCas);
			detectionInformation.setDetectionValue(new BigDecimal(0));
			detectionInformation.setIsOverproof("0");
			detectionInformation.setSampleCode(barcodeInfoInput.getSampleCode());
			this.save(detectionInformation);
		}
		
	}
	
	/**
	 * 更新默认检测信息(农产品发生变化)
	 * @param barcodeInfoInput
	 */
	public void updateDetectionInfo (SamplingInfoEntity atferSample, String beforeAgr) {
		// 若农产品发生改变则需更新默认检测信息
		if (!StringUtils.equals(beforeAgr, atferSample.getAgrCode())) {
			// 删除原有的默认检测信息
			delDetectionInfo(atferSample);
			// 增加新的默认检测信息
			addDetectionInfo(atferSample);
		}
	}
	
	/**
	 * 删除默认检测信息
	 * @param barcodeInfoInput
	 */
	public void delDetectionInfo (SamplingInfoEntity barcodeInfoInput) {
		List<NkyDetectionInformationEntity> detectionInformation = this.findByProperty(NkyDetectionInformationEntity.class, "sampleCode", barcodeInfoInput.getSampleCode());
		this.deleteAllEntitie(detectionInformation);
		//this.delete(detectionInformation.get(0));
	}
	
	/**
	 * 更新抽样信息
	 * @param barcodeInfoInput
	 * @throws Exception 
	 */
	public void updateMain(SamplingInfoEntity barcodeInfoInput) throws Exception {
		// 检查信息完整度，1：完整，0：不完整
		if(checkComplete(barcodeInfoInput)) {
			barcodeInfoInput.setComplete("1");
		} else {
			barcodeInfoInput.setComplete("0");
		}
		// 受检单位名称若为纯手工输入,则需检查在监测点表里是否存在
		if (StringUtils.isEmpty(barcodeInfoInput.getUnitFullcode())
				&& StringUtils.isNotEmpty(barcodeInfoInput.getUnitFullname())) {
			NkyMonitoringSiteEntity monitoringSiteEntity = doCheckMonitoringSite(barcodeInfoInput);
			barcodeInfoInput.setUnitFullcode(monitoringSiteEntity.getCode());
		}
		String pre_spcode = barcodeInfoInput.getPre_spcode();
		// 例行监测
		List<RoutinemonitoringEntity> routinemonitoringList = barcodeInfoInput.getRoutinemonitoringList();
		// 普查
		GeneralcheckEntity generalCheck =  barcodeInfoInput.getGeneralcheckEntity();
		// 监督抽查
		SuperviseCheckEntity superviseCheckEntity = barcodeInfoInput.getSuperviseCheckEntity();
		// 畜禽
		//LivestockEntity livestockEntity = barcodeInfoInput.getLivestockEntity();
		List<LivestockEntity> livestockEntityList = barcodeInfoInput.getLivestockEntityList();
		// 生鲜乳
		NkyFreshMilkEntity nkyFreshMilk = barcodeInfoInput.getNkyFreshMilkEntity();
//		if (routinemonitoringList != null && routinemonitoringList.size() > 0) {//例行监测
//			// 备注
//			String remark = routinemonitoringList.get(0).getRemark();
//			// 农产品编码
//			String agrCode = routinemonitoringList.get(0).getAgrCode();
//			barcodeInfoInput.setRemark(remark);
//			barcodeInfoInput.setAgrCode(agrCode);
//			barcodeInfoInput.setTaskCode(routinemonitoringList.get(0).getTaskCode());
//			barcodeInfoInput.setdCode(routinemonitoringList.get(0).getdCode());
//			barcodeInfoInput.setSamplingAddress(routinemonitoringList.get(0).getSamplingAddress());
//			// 检查信息完整度，1：完整，0：不完整
//			if(checkComplete(barcodeInfoInput)) {
//				barcodeInfoInput.setComplete("1");
//			} else {
//				barcodeInfoInput.setComplete("0");
//			}
//			this.saveOrUpdate(barcodeInfoInput);
//				
//			RoutinemonitoringEntity routinemonitoring = routinemonitoringList.get(0);
//			this.saveOrUpdate(routinemonitoring);
//		}
		if (routinemonitoringList != null && routinemonitoringList.size() > 0) {//例行监测
			for (int i = 0; i < routinemonitoringList.size(); i++) {
				if (StringUtils.isEmpty(routinemonitoringList.get(i).getId())) {// id为空，则增加样品信息
					String sampleCode = ConverterUtil.getUUID();
					SamplingInfoEntity samplingInfoEntityClone = getCloneRouSamplingMain(routinemonitoringList);
					samplingInfoEntityClone.setId("");
					samplingInfoEntityClone.setSampleCode(sampleCode);
					samplingInfoEntityClone.setRemark(routinemonitoringList.get(i).getRemark());
					samplingInfoEntityClone.setAgrCode(routinemonitoringList.get(i).getAgrCode());
					samplingInfoEntityClone.setTaskCode(routinemonitoringList.get(i).getTaskCode());
					samplingInfoEntityClone.setdCode(routinemonitoringList.get(i).getdCode());
					samplingInfoEntityClone.setSamplingAddress(routinemonitoringList.get(i).getSamplingAddress());
					if (StringUtils.isNotEmpty(routinemonitoringList.get(i).getSpCode())) {
						samplingInfoEntityClone.setSpCode(pre_spcode + "-" + routinemonitoringList.get(i).getSpCode());
					}
					// 检查信息完整度，1：完整，0：不完整
					if(checkComplete(samplingInfoEntityClone)) {
						samplingInfoEntityClone.setComplete("1");
					} else {
						samplingInfoEntityClone.setComplete("0");
					}
					this.save(samplingInfoEntityClone);
					RoutinemonitoringEntity routinemonitoringEntity = routinemonitoringList.get(i);
					routinemonitoringEntity.setSampleCode(sampleCode);
					routinemonitoringEntity.setSamplingMonadId(samplingInfoEntityClone.getSamplingMonadId());
					this.save(routinemonitoringEntity);
					// 录入默认检测信息
					samplingInfoEntityClone.setProjectCode(barcodeInfoInput.getProjectCode());
					addDetectionInfo(samplingInfoEntityClone);
					continue;
				}
	
				String sampleCode = routinemonitoringList.get(i).getSampleCode();
				SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
				String beforeId = samplingInfoEntity.getId();
				String beforeAgr = samplingInfoEntity.getAgrCode();// 更新前农产品
				MyBeanUtils.copyBeanNotNull2Bean(barcodeInfoInput, samplingInfoEntity);
				samplingInfoEntity.setId(beforeId);
				samplingInfoEntity.setRemark(routinemonitoringList.get(i).getRemark());
				samplingInfoEntity.setAgrCode(routinemonitoringList.get(i).getAgrCode());
				samplingInfoEntity.setTaskCode(routinemonitoringList.get(i).getTaskCode());
				samplingInfoEntity.setdCode(routinemonitoringList.get(i).getdCode());
				samplingInfoEntity.setSamplingAddress(routinemonitoringList.get(i).getSamplingAddress());
				if (StringUtils.isNotEmpty(routinemonitoringList.get(i).getSpCode())) {
					samplingInfoEntity.setSpCode(pre_spcode + "-" + routinemonitoringList.get(i).getSpCode());
				}
				// 检查信息完整度，1：完整，0：不完整
				if(checkComplete(samplingInfoEntity)) {
					samplingInfoEntity.setComplete("1");
				} else {
					samplingInfoEntity.setComplete("0");
				}
				
				this.saveOrUpdate(samplingInfoEntity);
				this.saveOrUpdate(routinemonitoringList.get(i));
				// 更新默认检测信息
				samplingInfoEntity.setProjectCode(barcodeInfoInput.getProjectCode());
				updateDetectionInfo(samplingInfoEntity, beforeAgr);
			}
			// 删除抽样单上删除的记录
			delRouSampling(routinemonitoringList);

		} else if (generalCheck != null) {//普查
			this.saveOrUpdate(barcodeInfoInput);
			this.saveOrUpdate(generalCheck);
			// 更新默认检测信息
			SamplingInfoEntity before = this.findByProperty(SamplingInfoEntity.class, "sampleCode", barcodeInfoInput.getSampleCode()).get(0);
			updateDetectionInfo(barcodeInfoInput, before.getAgrCode());

		} else if (superviseCheckEntity != null) {//监督抽查
			this.saveOrUpdate(barcodeInfoInput);
			this.saveOrUpdate(superviseCheckEntity);
			// 更新默认检测信息
			SamplingInfoEntity before = this.findByProperty(SamplingInfoEntity.class, "sampleCode", barcodeInfoInput.getSampleCode()).get(0);
			updateDetectionInfo(barcodeInfoInput, before.getAgrCode());
//		} else if (livestockEntityList != null && livestockEntityList.size() > 0) {
//			// 备注
//			String remark = livestockEntityList.get(0).getRemark();
//			barcodeInfoInput.setRemark(remark);
//			barcodeInfoInput.setTaskCode(livestockEntityList.get(0).getTaskCode());
//			barcodeInfoInput.setdCode(livestockEntityList.get(0).getdCode());
//			barcodeInfoInput.setSamplingAddress(livestockEntityList.get(0).getSamplingAddress());
//			// 检查信息完整度，1：完整，0：不完整
//			if(checkComplete(barcodeInfoInput)) {
//				barcodeInfoInput.setComplete("1");
//			} else {
//				barcodeInfoInput.setComplete("0");
//			}
//			this.saveOrUpdate(barcodeInfoInput);
//				
//			LivestockEntity livestockEntity = livestockEntityList.get(0);
//			this.saveOrUpdate(livestockEntity);
		} else if (livestockEntityList != null && livestockEntityList.size() > 0) {
			for (int i = 0; i < livestockEntityList.size(); i++) {
				if (StringUtils.isEmpty(livestockEntityList.get(i).getId())) {// id为空，则增加样品信息
					String sampleCode = ConverterUtil.getUUID();
					SamplingInfoEntity samplingInfoEntityClone = getCloneLivSamplingMain(livestockEntityList);
					samplingInfoEntityClone.setId("");
					samplingInfoEntityClone.setSampleCode(sampleCode);
					samplingInfoEntityClone.setRemark(livestockEntityList.get(i).getRemark());
					samplingInfoEntityClone.setTaskCode(livestockEntityList.get(i).getTaskCode());
					samplingInfoEntityClone.setdCode(livestockEntityList.get(i).getdCode());
					samplingInfoEntityClone.setSamplingAddress(livestockEntityList.get(i).getSamplingAddress());
					if (StringUtils.isNotEmpty(livestockEntityList.get(i).getSpCode())) {
						samplingInfoEntityClone.setSpCode(pre_spcode + "-" + livestockEntityList.get(i).getSpCode());
					}
					// 检查信息完整度，1：完整，0：不完整
					if(checkComplete(samplingInfoEntityClone)) {
						samplingInfoEntityClone.setComplete("1");
					} else {
						samplingInfoEntityClone.setComplete("0");
					}
					this.save(samplingInfoEntityClone);
					LivestockEntity livestockEntity = livestockEntityList.get(i);
					livestockEntity.setSampleCode(sampleCode);
					livestockEntity.setSamplingMonadId(samplingInfoEntityClone.getSamplingMonadId());
					this.save(livestockEntity);
					// 录入默认检测信息
					samplingInfoEntityClone.setProjectCode(barcodeInfoInput.getProjectCode());
					addDetectionInfo(samplingInfoEntityClone);
					continue;
				}
	
				String sampleCode = livestockEntityList.get(i).getSampleCode();
				SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
				String beforeId = samplingInfoEntity.getId();
				String beforeAgr = samplingInfoEntity.getAgrCode();// 更新前农产品
				MyBeanUtils.copyBeanNotNull2Bean(barcodeInfoInput, samplingInfoEntity);
				samplingInfoEntity.setId(beforeId);
				samplingInfoEntity.setAgrCode(barcodeInfoInput.getAgrCode());
				samplingInfoEntity.setRemark(livestockEntityList.get(i).getRemark());
				samplingInfoEntity.setTaskCode(livestockEntityList.get(i).getTaskCode());
				samplingInfoEntity.setdCode(livestockEntityList.get(i).getdCode());
				samplingInfoEntity.setSamplingAddress(livestockEntityList.get(i).getSamplingAddress());
				if (StringUtils.isNotEmpty(livestockEntityList.get(i).getSpCode())) {
					samplingInfoEntity.setSpCode(pre_spcode + "-" + livestockEntityList.get(i).getSpCode());
				}
				// 检查信息完整度，1：完整，0：不完整
				if(checkComplete(samplingInfoEntity)) {
					samplingInfoEntity.setComplete("1");
				} else {
					samplingInfoEntity.setComplete("0");
				}
				
				this.saveOrUpdate(samplingInfoEntity);
				this.saveOrUpdate(livestockEntityList.get(i));
				// 更新默认检测信息
				samplingInfoEntity.setProjectCode(barcodeInfoInput.getProjectCode());
				updateDetectionInfo(samplingInfoEntity, beforeAgr);
			}
			// 删除抽样单上删除的记录
			delLivSampling(livestockEntityList);
		} else if (nkyFreshMilk != null) {	// 生鲜乳
			this.saveOrUpdate(barcodeInfoInput);
			this.saveOrUpdate(nkyFreshMilk);
			// 更新默认检测信息
			SamplingInfoEntity before = this.findByProperty(SamplingInfoEntity.class, "sampleCode", barcodeInfoInput.getSampleCode()).get(0);
			updateDetectionInfo(barcodeInfoInput, before.getAgrCode());
		}
	}
	
	/**
	 * 取得例行检测抽样单克隆样品主信息
	 */
	private SamplingInfoEntity getCloneRouSamplingMain(List<RoutinemonitoringEntity> routinemonitoringList) {
		String sampleCode = "";
		for(RoutinemonitoringEntity rou: routinemonitoringList) {
			if (StringUtils.isNotEmpty(rou.getSampleCode())) {
				sampleCode = rou.getSampleCode();
				break;
			}
		}
		SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
		return (SamplingInfoEntity)samplingInfoEntity.clone();
	}
	
	/**
	 * 取得畜禽抽样单克隆样品主信息
	 */
	private SamplingInfoEntity getCloneLivSamplingMain(List<LivestockEntity> livestockEntityList) {
		String sampleCode = "";
		for(LivestockEntity liv: livestockEntityList) {
			if (StringUtils.isNotEmpty(liv.getSampleCode())) {
				sampleCode = liv.getSampleCode();
				break;
			}
		}
		SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
		return (SamplingInfoEntity)samplingInfoEntity.clone();
	}

	/**
	 * 删除例行检测抽样单上删除的样品信息
	 */
	private void delRouSampling(List<RoutinemonitoringEntity> afterList){
		String sampleCode = "";
		for(RoutinemonitoringEntity rou: afterList) {
			if (StringUtils.isNotEmpty(rou.getSampleCode())) {
				sampleCode = rou.getSampleCode();
				break;
			}
		}
		SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
		String samplingMonadId = samplingInfoEntity.getSamplingMonadId();
		// 取在编辑抽样单之前时样品信息集合
		CriteriaQuery cq = new CriteriaQuery(RoutinemonitoringEntity.class);
		cq.eq("samplingMonadId", samplingMonadId);
		cq.add();
		List<RoutinemonitoringEntity> beforeList =  this.getListByCriteriaQuery(cq, false);
		
		// beforeList中有的afterList中没有的，即为删除的信息
		List<String> befores = new ArrayList<String>();
		List<String> afters = new ArrayList<String>();
		for (RoutinemonitoringEntity before : beforeList) {
			befores.add(before.getId());
		}
		for (RoutinemonitoringEntity after : afterList) {
			afters.add(after.getId());
		}
		for (String id : befores) {
			if (!afters.contains(id)) {
				RoutinemonitoringEntity rou = this.getEntity(RoutinemonitoringEntity.class, id);
			    sampleCode = rou.getSampleCode();
			    samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
			    this.delete(samplingInfoEntity);
			    this.delete(rou);
				// 删除默认检测信息
				delDetectionInfo(samplingInfoEntity);
			}
		}
		

	}
	
	/**
	 * 删除畜禽抽样单上删除的样品信息
	 */
	private void delLivSampling(List<LivestockEntity> afterList){
		String sampleCode = "";
		for(LivestockEntity liv: afterList) {
			if (StringUtils.isNotEmpty(liv.getSampleCode())) {
				sampleCode = liv.getSampleCode();
				break;
			}
		}
		SamplingInfoEntity samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
		String samplingMonadId = samplingInfoEntity.getSamplingMonadId();
		// 取在编辑抽样单之前时样品信息集合
		CriteriaQuery cq = new CriteriaQuery(LivestockEntity.class);
		cq.eq("samplingMonadId", samplingMonadId);
		cq.add();
		List<LivestockEntity> beforeList =  this.getListByCriteriaQuery(cq, false);
		
		// beforeList中有的afterList中没有的，即为删除的信息
		List<String> befores = new ArrayList<String>();
		List<String> afters = new ArrayList<String>();
		for (LivestockEntity before : beforeList) {
			befores.add(before.getId());
		}
		for (LivestockEntity after : afterList) {
			afters.add(after.getId());
		}
		for (String id : befores) {
			if (!afters.contains(id)) {
				LivestockEntity liv = this.getEntity(LivestockEntity.class, id);
			    sampleCode = liv.getSampleCode();
			    samplingInfoEntity = this.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
			    this.delete(samplingInfoEntity);
			    this.delete(liv);
				// 删除默认检测信息
				delDetectionInfo(samplingInfoEntity);
			}
		}
		

	}
	
	/**
	 * 删除抽样信息
	 * @param barcodeInfoInput
	 */
	public void delMain(SamplingInfoEntity barcodeInfoInput) {
		// 例行监测
		List<RoutinemonitoringEntity> routinemonitoringList = barcodeInfoInput.getRoutinemonitoringList();
		// 普查
		GeneralcheckEntity generalCheck =  barcodeInfoInput.getGeneralcheckEntity();
		// 监督抽查
		SuperviseCheckEntity superviseCheckEntity = barcodeInfoInput.getSuperviseCheckEntity();
		// 畜禽
		List<LivestockEntity> livestockEntityList = barcodeInfoInput.getLivestockEntityList();
		// 生鲜乳
		NkyFreshMilkEntity nkyFreshMilk = barcodeInfoInput.getNkyFreshMilkEntity();
		if (routinemonitoringList != null && routinemonitoringList.size() > 0) {//例行监测
			this.delete(barcodeInfoInput);
			this.delete(routinemonitoringList.get(0));
	
		} else if (generalCheck != null) {//普查
			this.delete(barcodeInfoInput);
			this.delete(generalCheck);

		} else if (superviseCheckEntity != null) {//监督抽查
			this.delete(barcodeInfoInput);
			this.delete(superviseCheckEntity);

		} else if(livestockEntityList != null && livestockEntityList.size() > 0) {//畜禽
			this.delete(barcodeInfoInput);
			this.delete(livestockEntityList.get(0));

		} else if (nkyFreshMilk != null) {	// 生鲜乳
			this.delete(barcodeInfoInput);
			this.delete(nkyFreshMilk);
		}
		// 删除默认检测信息
		delDetectionInfo(barcodeInfoInput);
	}
	
	/**
	 * 验证信息完整度
	 * @param barcodeInfoInput
	 * @return
	 */
	private boolean checkComplete (SamplingInfoEntity barcodeInfoInput) {
		boolean isComplete = false;
		// 条码
		String dCode = barcodeInfoInput.getdCode();
		// 样品
		String agrCode = barcodeInfoInput.getAgrCode();
		// 抽样任务
		String  taskCode = barcodeInfoInput.getTaskCode();
		// 抽样场所
		String moinitorLink = barcodeInfoInput.getMonitoringLink();
		// 抽样时间
		Date samplingDate = barcodeInfoInput.getSamplingDate();
		// 抽样地点
		String cityCode = barcodeInfoInput.getCityCode();
		// 受检单位
		String unitFullName = barcodeInfoInput.getUnitFullname();
		
		if (StringUtils.isNotEmpty(dCode)
				&& StringUtils.isNotEmpty(agrCode)
				&& StringUtils.isNotEmpty(taskCode)
				&& StringUtils.isNotEmpty(moinitorLink)
				&& samplingDate != null
				&& StringUtils.isNotEmpty(cityCode)
				&& StringUtils.isNotEmpty(unitFullName)) {
			isComplete = true;
		}
		return isComplete;
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

	/**
	 * 取得用户监测项目,带过滤条件
	 * @return
	 */
	@Override
	public List<MonitoringProjectEntity> getUserMonintorProgaram(TSUser user, String monitorType, String year, String showBelowGradeRepFlg){
		
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("orgId", user.getTSDepart().getId());
		if (StringUtil.isNotEmpty(monitorType)) {
			selCodition.put("monitorType", monitorType);
		}
		if (StringUtil.isNotEmpty(year)) {
			selCodition.put("year", year);
		}
		List<MonitoringProjectEntity> progaramList = null;

		if (StringUtils.isNotEmpty(showBelowGradeRepFlg)) {// 某些菜单允许已完成的项目出现
			selCodition.put("showCompleteFlg", "show");
		}
		// 管理部门
		if("0".equals(user.getUsertype())){
			if (StringUtil.equals(showBelowGradeRepFlg, "yes")) {
				//selCodition.put("showBelowGradeRepFlg", "yes");
				Map<String, String> pmap = getManagementDataPriv(user);
				selCodition.put("gl_g", pmap.get("gl_g"));
				selCodition.put("gl_code1", pmap.get("gl_code1"));
				selCodition.put("gl_code2", pmap.get("gl_code2"));
				selCodition.put("gl_code3", pmap.get("gl_code3"));
			}
			progaramList = this.findListByMyBatis(NAME_SPACE+"getUserMonintorProgaramForDepartment", selCodition);
		// 质检机构
		} else if("1".equals(user.getUsertype())){
			if (StringUtil.equals(showBelowGradeRepFlg, "yes")) {
				// 牵头单位能浏览或操作自己的项目的所有数据
				OrganizationEntity org = this.getEntity(OrganizationEntity.class, user.getTSDepart().getId());
				selCodition.put("orgCode", org.getCode());
				selCodition.put("qtFlg", "1");
			} 
			progaramList = this.findListByMyBatis(NAME_SPACE+"getUserMonintorProgaram", selCodition);
		}
		
		return progaramList;
	}
	
	/**
	 *取得页面sampleMakeCode的datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @param areaCode 用户所属部门的行政区划code
	 * @return
	 */
	@Override
	public JSONObject getSampleMakeCodeDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid, String areaCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		try {
			selCodition.putAll(ConverterUtil.entityToMap(pageObj));
		} catch (Exception e) {
		}
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("samplingOrgCode", org.getCode());
		if (selCodition.get("projectCode") == null
				|| StringUtils.isEmpty((String)selCodition.get("projectCode"))) {
			selCodition.put("projectCode", "NOPROJECTNODATA");
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleMakeCodeGridCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSampleMakeCodeGrid", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("cityAndCountry")
				,new Db2Page("spCode")
				,new Db2Page("dCode")
				,new Db2Page("unitFullname")
				,new Db2Page("sampleCode")
				,new Db2Page("agrname")
				,new Db2Page("samplingDate")
				,new Db2Page("monitoringLink")
				,new Db2Page("rn")
		};
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("monitoringLink","allmonLink");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages, dataDicMap);
		return jObject;
	}
	
	/**
	 * 设置制样编码
	 * @param projectCode 项目code
	 * @return 修改sql的数量
	 */
	@Override
	public int setSpCode(String projectCode){
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("projectCode", projectCode);
		return this.updateByMyBatis(NAME_SPACE+"callUpdateSpCode", dataDicMap);
	}
	
	@Override
	public Object findObsoleteByCode(String dcode,String orgcode) {
		Map<String,String> oMap = new HashMap<String, String>();
		oMap.put("orgCode", orgcode);
		oMap.put("dcode", dcode);
		return this.getObjectByMyBatis(NAME_SPACE+"findObsoleteByCode", oMap);
	}
	
	/**
	 * 二维码存在性check
	 */
	public int checkCode (String code){
		Integer count = this.getObjectByMyBatis(NAME_SPACE+"checkCode", code);
		return count;
	}
	
	@Override
	public int checkSampleDetached(SamplingInfoEntity pageObj) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", pageObj.getProjectCode());
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("samplingOrgCode", org.getCode());
		Integer allcount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"checkSampleDetached", selCodition);
		if(allcount.intValue() > 0){
			return 0;
		}else{
			return 1;
		}
	}
	
	@Override
	public Map<String, Integer> sampleReportCount(SamplingInfoEntity pageObj, String sampleIdList) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		Map<String, Integer> rtnMap = new HashMap<String, Integer>(); 
		selCodition.put("projectCode", pageObj.getProjectCode());
		selCodition.put("orgCode", pageObj.getOrgCode());
		Integer subcount = 0;
		if (StringUtils.isNotEmpty(sampleIdList)) {//抽检不分离
			selCodition.put("sampleIdList", sampleIdList);
			subcount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleSubCountByStatus1", selCodition);
		} else {//抽检分离
			subcount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleSubCountByStatus", selCodition);
		}	
		Integer allcount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleAllCountByStatus", selCodition);
		
		rtnMap.put("allcount", allcount);
		rtnMap.put("subcount", subcount);
		rtnMap.put("minuscount", (allcount-subcount));
		return rtnMap;
	}
	
	@Override
	public int toReport(SamplingInfoEntity samplingInfoEntity, String sampleIdList) {
		Map<String, Object> tempMap = new HashMap<String, Object>(); 
		tempMap.put("projectCode", samplingInfoEntity.getProjectCode());
		tempMap.put("samplingOrgCode", samplingInfoEntity.getOrgCode());
		if (sampleIdList == null) {
			// 抽检分离
			return this.updateByMyBatis(NAME_SPACE+"toUpdateReport", tempMap);
		} else {
			// 抽检不分离，分批次上报
			tempMap.put("sampleIdList", sampleIdList);
			return this.updateByMyBatis(NAME_SPACE+"toUpdateReport1", tempMap);
		}	
	}
	
	/**
	 * 取得对应项目中的地市或区县的下拉框
	 * @param pageObj
	 * @param isCity 是否是地市
	 * @return
	 */
	@Override
	public String getCityAndCountryList(SamplingInfoEntity pageObj,int isCity) {
		List<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();
		if(1 == isCity){
			codeList = this.findListByMyBatis(NAME_SPACE + "getCityCodeList", pageObj);
		} else {
			codeList = this.findListByMyBatis(NAME_SPACE + "getCountryCodeList", pageObj);
		}
		
		StringBuffer result = new StringBuffer();
		for (Map<String, Object> map : codeList) {
			result.append(map.get("code"));
			result.append(ConverterUtil.SEPARATOR_KEY_VALUE);
			result.append(map.get("areaName"));
			result.append(ConverterUtil.SEPARATOR_ELEMENT);
		}

		return result.toString();
	}
	
	/**
	 * 取得最新版本农产品名称
	 * @param sampleCode
	 * @return
	 */
	public String getLastVersionAgrName(String agrCode, String projectCode){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("agrCode", agrCode);
		selCodition.put("projectCode", projectCode);
		String agrName = "";
		List<String> agrList = this.findListByMyBatis(NAME_SPACE+"getLastVersionAgrName", selCodition);
		if (agrList != null && agrList.size() > 0) {
			agrName = agrList.get(0);
		}
		return agrName;
	}
	
	@Override
	public JSONObject findForStatistics(SamplingInfoEntity pageObj,
		DataGrid dataGrid) {
		Map<String, Object> selConditionMap = new HashMap<String,Object>();
		Map<String, String> UserDataPrivMap = getUserDataPriv(pageObj.getProjectCode());
		selConditionMap.put("gl", UserDataPrivMap.get("gl"));
		selConditionMap.put("qt", UserDataPrivMap.get("qt"));
		selConditionMap.put("pt", UserDataPrivMap.get("pt"));
	
		if (StringUtil.isNotEmpty(pageObj.getProjectCode())) {
			selConditionMap.put("projectCode", pageObj.getProjectCode());
		} else{
			selConditionMap.put("projectCode", "NOPROJECTNODATA");
		}
		if (StringUtil.isNotEmpty(pageObj.getSampleStatus())){
			String status = pageObj.getSampleStatus();
			if(status.indexOf(",") == -1){
				selConditionMap.put("sampleStatus", new String[]{status});
			}else{
				String stas[] = status.split(",");
				selConditionMap.put("sampleStatus", stas);
			}
		}
		setBeginAndEnd(dataGrid, selConditionMap);
		
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForStatistics", selConditionMap);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"findForStatistics", selConditionMap);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("rn")
				, new Db2Page("orgcode")
				, new Db2Page("projectcode")
				, new Db2Page("count")
				, new Db2Page("reportcount")
				, new Db2Page("agrname") };
		
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("monitoringLink","allmonLink");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages, dataDicMap);
		return jObject;
	}
	
	/**
	 * 根据登录的用户取得查看数据的权限
	 * @return
	 */
	private Map<String, String> getUserDataPriv(String projectCode) {
		Map<String, String> privMap = new HashMap<String, String>();
		// 取得用户类型
		String userType = ResourceUtil.getSessionUserName().getUsertype();
		
		// 管理部门
		if (StringUtils.equals(userType, "0")) {
			// 取得管理部门级别 [1:省级,2:市级,3:区县级]
			TSDepart depart = ResourceUtil.getSessionUserName().getTSDepart();
			privMap.put("gl", depart.getId());   //管理部门ID
			
		// 质检机构
		} else {
			OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();

			CriteriaQuery cq = new CriteriaQuery(MonitoringProjectEntity.class);
			cq.eq("projectCode", projectCode);
			cq.eq("leadunit", org.getCode());
			cq.add();
			List<MonitoringProjectEntity> projectList =  this.getListByCriteriaQuery(cq, false); 
			if (projectList != null && projectList.size() > 0) {
				privMap.put("qt", org.getCode()); //牵头单位code
			} else {
				privMap.put("pt", org.getCode()); //普通质检机构code
			}
		}
		return privMap;
	}
	
	
	/**
	 * 样品分发数据取得
	 * @param barcodeInfoInput
	 * @param request
	 * @return
	 */
	@Override
	public JSONObject getSampleDistributeDataGrid(MonitoringProjectEntity pageObj,DataGrid dataGrid){
		//TSUser user = ResourceUtil.getSessionUserName();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		//selCodition.put("orgId", user.getTSDepart().getId());
		
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getName())) {
			selCodition.put("name", pageObj.getName());
		}
		if (pageObj.getPublishDate_begin() != null && pageObj.getPublishDate_end() != null) {
			selCodition.put("publishDate_begin", pageObj.getPublishDate_begin());
			selCodition.put("publishDate_end", pageObj.getPublishDate_end());
		}
		setBeginAndEnd(dataGrid, selCodition);
		
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForSampleDistribute", selCodition);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSampleDistribute", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id")
				, new Db2Page("projectName")
				, new Db2Page("packingFlg")
				, new Db2Page("releaseunit")
				, new Db2Page("publishDate")
				, new Db2Page("count")
		        , new Db2Page("dscount")};
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}

	/**
	 * 样品分发数据取得
	 * @param barcodeInfoInput
	 * @param request
	 * @return
	 */
	public JSONObject getPackingDistributeDataGrid(MonitoringProjectEntity pageObj, DataGrid dataGrid){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(pageObj.getProjectCode())) {
			selCodition.put("projectCode", pageObj.getProjectCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getOgrname())) {
			selCodition.put("ogrname", pageObj.getOgrname());
		}
		if (StringUtils.isNotEmpty(pageObj.getName())) {
			selCodition.put("name", pageObj.getName());
		}
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForPackkingDistribute", selCodition);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getPackingDistribute", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id")
				, new Db2Page("ogrname")
				, new Db2Page("name")
				, new Db2Page("areaname")
				, new Db2Page("reportingDate")
				, new Db2Page("rpCount")
				, new Db2Page("oname")};
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 整包分发前确认样品是否接收
	 * @param projectCode
	 */
	@Override
	public boolean checkRecvPacking(SamplingInfoEntity samplingInfoEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", samplingInfoEntity.getProjectCode());
		selCodition.put("samplingOrgCode", samplingInfoEntity.getSamplingOrgCode());
		selCodition.put("cityCode", samplingInfoEntity.getCityCode());
		selCodition.put("countyCode", samplingInfoEntity.getCountyCode());
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getRecvCount", selCodition);
		if (iCount > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 拆包样品分发数据取得
	 * @param monitoringProjectEntity
	 * @param request
	 * @return
	 */
	public JSONObject getUnpackingDistributeDataGrid(MonitoringProjectEntity pageObj, DataGrid dataGrid){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(pageObj.getProjectCode())) {
			selCodition.put("projectCode", pageObj.getProjectCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getOgrname())) {
			selCodition.put("ogrname", pageObj.getOgrname());
		}
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getdCode())) {
			selCodition.put("dCode", pageObj.getdCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getSpCode())) {
			selCodition.put("spCode", pageObj.getSpCode());
		}
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForUnpackkingDistribute", selCodition);
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getUnpackingDistribute", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id")
				, new Db2Page("sampleCode")
				, new Db2Page("cname")
				, new Db2Page("spCode")
				, new Db2Page("dCode")
				, new Db2Page("orgCode")
				, new Db2Page("ogrname")
				, new Db2Page("reportingDate")
				, new Db2Page("oname")
				, new Db2Page("rn")
		};
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	
	/**
	 * 拆包分发前确认样品是否接收
	 * @param projectCode
	 */
	@Override
	public boolean checkRecvUnPacking(Map<String, Object> paramMap) {
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getRecvCount1", paramMap);
		if (iCount > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 样品分发数据保存
	 */
	public int saveSamplingDistribute(Map<String, Object> paramMap, int flg){
		int updRes = 0;
		if (1 == flg) {
			updRes = this.updateByMyBatis(NAME_SPACE+"saveSamplingDistribute", paramMap);
			// 设置当前项目整包拆包FLG,0:整包，1:拆包
			paramMap.put("flgValue", 0);
			this.updateByMyBatis(NAME_SPACE+"setProjectPackingFlg", paramMap);
		} else {
			updRes = this.updateByMyBatis(NAME_SPACE+"saveUnpSamplingDistribute", paramMap);
			// 设置当前项目整包拆包FLG,0:整包，1:拆包
			paramMap.put("flgValue", 1);
			this.updateByMyBatis(NAME_SPACE+"setProjectPackingFlg", paramMap);
		}
		return updRes;
	}

	/**
	 * 取得当前项目下的质检机构（当前抽样机构除外）
	 */
	public JSONObject getProjectOrgdatagrid (MonitoringProjectEntity pageObj, String orgCode, String sampleIdList, DataGrid dataGrid) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(pageObj.getProjectCode())) {
			selCodition.put("projectCode", pageObj.getProjectCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getOgrname())) {
			selCodition.put("ogrname", pageObj.getOgrname());
		}
		setBeginAndEnd(dataGrid, selCodition);
		
		Integer iCount = 0;
		List<Map<String, Object>> mapList = null;
		if (StringUtils.isNotEmpty(orgCode)) {//整包
			selCodition.put("orgCode", orgCode);
			
		    iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForProjectOrg", selCodition);
		    mapList = this.findListByMyBatis(NAME_SPACE+"getProjectOrg", selCodition);
		} else { // 拆包
			selCodition.put("sampleIdList", sampleIdList);
			iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForProjectOrg1", selCodition);
			mapList = this.findListByMyBatis(NAME_SPACE+"getProjectOrg1", selCodition);
		}
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id")
				, new Db2Page("ogrname")
				, new Db2Page("leader")
				, new Db2Page("contactstel")
		};
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}

	/**
	 * 取得制样编码的按钮是否可用
	 * 
	 * @param projectCode
	 *            项目code
	 * @return
	 */
	@Override
	public boolean getMakeCodeButtonEnable(String projectCode) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectCode", projectCode);
		List<Map<String, Object>> countList = this.findListByMyBatis(NAME_SPACE + "getProjectAndSampleCount", paramsMap);
		Long projectCount = 0L;
		Long sampleCount = 0L;
		for (Map<String, Object> countor : countList) {
			projectCount += ConverterUtil.toLong(countor.get("ACCOUNT"));
			sampleCount += ConverterUtil.toLong(countor.get("SICOUNT"));
		}
		if (!projectCount.equals(0) && projectCount.equals(sampleCount)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查项目是否上报
	 * @param projectCode
	 * @return
	 */
	public int checkReport(String projectCode){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", projectCode);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		Integer checkResult = this.getObjectByMyBatis(NAME_SPACE+"checkReport", selCodition);
		if (checkResult == null) {
			checkResult = 0;
		}
		return checkResult;
	}
	
	/**
	 * 检查样品信息是否上报
	 * @param samplingInfoId
	 * @return
	 */
	public int checkSampleReport(String samplingInfoId){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("samplingInfoId", samplingInfoId);
		int checkResult = (Integer)this.getObjectByMyBatis(NAME_SPACE+"checkSampleReport", selCodition);
		return checkResult;
	}
	
	/**
	 * 更据关键子搜索受检单位
	 * @param keyWords
	 * @return
	 */
	@Override
	public List<NkyMonitoringSiteEntity> getMonitoringSite (String keyWords) {
		if (StringUtils.isNotEmpty(keyWords)) {
			Map<String, Object> selCodition = new HashMap<String, Object>();
			selCodition.put("keyWords", keyWords);
			return this.findListByMyBatis(NAME_SPACE+"getMonitoringSite", selCodition);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> getCommonSampleDetail(SamplingInfoEntity samplingInfoEntity) {
		return this.getObjectByMyBatis(NAME_SPACE+"getCommonSampleDetail", samplingInfoEntity);
	}
	
	/**
	 * 取得项目对应的抽样品种
	 */
	@Override
	public List<MonitoringBreedEntity> getMonitoringBreedForProject(String projectCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("projectCode", projectCode);
		//return this.findListByMyBatis(NAME_SPACE + "getMonitoringBreedForProject", selCodition);
		List<String> beedCodes = this.findListByMyBatis(NAME_SPACE + "getMonitoringBreedForProject", selCodition);
		String beedCode0 = beedCodes.get(0);
		selCodition.put("beedCode0", beedCode0);
		beedCodes.remove(0);
		selCodition.put("beedCodes", beedCodes);
		
		return this.findListByMyBatis(NAME_SPACE + "getMonitoringBreeds", selCodition);
		
	}
	
	/**
	 * 取得抽样信息退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingSetBack(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getdCode())) {
			selCodition.put("dCode", pageObj.getdCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSamplingSetBackCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingSetBack", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("status")
				,new Db2Page("projectName")
				,new Db2Page("agrName")
				,new Db2Page("spCode")
				,new Db2Page("unitFullName")
				,new Db2Page("samplingDate")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 取得抽样信息退回列表数据(审核通过已退回)
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingSetBack2(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getdCode())) {
			selCodition.put("dCode", pageObj.getdCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSamplingSetBackCount2", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingSetBack2", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("status")
				,new Db2Page("projectName")
				,new Db2Page("agrName")
				,new Db2Page("spCode")
				,new Db2Page("unitFullName")
				,new Db2Page("samplingDate")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 取得抽样信息退回审核列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingSetBackCheck(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getdCode())) {
			selCodition.put("dCode", pageObj.getdCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}
		if (StringUtils.isNotEmpty(pageObj.getOgrName())) {
			selCodition.put("ogrName", pageObj.getOgrName());
		}

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSamplingSetBackCheckCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingSetBackCheck", selCodition);
		
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
	 * 取得盲样管理列表
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingBlindData(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getProjectCode())) {
			selCodition.put("projectCode", pageObj.getProjectCode().split(","));
		}
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		if (StringUtils.isNotEmpty(pageObj.getCityAndCountry())) {
			selCodition.put("areaName", pageObj.getCityAndCountry());
		}
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getSpCode())) {
			selCodition.put("spCode", pageObj.getSpCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("unitFullname", pageObj.getUnitFullname());
		}
		if (StringUtils.isNotEmpty(pageObj.getRemark())) {
			selCodition.put("remark", pageObj.getRemark());
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountSamplingBlindData", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingBlindData", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("projectCode")
				,new Db2Page("projectName")
				,new Db2Page("areaName")
				,new Db2Page("cname")
				,new Db2Page("spCode")
				,new Db2Page("sogrName")
				,new Db2Page("dogrName")
				,new Db2Page("sampleStatus")
				,new Db2Page("sampleCode")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}

	/**
	 * 取得添加盲样任务数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getAddSamplingBlindData(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("taskName", pageObj.getProjectName());
		}
		if (StringUtils.isNotEmpty(pageObj.getUnitFullname())) {
			selCodition.put("ogrName", pageObj.getUnitFullname());
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountForAddSamplingBlind", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getAddSamplingBlindData", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("taskName")
				,new Db2Page("taskCode")
				,new Db2Page("ogrName")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}

	
	/**
	 * 增加盲样选择样品 数据取得
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSelectSamplingBlind(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("leadunit", org.getCode());
		selCodition.put("taskCode", pageObj.getTaskCode());
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("sampleName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getSpCode())) {
			selCodition.put("spCode", pageObj.getSpCode());
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getCountSelectSamplingBlind", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSelectSamplingBlind", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("rn")
				,new Db2Page("cname")
				,new Db2Page("sampleCode")
				,new Db2Page("spCode")
				,new Db2Page("ogrName")
				,new Db2Page("monitoringLink")
		};
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("monitoringLink","allmonLink");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages, dataDicMap);
		return jObject;
	}
	
	/**
	 * 取得盲样列表导出
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportSamplingBlindInfo(Map<String, Object> paramMap) { 
		if(paramMap.get("projectCode") !=null && !"".equals(paramMap.get("projectCode"))){
			paramMap.put("projectCode", String.valueOf(paramMap.get("projectCode")).split(","));
		}else{
			paramMap.put("projectCode", null);
		}
		if(paramMap.get("cityAndCountry")  !=null && !"".equals(paramMap.get("cityAndCountry") )){
			paramMap.put("areaName", paramMap.get("cityAndCountry"));
		}
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		paramMap.put("leadunit", org.getCode());
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingBlindDataForExport", paramMap);
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		headerMap.put("RN", "title#KV#序号#EM#width#KV#10");
		headerMap.put("NAME", "title#KV#项目名称#EM#width#KV#30");
		headerMap.put("AREANAME", "title#KV#抽样地区#EM#width#KV#30");
		headerMap.put("CNAME", "title#KV#样品名称#EM#width#KV#50");
		headerMap.put("SP_CODE", "title#KV#制氧编码#EM#width#KV#20");
		headerMap.put("SOGRNAME", "title#KV#抽样单位#EM#width#KV#20");
		headerMap.put("DOGRNAME", "title#KV#检测单位#EM#width#KV#30");
		headerMap.put("BLIND_SAMPLE_VALUE", "title#KV#检测值#EM#width#KV#30");
		mapList.add(0, headerMap);
		
		return mapList;
	}
	
	public void saveProjectBreed(MonitoringBreedEntity monitoringBreedEntity) {
		this.updateByMyBatis(NAME_SPACE+"saveProjectBreed", monitoringBreedEntity);
	}
	
	/**
	 *取得抽样信息汇总页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getSampleCollectDatagrid(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = getSelCodition(pageObj);
		setBeginAndEnd(dataGrid, selCodition);
		if (pageObj.getSamplingDate() != null) {
			selCodition.put("samplingDate", pageObj.getSamplingDate());
		}
		Map<String, String> UserDataPrivMap = getUserDataPriv(pageObj.getProjectCode());
		selCodition.put("gl", UserDataPrivMap.get("gl"));
		selCodition.put("qt", UserDataPrivMap.get("qt"));
		selCodition.put("pt", UserDataPrivMap.get("pt"));
	
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSampleCollectCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSampleCollect", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("unitFullname")
				,new Db2Page("sampleCode")
				,new Db2Page("agrname")
				,new Db2Page("samplingDate")
				,new Db2Page("monitoringLink")
				,new Db2Page("taskCode")
				,new Db2Page("projectName")
				,new Db2Page("projectCode")
				,new Db2Page("countycode")
				,new Db2Page("dcode")
				,new Db2Page("spCode")
				,new Db2Page("rn")
				,new Db2Page("cityAndCountry")
				,new Db2Page("reportingDate")
		};
		
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("monitoringLink","allmonLink");
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages, dataDicMap);
		return jObject;
	}

	/**
	 * 取得抽样信息批量退回列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingBatchSetBack(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		selCodition.put("orgCode", org.getCode());
		if (StringUtils.isNotEmpty(pageObj.getProjectName())) {
			selCodition.put("projectName", pageObj.getProjectName());
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSamplingBatchSetBackCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingBatchSetBack", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("projectCode")
				,new Db2Page("projectName")
				,new Db2Page("ogrName")
				,new Db2Page("publishDate")
				,new Db2Page("aCount")
				,new Db2Page("rCount1")
				,new Db2Page("rCount2")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 取得抽样信息批量退回详情列表数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	@Override
	public JSONObject getSamplingBatchSetBackDetail(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		selCodition.put("projectCode", pageObj.getProjectCode());
		if (StringUtils.isNotEmpty(pageObj.getOgrName())) {
			selCodition.put("ogrName", pageObj.getOgrName());
		}
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getSamplingBatchSetBackDetailCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getSamplingBatchSetBackDetail", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("ogrName")
				,new Db2Page("rCount1")
				,new Db2Page("ogrCode")
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 *取得页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getBatchSetBackSampleList(SamplingInfoEntity pageObj, DataGrid dataGrid) {

		Map<String, Object> selCodition = getSelCodition(pageObj);
		if (StringUtils.isNotEmpty(pageObj.getSampleName())) {
			selCodition.put("agrName", pageObj.getSampleName());
		}
		if (StringUtils.isNotEmpty(pageObj.getDCode())) {
			selCodition.put("dCode", pageObj.getDCode());
		}
		if (StringUtils.isNotEmpty(pageObj.getSpCode())) {
			selCodition.put("spCode", pageObj.getSpCode());
		}

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getBatchSetBackSampleListCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getBatchSetBackSampleList", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("unitFullname")
				,new Db2Page("sampleCode")
				,new Db2Page("agrname")
				,new Db2Page("samplingDate")
				,new Db2Page("monitoringLink")
				,new Db2Page("taskCode")
				,new Db2Page("projectName")
				,new Db2Page("projectCode")
				,new Db2Page("countycode")
				,new Db2Page("dcode")
				,new Db2Page("spCode")
				,new Db2Page("rn")
				,new Db2Page("reportingDate")
		};
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}

}