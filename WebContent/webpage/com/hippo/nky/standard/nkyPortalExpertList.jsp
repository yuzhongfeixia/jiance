<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="nkyPortalExpertList" title="专家委员会" actionUrl="nkyPortalExpertController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','nkyPortalExpertController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="专家名称" field="name" ></t:dgCol>
   <t:dgCol title="专家简介" field="description" ></t:dgCol>
   <t:dgCol title="工作业绩" field="achievement" ></t:dgCol>
   <t:dgCol title="研究方向" field="orientation" ></t:dgCol>
   <t:dgCol title="邮箱" field="email" ></t:dgCol>
   <t:dgCol title="单位" field="unit" ></t:dgCol>
   <t:dgCol title="地址" field="address" ></t:dgCol>
   <t:dgCol title="手机" field="mobilePhone" ></t:dgCol>
   <t:dgCol title="电话" field="telephone" ></t:dgCol>
   <t:dgCol title="传真" field="faxNumber" ></t:dgCol>
   <t:dgCol title="邮编" field="postcode" ></t:dgCol>
   <t:dgCol title="职称" field="positionaltitle" ></t:dgCol>
   <t:dgCol title="职务" field="duty" ></t:dgCol>
   <t:dgCol title="创建时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="创建人" field="createby" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="nkyPortalExpertController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="nkyPortalExpertController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="nkyPortalExpertController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="nkyPortalExpertController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>