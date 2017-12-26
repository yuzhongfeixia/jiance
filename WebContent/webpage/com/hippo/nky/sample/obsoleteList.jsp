<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<link href="assets/plugins/select2/select2_metro.css" rel="stylesheet" type="text/css" />
<script src="assets/plugins/select2/select2.min.js" type="text/javascript"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />

<script type="text/javascript">
var $modal = $('#ajax-modal');
jQuery(document).ready(function() {
	$('#searchBtn').on('click', function(){
		setQueryParams('obsoleteTable',$('#searchConditionForm').getFormValue());
		refreshListToFirst($('#obsoleteTable'));
	});
	
   	registAjaxDataTable({
	   	id:"obsoleteTable",
		actionUrl: "samplingInfoController.do?obsoleteList&sampleStatus=2&rand="+Math.random(),
		search:true,
		aoColumns:[
				{ "mDataProp": "rn"},
				{ "mDataProp": "dcode",
				  "mRender" : function(data, type, full) {
						return '<a onclick=\"toDetal(\''+full.sampleCode+'\',\''+full.projectCode+'\')\">'+data+'</a>';
				  }
				},
				{ "mDataProp": "projectName"},
				{ "mDataProp": "agrname"},
				{ "mDataProp": "unitFullname"},
				{ "mDataProp": "monitoringLink",
				  "mRender" : function(data, type, full) {
				  	return full.monitoringLink_name;
				  }		
				},
				{ "mDataProp": "samplingDate"}
				],
			aoColumnDefs : [ {
				'bSortable' : false,
				'aTargets' : [0,1,2,3,4,5,6]
			} ]
	});
});

$('#ajax_add_btn').on('click', function(){
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
     $modal.load('samplingInfoController.do?toAddObsolete', '', function(){
      $modal.modal({width:"650px"});
      App.unblockUI(pageContent);
    });
});

function toDetal(sampleCode,projectCode){
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
     $modal.load('samplingInfoController.do?samplingInfoDetails&sampleCode='+sampleCode+'&projectCode='+projectCode, '', function(){
      $modal.modal({width:"850px"});
      App.unblockUI(pageContent);
    });
}

//重置
function reset() {
	$('#dCode').val('');
	$('#projectName').val('');
	$('#sampleCode').val('');
	$('#unitFullname').val('');
	$('#monitoringLink').val('');
}
</script>
</head>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" id="searchConditionForm" name="searchConditionForm" class="form-horizontal">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">废样条码:</label>
									<div class="seach-element">
										<input type="text" placeholder="" id="dCode" name="dCode" class="m-wrap small">
									</div>
								</div>
								
								<div class="table-seach">
									<label class="help-inline seach-label">监测项目:</label>
									<div class="seach-element">
										<input type="text" placeholder="" id="projectName" name="projectName" class="m-wrap small">
									</div>
								</div>
								
								<div class="table-seach">
									<label class="help-inline seach-label">样品名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" id="sampleCode" name="sampleCode" class="m-wrap small">
									</div>
								</div>
								
								<div class="table-seach">
									<label class="help-inline seach-label">受检单位:</label>
									<div class="seach-element">
										<input type="text" placeholder="" id="unitFullname" name="unitFullname" class="m-wrap small">
									</div>
								</div>
								
								<div class="table-seach">
									<label class="help-inline seach-label">抽样环节:</label>
									<div class="seach-element">
										<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false" 
											extend="{class:{value:'small'}}"></t:dictSelect>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<t:authFilter name="ajax_add_btn"><a class="btn btngroup_usual" data-toggle="modal" id="ajax_add_btn"><i class="icon-plus"></i>添加废样</a></t:authFilter>
						</div>
						<div class="pull-right">
							<t:authFilter name="searchBtn"><a href="#" class="btn btngroup_seach" id="searchBtn"><i class="icon-search"></i>搜索</a></t:authFilter>
<%--  							<t:authFilter name="resetBtn"><button type="button" class="btn btngroup_usual" onclick="reset();" id="resetBtn"><i class="icon-reload"></i>重置</button></t:authFilter> --%>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="obsoleteTable">
						<thead>
							<tr>	
								<th class="hidden-480">序号</th>
								<th class="hidden-480">废样条码</th>
								<th class="hidden-480">抽样项目</th>
								<th class="hidden-480">样品名称</th>
								<th class="hidden-480">受检单位名称</th>
								<th class="hidden-480">抽样环节</th>
								<th class="hidden-480">抽样时间</th>
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