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
 * @Description: 质检中心
 * @author zhangdaihao
 * @date 2013-08-15 16:43:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_inspection", schema = "")
@SuppressWarnings("serial")
public class InspectionEntity implements java.io.Serializable {
	/**机构编号*/
	private java.lang.String id;
	/**机构名称*/
	private java.lang.String ogrname;
	/**行业*/
	private java.lang.String industry;
	/**详细地址*/
	private java.lang.String address;
	/**负责人*/
	private java.lang.String leader;
	/**联系人*/
	private java.lang.String contacts;
	/**类型*/
	private java.lang.Integer type;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String createby;
	/**邮编*/
	private java.lang.String zipcode;
	/**负责人电话*/
	private java.lang.String leadertel;
	/**联系人电话*/
	private java.lang.String contactstel;
	/**负责人座机*/
	private java.lang.String leaderphone;
	/**传真*/
	private java.lang.String fax;
	/**负责人电子邮箱*/
	private java.lang.String email;
	/**联系人座机*/
	private java.lang.String contactsphone;
	/**省市*/
	private java.lang.String province;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构编号
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=32)
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
	 *@return: java.lang.String  行业
	 */
	@Column(name ="INDUSTRY",nullable=true,length=32)
	public java.lang.String getIndustry(){
		return this.industry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行业
	 */
	public void setIndustry(java.lang.String industry){
		this.industry = industry;
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
	 *@return: java.lang.Integer  类型
	 */
	@Column(name ="TYPE",nullable=true)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型
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
	 *@return: java.lang.String  负责人座机
	 */
	@Column(name ="LEADERPHONE",nullable=true,length=32)
	public java.lang.String getLeaderphone(){
		return this.leaderphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人座机
	 */
	public void setLeaderphone(java.lang.String leaderphone){
		this.leaderphone = leaderphone;
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
	 *@return: java.lang.String  负责人电子邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=32)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人电子邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人座机
	 */
	@Column(name ="CONTACTSPHONE",nullable=true,length=32)
	public java.lang.String getContactsphone(){
		return this.contactsphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人座机
	 */
	public void setContactsphone(java.lang.String contactsphone){
		this.contactsphone = contactsphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省市
	 */
	@Column(name ="PROVINCE",nullable=true,length=32)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省市
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
}
