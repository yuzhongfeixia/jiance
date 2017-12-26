package com.hippo.nky.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 二维码管理
 * @author nky
 * @date 2013-11-07 13:54:32
 * @version V1.0
 * 
 */
@Entity
@Table(name = "NKY_SAMPLE_TWODIMENSION", schema = "")
@SuppressWarnings("serial")
public class TwoDimensionEntity implements java.io.Serializable {
	/** 二维码主键 */
	private java.lang.String id;
	/** 二维码标题 */
	private java.lang.String title;
	/** 二维码图片路径 */
	private java.lang.String realpath;
	/** 监测项目主键 */
	private java.lang.String projectCode;
	/** 起始随机数 */
	private java.lang.Integer randNo;
	/** 部门地区code + year*/
	private java.lang.String mdCode;
	/** 编号 */
	private java.lang.String serialNo;
	/** 创建时间 */
	private java.util.Date createDate;
	/** 创建人 */
	private java.lang.String createBy;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码主键
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码标题
	 */
	@Column(name = "TITLE", nullable = true, length = 100)
	public java.lang.String getTitle() {
		return this.title;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码标题
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码图片路径
	 */
	@Column(name = "REALPATH", nullable = true, length = 100)
	public java.lang.String getRealpath() {
		return this.realpath;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码图片路径
	 */
	public void setRealpath(java.lang.String realpath) {
		this.realpath = realpath;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 监测项目主键
	 */
	@Column(name = "PROJECT_CODE", nullable = true, length = 32)
	public java.lang.String getProjectCode() {
		return projectCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 监测项目主键
	 */
	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_DATE", nullable = true)
	public java.util.Date getCreatedate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 32)
	public java.lang.String getCreateBy() {
		return createBy;
	}
	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	@Column(name = "RANDNO", nullable = true, length = 1)
	public java.lang.Integer getRandNo() {
		return randNo;
	}

	public void setRandNo(java.lang.Integer randNo) {
		this.randNo = randNo;
	}
	@Column(name = "MDCODE", nullable = true, length = 6)
	public java.lang.String getMdCode() {
		return mdCode;
	}

	public void setMdCode(java.lang.String mdCode) {
		this.mdCode = mdCode;
	}
	@Column(name = "SERIALNO", nullable = true, length = 6)
	public java.lang.String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(java.lang.String serialNo) {
		this.serialNo = serialNo;
	}
	
}
