<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<t:datagrid name="introductionList" title="栏目管理" actionUrl="nkyPortalIntroductionsController.do?introductionsGrid" idField="id" treegrid="true" pagination="false">
 <t:dgCol title="编号" field="id" treefield="id" hidden="false"></t:dgCol>
 <t:dgCol title="栏目名称" field="name" width="100" treefield="text"></t:dgCol>
 <t:dgCol title="创建时间" field="createdate" treefield="src"></t:dgCol>
<%-- <t:dgCol title="操作" field="opt" width="100" ></t:dgCol>
	<t:dgDelOpt url="nkyPortalIntroductionsController.do?del&id={id}" title="删除"></t:dgDelOpt>  --%>   
 <t:dgToolBar title="栏目录入" icon="icon-add" url="nkyPortalIntroductionsController.do?addorupdate" funname="add"></t:dgToolBar>
 <t:dgToolBar title="栏目编辑" icon="icon-edit" url="nkyPortalIntroductionsController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid>
</div>
<script type="text/javascript">
  function test(id,text){
	  //doSubmit('nkyPortalIntroductionsController.do?del&id='+id,'introductionList');	  
	  //var parent = $("#introductionList").treegrid('getParent',id);
	  //$("#introductionList").treegrid('remove',id);
	  //alert(parent.id);
	  //$("#introductionList").treegrid('expand',parent.id);
	  //alert('sg');
	  
  }
  
  function doSubmit(url,name) {
		gridname=name;
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					
					reloadTable();
				}
			}
		});
	}
  
  function check(id){
	  //$('#introductionList').treegrid("expand",id);
	  //var nodes  = $('#introductionList').treegrid("getChildren",id);
		//if(nodes.length>0)
			//alert("aa"+nodes.length);
  }


</script>
</div>

