<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:tabs id="typeGroupTabs" iframe="false"  tabPosition="top">
 <c:forEach items="${typegroupList}" var="typegroup">
  <t:tab iframe="standardVersionController.do?standardVersion&flag=tabs&category=${typegroup.id}" icon="icon-add" title="${typegroup.name}" id="${typegroup.code}"></t:tab>
 </c:forEach>
</t:tabs>
