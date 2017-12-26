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
 * @Description: xudltest
 * @author zhangdaihao
 * @date 2013-06-24 15:07:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "XDLTEST", schema = "")
@SuppressWarnings("serial")
public class XDLTESTEntity implements java.io.Serializable {
	/**xudl测试自定义主键*/
	private java.lang.String id;
	/**xudl测试用户名*/
	private java.lang.String username;
	/**xudl测试密码*/
	private java.lang.String password;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xudl测试自定义主键
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
	 *@param: java.lang.String  xudl测试自定义主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xudl测试用户名
	 */
	@Column(name ="USERNAME",nullable=false,length=20)
	public java.lang.String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  xudl测试用户名
	 */
	public void setUsername(java.lang.String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xudl测试密码
	 */
	@Column(name ="PASSWORD",nullable=false,length=30)
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  xudl测试密码
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}
}
