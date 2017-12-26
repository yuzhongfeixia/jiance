<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="portalExpertList" title="专家组" actionUrl="portalExpertController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','portalExpertController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="专家名称" field="name" ></t:dgCol>
   <t:dgCol title="专家简介" field="description" hidden="false"></t:dgCol>
   <t:dgCol title="工作业绩" field="achievement" hidden="false"></t:dgCol>
   <t:dgCol title="研究方向" field="orientation" ></t:dgCol>
   <t:dgCol title="邮箱" field="email" hidden="false"></t:dgCol>
   <t:dgCol title="职称" field="positionaltitle" ></t:dgCol>
   <t:dgCol title="职务" field="duty" ></t:dgCol>
   <t:dgCol title="邮编" field="postcode" hidden="false"></t:dgCol>
    <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="单位" field="unit" ></t:dgCol>
   <t:dgCol title="地址" field="address" hidden="false"></t:dgCol>
   <t:dgCol title="手机" field="mobilephone" hidden="false"></t:dgCol>
   <t:dgCol title="电话" field="telephone" hidden="false"></t:dgCol>
   <t:dgCol title="传真 " field="faxnumber" hidden="false"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="portalExpertController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="portalExpertController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="portalExpertController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="portalExpertController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>