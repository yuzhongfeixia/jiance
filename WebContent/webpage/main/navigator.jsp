<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="/easyui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Map<?, ?> paramMap = request.getParameterMap();
	setNavigatorMap(request);
	for (Object key : paramMap.keySet()) {
		if (key.equals("menuName") || key.equals("menuCaption")
				|| key.equals("parentItems")) {
			continue;
		} else {
			request.setAttribute((String) key, request.getParameter(key.toString()));
		}
	}
%>
<%!public void setNavigatorMap(HttpServletRequest request) {
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
	}%>
<!-- <script type="text/javascript"> -->
<!-- // 	App.resetColorPanel(); -->
<!-- </script> -->
<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
<!-- 	<div class="span12"> -->
		<!-- BEGIN STYLE CUSTOMIZER -->
<!-- 		<div class="color-panel hidden-phone"> -->
<!-- 			<div class="color-mode-icons icon-color"></div> -->
<!-- 			<div class="color-mode-icons icon-color-close"></div> -->
<!-- 			<div class="color-mode"> -->
<!-- 				<p>THEME COLOR</p> -->
<!-- 				<ul class="inline"> -->
<!-- 					<li class="color-black current color-default" data-style="default"></li> -->
<!-- 					<li class="color-blue" data-style="blue"></li> -->
<!-- 					<li class="color-brown" data-style="brown"></li> -->
<!-- 					<li class="color-purple" data-style="purple"></li> -->
<!-- 					<li class="color-grey" data-style="grey"></li> -->
<!-- 					<li class="color-white color-light" data-style="light"></li> -->
<!-- 				</ul> -->
<!-- 				<label> -->
<!-- 					<span>Layout</span> -->
<!-- 					<select class="layout-option m-wrap small"> -->
<!-- 						<option value="fluid" selected>Fluid</option> -->
<!-- 						<option value="boxed">Boxed</option> -->
<!-- 					</select> -->
<!-- 				</label> -->
<!-- 				<label> -->
<!-- 					<span>Header</span> -->
<!-- 					<select class="header-option m-wrap small"> -->
<!-- 						<option value="fixed" selected>Fixed</option> -->
<!-- 						<option value="default">Default</option> -->
<!-- 					</select> -->
<!-- 				</label> -->
<!-- 				<label> -->
<!-- 					<span>Sidebar</span> -->
<!-- 					<select class="sidebar-option m-wrap small"> -->
<!-- 						<option value="fixed">Fixed</option> -->
<!-- 						<option value="default" selected>Default</option> -->
<!-- 					</select> -->
<!-- 				</label> -->
<!-- 				<label> -->
<!-- 					<span>Footer</span> -->
<!-- 					<select class="footer-option m-wrap small"> -->
<!-- 						<option value="fixed">Fixed</option> -->
<!-- 						<option value="default" selected>Default</option> -->
<!-- 					</select> -->
<!-- 				</label> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<!-- END BEGIN STYLE CUSTOMIZER -->             
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<h3 class="page-title" style="display:none;">
			${navigator.menuName}<small>${navigator.menuCaption}</small>
		</h3>
<!-- 		<ul class="breadcrumb"> -->
<!-- 			<li> -->
<!-- 				<i class="icon-home"></i> -->
<!-- 				<a href="index.jsp">首页</a>  -->
<%-- 				<c:if test="${not empty navigator.items}"> --%>
<!-- 					<i class="icon-angle-right"></i> -->
<%-- 				</c:if> --%>
<!-- 			</li> -->
<%-- 			<c:forEach var="item" items="${navigator.items}" varStatus="status"> --%>
<!-- 				<li> -->
<%-- 					<a src="${item.url}" onclick="loadContent(this, '${item.url}');">${item.name}</a> --%>
<%-- 					<c:if test="${not status.last}"> --%>
<!-- 						<i class="icon-angle-right"></i> -->
<%-- 					</c:if> --%>
<!-- 				</li> -->
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
		<!-- END PAGE TITLE & BREADCRUMB-->
<!-- 	</div> -->
		<ul class="breadcrumb">
			<li>
				<i class="icon-home" style="color: #ffa92e;"></i>
				<a href="loginController.do?login">首页</a> 
				<c:if test="${not empty navigator.items}">
					<i class="icon-angle-right"></i>
				</c:if>
			</li>
			<c:forEach var="item" items="${navigator.items}" varStatus="status">
				<li>
					<a src="${item.url}" onclick="loadContent(this, '${item.url}');">${item.name}</a>
					<c:if test="${not status.last}">
						<i class="icon-angle-right"></i>
					</c:if>
				</li>
			</c:forEach>
		</ul>
</div>
<!-- END PAGE HEADER-->