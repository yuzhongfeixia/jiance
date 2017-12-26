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
 * @Description: 监测点
 * @author nky
 * @date 2013-10-18 16:33:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_MONITORING_SITE", schema = "")
@SuppressWarnings("serial")
public class NkyMonitoringSiteEntity implements java.io.Serializable {
	/**监测点ID*/
	private java.lang.String id;
	/**监测点代码*/
	private java.lang.String code;
	/**监测点名称*/
	private java.lang.String name;
	/**法定代表人或负责人*/
	private java.lang.String legalPerson;
	/**邮编*/
	private java.lang.String zipcode;
	/**详细地址*/
	private java.lang.String address;
	/**联系电话*/
	private java.lang.String contact;
	/**所属区域*/
	private java.lang.String areacode;
	/**所属区县*/
	private java.lang.String areacode2;
	/**监测环节*/
	private java.lang.String monitoringLink;
	/**企业性质*/
	private java.lang.String enterprise;
	/**企业规模*/
	private java.lang.String scale;
	/**主管单位*/
	private java.lang.String unit;
	/** 经度地理坐标 */
	private java.lang.String longitude;
	/** 纬度地理坐标 */
	private java.lang.String latitude;
	/**联系人*/
	private java.lang.String contactPerson;
	/**传真*/
	private java.lang.String fax;
	/**乡镇/街道*/
	private java.lang.String townandstreet;
	/**规模类型*/
	private java.lang.String scaletype;
	/**规模单位*/
	private java.lang.String scaleunit;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测点ID
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
	 *@param: java.lang.String  监测点ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测点代码
	 */
	@Column(name ="CODE",nullable=true,length=32)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测点代码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测点名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测点名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人或负责人
	 */
	@Column(name ="LEGAL_PERSON",nullable=true,length=32)
	public java.lang.String getLegalPerson(){
		return this.legalPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人或负责人
	 */
	public void setLegalPerson(java.lang.String legalPerson){
		this.legalPerson = legalPerson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编
	 */
	@Column(name ="ZIPCODE",nullable=true,length=32)
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
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
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
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="CONTACT",nullable=true,length=15)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属区域
	 */
	@Column(name ="AREACODE",nullable=true,length=6)
	public java.lang.String getAreacode(){
		return this.areacode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属区域
	 */
	public void setAreacode(java.lang.String areacode){
		this.areacode = areacode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属区县
	 */
	@Column(name ="AREACODE2",nullable=true,length=6)
	public java.lang.String getAreacode2() {
		return areacode2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属区县
	 */
	public void setAreacode2(java.lang.String areacode2) {
		this.areacode2 = areacode2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监测环节
	 */
	@Column(name ="MONITORING_LINK",nullable=true,length=1)
	public java.lang.String getMonitoringLink(){
		return this.monitoringLink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监测环节
	 */
	public void setMonitoringLink(java.lang.String monitoringLink){
		this.monitoringLink = monitoringLink;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业性质
	 */
	@Column(name ="ENTERPRISE",nullable=true,length=1)
	public java.lang.String getEnterprise(){
		return this.enterprise;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业性质
	 */
	public void setEnterprise(java.lang.String enterprise){
		this.enterprise = enterprise;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业规模
	 */
	@Column(name ="SCALE",nullable=true,length=1)
	public java.lang.String getScale(){
		return this.scale;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业规模
	 */
	public void setScale(java.lang.String scale){
		this.scale = scale;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主管单位
	 */
	@Column(name ="UNIT",nullable=true,length=128)
	public java.lang.String getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主管单位
	 */
	public void setUnit(java.lang.String unit){
		this.unit = unit;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 经度地理坐标
	 */
	@Column(name = "LONGITUDE", nullable = true, length = 32)
	public java.lang.String getLongitude() {
		return this.longitude;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 经度地理坐标
	 */
	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 纬度地理坐标
	 */
	@Column(name = "LATITUDE", nullable = true, length = 32)
	public java.lang.String getLatitude() {
		return this.latitude;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 纬度地理坐标
	 */
	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT_PERSON",nullable=true,length=32)
	public java.lang.String getContactPerson(){
		return this.contactPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContactPerson(java.lang.String contactPerson){
		this.contactPerson = contactPerson;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=15)
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

	@Column(name ="TOWNANDSTREET",nullable=true,length=500)
	public java.lang.String getTownandstreet() {
		return townandstreet;
	}

	public void setTownandstreet(java.lang.String townandstreet) {
		this.townandstreet = townandstreet;
	}

	@Column(name ="SCALE_TYPE",nullable=true,length=10)
	public java.lang.String getScaletype() {
		return scaletype;
	}

	public void setScaletype(java.lang.String scaletype) {
		this.scaletype = scaletype;
	}

	@Column(name ="SCALE_UNIT",nullable=true,length=10)
	public java.lang.String getScaleunit() {
		return scaleunit;
	}

	public void setScaleunit(java.lang.String scaleunit) {
		this.scaleunit = scaleunit;
	}
	
	

}
