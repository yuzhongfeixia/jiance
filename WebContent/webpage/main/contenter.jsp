<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navigator.jsp" %>
<link href="assets/css/contenter.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js"></script>

<script>
//展开菜单节点
function expandSidebar(sidebarName){
	// 取得菜单名称
// 	var sidebarName = $(tabIcon).text().trim();
	// 找到对应的菜单文字的最近节点并展开
	$('.page-sidebar-menu .root-menu .root-menu-text span[class="title"]').each(function(){
		var htmlText = $(this).text().trim();
		if('监测信息统计分析' == sidebarName){
			if(htmlText.indexOf(sidebarName) > -1){
				expandLeftBar();
				$(this).closest("li .root-menu-text").click();
				return false;
			}
		} else {
			if(sidebarName == htmlText){
				expandLeftBar();
				$(this).closest("li .root-menu-text").click();
				return false;
			}
		}
	});
}
function expandLeftBar(){
	var body = $('body.page-header-fixed');
	
	if(body.hasClass("page-sidebar-closed")){
		$('div[class="sidebar-toggler hidden-phone"]').click();
	}
}
</script>

<div class="row-fluid">
	<div class="span12">
<div class="ctn">
   <div class="ctn_tbs">

      <div class="ta_ctn">
      <input type="hidden" value="" id="clickFunctionId">
        <div class="tab_icon1">
          <dl><img src="assets/img/icon01.png" /></dl>
          <dl id="tabIcon_monitoringPlan"></dl>
        </div>
        
        
        <div class="tab_icon2">
          <dl><img src="assets/img/icon02.png" /></dl>
          <dl id="tabIcon_samplingInfo"></dl>
        </div>
        
        <div class="tab_icon3">
          <dl><img src="assets/img/icon03.png" /></dl>
          <dl id="tabIcon_detection"></dl>
        </div>
        
        <div class="tab_icon4">
          <dl><img src="assets/img/icon04.png" /></dl>
          <dl id="tabIcon_gisReport"></dl>
        </div>
        
        <div class="tab_icon5">
          <dl><img src="assets/img/icon05.png" /></dl>
          <dl id="tabIcon_situation"></dl>
        </div>
        
        <div class="tab_icon6">
          <dl><img src="assets/img/icon06.png" /></dl>
          <dl id="tabIcon_standardVersion"></dl>
        </div>
      </div>
       <div style="clear:both;"> </div>
  
   </div>
  
</div>
<div class="tb_bot"></div>
</div>
</div>
<script>
getContenterAuth();
addTabIcons();
function addTabIcons(){
	var htmls = "";
	// 监测计划管理
	if(isCheckedAuth("tabIcon_monitoringPlan")){
		htmls += '<a>监测计划管理</a>';
		$(".tab_icon1").on('click', function(){
			expandSidebar('监测计划管理');
		});
	} else {
		htmls += '<a>监测计划管理</a>';
		$(".tab_icon1").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_monitoringPlan").append(htmls);
	
	
	// 抽样信息管理
	htmls = "";
	if(isCheckedAuth("tabIcon_samplingInfo")){
		htmls += '<a>抽样信息管理</a>';
		$(".tab_icon2").on('click', function(){
			expandSidebar('抽样信息管理');
		});
	} else {
		htmls += '<a>抽样信息管理</a>';
		$(".tab_icon2").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_samplingInfo").append(htmls);
	
	// 检测信息管理
	htmls = "";
	if(isCheckedAuth("tabIcon_detection")){
		htmls += '<a>检测信息管理</a>';
		$(".tab_icon3").on('click', function(){
			expandSidebar('检测信息管理');
		});
	} else {
		htmls += '<a>检测信息管理</a>';
		$(".tab_icon3").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_detection").append(htmls);
	
	// GIS统计分析
	htmls = "";
	if(isCheckedAuth("tabIcon_gisReport")){
		htmls += '<a>GIS统计分析</a>';
		$(".tab_icon4").on('click', function(){
			expandSidebar('GIS统计分析');
		});
	} else {
		htmls += '<a>GIS统计分析</a>';
		$(".tab_icon4").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_gisReport").append(htmls);
	
	// 监测信息统计分析
	htmls = "";
	if(isCheckedAuth("tabIcon_situation")){
		htmls += '<a>监测信息统计分析</a>';
		$(".tab_icon5").on('click', function(){
			expandSidebar('监测信息统计分析');
		});
	} else {
		htmls += '<a>监测信息统计分析</a>';
		$(".tab_icon5").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_situation").append(htmls);
	
	// 基础信息管理
	htmls = "";
	if(isCheckedAuth("tabIcon_standardVersion")){
		htmls += '<a>基础信息管理</a>';
		$(".tab_icon6").on('click', function(){
			expandSidebar('基础信息管理');
		});
	} else {
		htmls += '<a>基础信息管理</a>';
		$(".tab_icon6").on('click', function(){
			contenterNoAuth();
		});
	}
	$("#tabIcon_standardVersion").append(htmls);
}
// 取得首页contenter权限
function getContenterAuth(){
	var jsonParam = {};
	jsonParam["targetUrl"] = 'commonController.do?getContenterAuth';
	jsonParam["after"] = "setContenterClickFunctionId";
	AjaxMode.nomalAction(jsonParam);
}
// 设置菜单ID
function setContenterClickFunctionId(data){
	if(data.success){
		var clickFunctionId = data.attributes.clickFunctionId;
		$("#clickFunctionId").val(clickFunctionId);
	}
}
// 提示没有权限
function contenterNoAuth(){
	modalAlert("您没有使用该功能的权限!");
}

</script>