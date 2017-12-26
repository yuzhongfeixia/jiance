<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>行业集合</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:datagrid name="industryList" title="按行业选择" actionUrl="organizationController.do?datagridIndustry" idField="id" showRefresh="false">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="行业名称" field="name" width="50"></t:dgCol>
  </t:datagrid>
 </body>
</html>
