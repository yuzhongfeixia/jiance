package com.hippo.nky.entity.monitoring;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 检测污染物模板数据
 * @author nky
 * @date 2013-10-30 13:50:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_dection_templet", schema = "")
@SuppressWarnings("serial")
public class MonitoringDectionTempletEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**项目id*/
	private java.lang.String projectCode;
	/**农产品编号*/
	private java.lang.String agrCode;
	/**污染物CAS*/
	private java.lang.String pollCas;
	
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
	 *@return: java.lang.String  项目id
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目id
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品编号
	 */
	@Column(name ="AGR_CODE",nullable=true,length=32)
	public java.lang.String getAgrCode(){
		return this.agrCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品编号
	 */
	public void setAgrCode(java.lang.String agrCode){
		this.agrCode = agrCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物CAS
	 */
	@Column(name ="POLL_CAS",nullable=true,length=32)
	public java.lang.String getPollCas(){
		return this.pollCas;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物CAS
	 */
	public void setPollCas(java.lang.String pollCas){
		this.pollCas = pollCas;
	}
}
