<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>
<script src="assets/scripts/ui-jqueryui.js"></script> 
<script src="assets/js/curdtools.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script>
    LinkChange.init();
    var dataTable = null;
    dataTable = $('#detectionInfoTable').dataTable({
    	"bAutoWidth": true,
    	"bPaginate" : false,
    	"bSort": false,
    	"sDom" : "t<'row-fluid'<'span_paginate'>>"
    });
	$('#searchBtn').on('click', function(){
		showTips("正在加载,请稍等...","400",1000);
		getTabInfo();
		setCodeTitle($('#projectCode').val());
		//setQueryParams('detectionInfoTable',$('#searchConditionForm').getFormValue());
		//$("#detectionInfoTable").dataTable().fnPageChange('first');  
		
		
		$.ajax({
			async: true,
			type : 'POST',
			url : 'detectionController.do?dectionInfoCollectDatagrid',
			data: {'projectCode':$('#projectCode').val(), 'sampleName': $('#sampleName').val(), 
				   'unitFullname': $('#unitFullname').val(), 'monitoringLink' : $('#monitoringLink').val()},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				if(dataJson.success){
					// 总抽样数统计列表取得
					var htmls = dataJson.attributes.htmls;
					var pollSize = dataJson.attributes.pollSize;
					drewTableCells(htmls, pollSize);
				}
			}
		});
	});
	
	function drewTableCells(htmls, pollSize){
		// 如果不是初始化datatable就先销毁元table
		if(dataTable != null){
			dataTable.fnDestroy();
		}
		// 设置拼接的字符串到table中
		$("#detectionInfoTable").html(htmls);
		$( 'div.tipsClass' ).fadeOut();
			
	    dataTable = $('#detectionInfoTable').dataTable({
	    	"bAutoWidth": true,
	    	"bPaginate" : false,
	    	"bSort": false,
	    	"sDom" : "t<'row-fluid'<'span_paginate'>>",
	    	"fnDrawCallback": function( oSettings ) {
	    	 }
	    });
	    $('#detectionInfoTable').css({"width" : (100 * pollSize + 2000) + 'px'});
	    //$('.pollheader').css({"width" : '100px'});
	   // $('.juge').css({"width" : '200px'});
	}

//	var $modal = $('#ajax-modal');
// 	jQuery(document).ready(function() {
// 	   getProjects({"showBelowGradeRepFlg" : "yes"});
// 	   registAjaxDataTable({
// 		   	id:"detectionInfoTable",
// 			actionUrl:"detectionController.do?dectionInfoCollectDatagrid&rand="+Math.random(),
// 			search:true,
// 			aoColumns:[
// 					{ "mDataProp": "rn"},
// 					{ "mDataProp": "taskName"},
// 					{ "mData": "isOverproof",
// 						"mRender" : function(data, type, full) {
// 							return full.isOverproof_name;
// 						}
// 					},
// 					{ "mDataProp": "spCode",
// 						  "mRender" : function(data, type, full) {
// 								return '<a onclick=\"showDetail(\''+full.id+'\',\''+full.sampleCode+'\')\">'+data+'</a>';
// 						  }
// 						},
// 					{ "mDataProp": "cname"},
// 					{ "mDataProp": "unitFullname"},
// 					{ "mData": "monitoringLink",
// 						"mRender" : function(data, type, full) {
// 							return full.monitoringLink_name;
// 						}
// 					},
// 					{ "mDataProp": "unitAddress"},
// 					{ "mDataProp": "samplingOgrname"},
// 					{ "mDataProp": "detectionOgrname"}
// 			],
// 		});

