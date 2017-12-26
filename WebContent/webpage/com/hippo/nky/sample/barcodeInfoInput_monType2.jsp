<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>
<style>
#monitoring_plan_program_tab1 .dropdown-menu {
	font-size: 13px;
}
#monitoring_plan_program_tab1 .dropdown-menu li{
	width:206px;
}
</style>
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

$(document).ready(function(){
	 $("input[name$='agrCode']").select2({
		 data:eval("${jsonBreedList}"),
		 formatNoMatches:'无此数据'
	 });
});

function setAclickEvent(data){
	data.thisElem.attr("clickElem",true);
	return true;
}

function callSetId(data){
	var asibling = $("a[clickElem='true']").siblings();
    $(asibling[0]).val(data.params.code);
    $(asibling[1]).focus();
	$(asibling[1]).val(data.params.name);
	$("a[clickElem='true']").removeAttr("clickElem");
}

function checkself(data) {
	var validator = $("#saveForm").data("validator");
	if(validator != null){
		// 如果已经注册了validateForm插件，则进行check
		validFlg = validator.check(false, '#'+data.id);
		if(!validFlg){
			return;
		}
	}

}

function setAutoData(){
 	var name = $("#unitFullname").val();
 	var monitoringSiteInfo = null;
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?getMonitoringSiteInfo",
		data : {'name':name},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			monitoringSiteInfo = dataJson.attributes.monitoringSiteInfo;
		}
	});
 	if (monitoringSiteInfo != null) {
 		$("#unitFullcode").val(monitoringSiteInfo.code);
 		$("#unitAddress").val(monitoringSiteInfo.address);
 		$("#legalPerson").val(monitoringSiteInfo.legalPerson);
 		$("#zipCode").val(monitoringSiteInfo.zipcode);
 		$("#telphone").val(monitoringSiteInfo.contact);
 		$("#contact").val(monitoringSiteInfo.contactPerson);
 		$("#fax").val(monitoringSiteInfo.fax);
 	} else {
 		$("#unitFullcode").val("");
 		$("#unitAddress").val("");
 		$("#legalPerson").val("");
 		$("#zipCode").val("");
 		$("#telphone").val("");
 		$("#contact").val("");
 		$("#fax").val("");
 	}
}

function checkTaskCount() {
	if($("[name='id']").val() != '') {
		var reqTaskCode = "${barcodeInfoInputPage.taskCode}";
		// 编辑时，若任务没有改变
		if (reqTaskCode == $("[name='taskCode']").val()) {
			return true;
		}
	}
	//var jsonDatas = {"projectCode" : $("#projectCode").val()};
	var checkResult = true;
	var jsonDatas = {};
	$("[name='taskCode']").each(function(i, element){
		var taskCode = $(element).val();
		if(jsonDatas[taskCode] != null){
			jsonDatas[taskCode] = jsonDatas[taskCode]+1;
		}else{
			jsonDatas[taskCode] = 1;	
		}
	});
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?checkTaskCount",
		data : jsonDatas,
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			checkResult = dataJson.success;
			if (!checkResult) {
				modalAlert(dataJson.msg);
			} else {
				// 防止重复提交
				$("button[class='btn popenter']").attr("disabled",true);
			}
		}
	});
	return checkResult;
}

/**
 * 退回时需验证样品名称是否修改
 */
function sampleNameChange(){
	//return false;
	var agrCode = "";
	var oldAgrCode = "${barcodeInfoInputPage.agrCode}";
	var breedsize = "${fn:length(breedList)}";
// 	if (breedsize == 0) {
// 		agrCode = $("input[name='agrCode']").val();
// 	} else {
// 		agrCode = $("select[name='agrCode']").val();
// 	}
	agrCode = $("input[name='agrCode']").val();
	if (agrCode != oldAgrCode) {
		return true;
	} 
	return false;
}

/**
 * 确认提交
 */
function setBackSubmit() {
	if (!checkTaskCount()) {
		return;
	}

	var isSampleNameChange = sampleNameChange(); 
		
	var $modal1 = $('#ajax-modal1'); 
    $modal1.load('samplingInfoController.do?confirmSubmit&isSampleNameChange='+isSampleNameChange+'&vdcode=${vdcode}', '', function(){
      $modal1.modal({width:"562px"});
    });
}

var isSamplingAddressNull = "";
function samplingAddressChk(data) {
	if ($("#countyCode").val() == "" || $("#countyCode").val() == null) {
		modalTips("请先选择抽样地区！");
		isSamplingAddressNull = "1";
	} else {
		isSamplingAddressNull = "0";
	}
}

function resetSpCode(data){
	if (isSamplingAddressNull == "1") {
		data.value="";
	}
}


