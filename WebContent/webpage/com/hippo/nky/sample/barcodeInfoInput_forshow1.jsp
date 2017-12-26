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
					<i class="icon-reorder"></i> <span class="hidden-480">例行监测样品信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
							<input name="id" type="hidden" value="${barcodeInfoInputPage.id }"/>
							<input name="projectCode" type="hidden" value="${barcodeInfoInputPage.projectCode }"/>
							<input name="routinemonitoringList[0].id" type="hidden" value="${routinemonitoringList[0].id }"/>
							<input name="routinemonitoringList[0].sampleCode" type="hidden" value="${routinemonitoringList[0].sampleCode}"/>
							<table class="table sample_info_table">
								<tr>
									<td class="title">
										<div class="title-label">
											<span>抽样人员姓名</span>
										</div>
									</td>
									<td class="value" colspan="5">
										<div class="value-elems">
											<input id="samplingPersons" name="samplingPersons" type="text" class="m-wrap"  value="${barcodeInfoInputPage.samplingPersons}" readonly="readonly"/>
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
											<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" value="${barcodeInfoInputPage.unitFullname}" readonly="readonly"/>
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
											<input id="unitAddress" name="unitAddress" type="text" class="m-wrap" value="${barcodeInfoInputPage.unitAddress}" readonly="readonly"/>
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
											<input id="legalPerson" name="legalPerson" type="text" class="m-wrap small" value="${barcodeInfoInputPage.legalPerson}" readonly="readonly"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>邮政编码</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input id="zipCode" name="zipCode" type="text" class="m-wrap small" value="${barcodeInfoInputPage.zipCode}" readonly="readonly"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>联系电话</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input id="telphone" name="telphone" type="text" class="m-wrap small" value="${barcodeInfoInputPage.telphone}" readonly="readonly"/>
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
											<input name="routinemonitoringList[0].taskSource" type="text" class="m-wrap small" value="${routinemonitoringList[0].taskSource}" readonly="readonly"/>
										</div>
									</td>
									<td class="title">
										<div class="title-label">
											<span>执行标准</span>
										</div>
									</td>
									<td class="value">
										<div class="value-elems">
											<input name="routinemonitoringList[0].execStanderd"  type="text" class="m-wrap small" value="${routinemonitoringList[0].execStanderd}" readonly="readonly"/>
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
												value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>" readonly="readonly" disabled="disabled"/>
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
											<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="${industryMonitoringLink}" hasLabel="false" defaultVal="${barcodeInfoInputPage.monitoringLink}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
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
												<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
												<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
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
<%-- 											<input name="samplingAddress"  type="text" class="m-wrap" value="${barcodeInfoInputPage.samplingAddress}" readonly="readonly"/> --%>
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 								</tr> -->
							</table>
							
							<div id="scrollDiv" style="height:330px;overflow-y:scroll;">
								<table class="table table-bordered" id="sampleFormTable" >
									<tr>
										<td>
											<ul class="monType-list">
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>条码:</span>
														</div>
															<input name="routinemonitoringList[0].dCode" type="text" placeholder="" class="m-wrap" value="${routinemonitoringList[0].dCode}" readonly="readonly">
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span style="color:red;">* </span><span>抽样任务:</span>
														</div>
														<select class="m-wrap" style="width:350px!important" name="routinemonitoringList[0].taskCode" readonly="readonly" disabled="disabled">
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
														<input name="routinemonitoringList[0].agrCode" type="hidden" value="${routinemonitoringList[0].agrCode}"/>
														<input type="text" class="m-wrap small" value="${sampleName}" readonly="readonly">
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
														<input name="routinemonitoringList[0].samplingAddress"  type="text" class="m-wrap" value="${routinemonitoringList[0].samplingAddress}" readonly="readonly"/>
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
														<input name="routinemonitoringList[0].spCode"  type="text" class="m-wrap" datatype="*" value="${routinemonitoringList[0].spCode}" readonly="readonly"/>
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>抽样地点:</span>
														</div>
														<input name="routinemonitoringList[0].samplingAddress"  type="text" class="m-wrap" value="${routinemonitoringList[0].samplingAddress}" readonly="readonly"/>
													</div>
												</li>
												</c:if>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品来源:</span>
														</div>
														<input name="routinemonitoringList[0].sampleSource" type="text" placeholder="" class="m-wrap" value="${routinemonitoringList[0].sampleSource}" readonly="readonly">
													</div>
													<div class="monType-item">
														<div class="monType-item-title">
															<span>样品量(n/N):</span>
														</div>
														<input name="routinemonitoringList[0].sampleCount" type="text" placeholder="" class="m-wrap" value="${routinemonitoringList[0].sampleCount}" readonly="readonly">
													</div>
												</li>
												<li class="monType-row">
													<div class="monType-item">
														<div class="monType-item-title">
															<span>备注:</span>
														</div>
														<textarea name="routinemonitoringList[0].remark" class="span6 m-wrap"  rows="1" style="width:500px;" readonly="readonly">${barcodeInfoInputPage.remark}</textarea>
													</div>
												</li>
											</ul>
										</td>
						
									</tr>
								</table>
							</div>
							
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
