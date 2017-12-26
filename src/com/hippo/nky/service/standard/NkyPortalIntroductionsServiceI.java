package com.hippo.nky.service.standard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.service.CommonService;

public interface NkyPortalIntroductionsServiceI extends CommonService{

	public void getTreeJson(HttpServletRequest request, HttpServletResponse response);
	//根据introductionleavel得到其子节点的栏目
	public void getTreeSon(HttpServletRequest request, HttpServletResponse response , int introductionleavel);
}
