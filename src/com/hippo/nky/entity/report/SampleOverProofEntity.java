package com.hippo.nky.entity.report;

import com.hippo.nky.entity.common.impl.PageNavigateEntity;
import com.sun.star.lib.uno.environments.java.java_environment;

/**
 * @Title: Entity
 * @Description: 统计结果用
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */

public class SampleOverProofEntity extends PageNavigateEntity {
	/** 排名*/
	private java.lang.String rank;
	/** 区县*/
	private java.lang.String countyArea;
	/** 抽样数 */
	private java.lang.Integer samplingCount;
	/** 合格数 */
	private java.lang.Integer qualifiedCount;
	/** 合格率*/
	private java.lang.String qualifiedRate;
	/** 监测环节(来源地)*/
	private java.lang.String monitoringLink;
	/** 农产品名称*/
	private java.lang.String agrName;
	/** 污染物名称*/
	private java.lang.String pollName;
	/** 检出次数*/
	private java.lang.Integer detectionCount;
	/** 不合格次数*/
	private java.lang.Integer unQualifiedCount;

	public java.lang.String getRank() {
		return rank;
	}
	public void setRank(java.lang.String rank) {
		this.rank = rank;
	}
	public java.lang.String getCountyArea() {
		return countyArea;
	}
	public void setCountyArea(java.lang.String countyArea) {
		this.countyArea = countyArea;
	}
	public java.lang.Integer getSamplingCount() {
		return samplingCount;
	}
	public void setSamplingCount(java.lang.Integer samplingCount) {
		this.samplingCount = samplingCount;
	}
	public java.lang.Integer getQualifiedCount() {
		return qualifiedCount;
	}
	public void setQualifiedCount(java.lang.Integer qualifiedCount) {
		this.qualifiedCount = qualifiedCount;
	}
	public java.lang.String getQualifiedRate() {
		return qualifiedRate;
	}
	public void setQualifiedRate(java.lang.String qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}
	public java.lang.String getMonitoringLink() {
		return monitoringLink;
	}
	public void setMonitoringLink(java.lang.String monitoringLink) {
		this.monitoringLink = monitoringLink;
	}
	public java.lang.String getAgrName() {
		return agrName;
	}
	public void setAgrName(java.lang.String agrName) {
		this.agrName = agrName;
	}
	public java.lang.String getPollName() {
		return pollName;
	}
	public void setPollName(java.lang.String pollName) {
		this.pollName = pollName;
	}
	public java.lang.Integer getDetectionCount() {
		return detectionCount;
	}
	public void setDetectionCount(java.lang.Integer detectionCount) {
		this.detectionCount = detectionCount;
	}
	public java.lang.Integer getUnQualifiedCount() {
		return unQualifiedCount;
	}
	public void setUnQualifiedCount(java.lang.Integer unQualifiedCount) {
		this.unQualifiedCount = unQualifiedCount;
	}

}
