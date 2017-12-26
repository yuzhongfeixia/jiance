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
 * @Description: 样品条码打印
 * @author nky
 * @date 2013-11-05 13:51:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_BARCODE_PRINTING", schema = "")
@SuppressWarnings("serial")
public class BarcodePrintEntity implements java.io.Serializable {
	/**打印份数*/
	private java.lang.Integer printNumberCopies;
	/**打印数量*/
	private java.lang.Integer printCount;
	/**打印时间*/
	private java.util.Date createDate;
	/**id*/
	private java.lang.String id;
	/**项目Code*/
	private java.lang.String projectCode;
	/**项目名称*/
	private java.lang.String projectName;
	
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  打印份数
	 */
	@Column(name ="PRINT_NUMBER_COPIES",nullable=true)
	public java.lang.Integer getPrintNumberCopies(){
		return this.printNumberCopies;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  打印份数
	 */
	public void setPrintNumberCopies(java.lang.Integer printNumberCopies){
		this.printNumberCopies = printNumberCopies;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  打印数量
	 */
	@Column(name ="PRINT_COUNT",nullable=true)
	public java.lang.Integer getPrintCount(){
		return this.printCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  打印数量
	 */
	public void setPrintCount(java.lang.Integer printCount){
		this.printCount = printCount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  打印时间
	 */
	@Column(name ="CREATE_DATE",nullable=true,scale=6)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  打印时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目code
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode() {
		return projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目code
	 */
	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	@Transient
	public java.lang.String getProjectName() {
		return projectName;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}
	
	
}
