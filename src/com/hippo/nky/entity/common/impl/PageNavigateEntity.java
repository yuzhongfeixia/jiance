package com.hippo.nky.entity.common.impl;

/**
 * 带有翻页属性的Entity
 * 
 * @author xudl
 * 
 */
public class PageNavigateEntity implements PageCommonEntity {

	/**
	 * 起始个数
	 */
	private int beginIndex = 0;

	/**
	 * 结束个数
	 */
	private int endIndex = 0;

	/**
	 * 取得起始个数
	 * 
	 * @return 起始个数
	 */
	@Override
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * 设置起始个数
	 * 
	 * @param beginIndex
	 *            起始个数
	 */
	@Override
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * 取得结束个数
	 * 
	 * @return 结束个数
	 */
	@Override
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * 设置结束个数
	 * 
	 * @param endIndex
	 *            结束个数
	 */
	@Override
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

}
