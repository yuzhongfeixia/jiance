<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />

<script type="text/javascript" src="assets/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>   
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script>
//注册datatable组件
var dataTable = null;
var $modal = $('#ajax-modal');
$('#startNO').on('click', function(){
	var projectCode = $('#projectCode').val();
	var labPre = $("#labPre").val();
 	var startSer = $("#startSer").val();
 	//console.info($("#detection_tb tbody tr"));
	var trid = $("#detection_tb tbody tr:eq(0)").attr("id");
	if(isEmpty(trid)){
		modalAlert("没有可编号的样品数据!");
		return;
	}

	if(isEmpty(labPre)){
		modalAlert("请填写实验室编码前缀!",{'callBack':function(){
			$("#labPre").focus();
		}});
		return;
	}
	
	if(isEmpty(startSer)){
		modalAlert("请填写起始序号!",{'callBack':function(){
			$("#startSer").focus();
		}});
		
		return;
	}
	
	if ($("#title_th").text() == "制样编码") {
		//验证实验室编码前缀是否已经使用
		if (!validateLabPre(labPre)) {
			modalAlert("该实验室编码前缀已经使用!");
			return;
		}		
	}


	$.ajax({
		type : "POST",
		url : "detectionController.do?updateDetection&rand=" + Math.random(),
		data : {'projectCode':projectCode,'labPre': labPre,'startSer':startSer},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				modalTips(d.msg);
				$modal.modal('hide');
				refreshListToFirst($('#detection_tb'));
			} else {
				modalAlert(d.msg);
			}
		}
	});
});

function validateLabPre(labPre) {
	var result = false;
	$.ajax({
		async: false,
		type : "POST",
		url : "detectionController.do?validateLabPre&rand=" + Math.random(),
		data : {'labPre': labPre},
		success : function(data) {
			var d = $.parseJSON(data);
			result = d.attributes.labPreFlg;
		}
	});
	return result;
}

function queryData(data){
	var taskselect = $('#'+data);
	var projectCode = taskselect.val();
	$('#projectCode').val(projectCode);
	checkTitle();
	getTableData(projectCode); 	
	setDefaultPrefix();
// 	$.ajax({
// 		type : "POST",
// 		url : "detectionController.do?setDefaultPrefix",
// 		data : {"projectCode":$('#projectCode').val()},
// 		success : function(data) {
// 			var d = $.parseJSON(data);
// 			var labCodePre = d.attributes.labCodePre;
// 			var maxLabSer = d.attributes.maxLabSer;
// 			if (labCodePre != null) {
// 				$("#labPre").val(labCodePre);
// 			 	$("#startSer").val(parseInt(maxLabSer) + 1);
// 			 	$("#labPre").prop("disabled", true);
// 			 	$("#startSer").prop("disabled", true);
// 			} else {
// 				$("#labPre").val("");
// 			 	$("#startSer").val("");
// 			 	$("#labPre").prop("disabled", false);
// 			 	$("#startSer").prop("disabled", false);
// 			}
// 		}
// 	});
}

function checkTitle(projectCode){
	if(projectCode != null){
		$.ajax({
			type : "POST",
			url : "detectionController.do?checkIsDetached&rand=" + Math.random(),
			data : {"projectCode":$('#projectCode').val()},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$("#title_th").text("");
					$("#title_th").text("制样编码");
				}else{
					$("#title_th").text("");
					$("#title_th").text("样品条码");
				}
			}
		});
	}
}
$(document).ready(function(){
	$("#startSer").inputmask({"mask": "9","repeat": 10,"greedy": false}); 
	getProjects();
	$("#proselect option:eq(0)").attr("selected","selected");
	$('#projectCode').val($("#proselect option:eq(0)").attr("value"));
	var projectCode = $("#projectCode").val();
	getTableData(projectCode);
	setDefaultPrefix();
});

function getTableData(projectCode){
	checkTitle(projectCode);
	if(dataTable != null){
		dataTable.fnDestroy();
	}
	
	dataTable = registAjaxDataTable({
		id : "detection_tb",
		actionUrl : "detectionController.do?datagrid&sampleStatus=3&projectCode="+projectCode+"&rand=" + Math.random(),
		aoColumns:[
					{"mDataProp" : "code"},
					{"mDataProp" : "agrname"}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1]
		} ]
/* 		,"fnDrawCallback": function( oSettings ) {
			var oTable = $('#detection_tb').dataTable();
			var parmArray = oTable.fnSettings().aoColumns;
			$.each(parmArray,function(index,data){
				if(!data.bVisible){
					oTable.fnSetColumnVis(index, false);
					return;
				}
    		});
			var total = oTable.fnSettings()._iRecordsTotal;
			if(total == 0){
				modalAlert($("select option:selected").text()+"还没有分配检测任务");
			}
		} */
	});
}

function setDefaultPrefix() {
	$.ajax({
		type : "POST",
		url : "detectionController.do?setDefaultPrefix",
		data : {"projectCode":$('#projectCode').val()},
		success : function(data) {
			var d = $.parseJSON(data);
			var labCodePre = d.attributes.labCodePre;
			var maxLabSer = d.attributes.maxLabSer;
			
			if (labCodePre != null) {
				$("#labPre").val(labCodePre);
			 	$("#startSer").val(parseInt(maxLabSer) + 1);
			 	$("#labPre").prop("disabled", true);
			 	$("#startSer").prop("disabled", true);
			} else {
				$("#labPre").val("");
			 	$("#startSer").val("");
			 	$("#labPre").prop("disabled", false);
			 	$("#startSer").prop("disabled", false);
			}
		}
	});
}
</script>
</head>	
<body class="page-header-fixed">
	<input type="hidden" name="projectCode" id="projectCode" value="">
	<div class="row-fluid" >
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe">样品接收</i></div>
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
									<div class="controls">
										<select id="proselect" class="span12 m-wrap" size="5" onchange="queryData('proselect');"></select>
									</div>
									<!-- <div style="float:right;"><span class="message">2013年第三次例行监测:<font color="red">230个样品，2013年8月10号前上报</font></span></div> -->
								</div>
								<div class="tab-pane" id="tab_1_2">
									<div class="controls">
										<select id="cityselect" class="span12 m-wrap" size="5" onchange="queryData('cityselect');"></select>
									</div>
									<!-- <div style="float:right;"><span class="message">2013年第三次例行监测:<font color="red">230个样品，2013年8月10号前上报</font></span></div> -->
								</div>
								<div class="tab-pane" id="tab_1_3">
									<div>
										<select id="areaselect" class="span12 m-wrap" size="5" onchange="queryData('areaselect');"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">实验室编码前缀:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" id="labPre" name="labPre"/>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">起始序号:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" id="startSer" name="startSer"/>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a href="#" class="btn btngroup_usual" id="startNO"><i class="icon-list-alt"></i>开始编号</a>
						</div>
				 	</div>
					<table class="table table-striped table-bordered table-hover" id="detection_tb">
						<thead>
							<tr>		
								<th class="hidden-480" id="title_th">样品条码</th>
								<th class="hidden-480">样品名称</th>
<!-- 								<th class="hidden-480">实验室编码</th> -->
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