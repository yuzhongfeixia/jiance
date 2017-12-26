package com.hippo.nky.entity.person123;

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
 * @Description: person123
 * @author zhangdaihao
 * @date 2013-06-04 17:32:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "person123", schema = "")
@SuppressWarnings("serial")
public class Person123Entity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**age*/
	private java.lang.Integer age;
	/**createdt*/
	private java.util.Date createdt;
	/**name*/
	private java.lang.String name;
	/**salary*/
	private BigDecimal salary;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  age
	 */
	@Column(name ="AGE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  age
	 */
	public void setAge(java.lang.Integer age){
		this.age = age;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createdt
	 */
	@Column(name ="CREATEDT",nullable=true)
	public java.util.Date getCreatedt(){
		return this.createdt;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createdt
	 */
	public void setCreatedt(java.util.Date createdt){
		this.createdt = createdt;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  salary
	 */
	@Column(name ="SALARY",nullable=true,precision=19,scale=2)
	public BigDecimal getSalary(){
		return this.salary;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  salary
	 */
	public void setSalary(BigDecimal salary){
		this.salary = salary;
	}
}
