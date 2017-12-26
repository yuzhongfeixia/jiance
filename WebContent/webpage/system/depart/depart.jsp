<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../context/mytags.jsp"%>

<script type="text/javascript">
	var tempAreaHTMLS = "";
	LinkChange.init();
	$(document).ready(function(){
		if(Sys.ie){
			tempAreaHTMLS = $("#areaRow").html().replace(/selected/g,'');
		} else {
			tempAreaHTMLS = $("#areaRow").html().replace(/selected=\"selected\"/g,'');
		}
		
		setAreaRows(false);
	});
	
	$("#grade").on('change', function(){
		setAreaRows(true);
	});
	
	function setAreaRows(reset){
		var grade = $("#grade").val();
		if(reset){
			$("#areaRow").html(tempAreaHTMLS);
			LinkChange.init();
		}
		// null或省级 不需要行政区划
		if(isEmpty(grade) || '1' == grade){
			$("#code").attr("disabled", "disabled").attr("ignore","ignore");
			$("#areacode2").attr("disabled", "disabled").attr("ignore","ignore");
		}
		// 市级不需要区县
		if('2' == grade){
			$("#code").removeAttr("disabled").removeAttr("ignore");
			$("#areacode2").attr("disabled", "disabled").attr("ignore","ignore");
		}
		// 区县级两个都需要
		if('3' == grade){
			$("#code").removeAttr("disabled").removeAttr("ignore");
			$("#areacode2").removeAttr("disabled").removeAttr("ignore");
		}
	}
</script>

<body>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">单位信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input name="id" type="hidden" value="${depart.id }"/>
						<input name="depart.TSPDepart" type="hidden" value="${depart.TSPDepart }"/>
							<div class="control-group">
								<label class="control-label">单位名称:</label>
								<div class="controls">
									<input  type="text" name="departname" class="m-wrap small" value="${depart.departname }" 
										datatype="byterange" min="4" max="100" ajaxurl="departController.do?isExsitCheck&id=${depart.id }" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">单位级别:</label>
								<div class="controls">
									<t:dictSelect id="grade" field="grade" typeGroupCode="departgrade" hasLabel="false" defaultVal="${depart.grade}" extend="{datatype:{value:'*'}}"></t:dictSelect>
								</div>
							</div>
							<div id="areaRow">
								<div class="control-group">
									<label class="control-label">行政区划:</label>
									<div class="controls">
										<div id="areasDiv">
											<t:dictSelect id="code" field="code" hasLabel="false" customData="${areacodeList}" defaultVal="${depart.code}" extend="{data-set:{value:'#areasDiv .areaSelect'},datatype:{value:'*'},link-Change:{value:'true'}}"></t:dictSelect>
											<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" customData="${areacodeList2}" defaultVal="${depart.areacode2}"  extend="{class:{value:'small areaSelect'},datatype:{value:'*'}}"></t:dictSelect>
										</div>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">职能描述:</label>
								<div class="controls">
									<textarea rows="5" cols="50" name="description" datatype="s1-500" ignore="ignore">${depart.description }</textarea>
<%-- 									<input  type="text" name="description" class="m-wrap small" value="${depart.description }" datatype="s1-500" ignore="ignore"/> --%>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">单位地址:</label>
								<div class="controls">
									<textarea rows="3" cols="50" name="address" datatype="s1-300">${depart.address }</textarea>
								</div>
							</div>
						
<!-- 							<table class="table table-striped table-bordered table-hover "> -->
<!-- 								<tr> -->
<!-- 									<td>单位名称:</td> -->
<%-- 									<td><input  type="text" name="departname" class="m-wrap small" value="${depart.departname }"/> --%>
<!-- 										<span class="Validform_checktip">单位名称在3~10位字符</span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>行政区划:</td> -->
<!-- 									<td> -->
<!-- 										<div id="areasDiv"> -->
<%-- 											<t:dictSelect id="code" field="code" hasLabel="false" customData="${areacodeList}" defaultVal="${depart.code}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'}}"></t:dictSelect> --%>
<%-- 											<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" customData="${areacodeList2}" defaultVal="${depart.areacode2}"  extend="{class:{value:'small areaSelect'}}"></t:dictSelect> --%>
<!-- 										</div> -->
<!-- 										<span class="Validform_checktip"></span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>单位级别:</td> -->
<!-- 									<td> -->
<%-- 										<t:dictSelect id="grade" field="grade" typeGroupCode="departgrade" hasLabel="false" defaultVal="${depart.grade}"></t:dictSelect> --%>
<!-- 										<span class="Validform_checktip"></span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>职能描述:</td> -->
<!-- 									<td> -->
<%-- 										<input  type="text" name="description" class="m-wrap small" value="${depart.description }"/> --%>
<!-- 										<span class="Validform_checktip"></span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>单位地址:</td> -->
<!-- 									<td> -->
<%-- 										<textarea rows="5" cols="20" name="address">${depart.address }</textarea> --%>
<!-- 										<span class="Validform_checktip"></span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">关闭</button>
<!-- 					<button id="addBtn" type="button" class="btn popenter">保存</button> -->
					<button type="button" class="btn popenter" href="departController.do?save" action-mode="ajax" action-event="click" action-operation="popsave" 
								 action-form="#saveForm" action-after="refresh_departList">保存</button>
				</div>
			</div>
		</div>
	</div>
</div>

