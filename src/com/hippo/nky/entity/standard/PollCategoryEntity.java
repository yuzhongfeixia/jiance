package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.Distinct;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;
import org.jeecgframework.core.annotation.excel.Excel;

/**   
 * @Title: Entity
 * @Description: 污染物分类
 * @date 2013-07-02 17:44:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_po_category", schema = "")
@SuppressWarnings("serial")
public class PollCategoryEntity implements java.io.Serializable {
	/**版本ID*/
	private java.lang.String versionid;
	/**ID*/
	private java.lang.String id;
	/**父节点ID*/
	@Excel(exportName="父分类编码", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String pid;
	/**分类名称*/
	@Excel(exportName="分类名称", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String name;
	/**分类编码*/
	@Excel(exportName="分类编码", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private java.lang.String code;
	/**创建时间*/
	private java.util.Date createdate;
	/**描述*/
	@Excel(exportName="描述", exportConvertSign = 0, exportFieldWidth = 40, importConvertSign = 0)
	private java.lang.String describe;
	/**保存节点 0：新增 1:修改*/
	private int saveDom = 0;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本ID
	 */
	@Column(name ="VERSIONID",nullable=true,length=32)
	public java.lang.String getVersionid(){
		return this.versionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本ID
	 */
	public void setVersionid(java.lang.String versionid){
		this.versionid = versionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父节点ID
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父节点ID
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类名称
	 */
	@Column(name ="NAME",nullable=true,length=128)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类编码
	 */
	@Distinct
	@Column(name ="CODE",nullable=true,length=128)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类编码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
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
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIBE",nullable=true,length=1000)
	public java.lang.String getDescribe(){
		return this.describe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescribe(java.lang.String describe){
		this.describe = describe;
	}
	
	/**
	 *方法: 取得int
	 *@return: int 保存节点
	 */
	@Transient
	public int getSaveDom(){
		return this.saveDom;
	}

	/**
	 *方法: 设置int
	 *@param: int 保存节点
	 */
	public void setSaveDom(int saveDom){
		this.saveDom = saveDom;
	}
}
