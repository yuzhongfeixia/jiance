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
					<input name="livestockEntityList[0].id" type="hidden" value="${livestockEntityList[0].id }"/>
					<input name="livestockEntityList[0].sampleCode" type="hidden" value="${livestockEntityList[0].sampleCode}"/>
					<table class="table sample_info_table">
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
									<span>样品名称</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="agrCode" type="hidden" value="${barcodeInfoInputPage.agrCode}"/>
									<input type="text" placeholder="" class="m-wrap small" value="${sampleName}"  datatype="*" nullMsg="请选择！" readonly="readonly">
<%-- 									<a class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a> --%>
									<c:if test="${not empty barcodeInfoInputPage.samplePath }">
										<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${imagePath}${barcodeInfoInputPage.samplePath}')">样品图片</a>
									</c:if>
								</div>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span style="color:red;">* </span> -->
<!-- 									<span>样品名称</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value"> -->
<!-- 								<div class="value-elems"> -->
<%-- 									<input name="agrCode" type="hidden" value="${barcodeInfoInputPage.agrCode}"/> --%>
<%-- 									<input type="text" placeholder="" class="m-wrap small" value="${sampleName}"  datatype="*" nullMsg="请选择！" readonly="readonly"> --%>
<%-- 									<a class="btn mini green" action-mode="ajax" action-url="samplingInfoController.do?selsample&projectCode=${barcodeInfoInputPage.projectCode }" action-pop="ajax-modal1" action-before="setAclickEvent" >选择...</a> --%>
<%-- 									<c:if test="${not empty barcodeInfoInputPage.samplePath }"> --%>
<%-- 										<a class="btn mini green" title="单击显示" onclick="showSampleImage('<%=basePath%>${barcodeInfoInputPage.samplePath}')">样品图片</a> --%>
<%-- 									</c:if> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span style="color:red;">* </span> -->
<!-- 									<span>条码</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value"> -->
<%-- 								<c:if test="${barcodeInfoInputPage.id == null}"> --%>
<%-- 									<input name="dCode" type="text" placeholder="" class="m-wrap" value="${barcodeInfoInputPage.dCode}"  --%>
<!-- 										datatype="/^\d{13}?$/" errormsg="此条码信息不正确或已经使用，请核实！" ajaxurl="samplingInfoController.do?checkcode"> -->
<%-- 								</c:if> --%>
<%-- 								<c:if test="${barcodeInfoInputPage.id != null}"> --%>
<%-- 									<input name="dCode" type="text" placeholder="" class="m-wrap" value="${barcodeInfoInputPage.dCode}" readonly="readonly"> --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span style="color:red;">* </span> -->
<!-- 									<span>抽样任务</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value" colspan="3"> -->
<!-- 								<select class="m-wrap" style="width:350px!important" name="taskCode" name="taskCode" datatype="*"> -->
<!-- 									<option value="" selected></option> -->
<%-- 									<c:forEach items="${monitoringTaskList}" var="task"> --%>
<%-- 										<option value="${task.taskcode}" <c:if test="${task.taskcode eq barcodeInfoInputPage.taskCode}">selected</c:if>>${task.taskname}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<td class="title">
								<div class="title-label">
									<span>抽样依据</span>
								</div>
							</td>
							<td class="value">
								<div class="value-elems">
									<input name="livestockEntityList[0].taskSource" type="text" class="m-wrap small" value="${livestockEntityList[0].taskSource}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>商标</span>
								</div>
							</td>
							<td class="value">
								<input  name="livestockEntityList[0].tradeMark" type="text" class="m-wrap small" value="${livestockEntityList[0].tradeMark}" readonly="readonly"/>
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
									<input  name="livestockEntityList[0].samplingBaseCount" type="text" class="m-wrap small" value="${livestockEntityList[0].samplingBaseCount}" readonly="readonly"/>
								</div>
							</td>
							<td class="title">
								<div class="title-label">
									<span>抽样数量</span>
								</div>
							</td>
							<td class="value">
								<input  name="livestockEntityList[0].samplingCount" type="text" class="m-wrap small" value="${livestockEntityList[0].samplingCount}" readonly="readonly"/>
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
									<span style="color:red;">* </span><span>抽样日期</span>
								</div>
							</td>
							<td class="value">
								<input class="m-wrap small date-picker" id="samplingDate" type="text" name="samplingDate" value="<fmt:formatDate value='${barcodeInfoInputPage.samplingDate}' type='both' pattern='yyyy-MM-dd'/>"  readonly="readonly" disabled="disabled"/>
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
										<li class="elemet"><t:dictSelect id="cityCode" field="cityCode" hasLabel="false" customData="${areacodeList}" defaultVal="${barcodeInfoInputPage.cityCode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},datatype:{value : '*'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
										<li class="elemet"><t:dictSelect id="countyCode" field="countyCode" hasLabel="false" customData="${areacodeList2}" defaultVal="${barcodeInfoInputPage.countyCode}"  extend="{class:{value:'small areaSelect'},disabled:{value : 'disabled'}}"></t:dictSelect></li>
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
									<input  name="livestockEntityList[0].saveSaveSituation" type="text" class="m-wrap small" value="${livestockEntityList[0].saveSaveSituation}" readonly="readonly"/>
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
								<input id="unitFullname" name="unitFullname" type="text" class="m-wrap" data-provide="typeahead" 
									data-source="${nameSource}" data-ids="${codeNameSource}" value="${barcodeInfoInputPage.unitFullname}" readonly="readonly"/>
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
						</tr>
						<tr>
							<td class="title">
								<div class="title-label">
									<span>联系电话</span>
								</div>
							</td>
							<td class="value">
								<input  id="telphone" name="telphone" type="text" class="m-wrap" value="${barcodeInfoInputPage.telphone}" readonly="readonly"/>
							</td>
							<td class="title">
								<div class="title-label">
									<span>邮政编码</span>
								</div>
							</td>
							<td class="value">
								<input  id="zipCode" name="zipCode" type="text" class="m-wrap small" value="${barcodeInfoInputPage.zipCode}" readonly="readonly"/>
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
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].samplingMode" value="0" <c:if test="${livestockEntityList[0].samplingMode eq '0'}">checked</c:if> readonly="readonly" disabled="disabled"/>总体随机</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].samplingMode" value="1" <c:if test="${livestockEntityList[0].samplingMode eq '1'}">checked</c:if> readonly="readonly" disabled="disabled"/>其他</li>
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
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].pack" value="0" <c:if test="${livestockEntityList[0].pack eq '0'}">checked</c:if> readonly="readonly" disabled="disabled"/>完好</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].pack" value="1" <c:if test="${livestockEntityList[0].pack eq '1'}">checked</c:if> readonly="readonly" disabled="disabled"/>不完好</li>
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
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].signFlg" value="0" <c:if test="${livestockEntityList[0].signFlg eq '0'}">checked</c:if> readonly="readonly" disabled="disabled"/>完好</li>
										<li class="elemet"><input  type="radio" name="livestockEntityList[0].signFlg" value="1" <c:if test="${livestockEntityList[0].signFlg eq '1'}">checked</c:if> readonly="readonly" disabled="disabled"/>不完好</li>
									</ul>
								</div>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span>畜主/货主</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value"> -->
