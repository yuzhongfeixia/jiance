<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">检测详细信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal">
							<div style="margin: 0 auto">
								<div style="float: left" class="form-horizontal">
									<div class="control-group">
										<label class="control-label">任务名称</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${taskName}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">检测结果</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${detectionResult}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<c:if test="${detached == 0 }">
											<label class="control-label">样品条码</label>
										</c:if>
										<c:if test="${detached == 1 }">
											<label class="control-label">制样编码</label>
										</c:if>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${spCode}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">样品名称</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${cname}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">受检单位名称</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${unitFullname}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">受检单位性质</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${monitoringLink}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">受检单位所在地</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${unitAddress}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">抽样单位名称</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${samplingOgrname}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">抽样时间</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${samplingDate}" readonly="readonly"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">检测单位名称</label>
										<div class="controls">
											<input type="text" class="m-wrap medium" value="${detectionOgrname}" readonly="readonly"/>
										</div>
									</div>
<!-- 									<div class="control-group"> -->
<!-- 										<label class="control-label">检测时间</label> -->
<!-- 										<div class="controls"> -->
<!-- 											<input type="text" class="m-wrap medium" value="2013-02-28" readonly="readonly"/> -->
<!-- 										</div> -->
<!-- 									</div> -->
								</div>

								<div style="width: 350px; height: 367px; padding: 0; float: left; margin-left: 60px; ">
									<table class="table table-striped table-bordered table-hover"
										style="margin-bottom: 0px !important;">
										<thead>
											<tr>
												<th class="center hidden-480" style="width:66px;">序号</th>
												<th class="center hidden-480" style="width:120px;">检测项目</th>
												<th class="center hidden-480">检测值</th>
<!-- 												<th width="2%"></th> -->
											</tr>
										</thead>
									</table>
									<div style="overflow-y: auto; height: 328px; margin-top: 0">
										<table
											class="table table-striped table-bordered table-hover"
											id="sample_1" style="border-top: 0">
											<tbody>
												<c:forEach items="${detectionInfo}" var="detection" varStatus="stu">
													<tr>
														<td width="66px">${stu.count}</td>
														<td width="120px">${detection.pollName}</td>
														<td>
															<c:if test="${detection.detectionValue < 0}">
																未检
															</c:if>
															<c:if test="${detection.detectionValue == 0}">
																未检出
															</c:if>
															<c:if test="${detection.detectionValue > 0}">
																${detection.detectionValue}
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer" style="margin-top: 5px;">
					<form id="frm_detectionReportForm">
						<input type="hidden" name="sampleId" value="${sampleId}"/>
						<input type="hidden" name="templete" value="${templete}"/>
						<input type="hidden" name="projectCode" value="${projectCode}"/>
					</form>
					<button type="button" class="btn popenter" onclick="exportDetectionReport()">导出检测报告</button>
					<button type="button" data-dismiss="modal"
						class="btn popclose">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
function exportDetectionReport(){
	exportWordByCustom('detection.DetectionService.getExportWordDataList','','frm_detectionReportForm');
}
</script>
