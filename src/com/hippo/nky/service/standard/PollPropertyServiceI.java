package com.hippo.nky.service.standard;

import java.util.Map;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.PollPropertyEntity;

public interface PollPropertyServiceI extends CommonService{
	/**
	 * 禁用污染物性质数据列表取得
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollDisableDatagrid(PollPropertyEntity pollProperty, DataGrid dataGrid);
	
	
	/**
	 * 限用污染物性质数据列表取得
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollEnableDatagrid(PollPropertyEntity pollProperty, DataGrid dataGrid);

	/**
	 * 取得污染物列表
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollProducts(PollPropertyEntity pollProperty, DataGrid dataGrid);
	
	/**
	 * 保存信息
	 * @param paramsMap
	 * @return
	 */
	public int savePollDisableFlg(Map<String, Object> paramsMap);
}
