<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="assets/plugins/data-tables/css/TableTools.css" />

<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/TableTools.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/ZeroClipboard.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>
<script src="assets/scripts/ui-jqueryui.js"></script> 
<script src="assets/js/curdtools.js" type="text/javascript" ></script>

<!-- BEGIN BODY -->
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
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">标准号:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small" name="standardCode">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">发布时间:</label>
									<div class="seach-element">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="publishDate_begin" name="publishDate_begin"/>
										<label class="help-inline">～</label>
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="publishDate_end" name="publishDate_end"/>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">状态:</label>
									<div class="seach-element">
									<t:dictSelect id="stopflag" field="stopflag" typeGroupCode="stopstart" hasLabel="false" defaultVal=""></t:dictSelect>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<!-- <a id="confirm_opener" class="btn btngroup_usual" ><i class="icon-random"></i>同步数据 </a> -->
							<a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="add()"><i class="icon-plus"></i>新增</a>
							<a id="copyData" class="btn btngroup_usual" data-toggle="modal" onclick="copyData();"><i class="icon-random" ></i>更新标准</a>
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="limitStanderdVersionTable">
						<thead>
							<tr>
								<th>标准号</th>
								<th>中文名称</th>
								<th>发布机构</th>
								<th class="hidden-480">发布日期</th>
								<th class="hidden-480">发布标识</th>
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
<script>
$('.date-picker').datepicker({
    rtl : App.isRTL(),
    language: "zh",
    autoClose: true,
    format: "yyyy-mm-dd",
    todayBtn: true,
    clearBtn:true
});

