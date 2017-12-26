package com.hippo.nky.service.standard;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.PollInfoEntity;

public interface PollutantServiceI extends CommonService {
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
	public DataGrid getDataGridByHql(PollInfoEntity pollutant,
			DataGrid dataGrid, String pcid, String versionId);

}
