<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ include file="/webpage/main/navigator.jsp" %>
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
/* #detectioin_statictscal_table th{ */
/* 	min-width:100px; */
/* } */
/* #detectioin_statictscal_table th.tabNum{ */
/* 	min-width:50px; */
/* 	max-width:100px; */
/* } */
</style>
<script>
    var dataTable = null;
	jQuery(document).ready(function() {
		//取得省市县项目
		//getProjects({"proDefVal" : 'first', "showBelowGradeRepFlg" : "no"});
		getProjects({"proDefVal" : 'first', "showBelowGradeRepFlg" : "yes"});
		
		getDataTable('init', null);
	});
	
	function getDataTable(data, projectCode) {
		showTips("正在加载,请稍等...","400",1000);
		if (data == 'init') {
			projectCode = $("#proselect").val();
		}
		var selCondtion = $('#selCondtion').val();
		$.ajax({
			async: true,
			type : 'POST',
			url : 'detectionController.do?getStatisticalTableList',
			data: {'projectCode':projectCode,'selCondtion':selCondtion},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				if(dataJson.success){
					// 总抽样数统计列表取得
					var detectionStatisticalList = dataJson.attributes.detectionStatisticalList;
					// 表头农产品列表取得
					//var agrtableHeader = dataJson.attributes.agrtableHeader;
					// 各农产品统计状况取得
					//var agrStatList = dataJson.attributes.agrStatList;
					
					//drewTableCells(detectionStatisticalList, agrtableHeader, agrStatList);
					drewTableCells(detectionStatisticalList);
				}
			}
		});
	}
	
// 	function drewTableCells(detectionStatisticalList, agrtableHeader, agrStatList){

// 		var htmls = '<thead><tr>';
// 		htmls += '<th class="center hidden-480" rowspan="2"  style="vertical-align: middle !important">序号</th>';
// 		htmls += '<th class="center hidden-480" rowspan="2" style="vertical-align: middle !important;">任务</th>';
// 		htmls += '<th class="center hidden-480" rowspan="2" style="vertical-align: middle !important;">检测单位</th>';
// 		htmls += '<th class="center hidden-480"  colspan="2">总抽样数</th>';
		
// 		if(agrtableHeader != null){
// 			for(var i = 0; i < agrtableHeader.length; i++){
// 				htmls += '<th class="center hidden-480"  colspan="2">' + agrtableHeader[i] + '</th>';
// 			}
// 		} else {
// 			return;
// 		}
// 		htmls += '<th class="center hidden-480"  rowspan="2" style="vertical-align: middle !important">最后上报时间</th>';
// 		htmls += '</tr>';
// 		htmls += '<tr>';
// 		htmls += '<th class="center hidden-480">任务</th>';
// 		htmls += '<th class="center hidden-480">实际完成</th>';
// 		for(var i = 0; i < agrtableHeader.length; i++){
// 			htmls += '<th class="center hidden-480">任务</th>';
// 			htmls += '<th class="center hidden-480">实际完成</th>';
// 		}
// 		htmls += '</tr>';
// 		htmls += '</thead>';
		
// 		htmls += '<tbody>';
// 		for(var i = 0; i < detectionStatisticalList.length; i++) {
// 			htmls += '<tr>';
// 			htmls += '<td>'+ (i+1) +'</td>';
// 			htmls += '<td>'+ detectionStatisticalList[i].taskName +'</td>';
// 			htmls += '<td>'+ detectionStatisticalList[i].dectectionOrgName +'</td>';
// 			htmls += '<td>'+ detectionStatisticalList[i].taskCount +'</td>';
// 			htmls += '<td>'+ detectionStatisticalList[i].actualCount +'</td>';
// 			var agrStatListTem = agrStatList[i];
// 			for (var j = 0; j < agrStatListTem.length; j++) {
// 				htmls += '<td>';
// 				if (agrStatListTem[j].taskCount != -1) {
// 					htmls += agrStatListTem[j].taskCount;
// 				} else {
// 					htmls += "-";
// 				}
// 				htmls += '</td>';
// 				htmls += '<td>';
// 				if (agrStatListTem[j].actualCount != -1) {
// 					htmls += agrStatListTem[j].actualCount;
// 				} else {
// 					htmls += "-";
// 				}
// 				htmls += '</td>';
// 			}
// 			if (detectionStatisticalList[i].reportingDate != undefined) {
// 				htmls += '<td>'+ detectionStatisticalList[i].reportingDate +'</td>';
// 			} else {
// 				htmls += '<td></td>';
// 			}
			
// 			htmls += '</tr>';
			
// 		}
// 		htmls += '</tbody>';

// 		// 如果不是初始化datatable就先销毁元table
// 		if(dataTable != null){
// 			dataTable.fnDestroy();
// 		}
// 		// 设置拼接的字符串到table中
// 		$("#detectioin_statictscal_table").html(htmls);
// 		$( 'div.tipsClass' ).fadeOut();
		
