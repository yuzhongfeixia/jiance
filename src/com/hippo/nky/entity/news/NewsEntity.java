package com.hippo.nky.entity.news;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.StringUtil;

/**
 * @Title: Entity
 * @Description: 农科院新闻
 * @author zhangdaihao
 * @date 2013-07-16 16:42:51
 * @version V1.0
 * 
 */
@Entity
@Table(name = "NKY_PORTAL_NEWS", schema = "")
@SuppressWarnings("serial")
public class NewsEntity implements java.io.Serializable {
	/** 新闻编号 */
	private java.lang.String id;
	/** 新闻标题 */
	private java.lang.String title;
	/** 新闻类型 */
	private java.lang.Integer type;
	/** 新闻关键字 */
	private java.lang.String keywords;
	/** 内容 */
	private java.lang.String content;
	/** 创建时间 */
	private java.util.Date createdate;
	/** 创建人 */
	private java.lang.String createby;
	/** 排序 */
	private java.lang.String sort;
	/** 作者 */
	private java.lang.String author;
	/** 创建时间 */
	private java.util.Date updatedate;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 作者
	 */
	@Column(name = "AUTHOR", nullable = true, length = 32)
	public java.lang.String getAuthor() {
		return author;
	}

	public void setAuthor(java.lang.String author) {
		this.author = author;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 修改时间
	 */
	@Column(name = "UPDATEDATE", nullable = true, scale = 6)
	public java.util.Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(java.util.Date updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 排序
	 */
	@Column(name = "SORT", nullable = true)
	public java.lang.String getSort() {
		return sort;
	}

	public void setSort(java.lang.String sort) {
		this.sort = sort;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 新闻编号
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Transient
	public java.lang.String getNoHtmlContent() {
		if(ConverterUtil.isEmpty(this.content)){
			return "";
		}
		String noHtmlContent = this.content.replaceAll("<[^>]*>","");
		if(noHtmlContent.length() > 80) {
			noHtmlContent = noHtmlContent.substring(0, 80) + "...";
		}
		return noHtmlContent;
	}
	
	@Transient
	public java.lang.String getIndexNoHtmlContent() {
		if(ConverterUtil.isEmpty(this.content)){
			return "";
		}
		String indexNoHtmlContent = this.content.replaceAll("<[^>]*>","");
		if(indexNoHtmlContent.length() > 130) {
			indexNoHtmlContent = indexNoHtmlContent.substring(0, 130) + "...";
		}
		return indexNoHtmlContent;
	}
	
	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 新闻编号
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 新闻标题
	 */
	@Column(name = "TITLE", nullable = true, length = 128)
	public java.lang.String getTitle() {
		return this.title;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 新闻标题
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 新闻类型
	 */
	@Column(name = "TYPE", nullable = true)
	public java.lang.Integer getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 新闻类型
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 新闻关键字
	 */
	@Column(name = "KEYWORDS", nullable = true, length = 128)
	public java.lang.String getKeywords() {
		return this.keywords;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 新闻关键字
	 */
	public void setKeywords(java.lang.String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	// @Type(type="org.springframework.orm.hibernate3.support.ClobStringType")
	@Column(name = "CONTENT", nullable = true, columnDefinition = "clob")
	public java.lang.String getContent() {
		return content;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容
	 */
	public void setContent(java.lang.String content) {
		this.content = StringUtil.replaceBlank(content, "\t|\r|\n");
	}
	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATEDATE", nullable = true, scale = 6)
	public java.util.Date getCreatedate() {
		return this.createdate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATEBY", nullable = true, length = 32)
	public java.lang.String getCreateby() {
		return this.createby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人
	 */
	public void setCreateby(java.lang.String createby) {
		this.createby = createby;
	}
	

	public static String getSplitContent(String content,int count) {
		if(ConverterUtil.isEmpty(content)){
			return "";
		}
		String noHtmlContent = content.replaceAll("<[^>]*>","");
		if(noHtmlContent.length() > count) {
			noHtmlContent = noHtmlContent.substring(0, count) + "...";
		}
		return noHtmlContent;
	}
}
