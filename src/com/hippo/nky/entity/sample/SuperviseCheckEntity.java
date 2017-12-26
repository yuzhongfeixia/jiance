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
 * @Description: 监督抽查抽样信息
 * @author nky
 * @date 2013-11-09 10:54:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_supervise_check", schema = "")
@SuppressWarnings("serial")
public class SuperviseCheckEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品编号*/
	private java.lang.String sampleCode;
	/**注册商标*/
	private java.lang.String tradeMark;
	/**包装*/
	private java.lang.String pack;
	/**等级规格*/
	private java.lang.String specifications;
	/**标识*/
	private java.lang.String flag;
	/**生产日期或批号（检疫证号）*/
	private java.lang.String batchNumber;
	/**执行标准*/
	private java.lang.String execStandard;
	/**产品认证登记情况*/
	private java.lang.String productCer;
	/**产品认证登记证书编号*/
	private java.lang.String productCerNo;
	/**获证日期*/
	private java.util.Date certificateTime;
	/**抽样数量*/
	//private java.lang.Integer samplingCount;
	private java.lang.String samplingCount;
	/**抽样基数*/
	private java.lang.String samplingBaseCount;
	/**通知书编号及有效期*/
	private java.lang.String noticeDetails;
	
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
	 *@return: java.lang.String  注册商标
	 */
	@Column(name ="TRADE_MARK",nullable=true,length=32)
	public java.lang.String getTradeMark(){
		return this.tradeMark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注册商标
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
	 *@return: java.lang.String  等级规格
	 */
	@Column(name ="SPECIFICATIONS",nullable=true,length=32)
	public java.lang.String getSpecifications(){
		return this.specifications;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  等级规格
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
	 *@return: java.lang.String  生产日期或批号（检疫证号）
	 */
	@Column(name ="BATCH_NUMBER",nullable=true,length=128)
	public java.lang.String getBatchNumber(){
		return this.batchNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期或批号（检疫证号）
	 */
	public void setBatchNumber(java.lang.String batchNumber){
		this.batchNumber = batchNumber;
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
	 *@return: java.lang.String  产品认证登记情况
	 */
	@Column(name ="PRODUCT_CER",nullable=true,length=50)
	public java.lang.String getProductCer(){
		return this.productCer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品认证登记情况
	 */
	public void setProductCer(java.lang.String productCer){
		this.productCer = productCer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品认证登记证书编号
	 */
	@Column(name ="PRODUCT_CER_NO",nullable=true,length=32)
	public java.lang.String getProductCerNo(){
		return this.productCerNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品认证登记证书编号
	 */
	public void setProductCerNo(java.lang.String productCerNo){
		this.productCerNo = productCerNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  获证日期
	 */
	@Column(name ="CERTIFICATE_TIME",nullable=true,scale=6)
	public java.util.Date getCertificateTime(){
		return this.certificateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  获证日期
	 */
	public void setCertificateTime(java.util.Date certificateTime){
		this.certificateTime = certificateTime;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  抽样基数
	 */
//	@Column(name ="SAMPLING_BASE_COUNT",nullable=true)
//	public java.lang.Integer getSamplingBaseCount(){
//		return this.samplingBaseCount;
//	}
	@Column(name ="SAMPLING_BASE_COUNT",nullable=true,length=50)
	public java.lang.String getSamplingBaseCount(){
		return this.samplingBaseCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  抽样基数
	 */
	//public void setSamplingBaseCount(java.lang.Integer samplingBaseCount){
	public void setSamplingBaseCount(java.lang.String samplingBaseCount){
		this.samplingBaseCount = samplingBaseCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知书编号及有效期
	 */
	@Column(name ="NOTICE_DETAILS",nullable=true,length=128)
	public java.lang.String getNoticeDetails(){
		return this.noticeDetails;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知书编号及有效期
	 */
	public void setNoticeDetails(java.lang.String noticeDetails){
		this.noticeDetails = noticeDetails;
	}
}
