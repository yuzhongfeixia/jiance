package com.hippo.nky.service.impl.person123;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.person123.Person123ServiceI;

@Service("person123Service")
@Transactional
public class Person123ServiceImpl extends CommonServiceImpl implements Person123ServiceI {
	
}