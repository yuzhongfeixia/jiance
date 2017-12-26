package com.hippo.nky.service.impl.standard;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.PollCategoryEntity;
import com.hippo.nky.service.standard.PollCategoryServiceI;

@Service("pollCategoryService")
@Transactional
public class PollCategoryServiceImpl extends CommonServiceImpl implements
		PollCategoryServiceI {
	/**
	 * 污染物分类zTree数据
	 * @param versionId 版本ID
	 * @return
	 */
	public String pollCategoryTreeData(String versionId) {
		List<PollCategoryEntity> pollCategoryEntityList = this.findHql(
				"from PollCategoryEntity t where t.versionid = ?", versionId);
		StringBuilder stringB = new StringBuilder();
		stringB.append("[");
		if (pollCategoryEntityList != null) {
			for (int i = 0; i < pollCategoryEntityList.size(); i++) {
				PollCategoryEntity pollCategoryEntity = pollCategoryEntityList
						.get(i);
				if (Constants.ROOT_ID.equals(pollCategoryEntity.getPid())) {
					stringB.append("{ id:\"" + pollCategoryEntity.getId()
							+ "\", pId:\"" + pollCategoryEntity.getPid()
							+ "\", name:\"" + pollCategoryEntity.getName()
							+ "\", cname:\"" + pollCategoryEntity.getName()+"("+pollCategoryEntity.getCode()+")"
							+ "\", open:true}");
				} else {
					stringB.append("{ id:\"" + pollCategoryEntity.getId()
							+ "\", pId:\"" + pollCategoryEntity.getPid()
							+ "\", name:\"" + pollCategoryEntity.getName()
							+ "\", cname:\"" + pollCategoryEntity.getName()+"("+pollCategoryEntity.getCode()+")"
							+ "\"}");
				}
				if (!(i == pollCategoryEntityList.size() - 1)) {
					stringB.append(",");
				}
			}
		}
		stringB.append("]");
		return stringB.toString();
	}

	/**
	 * 递归删除节点与子节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @param versionid
	 *            版本id
	 * @return 删除操作状态
	 */
	public Integer delPollCategoryTreeNode(String nodeId, String versionid) {
		this.executeSql(" delete from nky_poll_products t1 where EXISTS ("
		+ "select t2.id from nky_po_category t2 where t1.categoryid = t2.id and t2.versionid = '" + versionid
		+ "' start with t2.id = '" + nodeId + "' connect by prior t2.id = t2.pid" + ")");
		Integer	count = this.executeSql(" delete from nky_po_category t1 where EXISTS ("
					+ "select t2.id from nky_po_category t2 where t1.id = t2.id start with t2.id = '" + nodeId
					+ "' connect by prior t2.id = t2.pid" + ")");
		return count;
	}
}