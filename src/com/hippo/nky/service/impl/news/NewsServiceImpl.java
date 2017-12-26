package com.hippo.nky.service.impl.news;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.news.NewsEntity;
import com.hippo.nky.entity.standard.PortalAttachmentEntity;
import com.hippo.nky.service.news.NewsServiceI;

@Service("newsService")
@Transactional
public class NewsServiceImpl extends CommonServiceImpl implements NewsServiceI {
	@Override
	public void saveOrUpdateImpl(NewsEntity news, HttpServletRequest request) {
		this.saveOrUpdate(news);
		
		//List<PortalAttachmentEntity> portalAttachmentList = this.findHql("from PortalAttachmentEntity", news.getId());

		// 删除选择删除的附件
		String delOldUploadId = request.getParameter("delOldUploadList");
		List<String> delIdList = ConverterUtil.getSplitList(delOldUploadId, ",");
		for(String delId : delIdList){
			this.deleteEntityById(PortalAttachmentEntity.class, delId);
		}
		// 添加新加入的附件
		String attachmentList = request.getParameter("attachmentList");
		List<String> attachment = ConverterUtil.getSplitList(attachmentList, ";");
		for(String content : attachment){
			List<String> contentList = ConverterUtil.getSplitList(content, "\\|");
			PortalAttachmentEntity portalAttachmentEntity = new PortalAttachmentEntity();
			portalAttachmentEntity.setAssociateid(news.getId());
			portalAttachmentEntity.setFilename(contentList.get(0));
			Integer size = Integer.parseInt(contentList.get(1))/1000;
			portalAttachmentEntity.setDescription(String.valueOf(size));
			portalAttachmentEntity.setUrl(contentList.get(2));
			portalAttachmentEntity.setUploadtime(new Date());			
			this.save(portalAttachmentEntity);
		}
		
	}
}