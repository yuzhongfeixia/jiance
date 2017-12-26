<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js"></script>
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>

<style type="text/css">
.right-height{
	height:36px;
}
.right-height-radio{
	margin-top: 10px;
	margin-left: 5px;
	vertical-align: top;
	float: left;
}
.control-item{
	width:350px;
	float:left;
}
</style>

<script type="text/javascript">
var dataTable = null;
$(document).ready(function(){
	//取得省市县项目
	getProjects({"proDefVal" : 'first'});
	getTabInfo();
	queryReportList($("#proselect").val());
});


function queryReportList(projectId, selCondVal){
	showTips("正在加载,请稍等...","400",1000);
	var projectCode = "";
	if(isNotEmpty(projectId)){
		projectCode = projectId;
	} else {
		projectCode = $('.tab-pane.active select').val();
	}
	if(isEmpty(projectCode)){
		return;
	}
	var selCondtion = "";
	if(isNotEmpty(selCondVal)){
		selCondtion = selCondVal;
	}
	var jsonParam = {};
	jsonParam["targetUrl"] = 'detectionController.do?getDetectionReportList';
	jsonParam["params"] = {"projectCode" : projectCode , "selCondtion" : selCondtion};
	jsonParam["after"] = "makeReportList";
	jsonParam["async"] = true;
	AjaxMode.nomalAction(jsonParam);
}
function makeReportList(data){
	if(data.success){
		var htmls = "";
		
		// 上报list
		var reportList = data.attributes.reportList;
		if(reportList.length > 0 ){
			htmls = getDataToTableHTMLS(0, true, reportList);
		} else {
			htmls += '<thead><tr>';
			htmls += '<th class="center hidden-480">序号</th>';
			htmls += '<th class="center hidden-480">实验室编码</th>';
			htmls += '<th class="center hidden-480">样品名称</th>';
			htmls += '</tr></thead>';
		}
		
		// 如果不是初始化datatable就先销毁元table
		if(dataTable != null){
			dataTable.fnDestroy();
		}
		
		// 取得title
		var tableColumnsNum = $($(htmls)[0]).find("tr:last th").length;
		// 根据列数多少设定table的宽度 
		$('#table_detectionInformationReport').css({"width" : (150 * tableColumnsNum) + 'px'});
		
		// 设置拼接的字符串到table中
		$("#table_detectionInformationReport").html(htmls);
		$( 'div.tipsClass' ).fadeOut();
		// 设置不进行排序
		var aTargets = new Array();
		for(var i = 0; i < tableColumnsNum; i++){
			aTargets.push(i);
		}
		
		// 注册datatable组件
		dataTable = $('#table_detectionInformationReport').dataTable({
			"bAutoWidth": true,
			"bPaginate" : false,
			"sDom" : "",
			"aoColumnDefs" : [{'bSortable': false,"aTargets": aTargets}],
			"aaSorting": []
		});
	}
}

function getDetecthedFlg(projectCode) {
	var detecthedFlg = "";
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?getDetecthedFlg",
		data : "projectCode="+projectCode,
		success : function(data) {
			 var d = $.parseJSON(data);
			 detecthedFlg = d.attributes.detecthedFlg;
		}
	});
	return detecthedFlg;
}

function getProjectComplete(projectCode){
	var compFlg = false;
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?getProjectComplete",
		data : "projectCode="+projectCode,
		success : function(data) {
			 var d = $.parseJSON(data);
			 compFlg = d.attributes.isSetted;
		}
	});
	return compFlg;
}

function getNotRecvSample(projectCode){
	var hasNotRecvFlg = false;
	$.ajax({
		async: false,
		type : "POST",
		url : "detectionController.do?getNotRecvSample",
		data : "projectCode="+projectCode,
		success : function(data) {
			 var d = $.parseJSON(data);
			 hasNotRecvFlg = d.attributes.hasNotRecvFlg;
		}
	});
	return hasNotRecvFlg;
}

function setDetectionReported(){
	var tdClass = $("#table_detectionInformationReport tbody tr td:eq(0)");
	if(tdClass.hasClass('dataTables_empty')){
		modalAlert("没有可上报的样品数据!");
		return;
	}
	var projectCode = $('.tab-pane.active select').val();
	// 取抽检分离flg，0:抽检不分离;1:抽检分离
	var detecthedFlg = getDetecthedFlg(projectCode);
	if (detecthedFlg == '0') {
		if (getProjectComplete(projectCode)) {
			if (getNotRecvSample(projectCode)) {
				modalAlert("该任务下还有正在检测的样品信息，暂不可以上报!");
				return;
			}
		} else {
			modalAlert("该任务下还有正在抽样的样品信息，暂不可以上报!");
			return;
		}
	}
	
	// 校验任务完成情况（抽样数量大于等于任务数，检测数量等于任务数）
	var checkRtn = checkDetectionComlete(projectCode);
	var eCheckRtn = eval("("+checkRtn+")");
	if (eCheckRtn.result == "1") {
		modalAlert("[项目抽样总数："+eCheckRtn.allCount+"检测完成数："+eCheckRtn.jcCount+"],检测完成数量必须等于任务总数!");
		return;
	} 

	$("#confim_all_report_modal").confirmModal({
		heading : '请确认操作',
		body : '是否将检测结果进行上报?',
		callback : function () {
			var jsonParam = {};
			jsonParam["targetUrl"] = 'detectionController.do?setDetectionReported';
			jsonParam["params"] = {"projectCode" : projectCode};
			jsonParam["after"] = "reportedComplete";
			AjaxMode.nomalAction(jsonParam);
		}
	});
}
function reportedComplete(){
	modalTips('操作成功');
	queryReportList();
}

