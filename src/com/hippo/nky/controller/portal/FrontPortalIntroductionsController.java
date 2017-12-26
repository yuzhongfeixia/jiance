package com.hippo.nky.controller.portal;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.portal.FrontPortalIntroductionsEntity;
import com.hippo.nky.entity.standard.PortalAttachmentEntity;
import com.hippo.nky.service.news.NewsServiceI;
import com.hippo.nky.service.portal.FrontPortalIntroductionsServiceI;

/**   
 * @Title: Controller
 * @Description: 栏目管理
 * @author zhangdaihao
 * @date 2013-08-06 16:12:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/frontPortalIntroductionsController")
public class FrontPortalIntroductionsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FrontPortalIntroductionsController.class);

	@Autowired
	private FrontPortalIntroductionsServiceI frontPortalIntroductionsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private NewsServiceI newsService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 栏目管理列表 页面跳转
	 * 
	 * @return
	 */	
	@RequestMapping(params = "frontPortalIntroductions")
	public ModelAndView frontPortalIntroductions(HttpServletRequest request) {
		frontPortalIntroductionsService.sortIndex(request);
		return new ModelAndView("com/hippo/nky/portal/index");
	}
	
	@RequestMapping(params = "test")
	public ModelAndView test(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/portal/search_result");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(FrontPortalIntroductionsEntity frontPortalIntroductions,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(FrontPortalIntroductionsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, frontPortalIntroductions);
		this.frontPortalIntroductionsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除栏目管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(FrontPortalIntroductionsEntity frontPortalIntroductions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		frontPortalIntroductions = systemService.getEntity(FrontPortalIntroductionsEntity.class, frontPortalIntroductions.getId());
		message = "删除成功";
		frontPortalIntroductionsService.delete(frontPortalIntroductions);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加栏目管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(FrontPortalIntroductionsEntity frontPortalIntroductions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(frontPortalIntroductions.getId())) {
			message = "更新成功";
			FrontPortalIntroductionsEntity t = frontPortalIntroductionsService.get(FrontPortalIntroductionsEntity.class, frontPortalIntroductions.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(frontPortalIntroductions, t);
				frontPortalIntroductionsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			frontPortalIntroductionsService.save(frontPortalIntroductions);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 栏目管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(FrontPortalIntroductionsEntity entity, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		List<FrontPortalIntroductionsEntity> entitylist = systemService.getList(FrontPortalIntroductionsEntity.class);
		req.setAttribute("flist", entitylist);
		if (functionid != null) {
			entity = systemService.getEntity(FrontPortalIntroductionsEntity.class, functionid);
		}
		return new ModelAndView("com/hippo/nky/standard/introduction");
	}

	/**
	 * 前台栏目页面跳转
	 * 
	 * @return
	 */
	
	@RequestMapping(params = "prepareData")
	public ModelAndView prepareData(FrontPortalIntroductionsEntity entity, HttpServletRequest req) {
		entity = frontPortalIntroductionsService.getEntity(FrontPortalIntroductionsEntity.class, entity.getId());
		frontPortalIntroductionsService.prepareData(entity, req);
		 if(entity.getListdisplaytype()==0){
			//内容+图片链接
			 return new ModelAndView("com/hippo/nky/portal/portal");
		}else if(entity.getListdisplaytype()==1){
			// 显示类型：子栏目列表
			return new ModelAndView("com/hippo/nky/portal/portal");
		}else if(entity.getListdisplaytype()==2){
			// 新闻列表
			return new ModelAndView("com/hippo/nky/portal/newslist");
		}else if (entity.getListdisplaytype()==3){
			int associationcondition = Integer.parseInt(entity.getAssociatecondition());
			if(associationcondition==1){
				//部级质检中心
				return new ModelAndView("com/hippo/nky/portal/quality_inspection_center");
			}else if(associationcondition==2){
				//风险评估实验室
				return new ModelAndView("com/hippo/nky/portal/risk_assessment_laboratory");
			}
		}else if (entity.getListdisplaytype()==4){
			//委员会委员 列表
			return new ModelAndView("com/hippo/nky/portal/council");
		}else if (entity.getListdisplaytype()==5){
			// 标准模块  列表
			if(entity.getAssociatecondition().equals("0")){
				return new ModelAndView("com/hippo/nky/portal/agrproducts");
			}else if (entity.getAssociatecondition().equals("1")){
				return new ModelAndView("com/hippo/nky/portal/pollproducts");
			}else if(entity.getAssociatecondition().equals("2")){
				return new ModelAndView("com/hippo/nky/portal/limitstandard");
			}
		}	
		return null;
	}
	
	
	/**
	 * 跳转到标准详情页
	 * 
	 * @return
	 */
	@RequestMapping(params="toStandardDetail")
	public ModelAndView toStandardDetail(HttpServletRequest req) {
		FrontPortalIntroductionsEntity entity = frontPortalIntroductionsService.getEntity(FrontPortalIntroductionsEntity.class, req.getParameter("lmid"));
		frontPortalIntroductionsService.toStandardDetail(req);
		if(entity.getAssociatecondition().equals("0")){
			return new ModelAndView("com/hippo/nky/portal/agr_detail");
		}else if (entity.getAssociatecondition().equals("1")){
			return new ModelAndView("com/hippo/nky/portal/poll_detail");
		}else if(entity.getAssociatecondition().equals("2")){
			return new ModelAndView("com/hippo/nky/portal/limitstandard_detail");
		}
		return new ModelAndView("com/hippo/nky/portal/newscontent");
	}
	
	/**
	 * 跳转到新闻详情页
	 * 
	 * @return
	 */
	@RequestMapping(params="toANews")
	public ModelAndView toANews(HttpServletRequest req) {
		String lmid = req.getParameter("lmid");
		frontPortalIntroductionsService.toANews(lmid , req.getParameter("id") ,req);
		List<PortalAttachmentEntity> portalAttachmentList = newsService.findHql("from PortalAttachmentEntity where associateid = ? ", req.getParameter("id"));
		req.setAttribute("portalAttachmentList", portalAttachmentList);
		return new ModelAndView("com/hippo/nky/portal/newscontent");
	}
	
	/**
	 * 新闻附件下载
	 * 
	 * @return
	 */
	@RequestMapping(params = "attachmentDownload")
	public void attachmentDownload(HttpServletRequest request, HttpServletResponse response){
		PortalAttachmentEntity portalAttachmentEntity = newsService.getEntity(PortalAttachmentEntity.class, request.getParameter("id"));
		if(portalAttachmentEntity == null){
			return;
		}
		response.setContentType("UTF-8");
		response.setCharacterEncoding("UTF-8");
		BufferedOutputStream bos = null;
		FileInputStream  bis = null;
		long fileLength = 0;
		String basePath = request.getSession().getServletContext().getRealPath("");
		String path = portalAttachmentEntity.getUrl();
		File downloadFile = new File(basePath+ path.replace("/", "\\"));
		
		try {
			bis = new FileInputStream(downloadFile);
			fileLength = downloadFile.length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((portalAttachmentEntity.getFilename()).getBytes("GBK"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	
	}
	
	/**
	 * 分页显示
	 * 
	 * @return
	 */
	@RequestMapping(params = "splitPage")
	@ResponseBody
	public AjaxJson splitPage(FrontPortalIntroductionsEntity entity,HttpServletRequest req, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		entity = frontPortalIntroductionsService.getEntity(FrontPortalIntroductionsEntity.class, entity.getId());
		List<Map<String, Object>> result = frontPortalIntroductionsService.splitPage(entity,req,dataGrid);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("resultMap", result);
		j.setAttributes(attributes);
		return j;
	}
	
	/**
	 * 搜索页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "searchData")
	public ModelAndView searchData(HttpServletRequest req){
		frontPortalIntroductionsService.searchData(req);
		return new ModelAndView("com/hippo/nky/portal/search_result");
	}
	
	
	
}
