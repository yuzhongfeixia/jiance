package com.hippo.nky.service.sample;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.sample.TwoDimensionEntity;

public interface TwoDimensionServiceI extends CommonService {
	/**
	 * 取得当前项目二维码最大编号
	 * 
	 * @param midCode
	 * @return
	 */
	Long getMaxNum(String midCode);

	/**
	 * 打印当前生成二维码
	 * @param projectCode
	 * @param titles
	 * @return
	 */
	List<TwoDimensionEntity> findTwoListForPrint(String projectCode,
			String titles);

}
