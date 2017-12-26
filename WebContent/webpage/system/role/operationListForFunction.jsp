<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:forEach items="${operationList}" var="operation" >
	 <c:if test="${fn:contains(operationcodes, operation.operationcode)}"> 
	 <span class="icon ${operation.TSIcon.iconClas}">&nbsp;</span><input style="width:20px;" type="checkbox" name="operationCheckbox" value="${operation.operationcode}" checked="checked"/>${operation.operationname}
	 </c:if>
	  <c:if test="${!fn:contains(operationcodes, operation.operationcode)}"> 
	 <span class="icon group_add">&nbsp;</span><input style="width:20px;" type="checkbox" name="operationCheckbox" value="${operation.operationcode}" />${operation.operationname}
	 </c:if>
	<br>
</c:forEach>
<script type="text/javascript">
function submitOperation() {
	var functionId = "${functionId}";
	var roleId = $("#rid").val();
	var operationcodes = '';
	$("input[name='operationCheckbox']").each(function(i){
		   if(this.checked){
			   operationcodes+= this.value + ',';
		   }
	 });
	// 不需要转码
// 	operationcodes=escape(operationcodes); 
	var jsonParam = {};
	jsonParam["targetUrl"] = 'roleController.do?updateOperation';
	jsonParam["params"] = {"functionId" : functionId , "roleId" : roleId, "operationcodes" : operationcodes};
	jsonParam["after"] = "showUpdateAuthorityTips";
	AjaxMode.nomalAction(jsonParam);
	// 废弃值传递方式，以免造成字符串过长引起的异常
// 	doSubmit("roleController.do?updateOperation&functionId=" + functionId + "&roleId=" + roleId+"&operationcodes="+operationcodes);
}
</script>
