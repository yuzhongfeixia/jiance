<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>
<style>
#sampleFormTable td.row-selected{
	line-height: 37px;
	vertical-align: middle;
	text-align: center;
}
ul.monType-list{
	list-style: none;
	line-height: 20px;
	margin-left: 0px;
	margin-bottom: -5px;
}
.monType-list li.monType-row {
	display: inline-block;
	width: 100%;
}
.monType-row .monType-item{
	line-height: 28px;
	float: left;
	margin-right: 10px;
}
.monType-item .monType-item-title{
	width: 130px;
	float: left;
	display: inline;
	text-align: right;
	margin-right: 10px;
}

#monitoring_plan_program_tab1 .dropdown-menu {
	font-size: 13px;
}
#monitoring_plan_program_tab1 .dropdown-menu li{
	width:206px;
}
.messageColor{
	color:red;
}
.disableButtonColor{
	background-color:#c2c2c2;
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
var isDcodeOk = "";
var isSpCodeOk = "";
var isSamplingAddressNull = "";
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('tr').each(function(i){
		$(':input, select,textArea', this).each(function(){
			var $this = $(this), id = $this.attr('id'),name = $this.attr('name'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					if (id != null) {
						$this.attr("id",id.replace('#index#',i));
					}
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
		});
	});
}
function addSample(){   
	 if (${setBackFlg}){
		 modalTips("无法进行此操作!");
		 return;
	 }
	 var tr =  $("#sampleFormTable_template tr").clone();
	 $("#sampleFormTable").append(tr);
	 resetTrNum('sampleFormTable');
	 var scrollHeight =  $("#sampleFormTable").find("input[type='checkbox']").length * 300;
	 $("#scrollDiv").scrollTop(scrollHeight);
	 $("#saveForm").find("input[type='checkbox']").uniform();
	 
	 if ($("#sampleFormTable").find("input[type='checkbox']").length > 1) {
		 $("#sampleFormTable").find("input[type='checkbox']").prop("disabled", false);
	 }
	 $("#sampleFormTable").find("input[name$='.agrCode']").select2({
		 data:eval("${jsonBreedList}"),
		 formatNoMatches:'无此数据'
	 });
	 setLastDefault();
}

/**
 * 设置最后增加的样品的默认值
 */
function setLastDefault() {
	var lastIndex = $("#sampleFormTable tr").size() - 1;
	var oldLastIndex = lastIndex -1;
	// 设置抽样任务
	var task = $("#sampleFormTable tr:last").find("select[name$='.taskCode']");
	task.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("select[name$='.taskCode']").val());
	
	// 设置抽样地点
	var sampleSource = $("#sampleFormTable tr:last").find("input[name$='.samplingAddress']");
	sampleSource.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.samplingAddress']").val());
	
	// 设置样品来源
	var sampleSource = $("#sampleFormTable tr:last").find("input[name$='.sampleSource']");
	sampleSource.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.sampleSource']").val());
	
	// 设置样品量
	var sampleCount = $("#sampleFormTable tr:last").find("input[name$='.sampleCount']");
	sampleCount.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.sampleCount']").val());
	
	// 设置备注
	var remark = $("#sampleFormTable tr:last").find("textarea[name$='.remark']");
	remark.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("textarea[name$='.remark']").val());
	
	$("#sampleFormTable tr:last").find("input[name$='.dCode']").focus();
}

function delSample(){ 
	 if (${setBackFlg}){
		 modalTips("无法进行此操作!");
		 return;
	 }
	 if (allCheckFlg()) {
		 modalTips("样品至少为1件!");
		 return;
	 }
	
 	 $("#sampleFormTable").find("input:checked").closest("tr").remove();   
     resetTrNum('sampleFormTable'); 
	 var scrollHeight =  $("#sampleFormTable").find("input[type='checkbox']").length * 300;
	 $("#scrollDiv").scrollTop(scrollHeight);	 
	 if ($("#sampleFormTable").find("input[type='checkbox']").length == 1) {
		 $("#sampleFormTable").find("input[type='checkbox']").prop("disabled", true);
	 }
}

