package com.hippo.nky.service.monitoring;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;

public interface NkyMonitoringPadServiceI extends CommonService{
	/**
	 *取得页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDatagrid(NkyMonitoringPadEntity nkyMonitoringPad, DataGrid dataGrid);

}
