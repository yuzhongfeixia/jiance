<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="../../../../../context/mytags.jsp"%>
<script type="text/javascript">
function refresh(){
	refreshList($("#ncpflbz"));
}
jQuery(document).ready(function() {
	var type="${type}";
	if (type == 'copy') {
		$('#versioncname').focus();
	}
});
</script>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">版本信息</span>
				</div>
			</div>
			<div class="portlet-body">
                  <div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form id="form1" name="form1" action="#" class="form-horizontal" validate="true">
							<input id="id" name="id" type="hidden" value="${standardVersionPage.id}">
							<input id="type" name="type" type="hidden" value="${type}">
							<input id="category" name="category" type="hidden" value="${standardVersionPage.category}">
							<div class="control-group">
								<label class="control-label">版本名称:</label>
								<div class="controls">
									<c:if test="${type eq 'copy' }">
										<input id="versioncname" name="cname" type="text" class="m-wrap medium" value="${standardVersionPage.cname}"
										datatype="*4-128" ajaxurl="standardVersionController.do?isExsitCheck&category=${standardVersionPage.category}"/>
									</c:if>
									<c:if test="${type eq null}">
										<input id="versioncname" name="cname" type="text" class="m-wrap medium" value="${standardVersionPage.cname}"
										datatype="*4-128" ajaxurl="standardVersionController.do?isExsitCheck&id=${standardVersionPage.id}&category=${standardVersionPage.category}"/>
									</c:if>
									
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">发布机构:</label>
								<div class="controls">
									<input id="publishorg" name="publishorg" type="text" class="m-wrap medium" value="国家农产品质量安全检测信息平台" readonly/>
									<span class="help-inline"></span>
								</div>
							</div>
						</form> 
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							<c:if test="${type eq 'copy' }">
								<button id="copy_Btn" type="button" class="btn popenter" href="standardVersionController.do?save" action-mode="ajax" action-operation="popsave" action-event="click" action-form="#form1" action-after="refresh()">更新</button>
							</c:if>
							<c:if test="${type eq null}">
								<button id="save_btn" type="button" class="btn popenter" href="standardVersionController.do?save" action-mode="ajax" action-operation="popsave" action-event="click" action-form="#form1" action-after="refresh()">保存</button>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>