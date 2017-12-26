package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;
import org.jeecgframework.core.annotation.excel.Excel;

/**   
 * @Title: Entity
 * @Description: 限量标准
 * @author XuDL
 * @date 2013-08-07 16:42:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_LIMIT_STANDARD", schema = "")
@SuppressWarnings("serial")
public class LimitStandardEntity implements java.io.Serializable {
	/**限量标准ID*/
	private java.lang.String id;
	/**限量版本ID*/
	private java.lang.String versionid;
	
	/**污染物CAS码*/
	@Excel(exportName="污染物CAS码", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String cas;
	
	/**污染物(中文名)*/
	@Excel(exportName="污染物（中文名）", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String pollnameZh;
	
	/**污染物(英文名)*/
	@Excel(exportName="污染物（英文名）", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String pollnameEn;
	
	/**农产品类别*/
	@Excel(exportName="农产品类别", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String agrcategory;
	
	/**农产品名称*/
	@Excel(exportName="农产品名称", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String agrname;
	
	/**主要用途*/
	@Excel(exportName="主要用途", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String use;
	
	/**每日允许摄入量*/
	@Excel(exportName="每日允许摄入量", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
	private java.math.BigDecimal adi;
	
	/**残留物*/
	@Excel(exportName="残留物", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String residue;
	
	/**最大残留限量*/
	@Excel(exportName="最大残留限量", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
	private java.math.BigDecimal mrl;
	
	/**单位*/
	@Excel(exportName="单位", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
	private java.lang.Integer unit;
	
	/**检测方法*/
	@Excel(exportName="检测方法", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String method;
	
	/**备注*/
	@Excel(exportName="备注", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String describe;
	
	/**创建时间*/
	private java.util.Date createdate;
	
	/**创建人*/
	private java.lang.String creater;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限量标准ID
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@PrimaryKeyPolicy(policy = "semi", method = "default")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限量标准ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限量版本ID
	 */
	@Column(name ="VERSIONID",nullable=false,length=32)
	public java.lang.String getVersionid(){
		return this.versionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限量版本ID
	 */
	public void setVersionid(java.lang.String versionid){
		this.versionid = versionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物CAS码
	 */
	@Column(name ="CAS",nullable=false,length=32)
	public java.lang.String getCas(){
		return this.cas;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物CAS码
	 */
	public void setCas(java.lang.String cas){
		this.cas = cas;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物(中文名)
	 */
	@Column(name ="POLLNAME_ZH",nullable=true,length=300)
	public java.lang.String getPollnameZh(){
		return this.pollnameZh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物(中文名)
	 */
	public void setPollnameZh(java.lang.String pollnameZh){
		this.pollnameZh = pollnameZh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物(英文名)
	 */
	@Column(name ="POLLNAME_EN",nullable=true,length=500)
	public java.lang.String getPollnameEn(){
		return this.pollnameEn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物(英文名)
	 */
	public void setPollnameEn(java.lang.String pollnameEn){
		this.pollnameEn = pollnameEn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品类别
	 */
	@Column(name ="AGRCATEGORY",nullable=true,length=32)
	public java.lang.String getAgrcategory(){
		return this.agrcategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品类别
	 */
	public void setAgrcategory(java.lang.String agrcategory){
		this.agrcategory = agrcategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品名称
	 */
	@Column(name ="AGRNAME",nullable=true,length=128)
	public java.lang.String getAgrname(){
		return this.agrname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品名称
	 */
	public void setAgrname(java.lang.String agrname){
		this.agrname = agrname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要用途
	 */
	@Column(name ="USE",nullable=true,length=128)
	public java.lang.String getUse(){
		return this.use;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要用途
	 */
	public void setUse(java.lang.String use){
		this.use = use;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  每日允许摄入量
	 */
	@Column(name ="ADI",nullable=true,precision=10,scale=6)
	public java.math.BigDecimal getAdi(){
		return this.adi;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  每日允许摄入量
	 */
	public void setAdi(java.math.BigDecimal adi){
		this.adi = adi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  残留物
	 */
	@Column(name ="RESIDUE",nullable=true,length=128)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  最大残留限量
	 */
	@Column(name ="MRL",nullable=true,precision=10,scale=6)
	public java.math.BigDecimal getMrl(){
		return this.mrl;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  最大残留限量
	 */
	public void setMrl(java.math.BigDecimal mrl){
		this.mrl = mrl;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单位
	 */
	@Column(name ="UNIT",nullable=true,precision=1,scale=0)
	public java.lang.Integer getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单位
	 */
	public void setUnit(java.lang.Integer unit){
		this.unit = unit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检测方法
	 */
	@Column(name ="METHOD",nullable=true,length=1000)
	public java.lang.String getMethod(){
		return this.method;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检测方法
	 */
	public void setMethod(java.lang.String method){
		this.method = method;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="DESCRIBE",nullable=true,length=1000)
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
