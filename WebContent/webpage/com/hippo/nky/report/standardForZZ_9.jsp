<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/scripts/plantSituationUtil2.js"></script>


<script>
var dataTable = null;
dataTable = $('#standardForZZ_9_table').dataTable({
	"bAutoWidth": true,
	"bPaginate" : false,
	"sDom" : "t<'row-fluid'<'span_paginate'>>",
	"aoColumnDefs" : [{'bSortable': false,"aTargets": [0,1,2,3,4,5,6,7,8,9] }],
	"aaSorting": []
});
function drewTableCells(htmls){	
	
	// 如果不是初始化datatable就先销毁元table
	if(dataTable != null){
		dataTable.fnDestroy();
	}
	// 设置拼接的字符串到table中
	$("#standardForZZ_9_table").html(htmls);
	
	//注册datatable组件
	dataTable = $('#standardForZZ_9_table').dataTable({
		"bAutoWidth": true,
		"bPaginate" : false,
		"sDom" : "t<'row-fluid'<'span_paginate'>>",
		"aoColumnDefs" : [{'bSortable': false,"aTargets": [0,1,2,3,4,5,6,7,8,9] }],
		"aaSorting": []
	});
}

jQuery(document).ready(function() {
	getProject();

});

//统计搜索
$('#statisticsBtn').on('click', function(){
	var projectLevel = $('#projectLevel').val();
	var year = $('#year').val();
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;

	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return;
	}

	$.ajax({
		async: false,
		type : 'POST',
		url : 'plantSituationController.do?getProvincialCitiesOverStandardDetail',
		data: {'projectCode':projectCode},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			if(dataJson.success){
				// 总抽样数统计列表取得
				var htmls = dataJson.attributes.htmls;
				
				drewTableCells(htmls);
			}
		}
	});
});

function getProject(){
	var industryCode = "${industryCode}";
	var type = "${type}";
	getProjectCode({"industryCode" : industryCode, "type" : type});
}

function exportExcel_pcosd(){
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	$('#projectCode').val(projectCode);
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return;
	}
	exportExcelByCustom('report.PlantSituationService.exportPcosd','','searchConditionForm')
}
</script>
<body class="page-header-fixed">
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
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<input type="hidden" id="projectCode" />
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">级别:</label>
									<div class="seach-element">
										<select class="m-wrap small" tabindex="1" name="projectLevel"
											id="projectLevel" onchange="getProject();">
											<option value="1">省任务</option>
											<option value="2">市任务</option>
											<option value="3">县任务</option>
										</select>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">年份:</label>
									<div class="seach-element">
										<select class="m-wrap small" tabindex="2" name="year" id="year"
											onchange="getProject();">
											<c:forEach items="${yearList}" var="year">
												<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
											</c:forEach>	
										</select>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">项目:</label>
									<div class="seach-element">
<!-- 										<select class="m-wrap small" tabindex="3" name="projectCode" id="projectCode"> -->
<!-- 										</select> -->
										<input class="m-wrap large" type="text" name="" id="projectNames" placeholder="选择项目" onclick="showProjectDiv()" readonly="readonly"></input><span style="color: red;">*点击选择项目</span>
										<div id="showProject" hidden="hidden" style="min-width:320px;background-color: white;position:absolute;z-index:99;border:1px solid;color: black"></div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a class="btn btngroup_seach" id="statisticsBtn"><i class="icon-search"></i>统计</a> 
							<a class="btn btngroup_usual" onclick="exportExcel_pcosd()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="standardForZZ_9_table" style="margin-top: 5px;">
						<thead>
							<tr>
								<th class="center hidden-480" >地市</th>
								<th class="center hidden-480" >抽样总数</th>
								<th class="center hidden-480" >超标数</th>
								<th class="center hidden-480" >超标率</th>
								<th class="center hidden-480" >合格率</th>
								<th class="center hidden-480" >名次排序</th>
								<th class="center hidden-480" >检出数</th>
								<th class="center hidden-480" >检出率</th>
								<th class="center hidden-480" >检出参数达三个数</th>
								<th class="center hidden-480" >检出率</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
