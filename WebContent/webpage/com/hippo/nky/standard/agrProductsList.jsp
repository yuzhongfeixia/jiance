<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center"  border="false">
  <t:datagrid name="agrProductsList"  actionUrl="agrProductsController.do?datagrid&versionid=${versionid}&categoryid=${categoryid}" idField="id" fit="true" onDblClick="checkPublishForUpdate('agrProductsController.do?addorupdate','${versionid}','agrProductsList')">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="英文名称" field="ename" ></t:dgCol>
   <t:dgCol title="中文名称" field="cname" ></t:dgCol>
   <t:dgCol title="拉丁文名称" field="latin" ></t:dgCol>
   <t:dgCol title="农产品编码" field="agrcode" ></t:dgCol>
   <t:dgCol title="国际编码" field="codex" ></t:dgCol>
   <t:dgCol title="FOODEX" field="foodex" ></t:dgCol>
   <t:dgCol title="创建时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="描述" field="describe" ></t:dgCol>
   <t:dgCol title="分类id" field="categoryid" hidden="hidden"></t:dgCol>
   <t:dgCol title="版本id" field="versionid"  hidden="hidden"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="checkPublishForDelete(id,versionid)" title="删除" />
   <t:dgToolBar title="录入" icon="icon-add"  funname="add" onclick="checkPublishForAdd('agrProductsController.do?addorupdate&versionid=${versionid}&categoryid=${categoryid}','${versionid}','agrProductsList');"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" onclick="checkPublishForUpdate('agrProductsController.do?addorupdate','${versionid}','agrProductsList');" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="agrProductsController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
//删除之前进行版本是否发布的check
function checkPublishForDelete(id, versionid){
	if(isPublished(versionid)){
		modalAlert("版本已经发布,不能再进行删除操作!");
		return;
	}
	delObj("agrProductsController.do?del&id=" + id, "agrProductsList");
}
</script>