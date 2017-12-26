<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/scripts/table-managed.js"></script>
<script type="text/javascript" src="assets/scripts/ui-modals.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js"></script>
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//取得省市县项目
	getProjects({"proDefVal" : 'first'});
	
	checkTitle($('.tab-pane.active select').val());
	
	registAjaxDataTable({
		id : "grid_detectionInformation_sample",
		actionUrl : "detectionController.do?detectionInformationSamGrid&rand=" + Math.random(),
		queryParams : {"projectCode":$('.tab-pane.active select').val()},
		aoColumns:[
					{"mDataProp" : "RN"},
					{"mDataProp" : "spCode"},
					{"mDataProp" : "labCode"},
					{"mDataProp" : "sampleName"},
					{
						"mData" : 'rowIndex',
						bSortable : false,
						bSearchable : false,
						"mRender" : function(data, type, full) {
// 							var isAdd = '录入';
// 							var rowBtns = '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation&isDetail=true" action-before="getDetectionSampleParams(\'' + full.agrCode + '\')"><i class="icon-list-alt"></i>详情</a> \n';
// 							if(0 != data){
// 								isAdd = '编辑';
// 							}
// 							rowBtns += '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation" action-before="getDetectionSampleParams(\'' + full.agrCode + '\')"><i class="icon-list-alt"></i>' + isAdd + '</a>';
// 							return rowBtns;
							
							return '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation&isDetail=true" action-before="getDetectionSampleParams(\'' + full.agrCode + '\')"><i class="icon-list-alt"></i>详情</a> \n'+
							       '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation" action-before="getDetectionSampleParams(\'' + full.agrCode + '\')"><i class="icon-list-alt"></i>录入</a>';
						}
					},
					{"mDataProp" : "agrCode", "bVisible":false}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3]
		} ]
	});
	
	registAjaxDataTable({
		id : "grid_detectionInformation_poll",
		actionUrl : "detectionController.do?detectionInformationPollGrid&rand=" + Math.random(),
		queryParams : {"projectCode":$('.tab-pane.active select').val()},
		aoColumns:[
					{"mDataProp" : "RN"},
					{"mDataProp" : "pollName"},
					{
						"mData" : 'rowIndex',
						bSortable : false,
						bSearchable : false,
						"mRender" : function(data, type, full) {
// 							var isAdd = '录入';
// 							var rowBtns = '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation&isDetail=true" action-before="getDetectionPollParams(\'' + full.casCode + '\')"><i class="icon-list-alt"></i>详情</a> \n';
// 							if(0 != data){
// 								isAdd = '编辑';
// 							}
// 							rowBtns += '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation" action-before="getDetectionPollParams(\'' + full.casCode + '\')"><i class="icon-list-alt"></i>' + isAdd + '</a>';
// 							return rowBtns;
							return '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation&isDetail=true" action-before="getDetectionPollParams(\'' + full.casCode + '\')"><i class="icon-list-alt"></i>详情</a> \n'+
								   '<a class="btn green mini" action-mode="ajax" action-url="detectionController.do?addOrUpdateDetectionInformation" action-before="getDetectionPollParams(\'' + full.casCode + '\')"><i class="icon-list-alt"></i>录入</a>';
						}
					},
					{"mDataProp" : "casCode", "bVisible":false}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2]
		} ]
	});
	
});

function checkTitle(projectCode){
	if(projectCode != null){
		$.ajax({
			type : "POST",
			url : "detectionController.do?checkIsDetached&rand=" + Math.random(),
			data : {"projectCode":projectCode},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$("#title_th").text("");
					$("#title_th").text("制样编码");
					if ($("#codeLabel").text() == '样品条码:') {
						$("#codeLabel").text("制样编码:");
						$("input[name='spCode']").val("");
					}
					
				}else{
					$("#title_th").text("");
					$("#title_th").text("样品条码");
					if ($("#codeLabel").text() == '制样编码:') {
						$("#codeLabel").text("样品条码:");
						$("input[name='spCode']").val("");
					}
				}
			}
		});
	}
}

