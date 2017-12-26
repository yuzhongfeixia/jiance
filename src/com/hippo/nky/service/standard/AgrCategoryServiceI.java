package com.hippo.nky.service.standard;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.standard.AgrCategoryEntity;

public interface AgrCategoryServiceI extends CommonService{
	/**
	 *农产品分类zTree数据
	 * @param versionId 
	 * @return
	 */
	public String agrCategoryTreeData(String versionId);
	
	/**
	 * 检索数据
	 * 
	 * @param agrCategory
	 * @param dataGrid
	 * @param request
	 * @return
	 */
	public JSONObject getSearchData(AgrCategoryEntity agrCategory, DataGrid dataGrid,HttpServletRequest request);
	
	/**
	 * 递归删除节点与子节点
	 * @param Integer 节点id
	 * @return 删除操作状态
	 */
	public Integer delAgrCategoryTreeNode(String nodeId);
}
