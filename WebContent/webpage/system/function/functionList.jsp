<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/navigator.jsp" %>
<%@ include file="../../../context/mytags.jsp" %>
<%-- <%@ include file="../../common/scripts.jsp" %> --%>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<!-- <script src="assets/scripts/table-managed.js"></script> -->

<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<!-- <script src="assets/scripts/function-manager.js"></script> -->
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<link href="assets/plugins/treetable/stylesheets/screen.css" media="screen" />
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="assets/plugins/treetable/jquery.treetable.js"></script>

<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
	<div class="row-fluid">
<!-- 		<div class="span6" style="width:71%"> -->
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i>菜单管理</div>
				</div>
				<div class="portlet-body">
					<div class="clearfix">
						<div class="btn-group">
							<a href="functionController.do?addorupdate" class="btn btngroup_usual" action-mode="ajax" action-event="click" action-pop="#ajax-modal" ><i class="icon-plus"></i>菜单录入</a>
						</div>
					</div>
					<table id="functionTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>菜单名称 </th>
								<th>图标 </th>
								<th>菜单地址</th>
								<th>菜单顺序</th>
								<th>操作</th>
							</tr>
						</thead>
<!-- 						<tbody> -->
<%-- 							<c:forEach items="${funList}" var="function"> --%>
<%--  								<tr data-tt-id="${function.id}" data-tt-parent-id="${function.parentId}"> --%>
<%-- 									<td>${function.text}</td> --%>
<%-- 									<td><i class="${function.code}"></i></td> --%>
<%-- 									<td>${function.src}</td> --%>
<%-- 									<td>${function.order}</td> --%>
<!-- 									<td> -->
<%-- 										<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="functionController.do?addorupdate&id=${function.id }&parentId=${function.parentId}"><i class="icon-edit"></i>编辑</a> --%>
<%-- 										<a id="ajax_del" class="btn mini red" data-toggle="modal" onclick="delFunction('${function.id }')"><i class="icon-remove"></i>删除</a> --%>
<%-- 										<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="functionController.do?addorupdate&id=${function.id }&isChildAdd='yes'"><i class="icon-plus"></i>录入</a> --%>
<%-- 										<a id="ajax_btn_set" class="btn mini red" onclick="loadContent(this, 'functionController.do?operation&functionId=${function.id}')"><i class="icon-edit"></i>按钮设置</a> --%>
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
			refresh_functionList();
		});
		
		function refresh_functionList(ad) {
			$.ajax({
				async: false,
				type : 'POST',
				url : 'functionController.do?functionGrid',
				data:{},
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					if(dataJson.success){
						// 总抽样数统计列表取得
						var funList = dataJson.attributes.funList;
						drewTableCells(funList);
					}
				}
			});
			
			if (treeTable != null) {
				$("#functionTable").removeData("treetable").removeClass("treetable");
			}
			treeTable = $("#functionTable").treetable({ expandable: true });
			
			if (ad != undefined) {
				var expendId = ad.attributes.expendId;
				// 递归展开父节点
				if (isNotEmpty(expendId)) {
					expendParent(expendId);
					$("#functionTable").treetable("expandNode", expendId);
				}
				
			}
			
			function expendParent(id) {
				var parentId = $("#"+id).attr("data-tt-parent-id");
				if (isNotEmpty(parentId)) {
					$("#functionTable").treetable("expandNode", parentId);
					expendParent(parentId);
				}
			}
			
		}
		
		function drewTableCells(list){	

			$("#functionTable").html("");
			var htmls = '<thead><tr>';
			htmls += '<th>菜单名称 </th>';
			htmls += '<th>图标 </th>';
			htmls += '<th>菜单地址</th>';
			htmls += '<th>菜单顺序</th>';
			htmls += '<th>操作</th>';
			htmls += '</thead></tr>';
			htmls += '<tbody>';
			if(list != null && list.length > 0){
				for (var i=0;i<list.length;i++) {
					htmls += '<tr id="'+list[i].id+'" data-tt-id="'+list[i].id+'" data-tt-parent-id="'+list[i].parentId+'">';
					htmls += '<td>'+list[i].text+'</td> ';
					htmls += '<td><i class="'+list[i].code+'"></i></td> ';
					htmls += '<td>'+list[i].src+'</td> ';
					htmls += '<td>'+list[i].order+'</td> ';
					htmls += '<td>';
					htmls += '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="functionController.do?addorupdate&id='+list[i].id+'&parentId='+list[i].parentId+'"><i class="icon-edit"></i>编辑</a>';
					htmls += '\n<a id="ajax_del" class="btn mini red" data-toggle="modal" onclick="delFunction(\''+list[i].id+'\')"><i class="icon-remove"></i>删除</a>';
					htmls += '\n<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="functionController.do?addorupdate&id='+list[i].id+'&isChildAdd=yes"><i class="icon-plus"></i>录入</a>';
					htmls += '\n<a id="ajax_btn_set" class="btn mini red" onclick="loadContent(this, \'functionController.do?operation&functionId='+list[i].id+'\')"><i class="icon-edit"></i>按钮设置</a>';
					htmls += '</td>'
					htmls += '</tr>';
				}
			} 
			htmls += '</tbody>';
			$("#functionTable").html(htmls);		
		}
		
		function delFunction(data) {
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '你确认删除所选记录?',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "functionController.do?del&id="+data+"&rand=" + Math.random(),
						data : "id=" + data,
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if (d.success) {
				   				refresh_functionList(d);
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