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

<style type="text/css">
.control-item{
	width:350px;
	float:left;
}
</style>
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
									<label class="help-inline seach-label">污染物CAS码:</label>
									<div class="seach-element">
										<input type="text" name="cas" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">污染物名称:</label>
									<div class="seach-element">
										<input type="text" name="pollnameZh" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">农产品类别:</label>
									<div class="seach-element">
										<input type="text" name="agrcategory" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">农产品名称:</label>
									<div class="seach-element">
										<input type="text" name="agrname" placeholder="" class="m-wrap small">
									</div>
								</div>
							</div>
							<input id="standardCode" name="standardCode" type="hidden" value="${standardCode}">
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="addStandard()"><i class="icon-plus"></i>新增</a>
<!-- 							<a id="impData" class="btn btngroup_usual" data-toggle="modal"><i class="icon-plus"></i>导入</a> -->
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="limitStanderdTable">
						<thead>
							<tr>
								<th>污染物CAS编码</th>
								<th>污染物名称</th>
								<th class="hidden-480">农产品类别</th>
								<th class="hidden-480">农产品名称</th>
								<th class="hidden-480">限量</th>
								<th class="hidden-480">标准号</th>
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
	var versionid = "${versionId}";
	$('#searchBtn').on('click', function(){
		setQueryParams('limitStanderdTable',$('#searchConditionForm').getFormValue());
		refresh_limitStandardList();
	});
	var standardCode = $('#standardCode').val();
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"limitStanderdTable",
			actionUrl:"limitStandardController.do?datagrid&versionid=${versionId}",
			search:true,
			aoColumns:[
					{ "mDataProp": "cas"},
					{ "mDataProp": "pollnameZh"},
					{ "mDataProp": "agrcategory"},
					{ "mDataProp": "agrname"},
					{ "mDataProp": "mrl"},
					{
						"mRender" : function() {
							return standardCode;
						}
					},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update2(\''+data+ '\')">编辑</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="del2(\''+data+ '\')">删除</a>';
					}
					}],
// 				initModals:[
// 					{
// 					"id" : "addData",
// 					"operation" : "createwindow",
// 					"callBefore" :function(){
// 						if(judgePublish(versionid)){
// 							modalAlert("版本已经发布,不能再进行删除操作!");
// 							return false;
// 						}
						
// 					},
// 					"url":"limitStandardController.do?addorupdate&versionId=${versionId}&rand="+Math.random(),
// 				   	},

// 					{
// 					"id" : "addBtn",
// 					"operation" : "windowsave",
// 					"url":"limitStandardController.do?save",
// 					"formId":"saveForm"
// 					} 
// 				] ,
	        	fnCallBefore: function (queryParams,aoData){
	        		return  $('#searchConditionForm').getFormValue();
        		},
		});

		
	});
	function refresh_limitStandardList() {  
		$("#limitStanderdTable").dataTable().fnPageChange('first');  
	} 
	
	function addStandard() {
		if(judgePublish(versionid)){
			modalAlert("版本已经发布,不能再进行新增操作!");
			return false;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	 	App.unblockUI(pageContent);
	     $modal.load('limitStandardController.do?addorupdate&versionId='+versionid,'', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

	//删除信息
	function del2(data) {
		if(judgePublish(versionid)){
			modalAlert("版本已经发布,不能再进行删除操作!");
			return;
		}
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "limitStandardController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_limitStandardList();
			   			 }else {
			   				modalAlert(d.msg);
			   			 }
					}
				});
			}
		});
	}

	function update2(data) {
		if(judgePublish(versionid)){
			modalAlert("版本已经发布,不能再进行编辑操作!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('limitStandardController.do?addorupdate&id='+data, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

</script>
</body>
