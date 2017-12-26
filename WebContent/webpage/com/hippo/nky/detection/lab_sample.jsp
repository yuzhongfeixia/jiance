<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />

<!-- <script type="text/javascript" src="assets/js/jquery.PrintArea.js"></script> -->
<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
<script>
var dataTable = null;
var $modal = $('#ajax-modal');
$('#searchBtn').on('click', function(){
/* 	var projectCode = $("#projectCode").val();
	if(isEmpty(projectCode)){
		modalAlert("需要统计的项目不能为空!");
		return;
	} */
	setQueryParams('lab_sample_tb',$('#searchConditionForm').getFormValue());
	refreshListToFirst($('#lab_sample_tb'));
});

function queryData(data){
/* 	var taskselect = $('#'+data);
	var projectCode = taskselect.val();
	$('#projectCode').val(projectCode);
	getTableData(projectCode); 	 */	
	var taskselect = $('#'+data);
	checkTitle(taskselect.val());
	var queryParams = getQueryParams('lab_sample_tb');
	queryParams['projectCode'] = taskselect.val()+"";
	$('#projectCode').val(taskselect.val()+"");
	setQueryParams('lab_sample_tb',queryParams);
	refreshListToFirst($('#lab_sample_tb'));
}

$(document).ready(function(){
	//全选
	jQuery('#lab_sample_tb .group-checkable').change(function () {
          var set = jQuery(this).attr("data-set");
          var checked = jQuery(this).is(":checked");
          jQuery(set).each(function () {
        	  var chk = $(this).parent().parent().find("span");
        	  //$(this).attr("checked", false);
        	  if (chk.hasClass("checked")) {
            	  chk.removeClass('checked');
            	  $(this).attr("checked", false);
              } else {
            	  chk.addClass('checked');
            	  $(this).attr("checked", true);
              }
          });
          jQuery.uniform.update(set);
    });
	
	getProjects();
	getTableData();
});

function getTableData(){
	if(dataTable != null){
		dataTable.fnDestroy();
	}
	
	dataTable = registAjaxDataTable({
		id : "lab_sample_tb",
		actionUrl : "detectionController.do?labSampleDatagrid&sampleStatus=4&rand=" + Math.random(),
		fnDrawCallback: function(oSettings) {
		  	  // 样式修正
			  App.initUniform();
			},
		aoColumns:[
					{
						"mData" : "id",
						"sWidth" : "8px",
						"bSortable" : false,
						"mRender" : function(data, type, rec) {
							return "<input type='checkbox' class='checkboxes' name='samplechkName' value='"+data+"'/>"
						}
					},
					{"mDataProp" : "rn"},
					{"mDataProp" : "code"},
		            {"mDataProp" : "labCode"},
					{"mDataProp" : "agrname"},
					{"mDataProp" : "sampleStatus",
					  "mRender" : function(data, type, full) {
					  	 return full.sampleStatus_name;
					  }	
					},
					{
						"mDataProp" : "code",
						"mRender" : function(data, type, full) {
							if(full.sampleStatus == '4') {
								return '<a class="btn mini green" id="updateLabCodeBtn" onclick="update(\''+full.id+'\');"><i class="icon-edit"></i>编辑</a>';
							} else {
								return '';
							}
							
// 							if(isCheckedAuth('updateLabCodeBtn')){
// 								return '<a class="btn mini green" id="updateLabCodeBtn" onclick="update(\''+full.id+'\');"><i class="icon-edit"></i>编辑</a>';
// 							}else{
// 								return '';
// 							}
						 	/**
							var r = <t:authFilter name='updateLabCodeBtn'>'<a class=\'btn mini green\' id=\'updateLabCodeBtn\' onclick=\'update(\'\'+full.id+\'\');"><i class=\'icon-edit\'></i>编辑</a>'</t:authFilter>;
						 	console.info(r.length);
							return r;	
							return <t:authFilter name='updateLabCodeBtn'>'<a class=\'btn mini green\' id=\'updateLabCodeBtn\' onclick=\'update(\''+full.id+'\');\'><i class=\'icon-edit\'></i>编辑</a>'</t:authFilter>;
							**/
						}
					} 
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3,4,5]
		} ]
	});
}
//打印标签 跳转
$('#barcode_print_btn').on('click', function(){
	var ids = getSelectedRecord();
// 	$("#lab_sample_tb .checkboxes").each(function(index){ 
//         if($(this).attr("checked") != 'undefined' && $(this).attr("checked") == "checked"){
// 			ids += $(this).val()+","
//         } 
//     })
     
	if(isEmpty(ids)){
		modalAlert("至少要选择一条数据!");
		return;
	}
	
	var pageContent = $('.page-content');
   	App.blockUI(pageContent, false);
    $modal.load('detectionController.do?toPrint&ids='+ids, '', function(){
     $modal.modal();
     App.unblockUI(pageContent);
   });
});

