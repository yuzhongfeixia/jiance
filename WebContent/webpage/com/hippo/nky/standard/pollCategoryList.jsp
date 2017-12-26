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
									<label class="help-inline seach-label">版本名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">发布时间:</label>
									<div class="seach-element">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="monitoring_plan_project_txt1"/>
										<label class="help-inline">～</label>
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="monitoring_plan_project_txt2"/>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">状态:</label>
									<div class="seach-element">
									<t:dictSelect id="stopflag" field="stopflag" typeGroupCode="stopstart" hasLabel="false" defaultVal=""></t:dictSelect>
<!-- 										<select id="monitoring_plan_program_select1" class="m-wrap small" data-placeholder="" tabindex="1"> -->
<!-- 											<option value=""></option> -->
<!-- 											<option value="1">启用</option> -->
<!-- 											<option value="2">作废</option> -->
<!-- 										</select> -->
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="addData" class="btn btngroup_usual" data-toggle="modal"><i class="icon-plus"></i>新增</a>
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="pollCategoryTable">
						<thead>
							<tr>
								<th>版本名称</th>
								<th>发布机构</th>
								<th class="hidden-480">发布日期</th>
								<th class="hidden-480">状态</th>
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
	<div id="confirmDiv"></div>
<script>
$('.date-picker').datepicker({
    rtl : App.isRTL(),
    language: "zh",
    autoClose: true,
    format: "yyyy-mm-dd",
    todayBtn: true,
    clearBtn:true
});

	$('#searchBtn').on('click', function(){
		refresh_pollCategoryList();
	});
	
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"pollCategoryTable",
		   	dataDic : {"stopflag":"stopstart"},
			actionUrl:"pollCategoryController.do?datagrid&rand="+Math.random(),
			aoColumns:[
					{ "mDataProp": "standardCode"},
					{ "mDataProp": "publishOrg"},
					{ "mDataProp": "publishDate"},
					{ "mData": "stopflag",
						"mRender" : function(data, type, full) {
							return full.stopflag_name;
						}
					},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id="" data-toggle="modal" class="btn mini yellow" onclick="detailshow(\''+data+ '\');">详情</a>'+
							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="stop(\''+data+ '\')">停用</a>'+
							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="publish(\''+data+ '\')">发布</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
					}
					}],
				initModals:[
					{
					"id" : "addData",
					"operation" : "createwindow",
					"url":"limitStandardVersionController.do?addorupdate&rand="+Math.random(),
				   	},

					{
					"id" : "addBtn",
					"operation" : "windowsave",
					"url":"limitStandardVersionController.do?save",
					"formId":"saveForm"
					} 
				] ,
	        	fnCallBefore: function (queryParams,aoData){
	        		return  $('#searchConditionForm').getFormValue();
        		},
		});

		
	});
	function refresh_pollCategoryList() {  
		$("#pollCategoryTable").dataTable().fnPageChange('first');  
	} 

	function detailshow(data){
		loadContent(this, 'limitStandardController.do?limitStandard&versionId='+data);
	}
	//删除信息
	function del(data) {
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "limitStandardVersionController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_pollCategoryList();
			   			 }else {
			   				 alert(d.msg);
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
	     $modal.load('limitStandardVersionController.do?addorupdate&id='+data, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	    });
	}

	function stop(versionid){
		var url = "limitStandardVersionController.do?save";
		
		if(!judgeStop(versionid)){
			$("#ajax-modal").confirmModal({
				heading: '请确认操作',
				body: '确定停用该版本吗?',
				callback: function () {
					$.ajax({
						type : 'POST',
						url : url,
						data : {'id':versionid,'stopflag':1},
						success : function(data) {
							var dataJson = eval('(' + data + ')');
							if(dataJson.success){
								refresh_pollCategoryList();
								alert(dataJson.msg);
								//modalTips(dataJson.msg);
							} else {
								alert(dataJson.msg);
								//modalTips(dataJson.msg);
							}
						}
					});
				}
			});
		} else {
			modalAlert("已经停用的版本!");
		}
	}
	
	function publish(versionid){

		var url = "limitStandardVersionController.do?save";
		if(judgeStop(versionid)){
			alert(dataJson.msg);
			//modalAlert("已经停用的版本,不能再进行发布操作!");
		} else {
			if(!judgePublish(versionid)){
				$("#ajax-modal").confirmModal({
					heading: '请确认操作',
					body: '确定发布该版本吗?',
					callback: function () {
						$.ajax({
							type : 'POST',
							url : url,
							data : {'id':versionid,'publishflag':1},
							success : function(data) {
								var dataJson = eval('(' + data + ')');
								if(dataJson.success){
									refresh_pollCategoryList();
									alert(dataJson.msg);
									//modalTips(dataJson.msg);
								} else {
									alert(dataJson.msg);
									//modalTips(dataJson.msg);
								}
							}
						});
					}
				});
			} else {
				alert("已经发布的版本，不需要重新发布!");
				//modalTips("已经发布的版本，不需要重新发布!");
			}
		}
	}

</script>
</body>
