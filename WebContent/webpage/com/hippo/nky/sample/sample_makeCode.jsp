<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script type="text/javascript" src="assets/scripts/ui-modals.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script>
LinkChange.init();
$(document).ready(function(){
	// 取得省市县项目
	getProjects();
	// 取得制样编码的按钮是否可用
	//getMakeCodeButtonEnable();
	registAjaxDataTable({
		id : "table_sample_makecode",
		actionUrl : "samplingInfoController.do?sampleMakCodeDatagrid&rand=" + Math.random(),
		queryParams : {"projectCode":$('.tab-pane.active select').val()},
		fnDrawCallback: function(oSettings) {
			var tdClass = $("#table_sample_makecode tbody tr td:eq(0)");
			if(tdClass.hasClass('dataTables_empty')){
				$("#btn_sample_makecode").attr("disabled", true);
			} else {
				$("#btn_sample_makecode").attr("disabled", false);
			}
		},
		aoColumns:[
					{"mDataProp" : "spCode"},
					{"mDataProp" : "cityAndCountry"},
					{"mDataProp" : "dCode"},
					{"mDataProp" : "agrname"},
					{"mDataProp" : "unitFullname"},
					{"mData": "monitoringLink",
						"mRender" : function(data, type, full) {
							return full.monitoringLink_name;
						}
					},
					{"mDataProp" : "samplingDate"}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3,4,5,6]
		} ]
	});
});


// function getMakeCodeButtonEnable(){
// 	// 取得制样编码的按钮是否可用
// 	$.ajax({
// 		async: false,
// 		type : 'POST',
// 		url : 'samplingInfoController.do?getMakeCodeButtonEnable',
// 		data : {"projectCode" : $('.tab-pane.active select').val()},
// 		success : function(data) {
// 			var dataJson = eval('(' + data + ')');
// 			if(dataJson.attributes.makeCodeEnable){
// 				$("#btn_sample_makecode").removeAttr("disabled");
// 			} else {
// 				$("#btn_sample_makecode").attr("disabled", true);
// 			}
// 		}
// 	});
// }

function seachSampleMakeCode(){
	var taskselect = $('.tab-pane.active select').val();
	$('#projectCode').val(taskselect);
	setQueryParams('table_sample_makecode',$('#frm_sampleMakeCode_seach').getFormValue());
	$("#table_sample_makecode").dataTable().fnPageChange('first'); 
}

function makeCode(){
	// 按钮如果是不可用的则不可点击
	var btn_enable = $(event.target).attr("disabled");
	if("disabled" == btn_enable || false == btn_enable){
		return;
	}
	// 设置制样编码
	$.ajax({
		async: false,
		type : 'POST',
		url : 'samplingInfoController.do?makeSpCode',
		data : {"projectCode" : $('.tab-pane.active select').val()},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			if(dataJson.success){
				// 弹出tips消息 
				modalTips(dataJson.msg);
			} else {
				// 弹出alert消息 
				modalAlert(dataJson.msg);
			}
		}
	});
	seachSampleMakeCode();
}
function queryData(data){
	var taskselect = $('#'+data);
	var queryParams = getQueryParams('table_sample_makecode');
	queryParams['projectCode'] = taskselect.val();
	$('#projectCode').val(taskselect.val());
	setQueryParams('table_sample_makecode',queryParams);
	$("#table_sample_makecode").dataTable().fnPageChange('first'); 		
}
// function linkMonitoringLink(elem){
// 	var jsonParam = {};
// 	var dictName = $(elem).val() + "monitoringLink";
// 	jsonParam["async"] = false;
// 	jsonParam["targetUrl"] = 'commonController.do?getDictionaryList';
// 	jsonParam["params"] = {"dictName" : dictName};
// 	jsonParam["after"] = "setMonitoringLinkOptions";
// 	AjaxMode.nomalAction(jsonParam);
// }
// function setMonitoringLinkOptions(data){
// 	var options = data.attributes.dictSelect;
// 	$("#monitoringLink").html(options);
// }
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
function checkProjectIsSelected_smc(){
	var projectCode = $('.tab-pane.active select').val();
	if(isEmpty(projectCode)){
		modalAlert("请选择一个项目!");
		return false;
	}
	return true;
}
function exportExcel_smc(){
	if(checkProjectIsSelected_smc()){
		exportExcel('exportSampleMakeCodeList','','frm_sampleMakeCode_seach');
	}
}

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
									<select id="proselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_2">
								<div>
									<select id="cityselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
									</select>
								</div>
							</div>
							<div class="tab-pane" id="tab_1_3">
								<div>
									<select id="areaselect" class="span12 m-wrap" size="5" onchange="setCityCode()">
									</select>
								</div>
							</div>
						</div>
					</div>	
					
					<div class="clearfix">
						<form id="frm_sampleMakeCode_seach">
							<input type="hidden" name="projectCode" id="projectCode" value="">
							<div class="table-seach">
								<label class="help-inline seach-label">地区:</label>
								<div class="seach-element">
									<div id="areasDiv">
										<t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${cityCodeList}" extend="{onChange:{value:'linkCountry(this)'}}"></t:dictSelect>
										<select id="countyCode" name="countyCode" class="m-wrap small"></select>
									</div>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">条 码:</label>
								<div class="seach-element">
									<input name="dCode" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">样品名称:</label>
								<div class="seach-element">
									<input name="sampleName" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">受检单位:</label>
								<div class="seach-element">
									<input name="unitFullname" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<%--<div class="table-seach">
								<label class="help-inline seach-label">行业:</label>
								<div class="seach-element">
									<t:dictSelect id="industry" field="industry" typeGroupCode="industry" hasLabel="false" 
										extend="{onChange:{value:'linkMonitoringLink(this)'}}"></t:dictSelect>
								</div>
							</div> --%>
							<div class="table-seach">
								<label class="help-inline seach-label">抽样环节:</label>
								<div class="seach-element">
									<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false" 
										extend="{class:{value:'small'}}"></t:dictSelect>
								</div>
							</div>
						</form>
					</div>
				</div>
				
				<div class="clearfix">
					<div class="pull-left"><a id="btn_sample_makecode" href="#" class="btn btngroup_usual" onclick="makeCode()" disabled><i class="icon-list-alt"></i>生成制样编码</a></div>
					<div class="pull-right">
							<a id="btn_sample_makecode_seach" href="#" class="btn btngroup_seach" onclick="seachSampleMakeCode()"><i class="icon-search"></i>搜索</a> 
							<a href="#" class="btn btngroup_usual" onclick="exportExcel_smc();"><i class="icon-share"></i>导出Excel</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover"  id="table_sample_makecode">
					<thead>
						<tr>
							<th class="center">制样编码</th>
							<th class="center">地区</th>
							<th class="center">条码</th>
							<th class="center">样品名称</th>
							<th class="center">受检单位</th>
							<th class="center" >抽样环节</th>
							<th class="center" >抽样时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
