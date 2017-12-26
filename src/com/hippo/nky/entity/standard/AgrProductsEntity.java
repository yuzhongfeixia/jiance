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
 * @Description: 农产品基础信息
 * @author zhangdaihao
 * @date 2013-07-02 18:11:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_agr_products", schema = "")
@SuppressWarnings("serial")
public class AgrProductsEntity implements java.io.Serializable {
	/**农产品信息ID*/
	private java.lang.String id;
	/**英文名称*/
	private java.lang.String ename;
	/**中文名称*/
	private java.lang.String cname;
	/**拉丁文名称*/
	private java.lang.String latin;
	/**农产品编码*/
	private java.lang.String agrcode;
	/**国际编码*/
	private java.lang.String codex;
	/**FOODEX*/
	private java.lang.String foodex;
	/**创建时间*/
	private java.util.Date createdate;
	/**描述*/
	private java.lang.String describe;
	/**分类id*/
	private java.lang.String categoryid;
	/**版本id*/
	private java.lang.String versionid;
	/**图片路径*/
	private java.lang.String imagepath;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品信息ID
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
	 *@param: java.lang.String  农产品信息ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文名称
	 */
	@Column(name ="ENAME",nullable=true,length=32)
	public java.lang.String getEname(){
		return this.ename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文名称
	 */
	public void setEname(java.lang.String ename){
		this.ename = ename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文名称
	 */
	@Column(name ="CNAME",nullable=true,length=32)
	public java.lang.String getCname(){
		return this.cname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文名称
	 */
	public void setCname(java.lang.String cname){
		this.cname = cname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品编码
	 */
	@Column(name ="AGRCODE",nullable=true,length=32)
	public java.lang.String getAgrcode(){
		return this.agrcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品编码
	 */
	public void setAgrcode(java.lang.String agrcode){
		this.agrcode = agrcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国际编码
	 */
	@Column(name ="CODEX",nullable=true,length=32)
	public java.lang.String getCodex(){
		return this.codex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国际编码
	 */
	public void setCodex(java.lang.String codex){
		this.codex = codex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  FOODEX
	 */
	@Column(name ="FOODEX",nullable=true,length=32)
	public java.lang.String getFoodex(){
		return this.foodex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  FOODEX
	 */
	public void setFoodex(java.lang.String foodex){
		this.foodex = foodex;
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
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIBE",nullable=true,length=1000)
	public java.lang.String getDescribe(){
		return this.describe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescribe(java.lang.String describe){
		this.describe = describe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类id
	 */
	@Column(name ="CATEGORYID",nullable=true,length=32)
	public java.lang.String getCategoryid(){
		return this.categoryid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类id
	 */
	public void setCategoryid(java.lang.String categoryid){
		this.categoryid = categoryid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本id
	 */
	@Column(name ="VERSIONID",nullable=true,length=32)
	public java.lang.String getVersionid(){
		return this.versionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本id
	 */
	public void setVersionid(java.lang.String versionid){
		this.versionid = versionid;
	}
	
	@Column(name ="LATIN",nullable=true,length=32)
	public java.lang.String getLatin() {
		return latin;
	}

	public void setLatin(java.lang.String latin) {
		this.latin = latin;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String 图片路径
	 */
	@Column(name ="IMAGEPATH",nullable=true,length=128)
	public java.lang.String getImagepath() {
		return imagepath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 图片路径
	 */
	public void setImagepath(java.lang.String imagepath) {
		this.imagepath = imagepath;
	}
	
}
