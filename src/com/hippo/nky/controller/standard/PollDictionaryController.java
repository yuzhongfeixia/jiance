package com.hippo.nky.controller.standard;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.PollDictionaryEntity;
import com.hippo.nky.service.standard.PollDictionaryServiceI;

/**   
 * @Title: Controller
 * @Description: 污染物字典
 * @author zhangdaihao
 * @date 2013-07-31 12:47:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pollDictionaryController")
public class PollDictionaryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PollDictionaryController.class);

	@Autowired
	private PollDictionaryServiceI pollDictionaryService;
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
	 * 污染物字典列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollDictionary")
	public ModelAndView pollDictionary(HttpServletRequest request) {
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		if(StringUtil.isNotEmpty(categoryId)){
			request.setAttribute("categoryid", categoryId);
		}
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		if(StringUtil.isNotEmpty(versionId)){
			request.setAttribute("versionid", versionId);
		}
		return new ModelAndView("com/hippo/nky/standard/pollDictionaryList");
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
	public void datagrid(PollDictionaryEntity pollDictionary,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(PollDictionaryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, pollDictionary);
		this.pollDictionaryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据返回污染物基础信息表中不存在的数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @throws IllegalArgumentException
	 *             反射方法参数异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws InvocationTargetException
	 *             反射get方法异常
	 * @throws SecurityException
	 *             安全异常
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 * @throws InstantiationException
	 *             反射异常
	 */
	@RequestMapping(params = "subDatagrid")
	public void subDatagrid(PollDictionaryEntity pollDictionary, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		String where = ConverterUtil.getDataGridQuerySql(pollDictionary);

		String col1 = " COUNT(1) ";
		String col2 = " * ";
		// 查询条件组装器
		String sql = "select #{PARAM} FROM nky_poll_dictionary t1" + " WHERE NOT EXISTS (SELECT cas"
				+ " FROM nky_poll_products t2" + " WHERE versionid = '" + versionId + "'" + " AND t1.cas = t2.cas)";
		Long count = pollDictionaryService.getCountForJdbc(sql.replace("#{PARAM}", col1) + where);

		List<Map<String, Object>> mapList = pollDictionaryService.findForJdbc(sql.replace("#{PARAM}", col2) + where,
				dataGrid.getPage(), dataGrid.getRows());
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		dictionaryMap.put("unit", "unit");
		dictionaryMap.put("iscancer", "common");
		Db2Page[] db2Pages = ConverterUtil.autoGetEntityToPage(PollDictionaryEntity.class, dictionaryMap);
		JSONObject jObject = TagUtil
				.getDatagridWithListMapAndDb2Page(mapList, ConverterUtil.toInteger(count), db2Pages, dataGrid);
		DataUtils.responseDatagrid(response, jObject);
	}
	

	/**
	 * 删除污染物字典
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PollDictionaryEntity pollDictionary, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pollDictionary = systemService.getEntity(PollDictionaryEntity.class, pollDictionary.getId());
		message = "删除成功";
		pollDictionaryService.delete(pollDictionary);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加污染物字典
	 * 
	 * @param ids
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PollDictionaryEntity pollDictionary, HttpServletRequest request) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pollDictionary.getId())) {
			message = "更新成功";
			PollDictionaryEntity t = pollDictionaryService.get(PollDictionaryEntity.class, pollDictionary.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pollDictionary, t);
				pollDictionaryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			// 如果是默认的暂无图片不做存储
			if(null == pollDictionary.getStructure() || pollDictionary.getStructure().contains(Constants.IMAGE_PATH_NOIMAGE)){
				pollDictionary.setStructure("");
			}
			pollDictionaryService.save(pollDictionary);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 污染物字典列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PollDictionaryEntity pollDictionary, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(pollDictionary.getId())) {
			pollDictionary = pollDictionaryService.getEntity(PollDictionaryEntity.class, pollDictionary.getId());
		} else {
			// 设置单位默认选择mg/kg
			pollDictionary.setUnit(0);
			// 新规时如果缓存被清空时会发生图片加载不出来的问题，这里初始化一下
			pollDictionary.setStructure(ConverterUtil.getActionPath(req.getSession().getServletContext().getContextPath(), Constants.IMAGE_PATH_NOIMAGE));
			// 设置污染物性质默认为常规
			pollDictionary.setDisableFlg(2);
		}
		req.setAttribute("pollDictionaryPage", pollDictionary);
		
		String load = req.getParameter("load");
		if(StringUtil.isNotEmpty(load)){
			req.setAttribute("load", load);
		}
		return new ModelAndView("com/hippo/nky/standard/pollDictionary");
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
		String constraint = "CAS" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param");
		j = systemService.uniquenessCheck("NKY_POLL_DICTIONARY", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("污染物CAS码已存在!");
		}
		return j;
	}
}
