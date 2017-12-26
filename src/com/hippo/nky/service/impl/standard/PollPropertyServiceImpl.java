package com.hippo.nky.service.impl.standard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.standard.PollPropertyEntity;
import com.hippo.nky.service.standard.PollPropertyServiceI;

@Service("pollPropertyService")
@Transactional
public class PollPropertyServiceImpl extends CommonServiceImpl implements PollPropertyServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.standard.PollPropertyEntity.";
	
	/**
	 * 禁用污染物性质数据列表取得
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollDisableDatagrid(PollPropertyEntity pollProperty, DataGrid dataGrid) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("disableFlg", 0);
		selCodition.put("cas", pollProperty.getCas());
		selCodition.put("pollname", pollProperty.getPollname());
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getPollDisableDataCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getPollDisableData", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("cas")
				,new Db2Page("cname")
				,new Db2Page("rn")
		};
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	/**
	 * 限用污染物性质数据列表取得
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollEnableDatagrid(PollPropertyEntity pollProperty, DataGrid dataGrid) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("disableFlg", 1);
		selCodition.put("cas", pollProperty.getCas());
		selCodition.put("pollname", pollProperty.getPollname());
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getPollDisableDataCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getPollDisableData", selCodition);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("cas")
				,new Db2Page("cname")
				,new Db2Page("rn")
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
	
	
	/**
	 * 取得污染物列表
	 * @param pollProperty
	 * @param dataGrid
	 * @return
	 */
	public JSONObject getPollProducts(PollPropertyEntity pollProperty, DataGrid dataGrid){
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("disableFlg", pollProperty.getDisableFlg());
		selCodition.put("cas", pollProperty.getCas());
		selCodition.put("popcname", pollProperty.getPopcname());
		selCodition.put("popename", pollProperty.getPopename());
		setBeginAndEnd(dataGrid, selCodition);

		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getPollProdutsDataCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getPollProdutsData", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("cas")
				,new Db2Page("cname")
				,new Db2Page("ename")
		};
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	
	/**
	 * 保存信息
	 * @param paramsMap
	 * @return
	 */
	public int savePollDisableFlg(Map<String, Object> paramsMap) {
		int updRes = 0;
		String[] casArr = (String[])paramsMap.get("casArr");
		Integer disableFlg = (Integer)paramsMap.get("disableFlg");
		
		PollPropertyEntity ppe = null;
		for (String cas : casArr) {
			//如果存在,则更新
			ppe = checkRecord(cas);
			if (ppe != null) {
				ppe.setDisableFlg(disableFlg);
				this.saveOrUpdate(ppe);
			} else {
				ppe = new PollPropertyEntity();
				ppe.setCas(cas);
				ppe.setDisableFlg(disableFlg);
				
				this.save(ppe);
			}
			updRes++;
		}
	    return updRes;
	}
	
	private PollPropertyEntity checkRecord(String cas) {
		List<PollPropertyEntity> ppList = this.findByProperty(PollPropertyEntity.class, "cas", cas);
		if (ppList != null && ppList.size() > 0) {
			return ppList.get(0);
		}
		return null;
	}
}