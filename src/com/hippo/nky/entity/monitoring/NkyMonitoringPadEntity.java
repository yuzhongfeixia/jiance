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
 * @Description: 客户端(PAD)
 * @date 2013-10-18 16:28:48
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_MONITORING_PAD", schema = "")
@SuppressWarnings("serial")
public class NkyMonitoringPadEntity implements java.io.Serializable {
	/**客户端ID*/
	private java.lang.String id;
	/**用户名*/
	private java.lang.String username;
	/**密码*/
	private java.lang.String password;
	/**所属质检机构*/
	private java.lang.String orgCode;
	/**备注*/
	private java.lang.String remark;
	
	/**质检机构名 --检索条件*/
	private java.lang.String orgName;
	
	/** 任务数量 --检索结果*/
	private java.lang.Integer taskCount;
	
	/**登录成功flg**/
	private java.lang.String flg;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户端ID
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
	 *@param: java.lang.String  客户端ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USERNAME",nullable=true,length=32)
	public java.lang.String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUsername(java.lang.String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密码
	 */
	@Column(name ="PASSWORD",nullable=true,length=32)
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密码
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属质检机构
	 */
	@Column(name ="org_code",nullable=true,length=32)
	public java.lang.String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属质检机构
	 */
	public void setOrgCode(java.lang.String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
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
	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}

	@Transient
	public java.lang.Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(java.lang.Integer taskCount) {
		this.taskCount = taskCount;
	}

	@Transient
	public java.lang.String getFlg() {
		return flg;
	}

	public void setFlg(java.lang.String flg) {
		this.flg = flg;
	}
}