// 检测信息上报，验证任务完成数量
function checkDetectionComlete(data) {
	var projectStatisticalInfo = "";
	var	paramUrl = "monitoringProjectController.do?detectionComleteStatistical&projectCode="+data;

	$.ajax({
		async: false,
		type : "POST",
		url : paramUrl,
		data : "",
		success : function(data) {
			 var d = $.parseJSON(data);
			 projectStatisticalInfo = d.attributes.projectStatisticalInfo;
		}
	});
	if (parseInt(projectStatisticalInfo.jcCount) < parseInt(projectStatisticalInfo.allCount)) {
		//return "1";// 检测完成数量没达标
		return "{'result':'1','allCount':'"+projectStatisticalInfo.allCount+"','cycmCount':'"+projectStatisticalInfo.cycmCount+"','jcCount':'"+projectStatisticalInfo.jcCount+"'}";
	}
	return "{'result':'0'}";
}
function getTabInfo() {
	var tab1 = $('#tab_1_1');
	var tab2 = $('#tab_1_2');
	var tab3 = $('#tab_1_3');
	if (tab1.attr('class') == 'tab-pane active') {
		$('#projectCode').val($('#proselect').val());
	} else if (tab2.attr('class') == 'tab-pane active') {
		$('#projectCode').val($('#cityselect').val());
	} else if (tab3.attr('class') == 'tab-pane active'){
		$('#projectCode').val($('#areaselect').val());
	}
	setTabSelect();
}
function exportReport() {
	getTabInfo();
	if ($("#projectCode").val() == "") {
		modalAlert("请选择一个项目!");
		return;
	}
	exportExcelByCustom('detection.DetectionService.exprotReport','','searchConditionForm');
}
</script>

<div class="row-fluid">
	<div class="span12">
		<!-- BEGIN EXAMPLE TABLE PORTLET-->
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe"></i></div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">		
					<div class="tabbable tabbable-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1_1" data-toggle="tab" onclick="queryReportList($('#proselect').val())">省项目</a></li>
							<li><a href="#tab_1_2" data-toggle="tab" onclick="queryReportList($('#cityselect').val())">市项目</a></li>
							<li><a href="#tab_1_3" data-toggle="tab" onclick="queryReportList($('#areaselect').val())">县项目</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
								<div>
									<select id="proselect" name="projectCode" class="span12 m-wrap" size="5" onclick="queryReportList()">
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_2">
								<div>
									<select id="cityselect" name="projectCode" class="span12 m-wrap" size="5" onclick="queryReportList()">
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_3">
								<div>
									<select id="areaselect" name="projectCode" class="span12 m-wrap" size="5" onclick="queryReportList()">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix">
					<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
						<input type="hidden" name="projectCode" id="projectCode" value="">
					</form>
					<div class="btn-group">
						<a class="btn btngroup_usual" onclick="setDetectionReported()"><i class="icon-upload-alt"></i>检测信息上报</a>
					</div>
					<div class="pull-right" style="margin-left:5px;">
						<a class="btn btngroup_usual" onclick="exportReport()">导出Excel<i class="icon-share"></i></a>
					</div>
					<div class="pull-right right-height">
						<label class="radio right-height-radio"><input id="selCondtion" name="selCondtion" type="radio" value="" checked onclick="queryReportList()">所有</label>
						<label class="radio right-height-radio"><input id="selCondtion" name="selCondtion" type="radio" value="1" onclick="queryReportList('', this.value)">检出</label>
						<label class="radio right-height-radio"><input id="selCondtion" name="selCondtion" type="radio" value="2" onclick="queryReportList('', this.value)">超标</label>
						<label class="radio right-height-radio"><input id="selCondtion" name="selCondtion" type="radio" value="3" onclick="queryReportList('', this.value)">未检</label>
					</div>
				</div>
				<div style="max-width:100%;overflow-x: auto;">
					<table class="table table-striped table-bordered table-hover"  id="table_detectionInformationReport" style="min-width:100%">
					</table>
				</div>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</div>
<div id="confim_all_report_modal" class="modal hide fade" tabindex="-1" data-width="360"></div>