package com.hippo.nky.service.standard;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.StandardVersionEntity;

public interface StandardVersionServiceI extends CommonService{
	
	/**
	 * 更新
	 * @param t 
	 * @param publish 
	 * @param standardVersion
	 * @return
	 * @throws Exception 
	 */
	public void standardVersionUpdate(StandardVersionEntity t, boolean publish) throws JMSException;
	
	/**
	 * 复制版本操作 农产品分类
	 * @param standardVersion
	 * @return
	 * @throws Exception 
	 */
	public String copyRecordForAgrCategory(StandardVersionEntity standardVersion) throws Exception;
	
	/**
	 * 复制版本操作 污染物分类
	 * @param standardVersion
	 * @return
	 * @throws Exception 
	 */
	public String copyRecordForAgrPoll(StandardVersionEntity standardVersion) throws Exception;

	/**
	 * 复制判定标准
	 * @param standardVersion
	 * @throws Exception 
	 */
	public String copyRecordForjudgeStandard(StandardVersionEntity standardVersion) throws Exception;
	
	/**
	 * 删除版本
	 * 
	 * @return
	 */
	public String delStandardVersion(StandardVersionEntity standardVersion);
	
	/**
	 * 生成判定标准树的数据
	 * @param versionId
	 * @param type
	 * @return
	 */
	public String judgeStandardCreateTree(String versionId ,String type);
	
	/**
	 * 生成判定标准数据
	 * @param standardVersion
	 * @return
	 */
	public String judgeStandardDataCreate(StandardVersionEntity standardVersion);

	/**
	 * 标准库数据同步
	 * @param req
	 * @return
	 */
	public int dataSynchronization(HttpServletRequest req) throws JMSException;


}
