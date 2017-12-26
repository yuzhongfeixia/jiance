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

function setreplaceSamplingBlindToSubmit(data){
	for(var key in data.params){
		var value = data.params[key];
		// 如果是空则认为是0
		if(isEmpty(value)){
			data.params[key] = '0';
		}
		if("未检出" == value){
			data.params[key] = '0';
		}
		if("未检" == value){
			data.params[key] = '-1';
		}
	}
	return data;
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
					<span class="hidden-480">检测结果替换</span>
				</div>
			</div>
			<div class="portlet-body">
				
				
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form id="replace_sampling_blind_form" name="replace_sampling_blind_form" action="#" class="form-horizontal">
							
							<table class="table table-striped table-bordered table-hover " style="margin-top: 5px;">
								<tr>
									<td>
										制样编码
									</td>
									<td>${samplingInfo.spCode}</td>
									<td>
										样品名称
									</td>
									<td>${cname}</td>
								</tr>
								<tr>
									<td>
										抽样单位
									</td>
									<td>${sogrName}</td>
									<td>
										检测单位
									</td>
									<td>${dogrName}</td>
								</tr>
								<tr>
									<td>
										检测值
									</td>
									<td colspan='3'>${blindSample.blindSampleValue}
									</td>
									
								</tr>
							</table>
							
						<input type="hidden" name="projectCode" value="${projectCode}"/>
						<input type="hidden" name="sampleCode" value="${sampleCode}"/>
						<input type="hidden" name="labCode" value="${samplingInfo.labCode}"/>
						<input type="hidden" name="detectionCode" value="${samplingInfo.detectionCode}"/>
						<input type="hidden" name="blindSampleValue" value="${blindSample.blindSampleValue}"/>
						
							<table id="table_set_result_poll" class="table table-striped table-bordered table-hover detInfo_table">
								<thead>
									<tr>
										<th>污染物</th>
										<th>检出值</th>
										<%--  如果污染物只有一个,则只显示2列 --%>
										<c:if test="${fn:length(detInfoList) != 1}">
											<th>污染物</th>
											<th>检出值</th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:if test="${fn:length(detInfoList) == 1}">
										<tr>
											<c:set var="detInfo" value="${detInfoList[0]}"></c:set>
											<td class="detInfo_title">${detInfo.pollName}</td>
											<td class="detInfo_value">
												<c:set var="detInfoValueStr" value="${detInfo.detectionValue < 0?'未检':detInfo.detectionValue == 0?'未检出':detInfo.detectionValue}"/>
												<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="请输入整数4位，小数6位的数字"/>
											</td>
										</tr>
									</c:if>
									<c:if test="${fn:length(detInfoList) != 1}">
										<c:set var="needRows" value="${(fn:length(detInfoList)*2)/4}"></c:set>
										<c:set var="detIndex" value="0"></c:set>
										<c:forEach begin="0" end="${needRows-1}" var="row"> 
											<tr>
												<c:forEach items="${detInfoList}" begin="${detIndex}" end="${detIndex+1}" var="detInfo" varStatus="status">
													<td class="detInfo_title">${detInfo.pollName}</td>
													<td class="detInfo_value">
														<c:set var="detInfoValueStr" value="${empty detInfo.detectionValue? '未检出' :detInfo.detectionValue < 0?'未检':detInfo.detectionValue == 0?'未检出':detInfo.detectionValue}"/>
														<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="请输入整数4位，小数6位的数字"/>
													</td>
												</c:forEach>
												<c:set var="detIndex" value="${detIndex+2}"></c:set>
											</tr>
										</c:forEach> 
										<c:set var="modTd" value="${(fn:length(detInfoList)*2)%4}"></c:set>
										<c:if test="${modTd != 0}">
											<tr>
												<c:forEach items="${detInfoList}" begin="${detIndex}" end="${detIndex+1}" var="detInfo" varStatus="status">
													<td class="detInfo_title">${detInfo.pollName}</td>
													<td class="detInfo_value">
														<c:set var="detInfoValueStr" value="${empty detInfo.detectionValue? '未检出' :detInfo.detectionValue < 0?'未检':detInfo.detectionValue == 0?'未检出':detInfo.detectionValue}"/>
														<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="请输入整数4位，小数6位的数字"/>
													</td>
												</c:forEach>
												<c:set var="modTd" value="${(modTd-4 < 0 ? 4-modTd:modTd-4)}"></c:set>
												<c:forEach begin="0" end="${modTd-1}"> 
													<td></td>
												</c:forEach>
											</tr>
										</c:if>
									</c:if>
								</tbody>
							</table>
			
							
		
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					<button type="button" class="btn popenter" action-mode="ajax" action-url="samplingInfoController.do?saveDetectionValue" action-before="setreplaceSamplingBlindToSubmit('frm_set_result_poll')"
							action-operation="popsave" action-fresh="sample_blind_tb" action-form="replace_sampling_blind_form">保存</button>
				</div>
			
				
			</div>
		</div>
	</div>
</div>
	<div id="ajax-modal1" class="modal hide fade" tabindex="-1" ></div>
</body>
</html>