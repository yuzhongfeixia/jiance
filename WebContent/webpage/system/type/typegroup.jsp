<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>

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
							<span class="hidden-480">字典类型信息</span>
						</div>
					</div>
					<div class="portlet-body">
						<div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
									<div class="control-group">
										<label class="control-label">字典名称:</label>
										<div class="controls">
											<input name="typegroupname" type="text" datatype="byterange" min="1" max="50" ajaxurl="systemController.do?isTypeGroupExsitCheck&checkFlg=1&id=${typegroup.id }" 
												class="m-wrap small" value="${typegroup.typegroupname}"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">字典编码:</label>
										<div class="controls">
											<input  type="text" id="typegroupcode" name="typegroupcode" class="m-wrap small" datatype="s2-50" ajaxurl="systemController.do?isTypeGroupExsitCheck&checkFlg=2&id=${typegroup.id }" 
											 	value="${typegroup.typegroupcode}"/>
										</div>
									</div>
									 <input id="id" name="id" type="hidden" value="${typegroup.id }">
								</form>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
							<button type="button" class="btn popenter" href="systemController.do?saveTypeGroup" action-mode="ajax" action-event="click" action-operation="popsave" 
								 action-form="#saveForm" action-after="refresh_typeGroupList">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- 	</div> -->
</body>
</html>
