package com.hippo.nky.service.standard;

import javax.jms.JMSException;

import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.LimitStandardVersionEntity;

public interface LimitStandardVersionServiceI extends CommonService{
	/**
	 * 复制限量标准
	 * @param standardVersion
	 * @throws Exception 
	 */
	public String copyRecordForLimitStandard(LimitStandardVersionEntity limitStandardVersionEntity) throws Exception;

	
	/**
	 * 更新
	 * @param t
	 */
	public void limitStandardVersionUpdate(LimitStandardVersionEntity t) throws JMSException;
}
