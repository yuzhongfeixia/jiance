<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<!-- END HEAD -->
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css" />
<script src="assets/scripts/ui-jqueryui.js"></script>
<script src="assets/js/curdtools.js" type="text/javascript"></script>
<script type="text/javascript">
function importDictionaryData(data){
	// 调用导入方法
	importFile();
	AjaxMode.callRefresh("pollDicTable");
}
</script>
<style type="text/css">
.control-item {
	width: 350px;
	float: left;
}
</style>

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
						<form action="#" class="form-horizontal"
							name="searchConditionForm" id="searchConditionForm">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">CAS码:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="cas" id="cas"
											class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">中文名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="popcname"
											id="popcname" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">英文名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="popename"
											id="popename" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">污染物性质:</label>
									<div class="seach-element">
										<select  class="m-wrap small" name="disableFlg">
											<option value="" selected></option>
											<option value="0">禁用</option>
											<option value="1">限用</option>
											<option value="2">常规</option>
										</select>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="add()"><i class="icon-plus"></i>新增</a>
<!-- 							<a class="btn btngroup_usual" action-mode="ajax"  -->
<!-- 								action-url="systemController.do?callUpload&type=file&callback=importDictionaryData&multi=true&rename=true&isImport=true"  -->
<!-- 								action-pop="importDictionary" ><i class="icon-plus"></i>导入 -->
<!-- 							</a> -->
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"
								onclick="dataTabelSearch('pollDicTable','searchConditionForm')"><i
								class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover"
						id="pollDicTable">
						<thead>
							<tr>
								<th>CAS</th>
								<th class="hidden-480">中文通用名称</th>
								<th class="hidden-480">英文通用名称</th>
								<th class="hidden-480">类别</th>
								<th class="hidden-480">主要用途</th>
								<th class="hidden-480">污染物性质</th>
								<th class="hidden-480"></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
				<div id="importDictionary" class="modal hide fade" tabindex="-1" data-width="460"></div>
				<div id="confirmDiv" class="modal hide fade"></div>
			</div>
		</div>
	</div>

	<script>
		function refresh_pollDictionaryList() {
			$("#pollDicTable").dataTable().fnPageChange('first');
		}

		var $modal = $('#ajax-modal');
		jQuery(document)
				.ready(
						function() {
							registAjaxDataTable({
								id : "pollDicTable",
								actionUrl : "pollDictionaryController.do?datagrid",
								search : true,
								aoColumns : [
										{
											"mDataProp" : "cas"
										},
										{
											"mDataProp" : "popcname"
										},
										{
											"mDataProp" : "popename"
										},
										{
											"mDataProp" : "category"
										},
										{
											"mDataProp" : "use"
										},
										{
											"mData" : "disableFlg","dataDic":"pollproperty"
										},
										{
											"mData" : 'id',
											bSortable : false,
											"mRender" : function(data, type,
													full) {
												var url = ""
												return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''
														+ data
														+ '\')">编辑</a>'
														+ '&nbsp;<a class="btn mini yellow" onclick="del(\''
														+ data + '\')">删除</a>';
											}
										} ],
								initModals : [
// 										{
// 											"id" : "insert_dictionary",
// 											"operation" : "createwindow",
// 											"url" : "pollDictionaryController.do?addorupdate",
// 										},
// 										{
// 											"id" : "copy_standard",
// 											"operation" : "defined",
// 											"callBack" : function() {
// 												alert('sss');
// 											}
// 										},
// 										{
// 											"id" : "addBtn",
// 											"operation" : "windowsave",
// 											"url" : "pollDictionaryController.do?save",
// 											"formId" : "saveForm"
// 										} 
										],
								fnCallBefore : function(queryParams, aoData) {
									return $('#searchConditionForm')
											.getFormValue();
								},
							});
						});
		
		
		function add() {
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		     $modal.load('pollDictionaryController.do?addorupdate', '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });
		}
		

		function del(data) {
			$("#confirmDiv").confirmModal({
				heading : '请确认操作',
				body : '你确认删除所选记录?',
				callback : function() {
					$.ajax({
						type : "POST",
						url : "pollDictionaryController.do?del",
						data : "id=" + data,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								refresh_pollDictionaryList();
							} else {
								modalAlert(d.msg);
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
			$modal.load('pollDictionaryController.do?addorupdate&id='+ data, '', function() {
				$modal.modal();
				App.unblockUI(pageContent);
				 Validator.init();
			});
		}
		
	</script>
</body>
</html>