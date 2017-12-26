<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link href="assets/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	
<script src="assets/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/scripts/plantSituationUtil.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
getProjectCode({"industryCode" : '', "type" : $("#type").val()});
//自适应带有GIS的frame的Div大小
App.resetMapFrameDivSize();
var mapDatasJson = {};
var infoFlg = "0";
function getMarkerFun(){
	var markeJson = {};
	var legendsArray = new Array();
	legendsArray.push(createLegendControl('red', '未完成', 100));
	legendsArray.push(createLegendControl('green', '已完成', 100));
	markeJson["property"] = {"legend" : legendsArray};
	
	// 取得数据map数据
	var jsonParam = {};
	jsonParam["targetUrl"] = 'gisReportController.do?getmonitoringSiteTaskInfoListData';
	jsonParam["params"] = {"projectCode" : $("#projectCode").val() , "infoFlg" : infoFlg};
	jsonParam["after"] = "getMonitoringSiteInfoMarker";
	AjaxMode.nomalAction(jsonParam);
	
	markeJson["mapDatas"] = mapDatasJson;
	return markeJson;
}
//拼接成marker格式
function getMonitoringSiteInfoMarker(data){
	if(data.success){
		// 清空上次数据缓存
		mapDatasJson = {};
		var monitoringSiteTaskInfoList = data.attributes.monitoringSiteTaskInfoList;
		for(var row in monitoringSiteTaskInfoList){
			var msInfo = monitoringSiteTaskInfoList[row];
			var infoJson = {};
			infoJson["id"] = msInfo.id;
			infoJson["longitude"] = msInfo.longitude;
			infoJson["latitude"] = msInfo.latitude;
			if("1" == infoFlg){
				infoJson["info"] = '<h2 style="border-bottom:1px solid #C8C8C8;font-family: 微软雅黑;">' + (msInfo.ogrname || '') + '</h2><div style="font-size:12px; font-family:Open Sans;">'
					+ '<p>地址:' + (msInfo.address || '') 
					+ '<p>负责人电话:' + (msInfo.leadertel || '')
					+ '<p>联系人电话:' + (msInfo.contactstel || '')
					+ '<p>分配抽样数:' + msInfo.planCount
					+ '<p>实际抽样数:' + msInfo.realCount + "</div>";
			}
			if("2" == infoFlg){
				infoJson["info"] = '<h2 style="border-bottom:1px solid #C8C8C8;">' + (msInfo.ogrname || '') + '</h2><div style="font-size:12px; font-family:Open Sans;">'
				+ '<p>地址:' + (msInfo.address || '') 
				+ '<p>负责人电话:' + (msInfo.leadertel || '')
				+ '<p>联系人电话:' + (msInfo.contactstel || '')
				+ '<p>分配检测数:' + msInfo.planCount
				+ '<p>实际检测数:' + msInfo.realCount + "</div>";
			}
			infoJson["icon"] = msInfo.icon;
			mapDatasJson[row] = infoJson;
		}
	}
}
// 抽样任务完成情况
function showSampleTaskInfo(){
	if(!checkProjectIsSelected_msti()){
		return;
	}
	mapiframe.window.location.reload();
	infoFlg = "1";
}
// 检测任务完成情况
function showDetectionTaskInfo(){
	if(!checkProjectIsSelected_msti()){
		return;
	}
	mapiframe.window.location.reload();
	infoFlg = "2";
}
function checkProjectIsSelected_msti(){
	var projectCode = $("#projectCode").val();
	if(isEmpty(projectCode)){
		modalAlert("需要统计分析的项目不能为空!");
		return false;
	}
	return true;
}
// 项目检索
function searchProjectList(){
	getProjectCode({"industryCode" : '', "projectLevel": $("#projectLevel").val(),"type" : $("#type").val(), "year" :$("#year").val()});
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
										<select class="m-wrap" tabindex="4" name="projectCode" id="projectCode"></select>
									</div>
								</div>
								<div style="border-bottom:solid 1px #999999; margin-bottom:20px;"></div>
								<div style="height:80px;">
									<a href="#" class="btn btngroup_usual" style="margin-left:103px;" onclick="showSampleTaskInfo()">
										<i class="icon-search"></i>抽样任务完成情况
									</a>
								</div>
								<div style="height:80px;">
									<a href="#" class="btn btngroup_usual" style="margin-left:103px;" onclick="showDetectionTaskInfo()">
										<i class="icon-search"></i>检测任务完成情况
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
