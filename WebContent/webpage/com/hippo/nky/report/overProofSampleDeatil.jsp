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

<script>
var industryCode = "${industryCode}";
var type = "${type}";
var sysUnit = "${sysUnit}";
getProjectCode({"industryCode" : industryCode, "type" : type});
var table_overProofSampleDeatil_url = "";
if('f' == industryCode){
	// 种植用
	table_overProofSampleDeatil_url = "plantSituationController.do?getOverProofSampleInfo&rand=";
} else {
	// 畜禽用
	table_overProofSampleDeatil_url = "livestockSituationController.do?getOverProofSampleInfo&rand=";
}
registAjaxDataTable({
	id : "table_overProofSampleDeatil",
	actionUrl : table_overProofSampleDeatil_url + Math.random(),
	aoColumns:[
				{"mDataProp" : "rowIndex"},
				{"mDataProp" : "SAMPLE_AREA_NAME"},
				{"mDataProp" : "D_CODE"},
				{"mDataProp" : "SP_CODE"},
				{"mDataProp" : "UNIT_FULLNAME"},
				{"mDataProp" : "CNAME"},
				{"mData": "MONITORING_LINK",
					"mRender" : function(data, type, full) {
						return full.MONITORING_LINK_name;
					}
				},
				{"mDataProp" : "POLL_VALUE"},
				{"mDataProp" : "OGRNAME"}
			],
	search : true,
	bPaginate : false,
	aoColumnDefs : [ {
		'bSortable' : false,
		'aTargets' : [0,1,2,3,4,5]
	} ]
});
function exportoverProofSampleDeatil(){
	if(!checkProjectIsSelected()){
		return;
	}
	//exportExcel('getOverProofSampleInfo','','frm_overProofSampleDeatil_seach');
	exportExcelByCustom('report.PlantSituationService.exportOverProofSampleInfo','','frm_overProofSampleDeatil_seach');
}
function overProofSampleDeatilSeach(){
	if(!checkProjectIsSelected()){
		return;
	}
	var jsonParams = {"sysUnit" : sysUnit};
	$.extend(jsonParams,$('#frm_overProofSampleDeatil_seach').getFormValue());
	
	setQueryParams('table_overProofSampleDeatil', jsonParams);
	$("#table_overProofSampleDeatil").dataTable().fnPageChange('first'); 
}
function checkProjectIsSelected(){
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	$('#projectCode').val(projectCode);
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return false;
	}
	return true;
}
function getProjectCode_opsd(){
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
						<form id="frm_overProofSampleDeatil_seach">
							<div class="table-seach">
								<label class="help-inline seach-label">级别:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="1" name="projectLevel"
										id="projectLevel" onchange="getProjectCode_opsd()">
										<option value="1">省任务</option>
										<option value="2">市任务</option>
										<option value="3">县任务</option>
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">年份:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="2" name="year" id="year" onchange="getProjectCode_opsd()">
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
							<input type="hidden" id="projectCode" /> 
							<input type="hidden" name="sysUnit" value="${sysUnit}"/>
						</form>
					</div>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a class="btn btngroup_seach" id="btn_overProofSampleDeatil_seach" onclick="overProofSampleDeatilSeach()"><i class="icon-search"></i>统计</a> 
						<a class="btn btngroup_usual" onclick="exportoverProofSampleDeatil()"><i class="icon-share"></i>导出Excel</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover"  id="table_overProofSampleDeatil">
					<thead>
						<tr>
							<th class="center hidden-480">序号</th>
							<th class="center hidden-480">抽样地点</th>
							<th class="center hidden-480">样品条码</th>
							<th class="center hidden-480">制样编码</th>
							<th class="center hidden-480">受检单位</th>
							<th class="center hidden-480">样品名称</th>
							<th class="center hidden-480">监测环节</th>
							<th class="center hidden-480">不合格参数及检测值(${sysUnit})</th>
							<th class="center hidden-480">检测机构</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>