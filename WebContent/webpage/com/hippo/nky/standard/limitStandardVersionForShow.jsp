<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">版本信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input id="id" name="id" type="hidden" value="${limitStandardVersionPage.id }">
						<input id="type" name="type" type="hidden" value="${type}">
							<div class="control-group">
									<label class="control-label">标准号:</label>
									<div class="controls">
										<input id="standardCode" type="text" name="standardCode" class="m-wrap" value="${limitStandardVersionPage.standardCode}" readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">国别:</label>
									<div class="controls">
										<t:dictSelect id="standardCountry" field="standardCountry" typeGroupCode="standardCountry" 
											hasLabel="false" defaultVal="${limitStandardVersionPage.standardCountry}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">类别:</label>
									<div class="controls">
										<t:dictSelect id="standardType" field="standardType" typeGroupCode="standardType" 
											hasLabel="false" defaultVal="${limitStandardVersionPage.standardType}" extend="{disabled:{value : 'disabled'}}"></t:dictSelect>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">中文名称:</label>
									<div class="controls">
										<input id="" name="nameZh" type="text" class="m-wrap medium" value="${limitStandardVersionPage.nameZh}" readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">英文名称:</label>
									<div class="controls">
										<input id="" name="nameEn" type="text" class="m-wrap medium" value="${limitStandardVersionPage.nameEn}" readonly="readonly"/>
									</div>
								</div>
<!-- 								<div class="control-group"> -->
<!-- 									<label class="control-label">发布日期:</label> -->
<!-- 									<div class="controls"> -->
<!-- 										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="publishDate"  -->
<%-- 										name="publishDate" value="${limitStandardVersionPage.publishDate}"/> --%>
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="control-group">
									<label class="control-label">实施日期:</label>
									<div class="controls">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="implementDate"
										name="implementDate" value="<fmt:formatDate value='${limitStandardVersionPage.implementDate}' type='both' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">发布机构:</label>
									<div class="controls">
										<input id="" name="publishOrg" value="${limitStandardVersionPage.publishOrg}" type="text" class="m-wrap medium" readonly="readonly"/>
									</div>
								</div>
<!-- 								<div class="control-group"> -->
<!-- 									<label class="control-label">状态:</label> -->
<!-- 									<div class="controls"> -->
<%-- 										<t:dictSelect id="stopflag" field="stopflag" typeGroupCode="stopstart" hasLabel="false" defaultVal="${limitStandardVersionPage.stopflag}"></t:dictSelect> --%>
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="control-group">
									<label class="control-label">替代标准:</label>
									<div class="controls">
										<textarea class="span6 m-wrap" name="substitute" rows="1" style="width: 300px;" readonly="readonly">${limitStandardVersionPage.substitute}</textarea>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">作废标准:</label>
									<div class="controls">
										<textarea class="span6 m-wrap" name="invalid"  rows="1" style="width: 300px;" readonly="readonly">${limitStandardVersionPage.invalid}</textarea>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">备注:</label>
									<div class="controls">
										<textarea class="span6 m-wrap" name="describe" rows="1" style="width: 300px;" readonly="readonly">${limitStandardVersionPage.describe}</textarea>
									</div>
								</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>