var elem = null;
var initIndex = "";
$(document).ready(function(){
	 $("#sampleFormTable").find("input[name$='.agrCode']").select2({
		 data:eval("${jsonBreedList}"),
		 formatNoMatches:'无此数据'
	 });
	
	 if ($("#sampleFormTable").find("input[type='checkbox']").length == 1) {
		 $("#sampleFormTable").find("input[type='checkbox']").prop("disabled", true);
	 }
	 var index = "";
	 $("#sampleFormTable").find("input[name$='.dCode']").each(function(i,element){
		if($(this).val() == "${vdcode}") {
			initIndex = i;
			elem = $(this);
			return false;
		}
	 });

// 	 if (${setBackFlg}){
		 if(${fn:length(routinemonitoringList)} > 1) {

			 $("#sampleFormTable").find("tr").each(function(i,element){
				if (i != initIndex) {
					$(':input, select,textArea', this).each(function(){
						$(this).prop("disabled", true);
					});	
					
					// 制氧编码验证则去掉
					$(":input[name$='.spCode']", this).removeAttr("datatype");
				} 
			 });
 		 }
// 	 }
	 setTimeout("initScroll()", 1000);

		 
	 //window.setInterval("setMessageSpanColor()",100);

});

function initScroll() {
	var baseH = $("#sampleFormTable").find("tr:first").height();
	$("#scrollDiv").scrollTop(initIndex * baseH);
	$("#sampleFormTable").find("tr").each(function(i,element){
			if (i == initIndex) {
				$(this).find("input[name$='.dCode']").focus();
				$(this).find("input[name$='.dCode']").select();
			} 
	});
}

// function setMessageSpanColor() {
// 	$("span.help-inline").each(function(){
// 		if (!$(this).hasClass("messageColor")) {
// 			$(this).addClass("messageColor");
// 		}
// 	});	
// }

	
function allCheckFlg(){
	var flg = true;
	$("#sampleFormTable").find("input[type='checkbox']").each(function(){
		if ($(this).attr("checked") != 'checked'){
			flg = false;
		}
	});
	return flg;
}
function setAclickEvent(data){
	data.thisElem.attr("clickElem",true);
	return true;
}

function callSetId(data){
	var asibling = $("a[clickElem='true']").siblings();
    $(asibling[1]).val(data.params.code);
    $(asibling[2]).focus();
	$(asibling[2]).val(data.params.name);
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

// function doubleWriteChk(data) {
// 	if(data.value=="") {
// 		return;
// 	}
// 	var objarr = new Array();
// 	var objs = document.getElementsByTagName("input");
// 	for (var i=0;i<objs.length;i++) {
	
// 		var id = objs[i].getAttribute("id");

// 		if (id != null && id != data.id && id != "routinemonitoringList[#index#].dCode") {

// 			if (id.substring(id.length-5) == "dCode") {
// 				objarr.push(objs[i]);
// 			}
// 		}

// 	}
// 	if(objarr.length==0){
// 		return;
// 	}	
// 	for (var j=0;j<objarr.length;j++) {
// 		if (objarr[j].value == data.value) {
//  			modalAlert("重复填写的条码,请核实!");
//  			data.value="";
// 			return;
// 		}
// 	}

// }

function doubleWriteChk(data) {
	if(data.value=="") {
		return;
	}
	var objarr = new Array();
	//var objs = document.getElementsByTagName("input");
	$("#sampleFormTable").find("input[name$='.dCode']").each(function(i,element){
		var $name = $(this).attr("name");

		if ($name != data.name) {
			objarr.push(element);
		}
	});

	if(objarr.length==0){
		return;
	}


	for (var j=0;j<objarr.length;j++) {
		if (objarr[j].value == data.value) {
 			modalTips("重复填写的条码,请核实!");
 			data.focus();
 			data.select();
 			isDcodeOk = "1";
			return;
		}
		isDcodeOk = "0";
	}

}

function doubleWriteChk1(data) {
	if (isSamplingAddressNull == "1") {
		data.value="";
	}
	if(data.value=="") {
		return;
	}
	var objarr = new Array();
	$("#sampleFormTable").find("input[name$='.spCode']").each(function(i,element){
		var $name = $(this).attr("name");

		if ($name != data.name) {
			objarr.push(element);
		}
	});

	if(objarr.length==0){
		return;
	}

	for (var j=0;j<objarr.length;j++) {
		if (objarr[j].value == data.value) {
 			modalTips("重复填写的制样编码,请核实!");
 			data.focus();
 			data.select();
 			isSpCodeOk = "1";
			return;
		}
		isSpCodeOk = "0";
	}

}

function setAutoData(){
// 	var unitFullcodePro = $("#monitoring_plan_program_tab1").find(".active");
//  	if (unitFullcodePro != undefined) {
//  		$("#unitFullcode").val(unitFullcodePro.attr("data-id"));
//  	}
 	
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
 	} else {
 		$("#unitFullcode").val("");
 		$("#unitAddress").val("");
 		$("#legalPerson").val("");
 		$("#zipCode").val("");
 		$("#telphone").val(""); 	
 	}
}

