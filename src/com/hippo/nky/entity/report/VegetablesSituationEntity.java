package com.hippo.nky.entity.report;

import com.hippo.nky.entity.common.impl.PageNavigateEntity;
import com.sun.star.lib.uno.environments.java.java_environment;

/**
 * @Title: Entity
 * @Description: 蔬菜情况统计结果
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */

public class VegetablesSituationEntity extends PageNavigateEntity {
	/** 类别 */
	private java.lang.String category;
	/** 抽样数 */
	private java.lang.Integer samplingCount;
	/** 检出数 */
	private java.lang.Integer detetionCount;
	/** 超标数 */
	private java.lang.Integer overStanderdCount;
	/** 超标率*/
	private java.lang.String overStanderdRate;
	/** 检出率*/
	private java.lang.String detetionRate;
	/** 监测环节(来源地)*/
	private java.lang.String monitoringLink;
	/** 来源地数量*/
	private java.lang.Integer unitCount;
	/** 农产品名称*/
	private java.lang.String agrName;

	public java.lang.String getCategory() {
		return category;
	}
	public void setCategory(java.lang.String category) {
		this.category = category;
	}
	public java.lang.Integer getSamplingCount() {
		return samplingCount;
	}
	public void setSamplingCount(java.lang.Integer samplingCount) {
		this.samplingCount = samplingCount;
	}
	public java.lang.Integer getDetetionCount() {
		return detetionCount;
	}
	public void setDetetionCount(java.lang.Integer detetionCount) {
		this.detetionCount = detetionCount;
	}
	public java.lang.Integer getOverStanderdCount() {
		return overStanderdCount;
	}
	public void setOverStanderdCount(java.lang.Integer overStanderdCount) {
		this.overStanderdCount = overStanderdCount;
	}
	public java.lang.String getOverStanderdRate() {
		return overStanderdRate;
	}
	public void setOverStanderdRate(java.lang.String overStanderdRate) {
		this.overStanderdRate = overStanderdRate;
	}
	public java.lang.String getDetetionRate() {
		return detetionRate;
	}
	public void setDetetionRate(java.lang.String detetionRate) {
		this.detetionRate = detetionRate;
	}
	public java.lang.String getMonitoringLink() {
		return monitoringLink;
	}
	public void setMonitoringLink(java.lang.String monitoringLink) {
		this.monitoringLink = monitoringLink;
	}
	public java.lang.Integer getUnitCount() {
		return unitCount;
	}
	public void setUnitCount(java.lang.Integer unitCount) {
		this.unitCount = unitCount;
	}
	public java.lang.String getAgrName() {
		return agrName;
	}
	public void setAgrName(java.lang.String agrName) {
		this.agrName = agrName;
	}
}
