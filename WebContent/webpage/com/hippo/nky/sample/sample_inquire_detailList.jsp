<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
$('#sBtn').on('click', function(){
	setQueryParams('sample_inquire_list',$('#searchForm').getFormValue());
	refreshListToFirst($("#sample_inquire_list"));
});

$(document).ready(function(){
	registAjaxDataTable({
		id : "sample_inquire_list",
		actionUrl : "samplingInfoController.do?barcodeInputList&sampleStatus=3,4,5&projectCode=${projectCode}&samplingOrgCode=${orgCode}&rand=" + Math.random(),
				
		aoColumns:[
					{"mDataProp" : "rn"},
					{"mDataProp" : "agrname",
					 "mRender" : function(data, type, full) {
					  return '<a action-mode=\'ajax\' action-before=\'setWidth()\' action-url=\'samplingInfoController.do?addorupdate&id='+full.id+'&projectCode=${projectCode}&flg=show\' action-pop=\'ajax-modal1\'>'+data+'</a>';
					 }	
					},
					{ "mDataProp": "unitFullname"},
					{"mDataProp" : "cityAndCountry"},
					{"mDataProp": "monitoringLink",
					 "mRender" : function(data, type, full) {
						return full.monitoringLink_name;
					  }	
					},
					{ "mDataProp": "samplingDate"}
				],
		search : true,
		aoColumnDefs : [ {
			'bSortable' : false,
			'aTargets' : [0,1,2,3,4,5]
		} ]
	});
}); 

function linkCountry(elem){
	var jsonParam = {};
	jsonParam["async"] = false;
	jsonParam["targetUrl"] = 'samplingInfoController.do?getCityOrCountryCodeList';
	var projectCode = $("#projectCode").val();
	var cityCode = $(elem).val();
	jsonParam["params"] = {"projectCode" : projectCode, "cityCode" : cityCode, "isCity" : false};
	jsonParam["after"] = "setCityLinkOptions";
	AjaxMode.nomalAction(jsonParam);
}
function setCityLinkOptions(data){
	var options = data.attributes.codeList;
	$("#countyCode").html(options);
}

function setWidth(){
	$("#ajax-modal1").attr("data-width","900");
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
					<span class="hidden-480">查看抽样详细</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form action="#" class="form-horizontal" name="searchForm" id="searchForm">
					<input type="hidden" name="projectCode" id="projectCode" value="${projectCode }">
					<input type="hidden" name="orgCode" id="orgCode" value="${orgCode }">
						<div class="controls" style="margin-left: 0;">
							<span class="help-inline">样品名称:</span><input type="text" name="sampleName" style="width:150px;" />
							<span class="help-inline">抽样环节:</span>
							<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false"></t:dictSelect>
							<span class="help-inline">抽样地区:</span>
							<t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${cityCodeList}" extend="{onChange:{value:'linkCountry(this)'}}"></t:dictSelect>
							<select id="countyCode" name="countyCode" class="m-wrap small"></select>
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="sBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover table-full-width" id="sample_inquire_list">
					<thead>
						<tr>
							<th class="hidden-480">序号</th>
							<th class="center hidden-480">样品名称</th>
							<th class="center hidden-480" >受检单位名称</th>
							<th class="center hidden-480" >抽样地</th>
							<th class="center hidden-480" >抽样环节</th>
							<th class="center hidden-480" >抽样时间</th>
						</tr>
					</thead>
					<tbody>
<!-- 						<tr class="odd gradeX">
							<td class="center hidden-480">1</td>
							<td class="center hidden-480"><a onclick="toDetal(1);">大白菜</a></td>
							<td class="center hidden-480">南环桥批发市场A001</td>
							<td class="center hidden-480">苏州市吴中区</td>
							<td class="center hidden-480">批发市场</td>
							<td class="center hidden-480">2013年5月3日</td>
						</tr>
 -->
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