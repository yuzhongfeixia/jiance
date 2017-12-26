package com.hippo.nky.entity.standard;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 污染物性质
 * @author nky
 * @date 2013-12-02 13:59:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_POLL_PROPERTY", schema = "")
@SuppressWarnings("serial")
public class PollPropertyEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**CAS码*/
	private java.lang.String cas;
	/**禁用限用标识*/
	private java.lang.Integer disableFlg;
	
	/**污染物名称（检索条件）*/
	private java.lang.String pollname;
	
	/**中文名（检索条件）*/
	private java.lang.String popcname;
	
	/**英文名（检索条件）*/
	private java.lang.String popename;

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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用限用标识
	 */
	@Column(name ="DISABLE_FLG",nullable=true)
	public java.lang.Integer getDisableFlg(){
		return this.disableFlg;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用限用标识
	 */
	public void setDisableFlg(java.lang.Integer disableFlg){
		this.disableFlg = disableFlg;
	}
	
	@Transient
	public java.lang.String getPollname() {
		return pollname;
	}

	public void setPollname(java.lang.String pollname) {
		this.pollname = pollname;
	}

	@Transient
	public java.lang.String getPopcname() {
		return popcname;
	}

	public void setPopcname(java.lang.String popcname) {
		this.popcname = popcname;
	}
	@Transient
	public java.lang.String getPopename() {
		return popename;
	}

	public void setPopename(java.lang.String popename) {
		this.popename = popename;
	}
}
