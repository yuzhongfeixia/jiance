package com.hippo.nky.service.impl.standard;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.standard.PollInfoEntity;
import com.hippo.nky.service.standard.PollutantServiceI;

@Service("pollutantService")
@Transactional
public class PollutantServiceImpl extends CommonServiceImpl implements
		PollutantServiceI {

	/**
	 * 使用hql取得DataGrid的值
	 * 
	 * @param pollutant
	 *            污染物基础信息
	 * @param dataGrid
	 *            dataGird
	 * @param pcid
	 *            污染物分类ID
	 * @param versionId
	 *            版本ID
	 * @return 污染物基础信息dataGird
	 */
	@Override
	public DataGrid getDataGridByHql(PollInfoEntity pollutant,
			DataGrid dataGrid, String cid, String versionId) {
		String hql = "SELECT A FROM PollInfoEntity A , PollCategoryEntity B , PollInfoCategoryEntity C "
				+ " WHERE A.id = C.poid AND B.id = C.pcid AND C.pcid = ? AND C.vid = ? ORDER BY A.createdate desc";
		Object[] param = new Object[] { cid, versionId };
		return commonDao.getDataGridByHql(dataGrid, hql, param);
	}
}