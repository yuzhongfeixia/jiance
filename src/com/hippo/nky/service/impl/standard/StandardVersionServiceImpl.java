package com.hippo.nky.service.impl.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.activemq.StandardLibraryDataSynchronization;
import com.hippo.nky.common.MessageCustomer;
import com.hippo.nky.common.MessageProductor;
import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.monitoring.MonitoringAreaCountEntity;
import com.hippo.nky.entity.standard.AgrCategoryEntity;
import com.hippo.nky.entity.standard.AgrCategoryForCopyEntity;
import com.hippo.nky.entity.standard.JudgeStandardEntity;
import com.hippo.nky.entity.standard.LimitStandardEntity;
import com.hippo.nky.entity.standard.PollCategoryEntity;
import com.hippo.nky.entity.standard.PollCategoryForCopyEntity;
import com.hippo.nky.entity.standard.PollInfoEntity;
import com.hippo.nky.entity.standard.PollProductsEntity;
import com.hippo.nky.entity.standard.StandardVersionEntity;
import com.hippo.nky.service.standard.StandardVersionServiceI;

@Service("standardVersionService")
@Transactional
public class StandardVersionServiceImpl extends CommonServiceImpl implements StandardVersionServiceI {

	/**
	 * 更新
	 * @param standardVersion
	 * @return
	 * @throws JMSException 
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void standardVersionUpdate(StandardVersionEntity t,boolean publish) throws JMSException{
		this.saveOrUpdate(t);
		// 发送版本
		MessageProductor productor =new MessageProductor();
		if(publish && t.getCategory() == 0){
			String hql1 = "from AgrCategoryEntity where 1 = 1 AND versionid = ? ";
		    List<MonitoringAreaCountEntity> list = this.findHql(hql1,t.getId());
			ArrayList modelList = new ArrayList();
		    modelList.add(t);
		    modelList.add(list);
			productor.sendMessage(modelList);
		}else if(publish && t.getCategory() == 1){
			String hql1 = "from PollCategoryEntity where 1 = 1 AND versionid = ? ";
		    List<PollCategoryEntity> list = this.findHql(hql1,t.getId());
		    String hql2 = "from PollProductsEntity where 1 = 1 AND versionid = ? ";
		    List<PollCategoryEntity> list2 = this.findHql(hql2,t.getId());
			ArrayList modelList = new ArrayList();
		    modelList.add(t);
		    modelList.add(list);
		    modelList.add(list2);
			productor.sendMessage(modelList);
		}else if(publish && t.getCategory() == 2){
			String hql1 = "from JudgeStandardEntity where 1 = 1 AND vid = ? ";
		    List<PollCategoryEntity> list = this.findHql(hql1,t.getId());
			ArrayList modelList = new ArrayList();
		    modelList.add(t);
		    modelList.add(list);
			productor.sendMessage(modelList);
		}
	}
	/**
	 * 复制版本操作 农产品分类
	 * @param standardVersion
	 * @return
	 * @throws Exception 
	 */
	public String copyRecordForAgrCategory(StandardVersionEntity standardVersion) throws Exception {
		// 复制版本数据
		String oldVersionId = standardVersion.getId();
		standardVersion.setId(null);
		standardVersion.setPublishmark(0);
		standardVersion.setStopflag(0);
		standardVersion.setCreater(null);
		standardVersion.setCreatedate(null);
		this.save(standardVersion);
		String newVersionId = standardVersion.getId();
		// 复制树

		// 创建复制树所需的ID,并生成新旧ID Map
		List<Object[]> oldAndNewIdlist = this
				.findListbySql("select substr(sys_guid(), 1, 32) as newid,id as oldid from nky_agr_category where versionid = '"
						+ oldVersionId + "' start with pid='"+Constants.ROOT_ID+"' connect by prior id=pid");
		Map<String, String> oldIdToNewIdMap = new HashMap<String, String>();
		for (Object[] arr : oldAndNewIdlist) {
			if (arr == null) {
				continue;
			}
			oldIdToNewIdMap.put(String.valueOf(arr[1]), String.valueOf(arr[0]));
		}
		oldIdToNewIdMap.put(Constants.ROOT_ID, Constants.ROOT_ID);

		// 复制树
		for (Object[] attr : oldAndNewIdlist) {
			if (attr == null) {
				continue;
			}
			AgrCategoryForCopyEntity newAgrCategoryEntity = new AgrCategoryForCopyEntity();
			AgrCategoryEntity oldAgrCategoryEntity = this.get(AgrCategoryEntity.class, String.valueOf(attr[1]));
			MyBeanUtils.copyBeanNotNull2Bean(oldAgrCategoryEntity, newAgrCategoryEntity);
			newAgrCategoryEntity.setId(oldIdToNewIdMap.get(oldAgrCategoryEntity.getId()));
			newAgrCategoryEntity.setCreatedate(null);
			newAgrCategoryEntity.setVersionid(newVersionId);
			newAgrCategoryEntity.setPid(oldIdToNewIdMap.get(oldAgrCategoryEntity.getPid()));
			this.save(newAgrCategoryEntity);
		}

//		// 复制农产品基础信息
//		List<AgrProductsEntity> agrProductsEntityList = this.findHql(
//				"from AgrProductsEntity where versionid = ?", oldVersionId);
//		for (AgrProductsEntity oldAgrProductsEntity : agrProductsEntityList) {
//			if (oldAgrProductsEntity == null) {
//				continue;
//			}
//			AgrProductsEntity newAgrProductsEntity = new AgrProductsEntity();
//			MyBeanUtils.copyBeanNotNull2Bean(oldAgrProductsEntity, newAgrProductsEntity);
//			newAgrProductsEntity.setId(null);
//			newAgrProductsEntity.setCreatedate(null);
//			newAgrProductsEntity.setCategoryid(oldIdToNewIdMap.get(oldAgrProductsEntity.getCategoryid()));
//			newAgrProductsEntity.setVersionid(newVersionId);
//			this.save(newAgrProductsEntity);
//		}
		
		return "success";
	}
	
