<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
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

<script type="text/javascript" >
var $modal = $('#ajax-modal');
jQuery(document).ready(function() {
   registAjaxDataTable({
	   	id:"pollProperty_disable_table",
		actionUrl:"pollPropertyController.do?pollDisableDatagrid&rand="+Math.random(),
		search:true,
		aoColumns:[
				{ "mDataProp": "rn"},
				{ "mDataProp": "cas"},
				{ "mDataProp": "cname"},
				{
				"mData" : 'id',
				bSortable : false,
				"mRender" : function(data, type, full) {
					return '<a class="btn mini yellow" onclick="del(\''+data+'\',\''+0+'\')">删除</a>';
				}
				}],
			initModals:[
			] ,
	});
}); 

jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"pollProperty_enable_table",
			actionUrl:"pollPropertyController.do?pollEnableDatagrid&rand="+Math.random(),
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mDataProp": "cas"},
					{ "mDataProp": "cname"},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a class="btn mini yellow" onclick="del(\''+data+'\',\''+1+'\')">删除</a>';
					}
					}],
				initModals:[
				] ,
		});
	}); 

$('#searchBtn1').on('click', function(){
	setQueryParams('pollProperty_disable_table',$('#searchConditionForm1').getFormValue());
	$("#pollProperty_disable_table").dataTable().fnPageChange('first');
});

$('#searchBtn2').on('click', function(){
	setQueryParams('pollProperty_enable_table',$('#searchConditionForm2').getFormValue());
	$("#pollProperty_enable_table").dataTable().fnPageChange('first');
});

function disableAdd(data) {
   	var $modal = $('#ajax-modal'); 
   	var pageContent = $('.page-content');
   	App.blockUI(pageContent, false);
     $modal.load('pollPropertyController.do?addorupdate&disableFlg=0', '', function(){
     // $modal.modal();
      $modal.modal({width:"800px"});
      App.unblockUI(pageContent);
    });
}

function enableAdd(data) {
   	var $modal = $('#ajax-modal'); 
   	var pageContent = $('.page-content');
   	App.blockUI(pageContent, false);
     $modal.load('pollPropertyController.do?addorupdate&disableFlg=1', '', function(){
      //$modal.modal();
      $modal.modal({width:"800px"});
      App.unblockUI(pageContent);
    });
}

//删除信息
function del(data,flg) {
	$("#confirmDiv").confirmModal({
		heading: '请确认操作',
		body: '你确认删除所选记录?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : "pollPropertyController.do?del&rand=" + Math.random(),
				data : "id=" + data,
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				if (flg == 0) {
		   					$("#pollProperty_disable_table").dataTable().fnPageChange('first');
		   				} else {
		   					$("#pollProperty_enable_table").dataTable().fnPageChange('first');
		   				}
		   				modalTips(dataJson.msg);
		   			 }else {
		   				modalTips(dataJson.msg);
		   			 }
				}
			});
		}
	});
}

function refresh_pollPropertyList(data){
	if (data == 0) {
		$("#pollProperty_disable_table").dataTable().fnPageChange('first');
	} else {
		$("#pollProperty_enable_table").dataTable().fnPageChange('first');
	}
}
</script>

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
		
						
							<div class="controls">						
								<div class="tabbable tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1_1" data-toggle="tab" onclick="refresh_pollPropertyList(0)">禁用</a></li>
										<li><a href="#tab_1_2" data-toggle="tab" onclick="refresh_pollPropertyList(1)">限用</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="tab_1_1">
											<div class="controls">
											<div class="alert alert-success">
												<form action="#" class="form-horizontal" name="searchConditionForm1" id="searchConditionForm1">	
												<div class="clearfix">
													<div class="table-seach">
														<label class="help-inline seach-label">CAS码:</label>
														<div class="seach-element">
															<input type="text" placeholder="" name="cas" class="m-wrap small">
														</div>
													</div>
													<div class="table-seach">
														<label class="help-inline seach-label">污染物名称:</label>
														<div class="seach-element">
															<input type="text" placeholder="" name="pollname" class="m-wrap small">
														</div>
													</div>
												</div>
												</form>
												</div>
												<div class="clearfix">
													<div class="btn-group">
													<a class="btn btngroup_usual" id="disableAdd" data-toggle="modal" onclick="disableAdd();"><i class="icon-plus"></i>新增</a>
													</div>
													<div class="pull-right">
														<a href="#" id="searchBtn1" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
													</div>
												</div>
												<table class="table table-striped table-bordered table-hover" id="pollProperty_disable_table" style="margin-top: 5px;">
													<thead>
														<tr>
<!-- 														    <th class="center hidden-480" ><input type="checkbox" class="group-checkable" data-set="#wrwzd .checkboxes" /></th> -->
															<th class="center hidden-480">序号</th>
															<th class="center hidden-480">CAS码</th>
															<th class="center hidden-480">污染物名称</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</div>
										<div class="tab-pane" id="tab_1_2">
											<div class="controls">
											<div class="alert alert-success">
												<form action="#" class="form-horizontal" name="searchConditionForm2" id="searchConditionForm2">		
												<div class="clearfix">
													<div class="table-seach">
														<label class="help-inline seach-label">CAS码:</label>
														<div class="seach-element">
															<input type="text" placeholder="" name="cas" class="m-wrap small">
														</div>
													</div>
													<div class="table-seach">
														<label class="help-inline seach-label">污染物名称:</label>
														<div class="seach-element">
															<input type="text" placeholder="" name="pollname" class="m-wrap small">
														</div>
													</div>
												</div>
												</form>	
												</div>
												<div class="clearfix">
													<div class="btn-group">
													<a class="btn btngroup_usual" id="enableAdd" data-toggle="modal" onclick="enableAdd();"><i class="icon-plus"></i>新增</a>
													</div>
													<div class="pull-right">
														<a href="#" id="searchBtn2" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
													</div>
												</div>
												<table class="table table-striped table-bordered table-hover" id="pollProperty_enable_table" style="margin-top: 5px;">
													<thead>
														<tr>
<!-- 														  <th class="center hidden-480" ><input type="checkbox" class="group-checkable" data-set="#wrwzd .checkboxes" /></th> -->
															<th class="center hidden-480">序号</th>
															<th class="center hidden-480">CAS码</th>
															<th class="center hidden-480">污染物名称</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>
	
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade" tabindex="-1"></div>
</body>
		