<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="laboratoryList" title="风险评估实验室" actionUrl="laboratoryController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','laboratoryController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="机构名称" field="ogrname" ></t:dgCol>
   <t:dgCol title="行业" field="industry" ></t:dgCol>
   <t:dgCol title="地址" field="address" ></t:dgCol>
   <t:dgCol title="负责人" field="leader" ></t:dgCol>
   <t:dgCol title="联系人" field="contacts" ></t:dgCol>
   <t:dgCol title="类型" field="type" ></t:dgCol>
   <t:dgCol title="创建时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="创建人" field="createby" ></t:dgCol>
   <t:dgCol title="邮编" field="zipcode" ></t:dgCol>
   <t:dgCol title="负责人电话" field="leadertel" ></t:dgCol>
   <t:dgCol title="联系人电话" field="contactstel" ></t:dgCol>
   <t:dgCol title="负责人座机" field="leaderphone" ></t:dgCol>
   <t:dgCol title="传真" field="fax" ></t:dgCol>
   <t:dgCol title="电子邮箱" field="email" ></t:dgCol>
   <t:dgCol title="联系人座机" field="contactsphone" ></t:dgCol>
   <t:dgCol title="专业性风险评估实验室依托单位" field="professionalsupportunit" ></t:dgCol>
   <t:dgCol title="区域性风险评估实验室依托单位" field="regionalsupportunit" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="laboratoryController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="laboratoryController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="laboratoryController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="laboratoryController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>