package com.hippo.nky.controller.standard;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import com.hippo.nky.entity.standard.PollProductsEntity;
import com.hippo.nky.service.standard.PollProductsServiceI;

/**   
 * @Title: Controller
 * @Description: 污染物基础信息
 * @author zhangdaihao
 * @date 2013-08-01 11:58:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pollProductsController")
public class PollProductsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PollProductsController.class);

	@Autowired
	private PollProductsServiceI pollProductsService;
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
	 * 污染物基础信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollProducts")
	public ModelAndView pollProducts(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 取得分类ID
		String categoryId = request.getParameter("categoryId");
		if(StringUtil.isNotEmpty(categoryId)){
			request.setAttribute("categoryid", categoryId);
			paramMap.put("categoryid", categoryId);
		}
		// 取得版本ID
		String versionId = request.getParameter("versionId");
		if(StringUtil.isNotEmpty(versionId)){
			request.setAttribute("versionid", versionId);
			paramMap.put("versionid", versionId);
		}
		// 取得是否显示该分类下所有节点
		paramMap.put("showAllNoed", request.getParameter("showAllNoed"));
		// 取得是否是查询画面
		String query = request.getParameter("query");
		paramMap.put("query", query);
		request.setAttribute("query", query);
		// 取得用于定位的污染物ID
		String pollId = request.getParameter("pollId");
//		paramMap.put("pollId", pollId);
		request.setAttribute("pollId", pollId);
		// 设置画面参数
		request.setAttribute("customParam", ConverterUtil.mapToString(paramMap));
		// 取得污染物所在页码
		request.setAttribute("pageNum", ConverterUtil.toInteger(request.getParameter("pageNum"), 1));
		return new ModelAndView("com/hippo/nky/standard/pollProductsList");
	}
	
	/**
	 * 污染物基础信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pollProductsChoose")
	public ModelAndView pollProductsChoose(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		if(StringUtil.isNotEmpty(categoryId)){
			request.setAttribute("categoryid", categoryId);
			paramMap.put("categoryid", categoryId);
		}
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		if(StringUtil.isNotEmpty(versionId)){
			request.setAttribute("versionid", versionId);
			paramMap.put("versionid", versionId);
		}
		// 设置画面参数
		request.setAttribute("customParam", ConverterUtil.mapToString(paramMap));
		return new ModelAndView("com/hippo/nky/standard/pollProductsChoose");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
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
	@RequestMapping(params = "datagrid")
	public void datagrid(PollProductsEntity pollProducts, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) throws IllegalArgumentException, SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, InstantiationException {
		// 设置定位后页码
		if(ConverterUtil.isNotEmpty(request.getParameter("initPage"))){
			if(Integer.parseInt(request.getParameter("initPage")) > 1){
				dataGrid.setPage(Integer.parseInt(request.getParameter("initPage")));
			}
		}
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		// 取得是否显示所有节点
		String showAllNoed = request.getParameter("showAllNoed");
		// 取得是否是查询页面
		String query = request.getParameter("query");
		String col1 = "COUNT(1)";
		String col2 = "*";
		String col3 = "t2.*";
		Long count = 0l;
		List<Map<String, Object>> pollList = null;
		
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		dictionaryMap.put("unit", "unit");
		dictionaryMap.put("iscancer", "common");
		
		if (StringUtil.isNotEmpty(query) && Boolean.parseBoolean(query)) {
			String[] notQueryAttr = {"categoryid", "versionid", "categoryName", "categoryCode"};
			String where = ConverterUtil.getDataGridQuerySql(pollProducts, notQueryAttr);
			
			// 取得查询分类名称条件
			String categoryName = pollProducts.getCategoryName();
			// 取得查询分类编码条件
			String categoryCode = pollProducts.getCategoryCode();
			
			// 分类页面没有分类的区别，需要查出全部的污染物
			String sql = "SELECT #{PARAM} FROM nky_poll_products" 
						+ " WHERE versionid = '" + versionId + "'";
			String queryCategorySql = "SELECT #{PARAM} from nky_po_category t1,(#{FROM}) t2 where t1.id = t2.categoryid";
			String categoryNameWhere =" AND t1.name LIKE '%" + categoryName + "%'";
			String categoryCodeWhere = " AND t1.code LIKE '%" + categoryCode + "%'";
			
			if(StringUtil.isNotEmpty(categoryName)){
				queryCategorySql += categoryNameWhere;
			}
			if(StringUtil.isNotEmpty(categoryCode)){
				queryCategorySql += categoryCodeWhere;
			}
			
			if(StringUtil.isNotEmpty(categoryName) || StringUtil.isNotEmpty(categoryCode)){
				sql = queryCategorySql.replace("#{FROM}", sql.replace("#{PARAM}", col2));
				count = pollProductsService.getCountForJdbc(sql.replace("#{PARAM}", col1) + where);
				pollList = pollProductsService.findForJdbc(sql.replace("#{PARAM}", col3) + where,
						dataGrid.getPage(), dataGrid.getRows());
			} else {
				count = pollProductsService.getCountForJdbc(sql.replace("#{PARAM}", col1) + where);
				pollList = pollProductsService.findForJdbc(sql.replace("#{PARAM}", col2) + where,
						dataGrid.getPage(), dataGrid.getRows());
			}
		} else {
			String[] notQueryAttr = {"categoryid", "versionid"};
			String where = ConverterUtil.getDataGridQuerySql(pollProducts, notQueryAttr);
			if (StringUtil.isNotEmpty(showAllNoed) && Boolean.parseBoolean(showAllNoed)) {
				
				String sql = "select #{PARAM} from nky_poll_products t1 WHERE EXISTS ("
						+ "select ID from nky_po_category t2 where versionid = '" + versionId
						+ "' AND t1.categoryid = t2.id start with ID='" + categoryId + "' connect by prior id = pid)";
				count = pollProductsService.getCountForJdbc(sql.replace("#{PARAM}", col1) + where);
				pollList = pollProductsService.findForJdbc(sql.replace("#{PARAM}", col2) + where,
						dataGrid.getPage(), dataGrid.getRows());
			} else {
				// 正常页面
				String sql = "SELECT #{PARAM} FROM nky_poll_products" 
							+ " WHERE versionid = '" + versionId + "' AND categoryid='" + categoryId + "'";
				count = pollProductsService.getCountForJdbc(sql.replace("#{PARAM}", col1) + where);
				pollList = pollProductsService.findForJdbc(sql.replace("#{PARAM}", col2) + where,
						dataGrid.getPage(), dataGrid.getRows());
			}
		}
		Db2Page[] db2Pages = ConverterUtil.autoGetEntityToPage(PollProductsEntity.class, dictionaryMap);
		JSONObject jObject = TagUtil.getDatagridWithListMapAndDb2Page(pollList, ConverterUtil.toInteger(count), db2Pages,dataGrid);
		DataUtils.responseDatagrid(response, jObject);
	}
	
	/**
	 * 取得污染物所在第X页
	 * 
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping(params = "getPollPage")
	@ResponseBody
	public AjaxJson getPollPage(HttpServletRequest request, DataGrid dataGrid){
		AjaxJson j = new AjaxJson();
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		// 取得污染物id
		String pollId = request.getParameter("pollId");
		if(StringUtil.isEmpty(categoryId) || StringUtil.isEmpty(versionId)){
			j.setSuccess(false);
			return j;
		}
		// 拼装SQL
		String sql = "SELECT COLINDEX FROM(" + "select ID,ROWNUM AS COLINDEX" + " FROM NKY_POLL_PRODUCTS this_"
				+ " WHERE this_.CATEGORYID='" + categoryId + "'AND this_.VERSIONID='" + versionId
				+ "')WHERE ID = '" + pollId + "'";
		Map<String, Object> colIndexMap = pollProductsService.findOneForJdbc(sql);
		Integer lineNum = ConverterUtil.toInteger(colIndexMap.get("COLINDEX"), 0);
		int page = 0;
		if(lineNum > dataGrid.getRows()){
			// 计算定位的污染物在第几页
			page = (lineNum/dataGrid.getRows()) +( lineNum%dataGrid.getRows()!=0?1:0);
		}
		// 如果取得时0页要默认成第一页，否则画面显示会乱掉
		if(page == 0){
			page = 1;
		}
		j.setSuccess(true);
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("pageNum", page);
		j.setAttributes(attributes);
		return j;
	}

	/**
	 * 删除污染物基础信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PollProductsEntity pollProducts, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pollProducts = systemService.getEntity(PollProductsEntity.class, pollProducts.getId());
		message = "删除成功";
		pollProductsService.delete(pollProducts);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加污染物基础信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PollProductsEntity pollProducts, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pollProducts.getId())) {
			message = "更新成功";
			PollProductsEntity t = pollProductsService.get(PollProductsEntity.class, pollProducts.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pollProducts, t);
				pollProductsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			// 如果是默认的暂无图片不做存储
			if (null == pollProducts.getStructure() || pollProducts.getStructure().contains(Constants.IMAGE_PATH_NOIMAGE)) {
				pollProducts.setStructure("");
			}
			pollProductsService.save(pollProducts);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 保存选择的污染物到DB
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "saveChoosePolls")
	@ResponseBody
	public AjaxJson saveChoosePolls(PollProductsEntity pollProducts, HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		// 取得分类ID
		String categoryId = request.getParameter("categoryid");
		// 取得版本ID
		String versionId = request.getParameter("versionid");
		// 取得选择的污染物ID
		String pollIds = request.getParameter("pollIds");
		// 没选择污染物的情况直接返回
		if(StringUtil.isEmpty(pollIds)){
			j.setSuccess(true);
			j.setMsg("没有选择任何污染物");
			return j;
		}
		List<String> idList = ConverterUtil.getSplitList(pollIds, ",");
	
		CriteriaQuery cq = new CriteriaQuery(PollDictionaryEntity.class);
		cq.add(Restrictions.in("id", idList));
		List<PollDictionaryEntity> dictionaryList = pollProductsService.getListByCriteriaQuery(cq, false);
		List<PollProductsEntity> productList = ConverterUtil.copyListBeanToListBean(dictionaryList, PollProductsEntity.class);
		Map<String, List<Object>> setProperties = new HashMap<String, List<Object>>();
		List<Object> categoryLst = new ArrayList<Object>();
		categoryLst.add(new String());
		categoryLst.add(categoryId);
		setProperties.put("categoryid", categoryLst);
		List<Object> versionLst = new ArrayList<Object>();
		versionLst.add(new String());
		versionLst.add(versionId);
		setProperties.put("versionid", versionLst);
		List<Object> idLst = new ArrayList<Object>();
		idLst.add(new String());
		idLst.add("");
		setProperties.put("id", idLst);
		ConverterUtil.setListBeanValue(productList, setProperties);
		pollProductsService.batchSave(productList);
//		cq.add(cq.in("id", idList));
//		cq.in
//		cq.add(cq.and(Restrictions.eq("categoryid", categoryId),
//				Restrictions.eq("versionid", versionId)));
		//查询条件组装器
//		HqlGenerateUtil.
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, pollProducts);
		j.setSuccess(true);
		return j;
	}

	/**
	 * 污染物基础信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PollProductsEntity pollProducts, HttpServletRequest req) {
		String categoryId = req.getParameter("categoryid");
		String versionid=req.getParameter("versionid");
		if (StringUtil.isNotEmpty(pollProducts.getId())) {
			pollProducts = pollProductsService.getEntity(PollProductsEntity.class, pollProducts.getId());
		} else {
			pollProducts.setCategoryid(categoryId);
			pollProducts.setVersionid(versionid);
			// 设置单位默认选择mg/kg
			pollProducts.setUnit(0);
			// 新规时如果缓存被清空时会发生图片加载不出来的问题，这里初始化一下
			pollProducts.setStructure(ConverterUtil.getActionPath(req.getSession().getServletContext().getContextPath(), Constants.IMAGE_PATH_NOIMAGE));
		}
		req.setAttribute("pollProductsPage", pollProducts);
		req.setAttribute("load", req.getParameter("load"));

		return new ModelAndView("com/hippo/nky/standard/pollProducts");
	}

	@RequestMapping(params = "polllocate")
	public AjaxJson pollLocate(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String pollId = request.getParameter("pollId");
		String versionid = request.getParameter("versionid");
		
		// 取得当前所在污染物id所在的分类
		String categoryid = null;
		CriteriaQuery cq = new CriteriaQuery(PollProductsEntity.class);
		if (pollId != null) {
			cq.eq("id", pollId);
		}
		if(versionid != null){
			cq.eq("versionid", versionid);
		}
		cq.add();
		List<PollProductsEntity> pollProductsList = systemService.getListByCriteriaQuery(cq, false);
		if (pollProductsList != null && pollProductsList.size() > 0) {
			categoryid = pollProductsList.get(0).getCategoryid();
		}
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("categoryid", categoryid);
		j.setAttributes(attributes);
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
		String constraint = "CAS" + ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("param")
				+ ConverterUtil.SEPARATOR_ELEMENT + "VERSIONID"
				+ ConverterUtil.SEPARATOR_KEY_VALUE
				+ request.getParameter("versionid");
		j = systemService.uniquenessCheck("NKY_POLL_PRODUCTS", request.getParameter("id"), constraint);
		if(!j.isSuccess()){
			j.setMsg("污染物CAS码已存在!");
		}
		return j;
	}

//	/**
//	 * 定位
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public ModelAndView pollLocate(HttpServletRequest request) {
//		// 取得版本ID
//		String versionId = request.getParameter("versionId");
//		if (StringUtil.isNotEmpty(versionId)) {
//			request.setAttribute("versionid", versionId);
//		}
//		// 取得单位替换list
//		List<TSType> types = TSTypegroup.allTypes.get("unit");
//		String unitList = "";
//		unitList = ConverterUtil.getQueryReplaceList(types);
//		request.setAttribute("unitList", unitList);
//		// 取得单位替换list
//		List<TSType> commons = TSTypegroup.allTypes.get("common");
//		String cancerList = "";
//		cancerList = ConverterUtil.getQueryReplaceList(commons);
//		request.setAttribute("cancerList", cancerList);
//		return new ModelAndView("com/hippo/nky/standard/pollProductsList");
//	}
}
