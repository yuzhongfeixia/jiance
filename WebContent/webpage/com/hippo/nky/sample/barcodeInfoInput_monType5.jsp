<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	var samplingAddress = $("#sampleFormTable tr:last").find("input[name$='.samplingAddress']");
	samplingAddress.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.samplingAddress']").val());
	
	// 设置畜主/货主
	var cargoOwner = $("#sampleFormTable tr:last").find("input[name$='.cargoOwner']");
	cargoOwner.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.cargoOwner']").val());
	
	// 设置动物产地/来源
	var animalOrigin = $("#sampleFormTable tr:last").find("input[name$='.animalOrigin']");
	animalOrigin.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.animalOrigin']").val());
	
	// 设置检疫证号
	var cardNumber = $("#sampleFormTable tr:last").find("input[name$='.cardNumber']");
	cardNumber.val($("#sampleFormTable tr:eq("+oldLastIndex+")").find("input[name$='.cardNumber']").val());
	
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

var initIndex = "";
$(document).ready(function(){
	 $("input[name='agrCode']").select2({
		 data:eval("${jsonBreedList}"),
		 formatNoMatches:'无此数据'
	 });
	 if ("${detecthedFlg}" == "0") {
		 $("#scrollDiv").css({"height":"290"});
	 }
	 
	 if ($("#sampleFormTable").find("input[type='checkbox']").length == 1) {
		 $("#sampleFormTable").find("input[type='checkbox']").prop("disabled", true);
	 }
// 	 if (${setBackFlg}){
		 if(${fn:length(livestockEntityList)} > 1) {
 			 var index = "";
			 $("#sampleFormTable").find("input[name$='.dCode']").each(function(i,element){
				if($(this).val() == "${vdcode}") {
					initIndex = i;
					return false;
				}
			 });
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
 		//$("#legalPerson").val(monitoringSiteInfo.legalPerson);
 		$("#zipCode").val(monitoringSiteInfo.zipcode);
 		$("#telphone").val(monitoringSiteInfo.contact);
 		//$("#contact").val(monitoringSiteInfo.contactPerson);
 		//$("#fax").val(monitoringSiteInfo.fax);
 	} else {
 		$("#unitFullcode").val("");
 		$("#unitAddress").val("");
 		$("#zipCode").val("");
 		$("#telphone").val("");
 	}
}

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
 			//modalAlert("重复填写的条码,请核实!");
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

/**
 * 退回时需验证样品名称是否修改
 */
function checkTaskCount() {
// 	if($("[name='id']").val() != '') {
// 		var reqTaskCode = "${livestockEntityList[0].taskCode}";
// 		// 编辑时，若任务没有改变
// 		if (reqTaskCode == $("[name='livestockEntityList[0].taskCode']").val()) {
// 			return;
// 		}
// 	}
	//var jsonDatas = {"projectCode" : $("#projectCode").val()};
	if (isDcodeOk == "1") {
		modalTips("重复填写的条码,请核实!");
		return false;
	}
	
	if (isSpCodeOk == "1") {
		modalTips("重复填写的制样编码,请核实!");
		return false;
	}
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
					<i class="icon-reorder"></i> <span class="hidden-480">畜禽样品信息</span>
				</div>
			</div>
			<div class="portlet-body" id="monitoring_plan_program_tab1">
				<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
					<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
					<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
					<input id="pre_spcode" name="pre_spcode" type="hidden" value="${pre_spcode}"/>
<%-- 					<input name="livestockEntityList[0].id" type="hidden" value="${livestockEntityList[0].id }"/> --%>
<%-- 					<input name="livestockEntityList[0].sampleCode" type="hidden" value="${livestockEntityList[0].sampleCode}"/> --%>
					<table class="table sample_info_table">
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
									<span>样品名称</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<c:if test="${fn:length(breedList)  == 0 }">
										<input name="agrCode" type="hidden" value="${barcodeInfoInputPage.agrCode}"/>
										<input type="text" class="m-wrap small" value="${sampleName}" datatype="*" nullMsg="请选择！" readonly="readonly">
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
<%-- 									<c:if test="${not empty barcodeInfoInputPage.samplePath }"> --%>
<%-- 										<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${barcodeInfoInputPage.samplePath}')">样品图片</a> --%>
<%-- 									</c:if> --%>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样依据</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="livestockEntityList[0].taskSource" type="text" class="m-wrap small" datatype="*" ignore="ignore" value="${livestockEntityList[0].taskSource}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>商标</span>
								</div>
							</td>
							<td class="value">
								<input  name="livestockEntityList[0].tradeMark" type="text" class="m-wrap small"  datatype="*" ignore="ignore" value="${livestockEntityList[0].tradeMark}"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样基数</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="livestockEntityList[0].samplingBaseCount" type="text" class="m-wrap small" value="${livestockEntityList[0].samplingBaseCount}"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>抽样数量</span>
								</div>
							</td>
							<td class="value">
								<input  name="livestockEntityList[0].samplingCount" type="text" class="m-wrap small" value="${livestockEntityList[0].samplingCount}"/>
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
									<span style="color:red;">* </span><span>抽样日期</span>
								</div>
							</td>
							<td class="value">
								<input class="m-wrap small date-picker" id="samplingDate" type="text" name="samplingDate" value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" datatype="*" nullMsg="请选择！" readonly="readonly" onchange="checkself(this)"/>
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
									<span>保存情况</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<c:if test="${barcodeInfoInputPage.id != null}">
									<input  name="livestockEntityList[0].saveSaveSituation" type="text" class="m-wrap small"  datatype="*" ignore="ignore" value="${livestockEntityList[0].saveSaveSituation}"/>
									</c:if>
									<c:if test="${barcodeInfoInputPage.id == null}">
									<input  name="livestockEntityList[0].saveSaveSituation" type="text" class="m-wrap small"  datatype="*" ignore="ignore" value="冷冻(冷藏)"/>
									</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span>
									<span>被抽样单位</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input id="unitFullcode" name="unitFullcode" type="hidden" value="${barcodeInfoInputPage.unitFullcode }"/>
<!-- 								<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" datatype="*" data-provide="typeahead"  -->
<%-- 									data-source="${nameSource}" data-ids="${codeNameSource}" value="${barcodeInfoInputPage.unitFullname}" onblur="setAutoData()"/> --%>
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
								<input  id="unitAddress" name="unitAddress" type="text" class="m-wrap medium" datatype="*" ignore="ignore" value="${barcodeInfoInputPage.unitAddress}" />
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系电话</span>
								</div>
							</td>
							<td class="value">
								<input  id="telphone" name="telphone" type="text" class="m-wrap"  datatype="*" ignore="ignore" value="${barcodeInfoInputPage.telphone}" />
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮政编码</span>
								</div>
							</td>
							<td class="value">
								<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" datatype="/^\d{6}?$/" ignore="ignore" value="${barcodeInfoInputPage.zipCode}"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样方式</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<ul class="line radio">
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].samplingMode" value="0" <c:if test="${livestockEntityList[0].samplingMode eq '0'}">checked</c:if>/>总体随机</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].samplingMode" value="1" <c:if test="${livestockEntityList[0].samplingMode eq '1'}">checked</c:if>/>其他</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>样品包装</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul class="line radio">
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].pack" value="0" <c:if test="${livestockEntityList[0].pack eq '0'}">checked</c:if>/>完好</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].pack" value="1" <c:if test="${livestockEntityList[0].pack eq '1'}">checked</c:if>/>不完好</li>
									</ul>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>签封标志</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul class="line radio">
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].signFlg" value="0" <c:if test="${livestockEntityList[0].signFlg eq '0'}">checked</c:if>/>完好</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].signFlg" value="1" <c:if test="${livestockEntityList[0].signFlg eq '1'}">checked</c:if>/>不完好</li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
					
					<div>
						<a id="addSampleBtn" style="color: #076148;!important" title="添加" onclick="addSample()"><i class="icon-plus"></i>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="delSampleBtn" style="color: #076148;!important" title="删除" onclick="delSample()"><i class="icon-minus"></i>删除</a>
					</div>
					
				
					<div id="scrollDiv" style="height:330px;overflow-y:scroll;">
						<table class="table table-bordered" id="sampleFormTable" >
						<c:if test="${fn:length(livestockEntityList)  <= 0 }">
							<tr>
								<td hidden="">
								 <input name="livestockEntityList[0].id" type="hidden" value="${livestockEntityList[0].id }"/>
								 <input name="livestockEntityList[0].sampleCode" type="hidden" value="${livestockEntityList[0].sampleCode}"/>
								 <input name="livestockEntityList[0].samplingMonadId" type="hidden" value="${livestockEntityList[0].samplingMonadId}"/>
								</td>
								<td style="vertical-align: middle;"><input type="checkbox" name="ck"></td>
								<td>
									<ul class="monType-list">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>条码:</span>
												</div>
												<c:if test="${barcodeInfoInputPage.id == null}">
													<input id="livestockEntityList[0].dCode" name="livestockEntityList[0].dCode" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].dCode}" maxlength="13"
												    	 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode&projectCode=${barcodeInfoInputPage.projectCode }" onblur="doubleWriteChk(this)">
												</c:if>
												<c:if test="${barcodeInfoInputPage.id != null}">
													<input name="livestockEntityList[0].dCode" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].dCode}" readonly="readonly">
												</c:if>
												<c:if test="${not empty poVal.samplePath }">
													<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${barcodeInfoInputPage.samplePath}')">样品图片</a>
												</c:if>
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>抽样任务:</span>
												</div>
												<select class="m-wrap" style="width:350px!important" name="livestockEntityList[0].taskCode" datatype="*">
													<option value="" selected></option>
													<c:forEach items="${monitoringTaskList}" var="task">
														<option value="${task.taskcode}" <c:if test="${task.taskcode eq livestockEntityList[0].taskCode}">selected</c:if>>${task.taskname}</option>
													</c:forEach>
												</select>
											</div>
										</li>
										<c:if test="${detecthedFlg == 1}">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span>
													<span>制样编码:</span>
												</div>
												<input name="livestockEntityList[0].spCode"  type="text" class="m-wrap" datatype="n" value="${livestockEntityList[0].spCode}" onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
											</div>
										</li>
										</c:if>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>抽样地点:</span>
												</div>
												<input name="livestockEntityList[0].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${livestockEntityList[0].samplingAddress}"/>
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>畜主/货主:</span>
												</div>
												<input name="livestockEntityList[0].cargoOwner" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${livestockEntityList[0].cargoOwner}">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>动物产地/来源:</span>
												</div>
												<input name="livestockEntityList[0].animalOrigin" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${livestockEntityList[0].animalOrigin}">
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>检疫证号:</span>
												</div>
												<input name="livestockEntityList[0].cardNumber" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${livestockEntityList[0].cardNumber}">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>备注:</span>
												</div>
												<textarea name="livestockEntityList[0].remark" class="span6 m-wrap" datatype="*" ignore="ignore"  rows="1" style="width:500px;">${livestockEntityList[0].remark}</textarea>
											</div>
										</li>
									</ul>
								</td>
							</tr>
							</c:if>
							<c:if test="${fn:length(livestockEntityList)  > 0 }">
							<c:forEach items="${livestockEntityList}" var="poVal" varStatus="stuts">
							<tr>
								<td hidden="">
									<input name="livestockEntityList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
									<input name="livestockEntityList[${stuts.index }].sampleCode" type="hidden" value="${poVal.sampleCode}"/>
									<input name="livestockEntityList[${stuts.index }].samplingMonadId" type="hidden" value="${poVal.samplingMonadId}"/>
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
													<input id="livestockEntityList[${stuts.index }].dCode" name="livestockEntityList[${stuts.index }].dCode" type="text" placeholder="" class="m-wrap" value="${poVal.dCode}" maxlength="13"
												    	 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode" onblur="doubleWriteChk(this)">
												</c:if>
												<c:if test="${barcodeInfoInputPage.id != null && poVal.dCode != null}">
													<input name="livestockEntityList[${stuts.index }].dCode" type="text" placeholder="" class="m-wrap" value="${poVal.dCode}" readonly="readonly">
												</c:if>
												<c:if test="${not empty poVal.samplePath }">
													<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${poVal.samplePath}')">样品图片</a>
												</c:if>
											</div>
										</li>
										<c:if test="${detecthedFlg == 1}">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span>
													<span>制样编码:</span>
												</div>
												<input name="livestockEntityList[${stuts.index}].spCode"  type="text" class="m-wrap" datatype="n" value="${poVal.spCode}" onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
											</div>
										</li>
										</c:if>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>抽样任务:</span>
												</div>
												<select class="m-wrap" style="width:350px!important" name="livestockEntityList[${stuts.index }].taskCode" datatype="*">
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
													<span>抽样地点:</span>
												</div>
												<input name="livestockEntityList[${stuts.index }].samplingAddress"  type="text" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.samplingAddress}"/>
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>畜主/货主:</span>
												</div>
												<input name="livestockEntityList[${stuts.index }].cargoOwner" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.cargoOwner}">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>动物产地/来源:</span>
												</div>
												<input name="livestockEntityList[${stuts.index }].animalOrigin" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.animalOrigin}">
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>检疫证号:</span>
												</div>
												<input name="livestockEntityList[${stuts.index }].cardNumber" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="${poVal.cardNumber}">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>备注:</span>
												</div>
												<textarea name="livestockEntityList[${stuts.index }].remark" class="span6 m-wrap" datatype="*" ignore="ignore"  rows="1" style="width:500px;">${poVal.remark}</textarea>
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
					    
