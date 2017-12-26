package com.hippo.nky.entity.monitoring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 方案附件
 * @author nky
 * @date 2013-10-23 15:54:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "NKY_MONITORING_ATTACHMENT", schema = "")
@SuppressWarnings("serial")
public class MonitoringAttachmentEntity implements java.io.Serializable {
	/**编号*/
	private java.lang.String id;
	/**方案编号*/
	private java.lang.String planCode;
	/**附件路径*/
	private java.lang.String path;
	/**附件名称*/
	private java.lang.String pathName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
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
	 *@param: java.lang.String  编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  方案编号
	 */
	@Column(name ="PLAN_CODE",nullable=true,length=32)
	public java.lang.String getPlanCode(){
		return this.planCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  方案编号
	 */
	public void setPlanCode(java.lang.String planCode){
		this.planCode = planCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件路径
	 */
	@Column(name ="PATH",nullable=true,length=128)
	public java.lang.String getPath(){
		return this.path;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件路径
	 */
	public void setPath(java.lang.String path){
		this.path = path;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件名称
	 */
	@Transient
	public java.lang.String getPathName() {
		Pattern pattern = Pattern.compile("[^/\\\\]+$"); 
		Matcher matcher = pattern.matcher(this.path);
		if(matcher.find()){
			setPathName(matcher.group());
		}
		return pathName;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件名称
	 */
	public void setPathName(java.lang.String pathName) {
		this.pathName = pathName;
	}
	
}
