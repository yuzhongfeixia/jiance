<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>

<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
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
								<form action="#" class="form-horizontal" name="saveForm1" id="saveForm1" validate="true">
									<div class="control-group">
										<label class="control-label">参数名称:</label>
										<div class="controls">
											<input  type="text" id="typename" name="typename" class="m-wrap small" datatype="byterange" min="1" max="50" 
												ajaxurl="systemController.do?isTypeExsitCheck&checkFlg=1&typegroupid=${typegroupid}&id=${type.id }" value="${type.typename }"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">参数值:</label>
										<div class="controls">
											<input  type="text" id="typecode" name="typecode" class="m-wrap small" datatype="s1-50" 
												ajaxurl="systemController.do?isTypeExsitCheck&checkFlg=2&typegroupid=${typegroupid}&id=${type.id }" value="${type.typecode }"/>
										</div>
									</div>
									 <input name="id" type="hidden" value="${type.id }">
									 <input name="TSTypegroup.id" id="typegroupid" type="hidden" value="${typegroupid}">
								</form>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
<!-- 							<button id="add_btn1" type="button" class="btn popenter">保存</button> -->
							<button type="button" class="btn popenter" href="systemController.do?saveType&typegroupid=${typegroupid}&pid=${pid}"
								action-mode="ajax" action-event="click" action-form="#saveForm1" action-operation="popsave" action-after="refresh_typeGroupList(data)">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- 	</div> -->
</body>
</html>
