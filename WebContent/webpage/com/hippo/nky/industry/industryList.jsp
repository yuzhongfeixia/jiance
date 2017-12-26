<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="industryList" title="行业信息表 " actionUrl="industryController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','industryController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="编码" field="code" ></t:dgCol>
   <t:dgCol title="名称" field="name" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="industryController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="industryController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="industryController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="industryController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>