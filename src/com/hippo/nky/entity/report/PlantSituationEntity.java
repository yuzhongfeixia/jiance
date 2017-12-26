package com.hippo.nky.entity.report;

import com.hippo.nky.entity.common.impl.PageNavigateEntity;

/**
 * @Title: Entity
 * @Description: 总体情况表
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */

public class PlantSituationEntity extends PageNavigateEntity {
	/** 质检机构code(搜索条件) */
	private java.lang.String orgCode;
	/** 项目编码(搜索条件) */
	private java.lang.String projectCode;
	/** 项目级别(搜索条件) */
	private java.lang.String projectLevel;
	/** 年份(搜索条件) */
	private java.lang.String year;
	/** 地区编码(搜索条件) */
	private java.lang.String areaCode;
	/** 行业(搜索条件) */
	private java.lang.String industryCode;
	/** 监测类型(搜索条件) */
	private java.lang.String type;
	/** 项目名称 */
	private java.lang.String projectName;
	/** 项目级别名称 */
	private java.lang.String projectLevelName;

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

	public java.lang.String getYear() {
		return year;
	}

	public void setYear(java.lang.String year) {
		this.year = year;
	}

	public java.lang.String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(java.lang.String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public java.lang.String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}

	public java.lang.String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(java.lang.String industryCode) {
		this.industryCode = industryCode;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getProjectName() {
		return projectName;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}

	public java.lang.String getProjectLevelName() {
		return projectLevelName;
	}

	public void setProjectLevelName(java.lang.String projectLevelName) {
		this.projectLevelName = projectLevelName;
	}

}