function setSpCodeAjax(data) {
	$("#spCode").attr("ajaxurl", "samplingInfoController.do?checkSpCode&projectCode=${barcodeInfoInputPage.projectCode }&countyCode="+data.value);
	// ajax动态取得行政区域自定义编码
	$.ajax({
		async: false,
		type : "POST",
		url : "samplingInfoController.do?getSysAreaSelfCode",
		data : "code="+data.value,
		success : function(data) {
			 var d = $.parseJSON(data);
			 var selfcode = d.attributes.selfCode;
			 $("#pre_spcode").val(selfcode);	
		}
	});
}
</script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">监督抽查样品信息</span>
				</div>
			</div>
			<div class="portlet-body" id="monitoring_plan_program_tab1">
				<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
				<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
				<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
				<input name="sampleCode" type="hidden" value="${barcodeInfoInputPage.sampleCode}"/>
				<input name="superviseCheckEntity.id" type="hidden" value="${barcodeInfoInputPage.superviseCheckEntity.id }"/>
				<input name="superviseCheckEntity.sampleCode" type="hidden" value="${barcodeInfoInputPage.superviseCheckEntity.sampleCode}"/>
				<input id="pre_spcode" name="pre_spcode" type="hidden" value="${pre_spcode}"/>
					<table class="table sample_info_table">
						<c:if test="${detecthedFlg == 0}">
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样人员姓名</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<div class="value-elems">
									<input id="samplingPersons" name="samplingPersons" type="text" class="m-wrap"  datatype="*" ignore="ignore" value="${barcodeInfoInputPage.samplingPersons}"/>
								</div>
							</td>
						</tr>
						</c:if>
						<c:if test="${detecthedFlg == 1}">
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样人员姓名</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input id="samplingPersons" name="samplingPersons" type="text" class="m-wrap"  datatype="*" ignore="ignore" value="${barcodeInfoInputPage.samplingPersons}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>制样编码</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<input id="spCode" name="spCode" type="text" class="m-wrap"  datatype="n" value="${barcodeInfoInputPage.show_spcode}" onfocus="samplingAddressChk(this);" onblur="resetSpCode(this)"/>
								</div>
							</td>
						</tr>
						</c:if>
						<tr>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>样品名称</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<c:if test="${fn:length(breedList)  == 0 }">
										<input name="agrCode" type="hidden" value="${barcodeInfoInputPage.agrCode}"/>
										<input type="text" placeholder="" class="m-wrap small" value="${sampleName}" datatype="*" nullMsg="请选择！" readonly="readonly">
										<a class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a>
									</c:if>
									<c:if test="${fn:length(breedList)  > 0 }">
<!-- 										<select class="m-wrap small" name="agrCode" datatype="*"> -->
<!-- 											<option value="" selected></option> -->
<%-- 											<c:forEach items="${breedList}" var="breed"> --%>
<%-- 												<option value="${breed.agrCode}" <c:if test="${breed.agrCode eq barcodeInfoInputPage.agrCode}">selected</c:if>>${breed.agrName}</option> --%>
<%-- 											</c:forEach> --%>
<!-- 										</select> -->
										<input type="hidden" name="agrCode" style="width: 129px;" tabindex="-1" title="" value="${barcodeInfoInputPage.agrCode}" datatype="*">
									</c:if>
									<c:if test="${not empty barcodeInfoInputPage.samplePath }">
										<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${barcodeInfoInputPage.samplePath}')">样品图片</a>
									</c:if>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>条码</span>
								</div>
							</td>
							<td class="value">
								<c:if test="${barcodeInfoInputPage.id == null}">
									<input name="dCode" type="text" placeholder="" class="m-wrap" value="${barcodeInfoInputPage.dCode}" maxlength="13"
										datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode&projectCode=${barcodeInfoInputPage.projectCode }">
								</c:if>
								<c:if test="${barcodeInfoInputPage.id != null}">
									<input name="dCode" type="text" placeholder="" class="m-wrap" value="${barcodeInfoInputPage.dCode}" readonly="readonly">
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>抽样任务</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<select class="m-wrap" style="width:350px!important" name="taskCode"  datatype="*">
									<option value="" selected></option>
									<c:forEach items="${monitoringTaskList}" var="task">
										<option value="${task.taskcode}" <c:if test="${task.taskcode eq barcodeInfoInputPage.taskCode}">selected</c:if>>${task.taskname}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>注册商标</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.tradeMark" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.tradeMark}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>包装</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul class="line">
										<li class="elemet"><input  type="radio" name="superviseCheckEntity.pack" value="1" <c:if test="${barcodeInfoInputPage.superviseCheckEntity.pack eq '1'}">checked</c:if>/>有</li>
										<li class="elemet"><input  type="radio" name="superviseCheckEntity.pack" value="0" <c:if test="${barcodeInfoInputPage.superviseCheckEntity.pack eq '0'}">checked</c:if>/>无</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>等级规格</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.specifications" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.specifications}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>标识</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul class="line">
										<li class="elemet"><input  type="radio" name="superviseCheckEntity.flag" value="1" <c:if test="${barcodeInfoInputPage.superviseCheckEntity.flag eq '1'}">checked</c:if>/>有</li>
										<li class="elemet"><input  type="radio" name="superviseCheckEntity.flag" value="0" <c:if test="${barcodeInfoInputPage.superviseCheckEntity.flag eq '0'}">checked</c:if>/>无</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>生产日期或批号</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.batchNumber" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.batchNumber}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>执行标准</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.execStandard" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.execStandard}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>产品认证登记情况</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<t:dictSelect id="superviseCheckEntity.productCer" field="superviseCheckEntity.productCer" typeGroupCode="productCer" hasLabel="false" defaultVal="${barcodeInfoInputPage.superviseCheckEntity.productCer}"></t:dictSelect>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>产品认证证书编号</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="superviseCheckEntity.productCerNo" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.productCerNo}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>获证日期</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input class="m-wrap small m-ctrl-medium date-picker" type="text" name="superviseCheckEntity.certificateTime" value="<fmt:formatDate value='${barcodeInfoInputPage.superviseCheckEntity.certificateTime}' type='both' pattern='yyyy-MM-dd'/>"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样数量</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.samplingCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.superviseCheckEntity.samplingCount}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>抽样基数</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="superviseCheckEntity.samplingBaseCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.superviseCheckEntity.samplingBaseCount}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>抽样场所</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
								
									<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="${industryMonitoringLink}" hasLabel="false" defaultVal="${barcodeInfoInputPage.monitoringLink}" extend="{datatype:{value : '*'}}"></t:dictSelect>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>抽样时间</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input class="m-wrap small m-ctrl-medium date-picker" id="samplingDate" type="text" name="samplingDate" 
										value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" datatype="*" nullMsg="请选择！" readonly="readonly" onchange="checkself(this)"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>抽样地点</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul id="areasDiv" class="line">
										<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},datatype:{value : '*'}}"></t:dictSelect></li>
										<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},datatype:{value : '*'},onchange:{value : 'setSpCodeAjax(this)'}}"></t:dictSelect></li>
									</ul>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>抽样详细地点</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.samplingAddress}"/>
								</div>
							</td>
						</tr>
					</table>
					<table class="table sample_info_table">
						<tr>
							<td class="title" rowspan="4" style="line-height: 144px;">
								<div class="title-label">
									<span>受检单位情况</span>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>单位全称</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<input id="unitFullcode" name="unitFullcode" type="hidden" value="${barcodeInfoInputPage.unitFullcode }"/>
