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


<script>
 
	$('#searchBtn').on('click', function(){
		setQueryParams('packingDistribution_table',$('#searchConditionForm').getFormValue());
		$('#packingDistribution_table').dataTable().fnClearTable(true);
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"packingDistribution_table",
				actionUrl:"samplingInfoController.do?packingDistributeDataGrid&projectCode=${projectCode}",
				search:true,
				tableTools:true,
				aoColumns:[
						{ "mDataProp": "ogrname"},
						{ "mDataProp": "name"},
						{ "mDataProp": "areaname"},
						{ "mDataProp": "reportingDate"},
						{ "mDataProp": "rpCount"},
						{ "mDataProp": "oname"},
						{
						"mData" : 'id',
						bSortable : false,
						"mRender" : function(data, type, full) {
							return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="showSampleDetails(\''+data+ '\')">查看样品</a>';
							}
						}
				]
			});
	});	
	
	// 样品分发
	function sampleDistribute() {
		var projectCode = "${projectCode}";
		var checkFlg = false;
	
		var selectedParams = "";
		var oTT = TableTools.fnGetInstance( 'packingDistribution_table' );
	    var aSelectedTrs = oTT.fnGetSelected();
	    if(aSelectedTrs.length != 0){
	    	selectedParams = aSelectedTrs[0].id;
	    	checkFlg = true;
	    }
		if (!checkFlg) {
			modalAlert("请指定一条抽样单位记录!");
			return;
		}
		
		var selectedArr = new Array();
		selectedArr = selectedParams.split(',');
		// 抽样单位
		var orgCode = selectedArr[0];
		// 市代码
		var cityCode = selectedArr[1];
		// 区代码
		var countyCode = selectedArr[2];
		
    	// 已经被质检机构接收的样品不可以再分发
    	$.ajax({
			async: false,
			type : "POST",
			url : 'samplingInfoController.do?checkRecvPacking',
			data : {'projectCode':projectCode,'samplingOrgCode':orgCode,'cityCode':cityCode,'countyCode':countyCode},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				checkFlg = dataJson.success;
			}
		});
    	if (!checkFlg) {
			modalAlert("已经被接收的样品不可以再分发!");
			return;
		}
		
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?samplingorg&flg=packing&orgCode='+orgCode+'&projectCode='+projectCode+'&cityCode='+cityCode+'&countyCode='+countyCode, '', function(){
	      $modal.modal({width:"800px"});
	      App.unblockUI(pageContent);
	    });
	}
	
	// 查看样品
	function showSampleDetails(data) {
		var selectedArr = new Array();
		selectedArr = data.split(',');
		// 抽样单位
		var orgCode = selectedArr[0];
		// 市代码
		var cityCode = selectedArr[1];
		// 区代码
		var countyCode = selectedArr[2];
		
		var projectCode = "${projectCode}";
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?showsampledetails&samplingOrgCode='+orgCode+'&projectCode='+projectCode+'&cityCode='+cityCode+'&countyCode='+countyCode, '', function(){
	      $modal.modal({width:"1000px"});
	      App.unblockUI(pageContent);
	    });
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
									<label class="help-inline seach-label">抽样单位:</label>
									<div class="seach-element">
										<input type="text" name="ogrname" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">项目名称:</label>
									<div class="seach-element">
										<input type="text" name="name" placeholder="" class="m-wrap small">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					        <a class="btn btngroup_usual" onclick="sampleDistribute();"><i class="icon-external-link"></i>样品分发</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="packingDistribution_table">
						<thead>
							<tr>
								<th class="hidden-480">抽样单位</th>
								<th class="hidden-480">项目名称</th>
								<th class="hidden-480">抽样地区</th>
								<th class="hidden-480">上报日期</th>
								<th class="hidden-480">上报数量</th>
								<th class="hidden-480">接收机构</th>
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