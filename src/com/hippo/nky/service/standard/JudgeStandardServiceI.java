package com.hippo.nky.service.standard;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.JudgeStandardEntity;

public interface JudgeStandardServiceI extends CommonService{
	public JSONObject getDatagrid3(JudgeStandardEntity pageObj, DataGrid dataGrid);
}
