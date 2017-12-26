<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
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
						<input type="hidden" value="${ids }" name="ids">
						<div class="control-group">
							<label class="control-label">确认打印所选样品标签？</label>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							<button id="printBtn" data-dismiss="modal" type="button" class="btn popenter" onclick="">确定</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>