<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>
<style>
#monitoring_plan_program_tab1 .dropdown-menu {
	font-size: 13px;
}
#monitoring_plan_program_tab1 .dropdown-menu li{
	width:206px;
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
 		//$("#fax").val(monitoringSiteInfo.fax);
 	} else {
 		$("#unitFullcode").val("");
 		$("#unitAddress").val("");
 		$("#legalPerson").val("");
 		$("#zipCode").val("");
 		$("#telphone").val("");
 		$("#contact").val("");
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
					<i class="icon-reorder"></i> <span class="hidden-480">生鲜乳样品信息</span>
				</div>
			</div>
			<div class="portlet-body" id="monitoring_plan_program_tab1">
				<div class="tab-content">
					<div class="tab-pane active">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
						<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
						<input name="sampleCode" type="hidden" value="${barcodeInfoInputPage.sampleCode}"/>
						<input name="nkyFreshMilkEntity.id" type="hidden" value="${barcodeInfoInputPage.nkyFreshMilkEntity.id }"/>
						<input name="nkyFreshMilkEntity.sampleCode" type="hidden" value="${barcodeInfoInputPage.nkyFreshMilkEntity.sampleCode}"/>
						<input id="pre_spcode" name="pre_spcode" type="hidden" value="${pre_spcode}"/>
							<table class="table sample_info_table">
								<c:if test="${detecthedFlg == 0}">
								<tr>
									<td class="title">
										<div class="title-label">
											<span>抽样人员姓名</span>
										</div>
									</td>
									<td class="value" colspan="3">
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
									<td class="value">
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
<!-- 												<select class="m-wrap small" name="agrCode" datatype="*"> -->
<!-- 													<option value="" selected></option> -->
<%-- 													<c:forEach items="${breedList}" var="breed"> --%>
<%-- 														<option value="${breed.agrCode}" <c:if test="${breed.agrCode eq barcodeInfoInputPage.agrCode}">selected</c:if>>${breed.agrName}</option> --%>
<%-- 													</c:forEach> --%>
<!-- 												</select> -->
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
										<select class="m-wrap" style="width:350px!important" name="taskCode" datatype="*">
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
											<span style="color:red;">* </span>
											<span>抽样日期和时间</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="samplingDate" id="samplingDate" class="m-wrap medium date-picker" type="text" value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" datatype="*" nullMsg="请选择！" readonly="readonly" onchange="checkself(this)"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span style="color:red;">* </span>
											<span>抽样场所</span>
										</div>
									</td>
									<td class="value">
										<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="${industryMonitoringLink}" hasLabel="false" defaultVal="${barcodeInfoInputPage.monitoringLink}" extend="{datatype:{value : '*'}}"></t:dictSelect>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>抽样量</span>
										</div>
									</td>
									<td class="value">
										<input name="nkyFreshMilkEntity.samplingCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.samplingCount}"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>抽样基数</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="nkyFreshMilkEntity.samplingBaseCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.samplingBaseCount}"/>
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
								<tr>
									<td class="title">
										<div class="title-label">
											<span>(养殖环节不填)类型</span>
										</div>
									</td>
									<td class="value">
										<t:dictSelect id="nkyFreshMilkEntity.type" field="nkyFreshMilkEntity.type" typeGroupCode="fresh_milk_type" hasLabel="false" defaultVal="${barcodeInfoInputPage.nkyFreshMilkEntity.type}"></t:dictSelect>
									</td>
									<td class="title">
										<div class="title-label">
											<span>备注</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input  type="text" class="m-wrap medium" id="nkyFreshMilkEntity.typeRemark" name="nkyFreshMilkEntity.typeRemark" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.typeRemark}"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>生鲜乳收购许可证</span>
										</div>
									</td>
									<td class="value" colspan="3">
										<div class="value-elems">
											<ul class="line">
												<li class="elemet"><input type="radio" name="nkyFreshMilkEntity.buyLicence" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.buyLicence eq '1'}">checked</c:if>/>有</li>
												<li class="elemet"><span>许可证号</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.licenceNo" name="nkyFreshMilkEntity.licenceNo" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.licenceNo}"/></li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.buyLicence" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.buyLicence eq '0'}">checked</c:if>/>无</li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.licenceRemark" name="nkyFreshMilkEntity.licenceRemark" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.licenceRemark}"/></li>
											</ul>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>生鲜乳准运证</span>
										</div>
									</td>
									<td class="value" colspan="3">
										<div class="value-elems">
											<ul class="line">
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.navicert" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.navicert eq '1'}">checked</c:if>/>有 </li>
												<li class="elemet"><span>准运证号</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.navicertNo" name="nkyFreshMilkEntity.navicertNo" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.navicertNo}"/></li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.navicert" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.navicert eq '0'}">checked</c:if>/>无 </li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.navicertRemark" name="nkyFreshMilkEntity.navicertRemark" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.navicertRemark}"/></li>
											</ul>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>生鲜乳交接单</span>
										</div>
									</td>
									<td class="value" colspan="3">
										<div class="value-elems">
											<ul class="line">
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.deliveryReceitp" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitp eq '1'}">checked</c:if>/>有</li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.deliveryReceitp" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitp eq '0'}">checked</c:if>/>无</li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.deliveryReceitpRemark" name="nkyFreshMilkEntity.deliveryReceitpRemark" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitpRemark}"/></li>
											</ul>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>交奶去向</span>
										</div>
									</td>
										<td class="value" colspan="3">
										<div class="value-elems">
											<input  type="text" class="m-wrap medium" id="nkyFreshMilkEntity.direction" name="nkyFreshMilkEntity.direction" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.direction}"/>
										</div>
									</td>
								</tr>
							</table>
							<table class="table sample_info_table">
								<tr>
									<td class="title" rowspan="5" style="line-height: 180px;">
										<div class="title-label">
											<span>受检单位情况</span>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span style="color:red;">* </span>
											<span>单位名称</span>
										</div>
									</td>
									<td class="value" colspan="4">
										<input id="unitFullcode" name="unitFullcode" type="hidden" value="${barcodeInfoInputPage.unitFullcode }"/>
