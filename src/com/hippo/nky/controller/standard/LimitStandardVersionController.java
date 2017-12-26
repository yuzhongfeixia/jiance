package com.hippo.nky.controller.standard;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSFunction;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.service.SystemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.service.standard.LimitStandardVersionServiceI;

/**   
 * @Title: Controller
 * @Description: 限量标准版本
 * @author XuDL
 * @date 2013-08-07 13:58:31
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/limitStandardVersionController")
public class LimitStandardVersionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LimitStandardVersionController.class);

	@Autowired
	private LimitStandardVersionServiceI limitStandardVersionService;
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
	 * 限量标准版本列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "limitStandardVersion")
	public ModelAndView limitStandardVersion(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/limitStandardVersionList");
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
	public void datagrid(LimitStandardVersionEntity limitStandardVersion,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(LimitStandardVersionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, limitStandardVersion, request);
		this.limitStandardVersionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除限量标准版本
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LimitStandardVersionEntity limitStandardVersion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		limitStandardVersion = systemService.getEntity(LimitStandardVersionEntity.class, limitStandardVersion.getId());
		message = "删除成功";
		limitStandardVersionService.delete(limitStandardVersion);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加限量标准版本
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LimitStandardVersionEntity limitStandardVersion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean publish = Boolean.parseBoolean(request.getParameter("publish"));
		if(publish){
			limitStandardVersion.setPublishDate(new Date());
		}
		
		// 类型(编辑/复制)
		String type = request.getParameter("type");
		if (StringUtil.isNotEmpty(limitStandardVersion.getId())) {
			message = "更新成功";
			LimitStandardVersionEntity t = limitStandardVersionService.get(LimitStandardVersionEntity.class, limitStandardVersion.getId());
			try {				
				if("copy".equals(type)){
				message = "复制成功";
				// 复制限量标准
				limitStandardVersionService.copyRecordForLimitStandard(limitStandardVersion);
			}else{
				MyBeanUtils.copyBeanNotNull2Bean(limitStandardVersion, t);
				// 当中文名中写有空格时，点击详情的js会被截断，需要转成HTML的编码格式，这种格式保存时会变成chr(160)，正则的\s不能匹配，需要再做一次替换处理
				t.setNameZh(t.getNameZh().replaceAll("\\s", "&nbsp;").replace(String.valueOf((char)160), "&nbsp;"));
				
				//limitStandardVersionService.saveOrUpdate(t);
				limitStandardVersionService.limitStandardVersionUpdate(t);
			}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 设置启用停用默认值
			limitStandardVersion.setStopflag(0);
			// 设置发布标识默认值
			limitStandardVersion.setPublishflag(0);

			message = "添加成功";
			// 当中文名中写有空格时，点击详情的js会被截断，需要转成HTML的编码格式，这种格式保存时会变成chr(160)，正则的\s不能匹配，需要再做一次替换处理
			limitStandardVersion.setNameZh(limitStandardVersion.getNameZh().replace(" ", "&nbsp;").replace(String.valueOf((char)160), "&nbsp;"));
			limitStandardVersionService.save(limitStandardVersion);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 添加限量标准版本
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "copy")
	@ResponseBody
	public AjaxJson copy(LimitStandardVersionEntity limitStandardVersion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// 类型(复制)
		if (StringUtil.isNotEmpty(limitStandardVersion.getId())) {
//			message = "更新成功";
//			LimitStandardVersionEntity t = limitStandardVersionService.get(LimitStandardVersionEntity.class, limitStandardVersion.getId());
			try {				
	
				message = "复制成功";
				// 复制限量标准
				limitStandardVersionService.copyRecordForLimitStandard(limitStandardVersion);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return j;
	}

	/**
	 * 限量标准版本列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LimitStandardVersionEntity limitStandardVersion, HttpServletRequest req) {
		String flg = req.getParameter("flg");
		if (StringUtil.isNotEmpty(limitStandardVersion.getId())) {
			limitStandardVersion = limitStandardVersionService.getEntity(LimitStandardVersionEntity.class, limitStandardVersion.getId());
		} 
		req.setAttribute("type", req.getParameter("type"));
		req.setAttribute("limitStandardVersionPage", limitStandardVersion);
		if (StringUtils.equals(flg, "show")) {
			return new ModelAndView("com/hippo/nky/standard/limitStandardVersionForShow");
		}
		return new ModelAndView("com/hippo/nky/standard/limitStandardVersion");
	}

	/**
	 * 判断是否已经发布
	 * 
	 * @return
	 */
	@RequestMapping(params = "judgePublish")
	@ResponseBody
	public AjaxJson judgePublish(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = req.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			LimitStandardVersionEntity svEntity = new LimitStandardVersionEntity();
			svEntity.setId(id);
			svEntity = limitStandardVersionService.getEntity(LimitStandardVersionEntity.class, svEntity.getId());
			Integer publishMark = svEntity.getPublishflag();
			if(Constants.ONE.equals(publishMark)){
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 判断是否已经停用
	 * 
	 * @return
	 */
	@RequestMapping(params = "judgeStop")
	@ResponseBody
	public AjaxJson judgeStop(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = req.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			LimitStandardVersionEntity svEntity = new LimitStandardVersionEntity();
			svEntity.setId(id);
			svEntity = limitStandardVersionService.getEntity(LimitStandardVersionEntity.class, svEntity.getId());
			Integer stopFlag = svEntity.getStopflag();
			if(Constants.ONE.equals(stopFlag)){
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 重复性验证
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isExsitCheck")
	@ResponseBody
	public AjaxJson isExsitCheck(HttpServletRequest request) {
		AjaxJson j = null;
		String constraint = "STANDARD_CODE" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param");
		j = systemService.uniquenessCheck("NKY_LIMIT_STANDARD_VERSION", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("标准号已存在!");
		}
		return j;
	}
	
	/**
	 * 相同类别的标准发布次数限制
	 * 
	 * @return
	 */
	@RequestMapping(params = "sameTypePublishChk")
	@ResponseBody
	public AjaxJson sameTypePublishChk(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String standardType = req.getParameter("standardType");
		Integer publishflag = Integer.parseInt(req.getParameter("publishflag"));
		
		CriteriaQuery cq = new CriteriaQuery(LimitStandardVersionEntity.class);
		cq.eq("standardType", standardType);
		cq.eq("publishflag", publishflag);
		cq.add();
		
		List<LimitStandardVersionEntity> list = systemService.getListByCriteriaQuery(cq, false);
		if (list != null && list.size() == 1) {
			j.setSuccess(false);
		}
		return j;
	}
	
}
