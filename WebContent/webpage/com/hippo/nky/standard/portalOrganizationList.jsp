<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="portalOrganizationList" title="组织机构" actionUrl="portalOrganizationController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','portalOrganizationController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="机构名称" field="ogrname" ></t:dgCol>
   <t:dgCol title="依托单位" field="supportinstitution" ></t:dgCol>
   <t:dgCol title="性质" field="property" dictionary="organizationproperty" query="true"></t:dgCol>
   <t:dgCol title="代码" field="code" ></t:dgCol>
   <t:dgCol title="邮编" field="zipcode" hidden="false"></t:dgCol>
   <t:dgCol title="详细地址" field="address" hidden="false"></t:dgCol>
   <t:dgCol title="负责人" field="leader" ></t:dgCol>
   <t:dgCol title="联系人" field="contacts" ></t:dgCol>
   <t:dgCol title="类型"  field="type" dictionary="organizationtype" query="true"></t:dgCol>
   <t:dgCol title="负责人电话" field="leadertel" hidden="false"></t:dgCol>
   <t:dgCol title="联系人电话" field="contactstel" hidden="false"></t:dgCol>
   <t:dgCol title="传真" field="fax" hidden="false"></t:dgCol>
   <t:dgCol title="电子邮箱" field="email" hidden="false"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="portalOrganizationController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="portalOrganizationController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="portalOrganizationController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="portalOrganizationController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>