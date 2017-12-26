package com.hippo.nky.service.impl.portal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.news.NewsEntity;
import com.hippo.nky.entity.portal.FrontPortalIntroductionsEntity;
import com.hippo.nky.entity.standard.AgrCategoryEntity;
import com.hippo.nky.entity.standard.LimitStandardEntity;
import com.hippo.nky.entity.standard.LimitStandardVersionEntity;
import com.hippo.nky.entity.standard.PollProductsEntity;
import com.hippo.nky.entity.standard.PortalExpertEntity;
import com.hippo.nky.entity.standard.PortalOrganizationEntity;
import com.hippo.nky.entity.standard.StandardVersionEntity;
import com.hippo.nky.service.portal.FrontPortalIntroductionsServiceI;

@Service("frontPortalIntroductionsService")
@Transactional
public class FrontPortalIntroductionsServiceImpl extends CommonServiceImpl implements FrontPortalIntroductionsServiceI {
	
	private static final String NAME_SPACE = "com.hippo.nky.entity.portal.FrontPortalIntroductionsEntity.";
	
	/**
	 *  前台栏目跳转
	 * @param entity
	 * @param req
	 */
	@Override
	public void prepareData(FrontPortalIntroductionsEntity entity, HttpServletRequest req) {
		// 加载左侧菜单数据
		List<FrontPortalIntroductionsEntity> list = this.findListByMyBatis(NAME_SPACE+"searchIntroductions", entity.getId());
		req.setAttribute("introductionslList", list);
		// 加载当前页面层次结构
		req.setAttribute("dhName", findDhForLmid(entity.getId(), "lm", null));
		// 加载当前对象
		req.setAttribute("entity", entity);
		// 上边导航栏信息
		req.setAttribute("menuList", this.findListByMyBatis(NAME_SPACE+"findMenuList",null));
		// 一级栏目
		req.setAttribute("selectedId", this.getObjectByMyBatis(NAME_SPACE+"getSelectedId",entity.getId()));
		// 加载内容列表
		if(entity.getSourcelist()  == 1){
			// 标题列表
			CriteriaQuery cq = new CriteriaQuery(FrontPortalIntroductionsEntity.class);
			cq.eq("pid", entity.getId());
			cq.addOrder("sort", SortDirection.asc);
			cq.add();
			List<FrontPortalIntroductionsEntity> childrenlList = this.getListByCriteriaQuery(cq, false);
			req.setAttribute("childList", childrenlList);
		}else if(entity.getSourcelist()  == 2){
			
			// 新闻列表
			String hql = "from NewsEntity n where n.type = " + entity.getAssociatecondition();
			Query q = this.getSession().createQuery(hql);
			
			int rows = 5;
			int page = 0;
			if(!StringUtils.isEmpty(req.getParameter("rows"))){
				rows = Integer.parseInt(req.getParameter("rows"));
			}
			if(!StringUtils.isEmpty(req.getParameter("page"))){
				page = Integer.parseInt(req.getParameter("page"));
			}
			int beginIndex = page*rows;
			int endIndex = beginIndex+rows;
			
			q.setFirstResult(beginIndex); //从第几条开始      
	        q.setMaxResults(rows); //取出条数 
	        List<NewsEntity> newsList = q.list();
			req.setAttribute("dhNames", this.searchDhNames(newsList));
			req.setAttribute("entity", entity);
			req.setAttribute("childList", newsList);
			req.setAttribute("page", page);
			req.setAttribute("totalrecords", this.findByQueryString(hql).size());
		}else if(entity.getSourcelist()  == 3){
			// 质检体系列表
			this.fromOrganization(entity, req);
		}else if(entity.getSourcelist()  == 4){
			// 专家库列表
			String hql = "from PortalExpertEntity n";
			List<PortalExpertEntity> newsList = this.findByQueryString(hql);
			req.setAttribute("totalrecords", newsList.size());
			//req.setAttribute("childList", newsList);
		}else if(entity.getSourcelist()  == 5){
			// 版本和版本对应记录数查询
			getVersions(entity,req);
		}
		
	}

