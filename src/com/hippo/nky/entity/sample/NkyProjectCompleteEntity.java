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
 * @Description: 项目抽样完成
 * @author nky
 * @date 2014-03-03 16:36:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_PROJECT_COMPLETE", schema = "")
@SuppressWarnings("serial")
public class NkyProjectCompleteEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**质检机构code*/
	private java.lang.String orgCode;
	/**项目code*/
	private java.lang.String projectCode;
	
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
	 *@return: java.lang.String  质检机构code
	 */
	@Column(name ="ORG_CODE",nullable=true,length=32)
	public java.lang.String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  质检机构code
	 */
	public void setOrgCode(java.lang.String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目code
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目code
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
}
