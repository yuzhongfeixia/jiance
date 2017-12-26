package com.hippo.nky.service.impl.standard;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.service.standard.AgrProductsServiceI;

@Service("agrProductsService")
@Transactional
public class AgrProductsServiceImpl extends CommonServiceImpl implements AgrProductsServiceI {

	@Override
	public boolean checkAgrCodeIsExists(String agrCode) {
		String sql = "SELECT * FROM ( SELECT T.AGRCODE FROM NKY_AGR_PRODUCTS T UNION ALL  SELECT T.CODE FROM NKY_AGR_CATEGORY T) A WHERE A.AGRCODE = ?";
		List list = this.getSession().createSQLQuery(sql).setParameter(0, agrCode).list();
		if(list != null){
			return list.size() > 0 ? true : false ;
		}
		return false;
	}
}