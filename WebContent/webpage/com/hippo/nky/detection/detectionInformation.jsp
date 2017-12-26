<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script>
// 设置共通的缓存值
var tempVal = {};
var projectCode = "${projectCode}";
function getFormNotNullVal(formId){
	var tempCode = $("#" + formId).getFormValue();
	for(var key in tempCode){
		var value = tempCode[key];
		// 如果是空则认为是0
		if(isEmpty(value)){
			tempCode[key] = '0';
		}
		if("未检出" == value){
			delete tempCode[key];
		}
		if("未检" == value){
			tempCode[key] = '-1';
		}
	}
	return tempCode;
}
function setTempVal(tableId){
	$("#" + tableId + " input").each(function(){
		var tempName = $(this).attr("name");
		var tempCode = tempVal[tempName];
		if(isNotEmpty(tempCode)){
			$(this).val(tempCode);
		}
	});
}
function setTempValsToSubmit(data){
	data.params["projectCode"] = projectCode;
	// 实际工作中labCode可能带中文
	data.params["labCode"] = '${labCode}';
	$.extend(data.params, tempVal, getFormNotNullVal(data.arguments_0));
	for(var key in data.params){
		var value = data.params[key];
		// 如果是空则认为是0
		if(isEmpty(value)){
			data.params[key] = '0';
		}
		if("未检出" == value){
			delete data.params[key];
		}
		if("未检" == value){
			data.params[key] = '-1';
		}
	}
	return data;
}
</script>
<c:if test="${isSample}">
	<style>
	table.detInfo_table{
		width: 100%;
		margin: 5px 0px 0px 0px;
	}
	.detInfo_table td{
/* 		vertical-align: middle; */
	}
	.detInfo_table th{
/* 		text-align:center; */
	}
	.detInfo_table td.detInfo_title{
/* 		text-align:right; */
		width:20%;
	} 
	.detInfo_table td.detInfo_value{
		width:30%;
	} 
	.scrollDiv{
		max-height:300px;
		overflow-y: auto;
	}
	.detInfo_value .help-inline{
		color:red;
	}
	</style>
	<script>
	var labCode = "${labCode}";
	var agrCode = "${agrCode}";
	function setResultPollSeach(){
		var setBackFlg = "${setBackFlg}";
		var sampleStatus = '4';
		if (setBackFlg == 'true') {
			sampleStatus = '5';
		}
		// 取得缓存的值
		$.extend(tempVal, getFormNotNullVal("frm_set_result_poll"));
		var pollName = $("#pollName").val();
		var jsonParam = {};
		jsonParam["targetUrl"] = 'detectionController.do?getDetectionInfoItems&isSample=true';
		jsonParam["params"] = {"projectCode" : projectCode, "labCode" : labCode, "agrCode" : agrCode, "pollName" : pollName, "sampleStatus" : sampleStatus};
		jsonParam["after"] = "setResultPollTable";
		AjaxMode.nomalAction(jsonParam);
		setTempVal('table_set_result_poll');
	}
	function setResultPollTable(data){
		if(data.success){
			var detInfoList = data.attributes.detInfoList;
			var htmls = "";
			// 如果污染物只有一个,则只显示2列
			if(detInfoList.length == 1){
				$("#table_set_result_poll thead").html('<tr><th>污染物</th><th>检出值</th></tr>');
				htmls += "<tr>";
				htmls += getdetInfoTdHtmls(detInfoList[0]);
				htmls += "</tr>";
			} else {
				$("#table_set_result_poll thead").html('<tr><th>污染物</th><th>检出值</th><th>污染物</th><th>检出值</th></tr>');
				var needRows = Math.floor((detInfoList.length*2)/4);
				var detIndex = 0;
				for(var row = 0; row < needRows; row++){
					htmls += "<tr>";
					var detNum = detIndex;
					var stopNum = detNum + 1;
					for(detNum; detNum <= stopNum; detNum++){
						if(detNum < detInfoList.length){
							htmls += getdetInfoTdHtmls(detInfoList[detNum]);
						}
					}
					detIndex += 2;
					htmls += "</tr>";
				}
				// 计算剩余的格子数
				var modTd = ((detInfoList.length*2)%4);
				// 如果剩余了格子说明有单数
				if(modTd != 0){
					htmls += "<tr>";
					var detNum = detIndex;
					var stopNum = detNum + 1;
					for(detNum; detNum <= stopNum; detNum++){
						if(detNum < detInfoList.length){
							htmls += getdetInfoTdHtmls(detInfoList[detNum]);
						}
					}
					modTd = Math.abs(modTd - 4);
					for(var j=0 ; j <= modTd-1; j++){
						htmls +="<td></td>";
					}
					htmls += "</tr>";
				}
			}
			
			$("#table_set_result_poll tbody").html(htmls);
			
			$("#ajax-modal").modal('layout');
		}
	}
	function getdetInfoTdHtmls(detInfoItem){
		var htmls = "";
		var pollName = detInfoItem.pollName || '';
		var detectionValue = detInfoItem.detectionValue || '';
		if(detectionValue < 0){
			detectionValue = '未检';
		} else if(detectionValue == 0){
			detectionValue = '未检出';
		}
		htmls += '<td class="detInfo_title">' + pollName + '</td>';
		htmls += '<td class="detInfo_value"><input type="text" name="pld_' + detInfoItem.id + '" class="m-wrap small" value="' + detectionValue + '" ignore="ignore" datatype="/^未检$|^未检出$|^\\d{1,4}(\\.\\d{1,6})?$/" errormsg="输入值不合法！"/></td>';
		return htmls;
	}
	</script>
	<div class="row-fluid">
		<div class="span12">  
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i>
						<span class="hidden-480">设置检测结果</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="clearfix" style="margin-top: 5px;">
						<span>实验室编码:</span>
						<span>${labCode}</span>
						<c:if test="${isQtOper}">
						<span>样品条码:</span>
						<span>${dCode}</span>
						</c:if>
					</div>
					<div class="alert alert-success" style="margin-top: 5px;">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">污染物名称:</label>
								<div class="seach-element">
									<input type="text" id="pollName" name="pollName" placeholder="" class="m-wrap small">
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a class="btn btngroup_seach" onClick="setResultPollSeach()"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<form id="frm_set_result_poll" action="#" class="form-horizontal" validate="true">
						<input type="hidden" value="${labCode}"/>
						<div class="scrollDiv">
							<span style="float:right">单位: mg/kg(mg/l)</span>
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
												<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="输入值不合法！"/>
											</td>
										</tr>
									</c:if>
									<c:if test="${fn:length(detInfoList) != 1}">
										<c:set var="needRows" value="${(fn:length(detInfoList)*2)/4}"></c:set>
										<c:set var="detIndex" value="0"></c:set>
										<c:forEach begin="0" end="${(needRows-1) < 0 ? 0 : needRows-1}" var="row"> 
											<tr>
												<c:forEach items="${detInfoList}" begin="${detIndex}" end="${detIndex+1}" var="detInfo" varStatus="status">
													<td class="detInfo_title">${detInfo.pollName}</td>
													<td class="detInfo_value">
														<c:set var="detInfoValueStr" value="${empty detInfo.detectionValue? '未检出' :detInfo.detectionValue < 0?'未检':detInfo.detectionValue == 0?'未检出':detInfo.detectionValue}"/>
														<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="输入值不合法！"/>
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
														<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="输入值不合法！"/>
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
						</div>
					</form>
					<div class="clearfix">
						<div class="table-seach">
							<p style="color:red;">注： 检出值只能填写大于零的数据，小数点后最多保留6位；<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未检的参数值填汉字“未检”<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;若需要从其他值改回“未检出”填数字0</p>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						<c:if test="${setBackFlg == 'false'}">
						<button type="button" class="btn popenter" action-mode="ajax" action-url="detectionController.do?saveDetectionValue" action-before="setTempValsToSubmit('frm_set_result_poll')"
							action-operation="popsave" action-fresh="grid_detectionInformation_sample" action-form="frm_set_result_poll">保存</button>
						</c:if>
						<c:if test="${setBackFlg == 'true'}">
						<button type="button" class="btn popenter" action-mode="ajax" action-url="detectionController.do?saveDetectionValue&setBackFlg=${setBackFlg}&isQtOper=${isQtOper}&orgCode=${orgCode}" action-before="setTempValsToSubmit('frm_set_result_poll')"
							action-operation="popsave" action-fresh="grid_detectionInformation_sample" action-form="frm_set_result_poll" action-after="refresh_DetectionSetBack2">确认提交</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${!isSample}">
