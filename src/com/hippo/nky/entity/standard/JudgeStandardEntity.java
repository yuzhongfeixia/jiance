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
 * @Description: 判定标准
 * @author zhangdaihao
 * @date 2013-07-09 13:31:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_judge_standard", schema = "")
@SuppressWarnings("serial")
public class JudgeStandardEntity implements java.io.Serializable {
	/**判定标准ID*/
	private java.lang.String id;
	/**版本ID*/
	private java.lang.String vid;
	/**限量标准ID*/
	private java.lang.String lid;
	/**农产品ID*/
	private java.lang.String agrid;
	/**污染物ID*/
	private java.lang.String pollid;
	/**判定标准值*/
	private java.math.BigDecimal value;
	/**单位*/
	private java.lang.Integer units;
	/**创建时间*/
	private java.util.Date createdate;
	/**判定值来源*/
	private java.lang.Integer valuefrom;
	/**使用规定*/
	private java.lang.Integer stipulate;
	/**农产品名称*/
	private java.lang.String agrname;
	/**污染物名称*/
	private java.lang.String pollname;

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  判定标准ID
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
	 *@param: java.lang.String  判定标准ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本ID
	 */
	@Column(name ="VID",nullable=true,length=32)
	public java.lang.String getVid(){
		return this.vid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本ID
	 */
	public void setVid(java.lang.String vid){
		this.vid = vid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限量标准ID
	 */
	@Column(name ="LID",nullable=true,length=32)
	public java.lang.String getLid(){
		return this.lid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限量标准ID
	 */
	public void setLid(java.lang.String lid){
		this.lid = lid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品ID
	 */
	@Column(name ="AGRID",nullable=true,length=32)
	public java.lang.String getAgrid(){
		return this.agrid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品ID
	 */
	public void setAgrid(java.lang.String agrid){
		this.agrid = agrid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  污染物ID
	 */
	@Column(name ="POLLID",nullable=true,length=32)
	public java.lang.String getPollid(){
		return this.pollid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  污染物ID
	 */
	public void setPollid(java.lang.String pollid){
		this.pollid = pollid;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  判定标准值
	 */
	@Column(name ="VALUE",nullable=true,precision=10,scale=6)
	public java.math.BigDecimal getValue(){
		return this.value;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  判定标准值
	 */
	public void setValue(java.math.BigDecimal value){
		this.value = value;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单位
	 */
	@Column(name ="UNITS",nullable=true)
	public java.lang.Integer getUnits(){
		return this.units;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单位
	 */
	public void setUnits(java.lang.Integer units){
		this.units = units;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  判定值来源
	 */
	@Column(name ="VALUEFROM",nullable=true)
	public java.lang.Integer getValuefrom() {
		return valuefrom;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  判定值来源
	 */
	public void setValuefrom(java.lang.Integer valuefrom) {
		this.valuefrom = valuefrom;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  使用规定
	 */
	@Column(name ="STIPULATE",nullable=true)
	public java.lang.Integer getStipulate() {
		return stipulate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  使用规定
	 */
	public void setStipulate(java.lang.Integer stipulate) {
		this.stipulate = stipulate;
	}
	
	
	@Transient
	public java.lang.String getAgrname() {
		return agrname;
	}
	public void setAgrname(java.lang.String agrname) {
		this.agrname = agrname;
	}
	
	@Transient
	public java.lang.String getPollname() {
		return pollname;
	}
	public void setPollname(java.lang.String pollname) {
		this.pollname = pollname;
	}


}
