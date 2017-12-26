package com.hippo.nky.service.impl.standard;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.AgrCategoryEntity;
import com.hippo.nky.service.standard.AgrCategoryServiceI;

@Service("agrCategoryService")
@Transactional
public class AgrCategoryServiceImpl extends CommonServiceImpl implements AgrCategoryServiceI {
	
	public static final String NAME_SPACE = "com.hippo.nky.entity.standard.AgrCategoryEntity.";
	
	/**
	 *农产品分类zTree数据
	 * @return
	 */
	public String agrCategoryTreeData(String versionId){
		List<AgrCategoryEntity> agrCategoryEntityList = this.findHql("from AgrCategoryEntity a where a.versionid = ?", versionId);
		StringBuilder stringB = new StringBuilder();
		stringB.append("[");
		if(agrCategoryEntityList!=null){
			for (int i = 0; i < agrCategoryEntityList.size(); i++) {
				AgrCategoryEntity agrCategoryEntity = agrCategoryEntityList.get(i);
				if (Constants.ROOT_ID.equals(agrCategoryEntity.getPid())) {
					stringB.append("{ id:\"" + agrCategoryEntity.getId()
								+ "\", pId:\"" + agrCategoryEntity.getPid()
								+ "\", code:\"" + agrCategoryEntity.getCode()
								+ "\", cname:\"" + agrCategoryEntity.getCname()
								+ "\", name:\"" + agrCategoryEntity.getCname()+"("+agrCategoryEntity.getCode()+")"
								+ "\", iconSkin:\"diy" + agrCategoryEntity.getAgrcategorytype()
								+ "\", open:true}");					
				} else {
					stringB.append("{ id:\"" + agrCategoryEntity.getId()
								+ "\", pId:\"" + agrCategoryEntity.getPid()
								+ "\", code:\"" + agrCategoryEntity.getCode()
								+ "\", cname:\"" + agrCategoryEntity.getCname()
								+ "\", name:\"" + agrCategoryEntity.getCname()+"("+agrCategoryEntity.getCode()+")"
								+ "\", iconSkin:\"diy" + agrCategoryEntity.getAgrcategorytype()
								+ "\"}");
				}
				if(!(i== agrCategoryEntityList.size()-1)){
					stringB.append(",");
				}
			}
		}
		stringB.append("]");
		return stringB.toString();
	}
	

	/**
	 * 检索数据
	 * 
	 * @param agrCategory
	 * @param dataGrid
	 * @param request
	 * @return
	 */
	public JSONObject getSearchData(AgrCategoryEntity agrCategory, DataGrid dataGrid,HttpServletRequest request) {
		
		if(agrCategory == null){
			return JSONObject.fromObject("{\'total\':0,\'rows\':[]}");
		}
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"searchData", agrCategory);
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","ID")
				,new Db2Page("cname", "CNAME", null)
				,new Db2Page("agrcategorytype","AGRCATEGORYTYPE",null)
				,new Db2Page("code","CODE",null)
		};
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, mapList.size(),dataGrid.getsEcho(), db2Pages);
		return jObject;
	}
	
	/**
	 * 递归删除节点与子节点
	 * @param Integer 节点id
	 * @return 删除操作状态
	 */
	public Integer delAgrCategoryTreeNode(String nodeId){
		return this.executeSql(" delete from nky_agr_category where id in ("
									+ "select id from nky_agr_category start with id = '" + nodeId + "' connect by prior id = pid"
									+")");
	}
}