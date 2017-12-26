package com.hippo.nky.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 盲样信息
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */
@Entity
@Table(name = "NKY_BLIND_SAMPLE", schema = "")
@SuppressWarnings("serial")
public class BlindSampleEntity implements java.io.Serializable{
	/** ID */
	private java.lang.String id;
	/** 二维码 */
	private java.lang.String sampleCode;
	/** 状态 */
	private java.lang.Integer status;
	/** 检测值 */
	private java.lang.String blindSampleValue;

	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码
	 */
	@Column(name = "SAMPLE_CODE", nullable = true, length = 50)
	public java.lang.String getSampleCode() {
		return sampleCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码
	 */
	public void setSampleCode(java.lang.String sampleCode) {
		this.sampleCode = sampleCode;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 状态
	 */
	@Column(name = "STATUS", nullable = true, length = 50)
	public java.lang.Integer getStatus() {
		return status;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 状态
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	@Column(name = "BLIND_SAMPLE_VALUE", nullable = true, length = 200)
	public java.lang.String getBlindSampleValue() {
		return blindSampleValue;
	}

	public void setBlindSampleValue(java.lang.String blindSampleValue) {
		this.blindSampleValue = blindSampleValue;
	}
	


}
