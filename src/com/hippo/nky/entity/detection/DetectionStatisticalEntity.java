package com.hippo.nky.entity.detection;


/**
 * @Title: Entity
 * @Description: 检测情况完成统计列表
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */
@SuppressWarnings("serial")
public class DetectionStatisticalEntity implements java.io.Serializable {
	/** 农产品名*/
	private java.lang.String agrName;

	/** 任务名 */
	private java.lang.String taskName;
	
	/**检测机构*/
	private java.lang.String dectectionOrgName;

	/**上报时间*/
	private java.lang.String reportingDate;
	
	/**任务数*/
	private java.lang.Integer taskCount;
	
	/**实际完成数*/
	private java.lang.Integer actualCount;

	public java.lang.String getAgrName() {
		return agrName;
	}

	public void setAgrName(java.lang.String agrName) {
		this.agrName = agrName;
	}

	public java.lang.String getTaskName() {
		return taskName;
	}

	public void setTaskName(java.lang.String taskName) {
		this.taskName = taskName;
	}

	public java.lang.String getDectectionOrgName() {
		return dectectionOrgName;
	}

	public void setDectectionOrgName(java.lang.String dectectionOrgName) {
		this.dectectionOrgName = dectectionOrgName;
	}

	public java.lang.String getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(java.lang.String reportingDate) {
		this.reportingDate = reportingDate;
	}

	public java.lang.Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(java.lang.Integer taskCount) {
		this.taskCount = taskCount;
	}

	public java.lang.Integer getActualCount() {
		return actualCount;
	}

	public void setActualCount(java.lang.Integer actualCount) {
		this.actualCount = actualCount;
	}


	
	
}