// 	});	
	
	$(document).ready(function(){
		 getProjects({"showBelowGradeRepFlg" : "yes"});
		 
		 $("#editDetectionInfo_btn").attr("disabled",true);
		 $("#editDetectionInfo_btn").attr("onclick","");
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
	
	function showDetail(id, sampleCode) {
	   	var $modal = $('#ajax-modal'); 
	   	var projectCode = $("#projectCode").val();
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('detectionController.do?detectionInfoCollectDetail&id='+id+'&sampleCode='+sampleCode+'&projectCode='+projectCode, '', function(){
	      $modal.modal({width:"850px"});
	      App.unblockUI(pageContent);
	    });
	}
	
	function checkDetected(projectCode){
		var checkResult = false;
		if(projectCode != null){
			$.ajax({
				async: false,
				type : "POST",
				url : "detectionController.do?checkIsDetached&rand=" + Math.random(),
				data : {"projectCode":$('#projectCode').val()},
				success : function(data) {
					var d = $.parseJSON(data);
					checkResult = d.success;
				}
			});
		}
		return checkResult;
	}
	
	function setCodeTitle(projectCode){
		if (checkDetected(projectCode)) {
			$("#title_th").text("");
			$("#title_th").text("制样编码");
		} else {
			$("#title_th").text("");
			$("#title_th").text("样品条码");
		}
	}
	
	function exportDetectionCollect() {
		getTabInfo();
		if ($("#projectCode").val() == "") {
			modalAlert("请选择一个项目!");
			return;
		}
		exportExcelByCustom('detection.DetectionService.exportSampleCollect','','searchConditionForm');
	}
	
	function chgProject(data) {
		var projectCode = $('#'+data).val();
		// 根据项目和用户判断当前用户是否为牵头单位
		$.ajax({
			async: false,
			type : "POST",
			url : "detectionController.do?isQtCheck&rand=" + Math.random(),
			data : {"projectCode":projectCode},
			success : function(data) {
 				 var d = $.parseJSON(data);
				 if (d.success) {
					 $("#editDetectionInfo_btn").removeAttr("disabled");
					 $("#editDetectionInfo_btn").attr("onclick","editDetectionInfo()");
				 } else {
					 $("#editDetectionInfo_btn").attr("disabled",true);
					 $("#editDetectionInfo_btn").attr("onclick","");
				 }
			}
		});
	}
	
	// 为牵头单位设置可修改检测信息的权限
	function editDetectionInfo() {
	    $("#spCode").val("");
		$("#slabCode").val("");
		$("#dCode").val("");
		$("#search-dialog").modal('show');
	}
	
	function searchDetecInfo(){
		getTabInfo();
		var projectCode = $('#projectCode').val();
		var spCode = $("#spCode").val();
		var slabCode = $("#slabCode").val();
		var dCode = $("#dCode").val();
		if (spCode == "" && labCode == "" && dCode == "") {
			return;
		}
		// 通过spCode和labCode找样品信息，取得其对应的labCode和agrCode
		var agrCode="";
		var labCode="";
		var ogrId="";
		var sampleCode="";
		$.ajax({
			async: false,
			type : "POST",
			url : "detectionController.do?getTargetSampleInfo&rand=" + Math.random(),
			data : {"projectCode":$('#projectCode').val(),"spCode":spCode,"labCode":slabCode,"dCode":dCode},
			success : function(data) {
				 var d = $.parseJSON(data);
				 if (d.success) {
				 	agrCode = d.attributes.agrCode;
				 	labCode = d.attributes.labCode;
				 	ogrId = d.attributes.ogrId;
				 	dCode = d.attributes.dCode;
				 	sampleCode = d.attributes.sampleCode;
				 	$("#search-dialog").modal('hide');
				 }
			}
		});
		if (agrCode == "") {
		    modalTips("没有搜索结果!");
			return;
		}
		
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('detectionController.do?addOrUpdateDetectionInformation&isSample=true&setBackFlg=true&agrCode='+agrCode+'&labCode='+labCode+'&orgCode='+ogrId+"&projectCode="+projectCode+"&dCode="+dCode+"&sampleCode="+sampleCode, '', function(){
		    $modal.modal({width:"760px"});
		    App.unblockUI(pageContent);
		    Validator.init();
	    });
	}
</script>

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>
					</div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<div class="tabbable tabbable-custom">
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
												<select class="m-wrap" style="width:114px;" name="year1" id="year1" onchange="setFormValue('year1');">
													<c:forEach items="${yearList}" var="year">
														<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
													</c:forEach>	
												</select>
											</div>
										</div>
										<div class="">
											<select id="proselect" class="span12 m-wrap" size="5" onchange="chgProject('proselect')"></select>
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
												<select class="m-wrap" style="width:114px;" name="year2" id="year2" onchange="setFormValue('year2');">
													<c:forEach items="${yearList}" var="year">
														<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
													</c:forEach>	
												</select>
											</div>
											
										</div>
										<div class="">
											<select id="cityselect" class="span12 m-wrap" size="5" onchange="chgProject('cityselect')"></select>
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
												<select class="m-wrap" style="width:114px;" name="year3" id="year3" onchange="setFormValue('year3');">
													<c:forEach items="${yearList}" var="year">
														<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
													</c:forEach>	
												</select>
											</div>
										</div>
										<div class="">
											<select id="areaselect" class="span12 m-wrap" size="5" onchange="chgProject('areaselect')"></select>
										</div>
									</div>
	
								</div>
							</div>
							<div class="clearfix">
								<input type="hidden" name="projectCode" id="projectCode" value="">
								<input type="hidden" name="monitorType" id="monitorType" value="1">
								<input type="hidden" name="year" id="year" value="${currYear}">
								<div class="table-seach">
									<label class="help-inline seach-label">样品名称:</label>
									<div class="seach-element">
										<input id="sampleName" name="sampleName" type="text" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">受检单位:</label>
									<div class="seach-element">
										<input id="unitFullname" name="unitFullname" type="text" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">抽样环节:</label>
									<div class="seach-element">
										<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false" 
											extend="{class:{value:'small'}}"></t:dictSelect>
									</div>
								</div>
							</div>
						</form>
					</div>

					<div class="clearfix" >
						<div class="btn-group">
							<a class="btn btngroup_usual" id="editDetectionInfo_btn" data-toggle="modal" href="#" onclick="editDetectionInfo();"><i class="icon-edit"></i>修改检测信息</a>
						</div>
						<div class="pull-right">
							<a id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
<!-- 							<a class="btn btngroup_usual" onclick="exportExcel('getDetectionInfoCollect','','searchConditionForm')">导出Excel<i class="icon-share"></i></a> -->
							<a class="btn btngroup_usual" onclick="exportDetectionCollect()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>

					<div style="width:100%; overflow-x: scroll; overflow-y: hidden;">
						<table class="table table-striped table-bordered table-hover dataTable" id="detectionInfoTable" style="width: 2000px;">
							<thead>
								<tr>
									<th class="center hidden-480">序号</th>
<!-- 									<th class="center hidden-480">任务</th> -->
									<th class="center hidden-480">抽样单位</th>
									<th class="center hidden-480">检测单位</th>
									<th class="center hidden-480">被检市县</th>
									<th class="center hidden-480" id="title_th">制样编码</th>
									<th class="center hidden-480">样品名称</th>
									<th class="center hidden-480">受检单位名称</th>
									<th class="center hidden-480">监测环节</th>
									<th class="center hidden-480">受检单位所在地</th>
									<th class="center hidden-480">结果判定(合格或不合格)</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="search-dialog" class="modal hide fade" tabindex="-1">
		<div class="row-fluid">
			<div class="span12">
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<span class="hidden-480">检测信息检索</span>
						</div>
						<div class="tools">
							<a data-dismiss="modal"  class="closed"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">样品条码:</label>
								<div class="seach-element">
									<input id="dCode" name="dCode" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">制样编码:</label>
								<div class="seach-element">
									<input id="spCode" name="spCode" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">实验室编码:</label>
								<div class="seach-element">
									<input id="slabCode" name="slabCode" type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
						</div>
						<div class="modal-footer">
<!-- 							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button> -->
							<button type="button" class="btn popenter popConfim" onclick="searchDetecInfo();">搜索</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