function checkTaskCount() {
// 	if($("[name='id']").val() != '') {
// 		var reqTaskCode = "${routinemonitoringList[0].taskCode}";
// 		// 编辑时，若任务没有改变
// 		if (reqTaskCode == $("[name='routinemonitoringList[0].taskCode']").val()) {
// 			return;
// 		}
// 	}

	if (isDcodeOk == "1") {
		modalTips("重复填写的条码,请核实!");
		return false;
	}
	
	if (isSpCodeOk == "1") {
		modalTips("重复填写的制样编码,请核实!");
		return false;
	}
	
	//var jsonDatas = {"projectCode" : $("#projectCode").val()};
	var checkResult = true;
	var jsonDatas = {};
	$("[name$='.taskCode']").each(function(i, element){
		var taskCode = $(element).val();
		if(jsonDatas[taskCode] != null){
			jsonDatas[taskCode] = jsonDatas[taskCode]+1;
		}else{
			jsonDatas[taskCode] = 1;	
		}
	});
	if($("[name='id']").val() != '') {
		jsonDatas['isEdit'] = '1';
	} else {
		jsonDatas['isEdit'] = '0';
	}
	
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
// 				$("button[class='btn popenter']").css({"background-color":""});
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
	var oldAgrCode = elem.closest("tr").find("input[name='oldAgrCode']").val();
	var breedsize = "${fn:length(breedList)}";
// 	if (breedsize == 0) {
// 		agrCode = elem.closest("tr").find("input[name$='.agrCode']").val()
// 	} else {
// 		agrCode = elem.closest("tr").find("select[name$='.agrCode']").val()
// 	}
	agrCode = elem.closest("tr").find("input[name$='.agrCode']").val();
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

function samplingAddressChk(data) {
	if ($("#countyCode").val() == "" || $("#countyCode").val() == null) {
		modalTips("请先选择抽样地区！");
		isSamplingAddressNull = "1";
	} else {
		isSamplingAddressNull = "0";
	}
}


function setSpCodeAjax(data) {
	$("#sampleFormTable").find("input[name$='.spCode']").each(function(i,element){
		$(this).attr("ajaxurl", "samplingInfoController.do?checkSpCode&projectCode=${barcodeInfoInputPage.projectCode }&countyCode="+data.value);
	});
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
					<i class="icon-reorder"></i> <span class="hidden-480">例行监测样品信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
							<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
							<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
							<input id="pre_spcode" name="pre_spcode" type="hidden" value="${pre_spcode}"/>
<%-- 							<input name="routinemonitoringList[0].id" type="hidden" value="${routinemonitoringList[0].id }"/> --%>
<%-- 							<input name="routinemonitoringList[0].sampleCode" type="hidden" value="${routinemonitoringList[0].sampleCode}"/> --%>
							<table class="table sample_info_table" id="sample_info_table">
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
								<tr>
									<td class="title">
										<div class="title-label">
											<span style="color:red;">* </span>
											<span>受检单位</span>
										</div>
									</td>
									<td class="value" colspan="5">
										<div class="value-elems">
										    <input id="unitFullcode" name="unitFullcode" type="hidden" value="${barcodeInfoInputPage.unitFullcode }"/>
<%-- 											<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" datatype="*" data-provide="typeahead" data-source="${nameSource}" data-ids="${codeNameSource}"  --%>
<%-- 												value="${barcodeInfoInputPage.unitFullname}" onblur="setAutoData()"/> --%>
											<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" datatype="*" value="${barcodeInfoInputPage.unitFullname}" onkeyup="autoMatch()" onblur="setAutoData()"/>
											<div id="showmenu" style="min-width:320px;background-color: white;position:absolute;z-index:10999;border:1px solid;color: black;display:none" ></div>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>通讯地址</span>
										</div>
									</td>
									<td class="value" colspan="5">
										<div class="value-elems">
											<input id="unitAddress" name="unitAddress" type="text" class="m-wrap"  datatype="*" ignore="ignore" value="${barcodeInfoInputPage.unitAddress}"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>法定代表人或负责人</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input id="legalPerson" name="legalPerson" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.legalPerson}"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>邮政编码</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input id="zipCode" name="zipCode" type="text" class="m-wrap small" ignore="ignore" datatype="/^\d{6}?$/" value="${barcodeInfoInputPage.zipCode}"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input id="telphone" name="telphone" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.telphone}"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>任务来源</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="routinemonitoringList[0].taskSource" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${routinemonitoringList[0].taskSource}"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>执行标准</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="routinemonitoringList[0].execStanderd"  type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${routinemonitoringList[0].execStanderd}"/>
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
											<input id="samplingDate" class="m-wrap small date-picker" name="samplingDate" type="text" 
												value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" datatype="*" nullMsg="请选择！" readonly="readonly" onchange="checkself(this)"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span style="color:red;">* </span>
											<span>监测环节</span>
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
											<span>抽样地点</span>
										</div>
									</td>
									<td class="value" colspan="3">
										<div class="value-elems">
											<ul id="areasDiv" class="line">
												<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},datatype:{value : '*'}}"></t:dictSelect></li>
												<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},datatype:{value : '*'},onchange:{value : 'setSpCodeAjax(this)'}}"></t:dictSelect></li>
											</ul>
										</div>
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td class="title"> -->
<!-- 										<div class="title-label"> -->
<!-- 											<span>抽样详细地点</span> -->
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 									<td class="value" colspan="5"> -->
<!-- 										<div class="value-elems"> -->
<%-- 											<input name="samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.samplingAddress}"/> --%>
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 								</tr> -->
							</table>
								<div>
									<a id="addSampleBtn" style="color: #076148;!important" title="添加" onclick="addSample()"><i class="icon-plus"></i>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a id="delSampleBtn" style="color: #076148;!important" title="删除" onclick="delSample()"><i class="icon-minus"></i>删除</a>
								</div>
							
							<div id="scrollDiv" style="height:330px;overflow-y:scroll;">
								<table class="table table-bordered" id="sampleFormTable" >
									<c:if test="${fn:length(routinemonitoringList)  <= 0 }">
									<tr>
									<td hidden="">
									 <input name="routinemonitoringList[0].id" type="hidden" value="${routinemonitoringList[0].id }"/>
									 <input name="routinemonitoringList[0].sampleCode" type="hidden" value="${routinemonitoringList[0].sampleCode}"/>
									 <input name="routinemonitoringList[0].samplingMonadId" type="hidden" value="${routinemonitoringList[0].samplingMonadId}"/>
									  <input name="oldAgrCode" type="hidden" value="${routinemonitoringList[0].agrCode}"/>
									</td>
										<td style="vertical-align: middle;"><input type="checkbox" name="ck"></td>
										<td>
											<ul class="monType-list">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>条码:</span>
														</div>
														<c:if test="${barcodeInfoInputPage.id == null || poVal.dCode == null}">
															<input id="routinemonitoringList[0].dCode" name="routinemonitoringList[0].dCode" type="text" placeholder="" class="m-wrap" value="${routinemonitoringList[0].dCode}" maxlength="13"
														    	 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode&projectCode=${barcodeInfoInputPage.projectCode }" onblur="doubleWriteChk(this)">
														</c:if>
														<c:if test="${barcodeInfoInputPage.id != null && poVal.dCode != null}">
															<input name="routinemonitoringList[0].dCode" type="text" placeholder="" class="m-wrap" value="${routinemonitoringList[0].dCode}" readonly="readonly">
														</c:if>
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>抽样任务:</span>
														</div>
														<select class="m-wrap" style="width:350px!important" name="routinemonitoringList[0].taskCode" datatype="*">
															<option value="" selected></option>
															<c:forEach items="${monitoringTaskList}" var="task">
																<option value="${task.taskcode}" <c:if test="${task.taskcode eq routinemonitoringList[0].taskCode}">selected</c:if>>${task.taskname}</option>
															</c:forEach>
														</select>
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span>
															<span>样品名称</span>
														</div>
														<c:if test="${fn:length(breedList)  == 0 }">
															<input name="routinemonitoringList[0].agrCode" type="hidden" value="${routinemonitoringList[0].agrCode}"/>
															<input type="text" class="m-wrap" value="${sampleName}" datatype="*" nullMsg="请选择！" readonly="readonly">
															<a id="selSampleBtn" class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a>
														</c:if>
														<c:if test="${fn:length(breedList)  > 0 }">
