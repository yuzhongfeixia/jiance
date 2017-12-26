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

import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringAttachmentEntity;

/**   
 * @Title: Entity
 * @Description: 检测方案数据
 * @author nky
 * @date 2013-10-23 15:54:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_MONITORING_PLAN", schema = "")
@SuppressWarnings("serial")
public class MonitoringPlanPage implements java.io.Serializable {
	/**保存-检测项目数据*/
	private List<MonitoringProjectEntity> monitoringProjectList = new ArrayList<MonitoringProjectEntity>();
	public List<MonitoringProjectEntity> getMonitoringProjectList() {
		return monitoringProjectList;
	}
	public void setMonitoringProjectList(List<MonitoringProjectEntity> monitoringProjectList) {
		this.monitoringProjectList = monitoringProjectList;
	}
	/**保存-方案附件*/
	private List<MonitoringAttachmentEntity> monitoringAttachmentList = new ArrayList<MonitoringAttachmentEntity>();
	public List<MonitoringAttachmentEntity> getMonitoringAttachmentList() {
		return monitoringAttachmentList;
	}
	public void setMonitoringAttachmentList(List<MonitoringAttachmentEntity> monitoringAttachmentList) {
		this.monitoringAttachmentList = monitoringAttachmentList;
	}


	/**ID*/
	private java.lang.String id;
	/**方案名称*/
	private java.lang.String name;
	/**监测类型*/
	private java.lang.String type;
	/**方案级别*/
	private java.lang.String plevel;
	/**方案状态*/
	private java.lang.String state;
	/**发布单位*/
	private java.lang.String releaseunit;
	/**发布时间*/
	private java.util.Date releasetime;
	/**方案ID*/
	private java.lang.String planCode;
	
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
	 *@return: java.lang.String  方案名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  方案名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测类型
	 */
	@Column(name ="TYPE",nullable=true,length=1)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  方案级别
	 */
	@Column(name ="PLEVEL",nullable=true,length=1)
	public java.lang.String getPlevel(){
		return this.plevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  方案级别
	 */
	public void setPlevel(java.lang.String plevel){
		this.plevel = plevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  方案状态
	 */
	@Column(name ="STATE",nullable=true,length=1)
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  方案状态
	 */
	public void setState(java.lang.String state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布单位
	 */
	@Column(name ="RELEASEUNIT",nullable=true,length=32)
	public java.lang.String getReleaseunit(){
		return this.releaseunit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布单位
	 */
	public void setReleaseunit(java.lang.String releaseunit){
		this.releaseunit = releaseunit;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布时间
	 */
	@Column(name ="RELEASETIME",nullable=true,scale=6)
	public java.util.Date getReleasetime(){
		return this.releasetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布时间
	 */
	public void setReleasetime(java.util.Date releasetime){
		this.releasetime = releasetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  方案ID
	 */
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
}
