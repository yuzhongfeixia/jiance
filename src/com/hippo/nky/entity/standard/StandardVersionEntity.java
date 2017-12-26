package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;

/**   
 * @Title: Entity
 * @Description: 版本管理
 * @author zhangdaihao
 * @date 2013-07-01 11:38:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_standard_version", schema = "")
@SuppressWarnings("serial")
public class StandardVersionEntity implements java.io.Serializable {
	/**版本ID*/
	private java.lang.String id;
	/**版本名称*/
	private java.lang.String cname;
	/**实施起始日期*/
	private java.util.Date begindate;
	/**发布机构*/
	private java.lang.String publishorg;
	/**创建人*/
	private java.lang.String creater;
	/**创建时间*/
	private java.util.Date createdate;
	/**类别*/
	private java.lang.Integer category;
	/**发布标识(0：不发布 1：发布)*/
	private java.lang.Integer publishmark;
	/**停用标识(0：启用 1：停用)*/
	private java.lang.Integer stopflag;
	/**农产品id列表*/
	private java.lang.String agrIdArry;
	/**污染物id列表*/
	private java.lang.String pollIdArry;
	/** 农产品版本id */
	private java.lang.String agrVersionId;
	/** 污染物版本id */
	private java.lang.String pollVersionId;
	/** 限量标准版本id */
	private java.lang.String limitVersionId;
	/** 判定标准版本id */
	private java.lang.String judgeVersionId;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本ID
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	//@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@PrimaryKeyPolicy(policy = "semi", method = "default")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本名称
	 */
	@Column(name ="CNAME",nullable=true,length=128)
	public java.lang.String getCname(){
		return this.cname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本名称
	 */
	public void setCname(java.lang.String cname){
		this.cname = cname;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  实施起始日期
	 */
	@Column(name ="BEGINDATE",nullable=true)
	public java.util.Date getBegindate(){
		return this.begindate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  实施起始日期
	 */
	public void setBegindate(java.util.Date begindate){
		this.begindate = begindate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布机构
	 */
	@Column(name ="PUBLISHORG",nullable=true,length=128)
	public java.lang.String getPublishorg(){
		return this.publishorg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布机构
	 */
	public void setPublishorg(java.lang.String publishorg){
		this.publishorg = publishorg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATER",nullable=true,length=128)
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
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATEDATE",nullable=true)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类别
	 */
	@Column(name ="CATEGORY",nullable=true)
	public java.lang.Integer getCategory(){
		return this.category;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类别
	 */
	public void setCategory(java.lang.Integer category){
		this.category = category;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发布标识(0：不发布 1：发布)
	 */
	@Column(name ="PUBLISHMARK",nullable=true)
	public java.lang.Integer getPublishmark(){
		return this.publishmark;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发布标识(0：不发布 1：发布)
	 */
	public void setPublishmark(java.lang.Integer publishmark){
		this.publishmark = publishmark;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  停用标识(0：停用 1：停用)
	 */
	@Column(name ="STOPFLAG",nullable=true)
	public java.lang.Integer getStopflag() {
		return stopflag;
	}
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  停用标识(0：停用 1：停用)
	 */
	public void setStopflag(java.lang.Integer stopflag) {
		this.stopflag = stopflag;
	}

	@Transient
	public java.lang.String getAgrIdArry() {
		return agrIdArry;
	}

	public void setAgrIdArry(java.lang.String agrIdArry) {
		this.agrIdArry = agrIdArry;
	}
	@Transient
	public java.lang.String getPollIdArry() {
		return pollIdArry;
	}

	public void setPollIdArry(java.lang.String pollIdArry) {
		this.pollIdArry = pollIdArry;
	}

	@Column(name ="AGR_VERSION_ID",nullable=true,length=32)
	public java.lang.String getAgrVersionId() {
		return agrVersionId;
	}

	public void setAgrVersionId(java.lang.String agrVersionId) {
		this.agrVersionId = agrVersionId;
	}

	@Column(name ="POLL_VERSION_ID",nullable=true,length=32)
	public java.lang.String getPollVersionId() {
		return pollVersionId;
	}

	public void setPollVersionId(java.lang.String pollVersionId) {
		this.pollVersionId = pollVersionId;
	}

	@Column(name ="LIMIT_VERSION_ID",nullable=true,length=32)
	public java.lang.String getLimitVersionId() {
		return limitVersionId;
	}

	public void setLimitVersionId(java.lang.String limitVersionId) {
		this.limitVersionId = limitVersionId;
	}

	@Transient
	public java.lang.String getJudgeVersionId() {
		return judgeVersionId;
	}

	public void setJudgeVersionId(java.lang.String judgeVersionId) {
		this.judgeVersionId = judgeVersionId;
	}

}
