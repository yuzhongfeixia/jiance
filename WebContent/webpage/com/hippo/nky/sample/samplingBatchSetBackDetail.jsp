<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<!-- <script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script> -->
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
		setQueryParams('sampleBatchSetBackDetail_tb',$('#searchConditionForm').getFormValue());
		refresh_SamplingBatchSetBackDetail();
	});

  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sampleBatchSetBackDetail_tb",
			actionUrl:"samplingInfoController.do?getSamplingBatchSetBackDetail&projectCode=${projectCode}",	
			search:true,
			aoColumns:[
					{ "mData": "ogrName"},
					{ "mData": "rCount1"},
					{ 
						  mData: "ogrCode",
						  mRender : function(data, type, full) {
							  if (full.rCount1 > 0) {
									return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="batchSetBack(\''+data+'\')">抽样信息退回</a>';
							  } else {
									return '';
							  }
						  }
					}
					]

		});

	
	});
  
 function batchSetBack(ogrCode) {
		var $modal = $('#ajax-modal'); 
	 	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	      $modal.load('samplingInfoController.do?batchSetBackSampleList&projectCode=${projectCode}&ogrCode='+ogrCode, '', function(){
	      $modal.modal({width:"920px"});
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
 }
  
 function refresh_SamplingBatchSetBackDetail(){
  $("#sampleBatchSetBackDetail_tb").dataTable().fnPageChange('first');  
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
						<div class="controls">
							<div class="alert alert-success">
								<form action="#" name="searchConditionForm" id="searchConditionForm">
								<div class="clearfix">
									<div class="table-seach">
										<label class="help-inline seach-label">抽样机构:</label>
										<div class="seach-element">
											<input type="text" placeholder="" name="ogrName" class="m-wrap small">
										</div>
									</div>
								</div>
								</form>
							</div>
							<div class="clearfix">
								<div class="pull-right">
									<a href="#" class="btn btngroup_seach" id="searchBtn"><i
										class="icon-search"></i>搜索</a>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sampleBatchSetBackDetail_tb">
								<thead>
									<tr>
										<th class="hidden-480">抽样机构</th>
										<th class="hidden-480">已上报数量</th>
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
<!-- 			</form> -->
		</div>
		</div>
	</div>


	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>

</body>
</html>