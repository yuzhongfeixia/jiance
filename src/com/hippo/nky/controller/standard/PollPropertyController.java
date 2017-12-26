package com.hippo.nky.controller.standard;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.standard.PollPropertyEntity;
import com.hippo.nky.service.standard.PollPropertyServiceI;

/**   
 * @Title: Controller
 * @Description: 污染物性质
 * @author nky
 * @date 2013-12-02 13:59:07
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pollPropertyController")
public class PollPropertyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PollPropertyController.class);

	@Autowired
	private PollPropertyServiceI pollPropertyService;
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
	 * 污染物性质列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollProperty")
	public ModelAndView pollProperty(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/pollPropertyList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "pollDisableDatagrid")
	public void pollDisableDatagrid(PollPropertyEntity pollProperty,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		JSONObject jObject = pollPropertyService.getPollDisableDatagrid(pollProperty, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "pollEnableDatagrid")
	public void pollEnableDatagrid(PollPropertyEntity pollProperty,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		JSONObject jObject = pollPropertyService.getPollEnableDatagrid(pollProperty, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	

	@RequestMapping(params = "getPollProducts")
	public void getPollProducts(PollPropertyEntity pollProperty,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		JSONObject jObject = pollPropertyService.getPollProducts(pollProperty, dataGrid);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除污染物性质
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PollPropertyEntity pollProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pollProperty = systemService.getEntity(PollPropertyEntity.class, pollProperty.getId());
		message = "污染物性质删除成功";
		pollPropertyService.delete(pollProperty);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加污染物性质
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PollPropertyEntity pollProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pollProperty.getId())) {
			message = "污染物性质更新成功";
			PollPropertyEntity t = pollPropertyService.get(PollPropertyEntity.class, pollProperty.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pollProperty, t);
				pollPropertyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "污染物性质更新失败";
			}
		} else {
			message = "污染物性质添加成功";
			pollPropertyService.save(pollProperty);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加污染物性质
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveChoosePolls")
	@ResponseBody
	public AjaxJson saveChoosePolls(PollPropertyEntity pollProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String cas = request.getParameter("cas");
		String[] casArr = cas.substring(0,cas.length() - 1).split(",");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("casArr", casArr);
		paramsMap.put("disableFlg", pollProperty.getDisableFlg());
		int count = pollPropertyService.savePollDisableFlg(paramsMap);
		if (count == 0) {
			message = "污染物性质更新失败";
			j.setSuccess(true);
			j.setMsg(message);
			return j;
			
		}
		message = "污染物性质更新成功";
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	/**
	 * 污染物性质列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PollPropertyEntity pollProperty, HttpServletRequest req) {
		String disableFlg = req.getParameter("disableFlg");
		req.setAttribute("disableFlg", disableFlg);
		if (StringUtil.isNotEmpty(pollProperty.getId())) {
			pollProperty = pollPropertyService.getEntity(PollPropertyEntity.class, pollProperty.getId());
			req.setAttribute("pollPropertyPage", pollProperty);
		}
		return new ModelAndView("com/hippo/nky/standard/pollPropertyChoose");
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
}
