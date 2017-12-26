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
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>
<script src="assets/scripts/ui-jqueryui.js"></script> 
<script src="assets/js/curdtools.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script>
$('.date-picker').datepicker({
    rtl : App.isRTL(),
    language: "zh",
    autoClose: true,
    format: "yyyy-mm-dd",
    todayBtn: true,
    clearBtn:true
});
    LinkChange.init();
 
	$('#searchBtn').on('click', function(){
		getTabInfo();
		setQueryParams('sampleCollectTable',$('#searchConditionForm').getFormValue());
		refresh_sampleCollect();
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   getProjects({"showBelowGradeRepFlg" : "yes"});
	   registAjaxDataTable({
		   	id:"sampleCollectTable",
			actionUrl:"samplingInfoController.do?sampleCollectDatagrid&sampleStatus=1,3,4,5&rand="+Math.random(),
			search:true,
			aoColumns:[
					{ "mDataProp": "agrname",
						 "mRender" : function(data, type, full) {
						  		return '<a onclick=\"showSampleDetals(\''+full.projectCode+'\',\''+full.id+'\')\">'+data+'</a>';
						 }	
					},
					{ "mDataProp": "dcode"},
					{ "mDataProp": "unitFullname"},
					{ "mDataProp": "cityAndCountry"},
					{ "mData": "monitoringLink",
						"mRender" : function(data, type, full) {
							return full.monitoringLink_name;
						}
					},
					{ "mDataProp": "samplingDate"}
					],
		        	fnCallBefore: function (queryParams,aoData){
// 		        		queryParams['monitorType'] = $('#monitorType').val();
// 		        		queryParams['year'] = $('#year').val();
// 		        		return queryParams;
	        		}
		});

	});
	
	function showSampleDetals(projectCode, id){
		// 取抽检分离flg，0:抽检不分离;1:抽检分离
		var detecthedFlg = getDetecthedFlg(projectCode);
	 	var $modal = $('#ajax-modal'); 
	 	var pageContent = $('.page-content');
	 	App.unblockUI(pageContent);
	     $modal.load('samplingInfoController.do?addorupdate&id='+id+'&projectCode='+projectCode+'&flg=show'+'&detecthedFlg='+detecthedFlg, '', function(){
	      $modal.modal({width:"920px"});
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}
	function getDetecthedFlg(projectCode) {
		var detecthedFlg = "";
		$.ajax({
			async: false,
			type : "POST",
			url : "samplingInfoController.do?getDetecthedFlg",
			data : "projectCode="+projectCode,
			success : function(data) {
				 var d = $.parseJSON(data);
				 detecthedFlg = d.attributes.detecthedFlg;
			}
		});
		return detecthedFlg;
	}
	
	function refresh_sampleCollect() {  
		$("#sampleCollectTable").dataTable().fnPageChange('first');  
	} 
	
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
		getProjects({"showBelowGradeRepFlg" : "yes"});
	}
	
	function exportSampleCollect() {
		getTabInfo();
		if ($("#projectCode").val() == "") {
			modalAlert("请选择一个项目!");
			return;
		}
		// 若是牵头单位项目，则导出全部
		$.ajax({
			async: false,
			type : "POST",
			url : "samplingInfoController.do?getLeadUnitFlg",
			data : {"projectCode" : $("#projectCode").val(), "samplingOrgCode" : $("#samplingOrgCode").val()},
			success : function(data) {
				 var d = $.parseJSON(data);
				 if (d.success) {
					 $("#expAllData").val(1);
				 } else {
					 $("#expAllData").val("");
				 }

			}
		});
		exportExcel('getSample','','searchConditionForm');
	}
	
	function setCityCode() {
		var projectCode = $('.tab-pane.active select:eq(1)').val();
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
											<select id="proselect" class="span12 m-wrap" size="5" onchange="setCityCode()"></select>
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
											<select id="cityselect" class="span12 m-wrap" size="5" onchange="setCityCode()"></select>
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
											<select id="areaselect" class="span12 m-wrap" size="5" onchange="setCityCode()"></select>
										</div>
									</div>
	
								</div>
							</div>
							<div class="clearfix">
								<input type="hidden" name="projectCode" id="projectCode" value="">
								<input type="hidden" name="monitorType" id="monitorType" value="1">
								<input type="hidden" name="year" id="year" value="${currYear}">
								<input type="hidden" name="samplingOrgCode" id="samplingOrgCode" value="${samplingOrgCode}">
								<input type="hidden" name="expAllData" id="expAllData" value="">
								<div class="table-seach">
									<label class="help-inline seach-label">抽样地区:</label>
									<div class="seach-element">
										<t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${cityCodeList}" extend="{onChange:{value:'linkCountry(this)'}}"></t:dictSelect>
										<select id="countyCode" name="countyCode" class="m-wrap small"></select>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">抽样时间:</label>
									<div class="seach-element">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" name="samplingDate"/>
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
						<div class="pull-right">
							<a id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
							<a class="btn btngroup_usual" onclick="exportSampleCollect()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="sampleCollectTable" style="margin-top: 5px;">
						<thead>
							<tr>
								<th class="center hidden-480">样品名称</th>
								<th class="center hidden-480">样品条码</th>
								<th class="center hidden-480" >受检单位</th>
								<th class="center hidden-480" >抽样地点</th>
								<th class="center hidden-480" >抽样环节</th>
								<th class="center hidden-480" >抽样时间</th>
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
</body>
</html>				