	/**
	 *  质检体系表查询
	 * @param entity
	 * @param req
	 */
	public void fromOrganization(FrontPortalIntroductionsEntity entity, HttpServletRequest req) {
		PortalOrganizationEntity e = new PortalOrganizationEntity();
		List<PortalOrganizationEntity> list = new ArrayList<PortalOrganizationEntity>();
		int associationcondition = Integer.parseInt(entity.getAssociatecondition());
		if(associationcondition==4||associationcondition==6||associationcondition==8){
			String hql = " from PortalOrganizationEntity p order by createdate";
			list =  this.findByQueryString(hql);

		}else{
			String hql = "from PortalOrganizationEntity p order by createdate";
			list =  this.findByQueryString(hql);
		}
		req.setAttribute("totalrecords", list.size());
		
	}
	


	
	/**
	 * >导航 （ type='lm','xw'）
	 * @param lmid
	 * @param type
	 * @param name
	 */
	public String findDhForLmid(String lmid,String type,String name){
		StringBuilder dh = new StringBuilder();
		List<FrontPortalIntroductionsEntity> list = this.findListByMyBatis(NAME_SPACE+"findDh", lmid);			
		
		for(int i=0;i<list.size();i++){
			FrontPortalIntroductionsEntity frontPortalIntroductionsEntity = list.get(i);
			if(list.size() == i+1){
				if("lm".equals(type)){
					dh.append("<span class=\"barner2\">");
					dh.append(frontPortalIntroductionsEntity.getName());
					dh.append("</span>");
				}else{
					dh.append("<span class=\"barner2\">");
					dh.append("<a href=\"#\" onclick=\"pageJump('"+frontPortalIntroductionsEntity.getId()+"'); \">");
					dh.append(frontPortalIntroductionsEntity.getName());
					dh.append("</a>");
					dh.append("</span>");
					//dh.append("→");	
				}
			}else{
				dh.append("<span class=\"barner2\">");
				dh.append("<a href=\"#\" onclick=\"pageJump('"+frontPortalIntroductionsEntity.getId()+"'); \">");
				dh.append(frontPortalIntroductionsEntity.getName());
				dh.append("</a>");
				dh.append("</span>");
				//dh.append("→");	
			}
		}
		if(!"lm".equals(type)){
			dh.append("<span class=\"barner2\">");
			if(name != null && name.length() > 80) {
				dh.append(name.substring(0, 80) + "...");
			}
			dh.append(name);
			dh.append("</span>");
		}
		return dh.toString();
	}
	
	/**
	 *  新闻内容查询
	 * @param entity
	 * @param req
	 */
	@Override
	public void toANews( String lmid , String id , HttpServletRequest req) {
		NewsEntity newsEntity = this.getEntity(NewsEntity.class, id);
		List<FrontPortalIntroductionsEntity> list =    this.findListByMyBatis(NAME_SPACE+"searchIntroductions", lmid);
		req.setAttribute("selectedId", this.getObjectByMyBatis(NAME_SPACE+"getSelectedId",lmid));
		req.setAttribute("introductionslList", list);
		req.setAttribute("newsEntity", newsEntity);
		req.setAttribute("dhName", findDhForLmid(lmid,"xw",newsEntity.getTitle()));
		FrontPortalIntroductionsEntity entity = this.getEntity(FrontPortalIntroductionsEntity.class, lmid);
		req.setAttribute("entity", entity);
		req.setAttribute("menuList", this.findListByMyBatis(NAME_SPACE+"findMenuList",null));
	}

	/**
	 * 跳转到标准详情页面
	 * @param req
	 */
	public void toStandardDetail(HttpServletRequest req){
		req.setAttribute("menuList", this.findListByMyBatis(NAME_SPACE+"findMenuList",null));
		FrontPortalIntroductionsEntity entity = this.getEntity(FrontPortalIntroductionsEntity.class, req.getParameter("lmid"));
		req.setAttribute("entity", entity);
		req.setAttribute("selectedId", this.getObjectByMyBatis(NAME_SPACE+"getSelectedId",req.getParameter("lmid")));
		List<FrontPortalIntroductionsEntity> list =    this.findListByMyBatis(NAME_SPACE+"searchIntroductions", req.getParameter("lmid"));
		req.setAttribute("introductionslList", list);
		
		if(entity.getAssociatecondition().equals("0")){
			AgrCategoryEntity agrCategoryEntity = this.getEntity(AgrCategoryEntity.class, req.getParameter("id"));
			req.setAttribute("detailEntity", agrCategoryEntity);
			req.setAttribute("dhName", findDhForLmid(req.getParameter("lmid"),"bz",agrCategoryEntity.getCname()));
		}else if (entity.getAssociatecondition().equals("1")){
			PollProductsEntity pollProductsEntity = this.getEntity(PollProductsEntity.class, req.getParameter("id"));
			req.setAttribute("detailEntity", pollProductsEntity);
			req.setAttribute("dhName", findDhForLmid(req.getParameter("lmid"),"bz",pollProductsEntity.getPopcname()));
		}else if(entity.getAssociatecondition().equals("2")){
			LimitStandardVersionEntity limitStandardVersionEntity = this.getEntity(LimitStandardVersionEntity.class, req.getParameter("id"));
			req.setAttribute("detailEntity", limitStandardVersionEntity);
			req.setAttribute("dhName", findDhForLmid(req.getParameter("lmid"),"bz",limitStandardVersionEntity.getNameZh()));
		}
	}
	
