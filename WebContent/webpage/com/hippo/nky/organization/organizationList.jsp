<%@page import="org.apache.tools.ant.util.DateUtils"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>
<script src="assets/scripts/ui-jqueryui.js"></script> 
<script src="assets/js/curdtools.js" type="text/javascript" ></script>

<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>
					</div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">机构代码:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="code" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">机构名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="ogrname" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">负责人:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="leader" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">联系人电话:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="contactstel" class="m-wrap small">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="add_org" class="btn btngroup_usual" data-toggle="modal" onclick="addOrg()"><i class="icon-plus"></i>新增</a>
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
							<a href="#" id="exportExcel" class="btn btngroup_usual"><i class="icon-share"></i>导出Excel</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="organizationTable">
						<thead>
							<tr>
								<th>机构代码</th>
								<th>机构名称</th>
								<th class="hidden-480">负责人</th>
								<th class="hidden-480">联系人</th>
								<th class="hidden-480">操作</th>
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
	$('#searchBtn').on('click', function(){
		setQueryParams('organizationTable',$('#searchConditionForm').getFormValue());
		refresh_organizationList();
	});
	$('#exportExcel').on('click', function(){
		//confirmAndClose();
		exportExcelByCustom('organization.OrganizationService.exportExcelForOrganization','江苏省质检结构-<%=DateUtils.format(new Date(), "yyyyMMdd") %>','searchConditionForm');
		//exportExcelByCustom('sample.SamplingInfoService.exportSamplingBlindInfo','','searchConditionForm');
	});
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"organizationTable",
			actionUrl:"organizationController.do?datagrid&rand="+Math.random(),
			search : true,
			aoColumns:[
					{ "mDataProp": "code"},
					{ "mDataProp": "ogrname"},
					{ "mDataProp": "leader"},
					{ "mDataProp": "contacts"},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
					}
					}],
				initModals:[
// 					{
// 					"id" : "add_org",
// 					"operation" : "createwindow",
// 					"url":"organizationController.do?addorupdate&rand="+Math.random(),
// 				   	},

// 					{
// 					"id" : "addBtn",
// 					"operation" : "windowsave",
// 					"url":"organizationController.do?save",
// 					"formId":"saveForm"
// 					} 
				] ,
	        	fnCallBefore: function (queryParams,aoData){
	        		return  $('#searchConditionForm').getFormValue();
        		},
		});

		
	});
	function refresh_organizationList() {  
		$("#organizationTable").dataTable().fnPageChange('first');  
	}

	function addOrg() {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('organizationController.do?addorupdate', '', function(){
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
					url : "organizationController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_organizationList();
			   				modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
	}

	function update(data) {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('organizationController.do?addorupdate&id='+data, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

</script>
</body>
