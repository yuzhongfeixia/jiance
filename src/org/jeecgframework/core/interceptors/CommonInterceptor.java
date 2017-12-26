package org.jeecgframework.core.interceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 共通拦截器
 * <p>
 * 预处理导航信息 以及loadContent中含有的参数设置
 * @author XuDL
 * 
 */
public class CommonInterceptor implements HandlerInterceptor {

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		Map<?, ?> paramMap = request.getParameterMap();
		// 预处理导航信息 以及loadContent中含有的参数设置
		setNavigatorMap(request);
		for (Object key : paramMap.keySet()) {
			if (key.equals("menuName") || key.equals("menuCaption")
					|| key.equals("parentItems")) {
				continue;
			} else {
				request.setAttribute((String) key, request.getParameter(key.toString()));
			}
		}
		return true;
	}

	/**
	 * 预处理导航信息
	 * 
	 * @param request
	 */
	public void setNavigatorMap(HttpServletRequest request) {
		Map<String, Object> navigatorMap = new HashMap<String, Object>();
		// 取得菜单名称
		String menuName = request.getParameter("menuName");
		// 如果名称不为空，认为存在导航信息，并解析
		if (null != menuName) {
			navigatorMap.put("menuName", menuName);
			// 取得菜单说明
			String menuCaption = request.getParameter("menuCaption");
			if (null != menuCaption) {
				navigatorMap.put("menuCaption", menuCaption);
			}
			// 取得菜单父子信息
			String parentItems = request.getParameter("parentItems");
			if (null != parentItems) {
				// 删除末尾的[,]号，删除['(为了防止名称中含有特殊字符而加的)]
				parentItems = parentItems
						.substring(0, parentItems.length() - 1)
						.replace("'", "");
				List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
				String[] pitems = parentItems.split(",");
				// 组装父菜单
				for (String item : pitems) {
					if (null == item || "".equals(item)) {
						continue;
					}
					Map<String, Object> itemMap = new HashMap<String, Object>();
					String[] attrs = item.split(":");
					// 如果父菜单只设置了名称的情况，链接为#。
					if (attrs.length >= 2) {
						itemMap.put("name", attrs[0]);
						itemMap.put("url", attrs[1]);
					} else {
						itemMap.put("name", attrs[0]);
						itemMap.put("url", "#");
					}
					items.add(itemMap);
				}
				navigatorMap.put("items", items);
			}
		} else {
			// 默认情况下，导航为Dashboard TODO 默认待定
			navigatorMap.put("menuName", "江苏省农产品质量安全监测信息系统");
			navigatorMap.put("menuCaption", "");
			// 	List<Map<String,Object>> items = new ArrayList<Map<String, Object>>();
			// 	Map<String,Object> item1 = new HashMap<String, Object>();
			// 	item1.put("url", "#");
			// 	item1.put("name", "");
			// 	items.add(item1);
			// 	navigatorMap.put("items", items);
		}
		//将导航信息放回request对象
		request.setAttribute("navigator", navigatorMap);
	}
}
