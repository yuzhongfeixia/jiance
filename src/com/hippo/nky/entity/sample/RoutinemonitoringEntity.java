package com.hippo.nky.entity.sample;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.excel.Excel;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 例行监测信息表
 * @author nky
 * @date 2013-11-06 19:12:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_routine_monitoring", schema = "")
@SuppressWarnings("serial")
public class RoutinemonitoringEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品编号*/
	private java.lang.String sampleCode;
	/**样品来源*/
	private java.lang.String sampleSource;
	/**样品量(n/N)*/
	//private java.lang.Integer sampleCount;
	private java.lang.String sampleCount;
	/**任务来源*/
	private java.lang.String taskSource;
	/**执行标准*/
	private java.lang.String execStanderd;
	/**备注*/
	private java.lang.String remark;
	/**任务编码**/
	private java.lang.String taskCode;
	/**条码**/
	private java.lang.String dCode;
	/**农产品编码*/
	private java.lang.String agrCode;
	/**图片内容*/
	private String imgContent;
	/** 抽样详细地址*/
	private java.lang.String samplingAddress;
	/** 抽样单ID*/
	private java.lang.String samplingMonadId;
	/** 样品名称(回显用) */
	private java.lang.String sampleName;
	/** 样品图片(回显用) */
	private java.lang.String samplePath;
	/** 制样编码(回显用) */
	private java.lang.String spCode;
	/**
	 * 样品填报时间
	 */
	private java.lang.String samplingTime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  样品编号
	 */
	@Column(name ="SAMPLE_CODE",nullable=true,length=32)
	public java.lang.String getSampleCode(){
		return this.sampleCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品编号
	 */
	public void setSampleCode(java.lang.String sampleCode){
		this.sampleCode = sampleCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  样品来源
	 */
	@Column(name ="SAMPLE_SOURCE",nullable=true,length=64)
	public java.lang.String getSampleSource(){
		return this.sampleSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品来源
	 */
	public void setSampleSource(java.lang.String sampleSource){
		this.sampleSource = sampleSource;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  样品量(n/N)
	 */
//	@Column(name ="SAMPLE_COUNT",nullable=true)
//	public java.lang.Integer getSampleCount(){
//		return this.sampleCount;
//	}
	@Column(name ="SAMPLE_COUNT",nullable=true,length=50)
	public java.lang.String getSampleCount(){
		return this.sampleCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  样品量(n/N)
	 */
	//public void setSampleCount(java.lang.Integer sampleCount){
	public void setSampleCount(java.lang.String sampleCount){
		this.sampleCount = sampleCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务来源
	 */
	@Column(name ="TASK_SOURCE",nullable=true,length=64)
	public java.lang.String getTaskSource(){
		return this.taskSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务来源
	 */
	public void setTaskSource(java.lang.String taskSource){
		this.taskSource = taskSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行标准
	 */
	@Column(name ="EXEC_STANDERD",nullable=true,length=64)
	public java.lang.String getExecStanderd(){
		return this.execStanderd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行标准
	 */
	public void setExecStanderd(java.lang.String execStanderd){
		this.execStanderd = execStanderd;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Transient
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	
	@Transient
	public java.lang.String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(java.lang.String taskCode) {
		this.taskCode = taskCode;
	}

	@Transient
	public java.lang.String getdCode() {
		return dCode;
	}

	public void setdCode(java.lang.String dCode) {
		this.dCode = dCode;
	}

	@Transient
	public java.lang.String getAgrCode() {
		return agrCode;
	}

	public void setAgrCode(java.lang.String agrCode) {
		this.agrCode = agrCode;
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
	 * @return: java.lang.String 抽样详细地址
	 */
	@Transient
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
	 * @return: java.lang.String 抽样单ID
	 */
	@Column(name = "SAMPLING_MONAD_ID", nullable = true, length = 32)
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
	 * @return: java.lang.String 样品图片
	 */
	@Transient
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
	 * @return: java.lang.String 制样编码
	 */
	@Transient
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
	
	@Transient
	public java.lang.String getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(java.lang.String samplingTime) {
		this.samplingTime = samplingTime;
	}
}