	/**
	 * 复制版本操作 污染物分类
	 * @param standardVersion
	 * @return
	 * @throws Exception 
	 */
	public String copyRecordForAgrPoll(StandardVersionEntity standardVersion) throws Exception{
		// 复制版本数据
		String oldVersionId = standardVersion.getId();
		standardVersion.setId(null);
		standardVersion.setPublishmark(0);
		standardVersion.setStopflag(0);
		standardVersion.setCreater(null);
		standardVersion.setCreatedate(null);
		this.save(standardVersion);
		String newVersionId = standardVersion.getId();
		// 复制树

		// 创建复制树所需的ID,并生成新旧ID Map
		List<Object[]> oldAndNewIdlist = this
				.findListbySql("select substr(sys_guid(), 1, 32) as newid,id as oldid from nky_po_category where versionid = '"
						+ oldVersionId + "' start with pid='"+Constants.ROOT_ID+"' connect by prior id=pid");
		Map<String, String> oldIdToNewIdMap = new HashMap<String, String>();
		for (Object[] arr : oldAndNewIdlist) {
			if (arr == null) {
				continue;
			}
			oldIdToNewIdMap.put(String.valueOf(arr[1]), String.valueOf(arr[0]));
		}
		oldIdToNewIdMap.put(Constants.ROOT_ID, Constants.ROOT_ID);

		// 复制树
		for (Object[] attr : oldAndNewIdlist) {
			if (attr == null) {
				continue;
			}
			PollCategoryForCopyEntity newPollCategoryEntity = new PollCategoryForCopyEntity();
			PollCategoryEntity oldPollCategoryEntity = this.get(PollCategoryEntity.class, String.valueOf(attr[1]));
			MyBeanUtils.copyBeanNotNull2Bean(oldPollCategoryEntity, newPollCategoryEntity);
			newPollCategoryEntity.setId(oldIdToNewIdMap.get(oldPollCategoryEntity.getId()));
			newPollCategoryEntity.setCreatedate(null);
			newPollCategoryEntity.setVersionid(newVersionId);
			newPollCategoryEntity.setPid(oldIdToNewIdMap.get(oldPollCategoryEntity.getPid()));
			this.save(newPollCategoryEntity);
		}
		
		// 复制污染物基础信息
		List<PollProductsEntity> pollProductsEntityList = this.findHql(
				"from PollProductsEntity where versionid = ?", oldVersionId);
		for (PollProductsEntity oldPollProductsEntity : pollProductsEntityList) {
			if (oldPollProductsEntity == null) {
				continue;
			}
			PollProductsEntity newPollProductsEntity = new PollProductsEntity();
			MyBeanUtils.copyBeanNotNull2Bean(oldPollProductsEntity, newPollProductsEntity);
			newPollProductsEntity.setId(null);
			newPollProductsEntity.setCategoryid(oldIdToNewIdMap.get(oldPollProductsEntity.getCategoryid()));
			newPollProductsEntity.setVersionid(newVersionId);
			this.save(newPollProductsEntity);
		}
		
		return "success";
	}
	/**
	 * 复制判定标准
	 * @param standardVersion
	 * @throws Exception 
	 */
	public String copyRecordForjudgeStandard(StandardVersionEntity standardVersion) throws Exception{
		// 复制版本数据
		String oldVersionId = standardVersion.getId();
		standardVersion.setId(null);
		standardVersion.setPublishmark(0);
		standardVersion.setStopflag(0);
		standardVersion.setCreater(null);
		standardVersion.setCreatedate(null);
		this.save(standardVersion);
		String newVersionId = standardVersion.getId();
		
		// 复制判定标准信息
		List<JudgeStandardEntity> judgeStandardEntityList = this.findHql(
				"from JudgeStandardEntity where vid = ?", oldVersionId);
		for (JudgeStandardEntity oldJudgeStandardEntity : judgeStandardEntityList) {
			if (oldJudgeStandardEntity == null) {
				continue;
			}
			JudgeStandardEntity newJudgeStandardEntity = new JudgeStandardEntity();
			MyBeanUtils.copyBeanNotNull2Bean(oldJudgeStandardEntity, newJudgeStandardEntity);
			newJudgeStandardEntity.setId(null);
			newJudgeStandardEntity.setCreatedate(null);
			newJudgeStandardEntity.setVid(newVersionId);
			this.save(newJudgeStandardEntity);
		}
		return null;
	}
	
