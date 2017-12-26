package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 污染物基础信息
 * @date 2013-07-02 17:42:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_po_info", schema = "")
@SuppressWarnings("serial")
public class PollInfoEntity implements java.io.Serializable {
	/**污染物ID*/
	private java.lang.String id;
	/**污染物(中文名)*/
	private java.lang.String cname;
	/**污染物(英文名)*/
	private java.lang.String ename;
	/**污染物(拉丁文名)*/
	private java.lang.String latin;
	/**污染物序号*/
	private java.lang.Integer porder;
	/**化学结构*/
	private java.lang.String structure;
	/**分类一*/
	private java.lang.String sort1;
	/**分类二*/
	private java.lang.String sort2;
	/**分类标准编号（污染物）*/
	private java.lang.String sortcode;
	/**CAS码*/
	private java.lang.String cascode;
	/**类别*/
	private java.lang.String category;
	/**用途*/
	private java.lang.String use;
	/**每日允许摄入量*/
	private java.lang.String adi;
	/**残留物*/
	private java.lang.String residue;
	/**中文商品名*/
	private java.lang.String pname;
	/**药物类型*/
	private java.lang.String medtype;
	/**创建时间*/
	private java.util.Date createdate;
	/**分类id*/
	private java.lang.String categoryid;
	/**版本id*/
	private java.lang.String versionid;
	/**描述*/
	private java.lang.String describe;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物ID
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
	 *@param: java.lang.String  污染物ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物(中文名)
	 */
	@Column(name ="CNAME",nullable=true,length=128)
	public java.lang.String getCname(){
		return this.cname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物(中文名)
	 */
	public void setCname(java.lang.String cname){
		this.cname = cname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物(英文名)
	 */
	@Column(name ="ENAME",nullable=true,length=128)
	public java.lang.String getEname(){
		return this.ename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物(英文名)
	 */
	public void setEname(java.lang.String ename){
		this.ename = ename;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  污染物序号
	 */
	@Column(name ="PORDER",nullable=true)
	public java.lang.Integer getPorder(){
		return this.porder;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  污染物序号
	 */
	public void setPorder(java.lang.Integer porder){
		this.porder = porder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  化学结构
	 */
	@Column(name ="STRUCTURE",nullable=true,length=128)
	public java.lang.String getStructure(){
		return this.structure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  化学结构
	 */
	public void setStructure(java.lang.String structure){
		this.structure = structure;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类一
	 */
	@Column(name ="SORT1",nullable=true,length=32)
	public java.lang.String getSort1(){
		return this.sort1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类一
	 */
	public void setSort1(java.lang.String sort1){
		this.sort1 = sort1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类二
	 */
	@Column(name ="SORT2",nullable=true,length=32)
	public java.lang.String getSort2(){
		return this.sort2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类二
	 */
	public void setSort2(java.lang.String sort2){
		this.sort2 = sort2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类标准编号（污染物）
	 */
	@Column(name ="SORTCODE",nullable=true,length=32)
	public java.lang.String getSortcode(){
		return this.sortcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类标准编号（污染物）
	 */
	public void setSortcode(java.lang.String sortcode){
		this.sortcode = sortcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  CAS码
	 */
	@Column(name ="CASCODE",nullable=true,length=32)
	public java.lang.String getCascode(){
		return this.cascode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  CAS码
	 */
	public void setCascode(java.lang.String cascode){
		this.cascode = cascode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别
	 */
	@Column(name ="CATEGORY",nullable=true,length=32)
	public java.lang.String getCategory(){
		return this.category;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别
	 */
	public void setCategory(java.lang.String category){
		this.category = category;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用途
	 */
	@Column(name ="USE",nullable=true,length=128)
	public java.lang.String getUse(){
		return this.use;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用途
	 */
	public void setUse(java.lang.String use){
		this.use = use;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  每日允许摄入量
	 */
	@Column(name ="ADI",nullable=true,length=32)
	public java.lang.String getAdi(){
		return this.adi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  每日允许摄入量
	 */
	public void setAdi(java.lang.String adi){
		this.adi = adi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  残留物
	 */
	@Column(name ="RESIDUE",nullable=true,length=32)
	public java.lang.String getResidue(){
		return this.residue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  残留物
	 */
	public void setResidue(java.lang.String residue){
		this.residue = residue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文商品名
	 */
	@Column(name ="PNAME",nullable=true,length=128)
	public java.lang.String getPname(){
		return this.pname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文商品名
	 */
	public void setPname(java.lang.String pname){
		this.pname = pname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  药物类型
	 */
	@Column(name ="MEDTYPE",nullable=true,length=32)
	public java.lang.String getMedtype(){
		return this.medtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  药物类型
	 */
	public void setMedtype(java.lang.String medtype){
		this.medtype = medtype;
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
	@Column(name ="LATIN",nullable=true,length=128)
	public java.lang.String getLatin() {
		return latin;
	}

	public void setLatin(java.lang.String latin) {
		this.latin = latin;
	}
}