$('#searchBtn').on('click', function(){
	if(!compareDateValue()) {
		return;
	}
	setQueryParams('limitStanderdVersionTable',$('#searchConditionForm').getFormValue());
	refresh_limitStandardVersionList();
});
	
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"limitStanderdVersionTable",
			actionUrl:"limitStandardVersionController.do?datagrid&rand="+Math.random(),
			search:true,
			tableTools:true,
			aoColumns:[
					{ "mData": "standardCode",
						"mRender" : function(data, type, full) {
					  		return '<a onclick=\"showDetails(\''+full.id+'\')\">'+data+'</a>';
					 	}	
					},
					{ "mData": "nameZh"},
					{ "mData": "publishOrg"},
					{ "mData": "publishDate","dateFormat":"yyyy-MM-dd"},
					{ "mData": "publishflag","dataDic":"publish"},
					{ "mData": "stopflag","dataDic":"stopstart"},
					{ "mData": "standardType","dataHidden":true},
					{
						  "mData" : 'id',
						  "button":[
							{
								"className":"btn mini yellow",
								"onclick":"detailshow('this', id)",
								"buttonName":"详情",
								"dataAuthority":[{"publishflag":"0,1","stopflag":"0,1"}]
							},{
								"className":"btn mini yellow",
								"onclick":"stop(id)",
								"buttonName":"停用",
								"dataAuthority":[{"publishflag":"1","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"publish(id,standardType)",
								"buttonName":"发布",
								"dataAuthority":[{"publishflag":"0","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"update(id)",
								"buttonName":"编辑",
								"dataAuthority":[{"publishflag":"0","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"del(id)",
								"buttonName":"删除",
								"dataAuthority":[{"publishflag":"0","stopflag":"0"}]
							}]
					}
// 					{
// 					"mData" : 'id',
// 					bSortable : false,
// 					"mRender" : function(data, type, full) {
// 						return '<a id="" data-toggle="modal" class="btn mini yellow" onclick="detailshow(\''+data+ '\');">详情</a>'+
// 							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
// 							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="stop(\''+data+ '\')">停用</a>'+
// 							'&nbsp;<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="publish(\''+data+ '\')">发布</a>'+
// 							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
// 						}
// 					}
					],
// 				initModals:[
// 					{
// 					"id" : "addData",
// 					"operation" : "createwindow",
// 					"url":"limitStandardVersionController.do?addorupdate&rand="+Math.random(),
// 				   	},	
// 					{
// 						"id" : "copyBtn",
// 						"operation" : "windowsave",
// 						"url":"limitStandardVersionController.do?copy",
// 						"formId":"saveForm"
// 					} ,
// 					{
// 						"id" : "addBtn",
// 						"operation" : "windowsave",
// 						"url":"limitStandardVersionController.do?save",
// 						"formId":"saveForm"
// 					},
// 				] ,
	        	fnCallBefore: function (queryParams,aoData){
	        		return  $('#searchConditionForm').getFormValue();
        		},
		});

		
	});
	function showDetails(id) {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	 	App.unblockUI(pageContent);
	     $modal.load('limitStandardVersionController.do?addorupdate&id='+id+'&flg=show', '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}
	function refresh_limitStandardVersionList() {  
		$("#limitStanderdVersionTable").dataTable().fnPageChange('first');  
	} 

	function detailshow($this, data){
		loadContent($this, 'limitStandardController.do?limitStandard&versionId='+data);
	}
	//删除信息
	function del(data) {
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "limitStandardVersionController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_limitStandardVersionList();
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
	}
	
	function add() {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('limitStandardVersionController.do?addorupdate', '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

	function update(data) {
		if(!judgePublish(data)){
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		     $modal.load('limitStandardVersionController.do?addorupdate&id='+data, '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });
		} else{
			modalAlert("已经发布的版本，不需在进行编辑操作!");
		}
	}
	
	function copyData() { 
		var checkFlg = false;
		var vid = "";
		var oTT = TableTools.fnGetInstance( 'limitStanderdVersionTable' );
	    var aSelectedTrs = oTT.fnGetSelected();
	    if(aSelectedTrs.length != 0){
	    	vid = aSelectedTrs[0].id;
	    	checkFlg = true;
	    }

		if (!checkFlg) {
			modalAlert("请指定一个版本!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
		     $modal.load('limitStandardVersionController.do?addorupdate&type=copy&id='+vid, '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });
	}

	function stop(versionid){
		var url = "limitStandardVersionController.do?save";
		
		if(!judgeStop(versionid)){
			$("#ajax-modal").confirmModal({
				heading: '请确认操作',
				body: '确定停用该版本吗?',
				callback: function () {
					$.ajax({
						type : 'POST',
						url : url,
						data : {'id':versionid,'stopflag':1},
						success : function(data) {
							var dataJson = eval('(' + data + ')');
							if(dataJson.success){
								refresh_limitStandardVersionList();
								modalTips(dataJson.msg);
							} else {
								modalTips(dataJson.msg);
							}
						}
					});
				}
			});
		} else {
			modalAlert("已经停用的版本!");
		}
	}
	
	function publish(versionid, standardType){
// 		// 相同类别的标准只能有一个是发布状态
// 		if (!sameTypePublishChk(standardType)) {
// 			modalAlert("同一类型的标准，最多只能发布一次!");
// 			return;
// 		}

		if(judgeStop(versionid)){
			modalAlert("已经停用的版本,不能再进行发布操作!");
		} else {
			if(!judgePublish(versionid)){
				$("#ajax-modal").confirmModal({
					heading: '请确认操作',
					body: '确定发布该版本吗?',
					callback: function () {
						$.ajax({
							type : 'POST',
							url : 'limitStandardVersionController.do?save&publish=true',
							data : {'id':versionid,'publishflag':1},
							success : function(data) {
								var dataJson = eval('(' + data + ')');
								if(dataJson.success){
									refresh_limitStandardVersionList();
									modalTips(dataJson.msg);
								} else {
									modalTips(dataJson.msg);
								}
							}
						});
					}
				});
			} else {
				modalAlert("已经发布的版本，不需要重新发布!");
			}
		}
	}
	
// 	function sameTypePublishChk(standardType) {
// 		var result = true;
// 		var url = "limitStandardVersionController.do?sameTypePublishChk";
// 		$.ajax({
// 			async: false,
// 			type : 'POST',
// 			url : url,
// 			data : {'standardType':standardType,'publishflag':1},
// 			success : function(data) {
// 				var dataJson = eval('(' + data + ')');
// 				result = dataJson.success;
// 			}
// 		});
// 		return result;
// 	}
	
	function compareDateValue(){
		if ($("#publishDate_begin").val() != "" && $("#publishDate_end").val() != "") {
			var publishDate_begin = $("#publishDate_begin").val().replace("-","");
			var publishDate_end = $("#publishDate_end").val().replace("-","");
			if (publishDate_begin > publishDate_end) {
				modalAlert("开始日期应小于结束日期!");
				return false;
			}
		}
		return true;
	}

</script>
</body>
