package com.hippo.nky.controller.news;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.news.NewsEntity;
import com.hippo.nky.entity.standard.PortalAttachmentEntity;
import com.hippo.nky.service.news.NewsServiceI;

/**   
 * @Title: Controller
 * @Description: 农科院新闻
 * @author zhangdaihao
 * @date 2013-07-16 17:19:45
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/newsController")
public class NewsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NewsController.class);

	@Autowired
	private NewsServiceI newsService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 农科院新闻列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "news")
	public ModelAndView news(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/news/newsList");
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
	public void datagrid(NewsEntity news,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(NewsEntity.class, dataGrid);
		//查询条件组装器
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("updatedate", SortDirection.desc);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, news);
		this.newsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除农科院新闻
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NewsEntity news, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		news = systemService.getEntity(NewsEntity.class, news.getId());
		message = "删除成功";
		newsService.delete(news);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加农科院新闻
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NewsEntity news, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(news.getId())) {
			message = "更新成功";
			NewsEntity t = newsService.get(NewsEntity.class, news.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(news, t);
				newsService.saveOrUpdateImpl(t,request);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			newsService.save(news);
			news.setUpdatedate(news.getCreatedate());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 农科院新闻列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NewsEntity news, HttpServletRequest req) {
		List<PortalAttachmentEntity> portalAttachmentList = null;
		if (StringUtil.isNotEmpty(news.getId())) {
			news = newsService.getEntity(NewsEntity.class, news.getId());
			portalAttachmentList = newsService.findHql("from PortalAttachmentEntity where associateid = ? ", news.getId());
		}else{
			portalAttachmentList = new ArrayList<PortalAttachmentEntity>();
		}
		req.setAttribute("portalAttachmentList", portalAttachmentList);
		req.setAttribute("newsPage", news);
		return new ModelAndView("com/hippo/nky/news/news");
	}
	
	/**
	 * 农科院新闻列表页面跳转
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
}
