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
 * @Description: 毒理学
 * @author nky
 * @date 2013-11-04 14:39:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_TOXICOLOGY_INFOMATION", schema = "")
@SuppressWarnings("serial")
public class ToxicologyEntity implements java.io.Serializable {
	/**编号*/
	private java.lang.String id;
	/**CAS码*/
	private java.lang.String casCode;
	/**分子式*/
	private java.lang.String formula;
	/**结构式
*/
	private java.lang.String structuralPath;
	/**分子量*/
	private java.math.BigDecimal formulaWeight;
	/**中文通用名
*/
	private java.lang.String commonCname;
	/**英文通用名
*/
	private java.lang.String commonEname;
	/**中文化学名
*/
	private java.lang.String chemicalCname;
	/**英文化学名
*/
	private java.lang.String chemicalEname;
	/**中文商品名
*/
	private java.lang.String tradeCname;
	/**英文商品名
*/
	private java.lang.String tradeEname;
	/**半数致死浓度*/
	private java.lang.String lc50;
	/**半数致死剂量*/
	private java.lang.String ld50;
	/**最大无作用剂量*/
	private java.lang.String noel;
	/**无可见效应作用水平*/
	private java.lang.String noael;
	/**最低可见效应作用水平*/
	private java.lang.String loael;
	/**每日允许摄入量*/
	private java.lang.String adi;
	/**基准剂量*/
	private java.lang.String bmd;
	/**暂定每月耐受摄入量*/
	private java.lang.String ptmi;
	/**暂定每周耐受摄入量*/
	private java.lang.String ptwi;
	/**暂定每日耐受摄入量*/
	private java.lang.String ptdi;
	/**参考剂量*/
	private java.lang.String rfd;
	/**参考浓度*/
	private java.lang.String rfc;
	/**最大耐受剂量*/
	private java.lang.String mtd;
	/**急性参考剂量*/
	private java.lang.String acuteRfd;
	/**是否致癌
*/
	private java.lang.String isCarcinogenic;
	/**是否基因致癌物
*/
	private java.lang.String isGeneCarcinogen;
	/**其他*/
	private java.lang.String others;
	/**性状描述
*/
	private java.lang.String description;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
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
	 *@param: java.lang.String  编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  CAS码
	 */
	@Column(name ="CAS_CODE",nullable=true,length=32)
	public java.lang.String getCasCode(){
		return this.casCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  CAS码
	 */
	public void setCasCode(java.lang.String casCode){
		this.casCode = casCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分子式
	 */
	@Column(name ="FORMULA",nullable=true,length=32)
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
	@Column(name ="STRUCTURAL_PATH",nullable=true,length=64)
	public java.lang.String getStructuralPath(){
		return this.structuralPath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结构式

	 */
	public void setStructuralPath(java.lang.String structuralPath){
		this.structuralPath = structuralPath;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  分子量
	 */
	@Column(name ="FORMULA_WEIGHT",nullable=true,precision=10,scale=3)
	public java.math.BigDecimal getFormulaWeight(){
		return this.formulaWeight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  分子量
	 */
	public void setFormulaWeight(java.math.BigDecimal formulaWeight){
		this.formulaWeight = formulaWeight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文通用名

	 */
	@Column(name ="COMMON_CNAME",nullable=true,length=32)
	public java.lang.String getCommonCname(){
		return this.commonCname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文通用名

	 */
	public void setCommonCname(java.lang.String commonCname){
		this.commonCname = commonCname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文通用名

	 */
	@Column(name ="COMMON_ENAME",nullable=true,length=64)
	public java.lang.String getCommonEname(){
		return this.commonEname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文通用名

	 */
	public void setCommonEname(java.lang.String commonEname){
		this.commonEname = commonEname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文化学名

	 */
	@Column(name ="CHEMICAL_CNAME",nullable=true,length=32)
	public java.lang.String getChemicalCname(){
		return this.chemicalCname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文化学名

	 */
	public void setChemicalCname(java.lang.String chemicalCname){
		this.chemicalCname = chemicalCname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文化学名

	 */
	@Column(name ="CHEMICAL_ENAME",nullable=true,length=64)
	public java.lang.String getChemicalEname(){
		return this.chemicalEname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文化学名

	 */
	public void setChemicalEname(java.lang.String chemicalEname){
		this.chemicalEname = chemicalEname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文商品名

	 */
	@Column(name ="TRADE_CNAME",nullable=true,length=128)
	public java.lang.String getTradeCname(){
		return this.tradeCname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文商品名

	 */
	public void setTradeCname(java.lang.String tradeCname){
		this.tradeCname = tradeCname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文商品名

	 */
	@Column(name ="TRADE_ENAME",nullable=true,length=128)
	public java.lang.String getTradeEname(){
		return this.tradeEname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文商品名

	 */
	public void setTradeEname(java.lang.String tradeEname){
		this.tradeEname = tradeEname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  半数致死浓度
	 */
	@Column(name ="LC50",nullable=true,length=128)
	public java.lang.String getLc50(){
		return this.lc50;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  半数致死浓度
	 */
	public void setLc50(java.lang.String lc50){
		this.lc50 = lc50;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  半数致死剂量
	 */
	@Column(name ="LD50",nullable=true,length=128)
	public java.lang.String getLd50(){
		return this.ld50;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  半数致死剂量
	 */
	public void setLd50(java.lang.String ld50){
		this.ld50 = ld50;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大无作用剂量
	 */
	@Column(name ="NOEL",nullable=true,length=128)
	public java.lang.String getNoel(){
		return this.noel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大无作用剂量
	 */
	public void setNoel(java.lang.String noel){
		this.noel = noel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  无可见效应作用水平
	 */
	@Column(name ="NOAEL",nullable=true,length=128)
	public java.lang.String getNoael(){
		return this.noael;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  无可见效应作用水平
	 */
	public void setNoael(java.lang.String noael){
		this.noael = noael;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最低可见效应作用水平
	 */
	@Column(name ="LOAEL",nullable=true,length=128)
	public java.lang.String getLoael(){
		return this.loael;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最低可见效应作用水平
	 */
	public void setLoael(java.lang.String loael){
		this.loael = loael;
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
	 *@return: java.lang.String  基准剂量
	 */
	@Column(name ="BMD",nullable=true,length=32)
	public java.lang.String getBmd(){
		return this.bmd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基准剂量
	 */
	public void setBmd(java.lang.String bmd){
		this.bmd = bmd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  暂定每月耐受摄入量
	 */
	@Column(name ="PTMI",nullable=true,length=32)
	public java.lang.String getPtmi(){
		return this.ptmi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  暂定每月耐受摄入量
	 */
	public void setPtmi(java.lang.String ptmi){
		this.ptmi = ptmi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  暂定每周耐受摄入量
	 */
	@Column(name ="PTWI",nullable=true,length=32)
	public java.lang.String getPtwi(){
		return this.ptwi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  暂定每周耐受摄入量
	 */
	public void setPtwi(java.lang.String ptwi){
		this.ptwi = ptwi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  暂定每日耐受摄入量
	 */
	@Column(name ="PTDI",nullable=true,length=32)
	public java.lang.String getPtdi(){
		return this.ptdi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  暂定每日耐受摄入量
	 */
	public void setPtdi(java.lang.String ptdi){
		this.ptdi = ptdi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参考剂量
	 */
	@Column(name ="RFD",nullable=true,length=32)
	public java.lang.String getRfd(){
		return this.rfd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参考剂量
	 */
	public void setRfd(java.lang.String rfd){
		this.rfd = rfd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参考浓度
	 */
	@Column(name ="RFC",nullable=true,length=32)
	public java.lang.String getRfc(){
		return this.rfc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参考浓度
	 */
	public void setRfc(java.lang.String rfc){
		this.rfc = rfc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大耐受剂量
	 */
	@Column(name ="MTD",nullable=true,length=32)
	public java.lang.String getMtd(){
		return this.mtd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大耐受剂量
	 */
	public void setMtd(java.lang.String mtd){
		this.mtd = mtd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  急性参考剂量
	 */
	@Column(name ="ACUTE_RFD",nullable=true,length=32)
	public java.lang.String getAcuteRfd(){
		return this.acuteRfd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  急性参考剂量
	 */
	public void setAcuteRfd(java.lang.String acuteRfd){
		this.acuteRfd = acuteRfd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否致癌

	 */
	@Column(name ="IS_CARCINOGENIC",nullable=true,length=1)
	public java.lang.String getIsCarcinogenic(){
		return this.isCarcinogenic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否致癌

	 */
	public void setIsCarcinogenic(java.lang.String isCarcinogenic){
		this.isCarcinogenic = isCarcinogenic;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否基因致癌物

	 */
	@Column(name ="IS_GENE_CARCINOGEN",nullable=true,length=1)
	public java.lang.String getIsGeneCarcinogen(){
		return this.isGeneCarcinogen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否基因致癌物

	 */
	public void setIsGeneCarcinogen(java.lang.String isGeneCarcinogen){
		this.isGeneCarcinogen = isGeneCarcinogen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他
	 */
	@Column(name ="OTHERS",nullable=true,length=128)
	public java.lang.String getOthers(){
		return this.others;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他
	 */
	public void setOthers(java.lang.String others){
		this.others = others;
	}
	/**
	 *方法: 取得java.sql.Clob
	 *@return: java.sql.Clob  性状描述

	 */
	@Column(name ="DESCRIPTION",nullable=true,columnDefinition = "clob")
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 *方法: 设置java.sql.Clob
	 *@param: java.sql.Clob  性状描述

	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
}