<script>
var casCode = "${casCode}";
function setResultAgrSeach(){
	//取得缓存的值
	$.extend(tempVal, getFormNotNullVal("frm_set_result_sample"));
	var sampleName = $("#sampleName").val();
	var labCode = $("#labCode").val();
	var jsonParam = {};
	jsonParam["targetUrl"] = 'detectionController.do?getDetectionInfoItems&isSample=false';
	jsonParam["params"] = {"projectCode" : projectCode, "casCode" : casCode, "sampleName" : sampleName, "labCode" : labCode};
	jsonParam["after"] = "setResultAgrTable";
	AjaxMode.nomalAction(jsonParam);
	setTempVal('table_set_result_poll');
}
function setResultAgrTable(data){
	if(data.success){
		var detInfoList = data.attributes.detInfoList;
		var htmls = "";
		for(var i = 0; i < detInfoList.length; i++){
			var labCode = detInfoList[i].labCode || '';
			var sampleName = detInfoList[i].sampleName || '';
			var detectionValue = detInfoList[i].detectionValue || '';
			if(detectionValue < 0){
				detectionValue = '未检';
			} else if(detectionValue == 0){
				detectionValue = '未检出';
			}
			htmls += "<tr>";
			htmls += '<td>' + labCode + '</td>';
			htmls += '<td>' + sampleName + '</td>';
			htmls += '<td><input type="text" name="pld_' + detInfoList[i].id + '" class="m-wrap small" value="' + detectionValue + '" ignore="ignore" datatype="/^未检$|^未检出$|^\\d{1,4}(\\.\\d{1,6})?$/" errormsg="输入值不合法！"/></td>';
			htmls += "</tr>";
		}
		$("#table_set_result_sample tbody").html(htmls);
		$("#ajax-modal").modal('layout');
	}
}
function makeAllunDetection(pollName){
	$("#confim_all_unDetection_modal").confirmModal({
		body: '是否将' + pollName + '对应的<font style="color:red">【所有样品】</font>设置成未检？',
		callback: function () {
			var jsonParam = {};
			jsonParam["targetUrl"] = 'detectionController.do?setAllWithUnDetection';
			jsonParam["params"] = {"projectCode" : projectCode, "casCode" : casCode};
			jsonParam["after"] = "unDetectionComplete";
			AjaxMode.nomalAction(jsonParam);
		}
	});
}
function unDetectionComplete(){
	modalTips('操作成功');
	$("#sampleName").val('');
	$("#labCode").val('');
	setResultAgrSeach();
	$("#btn_detectionInfo_poll_save").attr("disabled", "disabled");
}
</script>
<div class="row-fluid">
	<div class="span12">  
		<div id="detectionInfo_poll" class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">设置检测结果</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="clearfix" style="margin-top: 5px;">
					<span>污染物名称:</span>
					<span>${pollName}</span>
				</div>
				<div class="alert alert-success" style="margin-top: 5px;">
					<div class="clearfix">
						<div class="table-seach">
							<label class="help-inline seach-label">实验室编码:</label>
							<div class="seach-element">
								<input id="labCode" type="text" placeholder="" class="m-wrap small">
							</div>
						</div>
						<div class="table-seach">
							<label class="help-inline seach-label">样品名称:</label>
							<div class="seach-element">
								<input id="sampleName" type="text" placeholder="" class="m-wrap small">
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="pull-left">
						<a id="btn_sample_all_unDetection" href="#" class="btn btngroup_usual" onclick="makeAllunDetection('${pollName}')"><i class="icon-ban-circle"></i>全部未检</a>
					</div>
					<div class="pull-right">
						<a href="#" class="btn btngroup_seach" onClick="setResultAgrSeach()"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<form id="frm_set_result_sample" action="#" class="form-horizontal" validate="true">
					<div class="scrollDiv">
						<span style="float:right">单位: mg/kg(mg/l)</span>
						<table id="table_set_result_sample" class="table table-striped table-bordered table-hover " style="margin-top: 5px;">
							<thead>
								<tr>
									<th>实验室编码</th>
									<th>样品名称</th>
									<th>检出值</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${detInfoList}" var="detInfo" varStatus="stuts">
									<tr>
										<td>${detInfo.labCode}</td>
										<td>${detInfo.sampleName}</td>
										<td>
											<c:set var="detInfoValueStr" value="${detInfo.detectionValue < 0?'未检':detInfo.detectionValue == 0?'未检出':detInfo.detectionValue}"/>
											<input name="pld_${detInfo.id}" type="text" class="m-wrap small" value="${detInfoValueStr}" ignore="ignore" datatype="/^未检$|^未检出$|^\d{1,4}(\.\d{1,6})?$/" errormsg="输入值不合法！"/>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form>
				
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					<button id="btn_detectionInfo_poll_save" type="button" class="btn popenter" action-mode="ajax" action-url="detectionController.do?saveDetectionValue" action-before="setTempValsToSubmit('frm_set_result_sample')"
							action-operation="popsave" action-fresh="grid_detectionInformation_poll" action-form="frm_set_result_sample">保存</button>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="confim_all_unDetection_modal" class="modal hide fade" tabindex="-1" ></div>
</c:if>