	/**
	 * 删除版本
	 * 
	 * @return
	 */
	public String delStandardVersion(StandardVersionEntity standardVersion){
		// 类别
		Integer cotegory = standardVersion.getCategory();
		if(cotegory == 0){
			// 删除农产品分类
			List<AgrCategoryEntity> agrCategoryEntityList = this.findHql(
					"from AgrCategoryEntity where versionid = ?", standardVersion.getId());
			this.deleteAllEntitie(agrCategoryEntityList);
		}else if(cotegory == 1){
			// 删除污染物分类
			List<PollCategoryEntity> pollCategoryEntityList = this.findHql(
					"from PollCategoryEntity where versionid = ?", standardVersion.getId());
			this.deleteAllEntitie(pollCategoryEntityList);
			// 删除污染物基础信息
			List<PollInfoEntity> pollInfoEntityList = this.findHql(
					"from PollProductsEntity where versionid = ?", standardVersion.getId());
			this.deleteAllEntitie(pollInfoEntityList);
		}else if(cotegory == 2){
			// 删除判定标准
			List<JudgeStandardEntity> judgeStandardEntityList = this.findHql(
					"from JudgeStandardEntity where vid = ?", standardVersion.getId());
			this.deleteAllEntitie(judgeStandardEntityList);
		}
		// 删除版本表记录
		this.delete(standardVersion);
		
		// delete
		return null;
	}
	
