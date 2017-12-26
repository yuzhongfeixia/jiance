package com.hippo.nky.entity.sample;

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
 * @Description: 普查样品信息
 * @author nky
 * @date 2013-11-08 16:27:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_general_check", schema = "")
@SuppressWarnings("serial")
public class GeneralcheckEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品编号*/
	private java.lang.String sampleCode;
	/**商标*/
	private java.lang.String tradeMark;
	/**包装*/
	private java.lang.String pack;
	/**规格*/
	private java.lang.String specifications;
	/**标识*/
	private java.lang.String flag;
	/**执行标准*/
	private java.lang.String execStandard;
	/**生产日期或批号*/
	private java.lang.String batchNumber;
	/**产品认证情况*/
	private java.lang.String productCer;
	/**证书编号*/
	private java.lang.String productCerNo;
	/**抽样数量*/
	//private java.lang.Integer samplingCount;
	private java.lang.String samplingCount;
	/**受检人与摊位号*/
	private java.lang.String stall;
	/**电话*/
	private java.lang.String telphone;
	/**传真*/
	private java.lang.String fax;
	/**单位性质(生产)*/
	private java.lang.String unitProperties;
	/**单位名称(生产)*/
	private java.lang.String unitName;
	/**通讯地址(生产)*/
	private java.lang.String unitAddress;
	/**邮编(生产)*/
	private java.lang.String zipCode;
	/**法定代表人(生产)*/
	private java.lang.String legalPerson;
	/**联系人(生产)*/
	private java.lang.String contacts;
	/**电话(生产)*/
	private java.lang.String telphone2;
	/**传真(生产)*/
	private java.lang.String fax2;
	/**抽样文件编号*/
	private java.lang.String samplingNo;
	/**等级*/
	private java.lang.String grade;
	/**抽样基数*/
	private java.lang.String samplingCardinal;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  样品编号
	 */
	@Column(name ="SAMPLE_CODE",nullable=true,length=32)
	public java.lang.String getSampleCode(){
		return this.sampleCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品编号
	 */
	public void setSampleCode(java.lang.String sampleCode){
		this.sampleCode = sampleCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商标
	 */
	@Column(name ="TRADE_MARK",nullable=true,length=32)
	public java.lang.String getTradeMark(){
		return this.tradeMark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商标
	 */
	public void setTradeMark(java.lang.String tradeMark){
		this.tradeMark = tradeMark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包装
	 */
	@Column(name ="PACK",nullable=true,length=50)
	public java.lang.String getPack(){
		return this.pack;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包装
	 */
	public void setPack(java.lang.String pack){
		this.pack = pack;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */
	@Column(name ="SPECIFICATIONS",nullable=true,length=32)
	public java.lang.String getSpecifications(){
		return this.specifications;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setSpecifications(java.lang.String specifications){
		this.specifications = specifications;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标识
	 */
	@Column(name ="FLAG",nullable=true,length=50)
	public java.lang.String getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标识
	 */
	public void setFlag(java.lang.String flag){
		this.flag = flag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行标准
	 */
	@Column(name ="EXEC_STANDARD",nullable=true,length=128)
	public java.lang.String getExecStandard(){
		return this.execStandard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行标准
	 */
	public void setExecStandard(java.lang.String execStandard){
		this.execStandard = execStandard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生产日期或批号
	 */
	@Column(name ="BATCH_NUMBER",nullable=true,length=128)
	public java.lang.String getBatchNumber(){
		return this.batchNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期或批号
	 */
	public void setBatchNumber(java.lang.String batchNumber){
		this.batchNumber = batchNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品认证情况
	 */
	@Column(name ="PRODUCT_CER",nullable=true,length=50)
	public java.lang.String getProductCer(){
		return this.productCer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品认证情况
	 */
	public void setProductCer(java.lang.String productCer){
		this.productCer = productCer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  证书编号
	 */
	@Column(name ="PRODUCT_CER_NO",nullable=true,length=128)
	public java.lang.String getProductCerNo(){
		return this.productCerNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  证书编号
	 */
	public void setProductCerNo(java.lang.String productCerNo){
		this.productCerNo = productCerNo;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  抽样数量
	 */
//	@Column(name ="SAMPLING_COUNT",nullable=true)
//	public java.lang.Integer getSamplingCount(){
//		return this.samplingCount;
//	}
	@Column(name ="SAMPLING_COUNT",nullable=true,length=50)
	public java.lang.String getSamplingCount(){
		return this.samplingCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  抽样数量
	 */
	//public void setSamplingCount(java.lang.Integer samplingCount){
	public void setSamplingCount(java.lang.String samplingCount){
		this.samplingCount = samplingCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受检人与摊位号
	 */
	@Column(name ="STALL",nullable=true,length=128)
	public java.lang.String getStall(){
		return this.stall;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受检人与摊位号
	 */
	public void setStall(java.lang.String stall){
		this.stall = stall;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="TELPHONE",nullable=true,length=16)
	public java.lang.String getTelphone(){
		return this.telphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setTelphone(java.lang.String telphone){
		this.telphone = telphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=16)
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
	 *@return: java.lang.String  单位性质(生产)
	 */
	@Column(name ="UNIT_PROPERTIES",nullable=true,length=16)
	public java.lang.String getUnitProperties(){
		return this.unitProperties;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位性质(生产)
	 */
	public void setUnitProperties(java.lang.String unitProperties){
		this.unitProperties = unitProperties;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位名称(生产)
	 */
	@Column(name ="UNIT_NAME",nullable=true,length=128)
	public java.lang.String getUnitName(){
		return this.unitName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名称(生产)
	 */
	public void setUnitName(java.lang.String unitName){
		this.unitName = unitName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通讯地址(生产)
	 */
	@Column(name ="UNIT_ADDRESS",nullable=true,length=128)
	public java.lang.String getUnitAddress(){
		return this.unitAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通讯地址(生产)
	 */
	public void setUnitAddress(java.lang.String unitAddress){
		this.unitAddress = unitAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编(生产)
	 */
	@Column(name ="ZIP_CODE",nullable=true,length=6)
	public java.lang.String getZipCode(){
		return this.zipCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮编(生产)
	 */
	public void setZipCode(java.lang.String zipCode){
		this.zipCode = zipCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人(生产)
	 */
	@Column(name ="LEGAL_PERSON",nullable=true,length=16)
	public java.lang.String getLegalPerson(){
		return this.legalPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人(生产)
	 */
	public void setLegalPerson(java.lang.String legalPerson){
		this.legalPerson = legalPerson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人(生产)
	 */
	@Column(name ="CONTACTS",nullable=true,length=16)
	public java.lang.String getContacts(){
		return this.contacts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人(生产)
	 */
	public void setContacts(java.lang.String contacts){
		this.contacts = contacts;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话(生产)
	 */
	@Column(name ="TELPHONE2",nullable=true,length=16)
	public java.lang.String getTelphone2(){
		return this.telphone2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话(生产)
	 */
	public void setTelphone2(java.lang.String telphone2){
		this.telphone2 = telphone2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真(生产)
	 */
	@Column(name ="FAX2",nullable=true,length=16)
	public java.lang.String getFax2(){
		return this.fax2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真(生产)
	 */
	public void setFax2(java.lang.String fax2){
		this.fax2 = fax2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽样文件编号
	 */
	@Column(name ="SAMPLING_NO",nullable=true,length=32)
	public java.lang.String getSamplingNo(){
		return this.samplingNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样文件编号
	 */
	public void setSamplingNo(java.lang.String samplingNo){
		this.samplingNo = samplingNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String 等级
	 */
	@Column(name ="GRADE",nullable=true,length=32)
	public java.lang.String getGrade(){
		return this.grade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 等级
	 */
	public void setGrade(java.lang.String grade){
		this.grade = grade;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽样基数
	 */
	@Column(name ="SAMPLING_CARDINAL",nullable=true,length=50)
	public java.lang.String getSamplingCardinal(){
		return this.samplingCardinal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样基数
	 */
	public void setSamplingCardinal(java.lang.String samplingCardinal){
		this.samplingCardinal = samplingCardinal;
	}
}
