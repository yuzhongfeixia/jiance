<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">限量信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input id="id" name="id" type="hidden" value="${limitStandardPage.id }">
						<input id="versionid" name="versionid" type="hidden" value="${limitStandardPage.versionid }">
								<div class="control-group">
									<label class="control-label">污染物CAS码:</label>
									<div class="controls">
										<input id="cas" type="text" name="cas" class="m-wrap medium" value="${limitStandardPage.cas}" datatype="*2-32" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">污染物(中文名):</label>
									<div class="controls">
										<input id="" type="text" name="pollnameZh" class="m-wrap medium" value="${limitStandardPage.pollnameZh}"
											datatype="byterange" min="4" max="300"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">污染物(英文名):</label>
									<div class="controls">
										<input id="" type="text" name="pollnameEn" class="m-wrap medium" value="${limitStandardPage.pollnameEn}"
											datatype="s2-500"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">农产品类别:</label>
									<div class="controls">
										<input id="" type="text" name="agrcategory" class="m-wrap medium" value="${limitStandardPage.agrcategory}"
											datatype="s1-32"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">农产品名称:</label>
									<div class="controls">
										<input id="" type="text" name="agrname" class="m-wrap medium" value="${limitStandardPage.agrname}"
											datatype="byterange" min="4" max="128"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">主要用途:</label>
									<div class="controls">
										<input id="" type="text" name="use" class="m-wrap medium" value="${limitStandardPage.use}"
											datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">每日允许摄入量:</label>
									<div class="controls">
										<input id="" type="text" name="adi" class="m-wrap medium" value="${limitStandardPage.adi}"
											 datatype="/^\d{1,4}(\.\d{1,6})?$/" errormsg="请输入整数4位，小数6位的数字"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">残留物:</label>
									<div class="controls">
										<input id="" type="text" name="residue" class="m-wrap medium" value="${limitStandardPage.residue}"
											datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">最大残留限量:</label>
									<div class="controls">
										<input id="" type="text" name="mrl" class="m-wrap medium" value="${limitStandardPage.mrl}"
											 datatype="/^\d{1,4}(\.\d{1,6})?$/" errormsg="请输入整数4位，小数6位的数字"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">单位:</label>
									<div class="controls">
										<t:dictSelect id="unit" field="unit" typeGroupCode="unit" hasLabel="false" defaultVal="${limitStandardPage.unit}" extend="{datatype:{value : '*'}}"></t:dictSelect>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">检测方法:</label>
									<div class="controls">
										<textarea class="span6 m-wrap" name="method"  rows="1" style="width: 300px;" datatype="s1-1000" ignore="ignore" errormsg="请填写1到1000位字符！(特殊字符除外)">${limitStandardPage.method}</textarea>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">备注:</label>
									<div class="controls">
										<textarea class="span6 m-wrap" name="describe" rows="1" style="width: 300px;" datatype="s1-1000" ignore="ignore" errormsg="请填写1到1000位字符！(特殊字符除外)">${limitStandardPage.describe}</textarea>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
									<button id="addBtn" type="button" class="btn popenter" href="limitStandardController.do?save" action-operation="popsave" 
										  action-mode="ajax" action-event="click" action-form="#saveForm" action-after="refresh_limitStandardList">保存</button>
								</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>