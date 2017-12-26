<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
$('#sBtn').on('click', function(){
	setQueryParams('addSamplingBlind',$('#searchForm').getFormValue());
	refreshListToFirst($("#addSamplingBlind"));
});

$(document).ready(function(){
	registAjaxDataTable({
		id : "addSamplingBlind",
		actionUrl : "samplingInfoController.do?getAddSamplingBlindData",		
		aoColumns:[
					{"mDataProp" : "rn"},
					{"mDataProp" : "taskName"},
					{ "mDataProp": "ogrName"},
					{ "mDataProp": "taskCode",
						"mRender" : function(data, type, full) {
					  		return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="selectSamplingBlind(\''+data+'\')">选择样品</a>';
					     }	
					}
				],
		search : true
	});
}); 


function selectSamplingBlind(data){
 	var $modal1 = $('#ajax-modal1'); 
   	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	$modal1.load('samplingInfoController.do?selectSamplingBlind&taskCode='+data, '', function(){
	    $modal1.modal({width:"960px"});
	    App.unblockUI(pageContent);
	    Validator.init();
    });
}

</script>
</head>
<body>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">添加盲样</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form action="#" class="form-horizontal" name="searchForm" id="searchForm">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">任务名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="projectName" class="m-wrap small">
								</div>
							</div>
							
							<div class="table-seach">
								<label class="help-inline seach-label">抽样单位:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="unitFullname" class="m-wrap small">
								</div>
							</div>

						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="sBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="addSamplingBlind">
					<thead>
						<tr>
							<th class="hidden-480">序号</th>
							<th class="center hidden-480">所属任务</th>
							<th class="center hidden-480" >抽样单位</th>
							<th class="center hidden-480" >样品信息</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
	<div id="ajax-modal1" class="modal hide fade" tabindex="-1" ></div>
</body>
</html>