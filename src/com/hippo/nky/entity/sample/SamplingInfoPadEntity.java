package com.hippo.nky.entity.sample;

import java.util.List;

/**
 * @Title: Entity
 * @Description: 抽样信息
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */
@SuppressWarnings("serial")
public class SamplingInfoPadEntity implements java.io.Serializable, Cloneable {
	/** ID */
	private java.lang.String id;
	/** 监测任务 */
	private java.lang.String taskCode;
	/** 二维码 */
	private java.lang.String dCode;
	/** 样品图片 */
	private java.lang.String samplePath;
	/** 经度地理坐标 */
	private java.lang.String longitude;
	/** 纬度地理坐标 */
	private java.lang.String latitude;
	/** 农产品code */
	private java.lang.String agrCode;
	/** 抽样地市 */
	private java.lang.String cityCode;
	/** 抽样环节 */
	private java.lang.String monitoringLink;
	/** 抽样人(Pad) */
	private java.lang.String padId;
	/** 抽样时间 */
	private java.lang.String samplingDate;
	/** 抽样时间(导出用) */
	private java.lang.String samplingDateStr;
	/** 单位全称(受检) */
	private java.lang.String unitFullname;
	/** 通讯地址(受检) */
	private java.lang.String unitAddress;
	/** 邮编(受检) */
	private java.lang.String zipCode;
	/** 法定代表人(受检) */
	private java.lang.String legalPerson;
	/** 联系人(受检) */
	private java.lang.String contact;
	/** 电话(受检) */
	private java.lang.String telphone;
	/** 传真(受检) */
	private java.lang.String fax;
	/** 备注 */
	private java.lang.String remark;
	/** 样品编号 */
	private java.lang.String sampleCode;
	/** 实验室编码 */
	private java.lang.String labCode;
	/** 样品是否合格 */
	private java.lang.String isQualified;
	/** 抽样单状态 */
	private java.lang.String sampleStatus;
	/** 检测机构 */
	private java.lang.String detectionCode;
	/** 制样编码 */
	private java.lang.String spCode;
	/** 抽样区县 */
	private java.lang.String countyCode;
	/** 检测项目名称 */
	private java.lang.String projectName;
	/** 样品名称(搜索条件) */
	private java.lang.String sampleName;
	/**项目编码(搜索条件)*/
	private java.lang.String projectCode;
	/** 信息完整度*/
	private java.lang.String complete;
	/** 上报时间*/
	private java.util.Date reportingDate;
	/** 抽样单位*/
	private java.lang.String samplingOrgCode;
	/** 监测类型*/
	private java.lang.String monitorType;
	/** 年度*/
	private java.lang.String year;
	/**质检机构code(搜索条件) */
	private java.lang.String orgCode;
	/**打印数量 */
	private java.lang.Integer printCount;
	/** 图片内容*/
	private String imgContent;
	
	/** 地区(导出用)*/
	private java.lang.String cityAndCountry;
	
	/** 服务器路径图片*/
	private java.lang.String serverUrlPathImg;

	/** 抽样环节名称*/
	private java.lang.String monitoringLinkName;
	/**
	 * 抽样详细地址
	 */
	private java.lang.String samplingAddress;
	
	/**
	 * 抽样人员姓名
	 */
	private java.lang.String samplingPersons;
	
	/**
	 * 抽样单ID
	 */
	private java.lang.String samplingMonadId;
	
	/**
	 * 样品填报时间
	 */
	private java.lang.String samplingTime;
	
	private java.lang.String templete;
	
	/** 单位编码（受检）**/
	private java.lang.String unitFullcode;

	public java.lang.String getServerUrlPathImg() {
		return serverUrlPathImg;
	}

	public void setServerUrlPathImg(java.lang.String serverUrlPathImg) {
		this.serverUrlPathImg = serverUrlPathImg;
	}

	/** 例行监测类信息-- */
	private List<RoutinemonitoringEntity> routinemonitoringList;

	/** 普查类信息 **/
	private GeneralcheckEntity generalcheckEntity;

	/** 监督抽查类信息 */
	private SuperviseCheckEntity superviseCheckEntity;

	/** 生鲜乳实体 */
	private NkyFreshMilkEntity nkyFreshMilkEntity;

	/** 畜禽类信息 */
	private List<LivestockEntity> livestockEntityList;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(java.lang.String taskCode) {
		this.taskCode = taskCode;
	}

	public java.lang.String getdCode() {
		return dCode;
	}

	public void setdCode(java.lang.String dCode) {
		this.dCode = dCode;
	}

	public java.lang.String getSamplePath() {
		return samplePath;
	}

	public void setSamplePath(java.lang.String samplePath) {
		this.samplePath = samplePath;
	}

