package com.hippo.nky.entity.sample;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.excel.Excel;

/**
 * @Title: Entity
 * @Description: 抽样信息
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */
@Entity
@Table(name = "nky_sampling_info", schema = "")
@SuppressWarnings("serial")
public class SamplingInfoEntity implements java.io.Serializable, Cloneable {
	/** ID */
	private java.lang.String id;
	/** 监测任务 */
	private java.lang.String taskCode;
	/** 二维码 */
	@Excel(exportName="条码", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0, sortNum = 2)
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
	@Excel(exportName="抽样环节", exportConvertSign = 0, exportFieldWidth = 23, importConvertSign = 0, sortNum = 5)
	private java.lang.String monitoringLink;
	/** 抽样人(Pad) */
	private java.lang.String padId;
	/** 抽样时间 */
	private java.util.Date samplingDate;
	/** 抽样时间(导出用) */
	@Excel(exportName="抽样时间", exportConvertSign = 0, exportFieldWidth = 23, importConvertSign = 0, sortNum = 6)
	private java.lang.String samplingDateStr;
	/** 单位全称(受检) */
	@Excel(exportName="受检单位", exportConvertSign = 0, exportFieldWidth = 50, importConvertSign = 0, sortNum = 4)
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
	@Excel(exportName="制样编码", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0, sortNum = 0)
	private java.lang.String spCode;
	/** 抽样区县 */
	private java.lang.String countyCode;
	/** 检测项目名称 */
	private java.lang.String projectName;
	/** 样品名称(搜索条件) */
	@Excel(exportName="样品名称", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0, sortNum = 3)
	private java.lang.String sampleName;
	/**项目编码(搜索条件)*/
	private java.lang.String projectCode;
	/** 信息完整度*/
	private java.lang.String complete;
	/** 上报时间*/
	private java.util.Date reportingDate;
	/** 检测上报时间*/
	private java.util.Date detectionReportingDate;
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
	@Excel(exportName="地区", exportConvertSign = 0, exportFieldWidth = 15, importConvertSign = 0, sortNum = 1)
	private java.lang.String cityAndCountry;
	
	/** 单位编码（受检）**/
	private java.lang.String unitFullcode;
	
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
	 * 制样编码前缀
	 */
	private java.lang.String pre_spcode;
	
	/**
	 * 制样编码(会显用)
	 */
	private java.lang.String show_spcode;
	
	/**
	 * 样品填报时间
	 */
	private java.lang.String samplingTime;
	
	private java.lang.String templete;
	

	/** 例行监测类信息-- */
	private List<RoutinemonitoringEntity> routinemonitoringList;

	@Transient
	public List<RoutinemonitoringEntity> getRoutinemonitoringList() {
		return routinemonitoringList;
	}

	public void setRoutinemonitoringList(
			List<RoutinemonitoringEntity> routinemonitoringList) {
		this.routinemonitoringList = routinemonitoringList;
	}

	/** 普查类信息 **/
	private GeneralcheckEntity generalcheckEntity;

	@Transient
	public GeneralcheckEntity getGeneralcheckEntity() {
		return generalcheckEntity;
	}

	public void setGeneralcheckEntity(GeneralcheckEntity generalcheckEntity) {
		this.generalcheckEntity = generalcheckEntity;
	}

	/** 监督抽查类信息 */
	private SuperviseCheckEntity superviseCheckEntity;

	@Transient
	public SuperviseCheckEntity getSuperviseCheckEntity() {
		return superviseCheckEntity;
	}

	public void setSuperviseCheckEntity(
			SuperviseCheckEntity superviseCheckEntity) {
		this.superviseCheckEntity = superviseCheckEntity;
	}

	/** 生鲜乳实体 */
	private NkyFreshMilkEntity nkyFreshMilkEntity;

	@Transient
	public NkyFreshMilkEntity getNkyFreshMilkEntity() {
		return nkyFreshMilkEntity;
	}

