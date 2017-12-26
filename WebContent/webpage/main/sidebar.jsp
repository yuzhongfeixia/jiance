<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!-- BEGIN SIDEBAR MENU -->        
<ul class="page-sidebar-menu">
	 <li>
		<div class="sidebar-toggler hidden-phone"></div>
	</li>
	<li class="searchLi">
		<form class="sidebar-search" action="">
			<div class="input-box">
				<a href="javascript:;" class="remove"></a>
				<%-- data-items="4" 属性可以设置默认下拉几个 --%>
				<input id="sidebarName" type="text" placeholder="菜单..." data-provide="typeahead" data-source="${dataSource}" data-ids="${dataIds}" data-sort="false"/>
				<input id="sidebarName_id" type="hidden"/>
				<input type="button" class="submit" />
			</div>
		</form>
	</li>
 	<t:menu  menuFun="${menuMap}"></t:menu>
</ul>
<!-- END SIDEBAR MENU -->