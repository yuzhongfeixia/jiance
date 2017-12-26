package com.hippo.nky.entity.portal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 栏目管理
 * @author zhangdaihao
 * @date 2013-07-29 11:43:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "nky_portal_introductions", schema = "")
@SuppressWarnings("serial")
public class FrontPortalIntroductionsEntity implements java.io.Serializable {
	
	/**栏目主键*/
	private java.lang.String id;
	/**栏目名称*/
	private java.lang.String name;
	/**类型*/
	private java.lang.Integer type;
	/**内容*/
	private java.lang.String content;
	/**创建时间*/
	private java.util.Date createdate;
	/**创建人*/
	private java.lang.String createby;
	/**上级栏目id*/
	private java.lang.String pid;
	/**修改时间*/
	private java.util.Date updatedate;
	/**修改人*/
	private java.lang.String updateby;
	/**排序*/
	private java.lang.Integer sort;
	/**栏目等级*/
	private java.lang.Integer introductionleavel;
	/**显示类型*/
	private java.lang.Integer displaytype;
	/**列表来源*/
	private java.lang.Integer sourcelist;
	/**列表显示类型*/
	private java.lang.Integer listdisplaytype;
	/**关联条件*/
	private java.lang.String associatecondition;
	
	private List<FrontPortalIntroductionsEntity> FrontPortalIntroductionsEntitys = new ArrayList<FrontPortalIntroductionsEntity>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<FrontPortalIntroductionsEntity> getFrontPortalIntroductionsEntitys() {
		return FrontPortalIntroductionsEntitys;
	}

	public void setFrontPortalIntroductionsEntitys(List<FrontPortalIntroductionsEntity> frontPortalIntroductionsEntitys) {
		FrontPortalIntroductionsEntitys = frontPortalIntroductionsEntitys;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}
	
	@Transient
	public java.lang.String getNoHtmlContent() {
		String noHtmlContent = this.content.replaceAll("<[^>]*>","");
		if(noHtmlContent.length() > 80) {
			noHtmlContent = noHtmlContent.substring(0, 80) + "...";
		}
		return noHtmlContent;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类型
	 */
	@Column(name ="TYPE",nullable=true)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true, columnDefinition = "clob")
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
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
	 *@return: java.lang.String  上级栏目id
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级栏目id
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATEDATE",nullable=true,scale=6)
	public java.util.Date getUpdatedate(){
		return this.updatedate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdatedate(java.util.Date updatedate){
		this.updatedate = updatedate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATEBY",nullable=true,length=32)
	public java.lang.String getUpdateby(){
		return this.updateby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateby(java.lang.String updateby){
		this.updateby = updateby;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  栏目等级
	 */
	@Column(name ="INTRODUCTIONLEAVEL",nullable=true)
	public java.lang.Integer getIntroductionleavel(){
		return this.introductionleavel;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  栏目等级
	 */
	public void setIntroductionleavel(java.lang.Integer introductionleavel){
		this.introductionleavel = introductionleavel;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  显示类型
	 */
	@Column(name ="DISPLAYTYPE",nullable=true)
	public java.lang.Integer getDisplaytype(){
		return this.displaytype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  显示类型
	 */
	public void setDisplaytype(java.lang.Integer displaytype){
		this.displaytype = displaytype;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  列表来源
	 */
	@Column(name ="SOURCELIST",nullable=true)
	public java.lang.Integer getSourcelist(){
		return this.sourcelist;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  列表来源
	 */
	public void setSourcelist(java.lang.Integer sourcelist){
		this.sourcelist = sourcelist;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  列表显示类型
	 */
	@Column(name ="LISTDISPLAYTYPE",nullable=true)
	public java.lang.Integer getListdisplaytype(){
		return this.listdisplaytype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  列表显示类型
	 */
	public void setListdisplaytype(java.lang.Integer listdisplaytype){
		this.listdisplaytype = listdisplaytype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联条件
	 */
	@Column(name ="ASSOCIATECONDITION",nullable=true,length=32)
	public java.lang.String getAssociatecondition(){
		return this.associatecondition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联条件
	 */
	public void setAssociatecondition(java.lang.String associatecondition){
		this.associatecondition = associatecondition;
	}
}
