package com.hippo.nky.entity.standard;

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
 * @Description: 附件上传
 * @author zhangdaihao
 * @date 2013-09-05 17:11:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_portal_attachment", schema = "")
@SuppressWarnings("serial")
public class PortalAttachmentEntity implements java.io.Serializable {
	/**附件编号*/
	private java.lang.String id;
	/**附件描述*/
	private java.lang.String description;
	/**附件地址*/
	private java.lang.String url;
	/**上传日期*/
	private java.util.Date uploadtime;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String createby;
	/**文件名称*/
	private java.lang.String filename;
	/**关联id*/
	private java.lang.String associateid;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件编号
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
	 *@param: java.lang.String  附件编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=200)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件地址
	 */
	@Column(name ="URL",nullable=true,length=200)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件地址
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  上传日期
	 */
	@Column(name ="UPLOADTIME",nullable=true,scale=6)
	public java.util.Date getUploadtime(){
		return this.uploadtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  上传日期
	 */
	public void setUploadtime(java.util.Date uploadtime){
		this.uploadtime = uploadtime;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATEBY",nullable=true,length=32)
	public java.lang.String getCreateby(){
		return this.createby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateby(java.lang.String createby){
		this.createby = createby;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件名称
	 */
	@Column(name ="FILENAME",nullable=true,length=200)
	public java.lang.String getFilename(){
		return this.filename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件名称
	 */
	public void setFilename(java.lang.String filename){
		this.filename = filename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联id
	 */
	@Column(name ="ASSOCIATEID",nullable=true,length=32)
	public java.lang.String getAssociateid(){
		return this.associateid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联id
	 */
	public void setAssociateid(java.lang.String associateid){
		this.associateid = associateid;
	}
}
