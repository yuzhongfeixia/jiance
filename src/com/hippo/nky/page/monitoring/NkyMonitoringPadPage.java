package com.hippo.nky.page.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;

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
public class NkyMonitoringPadPage implements java.io.Serializable {
	/**客户端ID*/
	private java.lang.String id;
	/**用户名*/
	private java.lang.String username;
	/**密码*/
	private java.lang.String password;
	/**所属质检机构*/
	private java.lang.String orgid;
	/**备注*/
	private java.lang.String remark;
	
	private OrganizationEntity organizationEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgid")
	public OrganizationEntity getOrganizationEntity() {
		return this.organizationEntity;
	}

	public void setOrganizationEntity(OrganizationEntity organizationEntity) {
		this.organizationEntity = organizationEntity;
	}

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
	@Column(name ="ORGID",nullable=true,length=32)
	public java.lang.String getOrgid(){
		return this.orgid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属质检机构
	 */
	public void setOrgid(java.lang.String orgid){
		this.orgid = orgid;
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
}
