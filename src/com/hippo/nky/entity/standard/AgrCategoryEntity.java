package com.hippo.nky.entity.standard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;

/**   
 * @Title: Entity
 * @Description: 农产品分类
 * @author zhangdaihao
 * @date 2013-07-25 16:05:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_agr_category", schema = "")
@SuppressWarnings("serial")
public class AgrCategoryEntity implements java.io.Serializable {
	/**版本id*/
	private java.lang.String versionid;
	/**创建时间*/
	private java.util.Date createdate;
	/**id*/
	private java.lang.String id;
	/**父id*/
	private java.lang.String pid;
	/**农产品中文名*/
	private java.lang.String cname;
	/**农产品中文别名*/
	private java.lang.String calias;
	/**农产品拉丁名*/
	private java.lang.String latin;
	/**图片路径*/
	private java.lang.String imagepath;
	/**描述*/
	private java.lang.String describe;
	/**GEMS*/
	private java.lang.String gems;
	/**农产品英文别名*/
	private java.lang.String ealias;
	/**农产品英文名*/
	private java.lang.String ename;
	/**类型(1:类,2.物类,2:农产品)*/
	private java.lang.Integer agrcategorytype;
	/**FOODEX*/
	private java.lang.String foodex;
	/**创建人*/
	private java.lang.String creater;
	/**修改人*/
	private java.lang.String editor;
	/**修改时间*/
	private java.util.Date editdate;
	/**code*/
	private java.lang.String code;
	/**保存节点 0：新增 1:修改*/
	private int saveDom = 0;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本id
	 */
	@Column(name ="VERSIONID",nullable=true,length=32)
	public java.lang.String getVersionid(){
		return this.versionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本id
	 */
	public void setVersionid(java.lang.String versionid){
		this.versionid = versionid;
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
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品中文名
	 */
	@Column(name ="CNAME",nullable=true,length=32)
	public java.lang.String getCname(){
		return this.cname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品中文名
	 */
	public void setCname(java.lang.String cname){
		this.cname = cname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品中文别名
	 */
	@Column(name ="CALIAS",nullable=true,length=32)
	public java.lang.String getCalias(){
		return this.calias;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品中文别名
	 */
	public void setCalias(java.lang.String calias){
		this.calias = calias;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品拉丁名
	 */
	@Column(name ="LATIN",nullable=true,length=32)
	public java.lang.String getLatin(){
		return this.latin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品拉丁名
	 */
	public void setLatin(java.lang.String latin){
		this.latin = latin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片路径
	 */
	@Column(name ="IMAGEPATH",nullable=true,length=100)
	public java.lang.String getImagepath(){
		return this.imagepath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片路径
	 */
	public void setImagepath(java.lang.String imagepath){
		this.imagepath = imagepath;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  GEMS
	 */
	@Column(name ="GEMS",nullable=true,length=32)
	public java.lang.String getGems(){
		return this.gems;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  GEMS
	 */
	public void setGems(java.lang.String gems){
		this.gems = gems;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品英文别名
	 */
	@Column(name ="EALIAS",nullable=true,length=32)
	public java.lang.String getEalias(){
		return this.ealias;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品英文别名
	 */
	public void setEalias(java.lang.String ealias){
		this.ealias = ealias;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  农产品英文名
	 */
	@Column(name ="ENAME",nullable=true,length=32)
	public java.lang.String getEname(){
		return this.ename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  农产品英文名
	 */
	public void setEname(java.lang.String ename){
		this.ename = ename;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类型(1:类,2.物类,2:农产品)
	 */
	@Column(name ="AGRCATEGORYTYPE",nullable=true)
	public java.lang.Integer getAgrcategorytype(){
		return this.agrcategorytype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型(1:类,2.物类,2:农产品)
	 */
	public void setAgrcategorytype(java.lang.Integer agrcategorytype){
		this.agrcategorytype = agrcategorytype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  FOODEX
	 */
	@Column(name ="FOODEX",nullable=true,length=32)
	public java.lang.String getFoodex(){
		return this.foodex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  FOODEX
	 */
	public void setFoodex(java.lang.String foodex){
		this.foodex = foodex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATER",nullable=true,length=32)
	public java.lang.String getCreater(){
		return this.creater;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreater(java.lang.String creater){
		this.creater = creater;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="EDITOR",nullable=true,length=32)
	public java.lang.String getEditor(){
		return this.editor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setEditor(java.lang.String editor){
		this.editor = editor;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="EDITDATE",nullable=true,scale=6)
	public java.util.Date getEditdate(){
		return this.editdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setEditdate(java.util.Date editdate){
		this.editdate = editdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  code
	 */
	@Column(name ="CODE",nullable=true,length=100)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  code
	 */
	public void setCode(java.lang.String code){
		this.code = code;
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