<!-- 															<select class="m-wrap small" name="routinemonitoringList[0].agrCode" datatype="*"> -->
<!-- 																<option value="" selected></option> -->
<%-- 																<c:forEach items="${breedList}" var="breed"> --%>
<%-- 																	<option value="${breed.agrCode}" <c:if test="${breed.agrCode eq routinemonitoringList[0].agrCode}">selected</c:if>>${breed.agrName}</option> --%>
<%-- 																</c:forEach> --%>
<!-- 															</select> -->
															<input class="m-wrap small" type="hidden" name="routinemonitoringList[0].agrCode" style="width: 129px;" tabindex="-1" title="" value="" datatype="*">
														</c:if>
														<c:if test="${not empty barcodeInfoInputPage.samplePath }">
															<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${barcodeInfoInputPage.samplePath}')">样品图片</a>
														</c:if>
													</div>
												</li>
												<c:if test="${detecthedFlg == 0}">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>抽样地点:</span>
														</div>
														<input name="routinemonitoringList[0].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${routinemonitoringList[0].samplingAddress}"/>
													</div>
												</li>
												</c:if>
												<c:if test="${detecthedFlg == 1}">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span>
															<span>制样编码:</span>
														</div>
														<input name="routinemonitoringList[0].spCode"  type="text" class="m-wrap" datatype="n" value="${routinemonitoringList[0].spCode}" 
															 onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>抽样地点:</span>
														</div>
														<input name="routinemonitoringList[0].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${routinemonitoringList[0].samplingAddress}"/>
													</div>
												</li>
												</c:if>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品来源:</span>
														</div>
														<input name="routinemonitoringList[0].sampleSource" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${routinemonitoringList[0].sampleSource}">
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品量(n/N):</span>
														</div>
														<input name="routinemonitoringList[0].sampleCount" type="text" placeholder="" class="m-wrap"  value="${routinemonitoringList[0].sampleCount}">
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>备注:</span>
														</div>
														<textarea name="routinemonitoringList[0].remark" class="span6 m-wrap" datatype="*" ignore="ignore"  rows="1" style="width:500px;">${routinemonitoringList[0].remark}</textarea>
													</div>
												</li>
											</ul>
										</td>
									</tr>
									</c:if>
									<c:if test="${fn:length(routinemonitoringList)  > 0 }">
									<c:forEach items="${routinemonitoringList}" var="poVal" varStatus="stuts">
									<tr>
									<td hidden="">
									<input name="routinemonitoringList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
									<input name="routinemonitoringList[${stuts.index }].sampleCode" type="hidden" value="${poVal.sampleCode}"/>
									<input name="routinemonitoringList[${stuts.index }].samplingMonadId" type="hidden" value="${poVal.samplingMonadId}"/>
									 <input name="oldAgrCode" type="hidden" value="${poVal.agrCode}"/>
									</td>
										<td style="vertical-align: middle;"><input type="checkbox" name="ck"></td>
										<td>
											<ul class="monType-list">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>条码:</span>
														</div>
														<c:if test="${barcodeInfoInputPage.id == null || poVal.dCode == null}">
															<input id="routinemonitoringList[${stuts.index }].dCode" name="routinemonitoringList[${stuts.index }].dCode" type="text" placeholder="" class="m-wrap" value="${poVal.dCode}" maxlength="13"
														    	 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode" onblur="doubleWriteChk(this)">
														</c:if>
														<c:if test="${barcodeInfoInputPage.id != null && poVal.dCode != null}">
															<input name="routinemonitoringList[${stuts.index }].dCode" type="text" placeholder="" class="m-wrap" value="${poVal.dCode}" readonly="readonly">
														</c:if>
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>抽样任务:</span>
														</div>
														<select class="m-wrap" style="width:350px!important" name="routinemonitoringList[${stuts.index }].taskCode" datatype="*">
															<option value="" selected></option>
															<c:forEach items="${monitoringTaskList}" var="task">
																<option value="${task.taskcode}" <c:if test="${task.taskcode eq poVal.taskCode}">selected</c:if>>${task.taskname}</option>
															</c:forEach>
														</select>
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span>
															<span>样品名称</span>
														</div>
														<c:if test="${fn:length(breedList)  == 0 }">
															<input name="routinemonitoringList[${stuts.index }].agrCode" type="hidden" value="${poVal.agrCode}"/>
															<input type="text" class="m-wrap" value="${poVal.sampleName}" datatype="*" nullMsg="请选择！" readonly="readonly">
															<a id="selSampleBtn" class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a>
														</c:if>
														<c:if test="${fn:length(breedList)  > 0 }">
