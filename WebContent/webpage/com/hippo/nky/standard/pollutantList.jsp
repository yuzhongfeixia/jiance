<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center"  border="false">
  <t:datagrid name="pollutantList"  actionUrl="pollutantController.do?datagrid&categoryid=${categoryid}&versionid=${versionid}" idField="id" fit="true" onDblClick="checkPublishForUpdate('pollutantController.do?addorupdate','${versionid}','pollutantList');">
   <t:dgCol title="编号" field="id" ></t:dgCol>
   <t:dgCol title="污染物(中文名)" field="cname" query="true"></t:dgCol>
   <t:dgCol title="污染物(英文名)" field="ename" query="true"></t:dgCol>
   <t:dgCol title="污染物(拉丁文名)" field="latin" query="true"></t:dgCol>
   <t:dgCol title="污染物序号" field="porder" query="true"></t:dgCol>
   <t:dgCol title="化学结构" field="structure" query="true"></t:dgCol>
   <t:dgCol title="分类一" field="sort1" query="true"></t:dgCol>
   <t:dgCol title="分类二" field="sort2" query="true"></t:dgCol>
   <t:dgCol title="分类标准编号（污染物）" field="sortcode" query="true"></t:dgCol>
   <t:dgCol title="CAS码" field="cascode" query="true"></t:dgCol>
   <t:dgCol title="类别" field="category" query="true"></t:dgCol>
   <t:dgCol title="用途" field="use" query="true"></t:dgCol>
   <t:dgCol title="每日允许摄入量" field="adi" query="true"></t:dgCol>
   <t:dgCol title="残留物" field="residue" query="true"></t:dgCol>
   <t:dgCol title="中文商品名" field="pname" query="true"></t:dgCol>
   <t:dgCol title="药物类型" field="medtype" query="true"></t:dgCol>
   <t:dgCol title="分类id" field="categoryid" hidden="hidden"></t:dgCol>
   <t:dgCol title="版本id" field="versionid"  hidden="hidden"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="checkPublishForDelete(id,versionid)" title="删除" />
   <t:dgToolBar title="录入" icon="icon-add" onclick="checkPublishForAdd('pollutantController.do?addorupdate&categoryid=${categoryid}&versionid=${versionid}','${versionid}','pollutantList');" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" onclick="checkPublishForUpdate('pollutantController.do?addorupdate','${versionid}','pollutantList');" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="pollutantController.do?addorupdate" funname="detail"></t:dgToolBar>
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
	delObj("pollutantController.do?del&id=" + id, "pollutantList");
}
</script>