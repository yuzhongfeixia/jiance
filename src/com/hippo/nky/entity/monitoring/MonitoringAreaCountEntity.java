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
 * @Description: 监测地区及数量数据
 * @author nky
 * @date 2013-10-30 13:50:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_monitoring_area_count", schema = "")
@SuppressWarnings("serial")
public class MonitoringAreaCountEntity implements java.io.Serializable {
	/**编号*/
	private java.lang.String id;
	/**监测项目编号*/
	private java.lang.String projectCode;
	/**抽检市编码*/
	private java.lang.String citycode;
	/**抽检区县编码*/
	private java.lang.String districtcode;
	/**抽样数量*/
	private java.lang.Integer count;
	
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
	 *@return: java.lang.String  监测项目编号
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测项目编号
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽检市编码
	 */
	@Column(name ="CITYCODE",nullable=true,length=6)
	public java.lang.String getCitycode(){
		return this.citycode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽检市编码
	 */
	public void setCitycode(java.lang.String citycode){
		this.citycode = citycode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽检区县编码
	 */
	@Column(name ="DISTRICTCODE",nullable=true,length=6)
	public java.lang.String getDistrictcode(){
		return this.districtcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽检区县编码
	 */
	public void setDistrictcode(java.lang.String districtcode){
		this.districtcode = districtcode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  抽样数量
	 */
	@Column(name ="COUNT",nullable=true)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  抽样数量
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
}
