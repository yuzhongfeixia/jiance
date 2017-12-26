<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
 </head>
<body>
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
					<div class="tab-pane active" id="tab1">
						<form id="addForm" name="addForm" class="form-horizontal">
							<input id="id" name="id" type="hidden" value="${judgeStandardPage.id }">
							<div class="control-group">
								<label class="control-label">判定标准值:</label>
								<div class="controls">
									<input class="m-wrap small" id="value" name="value" value="${judgeStandardPage.value}" disabled>
									<span class="help-inline"></span><input id="valueCheck" type="checkbox" onchange="$('#value').attr('disabled',!$(this).is(':checked'));"/>
								</div>
							</div>
			<%-- 				<div class="control-group">
								<label class="control-label">判定值来源:</label>
								<div class="controls">
									<t:dictSelect id="valfrom" field="valuefrom" typeGroupCode="valuefrom" hasLabel="false" defaultVal="${judgeStandardPage.valuefrom}" extend="{disabled:{value:'true'}}"></t:dictSelect>
									<span class="help-inline"></span><input id="valuefromCheck" type="checkbox" onchange="$('#valfrom').attr('disabled',!$(this).is(':checked'));"/>
								</div>
							</div> --%>
							<div class="control-group">
								<label class="control-label">单位:</label>
								<div class="controls">
									<t:dictSelect id="substate" field="units" typeGroupCode="unit" hasLabel="false" defaultVal="${judgeStandardPage.units}" extend="{disabled:{value:'true'}}"></t:dictSelect>
									<span class="help-inline"></span><input id="unitsCheck" type="checkbox"  onchange="$('#substate').attr('disabled',!$(this).is(':checked'));"/>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">使用规定:</label>
								<div class="controls">
									<t:dictSelect id="stip" field="stipulate" typeGroupCode="stipulate" hasLabel="false" defaultVal="${judgeStandardPage.stipulate}" extend="{disabled:{value:'true'}}"></t:dictSelect>
									<span class="help-inline"></span><input id="stipulateCheck" type="checkbox" onchange="$('#stip').attr('disabled',!$(this).is(':checked'));"/>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
 								<button id="add_btn" type="button" class="btn popenter" >执行</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>