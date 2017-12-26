package com.hippo.nky.entity.monitoring;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import jeecg.system.pojo.base.TSType;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 检测项目数据
 * @author nky
 * @date 2013-10-30 13:50:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_project", schema = "")
@SuppressWarnings("serial")
public class MonitoringProjectEntity implements java.io.Serializable {
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
    /**整拆包分发FLG*/
	private java.lang.Integer packingFlg;
	/** 用户类型（0:管理部门；1：质检机构） */
	private java.lang.String userType;
	/**方案级别(检索结果用)**/
	private java.lang.String plevel;	
	/**起始发布时间(检索条件)*/
	private java.util.Date publishDate_begin;
	
	/**终了发布时间(检索条件)*/
	private java.util.Date publishDate_end;

	/**机构名称(检索条件)*/
	private java.lang.String ogrname;
	
	/**样品编码(检索条件)*/
	private java.lang.String spCode;
	
	/**条码(检索条件)*/
	private java.lang.String dCode;
	
	/**样品名称(检索条件)*/
	private java.lang.String sampleName;
	
	/**任务名称(检索条件)*/
	private java.lang.String taskName;
	
	/**客户端pad(检索条件)**/
	private java.lang.String padId;
	
	/**监测类型(检索结果)*/
	private java.lang.String type;
	
	/** 判定标准版本id */
	private java.lang.String judgeVersionId;
	
	/**项目任务总数量(检索结果)**/
	private java.lang.String allCount;
	
	/**项目抽样完成数量(检索结果)**/
	private java.lang.String cyCount;
	
	/**项目抽样信息完整的样品数量(检索结果)**/
	private java.lang.String cycmCount;
	
	/**项目检测完成数量(检索结果)**/
	private java.lang.String jcCount;
	
	/**pad任务详情页面抽样品种集合(检索结果)**/
	private String projectBreed;
	/**pad抽样单页面抽样环节集合(检索结果)**/
	private List<TSType> linkInfoList;
	/**任务详情页面抽样环节（检索结果）**/
	private java.lang.String linkInfo;
	/**抽样单类型 (1,2,3,4,5)*/
	private java.lang.String templete;
	
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
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  整拆包分发FLG
	 */
	@Column(name ="PACKING_FLG",nullable=true)
	public java.lang.Integer getPackingFlg(){
		return this.packingFlg;
	}

	/**
	 *方法: 设置java.lang.Integer	
	 *@param: java.lang.Integer  整拆包分发FLG
	 */
	public void setPackingFlg(java.lang.Integer packingFlg){
		this.packingFlg = packingFlg;
	}
	
	@Transient
	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}

	@Transient
	public java.lang.String getPlevel() {
		return plevel;
	}

	public void setPlevel(java.lang.String plevel) {
		this.plevel = plevel;
	}
	
	@Transient
	public java.util.Date getPublishDate_begin() {
		return publishDate_begin;
	}

	public void setPublishDate_begin(java.util.Date publishDate_begin) {
		this.publishDate_begin = publishDate_begin;
	}
	
	@Transient
	public java.util.Date getPublishDate_end() {
		return publishDate_end;
	}

	public void setPublishDate_end(java.util.Date publishDate_end) {
		this.publishDate_end = publishDate_end;
	}
	
	@Transient
	public java.lang.String getOgrname() {
		return ogrname;
	}

	public void setOgrname(java.lang.String ogrname) {
		this.ogrname = ogrname;
	}

	@Transient
	public java.lang.String getSpCode() {
		return spCode;
	}

	public void setSpCode(java.lang.String spCode) {
		this.spCode = spCode;
	}

	@Transient
	public java.lang.String getdCode() {
		return dCode;
	}

	public void setdCode(java.lang.String dCode) {
		this.dCode = dCode;
	}
	
	@Transient
	public java.lang.String getSampleName() {
		return sampleName;
	}

	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}

	@Transient
	public java.lang.String getTaskName() {
		return taskName;
	}

	public void setTaskName(java.lang.String taskName) {
		this.taskName = taskName;
	}

	@Transient
	public java.lang.String getPadId() {
		return padId;
	}

	public void setPadId(java.lang.String padId) {
		this.padId = padId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测类型
	 */
	@Transient
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}

	@Column(name ="JUDGE_VERSION_ID",nullable=true,length=32)
	public java.lang.String getJudgeVersionId() {
		return judgeVersionId;
	}

	public void setJudgeVersionId(java.lang.String judgeVersionId) {
		this.judgeVersionId = judgeVersionId;
	}

	@Transient
	public java.lang.String getAllCount() {
		return allCount;
	}

	public void setAllCount(java.lang.String allCount) {
		this.allCount = allCount;
	}

	@Transient
	public java.lang.String getCyCount() {
		return cyCount;
	}

	public void setCyCount(java.lang.String cyCount) {
		this.cyCount = cyCount;
	}
	
	@Transient
	public java.lang.String getCycmCount() {
		return cycmCount;
	}

	public void setCycmCount(java.lang.String cycmCount) {
		this.cycmCount = cycmCount;
	}

	@Transient
	public java.lang.String getJcCount() {
		return jcCount;
	}

	public void setJcCount(java.lang.String jcCount) {
		this.jcCount = jcCount;
	}
	
	@Transient
	public String getProjectBreed() {
		return projectBreed;
	}

	public void setProjectBreedList(String projectBreed) {
		this.projectBreed = projectBreed;
	}

	@Transient
	public List<TSType> getLinkInfoList() {
		return linkInfoList;
	}

	public void setLinkInfoList(List<TSType> linkInfoList) {
		this.linkInfoList = linkInfoList;
	}
	@Transient
	public java.lang.String getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(java.lang.String linkInfo) {
		this.linkInfo = linkInfo;
	}

	@Transient
	public java.lang.String getTemplete() {
		return templete;
	}

	public void setTemplete(java.lang.String templete) {
		this.templete = templete;
	}
	
	
	
}
