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
					<i class="icon-reorder"></i> <span class="hidden-480">生鲜乳样品信息</span>
				</div>
			</div>
			<div class="portlet-body" id="monitoring_plan_program_tab1">
				<div class="tab-content">
					<div class="tab-pane active">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm">
						<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
						<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
						<input name="nkyFreshMilkEntity.id" type="hidden" value="${barcodeInfoInputPage.nkyFreshMilkEntity.id }"/>
						<input name="nkyFreshMilkEntity.sampleCode" type="hidden" value="${barcodeInfoInputPage.nkyFreshMilkEntity.sampleCode}"/>
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
											<span style="color:red;">* </span>
											<span>抽样日期和时间</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="samplingDate" id="samplingDate" class="m-wrap medium date-picker" type="text" value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" readonly="readonly" disabled="disabled"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span style="color:red;">* </span>
											<span>抽样场所</span>
										</div>
									</td>
									<td class="value">
										<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="${industryMonitoringLink}" hasLabel="false" defaultVal="${barcodeInfoInputPage.monitoringLink}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>抽样量</span>
										</div>
									</td>
									<td class="value">
										<input name="nkyFreshMilkEntity.samplingCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.samplingCount}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>抽样基数</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="nkyFreshMilkEntity.samplingBaseCount" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.samplingBaseCount}" readonly="readonly"/>
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
												<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
												<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
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
											<input name="samplingAddress"  type="text" class="m-wrap" value="${barcodeInfoInputPage.samplingAddress}" readonly="readonly"/>
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
										<t:dictSelect id="nkyFreshMilkEntity.type" field="nkyFreshMilkEntity.type" typeGroupCode="fresh_milk_type" hasLabel="false" defaultVal="${barcodeInfoInputPage.nkyFreshMilkEntity.type}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
									</td>
									<td class="title">
										<div class="title-label">
											<span>备注</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input  type="text" class="m-wrap medium" id="nkyFreshMilkEntity.typeRemark" name="nkyFreshMilkEntity.typeRemark" value="${barcodeInfoInputPage.nkyFreshMilkEntity.typeRemark}" readonly="readonly"/>
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
												<li class="elemet"><input type="radio" name="nkyFreshMilkEntity.buyLicence" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.buyLicence eq '1'}">checked</c:if> disabled="disabled"/>有</li>
												<li class="elemet"><span>许可证号</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.licenceNo" name="nkyFreshMilkEntity.licenceNo" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.nkyFreshMilkEntity.licenceNo}" readonly="readonly"/></li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.buyLicence" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.buyLicence eq '0'}">checked</c:if> disabled="disabled"/>无</li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.licenceRemark" name="nkyFreshMilkEntity.licenceRemark" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.nkyFreshMilkEntity.licenceRemark}" readonly="readonly"/></li>
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
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.navicert" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.navicert eq '1'}">checked</c:if> disabled="disabled"/>有 </li>
												<li class="elemet"><span>准运证号</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.navicertNo" name="nkyFreshMilkEntity.navicertNo" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.navicertNo}" readonly="readonly"/></li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.navicert" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.navicert eq '0'}">checked</c:if> disabled="disabled"/>无 </li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.navicertRemark" name="nkyFreshMilkEntity.navicertRemark" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.nkyFreshMilkEntity.navicertRemark}" readonly="readonly"/></li>
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
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.deliveryReceitp" value="1" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitp eq '1'}">checked</c:if> disabled="disabled"/>有</li>
												<li class="elemet"><input  type="radio" name="nkyFreshMilkEntity.deliveryReceitp" value="0" <c:if test="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitp eq '0'}">checked</c:if> disabled="disabled"/>无</li>
												<li class="elemet"><span>备注</span></li>
												<li class="elemet"><input id="nkyFreshMilkEntity.deliveryReceitpRemark" name="nkyFreshMilkEntity.deliveryReceitpRemark" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.nkyFreshMilkEntity.deliveryReceitpRemark}" readonly="readonly"/></li>
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
											<input  type="text" class="m-wrap medium" id="nkyFreshMilkEntity.direction" name="nkyFreshMilkEntity.direction" value="${barcodeInfoInputPage.nkyFreshMilkEntity.direction}" readonly="readonly"/>
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
										<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" value="${barcodeInfoInputPage.unitFullname}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>通讯地址</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input  id="unitAddress" name="unitAddress" type="text" class="m-wrap medium" value="${barcodeInfoInputPage.unitAddress}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>邮编</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" value="${barcodeInfoInputPage.zipCode}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>法定代表人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input id="legalPerson" name="legalPerson" type="text" class="m-wrap small" value="${barcodeInfoInputPage.legalPerson}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="nkyFreshMilkEntity.telphone" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.telphone}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>受检人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input name="nkyFreshMilkEntity.examinee" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.examinee}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input name="nkyFreshMilkEntity.telphone2" type="text" class="m-wrap small" value="${barcodeInfoInputPage.nkyFreshMilkEntity.telphone2}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="title">
										<div class="title-label">
											<span>联系人</span>
										</div>
									</td>
									<td class="value" colspan="2">
										<input id="contact" name="contact" type="text" class="m-wrap small" value="${barcodeInfoInputPage.contact}" readonly="readonly"/>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value" colspan="">
										<input id="telphone" name="telphone" type="text" class="m-wrap small" value="${barcodeInfoInputPage.telphone}" readonly="readonly"/>
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
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
