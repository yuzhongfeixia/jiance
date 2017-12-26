<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@include file="/context/mytags.jsp"%>

<script>
function callbackStructureImg(data){
	var path = data.attributes.attProperties[0].fileRelativePath + data.attributes.attProperties[0].fileName;
	var attElement = $("#structureImage");
	if(attElement.length > 0){
		attElement.attr("src", getActionPath(path));
	}
	$("#structure").val(getActionPath(path));
}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">污染物信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input id="id" name="id" type="hidden" value="${pollDictionaryPage.id}">
							<div class="control-group">
								<label class="control-label">CAS码:</label>
								<div class="controls">
									<input id="" name="cas" type="text" value="${pollDictionaryPage.cas}"
										class="m-wrap medium" datatype="s2-32" ajaxurl="pollDictionaryController.do?isExsitCheck&id=${pollDictionaryPage.id}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">中文通用名称:</label>
								<div class="controls">
									<input id="" name="popcname" type="text" value="${pollDictionaryPage.popcname}"
										class="m-wrap medium" datatype="s1-128"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">英文通用名称:</label>
								<div class="controls">
									<input id="" name="popename" type="text" value="${pollDictionaryPage.popename}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">中文化学名称:</label>
								<div class="controls">
									<input id="" name="checname" type="text" value="${pollDictionaryPage.checname}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">英文化学名称:</label>
								<div class="controls">
									<input id="" name="cheename" type="text" value="${pollDictionaryPage.cheename}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">类别:</label>
								<div class="controls">
									<input id="" name="category" type="text" value="${pollDictionaryPage.category}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">主要用途:</label>
								<div class="controls">
									<input id="" name="use" type="text" value="${pollDictionaryPage.use}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">残留物中文名称:</label>
								<div class="controls">
								<input id="" name="residuecname" type="text" value="${pollDictionaryPage.residuecname}"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">残留物英文名称:</label>
								<div class="controls">
								<input id="" name="residueename" type="text" value="${pollDictionaryPage.residueename}"
								class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">污染物性质:</label>
								<div class="controls">
									<t:dictSelect id="disableFlg" field="disableFlg" typeGroupCode="pollproperty" hasLabel="false" defaultVal="${pollDictionaryPage.disableFlg}" extend="{datatype:{value : '*'}}"></t:dictSelect>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">结构式:</label>
								
							<div class="controls">
								<span>
									<img id="structureImage" name="structureImage" class="attachment-img" 
									src="<%=basePath%>${pollDictionaryPage.structure}" title="单击上传"
									action-mode="ajax" action-url="systemController.do?callUpload&type=image&auto=true&callback=callbackStructureImg" action-pop="uploadPop"/>
								</span>
							</div>
							<input type="hidden" id="structure" name="structure"
							   value="${pollDictionaryPage.structure}">
							</div>

							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="addBtn" type="button" class="btn popenter" href="pollDictionaryController.do?save" action-operation="popsave" 
										  action-mode="ajax" action-event="click" action-form="#saveForm" action-after="refresh_pollDictionaryList">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="uploadPop" class="modal hide fade" tabindex="-1" data-width="636"></div>