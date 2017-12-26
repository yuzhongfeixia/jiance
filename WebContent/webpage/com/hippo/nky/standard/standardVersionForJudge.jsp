<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script>
function createMsg_judge(){
	if($("#save_btn_view").attr("disabled")){
		return false;
	}
	var validator = $("#form1").data("validator");
	if(validator != null && !validator.checkAll(false, false)){
		return false;
	}
	alert('创建判断标准需要大量数据，请耐心等待,可能执行的时间较长!');
	$("#form1 button:not(#save_btn)").attr("disabled", "true");
	$("#save_btn_view").html("创建中...");
	setTimeout(function(){$("#save_btn").click()},100);
}
function refresh_judge(){
	$("#form1 button").removeAttr("disabled");
	$("#save_btn_view").html("保存");
	refreshList($("#ncpflbz"));
}
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
						<form id="form1" action="standardVersionController.do?save&type=${type}" class="form-horizontal" validate="true">
							<input id="id" name="id" type="hidden" value="${standardVersionPage.id}">
							<input id="type" name="type" type="hidden" value="${type}">
							<input id="category" name="category" type="hidden" value="2">
							<div class="control-group">
								<label class="control-label">版本名称:</label>
								<div class="controls">
									<input id="cname" name="cname" type="text" class="m-wrap medium" value="${standardVersionPage.cname}"
										datatype="*4-128" ajaxurl="standardVersionController.do?isExsitCheck&id=${standardVersionPage.id}&category=2"/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">发布机构:</label>
								<div class="controls">
									<input id="publishorg" name="publishorg" type="text" class="m-wrap medium" value="国家农产品质量安全检测信息平台" readonly/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group" style="display: none;">
								<label class="control-label">状态:</label>
								<div class="controls">
									<input id="state" name="state" type="text" class="m-wrap medium" value="${standardVersionPage.state}"/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">农产品版本:</label>
								<div class="controls">
									<select id="agrVersionId"  name="agrVersionId">
								       <c:forEach items="${agrVersionList}" var="agrVersion">
									        <option value="${agrVersion.id }" >
									         ${agrVersion.cname}
									        </option>
								       </c:forEach>
							     	</select>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">污染物版本:</label>
								<div class="controls">
									<select id="pollVersionId"  name="pollVersionId">
								       <c:forEach items="${pollVersionList}" var="pollVersion">
									        <option value="${pollVersion.id}">
									         ${pollVersion.cname}
									        </option>
								       </c:forEach>
							       </select>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">限量标准版本:</label>
								<div class="controls">
									<select id="limitVersionId"  name="limitVersionId">
								       <c:forEach items="${limitVersionList}" var="limitVersion">
									        <option value="${limitVersion.id}">
									         ${limitVersion.nameZh}
									        </option>
								       </c:forEach>
							       </select>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">判定标准版本:</label>
								<div class="controls">
									<select id="judgeVersionId"  name="judgeVersionId">
								       <c:forEach items="${judgeVersionList}" var="judgeVersion">
									        <option value="${judgeVersion.id}">
									         ${judgeVersion.cname}
									        </option>
								       </c:forEach>
							       </select>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="save_btn_view" type="button" class="btn popenter" onClick="createMsg_judge()">保存</button>
 								<button id="save_btn" type="button" class="btn popenter" style="display:none;" href="standardVersionController.do?save" action-mode="ajax" action-operation="popsave" action-event="click" action-form="#form1" action-after="refresh_judge()">保存</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>