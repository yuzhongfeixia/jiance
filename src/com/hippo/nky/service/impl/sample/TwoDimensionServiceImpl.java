package com.hippo.nky.service.impl.sample;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.sample.TwoDimensionEntity;
import com.hippo.nky.service.sample.TwoDimensionServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("twoDimensionService")
@Transactional
public class TwoDimensionServiceImpl extends CommonServiceImpl implements
		TwoDimensionServiceI {

	@Override
	public Long getMaxNum(String midCode) {
		String hql = "SELECT MAX(NST.SERIALNO) FROM NKY_SAMPLE_TWODIMENSION NST WHERE NST.MDCODE = '"
				+ midCode + "'";
		return this.getCountForJdbc(hql);
	}

	@Override
	public List<TwoDimensionEntity> findTwoListForPrint(String projectCode,
			String titles) {
		if (titles.length() > 0) {
			titles = titles.substring(0, titles.length()-1);
			titles = "'" + titles + "'";
			titles = titles.replaceAll(",", "','");
		}
		String hql = "FROM TwoDimensionEntity WHERE projectCode = '"
				+ projectCode + "' AND title IN(" + titles + ")";
		return this.findByQueryString(hql);
	}
}