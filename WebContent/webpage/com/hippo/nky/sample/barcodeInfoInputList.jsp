<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<!-- <link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" /> -->
<link rel="stylesheet" type="text/css" href="assets/plugins/select2-3.5.2/select2.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script> -->
<script type="text/javascript" src="assets/plugins/select2-3.5.2/select2.js"></script>
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
<style>
.messageColor{
	color:red;
}
</style>
<script>
	$('#searchBtn').on('click', function(){
		getTabInfo();
		refresh_samplingInfoList();
	});
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   getProjects();

	   registAjaxDataTable({
		   	id:"sampling_info_table",
			actionUrl:"samplingInfoController.do?datagrid&sampleStatus=1&rand="+Math.random(),
			dataDic : {"monitoringLink":"allmonLink"},		
			search:true,
			fnRowCallback: function ( nTr, asData, iDrawIndex, iDataIndex ) {
				var projectCode = $("#projectCode").val();
				if (projectCode != "" && getDetecthedFlg(projectCode) == 1) {
					var spCode = "";
					if (!isEmpty(asData.spCode)) {
						spCode = asData.spCode.substring(asData.spCode.lastIndexOf("-")+1);
					}
					$(nTr).prepend("<td>"+spCode+"</td>");
				}
			}, 
			aoColumns:[
					{ "mDataProp": "agrname",
						 "mRender" : function(data, type, full) {
						  		return '<a onclick=\"showSampleDetals(\''+full.projectCode+'\',\''+full.id+'\')\">'+data+'</a>';
						 }	
					},
					{ "mDataProp": "dcode"},
					{ "mDataProp": "unitFullname"},
					{ "mData": "monitoringLink",
						"mRender" : function(data, type, full) {
							return full.monitoringLink_name;
						}
					},
					{ "mDataProp": "samplingDate"},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+'\',\''+full.projectCode+'\')">编辑</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+'\',\''+full.projectCode+'\')">删除</a>'+
							'&nbsp;<a class="btn mini yellow" target="_BLANK" href="samplingInfoController.do?addorupdate&id='+data+'&projectCode='+full.projectCode+'&isprint=true">打印</a>';
					}
					}],

		});
	   
	    window.setInterval("setMessageSpanColor()",100);
	    // 为受检单位加入鼠标其他处点击，下拉隐藏
		$(document).bind("click", function (e) {
			var dis = document.getElementById("showmenu").style.display;
			if (dis != "none" && ($(e.target).attr("id") == undefined || $(e.target).attr("id") != "showmenu")) {
				$("#showmenu").html("");
				document.getElementById("showmenu").style.display="none";  		
			}
		 });
	});
	
	function showSampleDetals(projectCode, id){
		// 取抽检分离flg，0:抽检不分离;1:抽检分离
		var detecthedFlg = getDetecthedFlg(projectCode);
	 	var $modal = $('#ajax-modal'); 
	 	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?addorupdate&id='+id+'&projectCode='+projectCode+'&flg=show'+'&detecthedFlg='+detecthedFlg, '', function(){
	      $modal.modal({width:"920px"});
	      App.unblockUI(pageContent);
	      Validator.init();
	      $("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
	    });
	}
	
	function refresh_samplingInfoList() { 
		setQueryParams('sampling_info_table',$('#searchConditionForm').getFormValue());
		var projectCode = $("#projectCode").val();
		if (projectCode != "" && getDetecthedFlg(projectCode) == 1) {
			if ($("#sampling_info_table").find("tr th:eq(0)").text() != '制样编码') {
				$("#sampling_info_table").find("tr").prepend("<th class='hidden-480' style='border-bottom:2px #005e47 solid;'>制样编码</th>");	
			}
		} else {
			if ($("#sampling_info_table").find("tr th:eq(0)").text() == '制样编码') {
				$("#sampling_info_table").find("tr th:eq(0)").remove();	
			}
		}
		if ($( 'div.tipsClass' ) != null) {
			$( 'div.tipsClass' ).fadeOut();
		}
		$("#sampling_info_table").dataTable().fnPageChange('first');  
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
	
	function getProjectComplete(projectCode){
		var compFlg = false;
		$.ajax({
			async: false,
			type : "POST",
			url : "samplingInfoController.do?getProjectComplete",
			data : "projectCode="+projectCode,
			success : function(data) {
				 var d = $.parseJSON(data);
				 compFlg = d.attributes.isSetted;
			}
		});
		return compFlg;
	}
	
	$('#add_btn').on('click', function(){
		getTabInfo();
		if ($('#proselect').val()==null && $('#cityselect').val()==null && $('#areaselect').val()==null) {
			modalAlert("请选择一个项目!");
			return;
		}
		// 取抽检分离flg，0:抽检不分离;1:抽检分离
		var detecthedFlg = getDetecthedFlg($('#projectCode').val());
	    if (detecthedFlg == '1') {
			if (checkReport($('#projectCode').val())) {
				modalAlert("该项目为抽检分离项目，抽样信息已经上报,不可以再录入样品信息!");
				return;
			}
		} else {
			if (getProjectComplete($('#projectCode').val())) {// 已经设置为项目抽样完成
				modalAlert("该项目为抽检不分离项目，已设置为项目抽样完成,不可以再录入样品信息!");
				return;
			}
		}

	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?addorupdate&projectCode='+$('#projectCode').val()+"&detecthedFlg="+detecthedFlg, '', function(){
	      $modal.modal({width:"920px"});
	      App.unblockUI(pageContent);
	      Validator.init();
	      $("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
	    });
	});
	
	function update(id, projectCode) {
		// 取抽检分离flg，0:抽检不分离;1:抽检分离
		var detecthedFlg = getDetecthedFlg(projectCode);
	    if (detecthedFlg == '1') {
			if (checkReport($('#projectCode').val())) {
				modalAlert("该项目为抽检分离项目，抽样信息已经上报,不可以再录入样品信息!");
				return;
			}
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?addorupdate&id='+id+'&projectCode='+projectCode+"&detecthedFlg="+detecthedFlg, '', function(){
	      $modal.modal({width:"920px"});
 	      App.unblockUI(pageContent);
	      Validator.init(); 
	      $("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
	    });
	}
	
	//删除信息
	function del(id, projectCode) {
		// 取抽检分离flg，0:抽检不分离;1:抽检分离
		var detecthedFlg = getDetecthedFlg(projectCode);
	    if (detecthedFlg == '1') {
			if (checkReport($('#projectCode').val())) {
				modalAlert("该项目为抽检分离项目，抽样信息已经上报,不可以再录入样品信息!");
				return;
			}
		}
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "samplingInfoController.do?del&rand=" + Math.random(),
					data : {'id':id,'projectCode':projectCode},
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_samplingInfoList();
			   				modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
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
		//setTabSelect();
	}
	
	function setMessageSpanColor() {
		$("span.help-inline").each(function(){
			if (!$(this).hasClass("messageColor")) {
				$(this).addClass("messageColor");
			}
		});	
	}
	
