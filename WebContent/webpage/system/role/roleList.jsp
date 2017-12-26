<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/navigator.jsp" %>
<%@ include file="../../../context/mytags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script type="text/javascript" src="assets/scripts/role-managed.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<style>
.styletd1 tr td{
padding-top: 1px;
padding-bottom: 1px
}
</style>
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<input type="hidden" value="${requestScope.clickFunctionId}" id="clickFunctionId">
<div class="row-fluid">
	<div class="span6" style="width: 49.7%;">
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-globe"></i>角色列表
				</div>
			</div>
			<div class="portlet-body">
				<form action="#" class="form-horizontal" name="searchForm" id="searchForm">
				<div class="alert alert-success">
					<div class="clearfix">
						<div class="table-seach">
							<label class="help-inline seach-label">角色名称:</label>
							<div class="seach-element">
								<input type="text" placeholder="" class="m-wrap small" id="roleName">
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="pull-left">
						<t:authFilter name="ajax_add_btn"><a class="btn btngroup_usual" data-toggle="modal" id="ajax_add_btn"><i class="icon-plus"></i>新增</a></t:authFilter>
					</div>
					<div class="pull-right">
						<t:authFilter name="searchBtn"><a href="#" class="btn btngroup_seach" id="searchBtn"><i class="icon-search"></i>搜索</a></t:authFilter>
<%-- 						<t:authFilter name="resetBtn"><button type="button" id="resetBtn" class="btn btngroup_usual" onclick="reset();"><i class="icon-reload"></i>重置</button></t:authFilter> --%>
					</div>
				</div>
				</form>
				<table class="table table-striped table-bordered table-hover"
					id="lst_table" style="margin-top: 5px;">
					<thead>
						<tr>
							<th class="center hidden-480">角色编码</th>
							<th class="center hidden-480">角色名称</th>
							<th class="center hidden-480">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="span6" style="width: 50%; margin-left: 0.3%">
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-globe"></i>当前权限
				</div>
			</div>
			<div id="menuLst" class="portlet-body" style="height: 555px;">
			</div>
		</div>
	</div>
</div>
<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
<div id="confirmDiv" class="modal hide fade" tabindex="-1"></div>
</body>
</html>		