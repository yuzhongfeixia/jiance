<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>

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
							<span class="hidden-480">行政区划管理</span>
						</div>
					</div>
					<div class="portlet-body">
						<div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
								<input name="id" type="hidden" value="${sysAreaCode.id }"/>
								<input name="pid" type="hidden" value="${pid}"/>
								<input name="sysAreaCode.sysAreaCodeEntity" type="hidden" value="${sysAreaCode.sysAreaCodeEntity}"/>
								<input name="flag" type="hidden" value="${sysAreaCode.flag}"/>
								<input name="showOrder" type="hidden" value="${sysAreaCode.showOrder}"/>
									<div class="control-group">
										<label class="control-label">区划名称:</label>
										<div class="controls">
<%-- 											<c:if test="${(sysAreaCode.id != null && isChildAdd == 'yes') || sysAreaCode.id == null}"> --%>
<%-- 												<input  type="text" name="areaname" class="m-wrap small" value="${sysAreaCode.areaname }" datatype="byterange" min="4" max="16" --%>
<!-- 													ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=1"/> -->
<%-- 											</c:if> --%>
<%-- 											<c:if test="${sysAreaCode.id != null && isChildAdd == null}"> --%>
<%-- 												<input  type="text" name="areaname" class="m-wrap small" value="${sysAreaCode.areaname }" readonly="readonly"/> --%>
<%-- 											</c:if> --%>
<%-- 													<input  type="text" name="areaname" class="m-wrap small" value="${sysAreaCode.areaname }" datatype="byterange" min="4" max="128" --%>
<%-- 													ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=1&id=${sysAreaCode.id }"/> --%>
												<input  type="text" name="areaname" class="m-wrap small" value="${sysAreaCode.areaname }" datatype="byterange" min="4" max="128"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">区划代码:</label>
										<div class="controls">
<%-- 											<c:if test="${(sysAreaCode.id != null && isChildAdd == 'yes') || sysAreaCode.id == null}"> --%>
<%-- 												<input  type="text" name="code" class="m-wrap small" value="${sysAreaCode.code }" datatype="/^\d{6}?$/"  --%>
<!-- 													ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=2" errormsg="请输入6位数字！"/> -->
<%-- 											</c:if> --%>
<%-- 											<c:if test="${sysAreaCode.id != null && isChildAdd == null}"> --%>
<%-- 												<input  type="text" name="code" class="m-wrap small" value="${sysAreaCode.code }" readonly="readonly"/> --%>
<%-- 											</c:if> --%>
													<input  type="text" name="code" class="m-wrap small" value="${sysAreaCode.code }" datatype="/^\d{6}?$/" 
													ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=2&id=${sysAreaCode.id }" errormsg="请输入6位数字！"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">自定义编码:</label>
										<div class="controls">
<%-- 											<c:if test="${(sysAreaCode.id != null && isChildAdd == 'yes') || sysAreaCode.id == null}"> --%>
<%-- 												<input  type="text" name="selfcode" class="m-wrap small" value="${sysAreaCode.selfcode }" ignore="ignore" datatype="*" --%>
<!-- 													ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=3"/> -->
<%-- 											</c:if> --%>
<%-- 											<c:if test="${sysAreaCode.id != null && isChildAdd == null}"> --%>
<%-- 												<input  type="text" name="selfcode" class="m-wrap small" value="${sysAreaCode.selfcode }" ignore="ignore" datatype="*"/> --%>
<%-- 											</c:if> --%>
												<input  type="text" name="selfcode" class="m-wrap small" value="${sysAreaCode.selfcode }" ignore="ignore" datatype="s1-32" errormsg="请填写1到32位字符！(特殊字符除外)"
														ajaxurl="sysAreaCodeController.do?isExsitCheck&flg=3&id=${sysAreaCode.id }"/>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">关闭</button>
							<button type="button" class="btn popenter" href="sysAreaCodeController.do?save&parentId=${parentId}" 
								action-operation="popsave" action-mode="ajax" action-event="click" action-form="#saveForm" action-after="refresh_sysAreaCodeList(data)">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- 	</div> -->
</body>
</html>