// 	function checkSampleReport(samplingInfoId){
// 		var checkResult = false;
// 		var url = "samplingInfoController.do?checkSampleReport";
// 		$.ajax({
// 			async: false,
// 			type : "POST",
// 			url : url,
// 			data : {'samplingInfoId':samplingInfoId},
// 			success : function(data) {
// 				var dataJson = eval('(' + data + ')');
// 	   			 checkResult = dataJson.success;
// 			}
// 		});

// 		return checkResult;
// 	}
function autoMatch() {					
	var keyWords = $("#unitFullname").val();
// 	alert(keyWords);
	var monitoringSiteList = "";	
					
	if (isNotEmpty(keyWords)) {				
		// 根据关键字从数据库中查对应受检单位			
		$.ajax({
			async: false,		
			type : 'POST',		
			url : 'samplingInfoController.do?getMatchResult',
			data: {'keyWords':keyWords},
			success : function(data) {	
				var dataJson = eval('(' + data + ')');	
				if(dataJson.success){
					// 总抽样数统计列表取得
					monitoringSiteList = dataJson.attributes.monitoringSiteList;
					
				}	
			}		
		});			
	}				
					
    var inhtml="<ul style='list-style:none;' class='umatch'>";
    var isyou=0; 					
	for (var i=0;i<monitoringSiteList.length;i++) {				
 		var code = monitoringSiteList[i].code;			
 		var name = monitoringSiteList[i].name;			
		inhtml += "<li class='lmatch' onclick=\"setValue(\'"+code+"'\,\'"+name+"'\)\" style='margin-left:-20px;text-align:left' onmouseover=\"this.style.backgroundColor='#666666'\" onmouseout=\"this.style.backgroundColor=''\">"+name+"</li>";			
        isyou=1;
	}				
	inhtml += "</ul>";				
					
    if (isyou==1)			
    { 					
        $("#showmenu").html(inhtml); 					
        document.getElementById("showmenu").style.display="";  					
    } 					
    else 					
    { 					
    	$("#showmenu").html("");
    	document.getElementById("showmenu").style.display="none";  				
    } 					
}					
					
function setValue(code,name) {
	 $("#unitFullcode").val(code);				
	 $("#unitFullname").val(name);
	 $("#unitFullname").focus();
	 document.getElementById("showmenu").style.display="none"; 				
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
									<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
									<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
									<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="tab_1_1">
										<div>
											<select id="proselect" class="span12 m-wrap" size="5" onchange="setTabSelect();"></select>
										</div>
									</div>
									<div class="tab-pane" id="tab_1_2">
										<div>
											<select id="cityselect" class="span12 m-wrap" size="5" onchange="setTabSelect();"></select>
										</div>
									</div>
									<div class="tab-pane" id="tab_1_3">
										<div>
											<select id="areaselect" class="span12 m-wrap" size="5" onchange="setTabSelect();"></select>
										</div>
									</div>
								</div>
							</div>	
							
							<div class="clearfix">
								<input type="hidden" name="projectCode" id="projectCode" value="">
								<div class="table-seach">
									<label class="help-inline seach-label">条码:</label>
									<div class="seach-element">
										<input type="text" name="dCode" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">样品名称:</label>
									<div class="seach-element">
										<input type="text" name="sampleName" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">受检单位:</label>
									<div class="seach-element">
										<input type="text" name="unitFullname" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">信息完整度:</label>
									<div class="seach-element">
										<select  class="m-wrap small" name="complete">
											<option value="" selected>所有</option>
											<option value="1">完整</option>
											<option value="0">不完整</option>
										</select>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="add_btn" class="btn btngroup_usual" data-toggle="modal"><i class="icon-plus"></i>样品信息录入</a>
						</div>
						<div class="pull-right">
							<a class="btn btngroup_seach" id="searchBtn"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					
<!-- 					<div id="tablediv" hidden="true"> -->
					<table class="table table-striped table-bordered table-hover" id="sampling_info_table">
						<thead>
							<tr>	
								<th class="hidden-480">样品名称</th>
								<th class="hidden-480">条码</th>
								<th class="hidden-480">受检单位名称</th>
								<th class="hidden-480">监测环节</th>
								<th class="hidden-480">抽样时间</th>
								<th style="width:180px;"></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
<!-- 					</div> -->
				</div>
			</div>
		</div>
	</div>
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
</body>
</html>