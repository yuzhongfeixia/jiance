<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/scripts/ui-modals.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/scripts/plantSituationUtil2.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script>
var industryCode = "${industryCode}";
var type = "${type}";
var selCondtion = "${selCondtion}";
getProjectCode({"industryCode" : industryCode, "type" : type});

// 取得默认的title列数组
var initTableNotSort = getTableTitleColumnsNumArray("table_countryDetectionAndOverPoll");

// 注册datatable组件
var dataTable = $('#table_countryDetectionAndOverPoll').dataTable({
	"bAutoWidth": true,
	"bPaginate" : false,
	"sDom" : "t<'row-fluid'<'span_paginate'>>",
	"aoColumnDefs" : [{'bSortable': false,"aTargets": initTableNotSort }]
});
function checkProjectIsSelected_cdao(){
	//var projectCode = $("#projectCode").val();
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	$('#projectCode').val(projectCode);
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return false;
	}
	return true;
}
function countryDetectionAndOverPollSeach(){
	if(!checkProjectIsSelected_cdao()){
		return;
	}
	getCountryDetectionAndOverPoll($("#projectCode").val());
}
function makeCountryDetectionAndOverPoll(data){
	if(data.success){
		var htmls = "";
		var countryAndOverList = data.attributes.countryAndOverList;
		// 如果没有值，显示默认的空表
		if(null == countryAndOverList || countryAndOverList.length < 1){
			htmls = '<thead><tr>' + 
						'<th class="center hidden-480">序号</th>';
						
			if('4' == selCondtion){
				html += '<th class="center hidden-480">品种名称</th>' ;
			} else {
				html += '<th class="center hidden-480">地市</th>';
				if('2' != selCondtion){
					html += '<th class="center hidden-480">区县</th>' ;
				}
			}
			
			html += '<th class="center hidden-480">抽样总数</th>' +
					'<th class="center hidden-480">超标个数</th>' +
					'<th class="center hidden-480">超标率</th>' + 
				'</tr></thead>';
		} else {
			htmls = getDataToTableHTMLS(3, true, countryAndOverList);
		}
		
		// 如果不是初始化datatable就先销毁元table
		if(dataTable != null){
			dataTable.fnDestroy();
		}
		
		// 前N列是固定的
		var tableColumnsNum = $($(htmls)[0]).find("tr:last th").length + initTableNotSort.length;
		// 根据列数多少设定table的宽度 
		$('#table_countryDetectionAndOverPoll').css({"width" : (100 * tableColumnsNum) + 'px'});
		
		// 重新替换html
		$("#table_countryDetectionAndOverPoll").html(htmls);
		
		// 设置不进行排序
		var aTargets = new Array();
		for(var i = 0; i < tableColumnsNum; i++){
			aTargets.push(i);
		}
		
		// 重新注册datatable
		dataTable = $('#table_countryDetectionAndOverPoll').dataTable({
			"bAutoWidth": true,
			"bPaginate" : false,
			"sDom" : "t<'row-fluid'<'span_paginate'>>",
			"aoColumnDefs" : [{'bSortable': false,"aTargets": aTargets }]
		});
	}
}
function getCountryDetectionAndOverPoll(projectCode){
	var jsonParam = {};
	var table_countryDetectionAndOverPoll_url = "";
	if('f' == industryCode){
		// 种植用
		table_countryDetectionAndOverPoll_url = 'plantSituationController.do?getCountryDetectionAndOverPollGrid&rand=';
	} else {
		// 畜禽用
		table_countryDetectionAndOverPoll_url = 'livestockSituationController.do?getCountryDetectionAndOverPollGrid&rand=';
	}
	jsonParam["targetUrl"] = table_countryDetectionAndOverPoll_url + Math.random();
	jsonParam["params"] = {"projectCode" : projectCode , "selCondtion" : selCondtion};
	jsonParam["after"] = "makeCountryDetectionAndOverPoll";
	AjaxMode.nomalAction(jsonParam);
}

function exportoverProofSampleDeatil(){
	if(!checkProjectIsSelected_cdao()){
		return;
	}
	if('f' == industryCode){
		// 种植用
		exportExcelByCustom('report.PlantSituationService.getPollDetectionInfo','','frm_countryDetectionAndOverPoll_seach');
	} else {
		// 畜禽用
		exportExcelByCustom('report.LivestockPlantSituationService.getPollDetectionInfo','','frm_countryDetectionAndOverPoll_seach');
	}
	
}
function getProjectCode_cdao(){
	getProjectCode({"industryCode" : industryCode, "type" : type});
}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-globe"></i>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<div class="clearfix">
						<form id="frm_countryDetectionAndOverPoll_seach">
							<input type="hidden" id="projectCode" />
							<div class="table-seach">
								<label class="help-inline seach-label">级别:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="1" name="projectLevel"
										id="projectLevel" onchange="getProjectCode_cdao()">
										<option value="1">省任务</option>
										<option value="2">市任务</option>
										<option value="3">县任务</option>
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">年份:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="2" name="year" id="year" onchange="getProjectCode_cdao()">
										<c:forEach items="${yearList}" var="year">
											<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
										</c:forEach>	
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">项目:</label>
								<div class="seach-element">
<!-- 									<select class="m-wrap small" tabindex="3" name="projectCode" id="projectCode"> -->
<!-- 									</select> -->
									<input class="m-wrap large" type="text" name="" id="projectNames" placeholder="选择项目" onclick="showProjectDiv()" readonly="readonly"></input><span style="color: red;">*点击选择项目</span>
									<div id="showProject" hidden="hidden" style="min-width:320px;background-color: white;position:absolute;z-index:99;border:1px solid;color: black"></div>
								</div>
							</div>
							<input type="hidden" name="selCondtion" value="${selCondtion}"/>
						</form>
					</div>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a class="btn btngroup_seach" id="btn_overProofSampleDeatil_seach" onclick="countryDetectionAndOverPollSeach()"><i class="icon-search"></i>统计</a> 
						<a class="btn btngroup_usual" onclick="exportoverProofSampleDeatil()"><i class="icon-share"></i>导出Excel</a>
					</div>
				</div>
				<div style="max-width:100%;overflow-x: auto;">
					<table class="table table-striped table-bordered table-hover"  id="table_countryDetectionAndOverPoll" style="min-width:100%">
						<thead>
							<tr>
								<th class="center hidden-480">序号</th>
								<c:if test="${selCondtion == '4'}">
									<th class="center hidden-480">品种名称</th>
								</c:if>
								<c:if test="${selCondtion != '4'}">
									<th class="center hidden-480">地市</th>
									<c:if test="${selCondtion != '2'}">
										<th class="center hidden-480">区县</th>
									</c:if>
								</c:if>
								<th class="center hidden-480">抽样总数</th>
								<th class="center hidden-480">超标个数</th>
								<th class="center hidden-480">超标率</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>