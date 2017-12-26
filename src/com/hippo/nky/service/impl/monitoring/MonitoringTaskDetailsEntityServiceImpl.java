package com.hippo.nky.service.impl.monitoring;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.monitoring.MonitoringTaskDetailsEntityServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("monitoringTaskDetailsEntityService")
@Transactional
public class MonitoringTaskDetailsEntityServiceImpl extends CommonServiceImpl implements MonitoringTaskDetailsEntityServiceI {
	
}