package com.hippo.nky.entity.detection;

import com.hippo.nky.entity.common.impl.PageNavigateEntity;

/**
 * @Title: Entity
 * @Description: 检测信息
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */

public class DetectionEntity extends PageNavigateEntity {
	/** 质检机构code(搜索条件) */
	private java.lang.String orgCode;
	/** id(搜索条件) */
	private java.lang.String id;
	/** 项目编码(搜索条件) */
	private java.lang.String projectCode;
	/** 实验室编码前缀 */
	private java.lang.String labPre;
	/** 起始序号 */
	private java.lang.String startSer;
	/** 实验室编码 */
	private java.lang.String labCode;
	/** 样品名称(搜索条件) */
	private java.lang.String sampleName;
	/** 污染物名称(搜索条件) */
	private java.lang.String pollName;
	/** 样品编号 */
	private java.lang.String sampleCode;
	/** 实验室编码 (搜索条件) */
	private java.lang.String searchLabCode;
	/** 样品code */
	private java.lang.String agrCode;
	/** 制样编码 */
	private java.lang.String spCode;
	/** 上报时间(画面判断用) */
	private java.lang.String reportingDate;
	/** 是否是录入(画面判断用) */
	private java.lang.String isCreate;
	/** CAS码(查询条件) */
	private java.lang.String casCode;
	/** 是否是明细数据 */
	private java.lang.String isDetail;
	private java.lang.String pdfURL;
	/**检出值（上报页表示用）*/
	private java.math.BigDecimal detectionValue;
	/**过滤上报页条件*/
	private java.lang.String selCondtion;
	/**样品编状态*/
	private java.lang.String sampleStatus;
	/** 检测上报时间*/
	private java.util.Date detectionReportingDate;
	private java.lang.String RN;
	
	private java.lang.String detachedFlg;
	
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
	public java.util.Date getDetectionReportingDate() {
		return this.detectionReportingDate;
	}
	
	public java.lang.String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(java.lang.String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}

	public java.lang.String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	public java.lang.String getLabPre() {
		return labPre;
	}

	public void setLabPre(java.lang.String labPre) {
		this.labPre = labPre;
	}

	public java.lang.String getStartSer() {
		return startSer;
	}

	public void setStartSer(java.lang.String startSer) {
		this.startSer = startSer;
	}

	public java.lang.String getLabCode() {
		return labCode;
	}

	public void setLabCode(java.lang.String labCode) {
		this.labCode = labCode;
	}

	public java.lang.String getSampleName() {
		return sampleName;
	}

	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}

	public java.lang.String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(java.lang.String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public java.lang.String getSearchLabCode() {
		return searchLabCode;
	}

	public void setSearchLabCode(java.lang.String searchLabCode) {
		this.searchLabCode = searchLabCode;
	}

	/**
	 * 取得污染物名称
	 * 
	 * @return 污染物名称
	 */
	public java.lang.String getPollName() {
		return pollName;
	}

	/**
	 * 设置污染物名称
	 * 
	 * @param pollName
	 *            污染物名称
	 */
	public void setPollName(java.lang.String pollName) {
		this.pollName = pollName;
	}

	/**
	 * 取得样品code（农产品CODE）
	 * 
	 * @return 样品code（农产品CODE）
	 */
	public java.lang.String getAgrCode() {
		return agrCode;
	}

	/**
	 * 设置 样品code（农产品CODE）
	 * 
	 * @param ageCode
	 *            样品code（农产品CODE）
	 */
	public void setAgrCode(java.lang.String ageCode) {
		this.agrCode = ageCode;
	}

	/**
	 * 取得制样编码
	 * 
	 * @return 制样编码
	 */
	public java.lang.String getSpCode() {
		return spCode;
	}

	/**
	 * 设置制样编码
	 * 
	 * @param spCode
	 *            制样编码
	 */
	public void setSpCode(java.lang.String spCode) {
		this.spCode = spCode;
	}

	/**
	 * 取得上报时间
	 * 
	 * @return 上报时间
	 */
	public java.lang.String getReportingDate() {
		return reportingDate;
	}

	/**
	 * 设置上报时间
	 * 
	 * @param reportingDate
	 *            上报时间
	 */
	public void setReportingDate(java.lang.String reportingDate) {
		this.reportingDate = reportingDate;
	}

	/**
	 * 是否是录入
	 * 
	 * @return 是否是录入
	 */
	public java.lang.String getIsCreate() {
		return isCreate;
	}

	/**
	 * 是否是录入
	 * 
	 * @param isCreate
	 *            是否是录入
	 */
	public void setIsCreate(java.lang.String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * CAS码
	 * 
	 * @return CAS码
	 */
	public java.lang.String getCasCode() {
		return casCode;
	}

	/**
	 * CAS码
	 * 
	 * @param casCode
	 *            CAS码
	 */
	public void setCasCode(java.lang.String casCode) {
		this.casCode = casCode;
	}

	/**
	 * 是否是明细数据
	 * 
	 * @return
	 */
	public java.lang.String getIsDetail() {
		return isDetail;
	}

	/**
	 * 是否是明细数据
	 * 
	 * @param isDetail
	 */
	public void setIsDetail(java.lang.String isDetail) {
		this.isDetail = isDetail;
	}

	public java.lang.String getPdfURL() {
		return pdfURL;
	}

	public void setPdfURL(java.lang.String pdfURL) {
		this.pdfURL = pdfURL;
	}

	
	/**
	 * 检出值（上报页表示用）
	 * 
	 * @return
	 */
	public java.math.BigDecimal getDetectionValue() {
		return detectionValue;
	}

	/**
	 * 检出值（上报页表示用）
	 * 
	 * @param detectionValue
	 */
	public void setDetectionValue(java.math.BigDecimal detectionValue) {
		this.detectionValue = detectionValue;
	}

	/**
	 * 过滤上报页条件
	 * 
	 * @return
	 */
	public java.lang.String getSelCondtion() {
		return selCondtion;
	}

	/**
	 * 过滤上报页条件
	 * 
	 * @param selCondtion
	 */
	public void setSelCondtion(java.lang.String selCondtion) {
		this.selCondtion = selCondtion;
	}
	
	public java.lang.String getRN() {
		return RN;
	}

	public void setRN(java.lang.String rN) {
		RN = rN;
	}

	public java.lang.String getDetachedFlg() {
		return detachedFlg;
	}

	public void setDetachedFlg(java.lang.String detachedFlg) {
		this.detachedFlg = detachedFlg;
	}
	
	
	
}
