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
 * @Description: 任务详情
 * @author nky
 * @date 2013-11-15 17:30:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_MONITORING_TASK_DETAILS", schema = "")
@SuppressWarnings("serial")
public class MonitoringTaskDetailsEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**客户端(PAD)*/
	private java.lang.String padId;
	/**分配数量*/
	private java.lang.Integer taskCount;
	/**分配时间*/
	private java.util.Date assignTime;
	/**任务ID*/
	private java.lang.String taskCode;
	/**完成情况*/
	private java.lang.String taskStatus;
	/**发布单位--检索结果*/
	private java.lang.String releaseunit;
	/**截止时间*/
	private java.lang.String endTime;
	
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
	 *@return: java.lang.String  客户端(PAD)
	 */
	@Column(name ="PAD_ID",nullable=true,length=32)
	public java.lang.String getPadId(){
		return this.padId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户端(PAD)
	 */
	public void setPadId(java.lang.String padId){
		this.padId = padId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分配数量
	 */
	@Column(name ="TASK_COUNT",nullable=true)
	public java.lang.Integer getTaskCount(){
		return this.taskCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分配数量
	 */
	public void setTaskCount(java.lang.Integer taskCount){
		this.taskCount = taskCount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  分配时间
	 */
	@Column(name ="ASSIGN_TIME",nullable=true,scale=6)
	public java.util.Date getAssignTime(){
		return this.assignTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  分配时间
	 */
	public void setAssignTime(java.util.Date assignTime){
		this.assignTime = assignTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务ID
	 */
	@Column(name ="TASK_CODE",nullable=true,length=32)
	public java.lang.String getTaskCode(){
		return this.taskCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务ID
	 */
	public void setTaskCode(java.lang.String taskCode){
		this.taskCode = taskCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  完成情况
	 */
	@Column(name ="TASK_STATUS",nullable=true,length=50)
	public java.lang.String getTaskStatus(){
		return this.taskStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  完成情况
	 */
	public void setTaskStatus(java.lang.String taskStatus){
		this.taskStatus = taskStatus;
	}

	@Transient
	public java.lang.String getReleaseunit() {
		return releaseunit;
	}

	public void setReleaseunit(java.lang.String releaseunit) {
		this.releaseunit = releaseunit;
	}

	@Transient
	public java.lang.String getEndTime() {
		return endTime;
	}

	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}
	
	
}
