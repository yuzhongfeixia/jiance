<%@page import="org.apache.tools.ant.util.DateUtils"%>
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
									<label class="help-inline seach-label">受检单位代码:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="code" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">受检单位名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" name="name" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">所属区域:</label>
									<div id="areasDiv1" class="seach-element">
											<t:dictSelect id="areacode" field="areacode" hasLabel="false" customData="${areacodeList}" defaultVal="" extend="{data-set:{value:'#areasDiv1 .areaSelect'},link-Change:{value:'true'}}"></t:dictSelect>
											<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" defaultVal=""  extend="{class:{value:'small areaSelect'}}"></t:dictSelect>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">监测环节:</label>
									<div class="seach-element">
										<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false" defaultVal=""></t:dictSelect>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<a id="add_Monitor" class="btn btngroup_usual" data-toggle="modal" onclick="addMonitor()"><i class="icon-plus"></i>新增</a>
						</div>
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
							<a href="#" id="exportExcel" class="btn btngroup_usual"><i class="icon-share"></i>导出Excel</a>
						</div>
					</div>
					<div id="wcg"></div>
					<table class="table table-striped table-bordered table-hover" id="monitoringSiteTable">
						<thead>
							<tr>
								<th class="hidden-480">受检单位代码</th>
								<th class="hidden-480">受检单位名称</th>
								<th class="hidden-480">法定代表或负责人</th>
								<th class="hidden-480">所属市</th>
								<th class="hidden-480">所属区县</th>
								<th class="hidden-480">监测环节</th>
								<th class="hidden-480">操作</th>
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
	LinkChange.init();
	$('#searchBtn').on('click', function(){
		setQueryParams('monitoringSiteTable',$('#searchConditionForm').getFormValue());
		refresh_nkyMonitoringSiteList();
	});
	$('#exportExcel').on('click', function(){
		exportExcelByCustom('monitoring.NkyMonitoringSiteService.exportExcelForMonitoringSite','江苏省受检单位-<%=DateUtils.format(new Date(), "yyyyMMdd") %>','searchConditionForm');
	});
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {

	   registAjaxDataTable({
		   	id:"monitoringSiteTable",
			actionUrl:"nkyMonitoringSiteController.do?datagrid&rand="+Math.random(),
			dataDic : {"monitoringLink":"allmonLink","areacode":"sysArea","areacode2":"sysArea"},		
			search:true,
			aoColumns:[
					{ "mDataProp": "code"},
					{ "mDataProp": "name"},
					{ "mDataProp": "legalPerson"},
// 					{ "mDataProp": "areacode"},
// 					{ "mDataProp": "areacode2"},
					{ "mData": "areacode",
						"mRender" : function(data, type, full) {
							return full.areacode_name;
						}
					},
					{ "mData": "areacode2",
						"mRender" : function(data, type, full) {
							return full.areacode2_name;
						}
					},
					{ "mData": "monitoringLink",
						"mRender" : function(data, type, full) {
							return full.monitoringLink_name;
						}
					},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
					}
					}],
	        	fnCallBefore: function (queryParams,aoData){
	        		return  $('#searchConditionForm').getFormValue();
        		},
		});

		
	});
	
	function refresh_nkyMonitoringSiteList() {  
		$("#monitoringSiteTable").dataTable().fnPageChange('first');  
	}  
	
	
	function addMonitor() {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('nkyMonitoringSiteController.do?addorupdate', '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}
	//删除信息
	function del(data) {
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "nkyMonitoringSiteController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_nkyMonitoringSiteList();
			   				modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
	}

	function update(data) {
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('nkyMonitoringSiteController.do?addorupdate&id='+data, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

</script>
</body>
