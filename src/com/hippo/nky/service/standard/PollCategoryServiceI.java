package com.hippo.nky.service.standard;

import org.jeecgframework.core.common.service.CommonService;

public interface PollCategoryServiceI extends CommonService{
	/**
	 * 污染物分类zTree数据
	 * @param versionId 
	 * @return
	 */
	public String pollCategoryTreeData(String versionId);

	/**
	 * 递归删除污染物分类节点与子节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @param versionid
	 *            版本id
	 * @return 删除操作状态
	 */
	public Integer delPollCategoryTreeNode(String nodeId, String versionid);
}
