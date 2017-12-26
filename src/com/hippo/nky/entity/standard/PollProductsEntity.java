package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;
import org.jeecgframework.core.annotation.excel.Excel;

/**   
 * @Title: Entity
 * @Description: 污染物基础信息
 * @author XuDL
 * @date 2013-08-01 11:58:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_POLL_PRODUCTS", schema = "")
@SuppressWarnings("serial")
public class PollProductsEntity implements java.io.Serializable {
	/**污染物ID*/
	private java.lang.String id;
	
	/**版本ID*/
	private java.lang.String categoryid;
	
	/**分类ID*/
	private java.lang.String versionid;
	
	/**CAS码*/
	@Excel(exportName="CAS码", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String cas;
	
	/**英文通用名称*/
	@Excel(exportName="英文通用名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String popename;
	
	/**中文通用名称*/
	@Excel(exportName="中文通用名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String popcname;
	
	/**污染物名称（中文）*/
	@Excel(exportName="污染物名称（中文）", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String cname;
	
	/**污染物名称（英文）*/
	@Excel(exportName="污染物名称（英文）", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String ename;
	
	/**英文化学名称*/
	@Excel(exportName="英文化学名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String cheename;
	
	/**中文化学名称*/
	@Excel(exportName="中文化学名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String checname;
	
	/**分子式*/
	@Excel(exportName="分子式", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String formula;
	
	/**结构式*/
	private java.lang.String structure;
	
	/**分子量*/
	@Excel(exportName="分子量", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String weight;
	
	/**类别*/
	@Excel(exportName="类别", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String category;
	
	/**主要用途*/
	@Excel(exportName="主要用途", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String use;
	
	/**残留物中文名称*/
	@Excel(exportName="残留物中文名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String residuecname;
	
	/**残留物英文名称*/
	@Excel(exportName="残留物英文名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String residueename;
	
	/**每日允许摄入量*/
	@Excel(exportName="每日允许摄入量", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
	private java.math.BigDecimal adi;
	
	/**单位*/
	@Excel(exportName="单位", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
	private java.lang.Integer unit;
	
	/**药物类型*/
	@Excel(exportName="药物类型", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.String type;
	
	/**特征描述*/
	@Excel(exportName="特征描述", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String phydes;
	
	/**是否致癌物*/
	@Excel(exportName="是否致癌物", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private java.lang.Integer iscancer;
	
	/**作用描述*/
	@Excel(exportName="作用描述", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private java.lang.String affectddes;
	
	/** 用于查询污染物时查询污染物分类表的分类名*/
	
	private java.lang.String categoryName;
	/** 用于查询污染物时查询污染物分类表的分类编码*/
	private java.lang.String categoryCode;

	@Transient
	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	@Transient
	public java.lang.String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(java.lang.String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物ID
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
	 *@param: java.lang.String  污染物ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本ID
	 */
	@Column(name ="CATEGORYID",nullable=true,length=32)
	public java.lang.String getCategoryid(){
		return this.categoryid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本ID
	 */
	public void setCategoryid(java.lang.String categoryid){
		this.categoryid = categoryid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */
	@Column(name ="VERSIONID",nullable=true,length=32)
	public java.lang.String getVersionid(){
		return this.versionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
	 */
	public void setVersionid(java.lang.String versionid){
		this.versionid = versionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  CAS码
	 */
	@Column(name ="CAS",nullable=true,length=32)
	public java.lang.String getCas(){
		return this.cas;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  CAS码
	 */
	public void setCas(java.lang.String cas){
		this.cas = cas;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文通用名称
	 */
	@Column(name ="POPENAME",nullable=true,length=128)
	public java.lang.String getPopename(){
		return this.popename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文通用名称
	 */
	public void setPopename(java.lang.String popename){
		this.popename = popename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文通用名称
	 */
	@Column(name ="POPCNAME",nullable=true,length=128)
	public java.lang.String getPopcname(){
		return this.popcname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文通用名称
	 */
	public void setPopcname(java.lang.String popcname){
		this.popcname = popcname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物名称（中文）
	 */
	@Column(name ="CNAME",nullable=true,length=128)
	public java.lang.String getCname(){
		return this.cname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物名称（中文）
	 */
	public void setCname(java.lang.String cname){
		this.cname = cname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物名称（英文）
	 */
	@Column(name ="ENAME",nullable=true,length=128)
	public java.lang.String getEname(){
		return this.ename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物名称（英文）
	 */
	public void setEname(java.lang.String ename){
		this.ename = ename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文化学名称
	 */
	@Column(name ="CHEENAME",nullable=true,length=128)
	public java.lang.String getCheename(){
		return this.cheename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文化学名称
	 */
	public void setCheename(java.lang.String cheename){
		this.cheename = cheename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文化学名称
	 */
	@Column(name ="CHECNAME",nullable=true,length=128)
	public java.lang.String getChecname(){
		return this.checname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文化学名称
	 */
	public void setChecname(java.lang.String checname){
		this.checname = checname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分子式
	 */
	@Column(name ="FORMULA",nullable=true,length=128)
	public java.lang.String getFormula(){
		return this.formula;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分子式
	 */
	public void setFormula(java.lang.String formula){
		this.formula = formula;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结构式
	 */
	@Column(name ="STRUCTURE",nullable=true,length=128)
	public java.lang.String getStructure(){
		return this.structure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结构式
	 */
	public void setStructure(java.lang.String structure){
		this.structure = structure;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分子量
	 */
	@Column(name ="WEIGHT",nullable=true,length=128)
	public java.lang.String getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分子量
	 */
	public void setWeight(java.lang.String weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别
	 */
	@Column(name ="CATEGORY",nullable=true,length=128)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  残留物中文名称
	 */
	@Column(name ="RESIDUECNAME",nullable=true,length=128)
	public java.lang.String getResiduecname(){
		return this.residuecname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  残留物中文名称
	 */
	public void setResiduecname(java.lang.String residuecname){
		this.residuecname = residuecname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  残留物英文名称
	 */
	@Column(name ="RESIDUEENAME",nullable=true,length=128)
	public java.lang.String getResidueename(){
		return this.residueename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  残留物英文名称
	 */
	public void setResidueename(java.lang.String residueename){
		this.residueename = residueename;
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
	 *@return: java.lang.String  药物类型
	 */
	@Column(name ="TYPE",nullable=true,length=128)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  药物类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  特征描述
	 */
	@Column(name ="PHYDES",nullable=true,length=4000)
	public java.lang.String getPhydes(){
		return this.phydes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  特征描述
	 */
	public void setPhydes(java.lang.String phydes){
		this.phydes = phydes;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否致癌物
	 */
	@Column(name ="ISCANCER",nullable=true,precision=1,scale=0)
	public java.lang.Integer getIscancer(){
		return this.iscancer;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否致癌物
	 */
	public void setIscancer(java.lang.Integer iscancer){
		this.iscancer = iscancer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作用描述
	 */
	@Column(name ="AFFECTDDES",nullable=true,length=4000)
	public java.lang.String getAffectddes(){
		return this.affectddes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作用描述
	 */
	public void setAffectddes(java.lang.String affectddes){
		this.affectddes = affectddes;
	}
}
