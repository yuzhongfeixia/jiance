package com.hippo.nky.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 生鲜乳实体类
 * @author nky
 * @date 2013-11-09 10:40:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_fresh_milk", schema = "")
@SuppressWarnings("serial")
public class NkyFreshMilkEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品ID*/
	private java.lang.String sampleCode;
	/**抽样数量*/
	//private java.lang.Integer samplingCount;
	private java.lang.String samplingCount;
	/**抽样基数*/
	//private java.lang.Integer samplingBaseCount;
	private java.lang.String samplingBaseCount;
	/**生鲜乳类型*/
	private java.lang.String type;
	/**类型备注*/
	private java.lang.String typeRemark;
	/**生鲜乳收购许可证*/
	private java.lang.String buyLicence;
	/**许可证号*/
	private java.lang.String licenceNo;
	/**许可证备注*/
	private java.lang.String licenceRemark;
	/**生鲜乳准运证*/
	private java.lang.String navicert;
	/**准运证号*/
	private java.lang.String navicertNo;
	/**准运证备注*/
	private java.lang.String navicertRemark;
	/**生鲜乳交接单*/
	private java.lang.String deliveryReceitp;
	/**交接单备注*/
	private java.lang.String deliveryReceitpRemark;
	/**交奶去向*/
	private java.lang.String direction;
	/**联系电话(法定代表人)*/
	private java.lang.String telphone;
	/**受检人*/
	private java.lang.String examinee;
	/**联系电话(受检人)*/
	private java.lang.String telphone2;
	/**抽样日期和时间*/
	private java.util.Date samplingDate;
	
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
	 *@return: java.lang.String  样品ID
	 */
	@Column(name ="SAMPLE_CODE",nullable=true,length=32)
	public java.lang.String getSampleCode(){
		return this.sampleCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  样品ID
	 */
	public void setSampleCode(java.lang.String sampleCode){
		this.sampleCode = sampleCode;
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
	 *@return: java.lang.String  生鲜乳类型
	 */
	@Column(name ="TYPE",nullable=true,length=50)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生鲜乳类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型备注
	 */
	@Column(name ="TYPE_REMARK",nullable=true,length=64)
	public java.lang.String getTypeRemark(){
		return this.typeRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型备注
	 */
	public void setTypeRemark(java.lang.String typeRemark){
		this.typeRemark = typeRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生鲜乳收购许可证
	 */
	@Column(name ="BUY_LICENCE",nullable=true,length=50)
	public java.lang.String getBuyLicence(){
		return this.buyLicence;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生鲜乳收购许可证
	 */
	public void setBuyLicence(java.lang.String buyLicence){
		this.buyLicence = buyLicence;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  许可证号
	 */
	@Column(name ="LICENCE_NO",nullable=true,length=32)
	public java.lang.String getLicenceNo(){
		return this.licenceNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  许可证号
	 */
	public void setLicenceNo(java.lang.String licenceNo){
		this.licenceNo = licenceNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  许可证备注
	 */
	@Column(name ="LICENCE_REMARK",nullable=true,length=64)
	public java.lang.String getLicenceRemark(){
		return this.licenceRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  许可证备注
	 */
	public void setLicenceRemark(java.lang.String licenceRemark){
		this.licenceRemark = licenceRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生鲜乳准运证
	 */
	@Column(name ="NAVICERT",nullable=true,length=50)
	public java.lang.String getNavicert(){
		return this.navicert;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生鲜乳准运证
	 */
	public void setNavicert(java.lang.String navicert){
		this.navicert = navicert;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  准运证号
	 */
	@Column(name ="NAVICERT_NO",nullable=true,length=32)
	public java.lang.String getNavicertNo(){
		return this.navicertNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  准运证号
	 */
	public void setNavicertNo(java.lang.String navicertNo){
		this.navicertNo = navicertNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  准运证备注
	 */
	@Column(name ="NAVICERT_REMARK",nullable=true,length=64)
	public java.lang.String getNavicertRemark(){
		return this.navicertRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  准运证备注
	 */
	public void setNavicertRemark(java.lang.String navicertRemark){
		this.navicertRemark = navicertRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生鲜乳交接单
	 */
	@Column(name ="DELIVERY_RECEITP",nullable=true,length=50)
	public java.lang.String getDeliveryReceitp(){
		return this.deliveryReceitp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生鲜乳交接单
	 */
	public void setDeliveryReceitp(java.lang.String deliveryReceitp){
		this.deliveryReceitp = deliveryReceitp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  交接单备注
	 */
	@Column(name ="DELIVERY_RECEITP_REMARK",nullable=true,length=64)
	public java.lang.String getDeliveryReceitpRemark(){
		return this.deliveryReceitpRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交接单备注
	 */
	public void setDeliveryReceitpRemark(java.lang.String deliveryReceitpRemark){
		this.deliveryReceitpRemark = deliveryReceitpRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  交奶去向
	 */
	@Column(name ="DIRECTION",nullable=true,length=128)
	public java.lang.String getDirection(){
		return this.direction;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交奶去向
	 */
	public void setDirection(java.lang.String direction){
		this.direction = direction;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话(法定代表人)
	 */
	@Column(name ="TELPHONE",nullable=true,length=16)
	public java.lang.String getTelphone(){
		return this.telphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话(法定代表人)
	 */
	public void setTelphone(java.lang.String telphone){
		this.telphone = telphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受检人
	 */
	@Column(name ="EXAMINEE",nullable=true,length=16)
	public java.lang.String getExaminee(){
		return this.examinee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受检人
	 */
	public void setExaminee(java.lang.String examinee){
		this.examinee = examinee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话(受检人)
	 */
	@Column(name ="TELPHONE2",nullable=true,length=16)
	public java.lang.String getTelphone2(){
		return this.telphone2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话(受检人)
	 */
	public void setTelphone2(java.lang.String telphone2){
		this.telphone2 = telphone2;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  抽样日期和时间
	 */
	@Column(name ="SAMPLING_DATE",nullable=true,scale=6)
	public java.util.Date getSamplingDate(){
		return this.samplingDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  抽样日期和时间
	 */
	public void setSamplingDate(java.util.Date samplingDate){
		this.samplingDate = samplingDate;
	}
}