<!-- 										<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" datatype="*" data-provide="typeahead"  -->
<%-- 											data-source="${nameSource}" data-ids="${codeNameSource}" value="${barcodeInfoInputPage.unitFullname}" onblur="setAutoData()"/> --%>
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
									<td class="value" colspan="2">
										<input  id="unitAddress" name="unitAddress" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.unitAddress}"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>邮编</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" datatype="/^\d{6}?$/" ignore="ignore" value="${barcodeInfoInputPage.zipCode}"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>法定代表人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input id="legalPerson" name="legalPerson" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.legalPerson}"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="nkyFreshMilkEntity.telphone" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.telphone}"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>受检人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input name="nkyFreshMilkEntity.examinee" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.examinee}"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="nkyFreshMilkEntity.telphone2" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.nkyFreshMilkEntity.telphone2}"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>联系人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input id="contact" name="contact" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.contact}"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input id="telphone" name="telphone" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.telphone}"/>
									</td>
								</tr>
								
								<tr>
									<td class="title" rowspan="4" style="vertical-align: middle">
										<div class="title-label">
											<span>抽样单位情况</span>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>单位名称</span>
										</div>
									</td>
									<td class="value" colspan="4">
										<input  name="" type="text" class="m-wrap medium" value="${org.ogrname}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>通讯地址</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input  name="" type="text" class="m-wrap medium" value="${org.address}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>邮编</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input  name="" type="text" class="m-wrap small" value="${org.zipcode}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>联系人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input name="" type="text" class="m-wrap small" value="${org.contacts}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="" type="text" class="m-wrap small" value="${org.contactstel}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>电子邮箱</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input name="" type="text" class="m-wrap small" value="${org.email}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>传真</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="" type="text" class="m-wrap small" value="${org.fax}" readonly="readonly"/>
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
	</div>
</div>
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>