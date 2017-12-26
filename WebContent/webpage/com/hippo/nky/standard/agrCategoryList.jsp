<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="agrCategoryList" title="农产品分类" actionUrl="agrCategoryController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','agrCategoryController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="name" field="name" ></t:dgCol>
   <t:dgCol title="版本id" field="versionid" ></t:dgCol>
   <t:dgCol title="创建时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="父id" field="pid" ></t:dgCol>
   <t:dgCol title="农产品中文名" field="cname" ></t:dgCol>
   <t:dgCol title="农产品中文别名" field="calias" ></t:dgCol>
   <t:dgCol title="农产品拉丁名" field="latin" ></t:dgCol>
   <t:dgCol title="图片路径" field="imagepath" ></t:dgCol>
   <t:dgCol title="描述" field="describe" ></t:dgCol>
   <t:dgCol title="GEMS" field="gems" ></t:dgCol>
   <t:dgCol title="农产品英文别名" field="ealias" ></t:dgCol>
   <t:dgCol title="农产品英文名" field="ename" ></t:dgCol>
   <t:dgCol title="类型(1:类,2.物类,2:农产品)" field="agrcategorytype" ></t:dgCol>
   <t:dgCol title="FOODEX" field="foodex" ></t:dgCol>
   <t:dgCol title="创建人" field="creater" ></t:dgCol>
   <t:dgCol title="修改人" field="editor" ></t:dgCol>
   <t:dgCol title="修改时间" field="editdate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="code" field="code" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="agrCategoryController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="agrCategoryController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="agrCategoryController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="agrCategoryController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>