	/**
	 *  版本及内容查询
	 *  @param entity
	 * @param req
	 */

	public void getVersions(FrontPortalIntroductionsEntity entity,HttpServletRequest req) {
		if(entity.getAssociatecondition().equals("0")){
			// 农产品版本
			List<StandardVersionEntity> versionList = this.findListByMyBatis(NAME_SPACE+"getVersions",entity.getAssociatecondition());
			req.setAttribute("versionList", versionList);
			AgrCategoryEntity agrEntity= new AgrCategoryEntity();
			if(versionList.size()>0){
				// 初始化版本id
				if(!StringUtils.isEmpty(req.getParameter("versionid"))){
					agrEntity.setVersionid(req.getParameter("versionid"));
				}else{
					agrEntity.setVersionid(versionList.get(0).getId());
				}
				agrEntity.setCname(req.getParameter("cname"));
				agrEntity.setEname(req.getParameter("ename"));
				agrEntity.setCode(req.getParameter("code"));
				agrEntity.setGems(req.getParameter("gems"));
				agrEntity.setCalias(req.getParameter("calias"));
				agrEntity.setFoodex(req.getParameter("foodex"));
				req.setAttribute("agrEntity", agrEntity);
				req.setAttribute("totalrecords", this.getObjectByMyBatis(NAME_SPACE+"getAgrsCount", agrEntity));	
				
			}
		}else if(entity.getAssociatecondition().equals("1")){
			// 污染物版本
			List<StandardVersionEntity> versionList = this.findListByMyBatis(NAME_SPACE+"getVersions",entity.getAssociatecondition());
			req.setAttribute("versionList", versionList);
			PollProductsEntity pollEntity = new PollProductsEntity();
			if(versionList.size()>0){
				// 初始化版本id
				if(!StringUtils.isEmpty(req.getParameter("versionid"))){
					pollEntity.setVersionid(req.getParameter("versionid"));
				}else{
					pollEntity.setVersionid(versionList.get(0).getId());
				}
				pollEntity.setCategory(req.getParameter("category"));
				pollEntity.setCas(req.getParameter("cas"));
				pollEntity.setPopename(req.getParameter("popename"));
				pollEntity.setPopcname(req.getParameter("popcname"));
				pollEntity.setUse(req.getParameter("use"));
				req.setAttribute("pollEntity", pollEntity);
				req.setAttribute("totalrecords", this.getObjectByMyBatis(NAME_SPACE+"getPollsCount", pollEntity));
			}
		}else if(entity.getAssociatecondition().equals("2")){
			// 限量标准版本查询
			List<LimitStandardVersionEntity> versionList = this.findListByMyBatis(NAME_SPACE+"getLimitVersion",null);
			req.setAttribute("versionList", versionList);
			LimitStandardEntity limitEntity = new LimitStandardEntity();
			if(versionList.size()>0)
				// 初始化版本id
				if(!StringUtils.isEmpty(req.getParameter("versionid"))){
					limitEntity.setVersionid(req.getParameter("versionid"));
				}else{
					limitEntity.setVersionid(versionList.get(0).getId());
				}
				limitEntity.setCas(req.getParameter("cas"));
				limitEntity.setPollnameZh(req.getParameter("pollnameZh"));
				limitEntity.setAgrcategory(req.getParameter("agrcategory"));
				limitEntity.setAgrname(req.getParameter("agrname"));
				req.setAttribute("limitEntity", limitEntity);
				req.setAttribute("totalrecords", this.getObjectByMyBatis(NAME_SPACE+"getLimitStandardCount", limitEntity));
		}
	}
	/**
	 *  分页
	 *  @param entity
	 * @param req
	 */
	public List<Map<String, Object>> splitPage(FrontPortalIntroductionsEntity entity, HttpServletRequest req, DataGrid dataGrid) {
		
		if(entity.getListdisplaytype() == 5 ){
			// 标准分页查询
			if(entity.getAssociatecondition().equals("0")){
				Map<String,Object> modelMap = new HashMap<String,Object>();
				modelMap.put("versionid", req.getParameter("versionid"));
				modelMap.put("cname", req.getParameter("cname"));
				modelMap.put("ename", req.getParameter("ename"));
				modelMap.put("code", req.getParameter("code"));
				modelMap.put("gems", req.getParameter("gems"));
				modelMap.put("calias", req.getParameter("calias"));
				modelMap.put("foodex", req.getParameter("foodex"));
				prepareSplit(dataGrid,modelMap);
				List<Map<String, Object>> childList = this.findListByMyBatis(NAME_SPACE+"getAgrs", modelMap);
				return childList;
			}else if(entity.getAssociatecondition().equals("1")){
				Map<String,Object> modelMap = new HashMap<String,Object>();
				modelMap.put("versionid", req.getParameter("versionid"));
				modelMap.put("category", req.getParameter("category"));
				modelMap.put("cas", req.getParameter("cas"));
				modelMap.put("popcname", req.getParameter("popcname"));
				modelMap.put("popename", req.getParameter("popename"));
				modelMap.put("use", req.getParameter("use"));
				prepareSplit(dataGrid,modelMap);
				List<Map<String, Object>> childList = this.findListByMyBatis(NAME_SPACE+"getPolls", modelMap);
				return childList;
			}else if(entity.getAssociatecondition().equals("2")){
				Map<String,Object> modelMap = new HashMap<String,Object>();
				modelMap.put("versionid", req.getParameter("versionid"));
				modelMap.put("cas", req.getParameter("cas"));
				modelMap.put("pollnameZh", req.getParameter("pollnameZh"));
				modelMap.put("agrcategory", req.getParameter("agrcategory"));
				modelMap.put("agrname", req.getParameter("agrname"));
				prepareSplit(dataGrid,modelMap);
				List<Map<String, Object>> childList = this.findListByMyBatis(NAME_SPACE+"getLimitStandard", modelMap);
				return childList;
			}	
		}else if(entity.getListdisplaytype() == 4){
			// 专家库表分页查询
			//prepareSplit(dataGrid,modelMap);
			String sql = "select ORDERNO,NAME,POSITIONALTITLE,DUTY,UNIT,ORIENTATION from NKY_PORTAL_EXPERT n order by ORDERNO asc";
			List<Map<String,Object>> childList = this.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
			return childList;
		}else if(entity.getListdisplaytype() == 3){
			String sql = " select CODE,OGRNAME,PROPERTY from nky_portal_organization p order by createdate";
			List<Map<String,Object>> childList = this.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
			return childList;
		}
		
		
		return new ArrayList<Map<String, Object>>();
	}
	
