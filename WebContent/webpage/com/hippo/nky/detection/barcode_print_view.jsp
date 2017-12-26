<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%-- 	<jsp:plugin type="applet" code="com.hippo.nky.common.LocatePrint.class" --%>
<%-- 		codebase="." width="0" height="0"> --%>
<%-- 		<jsp:params> --%>
<%-- 			<jsp:param name="pdf_url" value="${param.pdfurl }"></jsp:param> --%>
<%-- 		</jsp:params> --%>
<%-- 		<jsp:fallback>   --%>
<!--     <p>浏览器不支持Applet的显示</p>   -->
<%--     </jsp:fallback> --%>
<%-- 	</jsp:plugin> --%>
<!-- 	<div> 启动中... -->
<!-- 	</div> -->
<!-- <div style="text-align: center; position: absolute; background-color: rgb(255, 255, 255); height:100%;scroll:none;opacity: 0; z-index: 1; background-position: initial initial; background-repeat: initial initial;"> -->
	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" style="background-color: rgb(38,38,38);width:100%;height:100%; border: 0px none;">  
	<param name="_Version" value="65539">  
	<param name="_ExtentX" value="20108">  
	<param name="_ExtentY" value="10866">  
	<param name="_StockProps" value="0">  
	<param name="SRC" value="${param.pdfurl }">  
	</object>
<!-- </div>   -->

</body>
</html>