package com.hippo.nky.service.impl.standard;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.common.MessageProductor;
import com.hippo.nky.entity.standard.LimitStandardEntity;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.service.standard.LimitStandardVersionServiceI;

@Service("limitStandardVersionService")
@Transactional
public class LimitStandardVersionServiceImpl extends CommonServiceImpl implements LimitStandardVersionServiceI {
	/**
	 * 复制限量标准
	 * @param standardVersion
	 * @throws Exception 
	 */
	public String copyRecordForLimitStandard(LimitStandardVersionEntity limitStandardVersion) throws Exception{
		// 复制版本数据
		String oldVersionId = limitStandardVersion.getId();
		limitStandardVersion.setId(null);
		limitStandardVersion.setPublishflag(0);
		limitStandardVersion.setStopflag(0);
		limitStandardVersion.setCreater(null);
		limitStandardVersion.setCreatedate(null);
		this.save(limitStandardVersion);
		String newVersionId = limitStandardVersion.getId();
		
		// 复制限量标准信息
		List<LimitStandardEntity> limitStandardEntityList = this.findHql(
				"from LimitStandardEntity where versionid = ?", oldVersionId);
		for (LimitStandardEntity oldLimitStandardEntity : limitStandardEntityList) {
			if (oldLimitStandardEntity == null) {
				continue;
			}
			LimitStandardEntity newLimitStandardEntity = new LimitStandardEntity();
			MyBeanUtils.copyBeanNotNull2Bean(oldLimitStandardEntity, newLimitStandardEntity);
			newLimitStandardEntity.setId(null);
			newLimitStandardEntity.setCreatedate(null);
			newLimitStandardEntity.setCreater(null);
			newLimitStandardEntity.setVersionid(newVersionId);
			this.save(newLimitStandardEntity);
		}
		return null;
	}
	
	/**
	 * 更新
	 * @param t
	 * @throws JMSException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void limitStandardVersionUpdate(LimitStandardVersionEntity t) throws JMSException{
		this.saveOrUpdate(t);
		MessageProductor productor =new MessageProductor();
		if(t.getPublishflag() == 1){
			String hql1 = "from LimitStandardEntity where 1 = 1 AND versionid = ? ";
		    List<LimitStandardEntity> list = this.findHql(hql1,t.getId());
			ArrayList modelList = new ArrayList();
		    modelList.add(t);
		    modelList.add(list);
			productor.sendMessage(modelList);
		}
	}
}