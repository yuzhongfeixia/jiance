package com.hippo.nky.service.impl.standard;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.standard.LimitStandardServiceI;

@Service("limitStandardService")
@Transactional
public class LimitStandardServiceImpl extends CommonServiceImpl implements LimitStandardServiceI {
	
}