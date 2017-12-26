<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script	src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/messages_cn.js"></script>
<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>

<!-- <script src="assets/scripts/role-validation.js"></script> -->
<!-- BEGIN BODY -->
<body>
	<!-- 弹出层 -->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i><span class="hidden-480">角色信息</span>
					</div>
				</div>
				<div class="portlet-body form">
					<div class="tab-content">
						<div class="tab-pane active" id="monitoring_plan_program_tab1">
							<form action="#" class="form-horizontal" name="addForm" id="addForm">
							 	<input id="id" name="id" type="hidden" value="${role.id }">
								<div class="control-group">
									<label class="control-label">角色名称:</label>
									<div class="controls">
									    <c:if test="${role.id!=null }">
									     	<input type="hidden" name="roleName" value="${role.roleName }"/>
									     	${role.roleName }
									    </c:if>		
									    <c:if test="${role.id==null }">
										<input type="text" class="m-wrap medium"  name="roleName" id="roleName"/>
										</c:if>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">角色编码:</label>
									<div class="controls">
										<input type="text" class="m-wrap medium"  name="roleCode" id="roleCode" value="${role.roleCode }"/>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" data-dismiss="modal" class="btn">关闭</button>
									<button id="add_btn" type="button" class="btn popenter">保存</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
