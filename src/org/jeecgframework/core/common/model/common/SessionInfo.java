package org.jeecgframework.core.common.model.common;

import java.io.Serializable;

import jeecg.system.pojo.base.TSUser;

public class SessionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSUser user;
	private String areaCode;

	public TSUser getUser() {
		return user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