	public void setNkyFreshMilkEntity(NkyFreshMilkEntity nkyFreshMilkEntity) {
		this.nkyFreshMilkEntity = nkyFreshMilkEntity;
	}

//	/** 畜禽类信息 */
//	private LivestockEntity livestockEntity;
//
//	@Transient
//	public LivestockEntity getLivestockEntity() {
//		return livestockEntity;
//	}
//
//	public void setLivestockEntity(LivestockEntity livestockEntity) {
//		this.livestockEntity = livestockEntity;
//	}
	
	private List<LivestockEntity> livestockEntityList;
	
	@Transient
	public List<LivestockEntity> getLivestockEntityList() {
		return livestockEntityList;
	}

	public void setLivestockEntityList(List<LivestockEntity> livestockEntityList) {
		this.livestockEntityList = livestockEntityList;
	}
	
	/** 单位名称 */
	private java.lang.String ogrName;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String ID
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 监测任务
	 */
	@Column(name = "TASK_CODE", nullable = true, length = 32)
	public java.lang.String getTaskCode() {
		return taskCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 监测任务
	 */
	public void setTaskCode(java.lang.String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码
	 */
	@Column(name = "D_CODE", nullable = true, length = 50)
	public java.lang.String getdCode() {
		return dCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码
	 */
	public void setdCode(java.lang.String dCode) {
		this.dCode = dCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码
	 */
	@Transient
	public java.lang.String getDCode() {
		return dCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码
	 */
	public void setDCode(java.lang.String dCode) {
		this.dCode = dCode;
	}

	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品图片
	 */
	@Column(name = "SAMPLE_PATH", nullable = true, length = 128)
	public java.lang.String getSamplePath() {
		return this.samplePath;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品图片
	 */
	public void setSamplePath(java.lang.String samplePath) {
		this.samplePath = samplePath;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 经度地理坐标
	 */
	@Column(name = "LONGITUDE", nullable = true, length = 32)
	public java.lang.String getLongitude() {
		return this.longitude;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 经度地理坐标
	 */
	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 纬度地理坐标
	 */
	@Column(name = "LATITUDE", nullable = true, length = 32)
	public java.lang.String getLatitude() {
		return this.latitude;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 纬度地理坐标
	 */
	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 农产品code
	 */
	@Column(name = "AGR_CODE", nullable = true, length = 32)
	public java.lang.String getAgrCode() {
		return this.agrCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 农产品code
	 */
	public void setAgrCode(java.lang.String agrCode) {
		this.agrCode = agrCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样地市
	 */
	@Column(name = "CITY_CODE", nullable = true, length = 6)
	public java.lang.String getCityCode() {
		return this.cityCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样地市
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样环节
	 */
	@Column(name = "MONITORING_LINK", nullable = true, length = 50)
	public java.lang.String getMonitoringLink() {
		return this.monitoringLink;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样环节
	 */
	public void setMonitoringLink(java.lang.String monitoringLink) {
		this.monitoringLink = monitoringLink;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样人(Pad)
	 */
	@Column(name = "PAD_ID", nullable = true, length = 32)
	public java.lang.String getPadId() {
		return this.padId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样人(Pad)
	 */
	public void setPadId(java.lang.String padId) {
		this.padId = padId;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 抽样时间
	 */
	@Column(name = "SAMPLING_DATE", nullable = true, scale = 6)
	public java.util.Date getSamplingDate() {
		return this.samplingDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 抽样时间
	 */
	public void setSamplingDate(java.util.Date samplingDate) {
		this.samplingDate = samplingDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 单位全称(受检)
	 */
	@Column(name = "UNIT_FULLNAME", nullable = true, length = 64)
	public java.lang.String getUnitFullname() {
		return this.unitFullname;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 单位全称(受检)
	 */
	public void setUnitFullname(java.lang.String unitFullname) {
		this.unitFullname = unitFullname;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 通讯地址(受检)
	 */
	@Column(name = "UNIT_ADDRESS", nullable = true, length = 128)
	public java.lang.String getUnitAddress() {
		return this.unitAddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 通讯地址(受检)
	 */
	public void setUnitAddress(java.lang.String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 邮编(受检)
	 */
	@Column(name = "ZIP_CODE", nullable = true, length = 6)
	public java.lang.String getZipCode() {
		return this.zipCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 邮编(受检)
	 */
	public void setZipCode(java.lang.String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 法定代表人(受检)
	 */
	@Column(name = "LEGAL_PERSON", nullable = true, length = 12)
	public java.lang.String getLegalPerson() {
		return this.legalPerson;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 法定代表人(受检)
	 */
	public void setLegalPerson(java.lang.String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 联系人(受检)
	 */
	@Column(name = "CONTACT", nullable = true, length = 12)
	public java.lang.String getContact() {
		return this.contact;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 联系人(受检)
	 */
	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 电话(受检)
	 */
	@Column(name = "TELPHONE", nullable = true, length = 12)
	public java.lang.String getTelphone() {
		return this.telphone;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 电话(受检)
	 */
	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 传真(受检)
	 */
	@Column(name = "FAX", nullable = true, length = 12)
	public java.lang.String getFax() {
		return this.fax;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 传真(受检)
	 */
	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 备注
	 */
	@Column(name = "REMARK", nullable = true, length = 255)
	public java.lang.String getRemark() {
		return this.remark;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 备注
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品编号
	 */
	@Column(name = "SAMPLE_CODE", nullable = true, length = 32)
	public java.lang.String getSampleCode() {
		return this.sampleCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品编号
	 */
	public void setSampleCode(java.lang.String sampleCode) {
		this.sampleCode = sampleCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 实验室编码
	 */
	@Column(name = "LAB_CODE", nullable = true, length = 32)
	public java.lang.String getLabCode() {
		return this.labCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 实验室编码
	 */
	public void setLabCode(java.lang.String labCode) {
		this.labCode = labCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品是否合格
	 */
	@Column(name = "IS_QUALIFIED", nullable = true, length = 50)
	public java.lang.String getIsQualified() {
		return this.isQualified;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品是否合格
	 */
	public void setIsQualified(java.lang.String isQualified) {
		this.isQualified = isQualified;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样单状态
	 */
	@Column(name = "SAMPLE_STATUS", nullable = true, length = 50)
	public java.lang.String getSampleStatus() {
		return this.sampleStatus;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样单状态
	 */
	public void setSampleStatus(java.lang.String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 检测机构
	 */
	@Column(name = "DETECTION_CODE", nullable = true, length = 32)
	public java.lang.String getDetectionCode() {
		return this.detectionCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 检测机构
	 */
	public void setDetectionCode(java.lang.String detectionCode) {
		this.detectionCode = detectionCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 制样编码
	 */
	@Column(name = "SP_CODE", nullable = true, length = 32)
	public java.lang.String getSpCode() {
		return this.spCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 制样编码
	 */
	public void setSpCode(java.lang.String spCode) {
		this.spCode = spCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样区县
	 */
	@Column(name = "COUNTY_CODE", nullable = true, length = 6)
	public java.lang.String getCountyCode() {
		return this.countyCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样区县
	 */
	public void setCountyCode(java.lang.String countyCode) {
		this.countyCode = countyCode;
	}

	@Transient
	public java.lang.String getProjectName() {
		return projectName;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}

	public Object clone() {
		SamplingInfoEntity o = null;
		try {
			o = (SamplingInfoEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品名称
	 */
	@Transient
	public java.lang.String getSampleName() {
		return sampleName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品名称
	 */
	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 项目编码
	 */
	 @Transient
	public java.lang.String getProjectCode() {
		return projectCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 项目编码
	 */
	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 信息完整度
	 */
	@Column(name = "COMPLETE", nullable = true, length = 1)
	public java.lang.String getComplete() {
		return this.complete;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 上报时间
	 */
	public void setReportingDate(java.util.Date reportingDate) {
		this.reportingDate = reportingDate;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 上报时间
	 */
	@Column(name = "REPORTING_DATE", nullable = true, scale = 6)
	public java.util.Date getReportingDate() {
		return this.reportingDate;
	}
	
	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 上报时间
	 */
	public void setDetectionReportingDate(java.util.Date detectionReportingDate) {
		this.detectionReportingDate = detectionReportingDate;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 上报时间
	 */
	@Column(name = "DETECTION_REPORTING_DATE", nullable = true, scale = 6)
	public java.util.Date getDetectionReportingDate() {
		return this.detectionReportingDate;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样单位
	 */
	public void setSamplingOrgCode(java.lang.String samplingOrgCode) {
		this.samplingOrgCode = samplingOrgCode;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样单位
	 */
	@Column(name = "SAMPLING_ORG_CODE", nullable = true, length = 32)
	public java.lang.String getSamplingOrgCode() {
		return this.samplingOrgCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 信息完整度
	 */
	public void setComplete(java.lang.String complete) {
		this.complete = complete;
	}

	@Transient
	public java.lang.String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(java.lang.String monitorType) {
		this.monitorType = monitorType;
	}
	@Transient
	public java.lang.String getYear() {
		return year;
	}

	public void setYear(java.lang.String year) {
		this.year = year;
	}
	@Transient
	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 取得地区
	 */
	@Transient
	public java.lang.String getCityAndCountry() {
		return cityAndCountry;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 设置地区
	 */
	public void setCityAndCountry(java.lang.String cityAndCountry) {
		this.cityAndCountry = cityAndCountry;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 取得抽样时间
	 */
	@Transient
	public java.lang.String getSamplingDateStr() {
		return samplingDateStr;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 设置抽样时间
	 */
	public void setSamplingDateStr(java.lang.String samplingDateStr) {
		this.samplingDateStr = samplingDateStr;
	}
	@Column(name = "PRINT_COUNT", nullable = true, length = 50)
	public java.lang.Integer getPrintCount() {
		return printCount;
	}

	public void setPrintCount(java.lang.Integer printCount) {
		this.printCount = printCount;
	}
	
	/**
	 * 方法: 设置byte[]
	 * 
	 * @param: byte[] 设置图片内容
	 */
	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}

	/**
	 * 方法: 取得byte[]
	 * 
	 * @return: byte[] 取得图片内容
	 */
	@Transient
	public String getImgContent() {
		return imgContent;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 单位编码(受检)
	 */
	@Column(name = "UNIT_FULLCODE", nullable = true, length = 32)
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
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样详细地址
	 */
	@Column(name = "SAMPLING_ADDRESS", nullable = true, length = 128)
	public java.lang.String getSamplingAddress() {
		return samplingAddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样详细地址
	 */
	public void setSamplingAddress(java.lang.String samplingAddress) {
		this.samplingAddress = samplingAddress;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样人员姓名
	 */
	@Column(name = "SAMPLING_PERSONS", nullable = true, length = 50)
	public java.lang.String getSamplingPersons() {
		return samplingPersons;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样人员姓名
	 */
	public void setSamplingPersons(java.lang.String samplingPersons) {
		this.samplingPersons = samplingPersons;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样单ID
	 */
	@Column(name = "SAMPLING_MONAD_ID", nullable = true, length = 30)
	public java.lang.String getSamplingMonadId() {
		return samplingMonadId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样单ID
	 */
	public void setSamplingMonadId(java.lang.String samplingMonadId) {
		this.samplingMonadId = samplingMonadId;
	}

	@Transient
	public java.lang.String getOgrName() {
		return ogrName;
	}

	public void setOgrName(java.lang.String ogrName) {
		this.ogrName = ogrName;
	}

	@Transient
	public java.lang.String getPre_spcode() {
		return pre_spcode;
	}

	public void setPre_spcode(java.lang.String pre_spcode) {
		this.pre_spcode = pre_spcode;
	}
	
	@Transient
	public java.lang.String getShow_spcode() {
		return show_spcode;
	}

	public void setShow_spcode(java.lang.String show_spcode) {
		this.show_spcode = show_spcode;
	}

	@Column(name = "SAMPLING_TIME", nullable = true, length = 50)
	public java.lang.String getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(java.lang.String samplingTime) {
		this.samplingTime = samplingTime;
	}

	@Transient
	public java.lang.String getTemplete() {
		return templete;
	}

	public void setTemplete(java.lang.String templete) {
		this.templete = templete;
	}
	
	
	
}