	public void prepareSplit(DataGrid dataGrid ,Map<String,Object> modelMap){
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page-1)*rows;
		int endIndex = beginIndex+rows;
		modelMap.put("beginIndex", beginIndex);
		modelMap.put("endIndex", endIndex);
		
	}
	
	/**
	 *  查询新闻
	 * @return
	 */
	@Override
	public List<NewsEntity> searchNews() {
		return this.findListByMyBatis(NAME_SPACE + "getNews", null);
	}
	/**
	 *  查询菜单
	 * @return
	 */

	@Override
	public List<FrontPortalIntroductionsEntity> searchMenus() {
		return this.findListByMyBatis(NAME_SPACE+"findMenuList",null);
	}
	/**
	 *  查询顶级菜单
	 * @return
	 */

	@Override
	public List<FrontPortalIntroductionsEntity> searchTopMenus() {
		return this.commonDao.findListByMyBatis(NAME_SPACE + "findTopMenus", null);
	}

	@Override
	public List<String> searchDhNames(List<NewsEntity> newsEntities) {
		List<String> dhNames = new ArrayList<String>();
		for (NewsEntity newsEntity : newsEntities) {
			dhNames.add(this.findDhForNewsId(newsEntity.getId()));
		}
		return dhNames;
	}
	
	public List<FrontPortalIntroductionsEntity> findLms(List<NewsEntity> newsEntities) {
		List<FrontPortalIntroductionsEntity> entities = new ArrayList<FrontPortalIntroductionsEntity>();
		for (NewsEntity newsEntity : newsEntities) {
			FrontPortalIntroductionsEntity entity = this.commonDao.getObjectByMyBatis(this.NAME_SPACE + "getFrontPortalIntroductionsEntityByNewId", newsEntity.getId());
			entities.add(entity);
		}
		return entities;
	}
	
	/**
	 * ,导航 （ type='lm','xw'）
	 * @param lmid
	 * @param type
	 * @param name
	 */
	private String findDhForNewsId(String newId){
		FrontPortalIntroductionsEntity entity = this.commonDao.getObjectByMyBatis(this.NAME_SPACE + "getFrontPortalIntroductionsEntityByNewId", newId);
		
		StringBuilder dh = new StringBuilder();
		List<FrontPortalIntroductionsEntity> list = this.findListByMyBatis(NAME_SPACE+"findDh", entity.getId());			
		
		for(int i=0;i<list.size();i++){
			FrontPortalIntroductionsEntity frontPortalIntroductionsEntity = list.get(i);
			if(list.size() == i+1){
				dh.append("<li>");
				dh.append(frontPortalIntroductionsEntity.getName());
				dh.append("</li>");
				
			}else{
				dh.append("<li>");
				dh.append(frontPortalIntroductionsEntity.getName());
				dh.append(",");	
				dh.append("</li>");
				
			}
		}
		return dh.toString();
	}

	@Override
	public void sortIndex(HttpServletRequest request) {
		List<NewsEntity> newsEntities = this.searchNews();
 		request.setAttribute("newsEntities", newsEntities);
		request.setAttribute("menuList", this.searchMenus());
		request.setAttribute("topMenus", this.searchTopMenus());
		request.setAttribute("lms", this.findLms(newsEntities));
		request.setAttribute("dhNames", this.searchDhNames(newsEntities));
		
	}

	@Override
	public void searchData(HttpServletRequest req) {
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		// 上边导航栏信息
		req.setAttribute("menuList", this.findListByMyBatis(NAME_SPACE+"findMenuList",null));
		int rows = 5;
		int page = 0;
		if(!StringUtils.isEmpty(req.getParameter("rows"))){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		if(!StringUtils.isEmpty(req.getParameter("page"))){
			page = Integer.parseInt(req.getParameter("page"));;
		}
		int beginIndex = page*rows;
		int endIndex = beginIndex+rows;
		String searchText = StringUtil.getEncodePra(req.getParameter("searchText"));
		searchText.trim();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		modelMap.put("beginIndex", beginIndex);
		modelMap.put("endIndex", endIndex);
		modelMap.put("searchText", searchText);
		modelMap.put("startDate", startDate);
		modelMap.put("endDate", endDate);
		
		// 设置查询栏目范围
		List<String> lmList = null;
		if(ConverterUtil.isNotEmpty(req.getParameter("lms"))){
			lmList = ConverterUtil.getSplitList(req.getParameter("lms"),"_");
		}
		modelMap.put("lmList", lmList);
		
		// 取得首层栏目列表
		List<FrontPortalIntroductionsEntity> list = this.findListByMyBatis(NAME_SPACE+"getFirstLevelList", null);
		List<Map<String , Object>> firstLevelList = new ArrayList<Map<String , Object>>();
		String[] s = new String[list.size()] ;
		Map<String,Object> lmMap = ConverterUtil.stringToMap(req.getParameter("lms"),"_");
		for(int i=0;i<list.size();i++){
				FrontPortalIntroductionsEntity e = list.get(i);
				Map<String , Object> map = new HashMap<String , Object>();
 				map.put("ID", e.getId());
				map.put("NAME", e.getName());
				if(ConverterUtil.isNotEmpty(lmMap.get(e.getId()))){
					map.put("ISSELECTED", "1");
				}else{
					map.put("ISSELECTED", "0");
				}
				firstLevelList.add(map);
		}

		req.setAttribute("startDate", startDate);
		req.setAttribute("endDate", endDate);
		req.setAttribute("searchText", searchText);
		req.setAttribute("page", page);
		req.setAttribute("firstLevelList", firstLevelList);
		req.setAttribute("dhName", findDhForLmid("","xw","检索结果"));
		req.setAttribute("totalrecords", this.getObjectByMyBatis(NAME_SPACE+"getSearchDataCount", modelMap));
		List<NewsEntity> childList = this.findListByMyBatis(NAME_SPACE+"getSearchData", modelMap);
		req.setAttribute("childList", childList);
	}
	
}