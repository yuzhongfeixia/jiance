package com.hippo.nky.service.news;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.news.NewsEntity;

public interface NewsServiceI extends CommonService{
	public void saveOrUpdateImpl(NewsEntity news, HttpServletRequest request);
}
