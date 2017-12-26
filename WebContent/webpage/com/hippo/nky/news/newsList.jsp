<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="newsList" title="农科院新闻" actionUrl="newsController.do?datagrid" idField="id" fit="true" onDblClick="createwindow('编辑','newsController.do?addorupdate&id=' + rowid)">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="新闻标题" field="title" query="true"></t:dgCol>
   <t:dgCol title="新闻类型" field="type" dictionary="newstype"></t:dgCol>
   <t:dgCol title="新闻关键字" field="keywords" ></t:dgCol>
   <t:dgCol title="文章来源" field="author" ></t:dgCol>
   <t:dgCol title="是否置顶" field="sort" dictionary="newssort" query="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="newsController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="newsController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="newsController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="newsController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>