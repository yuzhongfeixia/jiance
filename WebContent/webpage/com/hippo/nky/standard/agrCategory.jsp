<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-comments"></i>农产品分类详情</div>
			</div>
			<div class="portlet-body">
				<form action="#" class="form-horizontal" id="addAgrCategoryInfomation">
					<input id="id" name="id" type="hidden" value="${agrCategoryPage.id }">
					<input id="saveDom" name="saveDom" type="hidden" value="${agrCategoryPage.saveDom }">
					<input id="versionid" name="versionid" type="hidden" value="${agrCategoryPage.versionid }">
					<input id="pid" name="pid" type="hidden" value="${agrCategoryPage.pid }">
					<div class="control-group">
						<label class="control-label">编号（CODE）:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium"  id="code" name="code" 
							   value="${agrCategoryPage.code}"  datatype="*3-20" ajaxurl="agrCategoryController.do?checkAgrName&id=${agrCategoryPage.id}&versionid=${agrCategoryPage.versionid }"
							     nullmsg="编号不能为空！">
						</div>
					</div>
					<div class="control-group"> 
						<label class="control-label">是否参与判定标准:</label>
						<div class="controls">
						<c:if test="${agrCategoryPage.agrcategorytype != 1 }">
						<label class="radio">
							<div class="radio">
								<span>
								<input name="agrcategorytype" class="rt2" id="agrcategorytype1" type="radio" value="2" checked/>
								</span>
							</div>
						     	是
						 </label>
						 <label class="radio">
							<div class="radio">
								<span>
						    	<input name="agrcategorytype" class="rt2" id="agrcategorytype2" type="radio" value="1" />
						    	</span>
							</div>
						    	否
						 </label>
						</c:if>
						<c:if test="${agrCategoryPage.agrcategorytype == 1 }">
						<label class="radio">
							<div class="radio">
								<span>
								<input name="agrcategorytype" class="rt2" id="agrcategorytype1" type="radio" value="2"/>
								</span>
							</div>
						     	是
						 </label>
						 <label class="radio">
							<div class="radio">
								<span>
						    	<input name="agrcategorytype" class="rt2" id="agrcategorytype2" type="radio" value="1" checked/>
						    	</span>
							</div>
						    	否
						 </label>
						</c:if>
						</div>
					</div> 
					<div class="control-group">
						<label class="control-label">农产品中文名:</label>
						<div class="controls">
							<input type="text" placeholder="" class="m-wrap medium"  id="cname" name="cname" 
							   value="${agrCategoryPage.cname}"   datatype="*1-16"  nullmsg="农产品中文名不能为空">
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
								<img id="agrImage" src="<%=basePath%>assets/systemImages/NOIMAGE.jpg" style="border: 1px solid #c8c8c8;" title="单击上传"
													 title="单击上传" action-mode="ajax" 
														action-url="systemController.do?callUpload&type=image&callback=callbackStructureImgO" 
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
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
 						<button id="save_btn" type="button" class="btn popenter">保存</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
App.initUniform();
</script>

