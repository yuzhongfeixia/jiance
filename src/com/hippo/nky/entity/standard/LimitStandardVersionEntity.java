package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;

/**   
 * @Title: Entity
 * @Description: 限量标准版本
 * @author XuDL
 * @date 2013-08-07 13:58:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_LIMIT_STANDARD_VERSION", schema = "")
@SuppressWarnings("serial")
public class LimitStandardVersionEntity implements java.io.Serializable {
	/**限量标准版本表ID*/
	private java.lang.String id;
	/**标准国别*/
	private java.lang.String standardCountry;
	/**标准类型*/
	private java.lang.String standardType;
	/**标准编码*/
	private java.lang.String standardCode;
	/**中文名称*/
	private java.lang.String nameZh;
	/**英文名称*/
	private java.lang.String nameEn;
	/**发布日期*/
	private java.util.Date publishDate;
	/**实施日期*/
	private java.util.Date implementDate;
	/**发布机构*/
	private java.lang.String publishOrg;
	/**替代标准*/
	private java.lang.String substitute;
	/**作废标准*/
	private java.lang.String invalid;
	/**备注*/
	private java.lang.String describe;
	/**发布标识(0：未发布 1：发布)*/
	private java.lang.Integer publishflag;
	/**停用标志(0：启用 1：停用)*/
	private java.lang.Integer stopflag;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String creater;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限量标准版本表ID
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@PrimaryKeyPolicy(policy = "semi", method = "default")
	@Column(name ="ID",nullable=true,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限量标准版本表ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标准国别
	 */
	@Column(name ="STANDARD_COUNTRY",nullable=true,length=32)
	public java.lang.String getStandardCountry(){
		return this.standardCountry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标准国别
	 */
	public void setStandardCountry(java.lang.String standardCountry){
		this.standardCountry = standardCountry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标准类型
	 */
	@Column(name ="STANDARD_TYPE",nullable=true,length=32)
	public java.lang.String getStandardType(){
		return this.standardType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标准类型
	 */
	public void setStandardType(java.lang.String standardType){
		this.standardType = standardType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标准编码
	 */
	@Column(name ="STANDARD_CODE",nullable=true,length=32)
	public java.lang.String getStandardCode(){
		return this.standardCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标准编码
	 */
	public void setStandardCode(java.lang.String standardCode){
		this.standardCode = standardCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文名称
	 */
	@Column(name ="NAME_ZH",nullable=true,length=300)
	public java.lang.String getNameZh(){
		return this.nameZh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文名称
	 */
	public void setNameZh(java.lang.String nameZh){
		this.nameZh = nameZh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文名称
	 */
	@Column(name ="NAME_EN",nullable=true,length=500)
	public java.lang.String getNameEn(){
		return this.nameEn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文名称
	 */
	public void setNameEn(java.lang.String nameEn){
		this.nameEn = nameEn;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布日期
	 */
	@Column(name ="PUBLISH_DATE",nullable=true)
	public java.util.Date getPublishDate(){
		return this.publishDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布日期
	 */
	public void setPublishDate(java.util.Date publishDate){
		this.publishDate = publishDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  实施日期
	 */
	@Column(name ="IMPLEMENT_DATE",nullable=true)
	public java.util.Date getImplementDate(){
		return this.implementDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  实施日期
	 */
	public void setImplementDate(java.util.Date implementDate){
		this.implementDate = implementDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布机构
	 */
	@Column(name ="PUBLISH_ORG",nullable=true,length=1000)
	public java.lang.String getPublishOrg(){
		return this.publishOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布机构
	 */
	public void setPublishOrg(java.lang.String publishOrg){
		this.publishOrg = publishOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  替代标准
	 */
	@Column(name ="SUBSTITUTE",nullable=true,length=2000)
	public java.lang.String getSubstitute(){
		return this.substitute;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  替代标准
	 */
	public void setSubstitute(java.lang.String substitute){
		this.substitute = substitute;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作废标准
	 */
	@Column(name ="INVALID",nullable=true,length=2000)
	public java.lang.String getInvalid(){
		return this.invalid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作废标准
	 */
	public void setInvalid(java.lang.String invalid){
		this.invalid = invalid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="DESCRIBE",nullable=true,length=2000)
	public java.lang.String getDescribe(){
		return this.describe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setDescribe(java.lang.String describe){
		this.describe = describe;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发布标识(0：未发布 1：发布)
	 */
	@Column(name ="PUBLISHFLAG",nullable=true)
	public java.lang.Integer getPublishflag(){
		return this.publishflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发布标识(0：未发布 1：发布)
	 */
	public void setPublishflag(java.lang.Integer publishflag){
		this.publishflag = publishflag;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  停用标志(0：启用 1：停用)
	 */
	@Column(name ="STOPFLAG",nullable=true)
	public java.lang.Integer getStopflag(){
		return this.stopflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  停用标志(0：启用 1：停用)
	 */
	public void setStopflag(java.lang.Integer stopflag){
		this.stopflag = stopflag;
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
	@Column(name ="CREATER",nullable=true,length=32)
	public java.lang.String getCreater(){
		return this.creater;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreater(java.lang.String creater){
		this.creater = creater;
	}
}
