<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<table>
		<input type="hidden" value="${titles }" id="titles">
		<c:forEach items="${twoList}" var="two" varStatus="stus">
			<c:forEach var="fe" begin="1" end="${printCopies }" step="1">
				<c:if test="${fe %2 eq 1 }">
					<tr height="130px;"
						style="padding-top: 10px; padding-bottom: 10px;">
				</c:if>
			
				<td style="padding-left: 20px; text-align: center;" height="130px;"
					width="85px;">
<!-- 					<span style="font-size: 12px; text-align: center;">项目名称</span><br/> -->
<!-- 				    <span style="font-size: 12px; text-align: center;">打印时间</span> -->
					<img style="padding-top: 5px;"
					src="${two.realpath }" width="85px;" height="80px;" /><br />
				<span style="font-size: 12px; text-align: center;">
				  <c:if test="${stus.index %2 eq 1 }">■</c:if>
				  <c:if test="${stus.index %2 eq 0 }">●</c:if>${two.title}
				</span> 
				<c:if test="${fe %2 eq 1 }">
						<td>
				</c:if> 
				<c:if test="${fe %2 eq 0 }"></td>
				   </tr>
				 </c:if>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>