<%-- 															<select class="m-wrap small" name="routinemonitoringList[${stuts.index }].agrCode" datatype="*"> --%>
<!-- 																<option value="" selected></option> -->
<%-- 																<c:forEach items="${breedList}" var="breed"> --%>
<%-- 																	<option value="${breed.agrCode}" <c:if test="${breed.agrCode eq poVal.agrCode}">selected</c:if>>${breed.agrName}</option> --%>
<%-- 																</c:forEach> --%>
<!-- 															</select> -->
															<input type="hidden" name="routinemonitoringList[${stuts.index }].agrCode" style="width: 129px;" tabindex="-1" title="" value="${poVal.agrCode}" datatype="*">
														</c:if>
														<c:if test="${not empty poVal.samplePath }">
															<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${poVal.samplePath}')">样品图片</a>
														</c:if>
													</div>
												</li>
												<c:if test="${detecthedFlg == 0}">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>抽样地点:</span>
														</div>
														<input name="routinemonitoringList[${stuts.index }].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.samplingAddress}"/>
													</div>
												</li>
												</c:if>
												<c:if test="${detecthedFlg == 1}">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span>
															<span>制样编码:</span>
														</div>
														<input name="routinemonitoringList[${stuts.index }].spCode"  type="text" class="m-wrap" datatype="n" value="${poVal.spCode}" onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>抽样地点:</span>
														</div>
														<input name="routinemonitoringList[${stuts.index }].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.samplingAddress}"/>
													</div>
												</li>
												</c:if>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品来源:</span>
														</div>
														<input name="routinemonitoringList[${stuts.index }].sampleSource" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.sampleSource}">
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品量(n/N):</span>
														</div>
														<input name="routinemonitoringList[${stuts.index }].sampleCount" type="text" placeholder="" class="m-wrap" value="${poVal.sampleCount}">
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>备注:</span>
														</div>
														<textarea name="routinemonitoringList[${stuts.index }].remark" class="span6 m-wrap" datatype="*" ignore="ignore"  rows="1" style="width:500px;">${poVal.remark}</textarea>
													</div>
												</li>
											</ul>
										</td>
									</tr>
									</c:forEach>
									</c:if>
								</table>
							</div>
							
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
							
							<!-- 添加 项目 模版 -->
						<table class="table table-bordered" id="sampleFormTable_template" style="display:none">
							<tr>
								<td style="vertical-align: middle;"><input type="checkbox" name="ck"/></td>
								<td>
									<ul class="monType-list">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>条码:</span>
												</div>
