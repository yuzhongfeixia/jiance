package com.hippo.nky.service.impl.standard;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.standard.NkyPortalExpertServiceI;

@Service("nkyPortalExpertService")
@Transactional
public class NkyPortalExpertServiceImpl extends CommonServiceImpl implements NkyPortalExpertServiceI {
	
}