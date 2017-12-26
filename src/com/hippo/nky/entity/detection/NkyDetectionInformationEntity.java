package com.hippo.nky.entity.detection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * 样品检测信息实体类
 * 
 * @date 2013-11-16 15:18:58
 * @version V1.0   
 * @author xudl
 */
@Entity
@Table(name = "NKY_DETECTION_INFORMATION", schema = "")
@SuppressWarnings("serial")
public class NkyDetectionInformationEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品ID*/
	private java.lang.String sampleCode;
	/**污染物名(画面表示用)*/
	private java.lang.String pollName;
	/**污染物CAS码*/
	private java.lang.String casCode;
	/**检出值*/
	private java.math.BigDecimal detectionValue;
	/**判断标准值*/
	private java.math.BigDecimal judgeValue;
	/**样品检测是否超标*/
	private java.lang.String isOverproof;
	/**实验室编码(画面表示用)*/
	private java.lang.String labCode;
	/**样品名(农产品名)*/
	private java.lang.String sampleName;
	
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
	 *@return: java.lang.String  样品ID
	 */
	@Column(name ="SAMPLE_CODE",nullable=true,length=32)
	public java.lang.String getSampleCode(){
		return this.sampleCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品ID
	 */
	public void setSampleCode(java.lang.String sampleCode){
		this.sampleCode = sampleCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物CAS码
	 */
	@Column(name ="CAS_CODE",nullable=true,length=32)
	public java.lang.String getCasCode(){
		return this.casCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物CAS码
	 */
	public void setCasCode(java.lang.String casCode){
		this.casCode = casCode;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  检出值
	 */
	@Column(name ="DETECTION_VALUE",nullable=true,precision=10,scale=6)
	public java.math.BigDecimal getDetectionValue(){
		return this.detectionValue;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  检出值
	 */
	public void setDetectionValue(java.math.BigDecimal detectionValue){
		this.detectionValue = detectionValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  样品检测是否超标
	 */
	@Column(name ="IS_OVERPROOF",nullable=true,length=50)
	public java.lang.String getIsOverproof(){
		return this.isOverproof;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品检测是否超标
	 */
	public void setIsOverproof(java.lang.String isOverproof){
		this.isOverproof = isOverproof;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物名称
	 */
	@Transient
	public java.lang.String getPollName() {
		return pollName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物名称
	 */
	public void setPollName(java.lang.String pollName) {
		this.pollName = pollName;
	}

	/**
	 * 取得实验室编码(画面表示用)
	 * 
	 * @return 实验室编码(画面表示用)
	 */
	@Transient
	public java.lang.String getLabCode() {
		return labCode;
	}

	/**
	 * 设置实验室编码(画面表示用)
	 * 
	 * @param labCode 实验室编码(画面表示用)
	 */
	public void setLabCode(java.lang.String labCode) {
		this.labCode = labCode;
	}

	/**
	 * 取得样品名(农产品名)
	 * 
	 * @return 样品名(农产品名)
	 */
	@Transient
	public java.lang.String getSampleName() {
		return sampleName;
	}

	/**
	 * 设置样品名(农产品名)
	 * 
	 * @param sampleName样品名(农产品名)
	 */
	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}

	/**
	 * 判断标准值
	 * 
	 * @return
	 */
	@Transient
	public java.math.BigDecimal getJudgeValue() {
		return judgeValue;
	}

	/**
	 * 判断标准值
	 * 
	 * @param judgeValue
	 */
	public void setJudgeValue(java.math.BigDecimal judgeValue) {
		this.judgeValue = judgeValue;
	}
	
}