<%-- 												<c:if test="${barcodeInfoInputPage.id == null}"> --%>
													<input id="routinemonitoringList[#index#].dCode" name="routinemonitoringList[#index#].dCode" type="text" class="m-wrap" value="" maxlength="13" 
														 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode" onblur="doubleWriteChk(this)">
<%-- 												</c:if> --%>
<%-- 												<c:if test="${barcodeInfoInputPage.id != null}"> --%>
<!-- 													<input id="routinemonitoringList[#index#].dCode" name="routinemonitoringList[#index#].dCode" type="text" class="m-wrap" value="" readonly="readonly"> -->
<%-- 												</c:if> --%>
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>抽样任务:</span>
												</div>
												<select class="m-wrap" style="width:350px!important" name="routinemonitoringList[#index#].taskCode" datatype="*">
													<option value="" selected></option>
													<c:forEach items="${monitoringTaskList}" var="task">
														<option value="${task.taskcode}">${task.taskname}</option>
													</c:forEach>
												</select>
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>样品名称:</span>
												</div>
												<c:if test="${fn:length(breedList)  == 0 }">
													<input name="routinemonitoringList[#index#].agrCode" type="hidden" value=""/>
													<input type="text" class="m-wrap" value="" datatype="*" nullMsg="请选择！" readonly="readonly">
													<a class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a>
												</c:if>
												<c:if test="${fn:length(breedList)  > 0 }">
