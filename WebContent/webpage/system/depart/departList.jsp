<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<!-- <script src="assets/scripts/table-managed.js"></script> -->
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>


<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form id="searchForm" name="searchForm" action="#" class="form-horizontal" onsubmit="return false;">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">单位名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="departname" class="m-wrap small">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="addData" href="#" class="btn btngroup_usual" ><i class="icon-plus"></i>单位录入</a>
						</div>
						<div class="pull-right">
							<a id="searchBtn" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table id="departTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="hidden-480">单位名称</th>
								<th class="hidden-480">职能描述</th>
								<th class="hidden-480">操作</th>
							</tr>
						</thead>
						<tbody>
					    </tbody>
	     			</table>
				</div>
			</div>	
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<script>
		$('#searchBtn').on('click', function(){
			setQueryParams('departTable',$('#searchForm').getFormValue());
			refresh_departList();
		});
		var $modal = $('#ajax-modal');
		jQuery(document).ready(function() {
			   registAjaxDataTable({
				   	id:"departTable",
					actionUrl:"departController.do?datagrid",
					search:true,
					aoColumns:[
							{ "mDataProp": "departname"},
							{ "mDataProp": "description"},
							{
							"mData" : 'id',"sWidth":"15%",
							bSortable : false,
							"mRender" : function(data, type, full) {
								return '<a data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
								'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
							}
							}],
				   	   initModals:[
				   	        {
			               		"id" : "addData",
								"operation" : "createwindow",
								"url":"departController.do?add",
			   	   			}
// 			   	   			{
// 			               		"id" : "addBtn",
// 			               		"operation" : "windowsave",
// 								"url":"departController.do?save",
// 								"formId":"saveForm",
// 			   	   			}		   	   			
			   	   		]
				});
			});
		//编辑信息
		function update(data) {
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		    $modal.load('departController.do?update&id='+data, '', function(){
		     $modal.modal();
		     App.unblockUI(pageContent);
		     Validator.init();
		    });
		}
		//删除信息
		function del(data) {
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '你确认删除所选记录?',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "departController.do?del&rand=" + Math.random(),
						data : "id=" + data,
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if (d.success) {
				   				refresh_departList();
				   			 }else {
				   				modalTips(d.msg);
				   			 }
						}
					});
				}
			});
		}

		function refresh_departList() {  
			$("#departTable").dataTable().fnPageChange('first');  
		} 
	</script>

</body>

