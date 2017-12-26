<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/navigator.jsp" %>
<%@ include file="../../../context/mytags.jsp" %>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/scripts/opration-manager.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<body class="page-header-fixed">
	<div class="row-fluid">
<!-- 		<div class="span6" style="width:71%"> -->
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="clearfix">
						<div class="btn-group">
							<a href="functionController.do?addorupdateop&functionId=${functionId}"
								class="btn btngroup_usual" action-mode="ajax" action-event="click"
								action-pop="#ajax-modal"><i class="icon-plus"></i>操作录入</a>
						</div>
					</div>
					<input name="functionId" type="hidden" value="${functionId}"/>
					<input id="functionName" name="functionName" type="hidden" value="${functionName}"/>
					<table id="oprationTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>操作名称</th>
								<th>代码</th>
								<th>权限名称</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<script>
	jQuery(document).ready(function() {   
		TableManaged.init();
	});
	</script>

</body>













