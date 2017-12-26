<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>

<script type="text/javascript">
		
// 	$(function() {
// 		$('#cc').combotree({
// 			url : 'functionController.do?setPFunction',
// 			panelHeight:'auto',
// 			onClick: function(node){
// 				$("#functionId").val(node.id);
// 			}
// 		});
		
// 		if($('#functionLevel').val()=='1'){
// 			$('#pfun').show();
// 		}else{
// 			$('#pfun').hide();
// 		}
		
// 		$('#functionLevel').change(function(){
// 			if($(this).val()=='1'){
// 				$('#pfun').show();
// 				var t = $('#cc').combotree('tree');
// 				var nodes = t.tree('getRoots');
// 				if(nodes.length>0){
// 					$('#cc').combotree('setValue', nodes[0].id);
// 					$("#functionId").val(nodes[0].id);
// 				}
// 			}else{
// 				var t = $('#cc').combotree('tree');
// 				var node = t.tree('getSelected');
// 				if(node){
// 					$('#cc').combotree('setValue', null);
// 				}
// 				$('#pfun').hide();
// 			}
// 		});
// 	});
	
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
						<span class="hidden-480">菜单信息</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active" id="monitoring_plan_program_tab1">
							<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
							<input id="id" name="id" type="hidden" value="${function.id}"/>
							<input id="functionLevel" name="functionLevel" type="hidden" value="${function.functionLevel}"/>
								<div class="control-group">
									<label class="control-label">菜单名称:</label>
									<div class="controls">
<%-- 										<c:if test="${function.id != null && isChildAdd == null}"> --%>
<%-- 											<input type="text" name="functionName" class="m-wrap" value="${function.functionName}" readonly="readonly"/> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${function.id == null || (function.id != null && isChildAdd == 'yes')}"> --%>
<!-- 											<input id="functionName" type="text" name="functionName" class="m-wrap" datatype="byterange" min="4" max="50" ajaxurl="functionController.do?isExsitCheck"  -->
<%-- 												value="${function.functionName}"/> --%>
<%-- 										</c:if> --%>
<!-- 										<input id="functionName" type="text" name="functionName" class="m-wrap" datatype="byterange" min="4" max="50"  -->
<%-- 											ajaxurl="functionController.do?isExsitCheck&id=${function.id}" value="${function.functionName}"/> --%>
										<input id="functionName" type="text" name="functionName" class="m-wrap" datatype="byterange" min="4" max="50" 
											  value="${function.functionName}"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">菜单地址:</label>
									<div class="controls">
										<input  type="text" name="functionUrl" class="m-wrap" value="${function.functionUrl}"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">图标名称:</label>
									<div class="controls">
										<div class="functionIcon">
											<div class="functionIcon_view">
												<c:if test="${not empty function.iconId}">
													<i class="${function.iconId}"></i>
												</c:if>
												<c:if test="${empty function.iconId}">
													<span>没有图标</span>
												</c:if>
											</div>
											<input type="hidden" id="iconId"  name="iconId" class="m-wrap" value="${function.iconId}"/>
											<a class="btn mini green" action-mode="ajax" action-pop="system_icons" action-url="commonController.do?getSystemIcons&callback=setCallBackIcon"><i class="icon-info-sign"></i>选择</a>
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">菜单顺序:</label>
									<div class="controls">
										<input  type="text" name="functionOrder" class="m-wrap small" value="${function.functionOrder}" ignore="ignore" datatype="n"/>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn">关闭</button>
						<button type="button" class="btn popenter" href="functionController.do?saveFunction&parentId=${parentId}&isChildAdd=${isChildAdd}" action-operation="popsave" 
						  action-mode="ajax" action-event="click" action-form="#saveForm" action-after="refresh_functionList(data)">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<div id="system_icons" class="modal hide fade" tabindex="-1" data-width="800"></div>