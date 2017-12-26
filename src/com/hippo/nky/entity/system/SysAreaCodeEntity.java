package com.hippo.nky.entity.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.SequenceGenerator;

import jeecg.system.pojo.base.TSDepart;
import jeecg.system.pojo.base.TSTypegroup;

/**   
 * @Title: Entity
 * @Description: 行政区划
 * @author nky
 * @date 2013-10-23 13:16:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "SYS_AREA_CODE", schema = "")
@SuppressWarnings("serial")
public class SysAreaCodeEntity extends IdEntity implements java.io.Serializable {
	public static Map<String, SysAreaCodeEntity> allSysAreas = new HashMap<String, SysAreaCodeEntity>();

	private SysAreaCodeEntity sysAreaCodeEntity;//上级区域
	/**自编码*/
	private java.lang.String selfcode;
	/**父编号*/
	private java.lang.String parentareaid;
	/**行政区划编码*/
	private java.lang.String code;
	/**行政区划名称*/
	private java.lang.String areaname;
	/**是否为当前默认区域（0：否 ,1：是）*/
	private java.lang.String flag;
	/**显示排序*/
	private java.lang.Integer showOrder;
	/**
	 * 父编码(检索结果)
	 */
	private java.lang.String pcode;

	private List<SysAreaCodeEntity> sysAreaCodeEntitys = new ArrayList<SysAreaCodeEntity>();//下级部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentareaid")
	public SysAreaCodeEntity getSysAreaCodeEntity() {
		return this.sysAreaCodeEntity;
	}

	public void setSysAreaCodeEntity(SysAreaCodeEntity sysAreaCodeEntity) {
		this.sysAreaCodeEntity = sysAreaCodeEntity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自编码
	 */
	@Column(name ="SELFCODE",nullable=true,length=32)
	public java.lang.String getSelfcode(){
		return this.selfcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自编码
	 */
	public void setSelfcode(java.lang.String selfcode){
		this.selfcode = selfcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父编号
	 */
	@Transient
	public java.lang.String getParentareaid(){
		return this.parentareaid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父编号
	 */
	public void setParentareaid(java.lang.String parentareaid){
		this.parentareaid = parentareaid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行政区划编码
	 */
	@Column(name ="CODE",nullable=true,length=6)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行政区划编码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行政区划名称
	 */
	@Column(name ="AREANAME",nullable=true,length=128)
	public java.lang.String getAreaname(){
		return this.areaname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行政区划名称
	 */
	public void setAreaname(java.lang.String areaname){
		this.areaname = areaname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否为当前默认区域（0：否 ,1：是）
	 */
	@Column(name ="FLAG",nullable=true,length=1)
	public java.lang.String getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否为当前默认区域（0：否 ,1：是）
	 */
	public void setFlag(java.lang.String flag){
		this.flag = flag;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysAreaCodeEntity")
	public List<SysAreaCodeEntity> getSysAreaCodeEntitys() {
		return sysAreaCodeEntitys;
	}

	public void setSysAreaCodeEntitys(List<SysAreaCodeEntity> sysAreaCodeEntitys) {
		this.sysAreaCodeEntitys = sysAreaCodeEntitys;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行政区划名称
	 */
	@Column(name ="SHOW_ORDER",nullable=true)
	public java.lang.Integer getShowOrder(){
		return this.showOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行政区划名称
	 */
	public void setShowOrder(java.lang.Integer showOrder){
		this.showOrder = showOrder;
	}

	@Transient
	public java.lang.String getPcode() {
		return pcode;
	}

	public void setPcode(java.lang.String pcode) {
		this.pcode = pcode;
	}
	
	
}