<!-- 													<select class="m-wrap small" name="routinemonitoringList[#index#].agrCode" datatype="*"> -->
<!-- 														<option value="" selected></option> -->
<%-- 														<c:forEach items="${breedList}" var="breed"> --%>
<%-- 															<option value="${breed.agrCode}">${breed.agrName}</option> --%>
<%-- 														</c:forEach> --%>
<!-- 													</select> -->
													<input type="hidden" name="routinemonitoringList[#index#].agrCode" style="width: 129px;" tabindex="-1" title="" value="" datatype="*">
												</c:if>
											</div>
										</li>
										<c:if test="${detecthedFlg == 0}">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>抽样地点:</span>
												</div>
												<input name="routinemonitoringList[#index#].samplingAddress"  type="text" class="m-wrap" value=""/>
											</div>
										</li>
										</c:if>
										<c:if test="${detecthedFlg == 1}">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span>
													<span>制样编码:</span>
												</div>
												<input name="routinemonitoringList[#index#].spCode"  type="text" class="m-wrap" datatype="n" value="" onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>抽样地点:</span>
												</div>
												<input name="routinemonitoringList[#index#].samplingAddress"  type="text" class="m-wrap" value=""/>
											</div>
										</li>
										</c:if>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>样品来源:</span>
												</div>
												<input name="routinemonitoringList[#index#].sampleSource" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="">
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>样品量(n/N):</span>
												</div>
												<input name="routinemonitoringList[#index#].sampleCount" type="text" placeholder="" class="m-wrap" value="">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>备注:</span>
												</div>
												<textarea name="routinemonitoringList[#index#].remark" class="span6 m-wrap" rows="1" datatype="*" ignore="ignore" style="width:500px;"></textarea>
											</div>
										</li>
									</ul>
								</td>
				
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>
