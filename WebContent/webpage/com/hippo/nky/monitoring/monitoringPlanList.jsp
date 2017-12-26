<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>

<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/table-managed.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js"></script>
<script src="assets/scripts/monitoring_plan_program.js"></script>
<script src="assets/scripts/ui-jqueryui.js"></script> 

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" id="monitoring_plan_search_form" onSubmit="dataTabelSearch('monitoring_plan_program_tb1','monitoring_plan_search_form');return false;">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">方案名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small" name="name">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="monitoring_plan_program_btn1" class="btn btngroup_usual"> <i class="icon-plus"></i>添加方案</a>
						</div>
						<div class="pull-right">
							<a href="#" class="btn btngroup_seach" onclick="dataTabelSearch('monitoring_plan_program_tb1','monitoring_plan_search_form')"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<div id="div_monitoring_plan_program_tb1">
						<table class="table table-striped table-bordered table-hover" id="monitoring_plan_program_tb1">
							<thead>
								<tr>
									<th class="hidden-480">序号</th>
									<th class="hidden-480">方案名称</th>
									<th class="hidden-480">方案级别</th>
									<th class="hidden-480">监测类型</th>
									<th class="hidden-480">发布单位</th>
									<th class="hidden-480">发布时间</th>
									<th class="hidden-480">状态</th>	
									<th ></th>
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
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="monitoring_plan_program_window1" class="modal hide fade" tabindex="-1" data-width="1100"></div>
	<div id="monitoring_plan_program_window2" class="modal hide fade" tabindex="-1" data-width="760"></div>
	<script>
		jQuery(document).ready(function() {       
     	   registAjaxDataTable({
   		   	id:"monitoring_plan_program_tb1",
   			actionUrl:"monitoringPlanController.do?datagrid",
   			search:true,
   			aoColumns:[
					{ "mData": "rowIndex"},
   					{ "mData": "name"},
   					{ "mData": "plevel","dataDic":"departgrade"},
   					{ "mData": "type","dataDic":"monitorType"},
   					{ "mData": "departname"},
   					{ "mData": "releasetime","dateFormat":"yyyy-MM-dd"},
   					{ "mData": "state","dataDic":"plan_state"},
   					{ "mData": 'id',
   					  "button":[
   							{
   								"className":"btn mini green",
   								"onclick":"monitoringplan_publishPlan(id,state)",
   								"buttonName":"发布",
   								"dataAuthority":[{"state":"0"}]
   							},{
   								"className":"btn mini green",
   								"onclick":"dataTableUpdate('monitoringPlanController.do?addorupdate',id,'monitoring_plan_program_window1')",
   								"buttonName":"编辑",
   								"dataAuthority":[{"state":"0"}]
   							},{
   								"className":"btn mini green",
   								"onclick":"dataTableView('monitoringPlanController.do?addorupdate',id,'monitoring_plan_program_window1')",
   								"buttonName":"查看",
   								"dataAuthority":[{"state":"1,2"}]
   							},{
   								"className":"btn mini yellow",
   								"onclick":"monitoringplan_stopPlan(id)",
   								"buttonName":"停用",
   								"dataAuthority":[{"state":"1"}]
   							},{
   								"className":"btn mini yellow",
   								"onclick":"dataTableDel('monitoringPlanController.do?del',id,'monitoring_plan_program_tb1')",
   								"buttonName":"删除",
   								"dataAuthority":[{"state":"0"}]
   							}]
   						}],	
   		   	   initModals:[{
   	               		"id" : "monitoring_plan_program_btn1",
   	               		"modalId" : "monitoring_plan_program_window1",
   						"operation" : "createwindow",
   						"url":"monitoringPlanController.do?addorupdate",
   	   	   			}] 
   			});
		});
		function monitoringplan_publishPlan(id){
			var data = {id:id,state:"1"};
			stateUpdate('monitoringPlanController.do?stateUpdate','确认发布该方案吗?',data,'monitoring_plan_program_tb1');
		}
		function monitoringplan_stopPlan(id){
			var data = {id:id,state:"2"};
			stateUpdate('monitoringPlanController.do?stateUpdate','确认停用该方案吗?',data,'monitoring_plan_program_tb1');
		}
	</script>
</body>
</html>