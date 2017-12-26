<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="person123List" title="person123" actionUrl="person123Controller.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="age" field="age" ></t:dgCol>
   <t:dgCol title="createdt" field="createdt" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="name" field="name" ></t:dgCol>
   <t:dgCol title="salary" field="salary" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="person123Controller.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="person123Controller.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="person123Controller.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="person123Controller.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>