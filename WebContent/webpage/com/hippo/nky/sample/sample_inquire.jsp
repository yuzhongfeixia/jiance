<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<link href="assets/plugins/select2/select2_metro.css" rel="stylesheet" type="text/css" />
<script src="assets/plugins/select2/select2.min.js" type="text/javascript"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />

<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>

<script>
var $modal = $('#ajax-modal');
$('#searchBtn').on('click', function(){
	getTabInfo();
	setQueryParams('sample_inquire_tb',$('#searchConditionForm').getFormValue());
	$("#sample_inquire_tb").dataTable().fnPageChange('first');
});

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

function setFormValue(data){
	$('#year').val($('#'+data).val());
	$('#proselect').html("");
	$('#cityselect').html("");
	$('#areaselect').html("");
	getProjects({"showBelowGradeRepFlg" : "yes"});
}

function setMonTypeFormValue(data){
	$('#monitorType').val($("input[name='"+data.name+"']:checked").val());
	$('#proselect').html("");
	$('#cityselect').html("");
	$('#areaselect').html("");
	getProjects({"showBelowGradeRepFlg" : "yes"});
}

function changeTab(data) {
	if(data == '1') {
		$('#monitorType').val($("input[name='monitorType1']:checked").val());
		$('#year').val($('#year1').val());
	} else if (data == '2') {
		$('#monitorType').val($("input[name='monitorType2']:checked").val());
		$('#year').val($('#year2').val());
	} else {
		$('#monitorType').val($("input[name='monitorType3']:checked").val());
		$('#year').val($('#year3').val());
	}
	$('#proselect').html("");
	$('#cityselect').html("");
	$('#areaselect').html("");
	getProjects();
}
$(document).ready(function(){
	getProjects({"showBelowGradeRepFlg" : "yes"});
	registAjaxDataTable({
		id : "sample_inquire_tb",
		actionUrl : "samplingInfoController.do?statisticsDataGrid&rand=" + Math.random(),
		aoColumns:[
					{"mDataProp" : "rn"},
					{"mDataProp" : "agrname",
					 "mRender" : function(data, type, full) {
					  		return '<a onclick=\"toDetalList(\''+full.orgcode+'\')\">'+data+'</a>';
					 }	
					},
					{"mDataProp" : "count"},
					{"mDataProp" : "reportcount"}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3]
		} ]
	});
}); 

function toDetalList(orgcode){
	var projectCode = $("#projectCode").val();
	var pageContent = $('.page-content');
 	App.unblockUI(pageContent);
    $modal.load('samplingInfoController.do?toBarcodeInput&projectCode='+projectCode+'&orgCode='+orgcode, '', function(){
     $modal.modal({width:"800px"});
     App.unblockUI(pageContent);
   });
}
</script>
</head>	
<body class="page-header-fixed">
	<div class="row-fluid" >
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
					<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
						<div class="tabbable tabbable-custom">
							<input type="hidden" name="projectCode" id="projectCode" value="">
							<input type="hidden" name="monitorType" id="monitorType" value="1">
							<input type="hidden" name="year" id="year" value="${currYear}">
							<ul class="nav nav-tabs">
								<li class="active" onclick=""><a href="#tab_1_1" data-toggle="tab" onclick="changeTab('1')">省项目</a></li>
								<li><a href="#tab_1_2" data-toggle="tab" onclick="changeTab('2')">市项目</a></li>
								<li><a href="#tab_1_3" data-toggle="tab" onclick="changeTab('3')">县项目</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1_1">
									<div class="">
										<div style="float:left;padding-top:5px;">
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType1" value="1"  checked onclick="setMonTypeFormValue(this);"/>例行监测</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType1" value="2" onclick="setMonTypeFormValue(this);"/>普查</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType1" value="3" onclick="setMonTypeFormValue(this);"/>专项调查</label>
											<label class="radio" style="float:left;margin-right:20px;"><input type="radio" name="monitorType1" value="4" onclick="setMonTypeFormValue(this);"/>监督抽查</label>
										</div>
										<div style="float:left;">
											<select class="m-wrap" style="width:114px;" id="year1" name="year1" onchange="setFormValue('year1');">
												<c:forEach items="${yearList}" var="year">
													<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="">
										<select id="proselect" class="span12 m-wrap" size="5"></select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_2">
									<div class="">
										<div style="float:left;padding-top:5px;">
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType2" value="1"  checked onclick="setMonTypeFormValue(this);"/>例行监测</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType2" value="2" onclick="setMonTypeFormValue(this);"/>普查</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType2" value="3" onclick="setMonTypeFormValue(this);"/>专项调查</label>
											<label class="radio" style="float:left;margin-right:20px;"><input type="radio" name="monitorType2" value="4" onclick="setMonTypeFormValue(this);"/>监督抽查</label>
										</div>
										<div style="float:left;">
											<select class="m-wrap" style="width:114px;" id="year2" name="year2" onchange="setFormValue('year2');">
												<c:forEach items="${yearList}" var="year">
													<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
												</c:forEach>
											</select>	
										</div>
									</div>
									<div class="">
										<select id="cityselect" class="span12 m-wrap" size="5"></select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_3">
									<div class="">
										<div style="float:left;padding-top:5px;">
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType3" value="1"  checked onclick="setMonTypeFormValue(this);"/>例行监测</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType3" value="2" onclick="setMonTypeFormValue(this);"/>普查</label>
											<label class="radio" style="float:left;margin-right:10px;"><input type="radio" name="monitorType3" value="3" onclick="setMonTypeFormValue(this);"/>专项调查</label>
											<label class="radio" style="float:left;margin-right:20px;"><input type="radio" name="monitorType3" value="4" onclick="setMonTypeFormValue(this);"/>监督抽查</label>
										</div>
										<div style="float:left;">
											<select class="m-wrap" style="width:114px;" id="year3" name="year3" onchange="setFormValue('year3');">
												<c:forEach items="${yearList}" var="year">
													<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
												</c:forEach>	
											</select>
										</div>
									</div>
									<div class="">
										<select id="areaselect" class="span12 m-wrap" size="5" ></select>
									</div>
								</div>
							</div>
						</div>
						</form>
					</div>
					<div class="clearfix" >
						<div class="pull-right">
							<a id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="sample_inquire_tb">
						<thead>
							<tr>		
								<th class="hidden-480">序号</th>
								<th class="hidden-480">机构名称</th>
								<th class="hidden-480">任务数量</th>
								<th>上报数量</th>
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
</body>
</html>