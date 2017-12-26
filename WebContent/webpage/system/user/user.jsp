<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>
<script
	src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="assets/plugins/jquery-multi-select/css/multi-select.css" />
<script type="text/javascript"
	src="assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>

<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/messages_cn.js"></script>
<script type="text/javascript"
	src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>

<!-- <script src="assets/scripts/user-validation.js"></script> -->
<script>
	jQuery(document).ready(function() {  
		var roleIds = '${roleIds}';	
		$('#roleid').multiSelect({
			selectableHeader: "<div class='custom-header'>备选角色</div>",
			selectionHeader: "<div class='custom-header'>角色</div>",
			keepOrder: true
		});
		/* if(isNotEmpty(roleIds)){
			roleIds = roleIds.substring(0,roleIds.length-1);
			var array = roleIds.split(',');
			$('#roleid').multiSelect('select', array);
		} */
	});
	
	function changeChecked(){
		var utype = $("#usertype").val();
		if(utype != '' && utype == 0){
			$('#depId').attr("name","TSDepart.id");
			$('#depId').css("display","block");

			$('#orgId').css("display","none");
			$('#orgId').val("");
			$('#orgId').attr("name","");
		}else if(utype != '' && utype == 1){
			$('#orgId').attr("name","TSDepart.id");
			$('#orgId').css("display","block");
			
			$('#depId').css("display","none");
			$('#depId').val("");
			$('#depId').attr("name","");
		}
	}
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<!-- 弹出层 -->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">用户录入</span>
					</div>
				</div>
				<div class="portlet-body form">
					<form action="#" class="form-horizontal" name="addForm"
						id="addForm">
						<input id="id" name="id" type="hidden" value="${user.id }">
						<div class="control-group">
							<label class="control-label">用户名:</label>
							<div class="controls">
								<c:if test="${user.id!=null }">
									     	${user.userName }
									    </c:if>
								<c:if test="${user.id==null }">
									<input type="text" class="m-wrap medium" name="userName" id="userName"/>
								</c:if>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">真实姓名:</label>
							<div class="controls">
								<input type="text" class="m-wrap medium" name="realName"
									id="realName" value="${user.realName }" />
							</div>
						</div>
<%-- 						<c:if test="${user.id==null }"> --%>
						<div class="control-group">
							<label class="control-label">密码:</label>
							<div class="controls">
								<input type="password" class="m-wrap medium" name="password"
									id="password" value="${user.password }"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">确认密码:</label>
							<div class="controls">
								<input id="repassword" type="password" class="m-wrap medium"
									id="password" name="repassword" value="${user.password}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">用户类型:</label>
							<div class="controls">
								<t:dictSelect id="usertype" field="usertype" typeGroupCode="sys_user_type"  hasLabel="false" defaultVal="${user.usertype}" extend="{onchange:{value:'changeChecked();'}}"></t:dictSelect>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">单位:</label>
							<div class="controls">
								<c:if test="${user.usertype eq 1}">
									<select id="orgId" name="TSDepart.id">
										<option value=""></option>
										<c:forEach items="${orgList}" var="org">
											<option value="${org.id}"
												<c:if test="${org.id==user.TSDepart.id}">selected="selected"</c:if>>
												${org.ogrname}</option>
										</c:forEach>
									</select>
									<select id="depId" name="" style="display:none;">
										<option value=""></option>
										<c:forEach items="${departList}" var="depart">
											<option value="${depart.id }"
												<c:if test="${depart.id==user.TSDepart.id}">selected="selected"</c:if>>
												${depart.departname}</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${user.usertype eq 0 or empty user.usertype}">
									<select id="orgId" name="" style="display:none;">
										<option value=""></option>
										<c:forEach items="${orgList}" var="org">
											<option value="${org.id }"
												<c:if test="${org.id==user.TSDepart.id}">selected="selected"</c:if>>
												${org.ogrname}</option>
										</c:forEach>
									</select>
	
									<select id="depId" name="TSDepart.id">
										<option value=""></option>
										<c:forEach items="${departList}" var="depart">
											<option value="${depart.id }"
												<c:if test="${depart.id==user.TSDepart.id}">selected="selected"</c:if>>
												${depart.departname}</option>
										</c:forEach>
									</select>
								</c:if>
										
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">角色:</label>
							<div class="controls">
								<select multiple="multiple" id="roleid" name="roleid">
									<c:forEach items="${roleList}" var="role">
										<option value="${role.id }" <c:if test="${fn:containsIgnoreCase(roleIds,role.id)}">selected="selected"</c:if>>
										${role.roleName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">手机号码:</label>
							<div class="controls">
								<input type="text" class="m-wrap medium" id="mobilePhone"
									name="mobilePhone" value="${user.mobilePhone}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">办公电话:</label>
							<div class="controls">
								<input type="text" class="m-wrap medium" id="officePhone"
									name="officePhone" value="${user.officePhone}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">常用邮箱:</label>
							<div class="controls">
								<input type="text" class="m-wrap medium" name="email" id="email"
									value="${user.email}" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
							<button type="button" id="add_btn" class="btn popenter">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>