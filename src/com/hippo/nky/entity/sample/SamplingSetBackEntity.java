package com.hippo.nky.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 抽样信息
 * @author nky
 * @date 2013-11-07 12:55:29
 * @version V1.0
 * 
 */
@Entity
@Table(name = "NKY_SAMPLING_SETBACK", schema = "")
@SuppressWarnings("serial")
public class SamplingSetBackEntity implements java.io.Serializable{
	/** ID */
	private java.lang.String id;
	/** 二维码 */
	private java.lang.String code;
	/** 状态 */
	private java.lang.Integer status;

	
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
	@Column(name = "CODE", nullable = true, length = 50)
	public java.lang.String getCode() {
		return code;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
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

}
