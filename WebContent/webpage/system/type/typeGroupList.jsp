<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/navigator.jsp" %>
<%@ include file="../../../context/mytags.jsp" %>
<%-- <%@ include file="../../common/scripts.jsp" %> --%>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>

<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<link href="assets/plugins/treetable/stylesheets/screen.css" media="screen" />
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="assets/plugins/treetable/jquery.treetable.js"></script>

<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="clearfix">
						<div class="btn-group">
							<a href="systemController.do?aouTypeGroup" class="btn btngroup_usual" action-mode="ajax" action-event="click" action-pop="#ajax-modal" ><i class="icon-plus"></i>字典分类录入</a>
						</div>
					</div>
					<table id="typeGroupTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>字典名称 </th>
								<th>字典编码 </th>
								<th>操作</th>
							</tr>
						</thead>
<!-- 						<tbody> -->
<%-- 							<c:forEach items="${typeGupList}" var="typegroup"> --%>
<%--  								<tr data-tt-id="${typegroup.treeId}" data-tt-parent-id="${typegroup.parentId}"> --%>
<%-- 									<td>${typegroup.text}</td> --%>
<%-- 									<td>${typegroup.code}</td> --%>
<!-- 									<td> -->
<%-- 										<c:if test="${typegroup.parentId == null}"> --%>
<%-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="systemController.do?aouTypeGroup&id=${typegroup.id}"><i class="icon-edit"></i>编辑</a> --%>
<%-- 											<a id="ajax_del_All" class="btn mini red" data-toggle="modal" onclick="del('${typegroup.id}')"><i class="icon-remove"></i>删除</a> --%>
<%-- 											<a id="ajax_add_Type" class="btn mini green" data-toggle="modal" onclick="addType('${typegroup.id}')"> <i class="icon-plus"></i>录入</a> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${typegroup.parentId != null}"> --%>
<%-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="systemController.do?addorupdateType&id=${typegroup.id}&typegroupid=${typegroup.typegroupid}"><i class="icon-edit"></i>编辑</a> --%>
<%-- 											<a id="ajax_del_Type" class="btn mini red" data-toggle="modal" onclick="del('${typegroup.id }')"><i class="icon-remove"></i>删除</a> --%>
<%-- 										</c:if> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:forEach> --%>
<!-- 					    </tbody> -->
	     			</table>
				</div>
			</div>	
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<script>
	
	var treeTable = null;
	jQuery(document).ready(function() {
		refresh_typeGroupList();
	});
	
	function refresh_typeGroupList(ad) {
		$.ajax({
			async: false,
			type : 'POST',
			url : 'systemController.do?typeGridTree',
			data:{},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				if(dataJson.success){
					// 总抽样数统计列表取得
					var typeGupList = dataJson.attributes.typeGupList;
					drewTableCells(typeGupList);
				}
			}
		});
		
		if (treeTable != null) {
			$("#typeGroupTable").removeData("treetable").removeClass("treetable");
		}
		treeTable = $("#typeGroupTable").treetable({ expandable: true });

		if (ad != undefined) {
			var expendId = ad.attributes.expendId;
			if (isNotEmpty(expendId)) {
				$("#typeGroupTable").treetable("expandNode", expendId);
			}
		}
		
	}
	
	function drewTableCells(list){	

		$("#typeGroupTable").html("");
		var htmls = '<thead><tr>';
		htmls += '<th>字典名称 </th>';
		htmls += '<th>字典编码</th>';
		htmls += '<th>操作</th>';
		htmls += '</thead></tr>';
		htmls += '<tbody>';
		if(list != null && list.length > 0){
			for (var i=0;i<list.length;i++) {
				htmls += '<tr data-tt-id="'+list[i].treeId+'" data-tt-parent-id="'+list[i].parentId+'">';
				htmls += '<td>'+list[i].text+'</td> ';
				htmls += '<td>'+list[i].code+'</td> ';
				htmls += '<td>';
				if (isEmpty(list[i].parentId)) {
					htmls += '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="systemController.do?aouTypeGroup&id='+list[i].id+'"><i class="icon-edit"></i>编辑</a>';
					htmls += '\n<a id="ajax_del_All" class="btn mini red" data-toggle="modal" onclick="del(\''+list[i].id+'\')"><i class="icon-remove"></i>删除</a>';
					htmls += '\n<a id="ajax_add_Type" class="btn mini green" data-toggle="modal" onclick="addType(\''+list[i].id+'\',\''+list[i].treeId+'\')"> <i class="icon-plus"></i>录入</a>';
				} else {
					htmls += '\n<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="systemController.do?addorupdateType&id='+list[i].id+'&typegroupid='+list[i].typegroupid+'&pid='+list[i].parentId+'"><i class="icon-edit"></i>编辑</a>';
					htmls += '\n<a id="ajax_del_Type" class="btn mini red" data-toggle="modal" onclick="del(\''+list[i].id+'\',\''+list[i].parentId+'\')"><i class="icon-remove"></i>删除</a>';
				}
				htmls += '</td>'
				htmls += '</tr>';
			}
		} 
		htmls += '</tbody>';
		$("#typeGroupTable").html(htmls);		
	}

	//增加分类
	function addType(data,pid) {
		//查看
	   	var $modal = $('#ajax-modal'); 
		// create the backdrop and wait for next modal to be triggered
		var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
	     $modal.load('systemController.do?addorupdateType&typegroupid='+data+'&pid='+pid, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

	function del(data,pid) {
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "systemController.do?delTypeGridTree&pid="+pid+"&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				 refresh_typeGroupList(d);
			   				 modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
	}
</script>

</body>