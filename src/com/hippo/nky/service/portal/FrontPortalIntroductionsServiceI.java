package com.hippo.nky.service.portal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.hippo.nky.entity.news.NewsEntity;
import com.hippo.nky.entity.portal.FrontPortalIntroductionsEntity;

public interface FrontPortalIntroductionsServiceI extends CommonService{

	/**
	 *  前台栏目跳转
	 * @param entity
	 * @param req
	 */
	public void prepareData(FrontPortalIntroductionsEntity entity, HttpServletRequest req);
	
	
	/**
	 *  新闻内容查询
	 * @param entity
	 * @param req
	 */
	public void toANews(String lmid , String id , HttpServletRequest req);
	
	/**
	 *  查询按钮查询
	 *  @param entity
	 * @param req
	 * @param dataGrid 
	 */
	public List<Map<String, Object>> splitPage(FrontPortalIntroductionsEntity entity, HttpServletRequest req, DataGrid dataGrid);
	
	
	/**
	 *  新闻查询
	 * @return
	 */
	public List<NewsEntity> searchNews();
	/**
	 *  导航菜单查询
	 * @return
	 */
	public List<FrontPortalIntroductionsEntity> searchMenus();
	/**
	 *  导航顶级菜单查询
	 * @return
	 */
	public List<FrontPortalIntroductionsEntity> searchTopMenus();

	/**
	 *  新闻分类整理查询
	 * @return
	 */
	public List<String> searchDhNames(List<NewsEntity> newsEntities);


	public void sortIndex(HttpServletRequest request);


	/**
	 * 跳转到标准详情页面
	 * @param req
	 */
	public void toStandardDetail(HttpServletRequest req);

	/**
	 * 搜索页面跳转
	 * 
	 * @return
	 */
	public void searchData(HttpServletRequest req);
}
