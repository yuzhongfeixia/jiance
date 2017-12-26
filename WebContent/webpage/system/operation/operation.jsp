<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>

<script type="text/javascript">	
	// 设置返回的图标
	function setCallBackIcon(data){
		var icon = $(".functionIcon_view i");
		var selectIcon = data.params.selectIcon || '';
		if(icon.length > 0){
			if(isEmpty(selectIcon)){
				$(".functionIcon_view i").remove();
				$(".functionIcon_view").append("<span>没有图标</span>");
			} else {
				$(".functionIcon_view i").attr("class", selectIcon);
			}
		} else {
			$(".functionIcon_view span").remove();
			$(".functionIcon_view").append('<i class="' + selectIcon + '"></i>');
		}
		
		$("#iconId").val(data.params.selectIcon);
	}
</script>

<style>
.functionIcon{
	margin: 3px 0px 5px 0px;
}
.functionIcon_view{
	float: left;
	margin-top: 2px;
	margin-right:10px;
}
</style>

<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
		<div class="row-fluid">
			<div class="span12">  
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-reorder"></i>
							<span class="hidden-480">部门信息</span>
						</div>
					</div>
					<div class="portlet-body">
						<div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
									<div class="control-group">
											<label class="control-label">操作名称:</label>
											<div class="controls">
												<input  type="text" name="operationname" class="m-wrap" value="${operation.operationname}" 
													datatype="byterange" min="4" max="50" ajaxurl="functionController.do?isExsitCheckOp&flg=1&id=${operation.id}&functionId=${functionId}" />
											</div>
									</div>
									<div class="control-group">
											<label class="control-label">操作代码:</label>
											<div class="controls">
												<input  type="text" name="operationcode" class="m-wrap" value="${operation.operationcode}" 
													datatype="s2-50" ajaxurl="functionController.do?isExsitCheckOp&flg=2&id=${operation.id}&functionId=${functionId}"/>
											</div>
									</div>
									<div class="control-group">
										<label class="control-label">图标名称:</label>
										<div class="controls">
											<div class="functionIcon">
												<div class="functionIcon_view">
													<c:if test="${not empty operation.iconId}">
														<i class="${operation.iconId}"></i>
													</c:if>
													<c:if test="${empty operation.iconId}">
														<span>没有图标</span>
													</c:if>
												</div>
												<input type="hidden" id="iconId"  name="iconId" class="m-wrap" value="${operation.iconId}"/>
												<a class="btn mini green" action-mode="ajax" action-pop="system_opr_icons" action-url="commonController.do?getSystemIcons&callback=setCallBackIcon"><i class="icon-info-sign"></i>选择</a>
											</div>
										</div>
									</div>
									<div class="control-group">
											<label class="control-label">状态:</label>
											<div class="controls">
												<input type="text" name="status" class="m-wrap" value="${operation.status}"/>
											</div>
									</div>
								
<!-- 									<table class="table table-striped table-bordered table-hover "> -->
<!-- 										<tr> -->
<!-- 											<td>操作名称:</td> -->
<%-- 											<td><input  type="text" name="operationname" class="m-wrap small" value="${operation.operationname}"/> --%>
<!-- 												<span class="Validform_checktip">操作名称范围2~20位字符</span> -->
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 										<tr> -->
<!-- 											<td>操作代码:</td> -->
<%-- 											<td><input  type="text" name="operationcode" class="m-wrap small" value="${operation.operationcode}"/> --%>
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 										<tr> -->
<!-- 											<td>图标名称:</td> -->
<!-- 											<td><select name="TSIcon.id"> -->
<%-- 													<c:forEach items="${iconlist}" var="icon"> --%>
<%-- 														<option value="${icon.id}" --%>
<%-- 															<c:if test="${icon.id==function.TSIcon.id }">selected="selected"</c:if>> --%>
<%-- 															${icon.iconName}</option> --%>
<%-- 													</c:forEach> --%>
<!-- 											</select></td> -->
<!-- 										</tr> -->
<!-- 										<tr> -->
<!-- 											<td>状态:</td> -->
<%-- 											<td><input type="text" name="status" class="m-wrap small" value="${operation.status}"/> --%>
<!-- 											<span class="Validform_checktip">必须为数字</span> -->
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 									</table> -->
									<input name="id" type="hidden" value="${operation.id}">
									 <input id="functionId" name="TSFunction.id" value="${functionId}" type="hidden">
								</form>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
							<button type="button" class="btn popenter" 
							href="functionController.do?saveop" action-mode="ajax" action-event="click" action-form="#saveForm"  action-operation="popsave" action-after="refresh_operationList">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>

</body>
<div id="system_opr_icons" class="modal hide fade" tabindex="-1" data-width="1060"></div>
