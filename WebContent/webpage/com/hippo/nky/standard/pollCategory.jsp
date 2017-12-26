<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>
<link rel="stylesheet" type="text/css"
	href="assets/plugins/select2/select2_metro.css" />

<script type="text/javascript"
	src="assets/plugins/select2/select2.min.js"></script>
<script src="assets/scripts/ui-modals.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>
<script
	src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<link href="assets/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<script src="assets/scripts/app.js"></script>
<script src="assets/scripts/form-components.js"></script>
<script src="plug-in/easyui/jquery.easyui.min.1.3.2.js"
	type="text/javascript"></script>
<script src="plug-in/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>

<script type="text/javascript">
		
</script>

<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<!-- 弹出层 -->
<!-- 	<div id="jcxxgl_window1" class="modal hide fade" tabindex="-1" data-width="460"> -->
		<div class="row-fluid">
			<div class="span12">  
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-reorder"></i>
							<span class="hidden-480">污染物分类</span>
						</div>
					</div>
					<div class="portlet-body">
						<div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
								<input name="id" type="hidden" value="${pollCategoryPage.id }"/>
								<input name="versionid" type="hidden" value="${pollCategoryPage.versionid }"/>
								<input name="pid" type="hidden" value="${pollCategoryPage.pid }"/>
								<input name="saveDom" type="hidden" value="${pollCategoryPage.saveDom }"/>
									<div class="control-group">
										<label class="control-label">分类名称:</label>
										<div class="controls">
											<input type="text" name="name" class="m-wrap" value="${pollCategoryPage.name}"
												datatype="byterange" min="4" max="128" ajaxurl="pollCategoryController.do?isExsitCheck&id=${pollCategoryPage.id}&versionid=${pollCategoryPage.versionid}"/>
										</div>
									</div>
									<div class="control-group" hidden="hidden">
										<label class="control-label">分类编码:</label>
										<div class="controls">
											<input  type="text" name="code" class="m-wrap" value="${pollCategoryPage.code}"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">描述:</label>
										<div class="controls">
											<input type="text" name="describe" class="m-wrap" value="${pollCategoryPage.describe }" ignore="ignore"
												datatype="s1-1000" errormsg="请填写1到1000位字符！(特殊字符除外)"/>
										</div>
									</div>
<!-- 									<table class="table table-striped table-bordered table-hover "> -->
<!-- 										<tr> -->
<!-- 											<td>分类名称:</td> -->
<%-- 											<td><input  type="text" name="name" class="m-wrap small" value="${pollCategoryPage.name}"/> --%>
<!-- 												<span class="Validform_checktip"></span> -->
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 										<tr hidden="hidden"> -->
<!-- 											<td>分类编码:</td> -->
<!-- 											<td> -->
<%-- 												<input  type="text" name="code" class="m-wrap small" value="${pollCategoryPage.code}"/> --%>
<!-- 												<span class="Validform_checktip"></span> -->
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 										<tr> -->
<!-- 											<td>描述:</td> -->
<!-- 											<td> -->
<%-- 												<input type="text" name="describe" class="m-wrap small" value="${pollCategoryPage.describe }"/> --%>
<!-- 												<span class="Validform_checktip"></span> -->
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 									</table> -->
								</form>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
							<button id="addBtn1" type="button" class="btn popenter"
								 href="pollCategoryController.do?save" action-mode="ajax" action-event="click" action-operation="popsave" 
								 action-form="#saveForm" action-after="saveDom">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- 	</div> -->
</body>
</html>
