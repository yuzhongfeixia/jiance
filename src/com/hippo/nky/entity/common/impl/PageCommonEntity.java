package com.hippo.nky.entity.common.impl;

/**
 * 翻页接口类
 * 
 * @author xudl
 *
 */
public interface PageCommonEntity {
	/**
	 * 起始个数
	 */
	public int beginIndex = 0;

	/**
	 * 结束个数
	 */
	public int endIndex = 0;

	/**
	 * 取得起始个数
	 * 
	 * @return 起始个数
	 */
	public int getBeginIndex();

	/**
	 * 设置起始个数
	 * 
	 * @param beginIndex
	 *            起始个数
	 */
	public void setBeginIndex(int beginIndex);

	/**
	 * 取得结束个数
	 * 
	 * @return 结束个数
	 */
	public int getEndIndex();

	/**
	 * 设置结束个数
	 * 
	 * @param endIndex
	 *            结束个数
	 */
	public void setEndIndex(int endIndex);
}
