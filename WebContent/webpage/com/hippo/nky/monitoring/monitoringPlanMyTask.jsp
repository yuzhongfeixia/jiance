<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>

<link href="assets/plugins/select2/select2_metro.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>

<script src="assets/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>

<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script>
LinkChange.init();
$(document).ready(function(){
	// 取得省市县项目
	getProjects();
	registAjaxDataTable({
		id : "monitoring_plan_mytask_table",
		actionUrl : "monitoringPlanController.do?myTaskDatagrid&rand=" + Math.random(),
		aoColumns:[
					{"mData" : "taskName"},
					{"mData" : "samplingCount"},
					{"mData" : "areaName"},
					{"mData" : "monitoringlinkName"},
					{"mData" : "agrName"},
					{"mData" : "distributionCount"},
					{"mData": "taskCode",
						"mRender" : function(data, type, full) {
							return '<a class="btn mini green" onclick="distribution(\''+ full.taskCode +'\')"><i class="icon-edit"></i>发布</a>';
						}
					}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3,4,5,6]
		} ]
	});
});

function distribution(taskCode){
	createwindow("","monitoringPlanController.do?myTaskDistributionSet&taskCode="+taskCode,"mytask_distribution_set_window");

}

function seachMyTask(){
	var projectSelect = $('.tab-pane.active select').val();
	$('#projectCode').val(projectSelect);
	setQueryParams('monitoring_plan_mytask_table',$('#my_task_form').getFormValue());
	$("#monitoring_plan_mytask_table").dataTable().fnPageChange('first'); 
}

function linkCountry(elem){
	var jsonParam = {};
	jsonParam["async"] = false;
	jsonParam["targetUrl"] = 'samplingInfoController.do?getCityOrCountryCodeList';
	var projectCode = $("#projectCode").val();

	var cityCode = $(elem).val();
	if (cityCode == '') {
		$("#countyCode").html('');
		return;
	}
	jsonParam["params"] = {"projectCode" : projectCode, "cityCode" : cityCode, "isCity" : false};
	jsonParam["after"] = "setCityLinkOptions";
	AjaxMode.nomalAction(jsonParam);
}
function setCityLinkOptions(data){
	var options = data.attributes.codeList;
	$("#countyCode").html(options);
}
// 弹出发布内容
// function linkCountry(elem){
// 	var jsonParam = {};
// 	jsonParam["async"] = false;
// 	jsonParam["targetUrl"] = 'samplingInfoController.do?getCityOrCountryCodeList';
// 	var projectCode = $("#projectCode").val();
// 	var cityCode = $(elem).val();
// 	jsonParam["params"] = {"projectCode" : projectCode, "cityCode" : cityCode, "isCity" : false};
// 	jsonParam["after"] = "setCityLinkOptions";
// 	AjaxMode.nomalAction(jsonParam);
// }
function setCityCode() {
	var projectCode = $('.tab-pane.active select').val();
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?getCityOrCountryCodeList",
		data : {"projectCode" : projectCode, "isCity" : true},
		success : function(data) {
			 var d = $.parseJSON(data);
			 var codeList = d.attributes.codeList;
			 $('#cityCode').html(codeList);

		}
	});
}

</script>

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<div class="tabbable tabbable-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
								<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
								<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1_1">
									<div>
									 	<!-- onChange="queryData('proselect');" -->
										<select id="proselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
										</select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_2">
									<div>
									 	<!-- onchange="queryData('cityselect');" -->
										<select id="cityselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
										</select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_3">
									<div>
										 <!-- onchange="queryData('areaselect');" -->
										<select id="areaselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
										</select>
									</div>
								</div>
							</div>
						</div>	
						<form action="#" class="form-horizontal" id="my_task_form">
							<input type="hidden" name="projectCode" id="projectCode" value="">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">任务名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small" id="taskName" name="taskName">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">抽样品种:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small" id="agrName" name="agrName">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">抽样地区:</label>
									<div class="seach-element">
										<div id="areasDiv">
											<t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${cityCodeList}" extend="{onChange:{value:'linkCountry(this)'}}"></t:dictSelect>
											<select id="countyCode" name="countyCode" class="m-wrap small"></select>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix" style="">
						<div class="pull-right">
							<a href="#" class="btn btngroup_seach" onclick="seachMyTask();"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="monitoring_plan_mytask_table">
						<thead>
							<tr>		
								<th>任务名称</th>
								<th>抽样数量</th>
								<th>抽样地区</th>
								<th>抽样环节</th>
								<th>抽样品种</th>
								<th>已分配数量</th>
								<th style="width:120px;">发布到手持终端</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="mytask_distribution_set_window" class="modal hide fade" tabindex="-1" data-width="600" ></div>
	<div id="mytask_distribution_view_window" class="modal hide fade" tabindex="-1" data-width="700" >
		<div class="row-fluid">
			<div class="span12">  
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-reorder"></i>
							<span class="hidden-480">任务分配情况</span>
						</div>
					</div>
					<div class="portlet-body form">
						<form action="#" class="form-horizontal">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="hidden-480">序号</th>
										<th class="hidden-480">任务名称</th>
										<th class="hidden-480">分配数量</th>
										<th class="hidden-480">分配时间</th>
										<th class="hidden-480">完成情况</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>	

	<script>
		jQuery(document).ready(function() {       
			//MyTaskTableManaged.init();
			registDataTable("monitoring_plan_myTask_tb1",false,"0,6");
			groupCheckBoxAction("monitoring_plan_task_tb2_checkBtn");
		});
		
	</script>
</body>
</html>