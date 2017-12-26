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
<script>
	$('#searchBtn').on('click', function(){
		setQueryParams('sample_obsolete_tb1',$('#searchConditionForm').getFormValue());
		refresh_SamplingSetBack1();
	});
	$('#searchBtn2').on('click', function(){
		setQueryParams('sample_obsolete_tb2',$('#searchConditionForm2').getFormValue());
		refresh_SamplingSetBack2();
	});
  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sample_obsolete_tb1",
			actionUrl:"samplingInfoController.do?getSamplingSetBack",	
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mDataProp": "code"},
//  					{ "mDataProp": "spCode"},
					{ "mData": "spCode",
						mRender : function(data, type, full) {
							if (data != null) {
								return data.lastIndexOf("-") != -1 ? data.substring(data.lastIndexOf("-")+1) : data;
							} else {
								return '';
							}
							
						}	
					},
					{ "mDataProp": "projectName"},
					{ "mDataProp": "agrName"},
					{ "mDataProp": "unitFullName"},
					{ "mDataProp": "samplingDate"},
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
			actionUrl:"samplingInfoController.do?getSamplingSetBack2",	
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mDataProp": "code"},
//  					{ "mDataProp": "spCode"},
					{ "mData": "spCode",
						mRender : function(data, type, full) {
							if (data != null) {
								return data.lastIndexOf("-")!= -1 ? data.substring(data.lastIndexOf("-")+1) : data;
							} else {
								return '';
							}
							
						}	
					},
					{ "mDataProp": "projectName"},
					{ "mDataProp": "agrName"},
					{ "mDataProp": "unitFullName"},
					{ "mDataProp": "samplingDate"},
					{
					"mData" : 'id',
					bSortable : false,
					"mRender" : function(data, type, full) {
						return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="updateSetBackForSamplingInfo(\''+full.code+'\')">修改</a>'
					}
					}]

		});
	   window.setInterval("setMessageSpanColor()",100);
	
	});
  
  function addSetBack(){
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('samplingInfoController.do?addSetBack', '', function(){
		    $modal.modal({width:"760px"});
		    App.unblockUI(pageContent);
		    Validator.init();
	    });
  }
  
  function updateSetBack(code, id){
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('samplingInfoController.do?addSetBack&code='+code+'&id='+id, '', function(){
		    $modal.modal({width:"760px"});
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
					url : "samplingInfoController.do?delSetBack",
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
	 var id="";
	 $.ajax({
		 async: false,
		type : "POST",
		url : "samplingInfoController.do?getSamplingKeyInfo",
		data : {'dcode':code},
		success : function(data) {
			 var d = $.parseJSON(data);
   			 if (d.success) {
   				projectCode = d.attributes.projectCode;
   				id = d.attributes.id;
   			 }
		}
	 });
	 // 取抽检分离flg，0:抽检不分离;1:抽检分离
	 var detecthedFlg = getDetecthedFlg(projectCode);

	 var $modal = $('#ajax-modal'); 
	 var pageContent = $('.page-content');
	 App.blockUI(pageContent, false);
	 $modal.load('samplingInfoController.do?addorupdate&id='+id+'&projectCode='+projectCode+'&setBackFlg=true&vdcode='+code+"&detecthedFlg="+detecthedFlg, '', function(){
	    $modal.modal({width:"920px"});
	    App.unblockUI(pageContent);
	    Validator.init();
	    $("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
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
  
  
  function refresh_SamplingSetBack1(){
	  $("#sample_obsolete_tb1").dataTable().fnPageChange('first');  
  }
  function refresh_SamplingSetBack2(){
	  if ($( 'div.tipsClass' ) != null) {
		  $( 'div.tipsClass' ).fadeOut();
	  }
	  $("#sample_obsolete_tb2").dataTable().fnPageChange('first');  
  }
  
  function setMessageSpanColor() {
	$("span.help-inline").each(function(){
		if (!$(this).hasClass("messageColor")) {
			$(this).addClass("messageColor");
		}
	});	
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
							<li class="active"><a href="#tab_2_1" data-toggle="tab" onclick="refresh_SamplingSetBack1()">已申请</a></li>
							<li><a href="#tab_2_2" data-toggle="tab" onclick="refresh_SamplingSetBack2()">已退回</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_2_1">
								<div class="controls">
									<div class="alert alert-success">
										<form action="#" name="searchConditionForm" id="searchConditionForm">
										<div class="clearfix">
											<div class="table-seach">
												<label class="help-inline seach-label">条码:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="dCode" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">监测项目:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="projectName" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="sampleName" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">受检单位:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="unitFullname" class="m-wrap small">
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
												<th class="hidden-480">条码</th>
												<th class="hidden-480">制样编码</th>
												<th class="hidden-480">监测项目</th>
												<th class="hidden-480">样品名称</th>
												<th class="hidden-480">受检单位</th>
												<th class="hidden-480">抽样时间</th>
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
												<label class="help-inline seach-label">条码:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="dCode" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">监测项目:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="projectName" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="sampleName" class="m-wrap small">
												</div>
											</div>
	
											<div class="table-seach">
												<label class="help-inline seach-label">受检单位:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="unitFullname" class="m-wrap small">
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
												<th class="hidden-480">条码</th>
												<th class="hidden-480">制样编码</th>
												<th class="hidden-480">监测项目</th>
												<th class="hidden-480">样品名称</th>
												<th class="hidden-480">受检单位</th>
												<th class="hidden-480">抽样时间</th>
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