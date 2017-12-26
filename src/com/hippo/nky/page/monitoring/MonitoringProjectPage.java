package com.hippo.nky.page.monitoring;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import com.hippo.nky.entity.monitoring.MonitoringSamplelinkEntity;
import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringDectionTempletEntity;
import com.hippo.nky.entity.monitoring.MonitoringOrganizationEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;

/**   
 * @Title: Entity
 * @Description: 检测项目数据
 * @author nky
 * @date 2013-11-06 17:46:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_project", schema = "")
@SuppressWarnings("serial")
public class MonitoringProjectPage implements java.io.Serializable {
	/**保存-检测环节数据*/
	private List<MonitoringSamplelinkEntity> monitoringSamplelinkList = new ArrayList<MonitoringSamplelinkEntity>();
	public List<MonitoringSamplelinkEntity> getMonitoringSamplelinkList() {
		return monitoringSamplelinkList;
	}
	public void setMonitoringSamplelinkList(List<MonitoringSamplelinkEntity> monitoringSamplelinkList) {
		this.monitoringSamplelinkList = monitoringSamplelinkList;
	}
	/**保存-监测地区及数量数据*/
	private List<MonitoringAreaCountEntity> monitoringAreaCountList = new ArrayList<MonitoringAreaCountEntity>();
	public List<MonitoringAreaCountEntity> getMonitoringAreaCountList() {
		return monitoringAreaCountList;
	}
	public void setMonitoringAreaCountList(List<MonitoringAreaCountEntity> monitoringAreaCountList) {
		this.monitoringAreaCountList = monitoringAreaCountList;
	}
	/**保存-抽样品种数据*/
	private List<MonitoringBreedEntity> monitoringBreedList = new ArrayList<MonitoringBreedEntity>();
	public List<MonitoringBreedEntity> getMonitoringBreedList() {
		return monitoringBreedList;
	}
	public void setMonitoringBreedList(List<MonitoringBreedEntity> monitoringBreedList) {
		this.monitoringBreedList = monitoringBreedList;
	}
	/**保存-检测污染物模板数据*/
	private List<MonitoringDectionTempletEntity> monitoringDectionTempletList = new ArrayList<MonitoringDectionTempletEntity>();
	public List<MonitoringDectionTempletEntity> getMonitoringDectionTempletList() {
		return monitoringDectionTempletList;
	}
	public void setMonitoringDectionTempletList(List<MonitoringDectionTempletEntity> monitoringDectionTempletList) {
		this.monitoringDectionTempletList = monitoringDectionTempletList;
	}
	/**保存-项目质检机构关系表*/
	private List<MonitoringOrganizationEntity> monitoringOrganizationList = new ArrayList<MonitoringOrganizationEntity>();
	public List<MonitoringOrganizationEntity> getMonitoringOrganizationList() {
		return monitoringOrganizationList;
	}
	public void setMonitoringOrganizationList(List<MonitoringOrganizationEntity> monitoringOrganizationList) {
		this.monitoringOrganizationList = monitoringOrganizationList;
	}
	/**保存-监测任务表*/
	private List<MonitoringTaskEntity> monitoringTaskList = new ArrayList<MonitoringTaskEntity>();
	public List<MonitoringTaskEntity> getMonitoringTaskList() {
		return monitoringTaskList;
	}
	public void setMonitoringTaskList(List<MonitoringTaskEntity> monitoringTaskList) {
		this.monitoringTaskList = monitoringTaskList;
	}


	/**编号*/
	private java.lang.String id;
	/**方案ID*/
	private java.lang.String planCode;
	/**项目名称*/
	private java.lang.String name;
	/**牵头单位编号*/
	private java.lang.String leadunit;
	/**监测开始时间*/
	private java.util.Date starttime;
	/**监测结束时间*/
	private java.util.Date endtime;
	/**是否抽检分离*/
	private java.lang.String detached;
	/**项目状态*/
	private java.lang.String state;
	/**项目ID*/
	private java.lang.String projectCode;
	/**发布时间*/
	private java.util.Date publishDate;
	/**抽样单模板*/
	private java.lang.String sampleTemplet;
	/**行业*/
	private java.lang.String industryCode;
	/** 判定标准版本id */
	private java.lang.String judgeVersionId;
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
	 *@return: java.lang.String  方案ID
	 */
	@Column(name ="PLAN_CODE",nullable=true,length=32)
	public java.lang.String getPlanCode(){
		return this.planCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  方案ID
	 */
	public void setPlanCode(java.lang.String planCode){
		this.planCode = planCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  牵头单位编号
	 */
	@Column(name ="LEADUNIT",nullable=true,length=32)
	public java.lang.String getLeadunit(){
		return this.leadunit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  牵头单位编号
	 */
	public void setLeadunit(java.lang.String leadunit){
		this.leadunit = leadunit;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  监测开始时间
	 */
	@Column(name ="STARTTIME",nullable=true,scale=6)
	public java.util.Date getStarttime(){
		return this.starttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  监测开始时间
	 */
	public void setStarttime(java.util.Date starttime){
		this.starttime = starttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  监测结束时间
	 */
	@Column(name ="ENDTIME",nullable=true,scale=6)
	public java.util.Date getEndtime(){
		return this.endtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  监测结束时间
	 */
	public void setEndtime(java.util.Date endtime){
		this.endtime = endtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否抽检分离
	 */
	@Column(name ="DETACHED",nullable=true,length=50)
	public java.lang.String getDetached(){
		return this.detached;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否抽检分离
	 */
	public void setDetached(java.lang.String detached){
		this.detached = detached;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目状态
	 */
	@Column(name ="STATE",nullable=true,length=50)
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目状态
	 */
	public void setState(java.lang.String state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目ID
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目ID
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布时间
	 */
	@Column(name ="PUBLISH_DATE",nullable=true,scale=6)
	public java.util.Date getPublishDate(){
		return this.publishDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布时间
	 */
	public void setPublishDate(java.util.Date publishDate){
		this.publishDate = publishDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽样单模板
	 */
	@Column(name ="SAMPLE_TEMPLET",nullable=true,length=50)
	public java.lang.String getSampleTemplet(){
		return this.sampleTemplet;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样单模板
	 */
	public void setSampleTemplet(java.lang.String sampleTemplet){
		this.sampleTemplet = sampleTemplet;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行业
	 */
	@Column(name ="INDUSTRY_CODE",nullable=true,length=50)
	public java.lang.String getIndustryCode(){
		return this.industryCode;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行业
	 */
	public void setIndustryCode(java.lang.String industryCode){
		this.industryCode = industryCode;
	}
	
	@Column(name ="JUDGE_VERSION_ID",nullable=true,length=32)
	public java.lang.String getJudgeVersionId() {
		return judgeVersionId;
	}

	public void setJudgeVersionId(java.lang.String judgeVersionId) {
		this.judgeVersionId = judgeVersionId;
	}
}
