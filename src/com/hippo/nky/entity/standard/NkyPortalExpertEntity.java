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
 * @Description: 专家委员会
 * @author zhangdaihao
 * @date 2013-08-01 16:40:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_portal_expert", schema = "")
@SuppressWarnings("serial")
public class NkyPortalExpertEntity implements java.io.Serializable {
	/**专家编号*/
	private java.lang.String id;
	/**专家名称*/
	private java.lang.String name;
	/**专家简介*/
	private java.lang.String description;
	/**工作业绩*/
	private java.lang.String achievement;
	/**研究方向*/
	private java.lang.String orientation;
	/**邮箱*/
	private java.lang.String email;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String createby;
	/**单位*/
	private java.lang.String unit;
	/**地址*/
	private java.lang.String address;
	/**手机*/
	private int mobilePhone;
	/**电话*/
	private java.lang.String telephone;
	/**传真*/
	private java.lang.String faxNumber;
	/**职称*/
	private java.lang.String positionaltitle;
	/**邮编*/
	private java.lang.String postcode;
	/**职务*/
	private java.lang.String duty;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNIT",nullable=true,length=100)
	public java.lang.String getUnit() {
		return unit;
	}

	public void setUnit(java.lang.String unit) {
		this.unit = unit;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=200)
	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	/**
	 *方法: 取得int
	 *@return: java.lang.String  手机
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=32)
	public int getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(int mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称
	 */
	@Column(name ="POSITIONALTITLE",nullable=true,length=20)
	public String getPositionaltitle() {
		return positionaltitle;
	}

	public void setpositionaltitle(String positionaltitle) {
		this.positionaltitle = positionaltitle;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编
	 */
	@Column(name ="POSTCODE",nullable=true,length=6)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="TELEPHONE",nullable=true,length=20)
	public java.lang.String getTelephone() {
		return telephone;
	}

	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAXNUMBER",nullable=true,length=20)
	public java.lang.String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(java.lang.String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家编号
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
	 *@param: java.lang.String  专家编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家简介
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=200)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家简介
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作业绩
	 */
	@Column(name ="ACHIEVEMENT",nullable=true,length=200)
	public java.lang.String getAchievement(){
		return this.achievement;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作业绩
	 */
	public void setAchievement(java.lang.String achievement){
		this.achievement = achievement;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究方向
	 */
	@Column(name ="ORIENTATION",nullable=true,length=32)
	public java.lang.String getOrientation(){
		return this.orientation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究方向
	 */
	public void setOrientation(java.lang.String orientation){
		this.orientation = orientation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=32)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
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
	 *@return: java.lang.String  职务
	 */
	@Column(name ="DUTY",nullable=true,length=100)
	public java.lang.String getDuty() {
		return duty;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职务
	 */
	public void setDuty(java.lang.String duty) {
		this.duty = duty;
	}
}