	public java.lang.String getLongitude() {
		return longitude;
	}

	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}

	public java.lang.String getLatitude() {
		return latitude;
	}

	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}

	public java.lang.String getAgrCode() {
		return agrCode;
	}

	public void setAgrCode(java.lang.String agrCode) {
		this.agrCode = agrCode;
	}

	public java.lang.String getCityCode() {
		return cityCode;
	}

	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}

	public java.lang.String getMonitoringLink() {
		return monitoringLink;
	}

	public void setMonitoringLink(java.lang.String monitoringLink) {
		this.monitoringLink = monitoringLink;
	}

	public java.lang.String getPadId() {
		return padId;
	}

	public void setPadId(java.lang.String padId) {
		this.padId = padId;
	}

	public java.lang.String getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(java.lang.String samplingDate) {
		this.samplingDate = samplingDate;
	}

	public java.lang.String getSamplingDateStr() {
		return samplingDateStr;
	}

	public void setSamplingDateStr(java.lang.String samplingDateStr) {
		this.samplingDateStr = samplingDateStr;
	}

	public java.lang.String getUnitFullname() {
		return unitFullname;
	}

	public void setUnitFullname(java.lang.String unitFullname) {
		this.unitFullname = unitFullname;
	}

	public java.lang.String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(java.lang.String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public java.lang.String getZipCode() {
		return zipCode;
	}

	public void setZipCode(java.lang.String zipCode) {
		this.zipCode = zipCode;
	}

	public java.lang.String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(java.lang.String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getTelphone() {
		return telphone;
	}

	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(java.lang.String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public java.lang.String getLabCode() {
		return labCode;
	}

	public void setLabCode(java.lang.String labCode) {
		this.labCode = labCode;
	}

	public java.lang.String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(java.lang.String isQualified) {
		this.isQualified = isQualified;
	}

	public java.lang.String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(java.lang.String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public java.lang.String getDetectionCode() {
		return detectionCode;
	}

	public void setDetectionCode(java.lang.String detectionCode) {
		this.detectionCode = detectionCode;
	}

	public java.lang.String getSpCode() {
		return spCode;
	}

	public void setSpCode(java.lang.String spCode) {
		this.spCode = spCode;
	}

	public java.lang.String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(java.lang.String countyCode) {
		this.countyCode = countyCode;
	}

	public java.lang.String getProjectName() {
		return projectName;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}

	public java.lang.String getSampleName() {
		return sampleName;
	}

	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}

	public java.lang.String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	public java.lang.String getComplete() {
		return complete;
	}

	public void setComplete(java.lang.String complete) {
		this.complete = complete;
	}

	public java.util.Date getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(java.util.Date reportingDate) {
		this.reportingDate = reportingDate;
	}

	public java.lang.String getSamplingOrgCode() {
		return samplingOrgCode;
	}

	public void setSamplingOrgCode(java.lang.String samplingOrgCode) {
		this.samplingOrgCode = samplingOrgCode;
	}

	public java.lang.String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(java.lang.String monitorType) {
		this.monitorType = monitorType;
	}

	public java.lang.String getYear() {
		return year;
	}

	public void setYear(java.lang.String year) {
		this.year = year;
	}

	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}

	public java.lang.Integer getPrintCount() {
		return printCount;
	}

	public void setPrintCount(java.lang.Integer printCount) {
		this.printCount = printCount;
	}

	public String getImgContent() {
		return imgContent;
	}

	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}

	public java.lang.String getCityAndCountry() {
		return cityAndCountry;
	}

	public void setCityAndCountry(java.lang.String cityAndCountry) {
		this.cityAndCountry = cityAndCountry;
	}

	public List<RoutinemonitoringEntity> getRoutinemonitoringList() {
		return routinemonitoringList;
	}

	public void setRoutinemonitoringList(
			List<RoutinemonitoringEntity> routinemonitoringList) {
		this.routinemonitoringList = routinemonitoringList;
	}

	public GeneralcheckEntity getGeneralcheckEntity() {
		return generalcheckEntity;
	}

	public void setGeneralcheckEntity(GeneralcheckEntity generalcheckEntity) {
		this.generalcheckEntity = generalcheckEntity;
	}

	public SuperviseCheckEntity getSuperviseCheckEntity() {
		return superviseCheckEntity;
	}

	public void setSuperviseCheckEntity(SuperviseCheckEntity superviseCheckEntity) {
		this.superviseCheckEntity = superviseCheckEntity;
	}

	public NkyFreshMilkEntity getNkyFreshMilkEntity() {
		return nkyFreshMilkEntity;
	}

	public void setNkyFreshMilkEntity(NkyFreshMilkEntity nkyFreshMilkEntity) {
		this.nkyFreshMilkEntity = nkyFreshMilkEntity;
	}

	public List<LivestockEntity> getLivestockEntityList() {
		return livestockEntityList;
	}

	public void setLivestockEntityList(List<LivestockEntity> livestockEntityList) {
		this.livestockEntityList = livestockEntityList;
	}

	public java.lang.String getMonitoringLinkName() {
		return monitoringLinkName;
	}

	public void setMonitoringLinkName(java.lang.String monitoringLinkName) {
		this.monitoringLinkName = monitoringLinkName;
	}
	
	public java.lang.String getSamplingAddress() {
		return samplingAddress;
	}

	public void setSamplingAddress(java.lang.String samplingAddress) {
		this.samplingAddress = samplingAddress;
	}

	public java.lang.String getSamplingPersons() {
		return samplingPersons;
	}

	public void setSamplingPersons(java.lang.String samplingPersons) {
		this.samplingPersons = samplingPersons;
	}
	
	public java.lang.String getSamplingMonadId() {
		return samplingMonadId;
	}

	public void setSamplingMonadId(java.lang.String samplingMonadId) {
		this.samplingMonadId = samplingMonadId;
	}
	
	public java.lang.String getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(java.lang.String samplingTime) {
		this.samplingTime = samplingTime;
	}
	
	public java.lang.String getTemplete() {
		return templete;
	}

	public void setTemplete(java.lang.String templete) {
		this.templete = templete;
	}
	
	public java.lang.String getUnitFullcode() {
		return unitFullcode;
	}
	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 单位编码(受检)
	 */
	public void setUnitFullcode(java.lang.String unitFullcode) {
		this.unitFullcode = unitFullcode;
	}
	
	
}
