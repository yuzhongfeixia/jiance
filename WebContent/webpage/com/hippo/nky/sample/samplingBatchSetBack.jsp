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
		setQueryParams('sampleBatchSetBack_tb',$('#searchConditionForm').getFormValue());
		refresh_SamplingBatchSetBack();
	});

  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sampleBatchSetBack_tb",
			actionUrl:"samplingInfoController.do?getSamplingBatchSetBack",	
			search:true,
			aoColumns:[
					{ "mDataProp": "projectName"},
					{ "mDataProp": "ogrName"},
// 					{ 
// 						  mData: "publishDate",
// 						  mRender : function(data, type, full) {
// 							 var arr = data.split("-");
// 							 return arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
// 						  }
// 						},
					{ "mDataProp": "aCount"},
					{ "mDataProp": "rCount2"},
					{ "mDataProp": "rCount1"},
					{ 
						  mData: "projectCode",
						  mRender : function(data, type, full) {
							  if (full.rCount1 > 0) {
									return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="getDetail(\''+data+'\')">详细</a>';
							  } else {
									return '';
							  }
						  }
					}
					]

		});

	
	});
  
 function getDetail(projectCode) {
	 loadContent(this, "samplingInfoController.do?samplingBatchSetBackDetail&projectCode="+projectCode);
 }
  
 function refresh_SamplingBatchSetBack(){
  $("#sampleBatchSetBack_tb").dataTable().fnPageChange('first');  
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
										<label class="help-inline seach-label">项目名称:</label>
										<div class="seach-element">
											<input type="text" placeholder="" name="projectName" class="m-wrap small">
										</div>
									</div>

<!-- 									<div class="table-seach"> -->
<!-- 										<label class="help-inline seach-label">发布时间:</label> -->
<!-- 										<div class="seach-element"> -->
<!-- 											<input type="text" placeholder="" name="projectName" class="m-wrap small"> -->
<!-- 										</div> -->
<!-- 									</div> -->
								</div>
								</form>
							</div>
							<div class="clearfix">
								<div class="pull-right">
									<a href="#" class="btn btngroup_seach" id="searchBtn"><i
										class="icon-search"></i>搜索</a>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sampleBatchSetBack_tb">
								<thead>
									<tr>
										<th class="hidden-480">项目名称</th>
										<th class="hidden-480">发布机构</th>
<!-- 										<th class="hidden-480">发布时间</th> -->
										<th class="hidden-480">抽样任务数量</th>
										<th class="hidden-480">上报已接收</th>
										<th class="hidden-480">上报未接收</th>
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