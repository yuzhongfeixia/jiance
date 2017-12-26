<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/context/mytags.jsp"%>
<div class="portlet box box_usual">
	<div class="portlet box box_usual">
	<form action="#" class="form-horizontal" id="agrCategoryInfomation">
		<div class="portlet-title">
			<div class="caption"><i class="icon-comments"></i>农产品分类详情</div>
			<div class="tools">
				<a href="#" style="color: #076148;!important" title="保存" id="agrCategorySaveBtn" ><i class="icon-save"></i></a>
			</div>
		</div>
			<div class="portlet-body">
					<input id="id" name="id" type="hidden" value="${agrCategoryPage.id }">
					<input id="saveDom" name="saveDom" type="hidden" value="${agrCategoryPage.saveDom }">
					<input id="versionid" name="versionid" type="hidden" value="${agrCategoryPage.versionid }">
					<input id="pid" name="pid" type="hidden" value="${agrCategoryPage.pid }">
					<div class="control-group">
						<label class="control-label">编号（CODE）:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium"  id="code" name="code" 
							   value="${agrCategoryPage.code}" datatype="*3-20" ajaxurl="agrCategoryController.do?checkAgrName&id=${agrCategoryPage.id}&versionid=${agrCategoryPage.versionid }"
							     nullmsg="编号不能为空！">
						</div>
					</div>
					<div class="control-group"> 
						<label class="control-label">是否参与判定标准:</label>
						<div class="controls">
						<c:if test="${agrCategoryPage.agrcategorytype != 1 }">
						 <label class="radio"><input name="agrcategorytype" class="rt2" id="agrcategorytype1" type="radio" value="2" checked/>是</label>
						 <label class="radio"><input name="agrcategorytype" class="rt2" id="agrcategorytype2" type="radio" value="1"/>否</label>
						</c:if>
						<c:if test="${agrCategoryPage.agrcategorytype == 1 }">
						 <label class="radio"><input name="agrcategorytype" class="rt2" id="agrcategorytype1" type="radio" value="2"/>是</label>
						 <label class="radio"><input name="agrcategorytype" class="rt2" id="agrcategorytype2" type="radio" value="1" checked/>否</label>
						</c:if>
						</div>
					</div> 
					<div class="control-group">
						<label class="control-label">农产品中文名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium"  id="cname" name="cname" 
							   value="${agrCategoryPage.cname}" value="${agrCategoryPage.cname}"   datatype="*1-16"  nullmsg="农产品中文名不能为空">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">农产品英文名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="ename" name="ename"
							   value="${agrCategoryPage.ename}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">农产品拉丁名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="latin" name="latin"
							   value="${agrCategoryPage.latin}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">农产品中文别名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="calias" name="calias"
							   value="${agrCategoryPage.calias}">
							<a class="btn green mini small" id="caliasUpdate" data-toggle="modal" action-mode="ajax"
								action-url="agrCategoryController.do?anotherNameSet&id=${agrCategoryPage.id }" 
								action-pop="arCategoryAnotherName" style="margin-top: 5px;"> 编辑</a>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">农产品英文别名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="ealias" name="ealias"
							   value="${agrCategoryPage.ealias}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">GEMS编码:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="gems" name="gems"
							   value="${agrCategoryPage.gems}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">FOODEX_2编码:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium" id="foodex" name="foodex"
							   value="${agrCategoryPage.foodex}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">图片路径:</label>
						<div class="controls">
							<span>
								<input type="hidden" id="imagepath" name="imagepath" value="${agrCategoryPage.imagepath}">
								<img id="agrImage" src="<%=basePath%>${empty agrCategoryPage.imagepath?'assets/systemImages/NOIMAGE.jpg':agrCategoryPage.imagepath}" style="border: 1px solid #c8c8c8;" title="单击上传"
													 title="单击上传" action-mode="ajax" 
														action-url="systemController.do?callUpload&type=image&callback=callbackStructureImgR" 
														action-pop="uploadPop"/>
							</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">描述:</label>
						<div class="controls">
							<textarea class="span6 m-wrap" rows="1" style="width: 300px;">${agrCategoryPage.describe}</textarea>
						</div>
					</div>
			</div>
			</form>
	</div>
</div>
<script>
App.initUniform();
</script>
