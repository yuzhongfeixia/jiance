package com.hippo.nky.entity.monitoring;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 监测任务表
 * @author nky
 * @date 2013-11-06 17:46:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_task", schema = "")
@SuppressWarnings("serial")
public class MonitoringTaskEntity implements java.io.Serializable {
	/**编号*/
	private java.lang.String id;
	/**项目编号*/
	private java.lang.String projectCode;
	/**任务名称*/
	private java.lang.String taskname;
	/**质检机构*/
	private java.lang.String orgCode;
	/**行政区划*/
	private java.lang.String areacode;
	/**监测环节*/
	private java.lang.String monitoringLink;
	/**抽样品种*/
	private java.lang.String agrCode;
	/**抽样数量*/
	private java.lang.Integer samplingCount;
	/**任务编码*/
	private java.lang.String taskcode;


	/**抽样数量 字符串类型 */
	private java.lang.String samplingCounts;
	/**抽样单模板*/
	private java.lang.String sampleTemplet;
	/**方案级别**/
	private java.lang.String plevel;
	/**监测类型**/
	private java.lang.String monitorType;
	/**任务详情页面抽样地区（检索结果）**/
	private java.lang.String sampleArea;
	/**pad抽样单页面抽样品种（检索结果）**/
	private List<MonitoringBreedEntity> projectBreedList;
	/**pad任务详情（检索结果）**/
	private MonitoringTaskDetailsEntity taskDetail;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目编号
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目编号
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务名称
	 */
	@Column(name ="TASKNAME",nullable=true,length=16)
	public java.lang.String getTaskname(){
		return this.taskname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务名称
	 */
	public void setTaskname(java.lang.String taskname){
		this.taskname = taskname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  质检机构
	 */
	@Column(name ="ORG_CODE",nullable=true,length=32)
	public java.lang.String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  质检机构
	 */
	public void setOrgCode(java.lang.String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行政区划
	 */
	@Column(name ="AREACODE",nullable=true,length=6)
	public java.lang.String getAreacode(){
		return this.areacode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行政区划
	 */
	public void setAreacode(java.lang.String areacode){
		this.areacode = areacode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测环节
	 */
	@Column(name ="MONITORING_LINK",nullable=true,length=50)
	public java.lang.String getMonitoringLink(){
		return this.monitoringLink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测环节
	 */
	public void setMonitoringLink(java.lang.String monitoringLink){
		this.monitoringLink = monitoringLink;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽样品种
	 */
	@Column(name ="AGR_CODE",nullable=true,length=32)
	public java.lang.String getAgrCode(){
		return this.agrCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样品种
	 */
	public void setAgrCode(java.lang.String agrCode){
		this.agrCode = agrCode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  抽样数量
	 */
	@Column(name ="SAMPLING_COUNT",nullable=true)
	public java.lang.Integer getSamplingCount(){
		return this.samplingCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  抽样数量
	 */
	public void setSamplingCount(java.lang.Integer samplingCount){
		this.samplingCount = samplingCount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务编码
	 */
	@Column(name ="TASK_CODE",nullable=true,length=32)
	public java.lang.String getTaskcode() {
		return taskcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务编码
	 */
	public void setTaskcode(java.lang.String taskcode) {
		this.taskcode = taskcode;
	}

	@Transient
	public java.lang.String getSamplingCounts() {
		return samplingCounts;
	}

	public void setSamplingCounts(java.lang.String samplingCounts) {
		this.samplingCounts = samplingCounts;
	}
	
	@Transient
	public java.lang.String getSampleTemplet(){
		return this.sampleTemplet;
	}

	public void setSampleTemplet(java.lang.String sampleTemplet){
		this.sampleTemplet = sampleTemplet;
	}
	
	
	@Transient
	public java.lang.String getPlevel() {
		return plevel;
	}

	public void setPlevel(java.lang.String plevel) {
		this.plevel = plevel;
	}
	
	@Transient
	public java.lang.String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(java.lang.String monitorType) {
		this.monitorType = monitorType;
	}

	@Transient
	public java.lang.String getSampleArea() {
		return sampleArea;
	}

	public void setSampleArea(java.lang.String sampleArea) {
		this.sampleArea = sampleArea;
	}

	@Transient
	public List<MonitoringBreedEntity> getProjectBreedList() {
		return projectBreedList;
	}

	public void setProjectBreedList(List<MonitoringBreedEntity> projectBreedList) {
		this.projectBreedList = projectBreedList;
	}

	@Transient
	public MonitoringTaskDetailsEntity getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(MonitoringTaskDetailsEntity taskDetail) {
		this.taskDetail = taskDetail;
	}

	
}
