<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="../../../../../context/mytags.jsp"%>

<script>
function selectOrg() {
   	var $modal = $('#ajax-modal1'); 
   	var pageContent = $('.page-content');
   	App.blockUI(pageContent, false);
     $modal.load('nkyMonitoringPadController.do?selectorg', '', function(){
      $modal.modal({width:"800px"});
      App.unblockUI(pageContent);
    });
}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">客户端信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
							<input id="id" name="id" type="hidden" value="${nkyMonitoringPad.id }">
							<input id="orgCode" name="orgCode" type="hidden" value="${nkyMonitoringPad.orgCode }">
							<div class="control-group">
								<label class="control-label">用户名:</label>
								<div class="controls">
									<input id="" class="m-wrap medium" type="text" name="username" value="${nkyMonitoringPad.username }" datatype="s4-32"
										ajaxurl="nkyMonitoringPadController.do?isExsitCheck&id=${nkyMonitoringPad.id }"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">密码:</label>
								<div class="controls">
									<input id="" class="m-wrap medium" type="password" name="password" value="${nkyMonitoringPad.password }" 
										datatype="s6-32"/>
								</div>
							</div>
<!-- 							<div class="control-group"> -->
<!-- 								<label class="control-label">所属单位:</label> -->
<!-- 								<div class="controls"> -->
<%-- 									<input id="orgid" type="text" name="orgid" value="${nkyMonitoringPad.orgid }" class="m-wrap medium" /> --%>
<!-- 									<a id="sel_btn" class="btn mini blue" onclick="selectOrg();"><i class="icon-external-link"></i>选择</a> -->
<!-- 									<span class="help-inline"></span> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="control-group">
								<label class="control-label">所属单位:</label>
								<div class="controls">
									<input id="orgname" type="text" name="orgname" value="${orgname}" class="m-wrap medium" datatype="*" readonly="readonly"/>
									<a id="sel_btn" class="btn mini green" style="margin-top:4px;" onclick="selectOrg();"><i class="icon-external-link"></i>选择</a>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">备注:</label>
								<div class="controls">
									<textarea id="" class="m-wrap" name="remark"
										rows="5" datatype="s1-255" ignore="ignore" errormsg="请填写1到255位字符！(特殊字符除外)">${nkyMonitoringPad.remark}</textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="addBtn" type="button" class="btn popenter" href="nkyMonitoringPadController.do?save" action-mode="ajax" 
									action-operation="popsave" action-event="click" action-form="#saveForm" action-after="refresh_nkyMonitoringPadList">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>