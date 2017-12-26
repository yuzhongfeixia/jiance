package com.hippo.nky.service.standard;

import org.jeecgframework.core.common.service.CommonService;

public interface AgrProductsServiceI extends CommonService{
	
	/**
	 * 农产品编码以及农产品分类编码重复性验证
	 */
	boolean checkAgrCodeIsExists(String agrCode);
}
