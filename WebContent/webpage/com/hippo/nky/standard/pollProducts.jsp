<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>

<script type="text/javascript">
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
					<i class="icon-reorder"></i> <span class="hidden-480">污染物详情</span>
				</div>
			</div>
			<div class="portlet-body">
                 <div class="tab-content">
				<div class="tab-pane active" id="monitoring_plan_program_tab1">
					<form action="#" class="form-horizontal"  name="pollProSave" id="pollProSave" validate="true">
						<input id="id" name="id" type="hidden" value="${pollProductsPage.id }">
						<input id="categoryid" name="categoryid" type="hidden" value="${pollProductsPage.categoryid }">
						<input id="versionid" name="versionid" type="hidden" value="${pollProductsPage.versionid }">
						<input id="unit" name="unit" type="hidden" value="${pollProductsPage.unit }">
						<input id="structure" name="structure" type="hidden" value="${pollProductsPage.structure }">
						<div class="control-group">
							<label class="control-label">CAS码:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="cas" value="${pollProductsPage.cas }" 
									datatype="s2-32" ajaxurl="pollProductsController.do?isExsitCheck&id=${pollProductsPage.id}&versionid=${pollProductsPage.versionid}"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">中文通用名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="popcname" value="${pollProductsPage.popcname }"
									datatype="byterange" min="4" max="128"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">英文通用名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="popename" value="${pollProductsPage.popename }"
									 datatype="s2-128"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">中文化学名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="checname" value="${pollProductsPage.checname }"
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">英文化学名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="checname" value="${pollProductsPage.cheename }"
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">类别:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="category" value="${pollProductsPage.category }"
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">主要用途:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="use" value="${pollProductsPage.use }" 
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">残留物中文名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="residuecname" value="${pollProductsPage.residuecname }"
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">残留物英文名称:</label>
							<div class="controls">
								<input id="" type="text" class="m-wrap medium" name="residueename" value="${pollProductsPage.residueename }"
									datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">结构式:</label>
							<div class="controls">
								<span>
									<img id="structureImage" name="structureImage" class="attachment-img" 
									src="${pollProductsPage.structure}" title="单击上传"
									action-mode="ajax" action-url="systemController.do?callUpload&type=image&auto=true&callback=callbackStructureImg" action-pop="uploadPop"/>
								</span>
<%-- 								<c:if test="${load ne'detail'}"> --%>
<%-- 									<span><img id=attachmentImage name="attachmentImage"  src="${pollProductsPage.structure}" onclick="callUpload();" style="border: 1px solid #c8c8c8;width: 206px;" title="单击上传"/></span> --%>
<!-- 									<span class="help-inline"></span> -->
<%-- 								</c:if> --%>
<%-- 								<c:if test="${load eq'detail'}"> --%>
<%-- 									<span><img id=attachmentImage name="attachmentImage"  src="${pollProductsPage.structure}" style="border: 1px solid #c8c8c8;width: 206px;"/></span> --%>
<!-- 									<span class="help-inline"></span> -->
<%-- 								</c:if> --%>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
<!--  							<button id="addBtn" type="button" class="btn popenter" onclick="">保存</button> -->
 							<button id="addBtn" type="button" class="btn popenter" href="pollProductsController.do?save" action-operation="popsave" 
								 action-mode="ajax" action-event="click" action-form="#pollProSave" action-after="refresh_pollCategoryIframe">保存</button>
						</div>
					</form> 
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
<div id="uploadPop" class="modal hide fade" tabindex="-1" data-width="636"></div>