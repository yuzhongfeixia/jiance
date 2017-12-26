<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
.control-item {
	width: 350px;
	float: left;
}

select.length1 {
	width: 130px;
}

.styletd1 tr td {
	padding-top: 1px;
	padding-bottom: 1px
}
</style>
<script>
var industryCode = "${param.industryCode}";
var type = "${param.type}";
jQuery(document).ready(function() {
	getProject();
});
//统计搜索
$('#statisticsBtn').on('click', function(){
	var projectName = $('#projectNames').val();
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
		type : "POST",
		url : "plantSituationController.do?totleSituationStatistics&rand=" + Math.random(),
		data : {'projectName':projectName,'projectCode':projectCode,'year':year,'projectLevel':projectLevel},
		success : function(data) {
			 var d = $.parseJSON(data);
   			 if (d.success) {
   				$("#showYear").text(d.obj.year);
   				$("#showPro").text(d.obj.projectName);
   				$("#showLevel").text(d.obj.projectLevelName);
   				$("#showCounty").text(d.obj.areaCount);
   				$("#showCity").text(d.obj.areaCount1);
   				$("#showAgr").text(d.obj.argCount);
   				$("#showSamp").text(d.obj.sampCount);
   				$("#showPoll").text(d.obj.pollCount);
   				$("#showisQ").text(d.obj.qualifiedCount);
   				$("#showPct").text(d.obj.pct);
   				$("#showCnSmpleCount").text(d.obj.sampCount1);
   				$("#showCnSamplePct").text(d.obj.pct1);
   				$("#showCiSmpleCount").text(d.obj.sampCount2);
   				$("#showCiSamplePct").text(d.obj.pct2);
   				
   				$("#showDiv").show();
   			 }else {
   				modalTips(d.msg);
   			 }
		}
	});
});

//导出word 
$('#exportWord').bind('click',function(){
	//var projectLevelName = $('#projectLevel').find("option:selected").text();

	var projectLevel = $('#projectLevel').val();
	var year = $('#year').val();
	var projectName = $('#projectNames').val();
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return;
		if ($("#showYear").text() == '') {
			modalTips("没有可复制的文本！");
			return;
		}
	}else{
		$(this).attr("href","plantSituationController.do?exportWord&rand="+Math.random()+"&year="+year+"&projectName="+encodeURI(encodeURI(projectName))+"&projectLevel="+projectLevel+"&projectCode="+projectCode);
	}
});


function getProject(){
	getProjectCode({"industryCode" : industryCode, "type" : type});
}
</script>
</head>
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
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a class="btn btngroup_seach" id="statisticsBtn"><i class="icon-search"></i>统计</a> 
							<a class="btn btngroup_usual" id="exportWord">复制文本<i class="icon-share"></i></a>
						</div>
					</div>
					<div style="margin: 10px 30px 0 10px; font-size: 16pt; line-height: 40px; display: none;" id="showDiv">
						总体情况：<span style="color: red;" id="showYear"></span>年
							     <span style="color: red;" id="showPro"></span>对
							     <span style="color: red;" id="showLevel"></span>共
							     <span style="color: red;" id="showCounty"></span>个区县和
							     <span style="color: red;" id="showCity"></span>市辖区
							     <span style="color: red;" id="showAgr"></span>种产品 
							     <span style="color: red;" id="showSamp"></span>个样品进行了
								 <span style="color: red;" id="showPoll"></span>种污染物监测，合格数量为 
								 <span style="color: red;" id="showisQ"></span> ，合格率为
								 <span style="color: red;" id="showPct"></span>。
								 其中区县共<span style="color: red;" id="showCnSmpleCount"></span>个样品，合格率为<span style="color: red;" id="showCnSamplePct"></span>,
								 市辖区共<span style="color: red;" id="showCiSmpleCount"></span>个样品，合格率为<span style="color: red;" id="showCiSamplePct"></span>。
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