<%-- 						<button type="button" class="btn popenter" href="samplingInfoController.do?save&vdcode=${vdcode}" action-mode="ajax" action-event="click" action-operation="popsave"  --%>
<!-- 						 action-form="#saveForm" action-before="checkTaskCount()" action-after="refresh_SamplingSetBack2">确认提交</button> -->
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
<%-- 										<c:if test="${barcodeInfoInputPage.id == null}"> --%>
											<input id="livestockEntityList[#index#].dCode" name="livestockEntityList[#index#].dCode" type="text" class="m-wrap" value=""  maxlength="13"
												 datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode" onblur="doubleWriteChk(this)">
<%-- 										</c:if> --%>
<%-- 										<c:if test="${barcodeInfoInputPage.id != null}"> --%>
<!-- 											<input id="livestockEntityList[#index#].dCode" name="livestockEntityList[#index#].dCode" type="text" class="m-wrap" value="" readonly="readonly"> -->
<%-- 										</c:if> --%>
									</div>
								</li>
								<c:if test="${detecthedFlg == 1}">
								<li class="monType-row">
									<div class="monType-item">
										<div class="monType-item-title">
											<span style="color:red;">* </span>
											<span>制样编码:</span>
										</div>
										<input name="livestockEntityList[#index#].spCode"  type="text" class="m-wrap" datatype="n" value="" onfocus="samplingAddressChk(this);" onblur="doubleWriteChk1(this)"/>
									</div>
								</li>
								</c:if>
								<li class="monType-row">
									<div class="monType-item">
										<div class="monType-item-title">
											<span style="color:red;">* </span><span>抽样任务:</span>
										</div>
										<select class="m-wrap" style="width:350px!important" name="livestockEntityList[#index#].taskCode" datatype="*">
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
											<span>抽样地点:</span>
										</div>
										<input name="livestockEntityList[#index#].samplingAddress"  type="text" class="m-wrap" value=""/>
									</div>
									<div class="monType-item">
										<div class="monType-item-title">
											<span>畜主/货主:</span>
										</div>
										<input name="livestockEntityList[#index#].cargoOwner" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="">
									</div>
								</li>
	
								<li class="monType-row">
									<div class="monType-item">
										<div class="monType-item-title">
											<span>动物产地/来源:</span>
										</div>
										<input name="livestockEntityList[#index#].animalOrigin" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="">
									</div>
									<div class="monType-item">
										<div class="monType-item-title">
											<span>检疫证号:</span>
										</div>
										<input name="livestockEntityList[#index#].cardNumber" type="text" placeholder="" class="m-wrap" datatype="*" ignore="ignore" value="">
									</div>
								</li>
								<li class="monType-row">
									<div class="monType-item">
										<div class="monType-item-title">
											<span>备注:</span>
										</div>
										<textarea name="livestockEntityList[#index#].remark" class="span6 m-wrap" rows="1" datatype="*" ignore="ignore" style="width:500px;"></textarea>
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
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>