function getDetectionSampleParams(data){
	// 取得项目Code
	var projectCode = $('.tab-pane.active select').val();
// 	var checkResult = checkReport(projectCode);
// 	if(checkResult){
// 		modalAlert("项目已经上报,无法进行修改!");
// 	}
	// 取得当前行
	var eventRow = data.thisElem.closest("tr");
	// 取得实验室编码
	var labCode = $($(eventRow).children("td")[2]).text().trim();
	// 取得样品名
	var sampleName = $($(eventRow).children("td")[3]).text().trim();
	// 取得样品code
	var agrCode = data.arguments_0;
	if(isNotEmpty(projectCode)){
		data.params["projectCode"] = projectCode;
	}
	if(isNotEmpty(labCode)){
		data.params["labCode"] = labCode;
	}
	if(isNotEmpty(sampleName)){
		data.params["sampleName"] = sampleName;
	}
	if(isNotEmpty(agrCode)){
		data.params["agrCode"] = agrCode;
	}
	data.params["isSample"] = true;
	return data;
}
function getDetectionPollParams(data){
	var projectCode = $('.tab-pane.active select').val();
// 	var checkResult = checkReport(projectCode);
// 	if(checkResult){
// 		modalAlert("项目已经上报,无法进行修改!");
// 	}
	// 取得当前行
	var eventRow = data.thisElem.closest("tr");
	// 取得污染物CAS码
	var casCode = data.arguments_0;
	// 取得污染物名
	var pollName = $($(eventRow).children("td")[1]).text().trim();
	if(isNotEmpty(projectCode)){
		data.params["projectCode"] = projectCode;
	}
	if(isNotEmpty(casCode)){
		data.params["casCode"] = casCode;
	}
	if(isNotEmpty(pollName)){
		data.params["pollName"] = pollName;
	}
	data.params["isSample"] = false;
	return data;
}
function seachSampleInfo(){
	var taskselect = $('.tab-pane.active select');
	$('#projectCode1').val(taskselect.val());
	checkTitle(taskselect.val());
	setQueryParams('grid_detectionInformation_sample',$('#frm_detectionInformationSeach_sample').getFormValue());
	$('#grid_detectionInformation_sample').dataTable().fnClearTable(true);
}

function seachPollInfo(){
	var taskselect = $('.tab-pane.active select');
	$('#projectCode2').val(taskselect.val());
	setQueryParams('grid_detectionInformation_poll',$('#frm_detectionInformationSeach_poll').getFormValue());
	$('#grid_detectionInformation_poll').dataTable().fnClearTable(true);
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
							<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
							<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
							<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
								<div>
									<select id="proselect" class="span12 m-wrap" size="5" >
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_2">
								<div>
									<select id="cityselect" class="span12 m-wrap" size="5">
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_3">
								<div>
									<select id="areaselect" class="span12 m-wrap" size="5">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<div class="portlet box box_usual">
			<div class="portlet-body" style="border-top:1px solid #C8C8C8">
				<div class="controls">						
					<div class="tabbable tabbable-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_2_1" data-toggle="tab" onclick="seachSampleInfo()">样品</a></li>
							<li><a href="#tab_2_2" data-toggle="tab" onclick="seachPollInfo()">污染物</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_2_1">
								<div class="controls">
								<div class="alert alert-success">		
									<div class="clearfix">
										<form id="frm_detectionInformationSeach_sample">
											<input type="hidden" name="projectCode" id="projectCode1" value="">
											<div class="table-seach">
												<label class="help-inline seach-label" id="codeLabel">制样编码:</label>
												<div class="seach-element">
													<input name="spCode" type="text" placeholder="" class="m-wrap small">
												</div>
											</div>
											<div class="table-seach">
												<label class="help-inline seach-label">实验室编码:</label>
												<div class="seach-element">
													<input name="labCode" type="text" placeholder="" class="m-wrap small">
												</div>
											</div>
											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input name="sampleName" type="text" placeholder="" class="m-wrap small">
												</div>
											</div>
										</form>
									</div>
									</div>
									<div class="clearfix">
										<div class="pull-right">
											<a class="btn btngroup_seach" onclick="seachSampleInfo()"><i class="icon-search"></i>搜索</a>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover" id="grid_detectionInformation_sample">
										<thead>
											<tr>
												<th class="center hidden-480">序号</th>
												<th class="center hidden-480" id="title_th">制样编码</th>
												<th class="center hidden-480">实验室编码</th>
												<th class="center hidden-480">样品名称</th>
												<th class="center hidden-480" ></th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
							<div class="tab-pane" id="tab_2_2">
								<div class="controls">
									<div class="alert alert-success">		
										<div class="clearfix">
											<form id="frm_detectionInformationSeach_poll">
												<input type="hidden" name="projectCode" id="projectCode2" value="">
												<div class="table-seach">
													<label class="help-inline seach-label">污染物名称:</label>
													<div class="seach-element">
														<input name="pollName" type="text" placeholder="" class="m-wrap small">
													</div>
												</div>
											</form>
										</div>
									</div>
									<div class="clearfix">
										<div class="pull-right">
											<a class="btn btngroup_seach" onclick="seachPollInfo()"><i class="icon-search"></i>搜索</a>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover" id="grid_detectionInformation_poll">
										<thead>
											<tr>
												<th class="center hidden-480">序号</th>
												<th class="center hidden-480">污染物</th>
												<th class="center hidden-480" ></th>
											</tr>
										</thead>
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

<div id="ajax-modal" class="modal hide fade" tabindex="-1" data-width="760"></div>
