package com.hippo.nky.service.impl.standard;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.standard.PortalExpertEntity;
import com.hippo.nky.service.standard.PortalExpertServiceI;

@Service("portalExpertService")
@Transactional
public class PortalExpertServiceImpl extends CommonServiceImpl implements PortalExpertServiceI {

	@Override
	public <T> void save(T entity) {
		PortalExpertEntity pee = (PortalExpertEntity)entity;
		String hql = " select max(orderno) from  PortalExpertEntity ";
		int orderNo = this.singleResult(hql);
		pee.setOrderno(orderNo + 1);
		super.save(pee);
	}
	
}