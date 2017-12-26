	<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
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
						<i class="icon-reorder"></i> <span class="hidden-480">编辑实验室编码</span>
					</div>
				</div>
				<div class="portlet-body form">
					<form action="#" class="form-horizontal" name="addForm" id="addForm">
						<input type="hidden" name="sampleCode" id="sCode" value="${sEntity.id }">
						<div class="control-group">
							<label class="control-label">实验室编号:</label>
							<div class="controls">
								<input type="text" class="m-wrap small " value="${sEntity.labCode }" name="labCode" id="labCode"/>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							<button id="add_btn" type="button" class="btn popenter">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
