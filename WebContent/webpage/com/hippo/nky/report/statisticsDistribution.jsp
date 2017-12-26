<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link href="assets/plugins/select2/select2_metro.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
<script src="assets/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/scripts/plantSituationUtil.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
var markeJson = {"fillColor":"#e6e6e6","mapAreaDatas":{}};
//自适应带有GIS的frame的Div大小
App.resetMapFrameDivSize();
var legendsArray = new Array();
legendsArray.push(createLegendControl('#e6e6e6', '没有数据'));
legendsArray.push(createLegendControl('green', '95%以上'));
legendsArray.push(createLegendControl('blue', '80%～95%'));
legendsArray.push(createLegendControl('yellow', '75%～80%'));
legendsArray.push(createLegendControl('red', '75%以下'));
markeJson["property"] = {"legend" : legendsArray};
function getMarkerFun(){
    return markeJson;
}
function openInfo(id){
	window.frames["mapiframe"].openInfo(id); 
}
// 项目检索
function searchProjectList(){
	$("#breed").html('');
	$("#monitoringSampleLink").html('');
	getProjectCode({"industryCode" : '', "type" : $("#type").val()});
	
}
// 选择项目
function selectProject(){
	$("#breed").html('');
	$("#monitoringSampleLink").html('');
	if($("#projectCode").val() != ""){
		$.ajax({
			type : "POST",
			url : "gisReportController.do?statisticsDistributionSelectProject",
			data : {
				"projectCode" : $("#projectCode").val(),
				"poll" : $("#poll").val(), 
				"monitoringLink" : $("#monitoringSampleLink").val()
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var breedList = d.attributes.monitoringBreedList;
					agrSetSelectOptions(breedList,"breed","");
					var linkList = d.attributes.monitoringLinkList;
					agrSetSelectOptions(linkList,"monitoringSampleLink","");
					var pollList = d.attributes.monitoringPollList;
					agrSetSelectOptions(pollList,"poll","");
					searchBreed();
				}
			}
		});
	}
}

//选择品种 修改检测参数下拉框
function setPoll(){
	$("#poll").html('');
	if($("#projectCode").val() != ""){
		$.ajax({
			type : "POST",
			url : "gisReportController.do?statisticsDistributionSelectProject",
			data : {
				"projectCode" : $("#projectCode").val(),
				"breed" : $("#breed").val(), 
				"monitoringLink" : $("#monitoringSampleLink").val()
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var pollList = d.attributes.monitoringPollList;
					agrSetSelectOptions(pollList,"poll","");
					searchBreed();
				}
			}
		});
	}
}
function agrSetSelectOptions(list, selectid, defaultVal) {
	var proCodeSelector = $("#"+selectid);
	if(list.length > 0){
		proCodeSelector.append("<option value='' selected>全部</option>");
	}
	for(var i = 0; i < list.length; i++){
		proCodeSelector.append("<option value='" + list[i].CODE + "'>"  + list[i].NAME +  "</option>");
	}
}
function searchBreed(){
	$.ajax({
		type : "POST",
		url : "gisReportController.do?statisticsSearchBreed",
		data : {
			"projectCode" : $("#projectCode").val(),
			"breed" : $("#breed").val(), 
			"poll" : $("#poll").val(), 
			"monitoringLink" : $("#monitoringSampleLink").val(),
			"dataLevel" : $("#dataLevel").val()
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
 				var list = d.attributes.searchBreedList;
				var mapAreaDatasJson = {};
				for(var i = 0; i < list.length; i++){
					var color = "";
					if ( list[i].QUALIFIED_RATE_100  < 75){
						color = "red";
					}else if(list[i].QUALIFIED_RATE_100 < 80){
						color = "yellow";
					}else if(list[i].QUALIFIED_RATE_100 < 95){
						color = "blue";
					}else{
						color = "green";
					}
					mapAreaDatasJson["code_"+list[i].CITY_CODE] = {
						"id":i,
						"cityName":list[i].CITY_NAME,
						"samplingCount":list[i].SAMPLING_COUNT,
						"overStanderdCount":list[i].OVER_STANDERD_COUNT,
						"qualifiedRate":list[i].QUALIFIED_RATE,
						"fillColor":color,
						"title":list[i].CITY_NAME+"("+list[i].QUALIFIED_RATE +")",
						"info":'<h2 style=\'border-bottom:1px solid #C8C8C8;\'>'+list[i].CITY_NAME+'</h2><p>抽样个数：'+list[i].SAMPLING_COUNT +'<p>合格率：'+list[i].QUALIFIED_RATE
					};
				}
				markeJson["mapAreaDatas"] = mapAreaDatasJson;
				window.frames["mapiframe"].location.reload();
			}
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
				<div class="controls">
					<div class="row-fluid gisMapMaxDiv">
						<div class="span6" style="width:72.7%;">
							<iframe src="webpage/com/hippo/nky/report/map.html" frameborder="0" id="mapiframe" name="mapiframe" class="gisMapIframe"></iframe>
						</div>
						<div class="span6" style="width:27%;margin-left:0.3%;">
							<div class="tabbable tabbable-custom">
								<div style="margin:10px;">
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">级别:</span>
										<select class="m-wrap" tabindex="1" name="projectLevel" id="projectLevel" onchange="searchProjectList();">
											<option value="1">省任务</option>
											<option value="2">市任务</option>
											<option value="3">县任务</option>
										</select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">监测类型:</span>
										<select  class="m-wrap" tabindex="2" name="type" id="type" onchange="searchProjectList();">
											<OPTION  value="1" selected>例行监测</OPTION>
											<OPTION  value="2">普查</OPTION>
											<OPTION  value="3">专项调查</OPTION>
											<OPTION  value="4">监督抽查</OPTION>
										</select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">年份:</span>
										<select class="m-wrap" tabindex="3" name="year" id="year" onchange="searchProjectList();">
											<c:forEach items="${yearList}" var="year">
													<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
											</c:forEach>
										</select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">项目:</span>
										<select class="m-wrap" tabindex="4" name="projectCode" id="projectCode" onchange="selectProject();"></select>
									</div>
								</div>
								<div style="border-bottom:solid 1px #999999; margin-bottom:20px;"></div>
								<div style="margin:10px;">
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">品种:</span>
										<select  class="m-wrap" tabindex="5" id="breed" name="breed" onchange="setPoll();"></select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">检测参数:</span>
										<select  class="m-wrap" tabindex="5" id="poll" name="poll" onchange="searchBreed();"></select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">监测环节:</span>
										<select  class="m-wrap" tabindex="7" id="monitoringSampleLink" name="monitoringSampleLink" onchange="searchBreed();"></select>
									</div>
									<div style="height:50px;">
										<span class="help-inline" style="margin-right:15px; width:70px; text-align:right;">数据级别:</span>
										<select  class="m-wrap" tabindex="8" id="dataLevel" name="dataLevel" onchange="searchBreed();">
											<OPTION  value="0">市级总和</OPTION>
											<OPTION  value="1">省辖市批发市场</OPTION>
											<OPTION  value="2">县级</OPTION>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
jQuery(document).ready(function() {    
	searchProjectList();
});
</script>