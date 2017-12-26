package com.hippo.nky.service.impl.system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.system.SysAreaCodeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("sysAreaCodeService")
@Transactional
public class SysAreaCodeServiceImpl extends CommonServiceImpl implements SysAreaCodeServiceI {
	
}