// 		// 根据列数多少设定table的宽度 
// 		$('#detectioin_statictscal_table').css({"width" : (200 * agrtableHeader.length + 1000) + 'px'});
		
// 		// 设定table的单元格数
// 		var aTargets = new Array();
// 		for(var u = 0;u < agrtableHeader.length*2+6; u++){
// 			aTargets.push(u);
// 		}

// 		//注册datatable组件
// 		dataTable = $('#detectioin_statictscal_table').dataTable({
// 			"bAutoWidth": true,
// 			"bPaginate" : false,
// 			"sDom" : "t<'row-fluid'<'span_paginate'>>",
// 			"aoColumnDefs" : [{'bSortable': false,"aTargets": aTargets }]
// 		});
// 	}
	
	function drewTableCells(detectionStatisticalList){

		var htmls = '<thead><tr>';
		htmls += '<th class="center hidden-480" style="vertical-align: middle !important">序号</th>';
		htmls += '<th class="center hidden-480" style="vertical-align: middle !important;">检测单位</th>';
		htmls += '<th class="center hidden-480" style="vertical-align: middle !important;">任务</th>';
// 		htmls += '<th class="center hidden-480"  colspan="2">总抽样数</th>';
		htmls += '<th class="center hidden-480">任务</th>';
		htmls += '<th class="center hidden-480">实际完成</th>';
		htmls += '<th class="center hidden-480" style="vertical-align: middle !important">最后上报时间</th>';
		htmls += '</tr>';
		htmls += '</thead>';
		
		htmls += '<tbody>';
		for(var i = 0; i < detectionStatisticalList.length; i++) {
			htmls += '<tr>';
			htmls += '<td>'+ (i+1) +'</td>';
			htmls += '<td>'+ detectionStatisticalList[i].dectectionOrgName +'</td>';
			htmls += '<td>'+ detectionStatisticalList[i].taskName +'</td>';
			htmls += '<td>'+ detectionStatisticalList[i].taskCount +'</td>';
			htmls += '<td>'+ detectionStatisticalList[i].actualCount +'</td>';
			if (detectionStatisticalList[i].reportingDate != undefined) {
				htmls += '<td>'+ detectionStatisticalList[i].reportingDate +'</td>';
			} else {
				htmls += '<td></td>';
			}
			htmls += '</tr>';
		}
		htmls += '</tbody>';

		// 如果不是初始化datatable就先销毁元table
		if(dataTable != null){
			dataTable.fnDestroy();
		}
		// 设置拼接的字符串到table中
		$("#detectioin_statictscal_table").html(htmls);
		$( 'div.tipsClass' ).fadeOut();
		
		//注册datatable组件
		dataTable = $('#detectioin_statictscal_table').dataTable({
			"bAutoWidth": true,
			"bPaginate" : false,
			"sDom" : "t<'row-fluid'<'span_paginate'>>",
			"aoColumnDefs" : [{'bSortable': false,"aTargets": [0,1,2,3,4]  }]
		});
	}
	
	function queryData(){
		var projectCode = $('.tab-pane.active select').val();
		getDataTable('change', projectCode);
	}
	
	function setFormValue(data) {
		$('#selCondtion').val($("input[name='"+data.name+"']:checked").val());
		queryData();
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
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<input type="hidden" name="selCondtion" id="selCondtion" value="0">
							<div class="tabbable tabbable-custom">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
									<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
									<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="tab_1_1">
										<div>
											<select id="proselect" class="span12 m-wrap" size="5" onClick="queryData();"></select>
										</div>
									</div>
									<div class="tab-pane" id="tab_1_2">
										<div>
											<select id="cityselect" class="span12 m-wrap" size="5" onClick="queryData();"></select>
										</div>
									</div>
									<div class="tab-pane" id="tab_1_3">
										<div>
											<select id="areaselect" class="span12 m-wrap" size="5" onClick="queryData();"></select>
										</div>
									</div>
								</div>
							</div>	
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<div style="float:left;margin-top:5px;">
								<label class="radio"><input name="showStu"  type="radio" value="0" checked onclick="setFormValue(this)">查看所有</label>
							</div>
							<div style="float:left;margin-left:10px;margin-top:5px;">
								<label class="radio"><input name="showStu"  type="radio" value="1" onclick="setFormValue(this)">只看完成</label>
							</div>
							<div style="float:left;margin-left:10px;margin-top:5px;margin-right:5px;">
								<label class="radio"><input name="showStu"  type="radio" value="2" onclick="setFormValue(this)">只看未完成</label>
							</div>
						</div>
					</div>
<!-- 					<div id="sbsjgl_table" style="width:100%; overflow-x: auto; overflow-y: auto;"> -->
					<div id="sbsjgl_table">
						<table class="table table-striped table-bordered table-hover"  id="detectioin_statictscal_table" style="min-width:100%">
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
		<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
</body>
</html>				