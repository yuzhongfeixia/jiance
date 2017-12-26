package com.hippo.nky.service.impl.standard;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.standard.PollProductsServiceI;

@Service("pollProductsService")
@Transactional
public class PollProductsServiceImpl extends CommonServiceImpl implements PollProductsServiceI {
	
}