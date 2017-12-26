<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script>
$('#monitoring_plan_project_div1').find("input[type='checkbox']").uniform();
</script>

<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">选择区县</span>
				</div>
			</div>
			<div class="portlet-body form">
                  <div class="tab-content">
					<div class="tab-pane active">
						<form action="#" class="form-horizontal">
							<div id="monitoring_plan_project_div1" class="control-group" style="margin-top:30px; margin-left:20px;">
								<c:forEach items="${areaCountList}" var="areaCountObj" varStatus="stuts">
									<c:if test="${(stuts.index%column) == 0 }">
										<div class="span4" style="width:160px;">
									</c:if>
									<c:if test="${!empty areaCountObj.code}">
										<input type="text" placeholder="抽样数量" style="width:60px;float:right;" id="count_${areaCountObj.code}" name="count_${areaCountObj.code}" value="${areaCountObj.count}"/>
										<label class="checkbox line"><input type="checkbox" value="${areaCountObj.code}" valuename="${areaCountObj.name}" ${areaCountObj.checked}/> ${areaCountObj.name}</label>
									</c:if>
									<c:if test="${(stuts.index%column) == column - 1 }">
										</div>
									</c:if>
									<c:if test="${ (stuts.index%column) != column - 1 && stuts.last}">
										</div>
									</c:if>
								</c:forEach>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button type="button" action-mode="ajax"  action-operation="popsave"  class="btn popenter" action-before="ConfirmContry()">确定</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
function ConfirmContry(){
	var returnRows = "${returnRows}"; 
	var codeStr = "";
	var codeNameStr = "";
	var nameStr = "";
	var totalCount = 0;
	var result = true;
	$("#monitoring_plan_project_div1").find("input[type='checkbox']:checked").each(function(index){
		var id = $(this).val();
		var count = $("#count_"+id).val();
		if(count == ""){
			count = "0";
		}
		if(count != "0" && ! /^\+?[1-9][0-9]*$/.test(count)){
	  		result = false;
	  		modalTips("数量【"+count+"】格式错误，只能输入整数！");
	  		return false;
		}
		codeStr = codeStr + id + "#KV#" + count + "#EM#";
		codeNameStr = codeNameStr + id + "#KV#" + $(this).attr("valuename") + "#EM#";
		
		totalCount = totalCount + parseInt(count);
		if(count != "0"){
			nameStr = nameStr + $(this).attr("valuename") + "(" + count + ")、";
		}else{
			nameStr = nameStr + $(this).attr("valuename") +"、";
		}
		
	});
	if(!result){
		return false;
	}
	codeStr=codeStr.replace(/#EM#$/gi,"");
	codeNameStr=codeNameStr.replace(/#EM#$/gi,"");
	nameStr=nameStr.replace(/、$/gi,"");
	// 抽检市
	// 抽检县,市区(编码)
	$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+returnRows+"].districtcode']").val(codeStr);
	$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+returnRows+"].districtcodeName']").val(codeNameStr);
	
	// 抽检县,市区(名称)
	$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+returnRows+"].districtcode']").parent().find("span").text(nameStr);
	// 城市总数量
	$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+returnRows+"].count']").val(totalCount);
	// 总数量
	monitoringAreaCount_all();
	return true;
}
</script>