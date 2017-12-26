<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$('#searchBtn2').on('click', function(){
		setQueryParams('sampling_detail_table',$('#searchForm2').getFormValue());
		refresh_packingDistribute();
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"sampling_detail_table",
				actionUrl:"samplingInfoController.do?datagrid&sampleStatus=3,4,5&projectCode=${projectCode}&samplingOrgCode=${samplingOrgCode}&cityCode=${cityCode}&countyCode=${countyCode}",
				search:true,
				aoColumns:[
				   		{"mDataProp" : "agrname",
							 "mRender" : function(data, type, full) {
							  		return '<a onclick=\"showSampleDetals(\''+full.projectCode+'\',\''+full.sampleCode+'\')\">'+data+'</a>';
							 }	
						},
// 						{ "mDataProp": "agrname"},
						{ "mDataProp": "dcode"},
						{ "mDataProp": "spCode"},
						{ "mDataProp": "samplingOrgName"},
						{ "mDataProp": "reportingDate"}

				],
			});
	});
	
	function showSampleDetals(projectCode, sampleCode) {
		
	   	var $modal = $('#ajax-modal1'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?samplingInfoDetails&sampleCode='+sampleCode+'&projectCode='+projectCode, '', function(){
	      $modal.modal({width:"800px"});
	      App.unblockUI(pageContent);
	    });
	}
	
	function refresh_packingDistribute() {  
		$("#sampling_detail_table").dataTable().fnPageChange('first');  
	}
	
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">抽样样品信息</span>
				</div>
			</div>
			
			<div class="alert alert-success">
				<form id="searchForm2" name="searchForm2" action="#" class="form-horizontal">
					<div class="clearfix">	
						<div class="table-seach">
							<label class="help-inline seach-label">样品名称:</label>
							<div class="seach-element">
								<input type="text" id="sampleName" name="sampleName" placeholder="" class="m-wrap small">
							</div>
						</div>
						<div class="table-seach">
							<label class="help-inline seach-label">条码:</label>
							<div class="seach-element">
								<input type="text" id="dCode" name="dCode" placeholder="" class="m-wrap small">
							</div>
						</div>
<!-- 						<div class="table-seach"> -->
<!-- 							<label class="help-inline seach-label">抽样单位:</label> -->
<!-- 							<div class="seach-element"> -->
<!-- 								<input type="text" id="dcode2" name="dCode2" placeholder="" class="m-wrap small"> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>
				</form>
			</div>
			</div>
			<div class="clearfix">
				<div class="pull-right">
					<a href="#" id="searchBtn2" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
				</div>
			</div> 
			<table class="table table-striped table-bordered table-hover" id="sampling_detail_table">
				<thead>
					<tr>
					    <th class="hidden-480">样品名称</th>
						<th class="hidden-480">条码</th>
						<th class="hidden-480">制样编码</th>
						<th class="hidden-480">抽样单位</th>
						<th class="hidden-480">上报时间</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
<!-- 				<button type="button" class="btn popenter green" onclick="comfirma();">确定</button> -->
			</div>
	</div>
</div>

<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>
