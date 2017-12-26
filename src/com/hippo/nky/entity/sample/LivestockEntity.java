package com.hippo.nky.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 畜禽抽样信息
 * @author nky
 * @date 2013-11-09 14:05:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_LIVESTOCK", schema = "")
@SuppressWarnings("serial")
public class LivestockEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**样品编号*/
	private java.lang.String sampleCode;
	/**注册商标*/
	private java.lang.String tradeMark;
	/**包装*/
	private java.lang.String pack;
	/**签封标志*/
	private java.lang.String signFlg;
	/**畜主/货主*/
	private java.lang.String cargoOwner;
	/**动物产地/来源*/
	private java.lang.String animalOrigin;
	/**检疫证号*/
	private java.lang.String cardNumber;
	/**抽样依据*/
	private java.lang.String taskSource;
	/**抽样数量*/
	private java.lang.String samplingCount;
	/**抽样基数*/
	private java.lang.String samplingBaseCount;
	/**保存情况*/
	private java.lang.String saveSaveSituation;
	/**抽样方式*/
	private java.lang.String samplingMode;
	/** 抽样单ID*/
	private java.lang.String samplingMonadId;
	/** 样品名称(回显用) */
	private java.lang.String sampleName;
	/** 样品图片(回显用) */
	private java.lang.String samplePath;
	/**条码**/
	private java.lang.String dCode;
	/** 制样编码(回显用) */
	private java.lang.String spCode;
	/**
	 * 样品填报时间
	 */
	private java.lang.String samplingTime;

	@Transient
	public java.lang.String getdCode() {
		return dCode;
	}

	public void setdCode(java.lang.String dCode) {
		this.dCode = dCode;
	}

	/**任务编码**/
	private java.lang.String taskCode;
	@Transient
	public java.lang.String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(java.lang.String taskCode) {
		this.taskCode = taskCode;
	}
	/**备注*/
	private java.lang.String remark;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Transient
	public java.lang.String getRemark(){
		return this.remark;
	}
	
	/** 抽样详细地址*/
	private java.lang.String samplingAddress;
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样详细地址
	 */
	@Transient
	public java.lang.String getSamplingAddress() {
		return samplingAddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样详细地址
	 */
	public void setSamplingAddress(java.lang.String samplingAddress) {
		this.samplingAddress = samplingAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	/**图片内容*/
	private String imgContent;
	
	/**
	 * 方法: 设置String
	 * 
	 * @param: String 设置图片内容
	 */
	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}

	/**
	 * 方法: 取得String
	 * 
	 * @return: String 取得图片内容
	 */
	@Transient
	public String getImgContent() {
		return imgContent;
	}
	
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
	 *@return: java.lang.String  签封标志
	 */
	@Column(name ="SIGN_FLG",nullable=true,length=50)
	public java.lang.String getSignFlg(){
		return this.signFlg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签封标志
	 */
	public void setSignFlg(java.lang.String signFlg){
		this.signFlg = signFlg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  畜主/货主
	 */
	@Column(name ="CARGO_OWNER",nullable=true,length=50)
	public java.lang.String getCargoOwner(){
		return this.cargoOwner;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  畜主/货主
	 */
	public void setCargoOwner(java.lang.String cargoOwner){
		this.cargoOwner = cargoOwner;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动物产地/来源
	 */
	@Column(name ="ANIMAL_ORIGIN",nullable=true,length=50)
	public java.lang.String getAnimalOrigin(){
		return this.animalOrigin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动物产地/来源
	 */
	public void setAnimalOrigin(java.lang.String animalOrigin){
		this.animalOrigin = animalOrigin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检疫证号
	 */
	@Column(name ="CARD_NUMBER",nullable=true,length=50)
	public java.lang.String getCardNumber(){
		return this.cardNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检疫证号
	 */
	public void setCardNumber(java.lang.String cardNumber){
		this.cardNumber = cardNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务来源
	 */
	@Column(name ="TASK_SOURCE",nullable=true,length=50)
	public java.lang.String getTaskSource(){
		return this.taskSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务来源
	 */
	public void setTaskSource(java.lang.String taskSource){
		this.taskSource = taskSource;
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
	 *@return: java.lang.String  保存情况
	 */
	@Column(name ="SAVE_SAVE_SITUATION",nullable=true,length=50)
	public java.lang.String getSaveSaveSituation(){
		return this.saveSaveSituation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保存情况
	 */
	public void setSaveSaveSituation(java.lang.String saveSaveSituation){
		this.saveSaveSituation = saveSaveSituation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  抽样方式
	 */
	@Column(name ="SAMPLING_MODE",nullable=true,length=50)
	public java.lang.String getSamplingMode(){
		return this.samplingMode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  抽样方式
	 */
	public void setSamplingMode(java.lang.String samplingMode){
		this.samplingMode = samplingMode;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 抽样单ID
	 */
	@Column(name = "SAMPLING_MONAD_ID", nullable = true, length = 32)
	public java.lang.String getSamplingMonadId() {
		return samplingMonadId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 抽样单ID
	 */
	public void setSamplingMonadId(java.lang.String samplingMonadId) {
		this.samplingMonadId = samplingMonadId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品名称
	 */
	@Transient
	public java.lang.String getSampleName() {
		return sampleName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品名称
	 */
	public void setSampleName(java.lang.String sampleName) {
		this.sampleName = sampleName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 样品图片
	 */
	@Transient
	public java.lang.String getSamplePath() {
		return this.samplePath;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 样品图片
	 */
	public void setSamplePath(java.lang.String samplePath) {
		this.samplePath = samplePath;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 制样编码
	 */
	@Transient
	public java.lang.String getSpCode() {
		return this.spCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 制样编码
	 */
	public void setSpCode(java.lang.String spCode) {
		this.spCode = spCode;
	}
	
	@Transient
	public java.lang.String getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(java.lang.String samplingTime) {
		this.samplingTime = samplingTime;
	}
}
