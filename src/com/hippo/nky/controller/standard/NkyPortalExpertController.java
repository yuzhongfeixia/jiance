package com.hippo.nky.controller.standard;
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

import com.hippo.nky.entity.standard.NkyPortalExpertEntity;
import com.hippo.nky.service.standard.NkyPortalExpertServiceI;

/**
 * @Title: Controller
 * @Description: 专家委员会
 * @author zhangdaihao
 * @date 2013-08-01 16:40:39
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/nkyPortalExpertController")
public class NkyPortalExpertController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NkyPortalExpertController.class);

	@Autowired
	private NkyPortalExpertServiceI nkyPortalExpertService;
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
	 * 专家委员会列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "nkyPortalExpert")
	public ModelAndView nkyPortalExpert(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/nkyPortalExpertList");
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
	public void datagrid(NkyPortalExpertEntity nkyPortalExpert, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(NkyPortalExpertEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, nkyPortalExpert);
		this.nkyPortalExpertService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除专家委员会
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NkyPortalExpertEntity nkyPortalExpert, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		nkyPortalExpert = systemService.getEntity(NkyPortalExpertEntity.class, nkyPortalExpert.getId());
		message = "删除成功";
		nkyPortalExpertService.delete(nkyPortalExpert);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加专家委员会
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NkyPortalExpertEntity nkyPortalExpert, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(nkyPortalExpert.getId())) {
			message = "更新成功";
			NkyPortalExpertEntity t = nkyPortalExpertService.get(NkyPortalExpertEntity.class, nkyPortalExpert.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(nkyPortalExpert, t);
				nkyPortalExpertService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			nkyPortalExpertService.save(nkyPortalExpert);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * 专家委员会列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NkyPortalExpertEntity nkyPortalExpert, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(nkyPortalExpert.getId())) {
			nkyPortalExpert = nkyPortalExpertService.getEntity(NkyPortalExpertEntity.class, nkyPortalExpert.getId());
			req.setAttribute("nkyPortalExpertPage", nkyPortalExpert);
		}
		return new ModelAndView("com/hippo/nky/standard/nkyPortalExpert");
	}

//	@RequestMapping(params = "getTreeJson")
//	public void getTreeJson(JeecgJdbcEntity jeecgJdbc, HttpServletRequest request, HttpServletResponse response,
//			DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class);
//		cq.isNull("pid");
//		cq.add();
//		String JsonTemp = "[";
//		List<NkyPortalIntroductionsEntity> entityList = systemService.getListByCriteriaQuery(cq, false);
//		for (int i = 0; i < entityList.size(); i++) {
//			NkyPortalIntroductionsEntity entity = entityList.get(i);
//			if (i > 0)
//				JsonTemp += ",";
//			if (entity.getPid() == null) {
//				JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"\",\"text\":\""
//						+ entity.getName() + "\"";
//			} else {
//				JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"" + entity.getPid() + "\",\"text\":\""
//						+ entity.getName() + "\"";
//			}
//			List<NkyPortalIntroductionsEntity> list = systemService.findByProperty(NkyPortalIntroductionsEntity.class,
//					"pid", entity.getId());
//			if (list.size() > 0) {
//				JsonTemp += ",\"children\":[";
//				int j;
//				for (j = 0; j < list.size(); j++) {
//					NkyPortalIntroductionsEntity e = list.get(j);
//					if (j > 0)
//						JsonTemp += ",";
//					if (e.getPid() == null) {
//						JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"\",\"text\":\""
//								+ entity.getName();
//					} else {
//						JsonTemp += "{ \"id\":\"" + e.getId() + "\",\"pid\":\"" + e.getPid() + "\",\"text\":\""
//								+ e.getName();
//					}
//				}
//				JsonTemp += "\"}";
//				if (j == list.size())
//					JsonTemp += "]";
//			}
//			JsonTemp += "}";
//
//		}
//		JsonTemp += "]";
//		//JsonTemp = "{\"id\":11,\"text\":\"Photos\",\"state\":\"closed\",\"children\":[{\"id\":111,\"text\":\"Friend\"},{\"id\":112,\"text\":\"Wife\"},{\"id\":113,\"text\":\"Company\"}]}";
//		//JSONObject jObject = JSONObject.fromObject(JsonTemp);
//		//DataUtils.responseDatagrid(response, jObject);
//		response.setContentType("application/json");
//		response.setHeader("Cache-Control", "no-store");
//		try {
//			PrintWriter pw=response.getWriter();
//			pw.write(JsonTemp.toString());
//			pw.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(JsonTemp);
//	}
}
