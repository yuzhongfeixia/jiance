package com.hippo.nky.entity.standard;

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
 * @Description: 组织机构
 * @author zhangdaihao
 * @date 2013-08-23 13:07:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_portal_organization", schema = "")
@SuppressWarnings("serial")
public class PortalOrganizationEntity implements java.io.Serializable {
	/**依托单位*/
	private java.lang.String supportinstitution;
	/**性质(专业性、区域性、综合性)*/
	private java.lang.String property;
	/**机构编号*/
	private java.lang.String id;
	/**邮编*/
	private java.lang.String zipcode;
	/**机构名称*/
	private java.lang.String ogrname;
	/**详细地址*/
	private java.lang.String address;
	/**负责人*/
	private java.lang.String leader;
	/**联系人*/
	private java.lang.String contacts;
	/**类型（0-质检中心，1-风险评估实验室）*/
	private java.lang.Integer type;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String createby;
	/**负责人电话*/
	private java.lang.String leadertel;
	/**联系人电话*/
	private java.lang.String contactstel;
	/**传真*/
	private java.lang.String fax;
	/**电子邮箱*/
	private java.lang.String email;
	/**代码*/
	private java.lang.String code;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  依托单位
	 */
	@Column(name ="SUPPORTINSTITUTION",nullable=true,length=100)
	public java.lang.String getSupportinstitution(){
		return this.supportinstitution;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  依托单位
	 */
	public void setSupportinstitution(java.lang.String supportinstitution){
		this.supportinstitution = supportinstitution;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性质(专业性、区域性、综合性)
	 */
	@Column(name ="PROPERTY",nullable=true,length=10)
	public java.lang.String getProperty(){
		return this.property;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性质(专业性、区域性、综合性)
	 */
	public void setProperty(java.lang.String property){
		this.property = property;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构编号
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
	 *@param: java.lang.String  机构编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编
	 */
	@Column(name ="ZIPCODE",nullable=true,length=6)
	public java.lang.String getZipcode(){
		return this.zipcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮编
	 */
	public void setZipcode(java.lang.String zipcode){
		this.zipcode = zipcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构名称
	 */
	@Column(name ="OGRNAME",nullable=true,length=32)
	public java.lang.String getOgrname(){
		return this.ogrname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构名称
	 */
	public void setOgrname(java.lang.String ogrname){
		this.ogrname = ogrname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=2000)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  详细地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="LEADER",nullable=true,length=32)
	public java.lang.String getLeader(){
		return this.leader;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setLeader(java.lang.String leader){
		this.leader = leader;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACTS",nullable=true,length=32)
	public java.lang.String getContacts(){
		return this.contacts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContacts(java.lang.String contacts){
		this.contacts = contacts;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类型（0-质检中心，1-风险评估实验室）
	 */
	@Column(name ="TYPE",nullable=true)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型（0-质检中心，1-风险评估实验室）
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATEDATE",nullable=true,scale=6)
	public java.util.Date getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatedate(java.util.Date createdate){
		this.createdate = createdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATEBY",nullable=true,length=32)
	public java.lang.String getCreateby(){
		return this.createby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateby(java.lang.String createby){
		this.createby = createby;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人电话
	 */
	@Column(name ="LEADERTEL",nullable=true,length=16)
	public java.lang.String getLeadertel(){
		return this.leadertel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人电话
	 */
	public void setLeadertel(java.lang.String leadertel){
		this.leadertel = leadertel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人电话
	 */
	@Column(name ="CONTACTSTEL",nullable=true,length=16)
	public java.lang.String getContactstel(){
		return this.contactstel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人电话
	 */
	public void setContactstel(java.lang.String contactstel){
		this.contactstel = contactstel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=32)
	public java.lang.String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(java.lang.String fax){
		this.fax = fax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=32)
	public java.lang.String getEmail(){
		return this.email;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	
	@Column(name ="CODE",nullable=true,length=32)
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
}