<!-- 								<div class="value-elems"> -->
<%-- 									<input  name="livestockEntity.cargoOwner" type="text" class="m-wrap" datatype="s" ignore="ignore" value="${barcodeInfoInputPage.livestockEntity.cargoOwner}" /> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span>动物产地/来源</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value"> -->
<!-- 								<div class="value-elems"> -->
<%-- 									<input  name="livestockEntity.animalOrigin" type="text" class="m-wrap small" datatype="s" ignore="ignore" value="${barcodeInfoInputPage.livestockEntity.animalOrigin}"/> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span>检疫证号</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value" colspan="3"> -->
<!-- 								<div class="value-elems"> -->
<%-- 									<input  name="livestockEntity.cardNumber" type="text" class="m-wrap" datatype="s" ignore="ignore" value="${barcodeInfoInputPage.livestockEntity.cardNumber}" /> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td class="title"> -->
<!-- 								<div class="title-label"> -->
<!-- 									<span>备注</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td class="value" colspan="3"> -->
<!-- 								<div class="value-elems"> -->
<%-- 									<textarea name="remark" class="m-wrap" rows="1" style="width: 770px;" datatype="s" ignore="ignore">${barcodeInfoInputPage.remark}</textarea> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr> -->
					</table>
					
					<c:if test="${livestockEntityList == null}">
						<div>
							<a id="addSampleBtn" style="color: #076148;!important" title="添加" onclick="addSample()"><i class="icon-plus"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="delSampleBtn" style="color: #076148;!important" title="删除" onclick="delSample()"><i class="icon-minus"></i></a>
						</div>
					</c:if>
							
					<div id="scrollDiv" style="height:300px;overflow-y:scroll;">
						<table class="table table-bordered" id="sampleFormTable" >
							<tr>
								<c:if test="${livestockEntityList == null}">
									<td style="vertical-align: middle;"><input type="checkbox" name="ck"></td>
								</c:if>
								<td>
									<ul class="monType-list">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>条码:</span>
												</div>
												<input name="livestockEntityList[0].dCode" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].dCode}" readonly="readonly">
											</div>
										</li>
										<c:if test="${detecthedFlg == 1}">
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span>
													<span>制样编码:</span>
												</div>
												<input name="livestockEntityList[0].spCode"  type="text" class="m-wrap" value="${livestockEntityList[0].spCode}" readonly="readonly"/>
											</div>
										</li>
										</c:if>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span style="color:red;">* </span><span>抽样任务:</span>
												</div>
												<select class="m-wrap" style="width:350px!important" name="livestockEntityList[0].taskCode" disabled="disabled">
													<option value="" selected></option>
													<c:forEach items="${monitoringTaskList}" var="task">
														<option value="${task.taskcode}" <c:if test="${task.taskcode eq livestockEntityList[0].taskCode}">selected</c:if>>${task.taskname}</option>
													</c:forEach>
												</select>
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>抽样地点:</span>
												</div>
												<input name="livestockEntityList[0].samplingAddress"  type="text" class="m-wrap" value="${livestockEntityList[0].samplingAddress}" readonly="readonly"/>
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>畜主/货主:</span>
												</div>
												<input name="livestockEntityList[0].cargoOwner" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].cargoOwner}" readonly="readonly">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>动物产地/来源:</span>
												</div>
												<input name="livestockEntityList[0].animalOrigin" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].animalOrigin}" readonly="readonly">
											</div>
											<div class="monType-item">
												<div class="monType-item-title">
													<span>检疫证号:</span>
												</div>
												<input name="livestockEntityList[0].cardNumber" type="text" placeholder="" class="m-wrap" value="${livestockEntityList[0].cardNumber}" readonly="readonly">
											</div>
										</li>
										<li class="monType-row">
											<div class="monType-item">
												<div class="monType-item-title">
													<span>备注:</span>
												</div>
												<textarea name="livestockEntityList[0].remark" class="span6 m-wrap" rows="1" style="width:500px;" readonly="readonly">${barcodeInfoInputPage.remark}</textarea>
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
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>