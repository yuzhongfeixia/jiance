package com.hippo.nky.controller.monitoring;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.service.monitoring.NkyMonitoringPadServiceI;

/**   
 * @Title: Controller
 * @Description: 客户端(PAD)
 * @date 2013-10-18 16:28:48
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/nkyMonitoringPadController")
public class NkyMonitoringPadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NkyMonitoringPadController.class);

	@Autowired
	private NkyMonitoringPadServiceI nkyMonitoringPadService;
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
	 * 客户端(PAD)列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "nkyMonitoringPad")
	public ModelAndView nkyMonitoringPad(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/monitoring/nkyMonitoringPadList");
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
	public void datagrid(NkyMonitoringPadEntity nkyMonitoringPad,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(NkyMonitoringPadEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, nkyMonitoringPad, request.getParameterMap());
//		this.nkyMonitoringPadService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		
		JSONObject jObject = nkyMonitoringPadService.getDatagrid(nkyMonitoringPad, dataGrid);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除客户端(PAD)
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NkyMonitoringPadEntity nkyMonitoringPad, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		nkyMonitoringPad = systemService.getEntity(NkyMonitoringPadEntity.class, nkyMonitoringPad.getId());
		message = "客户端(PAD)删除成功";
		nkyMonitoringPadService.delete(nkyMonitoringPad);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 *密码重置(PAD)
	 * 
	 * @return
	 */
	@RequestMapping(params = "pwdreset")
	@ResponseBody
	public AjaxJson pwdreset(NkyMonitoringPadEntity nkyMonitoringPad, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		nkyMonitoringPad = systemService.getEntity(NkyMonitoringPadEntity.class, nkyMonitoringPad.getId());
		//设置成初始密码123456
		String initPass = PasswordUtil.encrypt(nkyMonitoringPad.getUsername(), "123456", PasswordUtil.getStaticSalt());
		nkyMonitoringPad.setPassword(initPass);
		message = "密码重置成功";
		nkyMonitoringPadService.saveOrUpdate(nkyMonitoringPad);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加客户端(PAD)
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NkyMonitoringPadEntity nkyMonitoringPad, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(nkyMonitoringPad.getId())) {
			message = "客户端(PAD)更新成功";
			NkyMonitoringPadEntity t = nkyMonitoringPadService.get(NkyMonitoringPadEntity.class, nkyMonitoringPad.getId());
			boolean modifyPwdFlg = false;
			// 为密码加密
			if (!nkyMonitoringPad.getPassword().equals(t.getPassword())) {
				modifyPwdFlg = true;
				//nkyMonitoringPad.setPassword(PasswordUtil.encrypt(t.getUsername(), nkyMonitoringPad.getPassword(), PasswordUtil.getStaticSalt()));
			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(nkyMonitoringPad, t);
				if (modifyPwdFlg) {
					t.setPassword(PasswordUtil.encrypt(t.getUsername(), t.getPassword(), PasswordUtil.getStaticSalt()));
				}
				nkyMonitoringPadService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "客户端(PAD)更新失败";
			}
		} else {
			message = "客户端(PAD)添加成功";
			// 为密码加密
			nkyMonitoringPad.setPassword(PasswordUtil.encrypt(nkyMonitoringPad.getUsername(),
					nkyMonitoringPad.getPassword(), PasswordUtil.getStaticSalt()));
			nkyMonitoringPadService.save(nkyMonitoringPad);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	

	/**
	 * 客户端(PAD)列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NkyMonitoringPadEntity nkyMonitoringPad, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(nkyMonitoringPad.getId())) {
			nkyMonitoringPad = nkyMonitoringPadService.getEntity(NkyMonitoringPadEntity.class, nkyMonitoringPad.getId());
			List<OrganizationEntity> org = systemService.findByProperty(OrganizationEntity.class, "code", nkyMonitoringPad.getOrgCode());
			req.setAttribute("orgname", org.get(0).getOgrname());
			req.setAttribute("nkyMonitoringPad", nkyMonitoringPad);
		}
		return new ModelAndView("com/hippo/nky/monitoring/nkyMonitoringPad");
	}
	
	/**
	 * 选择机构列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "selectorg")
	public ModelAndView selectorg(HttpServletRequest req) {
		return new ModelAndView("com/hippo/nky/monitoring/nkyMonitoringPad_sel");
	}

	
	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		String constraint = "USERNAME" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param");
		j = systemService.uniquenessCheck("NKY_MONITORING_PAD", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("用户名已存在!");
		}
		return j;
	}
}
