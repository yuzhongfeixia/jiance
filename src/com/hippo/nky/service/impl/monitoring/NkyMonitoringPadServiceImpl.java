package com.hippo.nky.service.impl.monitoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.service.monitoring.NkyMonitoringPadServiceI;

@Service("nkyMonitoringPadService")
@Transactional
public class NkyMonitoringPadServiceImpl extends CommonServiceImpl implements NkyMonitoringPadServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity.";
	/**
	 *取得页面datatable数据
	 * @param pageObj
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getDatagrid(NkyMonitoringPadEntity nkyMonitoringPad, DataGrid dataGrid){

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		if (org != null) {// 管理部门登录，则为空
			selCodition.put("samplingOrgCode", org.getCode());
		}
		
		
		if (StringUtils.isNotEmpty(nkyMonitoringPad.getUsername())) {
			selCodition.put("username", nkyMonitoringPad.getUsername());
		}
		if (StringUtils.isNotEmpty(nkyMonitoringPad.getOrgName())) {
			selCodition.put("orgName", nkyMonitoringPad.getOrgName());
		}
		

//		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getPadInfoCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getPadInfo", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("username")
				,new Db2Page("ogrname")
		};
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	// 设置查询的开始位置和结尾位置
	public void setBeginAndEnd(DataGrid dataGrid ,Map<String,Object> selCodition){
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page-1)*rows;
		int endIndex = beginIndex+rows;
		selCodition.put("beginIndex", beginIndex);
		selCodition.put("endIndex", endIndex);	
	}
}