$('#barcode_reset_btn').on('click', function(){
	var projectCode = $('#projectCode').val();
	if (projectCode == "") {
		modalAlert("请选择一个项目!");
		return;
	}
	$("#confirmDiv").confirmModal({
		heading: '请确认操作',
		body: '重置实验室编码，该项目下的实验室编码将会被清空，你确定吗?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : "detectionController.do?resetLabCode&rand=" + Math.random(),
				data : {'projectCode':projectCode},
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				refreshList($('#lab_sample_tb'));
		   				modalTips(d.msg);
		   			 }else {
		   				modalTips(d.msg);
		   			 }
				}
			});
		}
	});
});

//打印标签 
$modal.on('click', '#printBtn', function() {
	var url= '';
	if (Sys.chrome) {
	    url = '';

	} else {
		url = 'webpage/com/hippo/nky/detection/barcode_print_view.jsp?pdfurl=';
	   
	}
	var formData = $('#addForm').serialize();
	$.ajax({
		type : "POST",
		url : "detectionController.do?printLabBarcode&rand=" + Math.random(),
		data : formData,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				//console.info(d.obj.pdfURL);
				var list = $.dialog.list;
				for( var i in list ){
				    list[i].close();
				}
 	 			$.dialog({
 	 					id:'printWin',
						//content: 'url:webpage/com/hippo/nky/detection/barcode_print_view.jsp?pdfurl='+d.obj.pdfURL,
						//content: 'url:'+d.obj.pdfURL,
						content: 'url:'+url+d.obj.pdfURL,
					//	zIndex: -1,
						title: '打印',
						lock : false,
						width :400,
						height :250,
						left :'50%',
						top :'55%',
						ok : function(){
							this.iframe.contentWindow.print();
						}
						
/* 						init:function(){
							iframe = this.iframe.contentWindow;
							setTimeout("","1000");//为防止页面未加载完就打印，延迟一秒 
						} */
					//	opacity : 0.4
				});
				$.dialog.setting.zIndex = 20000;
				modalTips(d.msg);
				//refreshList($('#lab_sample_tb'));
			} else {
				modalAlert(d.msg);
			}
		}
	});

});
//样品修改
$modal.on('click', '#add_btn', function() {
	var projectCode = $('#projectCode').val();
	var rtnBool = registerForm({
	  	id : "addForm",
		rules : {
			"labCode" : {
				minlength : 3,
				maxlength : 32,
				remote : {
					url: "detectionController.do?checkLabCode&rand=" + Math.random(),     //后台处理程序
					async:false, 
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	"labCode" : function() {
				    		return $("#labCode").val();
				    	},
				    	"projectCode" : function(){
				    		return projectCode;
				    	},
				    	"id" : function(){
				    		return $("#sCode").val();
				    	}
				    },//使用ajax方法调用验证输入值
				},
				required : true
			}
		},
		messages : {
			labCode : {
				required : "实验室编号范围在3~32位字符",
				remote : "该实验室编号已存在或不符合规范"
			}
		}
	});

	if(rtnBool){
		var formData = $('#addForm').serialize();
		// 保存样品
		$.ajax({
			type : "POST",
			url : "detectionController.do?updateSampleLabCode&rand=" + Math.random(),
			data : formData,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					modalTips(d.msg);
					$modal.modal('hide');
					refreshList($('#lab_sample_tb'));
				} else {
					modalAlert(d.msg);
				}
			}
		});	
	}else{
		return false;
	}
});

