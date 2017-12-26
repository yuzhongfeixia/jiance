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

</script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">普查样品信息</span>
				</div>
			</div>
			<div class="portlet-body" id="monitoring_plan_program_tab1">
				<form action="#" class="form-horizontal" name="saveForm" id="saveForm">
				<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
				<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode}"/>
				<input name="generalcheckEntity.id" type="hidden" value="${barcodeInfoInputPage.generalcheckEntity.id }"/>
				<input name="generalcheckEntity.sampleCode" type="hidden" value="${barcodeInfoInputPage.generalcheckEntity.sampleCode}"/>
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
									<input id="samplingPersons" name="samplingPersons" type="text" class="m-wrap" value="${barcodeInfoInputPage.samplingPersons}" readonly="readonly"/>
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
									<input id="samplingPersons" name="samplingPersons" type="text" class="m-wrap" value="${barcodeInfoInputPage.samplingPersons}" readonly="readonly"/>
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
									<input id="spCode" name="spCode" type="text" class="m-wrap" value="${barcodeInfoInputPage.show_spcode}" readonly="readonly"/>
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
									<input name="agrCode" type="hidden" value="${barcodeInfoInputPage.agrCode}"/>
									<input type="text" placeholder="" class="m-wrap small" value="${sampleName}" readonly="readonly">
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
								<input name="dCode" type="text" placeholder="" class="m-wrap" value="${barcodeInfoInputPage.dCode}" readonly="readonly">
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
								<select class="m-wrap" style="width:350px!important" name="taskCode" disabled="disabled">
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
									<span>商标</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.tradeMark" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.tradeMark}" readonly="readonly"/>
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
										<li class="elemet"><input  type="radio" name="generalcheckEntity.pack" value="1" <c:if test="${barcodeInfoInputPage.generalcheckEntity.pack eq '1'}">checked</c:if> disabled="disabled"/>有</li>
										<li class="elemet"><input  type="radio" name="generalcheckEntity.pack" value="0" <c:if test="${barcodeInfoInputPage.generalcheckEntity.pack eq '0'}">checked</c:if> disabled="disabled"/>无</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>等级</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.grade" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.grade}" readonly="readonly"/>
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
										<li class="elemet"><input  type="radio" name="generalcheckEntity.flag" value="1" <c:if test="${barcodeInfoInputPage.generalcheckEntity.flag eq '1'}">checked</c:if> disabled="disabled"/>有</li>
										<li class="elemet"><input  type="radio" name="generalcheckEntity.flag" value="0" <c:if test="${barcodeInfoInputPage.generalcheckEntity.flag eq '0'}">checked</c:if> disabled="disabled"/>无</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>规格型号</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.specifications" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.specifications}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>执行标准</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.execStandard" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.execStandard}" readonly="readonly"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>生产日期或批号</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<input  name="generalcheckEntity.batchNumber" type="text" class="m-wrap" value="${barcodeInfoInputPage.generalcheckEntity.batchNumber}" readonly="readonly"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>产品认证情况</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<t:dictSelect id="productCer" field="generalcheckEntity.productCer" typeGroupCode="productCer" hasLabel="false" defaultVal="${barcodeInfoInputPage.generalcheckEntity.productCer}"  extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>证书编号</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="generalcheckEntity.productCerNo" type="text" class="m-wrap" value="${barcodeInfoInputPage.generalcheckEntity.productCerNo}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span style="color:red;">* </span><span>抽样地点</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul id="areasDiv" class="line">
										<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
										<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样详细地点</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<input name="samplingAddress"  type="text" class="m-wrap" value="${barcodeInfoInputPage.samplingAddress}" readonly="readonly"/>
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
									<input  name="generalcheckEntity.samplingCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.samplingCount}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>抽样基数</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.samplingCardinal" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.samplingCardinal}" readonly="readonly"/>
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
									<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="${industryMonitoringLink}" hasLabel="false" defaultVal="${barcodeInfoInputPage.monitoringLink}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
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
									<input class="m-wrap small m-ctrl-medium date-picker" id="samplingDate" type="text" name="samplingDate" value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" readonly="readonly" disabled="disabled"/>
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
									<span>单位名称</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<input id="unitFullcode" name="unitFullcode" type="hidden" value="${barcodeInfoInputPage.unitFullcode }"/>
								<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" value="${barcodeInfoInputPage.unitFullname}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>通讯地址</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  id="unitAddress" name="unitAddress" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.unitAddress}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮编</span>
								</div>
							</td>
							<td class="value">
								<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" value="${barcodeInfoInputPage.zipCode}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>法定代表人</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<input  id="legalPerson" name="legalPerson" type="text" class="m-wrap" value="${barcodeInfoInputPage.legalPerson}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系人</span>
								</div>
							</td>
							<td class="value">
								<input  id="contact" name="contact" type="text" class="m-wrap small" value="${barcodeInfoInputPage.contact}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>电话</span>
								</div>
							</td>
							<td class="value">
								<input  id="telphone" name="telphone" type="text" class="m-wrap small" value="${barcodeInfoInputPage.telphone}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>传真</span>
								</div>
							</td>
							<td class="value">
								<input  id="fax" name="fax" type="text" class="m-wrap small" value="${barcodeInfoInputPage.fax}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>受检人与摊位号</span>
								</div>
							</td>
							<td class="value" colspan="2">
								<input  name="generalcheckEntity.stall" type="text" class="m-wrap medium" style="width:150px" value="${barcodeInfoInputPage.generalcheckEntity.stall}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>电话</span>
								</div>
							</td>
							<td class="value">
								<input name="generalcheckEntity.telphone" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.telphone}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>传真</span>
								</div>
							</td>
							<td class="value">
								<input  name="generalcheckEntity.fax" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.fax}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title" rowspan="3" style="line-height: 120px;">
								<div class="title-label">
									<span>生产单位情况</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<ul class="line">
									<li class="elemet"><input  type="radio" name="generalcheckEntity.unitProperties" value="0" <c:if test="${barcodeInfoInputPage.generalcheckEntity.unitProperties eq '0'}">checked</c:if> disabled="disabled"/>生产</li>
									<li class="elemet"><input  type="radio" name="generalcheckEntity.unitProperties" value="1" <c:if test="${barcodeInfoInputPage.generalcheckEntity.unitProperties eq '1'}">checked</c:if> disabled="disabled"/>进货单位名称</li>
								</ul>
								</div>
							</td>
							<td class="value" colspan="5">
								<input name="generalcheckEntity.unitName" type="text" class="m-wrap" value="${barcodeInfoInputPage.generalcheckEntity.unitName}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>通讯地址</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<div class="value-elems">
									<input  name="generalcheckEntity.unitAddress" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.generalcheckEntity.unitAddress}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮编</span>
								</div>
							</td>
							<td class="value" colspan="2">
								<input  name="generalcheckEntity.zipCode" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.zipCode}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系人</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input  name="generalcheckEntity.contacts" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.contacts}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>电话</span>
								</div>
							</td>
							<td class="value">
								<input  name="generalcheckEntity.telphone2" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.telphone2}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>传真</span>
								</div>
							</td>
							<td class="value">
								<input  name="generalcheckEntity.fax2" type="text" class="m-wrap small" value="${barcodeInfoInputPage.generalcheckEntity.fax2}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title" rowspan="4" style="line-height: 120px;">
								<div class="title-label">
									<span>抽样单位情况</span>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>单位名称</span>
								</div>
							</td>
							<td class="value" colspan="3">
								<input  name="" type="text" class="m-wrap medium" value="${org.ogrname}" readonly="readonly"/>
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
								<input  name="" type="text" class="m-wrap medium" value="${org.address}" readonly="readonly"/>
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
									<span>E-Mail</span>
								</div>
							</td>
							<td class="value" colspan="5">
								<input  name="" type="text" class="m-wrap medium" value="${org.email}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样文件编号</span>
								</div>
							</td>
							<td class="value" colspan="6">
								<input  name="generalcheckEntity.samplingNo" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.generalcheckEntity.samplingNo}" readonly="readonly"/>
							</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
