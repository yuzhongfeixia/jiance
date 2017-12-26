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
<script>
	$('#searchBtn').on('click', function(){
		setQueryParams('sample_obsolete_tb1',$('#searchConditionForm').getFormValue());
		refresh_DetectionSetBack1();
	});
	$('#searchBtn2').on('click', function(){
		setQueryParams('sample_obsolete_tb2',$('#searchConditionForm2').getFormValue());
		refresh_DetectionSetBack2();
	});
  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sample_obsolete_tb1",
			actionUrl:"detectionController.do?getDetectionSetBack",	
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mDataProp": "code"},
					{ "mDataProp": "agrName"},
					{
				    "mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						if (full.status == 3) {
							return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="updateSetBack(\''+full.code+'\',\''+data+'\')">修改</a>'+
							'&nbsp;<a class="btn mini yellow" onclick="delSetBack(\''+data+'\')">删除</a>';
						}
						return "";
					 }
					}]

		});
	   
	   registAjaxDataTable({
		   	id:"sample_obsolete_tb2",
			actionUrl:"detectionController.do?getDetectionSetBack2",	
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mDataProp": "code"},
					{ "mDataProp": "agrName"},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="updateSetBackForSamplingInfo(\''+full.code+'\')">修改</a>'
					}
					}]

		});
	
	});
  
  function addSetBack(){
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('detectionController.do?addSetBack', '', function(){
		    $modal.modal({width:"800px"});
		    App.unblockUI(pageContent);
		    Validator.init();
	    });
  }
  
  function updateSetBack(code, id){
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('detectionController.do?addSetBack&labCode='+code+'&id='+id, '', function(){
		    $modal.modal({width:"800px"});
		    App.unblockUI(pageContent);
		    Validator.init();
	    });
  }
  
  function delSetBack(id){
	  $("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "detectionController.do?delSetBack",
					data : {'id':id},
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				$("#sample_obsolete_tb1").dataTable().fnPageChange('first');  
			   				modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
  }
  
  function updateSetBackForSamplingInfo(code){
	 // 取得样品项目code和id
	 var projectCode="";
	 var agrCode="";
	 $.ajax({
		 async: false,
		type : "POST",
		url : "detectionController.do?getDetectionKeyInfo",
		data : {'labCode':code},
		success : function(data) {
			 var d = $.parseJSON(data);
   			 if (d.success) {
   				projectCode = d.attributes.projectCode;
   				agrCode = d.attributes.agrCode;
   			 }
		}
	 });

	 var $modal = $('#ajax-modal'); 
	 var pageContent = $('.page-content');
	 App.blockUI(pageContent, false);
	 $modal.load('detectionController.do?addOrUpdateDetectionInformation&isSample=true&agrCode='+agrCode+'&projectCode='+projectCode+'&setBackFlg=true&labCode='+code, '', function(){
	    $modal.modal({width:"800px"});
	    App.unblockUI(pageContent);
	    Validator.init();
	    //$("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
	 });
  }
  
  
  function refresh_DetectionSetBack1(){
	  $("#sample_obsolete_tb1").dataTable().fnPageChange('first');  
  }
  function refresh_DetectionSetBack2(){
	  $("#sample_obsolete_tb2").dataTable().fnPageChange('first');  
  }
</script>
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
<!-- 			<form action="#" name="searchConditionForm" id="searchConditionForm"> -->
				<div class="controls">
					<div class="tabbable tabbable-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_2_1" data-toggle="tab" onclick="refresh_DetectionSetBack1()">已申请</a></li>
							<li><a href="#tab_2_2" data-toggle="tab" onclick="refresh_DetectionSetBack2()">已退回</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_2_1">
								<div class="controls">
									<div class="alert alert-success">
										<form action="#" name="searchConditionForm" id="searchConditionForm">
										<div class="clearfix">
											<div class="table-seach">
												<label class="help-inline seach-label">实验室编码:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="labCode" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="sampleName" class="m-wrap small">
												</div>
											</div>
										</div>
										</form>
									</div>
									<div class="clearfix">
										<div class="btn-group">
											<a class="btn btngroup_usual" data-toggle="modal" onclick="addSetBack()"><i class="icon-plus"></i>添加申请</a>
										</div>
										<div class="pull-right">
											<a href="#" class="btn btngroup_seach" id="searchBtn"><i
												class="icon-search"></i>搜索</a>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover"
										id="sample_obsolete_tb1">
										<thead>
											<tr>
												<th class="hidden-480">序号</th>
												<th class="hidden-480">实验室编码</th>
												<th class="hidden-480">样品名称</th>
												<th class="hidden-480">操作</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
							<div class="tab-pane" id="tab_2_2">
							<div class="controls">
									<div class="alert alert-success">
									<form action="#" name="searchConditionForm2" id="searchConditionForm2">
										<div class="clearfix">
											<div class="table-seach">
												<label class="help-inline seach-label">实验室编码:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="labCode" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="sampleName" class="m-wrap small">
												</div>
											</div>
										</div>
								     </form>
									</div>
									<div class="clearfix">
										
										<div class="pull-right">
											<a href="#" class="btn btngroup_seach" id="searchBtn2"><i
												class="icon-search"></i>搜索</a>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover"
										id="sample_obsolete_tb2">
										<thead>
											<tr>
												<th class="hidden-480">序号</th>
												<th class="hidden-480">实验室编码</th>
												<th class="hidden-480">样品名称</th>
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
				</div>
	
<!-- 			</form> -->
		</div>
		</div>
	</div>
</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>

</body>
</html>