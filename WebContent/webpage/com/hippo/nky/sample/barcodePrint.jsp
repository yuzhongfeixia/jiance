<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/messages_cn.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
</head>
<body>
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">打印条码</span>
					</div>
				</div>
				<div class="portlet-body">
					<form action="#" id="addForm" name="addForm" class="form-horizontal">
						<input id="projectCode" name="projectCode" type="hidden" value="${monitoringProject.projectCode }">
						<input id="id" name="id" type="hidden" value="${barcodeId }">
						<div class="control-group">
							<label class="control-label">项目名称:</label>
							<div class="controls">
								<input id="monitoring_plan_program_txt1" type="text"
									class="m-wrap medium" value="${monitoringProject.name }" readonly="readonly" /> <span
									class="help-inline"></span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">打印份数:</label>
							<div class="controls">
								<input id="printNumberCopies" name="printNumberCopies" type="text"
									class="m-wrap medium" value="4" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">打印数量:</label>
							<div class="controls">
								<input id="printCount" name="printCount" type="text"
									class="m-wrap medium" value="" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							<button id="printBtn" type="button" class="btn popenter" onclick="">确定</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>