package com.hippo.nky.entity.industry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 行业信息表 
 * @author nky
 * @date 2013-08-01 14:53:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_PORTAL_INDUSTRY", schema = "")
@SuppressWarnings("serial")
public class IndustryEntity implements java.io.Serializable {
	/**行业编号*/
	private java.lang.String id;
	/**编码*/
	private java.lang.String code;
	/**名称*/
	private java.lang.String name;
	//private Set<OrganizationEntity> lstOrgs = new HashSet<OrganizationEntity>();
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行业编号
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
	 *@param: java.lang.String  行业编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编码
	 */
	@Column(name ="CODE",nullable=true,length=8)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 * 
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}

//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="inEntity")
//	public Set<OrganizationEntity> getLstOrgs() {
//		return lstOrgs;
//	}
//
//	public void setLstOrgs(Set<OrganizationEntity> lstOrgs) {
//		this.lstOrgs = lstOrgs;
//	}
	
}
