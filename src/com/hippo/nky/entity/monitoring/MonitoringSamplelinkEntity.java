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
 * @Description: 检测环节数据
 * @author nky
 * @date 2013-10-30 13:50:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_samplelink", schema = "")
@SuppressWarnings("serial")
public class MonitoringSamplelinkEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**项目编号*/
	private java.lang.String projectCode;
	/**抽样环节*/
	private java.lang.String monitoringLink;
	
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
	 *@return: java.lang.String  抽样环节
	 */
	@Column(name ="MONITORING_LINK",nullable=true,length=50)
	public java.lang.String getMonitoringLink(){
		return this.monitoringLink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样环节
	 */
	public void setMonitoringLink(java.lang.String monitoringLink){
		this.monitoringLink = monitoringLink;
	}
}
