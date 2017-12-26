<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="assets/scripts/plantSituationUtil2.js"></script>
<style type="text/css">
.control-item{
	width:350px;
	float:left;
}

select.length1{
	width:130px;
}

.styletd1 tr td{
	padding-top: 1px;
	padding-bottom: 1px
}
</style>
</head>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
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
<!-- 									<select class="m-wrap small" tabindex="3" name="projectCode" -->
<!-- 										id="projectCode"> -->
<!-- 									</select> -->
									<input class="m-wrap large" type="text" name="" id="projectNames" placeholder="选择项目" onclick="showProjectDiv()" readonly="readonly"></input><span style="color: red;">*点击选择项目</span>
									<div id="showProject" hidden="hidden" style="min-width:320px;background-color: white;position:absolute;z-index:99;border:1px solid;color: black"></div>
								</div>
							</div>
						</div>
						<input type="hidden" id="flg" name="flg" value="3">
					</form>
					</div>
					<div class="clearfix" >
						<div class="pull-right">
							<a class="btn btngroup_seach" id="statisticsBtn"><i class="icon-search"></i>统计</a> 
<!-- 							<a class="btn btngroup_usual" onclick="exportExcelByCustom('report.LivestockPlantSituationService.exportFreshMilk','','searchConditionForm')">导出Excel<i class="icon-share"></i></a> -->
							<a class="btn btngroup_usual" onclick="doExportExcel()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" style="margin-top: 5px;" id="staticts_table">
						<thead>
							<tr>
								<th class="center hidden-480" >排名</th>
								<th class="center hidden-480" >地市</th>
								<th class="center hidden-480" >抽样总数</th>
								<th class="center hidden-480" >合格数</th>
								<th class="center hidden-480" >合格率</th>
							</tr>
						</thead>
					</table>					
				</div>
			</div>
		</div>
	</div>
	<script>
	    var dataTable = null;
	    dataTable = $('#staticts_table').dataTable({
	    	"bAutoWidth": true,
	    	"bPaginate" : false,
	    	"bSort": false,
	    	"sDom" : "t<'row-fluid'<'span_paginate'>>"
	    });
		
		var industryCode = "${param.industryCode}";
		var type = "${param.type}";
		jQuery(document).ready(function() {
			getProject();
		});
		
		function getDataTable(projectCode) {
			$.ajax({
				async: false,
				type : 'POST',
				url : 'livestockSituationController.do?getFreshMilkMonitor',
				data: {'projectCode':projectCode,'industryCode':industryCode},
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					if(dataJson.success){
						var htmls = dataJson.attributes.htmls;
						drewTableCells(htmls);
					}
				}
			});
		}	
		
		function drewTableCells(htmls){
			if (htmls == "") {
	 			var htmls = '<thead><tr>';
	 			htmls += '<th class="center hidden-480">排名</th>';
	 			htmls += '<th class="center hidden-480">地市</th>';
	 			htmls += '<th class="center hidden-480">抽样总数</th>';
	 			htmls += '<th class="center hidden-480">合格数</th>';
	 			htmls += '<th class="center hidden-480">合格率</th>';
	 			htmls += '</tr></thead>';
			}
// 			var c = 0;
// 			var htmls = '<thead><tr>';
// 			htmls += '<th class="center hidden-480" colspan="1" rowspan="2" style="vertical-align: middle !important">排名</th>';
// 			htmls += '<th class="center hidden-480" colspan="1" rowspan="2" style="vertical-align: middle !important">市别</th>';
// 			htmls += '<th class="center hidden-480" colspan="1" rowspan="2" style="vertical-align: middle !important">抽样总数</th>';
// 			htmls += '<th class="center hidden-480" colspan="1" rowspan="2" style="vertical-align: middle !important">合格数</th>';
// 			htmls += '<th class="center hidden-480" colspan="1" rowspan="2" style="vertical-align: middle !important">合格率</th>';
			
//  			if(activityList != null){
// 				//key值为Map的键值  
// 	            $.each(activityList, function(key, value) {
// 	            	c++;
// 	            	htmls += '<th class="center hidden-480" rowspan="1" colspan="3" style="text-align:center;">' + key + '</th>';
// 	            });  
// 			}
// 			htmls += '</tr>';
//  			htmls += '<tr>';
//  			if(activityList != null){
// 				//key值为Map的键值  
// 	            $.each(activityList, function(key, value) {  
// 	    			htmls += '<th class="center hidden-480">抽样数</th>';
// 	    			htmls += '<th class="center hidden-480">合格数</th>';
// 	    			htmls += '<th class="center hidden-480">合格率</th>';
// 	            });  
// 			}
// 			htmls += '</tr>';
// 			htmls += '</thead>';
// 			htmls += '<tbody>';
			
// 			for(var i = 0; i < totalList.length-1; i++) {
// 				htmls += '<tr>';
// 				htmls += '<td>'+ getViewData(totalList[i].CLSNO,'s') +'</td>';
// 				htmls += '<td>'+ getViewData(totalList[i].AREANAME,'s') +'</td>';
// 				htmls += '<td>'+ getViewData(totalList[i].TOTALSUM,'s') +'</td>';
// 				htmls += '<td>'+ getViewData(totalList[i].ISQUALSUM,'s') +'</td>';
// 				htmls += '<td>'+ getViewData(totalList[i].PCTSUM,'p') +'</td>';
//  				if(activityList != null){
// 					//key值为Map的键值  
// 		            $.each(activityList, function(key, value) {
// 		            	if(value[i].TYPENAME == key){
// 			              	if(value[i].CODE == totalList[i].CODE){
// 			            		htmls += '<td>'+ getViewData(value[i].TOTALSUM,'s') +'</td>';
// 					    		htmls += '<td>'+ getViewData(value[i].ISQUALSUM,'s') +'</td>';
// 					    		htmls += '<td>'+ getViewData(value[i].PCTSUM,'p') +'</td>';
// 								return true;
// 			            	}	
// 		            	}else{
// 		            		htmls += '<td>-</td>';
// 		            		htmls += '<td>-</td>';
// 		            		htmls += '<td>-</td>'; 
// 							return true;
// 		            	}
// 		            });  
// 				} 
// 				htmls += '</tr>';
// 			}
//  			if(totalList.length >= 2){
// 				htmls+="<tr><td colspan='2'>总计</td>";
// 				htmls += '<td>'+ totalList[totalList.length-1].TOTALSUM +'</td>';
// 				htmls += '<td>'+ totalList[totalList.length-1].ISQUALSUM +'</td>';
// 				htmls += '<td>'+ totalList[totalList.length-1].PCTSUM +'%</td>';
// 				if(activityList != null){
// 					//key值为Map的键值  
// 		            $.each(activityList, function(key, value) {
// 		            	if(value[totalList.length-1].typename == key){
// 				            htmls += '<td>'+ getViewData(value[totalList.length-1].TOTALSUM,'s') +'</td>';
// 						    htmls += '<td>'+ getViewData(value[totalList.length-1].ISQUALSUM,'s') +'</td>';
// 						    htmls += '<td>'+ getViewData(value[totalList.length-1].PCTSUM,'p') +'</td>'; 
// 							return true;
// 		            	}else{
// 		            		htmls += '<td>0</td>';
// 		            		htmls += '<td>0</td>';
// 		            		htmls += '<td>0.0%</td>'; 
// 							return true;
// 		            	}
// 		            });  
// 				}
// 				htmls += '</tr>';
// 			} 
// 			htmls += '</tbody>';

			// 如果不是初始化datatable就先销毁元table
			if(dataTable != null){
				dataTable.fnDestroy();
			}
			// 设置拼接的字符串到table中
			$("#staticts_table").html(htmls);
			// 设定table的单元格数
/* 			var aTargets = new Array();
			for(var i = 0;i < c;i++){
				aTargets.push()
			} */
			dataTable = $('#staticts_table').dataTable({
		    	"bAutoWidth": true,
		    	"bPaginate" : false,
		    	"bSort": false,
		    	"sDom" : "t<'row-fluid'<'span_paginate'>>",
		    	"fnDrawCallback": function( oSettings ) {
		    		$("#colspanR").css("border-right","none");
		    		$("#colspanL").css("border-left","none");
		    	 }
		    });
		}
		
function getViewData(data,type){
	if(data == undefined){
		return '-';	
	}else{
		if(type == 's'){
			return data;					
		}else{
			return data + '%';	
		}
	}
}

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
	
	getDataTable(projectCode);
});

function getProject(){
	getProjectCode({"industryCode" : industryCode, "type" : type});
}

function doExportExcel(){
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	$('#projectCode').val(projectCode);
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return;
	}
	exportExcelByCustom('report.LivestockPlantSituationService.getCommonLiveStockExport','','searchConditionForm');
}
</script> 
</body>
</html>