	/**
	 * 生成判定标准树的数据
	 * @param versionId
	 * @param type
	 * @return
	 */
	public String judgeStandardCreateTree(String versionId ,String type){
		
		String sql = "";
		if("agr".equals(type)){
			sql = "	select id, pid, name, type "+
			  "from (select id, pid, name, 'category' as type "+
			          "from nky_agr_category "+
			         "where versionid = '"+versionId+"' "+
			        "union "+
			        "select id, categoryid as pid, cname as name, 'info' as type "+
			          "from nky_agr_products "+
			         "where versionid = '"+versionId+"') "+
			 "start with pid = '"+Constants.ROOT_ID+"' "+
			"connect by prior id = pid ";
		}else if("poll".equals(type)){
			sql = "	select id, pid, name, type "+
			  "from (select id, pid, name, 'category' as type "+
			          "from nky_po_category "+
			         "where versionid = '"+versionId+"' "+
			        "union "+
			        "select id, categoryid as pid, cname as name, 'info' as type "+
			          "from nky_po_info "+
			         "where versionid = '"+versionId+"') "+
			 "start with pid = '"+Constants.ROOT_ID+"' "+
			"connect by prior id = pid ";
		}
		List<Object[]> treeList = this
				.findListbySql(sql);
		
		
		StringBuilder stringB = new StringBuilder();
		stringB.append("[");
		if(treeList!=null){
			for (int i = 0; i < treeList.size(); i++) {
				Object[] treeObj = treeList.get(i);
				if (Constants.ROOT_ID.equals(treeObj[1])) {
					stringB.append("{ id:\"" + treeObj[0]
								+ "\", pId:\"" + treeObj[1] 
								+ "\", name:\"" + treeObj[2]
								+ "\", noteType:\"" + treeObj[3]
								+ "\", open:true}");					
				} else {
					stringB.append("{ id:\"" + treeObj[0]
								+ "\", pId:\"" + treeObj[1]
								+ "\", name:\"" + treeObj[2]
								+ "\", noteType:\"" + treeObj[3]
								+ "\"}");
				}
				if(!(i== treeList.size()-1)){
					stringB.append(",");
				}
			}
		}
		stringB.append("]");
		return stringB.toString();
	}
	/**
	 * 生成判定标准数据
	 * @param standardVersion
	 * @return
	 */
	public String judgeStandardDataCreate(StandardVersionEntity standardVersion) {
		// 取得农产品
		List<AgrCategoryEntity> agrList = this.findHql("from AgrCategoryEntity where versionid = ? and agrcategorytype = ? ", standardVersion.getAgrVersionId(),2);
		// 取得污染物
		List<PollProductsEntity> pollList = this.findHql("from PollProductsEntity where versionid = ? ", standardVersion.getPollVersionId());
		// 取得限量标准
		List<LimitStandardEntity> limitList = this.findHql("from LimitStandardEntity where versionid = ? ", standardVersion.getLimitVersionId());
		// 取得判定标准
		List<Object[]> judgeList = this.findHql("select judge.value,judge.units,judge.valuefrom,poll.cas,agr.code from JudgeStandardEntity judge,AgrCategoryEntity agr,PollProductsEntity poll where judge.vid = ? and agr.id = judge.agrid and poll.id = judge.pollid ", standardVersion.getJudgeVersionId());
		
		// 引用map
		HashMap<String, LimitStandardEntity> yyMap = new HashMap<String,LimitStandardEntity>();  
		// 参照map
		HashMap<String, LimitStandardEntity> czMap = new HashMap<String,LimitStandardEntity>();
		// 限量标准map
		Map<String, Object[]> judgeMap = new HashMap<String,Object[]>();
				
		for(int i=0;i<limitList.size();i++){
			LimitStandardEntity limitStandardEntity = limitList.get(i);
			yyMap.put(limitStandardEntity.getCas()+"_"+limitStandardEntity.getPollnameZh(), limitStandardEntity);
			czMap.put(limitStandardEntity.getCas(), limitStandardEntity);
		}
		
		for(int i=0;i<judgeList.size();i++){
			Object[] judgeEntity = judgeList.get(i);
			judgeMap.put(ConverterUtil.toString(judgeEntity[3])+"_"+ConverterUtil.toString(judgeEntity[4]), judgeEntity);
		}
		
		// 保存版本
		this.save(standardVersion);
		
		List<JudgeStandardEntity> tempList = new ArrayList<JudgeStandardEntity>();
		// 性能检测-START
//		long startTime=System.currentTimeMillis(); 
		// 取得配置文件中的默认单位设置
		Integer sysUnits = ConverterUtil.toInteger(ResourceUtil.getConfigByName(Constants.PROPEERTIS_SYSTEMUNIT), 0);
		// 取得数据提交最大行
		int limitNum = Integer.valueOf(ResourceUtil.getConfigByName(Constants.IMPORT_COMMIT_LINE));
		
		for(int i=0;i<agrList.size();i++){
			AgrCategoryEntity agrObj = agrList.get(i);
			if(agrObj!=null){
				for(int j=0;j<pollList.size();j++){
					PollProductsEntity pollObj = pollList.get(j);
					if(pollList.get(j)!=null){
						JudgeStandardEntity judgeStandardEntity = new JudgeStandardEntity();
						judgeStandardEntity.setAgrid(agrObj.getId());
						judgeStandardEntity.setPollid(pollObj.getId());
						judgeStandardEntity.setVid(standardVersion.getId());
						judgeStandardEntity.setLid(standardVersion.getLimitVersionId());
						// 使用规定
						judgeStandardEntity.setStipulate(0);
						// 是否引用
						LimitStandardEntity yyLimitObj = yyMap.get(pollObj.getCas()+"_"+agrObj.getCname());
						if(yyLimitObj!=null){
							judgeStandardEntity.setValue(yyLimitObj.getMrl());
							judgeStandardEntity.setUnits(yyLimitObj.getUnit());
							judgeStandardEntity.setValuefrom(0);
						}else{
							LimitStandardEntity czLimitObj = czMap.get(pollObj.getCas());
							
							if(czLimitObj != null){
								judgeStandardEntity.setUnits(sysUnits);
								judgeStandardEntity.setValuefrom(1);
							}else{
								judgeStandardEntity.setUnits(sysUnits);
								judgeStandardEntity.setValuefrom(2);
							}
							
							Object[] judgeObj = judgeMap.get(pollObj.getCas()+"_"+agrObj.getCode());
							if(judgeObj!=null){
								judgeStandardEntity.setValue(ConverterUtil.toBigDecimal(judgeObj[0]));
								judgeStandardEntity.setUnits(ConverterUtil.toInteger(judgeObj[1]));
								judgeStandardEntity.setValuefrom(ConverterUtil.toInteger(judgeObj[2]));
							}
							
						}
//						this.save(JudgeStandardEntity);
						tempList.add(judgeStandardEntity);
						if (tempList.size() % limitNum == 0) {
							this.batchSaveByMaxNum(tempList);
							tempList.clear();
						}
					}
				}
			}
		}
		
		if (tempList.size() > 0) {
			this.batchSaveByMaxNum(tempList);
			tempList.clear();
		}
//		long endTime=System.currentTimeMillis();
//		System.out.println("Time： "+(endTime-startTime)+"ms");
		// 性能检测-END
		return null;
	}
	/**
	 * 标准库数据同步
	 * @param req
	 * @return
	 * @throws JMSException 
	 */
	@Override
	public int dataSynchronization(HttpServletRequest req) throws JMSException{
		MessageCustomer messageCustomer = new StandardLibraryDataSynchronization();
		messageCustomer.setCommonService(this);
		return messageCustomer.receiveMessage();
	}

}