//编辑
function update(sId) {
	var pageContent = $('.page-content');
   	App.blockUI(pageContent, false);
	$modal.load('detectionController.do?toUpdateSample&sampleCode='+sId, '', function(){
    	$modal.modal();
    });
	App.unblockUI(pageContent);
}

// function chkThis(obj){
// 	  var chk = $(obj).parent().parent().find("span");
// 	  if (chk.hasClass("checked")) {
//   	  chk.removeClass('checked');
//   	  $(this).attr("checked", false);
//     } else {
//   	  chk.addClass('checked');
//   	  $(this).attr("checked", true);
//     }
// }

function selectAll(){
	  var arrSon = document.getElementsByName("samplechkName" );
	  var cbAll = document.getElementById("sampleCheckAll");
	  var tempState=cbAll.checked;
	  for(i=0;i<arrSon.length;i++) {
		   if(arrSon[i].checked!=tempState)
		            arrSon[i].click();
	  }
}

function getSelectedRecord() {
	// 要上报的抽样信息
	var sampleIdArr = "";
	var arrSon = document.getElementsByName("samplechkName" );
	for (i=0;i<arrSon.length;i++) {
		if(arrSon[i].checked == true){
			sampleIdArr = sampleIdArr + arrSon[i].value + ",";
		}
	}
	return sampleIdArr;
}
function checkTitle(projectCode){
	if(projectCode != null){
		$.ajax({
			type : "POST",
			url : "detectionController.do?checkIsDetached&rand=" + Math.random(),
			data : {"projectCode":projectCode},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$("#title_th").text("");
					$("#title_th").text("制样编码");
				}else{
					$("#title_th").text("");
					$("#title_th").text("样品条码");
				}
			}
		});
	}
}

function doExportExcel(){
	if ($('#projectCode').val() == '') {
		modalAlert("请选择一个项目!");
		return;
	}
	exportExcelByCustom('detection.DetectionServiceI.exportLabSample','','searchConditionForm');
}
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" value="${requestScope.clickFunctionId}" id="clickFunctionId">
	<div class="row-fluid" >
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe">样品实验室</i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
						<input type="hidden" name="projectCode" id="projectCode" value="">
						<div class="tabbable tabbable-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
								<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
								<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1_1">
									<div class="">
										<select id="proselect" class="span12 m-wrap" size="5" onchange="queryData('proselect');"></select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_2">
									<div class="">
										<select id="cityselect" class="span12 m-wrap" size="5" onchange="queryData('cityselect');"></select>
									</div>
								</div>
								<div class="tab-pane" id="tab_1_3">
									<div class="">
										<select id="areaselect" class="span12 m-wrap" size="5" onchange="queryData('areaselect');"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">实验室编码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small "  id="searchLabCode" name="searchLabCode"/>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">样品名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small " id="sampleName" name="sampleName"/>
								</div>
							</div>
					 	</div>
					 	</form>
					</div>
					<div class="clearfix">
						<div class="btn-group" >
							<a id="barcode_print_btn" class="btn btngroup_usual"><i class="icon-print"></i>打印标签</a>
							<a id="barcode_reset_btn" class="btn btngroup_usual"><i class="icon-print"></i>重置实验室编码</a>
<%-- 							<t:authFilter name="barcode_print_btn"><a id="barcode_print_btn" class="btn btngroup_usual"><i class="icon-print"></i>打印标签</a></t:authFilter> --%>
						</div>
						<div class="pull-right">
							<a id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
							<a class="btn btngroup_usual" onclick="doExportExcel()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="lab_sample_tb" >
						<thead>
							<tr>
								<th width="10"><input type="checkbox" name="sampleCheckAll" id="sampleCheckAll" onclick="selectAll()"/></th>
								<th class="hidden-50">序号</th>
								<th class="hidden-50" id="title_th">样品条码</th>
								<th class="hidden-480">实验室编码</th>
								<th class="hidden-480">样品名称</th>
								<th class="hidden-480">状态</th>
								<th class="hidden-480"></th>
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
	<div id="confirmDiv" class="modal hide fade"></div>
</body>
</html>