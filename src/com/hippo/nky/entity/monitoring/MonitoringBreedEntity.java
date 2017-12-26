package com.hippo.nky.entity.monitoring;

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
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 抽样品种数据
 * @author nky
 * @date 2013-10-30 13:50:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_breed", schema = "")
@SuppressWarnings("serial")
public class MonitoringBreedEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**农产品编码*/
	private java.lang.String agrCode;
	/**项目ID*/
	private java.lang.String projectCode;
	
	/**农产品名称（检索结果）*/
	private java.lang.String agrName;
	
	/**父id（检索结果）*/
	private java.lang.String pId;

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
	 *@return: java.lang.String  农产品编码
	 */
	@Column(name ="AGR_CODE",nullable=true,length=32)
	public java.lang.String getAgrCode(){
		return this.agrCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品编码
	 */
	public void setAgrCode(java.lang.String agrCode){
		this.agrCode = agrCode;
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
	
	@Transient
	public java.lang.String getAgrName() {
		return agrName;
	}

	public void setAgrName(java.lang.String agrName) {
		this.agrName = agrName;
	}

	@Transient
	public java.lang.String getpId() {
		return pId;
	}

	public void setpId(java.lang.String pId) {
		this.pId = pId;
	}
	
	
}