<!-- 								<input id="unitFullname" name="unitFullname" type="text" class="m-wrap"  data-provide="typeahead"  -->
<%-- 									data-source="${nameSource}" data-ids="${codeNameSource}" datatype="*" value="${barcodeInfoInputPage.unitFullname}" onblur="setAutoData()"/> --%>
								<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" datatype="*" value="${barcodeInfoInputPage.unitFullname}" onkeyup="autoMatch()" onblur="setAutoData()"/>
								<div id="showmenu" style="min-width:320px;background-color: white;position:absolute;z-index:10999;border:1px solid;color: black;display:none" ></div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>通讯地址</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  id="unitAddress" name="unitAddress" type="text" datatype="*" ignore="ignore" class="m-wrap" value="${barcodeInfoInputPage.unitAddress}"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮编</span>
								</div>
							</td>
							<td class="value">
								<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" ignore="ignore" datatype="/^\d{6}?$/" value="${barcodeInfoInputPage.zipCode}"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>法定代表人</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<input  id="legalPerson" name="legalPerson" type="text" class="m-wrap" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.legalPerson}"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系人</span>
								</div>
							</td>
							<td class="value">
								<input  id="contact" name="contact" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.contact}"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>电话</span>
								</div>
							</td>
							<td class="value">
								<input  id="telphone" name="telphone" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.telphone}"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>传真</span>
								</div>
							</td>
							<td class="value">
								<input  id="fax" name="fax" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.fax}"/>
							</td>
						</tr>
						<tr>
							<td class="title" rowspan="3" style="vertical-align: middle;">
								<div class="title-label">
									<span>实施抽样单位情况</span>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>单位全称</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  name="" type="text" class="m-wrap" value="${org.ogrname}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>联系人</span>
								</div>
							</td>
							<td class="value">
								<input  name="" type="text" class="m-wrap small" value="${org.contacts}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>通讯地址</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  name="" type="text" class="m-wrap" value="${org.address}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮编</span>
								</div>
							</td>
							<td class="value">
								<input  name="" type="text" class="m-wrap small" value="${org.zipcode}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系电话</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  name="" type="text" class="m-wrap small" value="${org.contactstel}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>传真</span>
								</div>
							</td>
							<td class="value">
								<input  name="" type="text" class="m-wrap small" value="${org.fax}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>通知书编号及有效期</span>
								</div>
							</td>
							<td class="value" colspan="6">
								<input  name="superviseCheckEntity.noticeDetails" type="text" class="m-wrap large" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.superviseCheckEntity.noticeDetails}"/>
							</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						<c:if test="${setBackFlg == ''}">
						<button type="button" class="btn popenter" href="samplingInfoController.do?save" action-mode="ajax" action-event="click" action-operation="popsave" 
								 action-form="#saveForm" action-before="checkTaskCount()" action-load="正在保存信息..." action-async="true" action-after="refresh_samplingInfoList">保存</button>
					    </c:if>
					    <c:if test="${setBackFlg == true}">
			    		<button type="button" class="btn popenter" onclick="setBackSubmit()">确认提交</button>
			    		</c:if>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>