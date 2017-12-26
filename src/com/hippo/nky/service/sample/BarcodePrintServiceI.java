package com.hippo.nky.service.sample;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.sample.BarcodePrintEntity;

public interface BarcodePrintServiceI extends CommonService{

	JSONObject getDatagrid(BarcodePrintEntity barcodePrint, String id,
			DataGrid dataGrid);

	Object findByCode(String dcode);

}
