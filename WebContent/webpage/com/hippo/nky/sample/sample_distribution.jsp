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


<script>
    LinkChange.init();
    
    $('.date-picker').datepicker({
        rtl : App.isRTL(),
        language: "zh",
        autoClose: true,
        format: "yyyy-mm-dd",
        todayBtn: true,
        clearBtn:true
    });
 
	$('#searchBtn').on('click', function(){
		setQueryParams('sample_distribution_table',$('#searchConditionForm').getFormValue());
		$("#sample_distribution_table").dataTable().fnPageChange('first'); 
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"sample_distribution_table",
				actionUrl:"samplingInfoController.do?sampleDistributeDataGrid&rand="+Math.random(),
				search:true,
				aoColumns:[
						{ "mDataProp": "projectName"},
						{ "mDataProp": "releaseunit"},
						{ "mDataProp": "publishDate"},
						{ "mDataProp": "count"},
						{ "mDataProp": "dscount"},
						{
						"mData" : 'id',
						bSortable : false,
						"mRender" : function(data, type, full) {
							return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="packingDistribute(this,\''+data+'\',\''+full.packingFlg+'\')">整包分发</a>'+
								   '&nbsp;<a class="btn mini yellow" onclick="unPackingDistribute(this,\''+data+'\',\''+full.packingFlg+'\')">拆包分发</a>';
							}
						}
						]
			});


	});
	
	function packingDistribute($this, projectCode, packingFlg) {
		if (packingFlg == '1') {
			modalAlert("已经拆包的项目，不可以进行整包分发!");
			return;
		}
		loadContent($this, 'samplingInfoController.do?packingdistribute&projectCode='+projectCode);
	}
	
	function unPackingDistribute($this, projectCode, packingFlg) {
		if (packingFlg == '0') {
			modalAlert("已经整包发送的项目，不必再进行拆包分发!");
			return;
		}
		loadContent($this, 'samplingInfoController.do?unpackingdistribute&projectCode='+projectCode);
	}
	
	
</script>

<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">项目名称:</label>
									<div class="seach-element">
										<input type="text" name="name" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">发布时间:</label>
									<div class="seach-element">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" name="publishDate_begin"/>
										<label class="help-inline">～</label>
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" name="publishDate_end"/>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="sample_distribution_table">
						<thead>
							<tr>
								<th>项目名称</th>
								<th class="hidden-480">发布机构</th>
								<th class="hidden-480">发布时间</th>
								<th class="hidden-480">样品数量</th>
								<th class="hidden-480">已分发数量</th>
								<th class="hidden-480"></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
		</div>
	
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
</body>
</html>				