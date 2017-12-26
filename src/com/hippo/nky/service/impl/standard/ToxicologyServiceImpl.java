package com.hippo.nky.service.impl.standard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.standard.ToxicologyServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("toxicologyService")
@Transactional
public class ToxicologyServiceImpl extends CommonServiceImpl implements ToxicologyServiceI {
	
}