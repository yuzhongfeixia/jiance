package com.hippo.nky.service.organization;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.organization.OrganizationEntity;

public interface OrganizationServiceI extends CommonService{
	public List<Map<String, Object>> exportExcelForOrganization(Map<String, Object> paramMap);
	
	public JSONObject getDatagrid(OrganizationEntity organization, DataGrid dataGrid);
}
