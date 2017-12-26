package com.hippo.nky.service.impl.industry;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.industry.IndustryServiceI;

@Service("industryService")
@Transactional
public class IndustryServiceImpl extends CommonServiceImpl implements IndustryServiceI {
	
}